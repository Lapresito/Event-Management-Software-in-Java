<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="jakarta.servlet.*" %>
<%@ page import="clienteServidor.publicar.*" %>

<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Mi Perfil</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/css/bootstrap.min.css" rel="stylesheet">

  <style>
    body {
      background-color: #f8f9fa;
      font-family: "Segoe UI", Arial, sans-serif;
    }

    .perfil-container {
      padding: 20px 15px 60px;
      max-width: 500px;
      margin: 0 auto;
    }

    .perfil-card {
      border-radius: 15px;
      overflow: hidden;
      box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
    }

    .perfil-imagen {
      text-align: center;
      margin-bottom: 20px;
    }

    .perfil-imagen img {
      width: 160px;
      height: 160px;
      object-fit: cover;
      border-radius: 50%;
      border: 3px solid #007bff;
    }

    .perfil-texto h4 {
      font-weight: 600;
      margin-bottom: 5px;
    }

    .perfil-texto p {
      margin-bottom: 5px;
      font-size: 15px;
    }

    .perfil-info {
      text-align: center;
      padding: 15px 10px;
    }

    .acciones-extra {
      margin-top: 25px;
      text-align: center;
    }

    .acciones-extra .btn {
      width: 100%;
      max-width: 300px;
      border-radius: 25px;
      font-weight: 500;
    }

    .list-group-item {
      border-radius: 10px;
      margin-bottom: 8px;
    }

    .alert {
      border-radius: 10px;
    }

    .btn-danger {
      border-radius: 25px;
    }

    .footer-space {
      height: 50px;
    }
  </style>
</head>

<body>
  <jsp:include page="/WEB-INF/templates/navbar.jsp"/>

  <div class="perfil-container">
    <%
      jakarta.servlet.http.HttpSession sesion = (jakarta.servlet.http.HttpSession) request.getSession(false);
	  DtUsuario userReq =  (DtUsuario) request.getAttribute("usuario");
	  DtUsuario usuario = null;
      String tipo = null;
      List<String> listaAcciones = (List<String>) request.getAttribute("listaAcciones");

      if (sesion != null) {
        usuario = (DtUsuario) sesion.getAttribute("usuario_logueado");
        tipo = (String) sesion.getAttribute("tipo_usuario");
      }
    %>

    <% if (usuario != null) { %>
      <div class="card perfil-card">
        <div class="card-body">
          <div class="perfil-imagen">
            <img src="<%= userReq.getImg() %>" alt="Imagen de perfil">
          </div>

          <div class="perfil-info perfil-texto">
            <h4>
              <% if ("asistente".equalsIgnoreCase(tipo)) {
                   DtAsistente a = (DtAsistente) usuario;
                   out.print(a.getNombre() + " " + a.getApellido());
                 } %>
            </h4>
            <p class="text-muted">@<%= usuario.getNickname() %></p>
            <p><strong>Email:</strong> <%= usuario.getEmail() %></p>
            <p><strong>Tipo de usuario:</strong> <%= tipo %></p>

            <% if ("asistente".equalsIgnoreCase(tipo)) { %>
              <p><strong>Fecha de nacimiento:</strong>
                <%= ((DtAsistente) usuario).getNacimiento().getDay() %>/
                <%= ((DtAsistente) usuario).getNacimiento().getMonth() %>/
                <%= ((DtAsistente) usuario).getNacimiento().getYear() %>
              </p>
              <p><strong>Institución:</strong>
                <%= ((DtAsistente) usuario).getInstitucion() != null ?
                    ((DtAsistente) usuario).getInstitucion() :
                    "Aún no pertenece a ninguna institución" %>
              </p>
            <% } %>
          </div>

          <form action="<%= request.getContextPath() %>/cerrar-sesion" method="post" class="mt-4 text-center">
            <button type="submit" class="btn btn-danger px-4 py-2">Cerrar sesión</button>
          </form>
        </div>
      </div>

      <% if ("asistente".equalsIgnoreCase(tipo)) { %>
        <div class="acciones-extra">
          <button class="btn btn-info mt-4" type="button" data-toggle="collapse" data-target="#verRegistros">
            Ver mis registros
          </button>
        </div>

        <div class="collapse mt-3" id="verRegistros">
          <% if (listaAcciones == null || listaAcciones.isEmpty()) { %>
            <div class="alert alert-secondary text-center mt-3">Aún no tiene registros.</div>
          <% } else {
               String nickname = usuario.getNickname();
          %>
            <div class="list-group mt-3">
              <% for (String r : listaAcciones) {
                   String[] partes = r.split("—");
                   String nombreEdicion = partes[0].trim();
              %>
                <a href="<%= request.getContextPath() %>/consultaRegistro?nickname=<%= nickname %>&nombreEdicion=<%= java.net.URLEncoder.encode(nombreEdicion, "UTF-8") %>"
                   class="list-group-item list-group-item-action text-center">
                   <%= r %>
                </a>
              <% } %>
            </div>
          <% } %>
        </div>
      <% } %>

    <% } else { %>
      <div class="alert alert-warning text-center">No hay usuario logueado.</div>
    <% } %>

    <div class="footer-space"></div>
  </div>

  <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
