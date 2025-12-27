<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="clienteServidor.publicar.*" %>
<%
    DtUsuario usuario = (DtUsuario) request.getAttribute("usuario");
    String tipo = (String) request.getAttribute("tipo_usuario");
    String context = request.getContextPath();
    String mensajeError = (String) session.getAttribute("mensaje_editar");
%>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Modificar datos del usuario</title>
  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
  <style>
    /* pequeños ajustes visuales */
    .form-row > .col { padding-right: 8px; padding-left: 8px; }
    .profile-img-preview { max-width: 120px; max-height: 120px; object-fit: cover; border-radius: 8px; }
  </style>
</head>
<body>

  <jsp:include page="/WEB-INF/templates/navbar.jsp"/>

  <div class="container py-5">
    <h1 class="text-center mb-4">Modificar datos del Usuario</h1>

    <div class="card mx-auto" style="max-width: 900px;">
      <div class="card-body">

        <!-- NOTE: enctype necesario para file upload -->
        <form id="editarForm" action="<%= context %>/editar-perfil" method="post" enctype="multipart/form-data" novalidate>
          <!-- fila nickname + email (no editables) -->
          <div class="form-row">
            <div class="form-group col-md-6">
              <label for="nickname">Nickname</label>
              <input type="text" class="form-control" id="nickname" name="nickname"
                     value="<%= usuario.getNickname() %>" disabled
                     data-original="<%= usuario.getNickname() %>">
            </div>

            <div class="form-group col-md-6">
              <label for="correo">Email</label>
              <input type="email" class="form-control" id="correo" name="correo"
                     value="<%= usuario.getEmail() != null ? usuario.getEmail() : "" %>" disabled
                     data-original="<%= usuario.getEmail() != null ? usuario.getEmail() : "" %>">
            </div>
          </div>

          <div class="form-row">
            <div class="form-group col-md-6">
              <label for="nombre">Nombre</label>
              <input type="text" class="form-control" id="nombre" name="nombre"
                     value="<%= usuario.getNombre() != null ? usuario.getNombre() : "" %>"
                     data-original="<%= usuario.getNombre() != null ? usuario.getNombre() : "" %>">
            </div>

            <div class="form-group col-md-6">
              <label for="imgFile">Imagen de perfil</label>
              <div class="mb-2 d-flex align-items-center">
                <img id="imgPreview" class="profile-img-preview mr-3"
                     src="<%= usuario.getImg() %>"
                     alt="preview">
                <div>
                  <input type="file" id="imagen" name="imagen" accept="image/*" class="form-control-file">
                  <small class="form-text text-muted">Si subes una nueva imagen se eliminará la anterior. Se guardará como <code><%= usuario.getNickname() %>.png</code>.</small>
                </div>
              </div>
              <!-- hidden con valor original para comparación -->
              <input type="hidden" id="imgOriginal" value="<%= usuario.getImg() != null ? usuario.getImg() : "" %>">
            </div>
          </div>

          <div class="form-row">
            <div class="form-group col-md-6">
              <label for="password">Contraseña</label>
              <input type="password" class="form-control" id="password" name="password" placeholder="Dejar vacío para mantener la actual">
            </div>
            <div class="form-group col-md-6">
              <label for="passwordConfirm">Confirmar Contraseña</label>
              <input type="password" class="form-control" id="passwordConfirm" name="passwordConfirm" placeholder="Repetir contraseña">
            </div>
          </div>

          <% if ("asistente".equalsIgnoreCase(tipo) && usuario instanceof DtAsistente) {
               DtAsistente a = (DtAsistente) usuario; %>

            <div class="form-row">
              <div class="form-group col-md-6">
                <label for="apellido">Apellido</label>
                <input type="text" class="form-control" id="apellido" name="apellido"
                       value="<%= a.getApellido() != null ? a.getApellido() : "" %>"
                       data-original="<%= a.getApellido() != null ? a.getApellido() : "" %>">
              </div>

              <div class="form-group col-md-6">
                <label for="nacimiento">Fecha de nacimiento</label>
                <input type="date" class="form-control" id="nacimiento" name="nacimiento"
                       value="<%= a.getNacimiento() != null ? a.getNacimiento().toString() : "" %>"
                       data-original="<%= a.getNacimiento() != null ? a.getNacimiento().toString() : "" %>">
              </div>
            </div>

            <div class="form-group">
              <label>Institución</label>
              <input type="text" class="form-control"
                     value="<%= a.getInstitucion() != null ? a.getInstitucion() : "Aún no pertenece a ninguna institución" %>" disabled>
            </div>

          <% } else if ("organizador".equalsIgnoreCase(tipo) && usuario instanceof DtOrganizador) {
               DtOrganizador o = (DtOrganizador) usuario; %>

            <div class="form-row">
              <div class="form-group col-md-6">
                <label for="descripcion">Descripción</label>
                <input type="text" class="form-control" id="descripcion" name="descripcion"
                       value="<%= o.getDescripcion() != null ? o.getDescripcion() : "" %>"
                       data-original="<%= o.getDescripcion() != null ? o.getDescripcion() : "" %>">
              </div>

              <div class="form-group col-md-6">
                <label for="sitioWeb">Sitio web</label>
                <input type="url" class="form-control" id="sitioWeb" name="sitioWeb"
                       value="<%= o.getSitioWeb() != null ? o.getSitioWeb() : "" %>"
                       data-original="<%= o.getSitioWeb() != null ? o.getSitioWeb() : "" %>">
              </div>
            </div>

          <% } %>

          <div class="d-flex">
            <button type="submit" class="btn btn-primary mr-2">Guardar Cambios</button>
            <a href="<%= context %>/perfil" class="btn btn-secondary">Cancelar</a>
          </div>
        </form>

      </div>
    </div>
  </div>

  <!-- scripts -->
  <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
  <script>
  
  
    // Preview de imagen cargada
    document.getElementById('imagen').addEventListener('change', function (e) {
      const file = this.files[0];
      const imgPreview = document.getElementById('imgPreview');
      if (file) {
        const reader = new FileReader();
        reader.onload = function (ev) { imgPreview.src = ev.target.result; };
        reader.readAsDataURL(file);
      } else {
        // si quita la selección, restaurar preview original
        imgPreview.src = '<%= usuario.getImg() != null ? request.getContextPath() + "/" + usuario.getImg() : request.getContextPath() + "/media/images/defaultUser.png" %>';
      }
    });

    // Chequeo antes de enviar: si no hubo cambios, evitar POST
    document.getElementById('editarForm').addEventListener('submit', function (e) {
      // campos a comparar
      const inputsToCheck = ['nombre','apellido','nacimiento','descripcion','sitioWeb'];
      let changed = false;

      // revisar inputs (si existen en el DOM)
      inputsToCheck.forEach(function (id) {
        const el = document.getElementById(id);
        if (!el) return;
        const original = el.getAttribute('data-original') || '';
        const current = (el.value || '').trim();
        if (current !== (original || '').trim()) changed = true;
      });

      // password -> change only if user wrote something
      const pw = document.getElementById('password').value;
      if (pw && pw.trim().length > 0) changed = true;

      // file
      const fileInput = document.getElementById('imagen');
      if (fileInput && fileInput.files && fileInput.files.length > 0) changed = true;

      if (!changed) {
        e.preventDefault();
        alert(<%=mensajeError%>);
        return false;
      }
      

      // if password provided, require confirm equal
      const pwConfirm = document.getElementById('passwordConfirm').value;
      if (pw && pw.trim().length > 0) {
        if (pw !== pwConfirm) {
          e.preventDefault();
          alert('Las contraseñas no coinciden.');
          return false;
        }
      }
      // allow submit
    });
  </script>

  <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
