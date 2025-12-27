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
    @media (max-width: 768px) {
      .event-image img {
        border-radius: .25rem .25rem 0 0 !important;
      }
    }
  </style>
</head>
<body class="bg-light">
  <jsp:include page="/WEB-INF/templates/navbar.jsp"/>

  <div class="container-fluid my-4">
    <div class="row">
      <!-- Sidebar -->
      <div class="col-md-3 col-lg-2">
        <jsp:include page="/WEB-INF/templates/categorias.jsp"/>
      </div>

      <!-- Contenido principal -->
      <div class="col-md-9 col-lg-10">
        <h2 class="text-center mb-4">Consulta de Evento</h2>



<%
  String msgOk = (String) session.getAttribute("mensaje_exito");
  String msgErr = (String) session.getAttribute("mensaje_error");
  session.removeAttribute("mensaje_exito");
  session.removeAttribute("mensaje_error");
%>

<% if (msgOk != null) { %>
  <div class="alert alert-success text-center"><%= msgOk %></div>
<% } %>

<% if (msgErr != null) { %>
  <div class="alert alert-danger text-center"><%= msgErr %></div>
<% } %>


<!-- Card principal -->
<div class="card shadow mx-auto" style="max-width: 900px;">
  <div class="d-flex flex-wrap flex-md-nowrap">
    
    <!-- Imagen -->
    <div class="event-image flex-shrink-0" style="width: 40%;">
      <img src="<%= img %>" alt="Imagen de <%= nombre %>" 
           class="img-fluid h-100 w-100" 
           style="object-fit: cover; border-radius: .25rem 0 0 .25rem;">
    </div>

    <!-- Información -->
    <div class="flex-grow-1 p-4">
      <h3 class="mb-3"><%= nombre %></h3>
      <p class="text-muted"><%= desc %></p>

      <div class="row">
        <div class="col-md-6">
          <p><strong>Sigla:</strong> <%= sigla %></p>
        </div>
        <div class="col-md-6">
          <p><strong>Fecha de alta:</strong> <%= evento.getFechaAlta().toXMLFormat().split("T")[0] %></p>
        </div>
      </div>

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

      <% if (usuario != null && "organizador".equalsIgnoreCase(tipoUsuario)) { %>
        <div class="mt-3 text-center">
          <% if (!evento.isFinalizado()) { %>
           <form method="post" action="<%= baseServer %>/eventos" class="d-inline-block">
			  <input type="hidden" name="accion" value="finalizarEvento">
			  <input type="hidden" name="nombreEvento" value="<%= nombre %>">
			  <button 
			    type="submit" 
			    class="btn btn-danger px-4"
			    data-toggle="tooltip"
			    data-placement="top"
			    title="Al finalizar el evento no se podrán crear más ediciones del mismo">
			    Finalizar Evento
			  </button>
			</form>

          <% } else { %>
            <div class="alert alert-danger text-center mt-3 mb-0">
              <strong>Evento Finalizado</strong> – No se podrán crear más ediciones del mismo.
            </div>
          <% } %>
        </div>
      <% } %>

    </div>
  </div>
</div>


        <!-- Ediciones -->
        <div class="mt-4">
          <hr>
          <h5 class="text-center mb-3">Ediciones</h5>

          <%
            if (usuario != null) {
              if ("organizador".equalsIgnoreCase(tipoUsuario)) {
          %>
                <!-- Ediciones Aceptadas -->
                <h6 class="text-secondary mt-4">Aceptadas</h6>
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
                          onclick="window.location.href='/webServer/edicion?nombreEdicion=<%= ed.getNombreEdicion() %>'">
                          <strong><%= ed.getNombreEdicion() %></strong>
                        </button>
                  <%
                      }
                    }
                  %>
                </div>

                <!-- Ediciones Ingresadas -->
                <h6 class="text-secondary mt-4">Ingresadas</h6>
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
                              onclick="window.location.href='/webServer/edicion?nombreEdicion=<%= ed.getNombreEdicion() %>'">
                              <strong><%= ed.getNombreEdicion() %></strong>
                            </button>
                  <%
                        }
                      }
                    }
                  %>
                </div>

                <!-- Ediciones Rechazadas -->
                <h6 class="text-secondary mt-4">Rechazadas</h6>
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
                              onclick="window.location.href='/webServer/edicion?nombreEdicion=<%= ed.getNombreEdicion() %>'">
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
                          onclick="window.location.href='/webServer/edicion?nombreEdicion=<%= ed.getNombreEdicion() %>'">
                          <strong><%= ed.getNombreEdicion() %></strong>
                        </button>
                  <%
                      }
                    }
                  %>
                </div>
          <%
              }
            } else { // Usuario no logueado
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
                        onclick="window.location.href='/webServer/edicion?nombreEdicion=<%= ed.getNombreEdicion() %>'">
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
            <button class="btn btn-secondary" onclick="if (document.referrer) { window.location = document.referrer; } else { window.location.href = '<%= request.getContextPath() %>/inicio'; }">
              Volver
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</body>
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<script>
  $(function () {
    $('[data-toggle="tooltip"]').tooltip()
  })
</script>
</html>
