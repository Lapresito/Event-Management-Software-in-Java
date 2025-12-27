<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@ page import="clienteServidor.publicar.*" %>

<%! 
  String get(Object obj, String getter, String def) {
    if (obj == null) return def;
    try {
      Object v = obj.getClass().getMethod(getter).invoke(obj);
      if (v != null) {
        String s = v.toString();
        if (s != null && !s.trim().isEmpty()) return s;
      }
    } catch (Exception ignore) {}
    return def;
  }

  java.util.List<?> getList(Object obj, String getter) {
    if (obj == null) return java.util.Collections.emptyList();
    try {
      Object v = obj.getClass().getMethod(getter).invoke(obj);
      if (v instanceof java.util.List) return (java.util.List<?>) v;
    } catch (Exception ignore) {}
    return java.util.Collections.emptyList();
  }
%>

<%
  String baseServer = request.getContextPath();
  DtEvento evento = (DtEvento) request.getAttribute("evento");

  List<DtEdicion> edicionesAceptadas = (List<DtEdicion>) request.getAttribute("edicionesAceptadas");
  List<DtEdicion> edicionesIngresadas = (List<DtEdicion>) request.getAttribute("edicionesIngresadas");
  List<DtEdicion> edicionesRechazadas = (List<DtEdicion>) request.getAttribute("edicionesRechazadas");
  DtUsuario usuario = (DtUsuario) session.getAttribute("usuario_logueado");
  String tipoUsuario = (String) session.getAttribute("tipo_usuario");

  String nombre = get(evento, "getNombre", "Evento");
  String sigla  = get(evento, "getSigla", "—");
  String desc   = get(evento, "getDescripcion", "—");
  String img    = get(evento, "getImg", request.getContextPath() + "/media/imagenes/placeholder.png");
  String fechaAlta = get(evento, "getFechaAlta", "—");

  List<?> cats = getList(evento, "getCategorias");
%>

<!doctype html>
<html lang="es">
<head>
  <meta charset="UTF-8"/>
  <title><%= nombre %></title>
  <meta name="viewport" content="width=device-width, initial-scale=1"/>
  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">

  <style>
    body {
      background-color: #f8f9fa;
    }

    .event-card {
      border-radius: 1rem;
      overflow: hidden;
    }

    .event-image img {
      width: 100%;
      height: auto;
      object-fit: cover;
    }

    .card-body p {
      font-size: 0.95rem;
      margin-bottom: 0.4rem;
    }

    .list-group-item {
      border-radius: 0.5rem !important;
      margin-bottom: 0.5rem;
      font-size: 0.95rem;
      text-align: center;
    }

    @media (max-width: 768px) {
      h3 { font-size: 1.3rem; }
      .btn { font-size: 0.9rem; padding: 0.6rem 1rem; }
      .card-body { padding: 1.2rem; }
    }
  </style>
</head>
<body>
  <jsp:include page="/WEB-INF/templates/navbar.jsp"/>

  <div class="container my-4">
    <h2 class="text-center mb-4">Consulta de Evento</h2>

    <!-- Card principal -->
    <div class="card shadow-sm event-card mx-auto" style="max-width: 500px;">
      <div class="event-image">
        <img src="<%= img %>" alt="Imagen de <%= nombre %>">
      </div>
      <div class="card-body">
        <h3 class="text-center mb-2"><%= nombre %></h3>
        <p class="text-muted text-center mb-3"><%= desc %></p>

        <div class="text-center">
          <p><strong>Sigla:</strong> <%= sigla %></p>
          <p><strong>Fecha de alta:</strong> 
            <%= evento.getFechaAlta().toXMLFormat().split("T")[0] %></p>
          <p><strong>Categorías:</strong>
            <%
              if (cats.isEmpty()) { out.print("—"); }
              else {
                List<String> cs = new ArrayList<>();
                for (Object c : cats) if (c != null) cs.add(c.toString());
                out.print(String.join(", ", cs));
              }
            %>
          </p>
        </div>
      </div>
    </div>

    <!-- Ediciones -->
    <div class="mt-4">
      <h5 class="text-center mb-3">Ediciones</h5>
      <%
        if (usuario != null) {
          if ("organizador".equalsIgnoreCase(tipoUsuario)) {
      %>
        <!-- Aceptadas -->
        <h6 class="text-secondary mt-3 text-center">Aceptadas</h6>
        <div class="list-group mb-3">
          <%
            if (edicionesAceptadas.isEmpty()) {
          %>
              <div class="text-muted text-center">No hay ediciones aceptadas.</div>
          <%
            } else {
              for (DtEdicion ed : edicionesAceptadas) {
          %>
                <button class="list-group-item list-group-item-action"
                  onclick="window.location.href='/webServerMobile/edicion?nombreEdicion=<%= ed.getNombreEdicion() %>'">
                  <strong><%= ed.getNombreEdicion() %></strong>
                </button>
          <%
              }
            }
          %>
        </div>

        <!-- Ingresadas -->
        <h6 class="text-secondary mt-3 text-center">Ingresadas</h6>
        <div class="list-group mb-3">
          <%
            if (edicionesIngresadas.isEmpty()) {
          %>
              <div class="text-muted text-center">No hay ediciones ingresadas.</div>
          <%
            } else {
              for (DtEdicion ed : edicionesIngresadas) {
                if (usuario.getNickname().equals(ed.getOrganizador())) {
          %>
                  <button class="list-group-item list-group-item-action"
                    onclick="window.location.href='/webServerMobile/edicion?nombreEdicion=<%= ed.getNombreEdicion() %>'">
                    <strong><%= ed.getNombreEdicion() %></strong>
                  </button>
          <%
                }
              }
            }
          %>
        </div>

        <!-- Rechazadas -->
        <h6 class="text-secondary mt-3 text-center">Rechazadas</h6>
        <div class="list-group mb-3">
          <%
            if (edicionesRechazadas.isEmpty()) {
          %>
              <div class="text-muted text-center">No hay ediciones rechazadas.</div>
          <%
            } else {
              for (DtEdicion ed : edicionesRechazadas) {
                if (usuario.getNickname().equals(ed.getOrganizador())) {
          %>
                  <button class="list-group-item list-group-item-action"
                    onclick="window.location.href='/webServerMobile/edicion?nombreEdicion=<%= ed.getNombreEdicion() %>'">
                    <strong><%= ed.getNombreEdicion() %></strong>
                  </button>
          <%
                }
              }
            }
          %>
        </div>

      <%
          } else { // Asistente logueado
      %>
        <div class="list-group mb-3">
          <%
            if (edicionesAceptadas.isEmpty()) {
          %>
              <div class="text-muted text-center">Evento sin ediciones disponibles.</div>
          <%
            } else {
              for (DtEdicion ed : edicionesAceptadas) {
          %>
                <button class="list-group-item list-group-item-action"
                  onclick="window.location.href='/webServerMobile/edicion?nombreEdicion=<%= ed.getNombreEdicion() %>'">
                  <strong><%= ed.getNombreEdicion() %></strong>
                </button>
          <%
              }
            }
          %>
        </div>
      <%
          }
        } else { // No logueado
      %>
        <div class="list-group mb-3">
          <%
            if (edicionesAceptadas.isEmpty()) {
          %>
              <div class="text-muted text-center">Evento sin ediciones.</div>
          <%
            } else {
              for (DtEdicion ed : edicionesAceptadas) {
          %>
                <button class="list-group-item list-group-item-action"
                  onclick="window.location.href='/webServerMobile/edicion?nombreEdicion=<%= ed.getNombreEdicion() %>'">
                  <strong><%= ed.getNombreEdicion() %></strong>
                </button>
          <%
              }
            }
          %>
        </div>
      <%
        }
      %>

      <div class="text-center mt-4">
        <button class="btn btn-secondary w-75" onclick="window.location.href='/webServerMobile/eventos'">
          Volver
        </button>
      </div>
    </div>
  </div>

  <!-- Bootstrap JS -->
  <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
  <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
