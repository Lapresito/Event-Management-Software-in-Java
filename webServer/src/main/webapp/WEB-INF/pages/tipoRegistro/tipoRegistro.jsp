<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="clienteServidor.publicar.*" %>
<%
    // Leer parámetros de la URL
    String nombreEvento = request.getParameter("evento");
    String nombreEdicion = request.getParameter("edicion");

    // Leer objeto tipoRegistro desde atributo
    DtTipoRegistro tipoRegistro = (DtTipoRegistro) request.getAttribute("tipoRegistro");
%>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Consulta de Tipo de Registro</title>
  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"/>
</head>
<body class="bg-light">

<!-- navbar -->
  <jsp:include page="/WEB-INF/templates/navbar.jsp"/>
    <div class="sidebar">
        <jsp:include page="/WEB-INF/templates/categorias.jsp"/>
    </div>
  <div class="container mt-4 d-flex justify-content-center">
    <div id="tipoRegistroContainer" class="card shadow p-4" style="max-width: 900px;">
      <h3 class="mb-3">Detalles del Tipo de Registro</h3>

      <div id="tipoRegistroInfo" class="text-muted">
        <%
            if (nombreEvento == null || nombreEdicion == null) {
        %>
            <div class="text-danger">Faltan parámetros en la URL.</div>
        <%
            } else if (tipoRegistro == null) {
        %>
            <div class="text-warning">Tipo de registro no encontrado.</div>
        <%
            } else {
        %>
            <p><strong>Evento:</strong> <%= nombreEvento %></p>
            <p><strong>Edición:</strong> <%= nombreEdicion %></p>
            <p><strong>Nombre:</strong> <%= tipoRegistro.getNombreTipoRegistro() %></p>
            <p><strong>Descripción:</strong> <%= tipoRegistro.getDescripcion() %></p>
            <p><strong>Costo:</strong> $<%= tipoRegistro.getPrecio() %></p>
            <p><strong>Cupo:</strong> <%= tipoRegistro.getCupo() %></p>
        <%
            }
        %>
      </div>

      <button class="btn btn-primary mt-3" onclick="history.back()">← Volver</button>
    </div>
  </div>

</body>
</html>