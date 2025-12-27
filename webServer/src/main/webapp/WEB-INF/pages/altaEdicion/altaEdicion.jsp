<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="jakarta.servlet.*" %>
<%@ page import="clienteServidor.publicar.*" %>
<%
    List<String> eventosDisponibles = (List<String>) request.getAttribute("eventosDisponibles");
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Alta de Edición de Evento</title>
    <link
        rel="stylesheet"
        href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
    />
</head>
<body>

<!-- navbar -->
<jsp:include page="/WEB-INF/templates/navbar.jsp"/>

<div class="container mt-3">
    <h1 class="text-center mb-4">Alta De Edición De Evento</h1>

    <div id="notLogged" class="alert alert-warning text-center" style="display:none;">
        No está logueado. Por favor <a href="./Login.html">inicie sesión</a> para acceder a esta funcionalidad.
    </div>

    <div id="notOrg" class="alert alert-warning text-center" style="display:none;">
        Debe ser organizador para acceder a esta función. <a href="../index.html">Home</a>.
    </div>

    <% if (request.getAttribute("error") != null) { %>
    <div class="alert alert-danger text-center">
        <%= request.getAttribute("error") %>
    </div>
    <% } %>

    <!-- Formulario -->
    <form id="formulario" method="post" action="<%= request.getContextPath() %>/altaEdicion" enctype="multipart/form-data">
        <fieldset>
            <div class="mb-3">
                <label for="EventosDisponibles" class="form-label">Seleccione un Evento*</label>
                <select id="EventosDisponibles" name="nombreEvento" class="form-control" required>
                    <%
                        String nombreEventoSel = (String) request.getAttribute("nombreEvento");
                        if (eventosDisponibles != null) {
                            for (String evento : eventosDisponibles) {
                                String selected = (nombreEventoSel != null && nombreEventoSel.equals(evento)) ? "selected" : "";
                    %>
                        <option value="<%= evento %>" <%= selected %>><%= evento %></option>
                    <%
                            }
                        }
                    %>
                </select>
            </div>

            <div class="row mb-4">
                <div class="col-8">
                    <label for="nombreEdicion" class="form-label">Nombre*</label>
                    <input 
                        type="text" 
                        id="nombreEdicion" 
                        name="nombreEdicion" 
                        class="form-control" 
                        placeholder="Ej.: Web Summit 2026" 
                        required
                        value="<%= request.getAttribute("nombreEdicion") != null ? request.getAttribute("nombreEdicion") : "" %>">
                </div>
                <div class="col-4">
                    <label for="siglaEdicion" class="form-label">Sigla*</label>
                    <input 
                        type="text" 
                        id="siglaEdicion" 
                        name="siglaEdicion" 
                        class="form-control" 
                        placeholder="Ej.: WS" 
                        required
                        value="<%= request.getAttribute("siglaEdicion") != null ? request.getAttribute("siglaEdicion") : "" %>">
                </div>
            </div>

            <div class="row mb-4">
                <div class="col-6 mb-4">
                    <label for="paisEdicion" class="form-label">País*</label>
                    <input 
                        type="text" 
                        id="paisEdicion" 
                        name="paisEdicion" 
                        class="form-control" 
                        placeholder="Ej.: Portugal" 
                        required
                        value="<%= request.getAttribute("paisEdicion") != null ? request.getAttribute("paisEdicion") : "" %>">
                </div>
                <div class="col-6 mb-4">
                    <label for="ciudadEdicion" class="form-label">Ciudad*</label>
                    <input 
                        type="text" 
                        id="ciudadEdicion" 
                        name="ciudadEdicion" 
                        class="form-control" 
                        placeholder="Ej.: Lisboa" 
                        required
                        value="<%= request.getAttribute("ciudadEdicion") != null ? request.getAttribute("ciudadEdicion") : "" %>">
                </div>
            </div>

            <div class="row mb-4">
                <div class="col-6">
                    <label for="fechaIni" class="form-label">Fecha de Inicio*</label>
                    <input 
                        type="date" 
                        id="fechaIni" 
                        name="fechaIni" 
                        class="form-control" 
                        required
                        value="<%= request.getAttribute("fechaIni") != null ? request.getAttribute("fechaIni") : "" %>">
                </div>
                <div class="col-6">
                    <label for="fechaFin" class="form-label">Fecha de Fin*</label>
                    <input 
                        type="date" 
                        id="fechaFin" 
                        name="fechaFin" 
                        class="form-control" 
                        required
                        value="<%= request.getAttribute("fechaFin") != null ? request.getAttribute("fechaFin") : "" %>">
                </div>
            </div>
            
            <div class="row mb-4">
                <div class="col-12 mb-4">
                    <label for="video" class="form-label">URL de video promocional</label>
                    <input 
                        type="text" 
                        id="video" 
                        name="video" 
                        class="form-control" 
                        placeholder="http://www.example.com"
                        value="<%= request.getAttribute("video") != null ? request.getAttribute("video") : "" %>">
                </div>
            </div>
			
            <!-- Imagen -->
            <div class="row mb-3 align-items-center">
                <div class="col-2">
                    <img 
                        id="preview" 
                        src="${pageContext.request.contextPath}/media/imagenes/placeholderEvento-Edicion.png" 
                        class="rounded-3 img-thumbnail mb-0" 
                        alt="imagen de evento" 
                        width="150" height="150">
                </div>
                <div class="col-10 mx-auto">
                    <input 
                        type="file" 
                        class="form-control" 
                        id="imgEvento" 
                        name="imgEvento"
                        accept="image/*">
                </div>
            </div>

            <div class="row mb-3 justify-content-end d-flex">
                <div class="col-2">
                    <button type="submit" id="Aceptar" class="btn btn-primary w-100">Aceptar</button>
                </div>
                <div class="col-2">
                    <button type="button" id="Cancelar" class="btn btn-secondary w-100" onclick="history.back()">Cancelar</button>
                </div>
            </div>

        </fieldset>
    </form>
</div>

<!-- Script de vista previa -->
<script>
  const inputImg = document.getElementById("imgEvento");
  const preview = document.getElementById("preview");

  inputImg.addEventListener("change", function () {
    const file = this.files[0];
    if (file) {
      const reader = new FileReader();
      reader.onload = function (e) {
        preview.src = e.target.result;
      };
      reader.readAsDataURL(file);
    } else {
      preview.src = "${pageContext.request.contextPath}/media/imagenes/placeholderEvento-Edicion.png";
    }
  });
</script>

</body>
</html>
