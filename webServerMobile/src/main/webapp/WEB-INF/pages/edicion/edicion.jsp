<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="clienteServidor.publicar.*" %>

<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Consulta de Edición</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/css/bootstrap.min.css" rel="stylesheet">
  <style>
    body {
      background-color: #f8f9fa;
      font-size: 0.95rem;
    }

    .card {
      border: none;
      border-radius: 10px;
      overflow: hidden;
    }

    .card img {
      width: 100%;
      height: auto;
      object-fit: cover;
    }

    .info p {
      margin-bottom: .4rem;
    }

    .list-group-item {
      font-size: 0.9rem;
      padding: 0.75rem 1rem;
    }

    .section-title {
      font-size: 1.1rem;
      font-weight: bold;
      margin-top: 1.5rem;
      margin-bottom: .5rem;
    }

    .linkRegistros,
    .linkPatrocinios,
    .linkTiposRegistros {
      display: block;
      text-align: right;
      color: #777;
      font-size: 0.9rem;
      text-decoration: none;
      margin-top: .3rem;
    }

    .linkRegistros:hover,
    .linkPatrocinios:hover,
    .linkTiposRegistros:hover {
      color: #333;
      text-decoration: underline;
    }

    .btn {
      font-size: 0.9rem;
      width: 100%;
    }
  </style>
</head>

<body>

  <jsp:include page="/WEB-INF/templates/navbar.jsp"/>

  <div class="container my-3 px-3">

    <%
      DtUsuario usuario = (DtUsuario) session.getAttribute("usuario_logueado");
 	  String tipoUsuario = (String) session.getAttribute("tipo_usuario");
      DtEdicion edicion = (DtEdicion) request.getAttribute("edicion");
      List<String> tiposRegistros = (List<String>) request.getAttribute("tiposRegistros");
      List<DtRegistro> registrosAsistidos = (List<DtRegistro>) request.getAttribute("registrosAsistidos");
      List<String> patrocinios = (List<String>) request.getAttribute("listaPatrocinios");
      int maxPatrocinos = 1;
      int maxRegistros = 1;
      int maxTiposRegistros = 1;
    %>

    <!-- Tarjeta principal -->
    <div class="card shadow-sm mb-3">
      <img 
        src="<%= edicion.getImagen() %>"
        alt="<%= edicion.getNombreEdicion() %>">

      <div class="card-body">
        <h4 class="card-title mb-3 text-center"><%= edicion.getNombreEdicion() %></h4>

        <div class="info">
          <p><strong>Evento:</strong> <%= edicion.getNombreEvento() %></p>
          <p><strong>Organizador:</strong> <%= edicion.getOrganizador() %></p>
          <p><strong>Ciudad:</strong> <%= edicion.getCiudad() %></p>
          <p><strong>País:</strong> <%= edicion.getPais() %></p>
          <p><strong>Fecha:</strong> 
          <br>Inicio: <%= edicion.getFechaIni().getDay()%>/<%= edicion.getFechaIni().getMonth()%>/<%= edicion.getFechaIni().getYear()%>
          <br>Fin: <%= edicion.getFechaFin().getDay()%>/<%= edicion.getFechaFin().getMonth()%>/<%= edicion.getFechaFin().getYear()%> </p>

          <% if (usuario != null && usuario.getNickname().equals(edicion.getOrganizador())) { %>
            <p><strong>Estado:</strong> <%= edicion.getEstado() %></p>
          <% } %>
        </div>
      </div>
    </div>

    <!-- Patrocinios -->
    <div>
      <div class="section-title">Patrocinios</div>
      <div class="list-group">
        <%
          if (patrocinios != null && !patrocinios.isEmpty()) {
              for (String p : patrocinios.subList(0, Math.min(maxPatrocinos, patrocinios.size()))) {
        %>
          <button class="list-group-item list-group-item-action"
            onclick="window.location.href='/webServerMobile/patrocinios?evento=<%= edicion.getNombreEvento() %>&edicion=<%= edicion.getNombreEdicion() %>&institucion=<%= p %>'">
            Patrocinio <%= p %>
          </button>
        <% } %>
          <a class="linkPatrocinios" href="/webServerMobile/patrocinios?edicion=<%=edicion.getNombreEdicion()%>">
            Ver todos (<%= patrocinios.size() %>)
          </a>
        <% } else { %>
          <div class="text-muted">No hay patrocinios</div>
        <% } %>
      </div>
    </div>

    <!-- Tipos de Registro -->
    <div>
      <div class="section-title">Tipos de Registro</div>
      <div class="list-group">
        <%
          if (tiposRegistros != null && !tiposRegistros.isEmpty()) {
              for (String tr : tiposRegistros.subList(0, Math.min(maxTiposRegistros, tiposRegistros.size()))) {
        %>
          <button class="list-group-item list-group-item-action"
            onclick="window.location.href='/webServerMobile/tipoRegistro?evento=<%= edicion.getNombreEvento() %>&edicion=<%= edicion.getNombreEdicion() %>&tipoRegistro=<%= tr %>'">
            <%= tr %>
          </button>
        <% } %>
          <a class="linkTiposRegistros" href="/webServerMobile/tipoRegistro?edicion=<%=edicion.getNombreEdicion()%>">
            Ver todos (<%= tiposRegistros.size() %>)
          </a>
        <% } else { %>
          <div class="text-muted">No hay tipos de registro</div>
        <% } %>
      </div>
    </div>

    <!-- Registros (solo organizador) -->
    <%
      if (usuario != null) {

    %>
    <div>
      <div class="section-title">Registros Asistidos</div>
      <div class="list-group">
        <%
          if (registrosAsistidos != null && !registrosAsistidos.isEmpty()) {
            for (DtRegistro r : registrosAsistidos.subList(0, Math.min(maxRegistros, registrosAsistidos.size()))) {
        %>
          <button class="list-group-item list-group-item-action"
            onclick="window.location.href='/webServerMobile/consultaRegistro?nickname=<%= r.getNickAsistente() %>&nombreEdicion=<%= edicion.getNombreEdicion() %>'">
            Registro de <%= r.getNickAsistente() %>
          </button>
        <% } %>
          <a class="linkRegistros" href="/webServerMobile/consultaRegistroAsistido?nombreEdicion=<%=edicion.getNombreEdicion()%>">
            Ver todos (<%= registrosAsistidos.size() %>)
          </a>
        <% } else { %>
          <div class="text-muted">No hay registros Asistidos</div>
        <% } %>
      </div>
    </div>
    <%
      } else if ("asistente".equalsIgnoreCase(tipoUsuario)) {
        String nick = ((DtAsistente)usuario).getNickname();
        boolean yaRegistrado = false;
        for (DtRegistro reg : registrosAsistidos) {
          if (reg.getNickAsistente().equals(nick)) { yaRegistrado = true; break; }
        }
    %>
    <div>
      <div class="section-title">Registro</div>
      <% if (yaRegistrado) { %>
        <a href="/webServerMobile/consultaRegistro?nickname=<%= nick %>&nombreEdicion=<%= edicion.getNombreEdicion() %>" 
          class="btn btn-success mt-2">
          Ya estás registrado
        </a>
      <% } else { %>
        <p class="text-muted">No estás registrado en esta edición</p>
      <% } %>
    </div>
    <% } %>
  </div>
</body>
<!-- Bootstrap JS -->
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</html>

