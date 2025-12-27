<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String baseServer = "/webServer";

    // Usuario logueado
    Object usuarioLogueado = session.getAttribute("usuario_logueado");
    boolean logueado = (usuarioLogueado != null);

    // Obtener query actual
    String queryParam = request.getParameter("query");
    if (queryParam == null) queryParam = "";

    // Obtener orden actual
    String ordenActual = request.getParameter("orden");
    if (ordenActual == null) ordenActual = "fechaDesc";

    // Saber si estoy en /busqueda
    String uri = request.getRequestURI();
    boolean enBusqueda = uri.contains("/busqueda");
%>

<style>
#buscador {
    width: 250px;
}
</style>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
  <div class="container">

    <a class="navbar-brand" href="<%= baseServer %>/home">eventos.uy</a>

    <button class="navbar-toggler" type="button" data-toggle="collapse"
            data-target="#navbarContent" aria-controls="navbarContent"
            aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarContent">

      <div class="d-flex mx-auto">

        <ul class="navbar-nav">
          <li class="nav-item">
            <a class="nav-link" href="<%= baseServer %>/eventos">Eventos</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="<%= baseServer %>/usuarios">Usuarios</a>
          </li>
        </ul>

        <form class="form-inline ml-3 my-2 my-lg-0"
              action="<%= baseServer %>/busqueda" method="get">

          <div class="input-group" style="width: 300px;">

            <input class="form-control"
                   type="search"
                   name="query"
                   value="<%= queryParam %>"
                   placeholder="Buscar eventos o ediciones..."
                   aria-label="Buscar">

            <input type="hidden" id="ordenInput" name="orden" value="<%= ordenActual %>">

            <% if (enBusqueda) { %>
            <div class="input-group-append">
              <button class="btn btn-outline-light dropdown-toggle"
                      type="button"
                      id="ordenDropdown"
                      data-toggle="dropdown"
                      aria-haspopup="true"
                      aria-expanded="false">
              </button>

              <div class="dropdown-menu dropdown-menu-right" aria-labelledby="ordenDropdown">

                <button class="dropdown-item <%= ordenActual.equals("fechaDesc") ? "active" : "" %>"
                        type="button"
                        onclick="document.getElementById('ordenInput').value='fechaDesc'; this.closest('form').submit();">
                  Por defecto (Fecha DESC)
                </button>

                <button class="dropdown-item <%= ordenActual.equals("nombreAsc") ? "active" : "" %>"
                        type="button"
                        onclick="document.getElementById('ordenInput').value='nombreAsc'; this.closest('form').submit();">
                  Alfabético ASC 
                </button>

                <button class="dropdown-item <%= ordenActual.equals("nombreDesc") ? "active" : "" %>"
                        type="button"
                        onclick="document.getElementById('ordenInput').value='nombreDesc'; this.closest('form').submit();">
                  Alfabético DESC
                </button>

              </div>
            </div>
            <% } %>

          </div>

          <button class="btn btn-outline-light ml-2" type="submit">Buscar</button>
        </form>

      </div>

      <ul class="navbar-nav ml-auto">
        <% if (logueado) { %>
          <li class="nav-item">
            <a class="nav-link" href="<%= baseServer %>/perfil">Perfil</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="<%= baseServer %>/cerrar-sesion">Cerrar sesión</a>
          </li>
        <% } else { %>
          <li class="nav-item">
            <a class="nav-link" href="<%= baseServer %>/iniciar-sesion">Iniciar sesión</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="<%= baseServer %>/registrar">Registrarse</a>
          </li>
        <% } %>
      </ul>

    </div>
  </div>
</nav>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
