<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="clienteServidor.publicar.*" %>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Registro</title>
  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
  <style>
    .required::after { content:" *"; color:red; }
    .preview-img {
      width: 100px;
      height: 100px;
      object-fit: cover;
      border-radius: 50%;
      display: block;
      margin: 0 auto 10px;
    }
  </style>
</head>
<body class="bg-light">

  <jsp:include page="/WEB-INF/templates/navbar.jsp"/>

  <main class="container py-5">
    <div class="row justify-content-center">
      <div class="col-lg-8">
        <div class="card shadow-sm">
          <div class="card-body">
            <h3 class="card-title mb-4 text-center">Crear cuenta</h3>

            <%
			  String mensaje = (String) request.getAttribute("mensaje_registro");
			  String tipoMensaje = (String) request.getAttribute("tipo_mensaje");
			  if (mensaje == null) { // Si viene de redirección (éxito)
			      mensaje = (String) session.getAttribute("mensaje_registro");
			      tipoMensaje = (String) session.getAttribute("tipo_mensaje");
			      if (mensaje != null) {
			          session.removeAttribute("mensaje_registro");
			          session.removeAttribute("tipo_mensaje");
			      }
			  }
			  if (mensaje != null) {
			%>
			  <div class="alert <%= "error".equals(tipoMensaje) ? "alert-danger" : "alert-success" %> text-center">
			    <%= mensaje %>
			  </div>
			<% } %>


            <form id="registroForm" action="${pageContext.request.contextPath}/registrar" method="post" enctype="multipart/form-data">

              <div class="form-group">
                <label class="required" for="tipo">Tipo de usuario</label>
                <select class="form-control" id="tipo" name="tipo" required>
                  <option value="asistente" <%= "asistente".equals(request.getParameter("tipo")) ? "selected" : "" %>>Asistente</option>
                  <option value="organizador" <%= "organizador".equals(request.getParameter("tipo")) ? "selected" : "" %>>Organizador</option>
                </select>
              </div>

              <div class="form-row">
                <div class="form-group col-md-6">
                  <label class="required" for="nickname">Nickname</label>
                  <input type="text" id="nickname" name="nickname" class="form-control" placeholder="Tu nickname"
                         value="<%= request.getParameter("nickname") != null ? request.getParameter("nickname") : "" %>" required>
                  <small id="nickMsg"></small>
                </div>
                <div class="form-group col-md-6">
                  <label class="required" for="email">Correo electrónico</label>
                  <input type="email" id="email" name="email" class="form-control" placeholder="Tu correo"
                         value="<%= request.getParameter("email") != null ? request.getParameter("email") : "" %>" required>
                  <small id="mailMsg"></small>
                </div>
              </div>

              <div id="asistenteFields">
                <div class="form-row">
                  <div class="form-group col-md-6">
                    <label class="required" for="nombreAsistente">Nombre</label>
                    <input type="text" class="form-control" id="nombreAsistente" name="nombreAsistente"
                           value="<%= "asistente".equals(request.getParameter("tipo")) && request.getParameter("nombreAsistente") != null ? request.getParameter("nombreAsistente") : "" %>" >
                  </div>
                  <div class="form-group col-md-6">
                    <label class="required" for="apellido">Apellido</label>
                    <input type="text" class="form-control" id="apellido" name="apellido"
                           value="<%= "asistente".equals(request.getParameter("tipo")) && request.getParameter("apellido") != null ? request.getParameter("apellido") : "" %>" >
                  </div>
                </div>

                <div class="form-row">
                  <div class="form-group col-md-6">
                    <label class="required" for="fechaNacimiento">Fecha de nacimiento</label>
                    <input type="date" class="form-control" id="fechaNacimiento" name="fechaNacimiento"
                           value="<%= request.getParameter("fechaNacimiento") != null ? request.getParameter("fechaNacimiento") : "" %>">
                  </div>
                  <div class="form-group col-md-6">
                    <label for="institucion">Institución</label>
                    <select class="form-control" id="institucion" name="institucion">
                      <option value="">Selecciona una institución (opcional)</option>
                      <%
                        DtInstitucion[] instituciones = (DtInstitucion[]) request.getAttribute("instituciones");
                        String instSeleccionada = request.getParameter("institucion");
                        if (instituciones != null) {
                          for (DtInstitucion inst : instituciones) {
                            String selected = (instSeleccionada != null && instSeleccionada.equals(inst.getNombre())) ? "selected" : "";
                      %>
                        <option value="<%= inst.getNombre() %>" <%= selected %>><%= inst.getNombre() %></option>
                      <%
                          }
                        }
                      %>
                    </select>
                  </div>
                </div>
              </div>

              <div id="organizadorFields" style="display:none;">
                <div class="form-row">
                  <div class="form-group col-md-6">
                    <label class="required" for="nombreOrg">Nombre</label>
                    <input type="text" class="form-control" id="nombreOrg" name="nombreOrg"
                           value="<%= "organizador".equals(request.getParameter("tipo")) && request.getParameter("nombreOrg") != null ? request.getParameter("nombreOrg") : "" %>">
                  </div>
                  <div class="form-group col-md-6">
                    <label class="required" for="descripcion">Descripción</label>
                    <input type="text" class="form-control" id="descripcion" name="descripcion"
                           value="<%= request.getParameter("descripcion") != null ? request.getParameter("descripcion") : "" %>">
                  </div>
                </div>

                <div class="form-group">
                  <label for="sitioWeb">Sitio Web</label>
                  <input type="url" class="form-control" id="sitioWeb" name="sitioWeb" placeholder="https://..."
                         value="<%= request.getParameter("sitioWeb") != null ? request.getParameter("sitioWeb") : "" %>">
                </div>
              </div>

              <div class="form-group text-center">
                <img id="preview" src="${pageContext.request.contextPath}/media/imagenes/placeholderUsers.png" class="preview-img">
                <label for="imagen">Imagen de perfil</label>
                <input type="file" class="form-control-file" id="imagen" name="imagen" accept="image/*">
              </div>

              <div class="form-row">
                <div class="form-group col-md-6">
                  <label class="required" for="password">Contraseña</label>
                  <input type="password" class="form-control" id="password" name="password" minlength="6" required>
                </div>
                <div class="form-group col-md-6">
                  <label class="required" for="confirmPassword">Confirmar contraseña</label>
                  <input type="password" class="form-control" id="confirmPassword" name="confirmPassword" minlength="6" required>
                </div>
              </div>

              <button type="submit" class="btn btn-success btn-block">Registrarse</button>
              <p class="mt-3 text-center small">¿Ya tienes cuenta? <a href="${pageContext.request.contextPath}/iniciar-sesion">Inicia sesión</a></p>
            </form>


          </div>
        </div>
      </div>
    </div>
  </main>

<script>
  document.addEventListener("DOMContentLoaded", () => {
    const nickInput = document.getElementById("nickname");
    const mailInput = document.getElementById("email");
    const nickMsg = document.getElementById("nickMsg");
    const mailMsg = document.getElementById("mailMsg");

    // --- Funciones de validación de disponibilidad (se mantienen igual) ---
    async function checkDisponibilidad(paramName, value) {
      const url = "datosJson?" + paramName + "=" + value;
      const res = await fetch(url);
      if (!res.ok) return null;
      const data = await res.json();
      return data;
    }

    nickInput.addEventListener("input", async () => {
      const nick = nickInput.value.trim();
      if (nick === "") {
        nickMsg.textContent = "";
        return;
      }
      const existe = await checkDisponibilidad("existeNick", nick);
      if (existe === null) return;
      nickMsg.textContent = existe ? "❌ Nickname no disponible" : "✅ Nickname disponible";
      nickMsg.style.color = existe ? "red" : "green";
    });

    mailInput.addEventListener("input", async () => {
      const mail = mailInput.value.trim();
      if (mail === "") {
        mailMsg.textContent = "";
        return;
      }
      const existe = await checkDisponibilidad("existeMail", mail);
      if (existe === null) return;
      mailMsg.textContent = existe ? "❌ Correo ya registrado" : "✅ Correo disponible";
      mailMsg.style.color = existe ? "red" : "green";
    });

    // --- Lógica de alternancia y REQUIRED corregida ---
    const tipo = document.getElementById("tipo");
    const asistenteFields = document.getElementById("asistenteFields");
    const organizadorFields = document.getElementById("organizadorFields");

    const asistenteInputs = asistenteFields.querySelectorAll("input[type=text], input[type=date]");
    const organizadorInputs = organizadorFields.querySelectorAll("input[type=text], input[type=url]");
    const apellidoInput = document.getElementById("apellido"); // Campo obligatorio en asistente

    function toggleFields() {
      const isOrganizador = tipo.value === "organizador";

      // Mostrar/Ocultar los contenedores
      asistenteFields.style.display = isOrganizador ? "none" : "block";
      organizadorFields.style.display = isOrganizador ? "block" : "none";

      // Administrar el atributo 'required' en campos específicos
      // 1. Asistente (nombreAsistente, apellido, fechaNacimiento)
      asistenteInputs.forEach(input => {
        // La institución no es obligatoria, así que la omitimos
        if (input.id !== 'institucion') {
            input.required = !isOrganizador;
        }
      });

      // 2. Organizador (nombreOrg, descripcion)
      organizadorInputs.forEach(input => {
        // Sitio Web (type=url) no es obligatorio
        if (input.id !== 'sitioWeb') {
            input.required = isOrganizador;
        }
      });

      // Para evitar que los campos ocultos causen problemas de validación en navegadores viejos,
      // podemos deshabilitar los campos que no se envían.
      // Sin embargo, como estamos controlando el 'required' de forma estricta,
      // esto no debería ser necesario.
    }

    tipo.addEventListener("change", toggleFields);
    toggleFields(); // Llamar al inicio para establecer el estado inicial

    // --- Manejo de imagen (se mantiene igual) ---
    document.getElementById("imagen").addEventListener("change", (e) => {
      const [file] = e.target.files;
      if (file) document.getElementById("preview").src = URL.createObjectURL(file);
    });

    // --- Validación de contraseña (se mantiene igual) ---
    document.getElementById("registroForm").addEventListener("submit", (e) => {
      const pass = document.getElementById("password").value;
      const confirm = document.getElementById("confirmPassword").value;
      if (pass !== confirm) {
        e.preventDefault();
        const existingAlert = document.querySelector(".alert-danger");
        if (existingAlert) existingAlert.remove();

        const alertDiv = document.createElement("div");
        alertDiv.className = "alert alert-danger text-center mt-3";
        alertDiv.textContent = "❌ Las contraseñas no coinciden.";
        const form = document.getElementById("registroForm");
        form.prepend(alertDiv);
      }
    });
  });
</script>

</body>
</html>
