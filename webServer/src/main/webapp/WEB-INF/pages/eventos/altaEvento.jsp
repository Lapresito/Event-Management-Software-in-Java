<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%
  // Contexto dinámico
  String baseServer = request.getContextPath();

  List<String> categorias = (List<String>) request.getAttribute("categorias");
  if (categorias == null) categorias = java.util.Collections.emptyList();

  String err = (String) request.getAttribute("error");
  String nombre = request.getParameter("nombre");
  String sigla = request.getParameter("sigla");
  String descripcion = request.getParameter("descripcion");
  String img = request.getParameter("img");
  String[] seleccionadas = request.getParameterValues("categorias");
  Set<String> sel = new HashSet<>();
  if (seleccionadas != null) sel.addAll(Arrays.asList(seleccionadas));
%>
<!doctype html>
<html>
<head>
  <meta charset="UTF-8"/>
  <title>Alta de Evento</title>
  <!-- Solo CSS de Bootstrap (sin JS) -->
  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
  <jsp:include page="/WEB-INF/templates/navbar.jsp"/>

  <div class="container mt-3">
    <h1 class="text-center mb-4">Alta de Evento</h1>

    <% if (err != null) { %>
      <div class="alert alert-danger"><%= err %></div>
    <% } %>

    <form id="formulario" method="post" action="<%= request.getContextPath() %>/altaEvento" enctype="multipart/form-data" >
      <div class="form-group">
        <label>Nombre*</label>
        <input name="nombre" class="form-control" value="<%= nombre == null ? "" : nombre %>" required/>
      </div>

      <div class="form-group">
        <label>Sigla*</label>
        <input name="sigla" class="form-control" value="<%= sigla == null ? "" : sigla %>" required/>
      </div>

      <div class="form-group">
        <label>Descripción*</label>
        <textarea name="descripcion" class="form-control" rows="3" required><%= descripcion == null ? "" : descripcion %></textarea>
      </div>

      <div class="form-group">
        <label>Categorías*</label>
        <div class="row">
          <% for (String c : categorias) { %>
            <div class="col-md-4">
              <div class="form-check">
                <input class="form-check-input" type="checkbox" name="categorias" value="<%= c %>" <%= sel.contains(c) ? "checked" : "" %> id="cat_<%= c %>">
                <label class="form-check-label" for="cat_<%= c %>"><%= c %></label>
              </div>
            </div>
          <% } %>
        </div>
      </div>

 			<!-- Imagen -->
            <div class="row mb-3 align-items-center">
                <div class="col-2">
                    <!-- Imagen de evento (placeholder inicial) -->
                    <img 
                        id="preview" 
                        src="${pageContext.request.contextPath}/media/imagenes/placeholderEvento-Edicion.png" 
                        class="rounded-3 img-thumbnail mb-0" 
                        alt="imagen de evento" 
                        width="150" height="150">
                </div>
                <div class="col-10 mx-auto">
                    <!-- Input de carga -->
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
    </form>
  </div>
</body>


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
      preview.src = "${pageContext.request.contextPath}/media/imagenes/placeholder.png";
    }
  });
</script>
</html>

