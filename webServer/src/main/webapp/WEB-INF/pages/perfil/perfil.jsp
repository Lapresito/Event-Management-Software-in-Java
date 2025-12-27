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
    /* Layout general */
    .pagina-perfil {
      min-height: calc(100vh - 100px);
      display: flex;
      padding-top: 20px;
      padding-bottom: 40px;
    }

    /* Centrar el contenido principal dentro de la columna */
    .contenido-principal {
      display: flex;
      flex-direction: column;
      align-items: center;
    }

    .perfil-card {
      width: 100%;
      max-width: 680px;
    }

    /* Dentro de la card: imagen a la izquierda, texto a la derecha */
    .perfil-info {
      display: flex;
      align-items: center;
      justify-content: space-between;
      text-align: left;
      gap: 20px;
    }
    .perfil-imagen { flex-shrink: 0; text-align: left; }
    .perfil-imagen img {
      width: 150px;
      height: 150px;
      object-fit: cover;
      border-radius: 50%;
    }
    .perfil-texto { flex: 1; }

    /* Botones "Ver mis ..." centrados y con separación */
    .acciones-extra {
      width: 100%;
      max-width: 680px;
      margin-top: 22px;
      display: flex;
      justify-content: center;
    }
    .acciones-extra .btn {
      width: 80%;
      max-width: 480px;
    }
    
    .div-follow {
            cursor: pointer;
            padding: 4px;
    }
    .div-follow:hover {
            background-color: #F5F5F5;
            border-radius: 10%;
    }

    /* Responsive */
    @media (max-width: 768px) {
      .perfil-info {
        flex-direction: column;
        align-items: center;
        text-align: center;
      }
      .perfil-imagen img { margin-bottom: 10px; }
      .acciones-extra .btn { width: 95%; }
    }
  </style>
</head>

<body class="bg-light">
  <jsp:include page="/WEB-INF/templates/navbar.jsp"/>

    <%
    jakarta.servlet.http.HttpSession sesion = (jakarta.servlet.http.HttpSession) request.getSession(false);
    DtUsuario userReq =  (DtUsuario) request.getAttribute("usuario"); // si lo necesitás
    DtUsuario usuario = null;
    String tipo = null;
    List<String> listaAcciones = (List<String>) request.getAttribute("listaAcciones");

    // declarar fuera del if para que estén en scope después
    int cantidadSeguidores = 0;
    int cantidadSeguidos = 0;

    String contextPath = request.getContextPath();

    // preparar web service para obtener contadores actualizados (opcional pero recomendado)
    IUsuariosControllerWebService sistemaUsuarios = (IUsuariosControllerWebService) request.getAttribute("sistemaUsers");


    if (sesion != null) {
      usuario = (DtUsuario) sesion.getAttribute("usuario_logueado");
      tipo = (String) sesion.getAttribute("tipo_usuario");

      if (usuario != null) {
        try {
          // usar el servicio para obtener la lista de seguidores/seguidos y contar (más fiable)
          DtUsuarioArray segs = sistemaUsuarios.getSeguidoresDeUsuario(usuario.getNickname());
          if (segs != null && segs.getItem() != null) cantidadSeguidores = segs.getItem().size();

          DtUsuarioArray seguidosArr = sistemaUsuarios.getSeguidosDeUsuario(usuario.getNickname());
          if (seguidosArr != null && seguidosArr.getItem() != null) cantidadSeguidos = seguidosArr.getItem().size();
        } catch (Exception e) {
          System.out.println("[Perfil JSP] Error obteniendo contadores: " + e.getMessage());
          // fallback: intentar con el objeto en sesión (si existen los getters)
          try {
            if (usuario.getSeguidores() != null) cantidadSeguidores = usuario.getSeguidores().size();
            if (usuario.getSeguidos() != null) cantidadSeguidos = usuario.getSeguidos().size();
          } catch (Exception ex) {
            // ignora
          }
        }
      }
    }
  %>

  <div class="container-fluid pagina-perfil">
    <div class="row w-100">
      <!-- Sidebar -->
      <div class="col-md-3">
        <jsp:include page="/WEB-INF/templates/categorias.jsp"/>
      </div>

      <!-- Contenido principal centrado -->
      <div class="col-md-9 d-flex justify-content-center">
        <div class="contenido-principal">
          <% if (usuario != null) { %>
            <div class="card perfil-card shadow mb-0">
              <div class="card-body">
                <div class="perfil-info">
                  
                  <!-- Imagen a la izquierda -->
                  <div class="perfil-imagen">
                    <img src="<%= userReq.getImg() %>" alt="Imagen de perfil">
                  </div>

                  <!-- Texto a la derecha -->
                  <div class="perfil-texto">
                    <h4 class="mb-1">
                      <% if ("asistente".equalsIgnoreCase(tipo)) {
                           DtAsistente a = (DtAsistente) usuario;
                           out.print(a.getNombre() + " " + a.getApellido());
                         } else if ("organizador".equalsIgnoreCase(tipo)) {
                           DtOrganizador o = (DtOrganizador) usuario;
                           out.print(o.getNombre());
                         } %>
                    </h4>
                    <p class="text-muted mb-1">@<%= usuario.getNickname() %></p>
                    <p class="mb-1"><strong>Email:</strong> <%= usuario.getEmail() %></p>
                    <p class="mb-1"><strong>Tipo de usuario:</strong> <%= tipo %></p>

                    <div class="mt-3">
                      <% if ("asistente".equalsIgnoreCase(tipo)) { %>
                        <p class="mb-1"><strong>Fecha de nacimiento:</strong> <%= ((DtAsistente) usuario).getNacimiento().toXMLFormat().split("T")[0] %></p>
                        <p class="mb-0"><strong>Institución:</strong>
                          <%= ((DtAsistente) usuario).getInstitucion() != null ?
                              ((DtAsistente) usuario).getInstitucion() :
                              "Aún no pertenece a ninguna institución" %>
                        </p>
                      <% } else if ("organizador".equalsIgnoreCase(tipo)) { %>
                        <p class="mb-1"><strong>Descripción:</strong>
                          <%= ((DtOrganizador) usuario).getDescripcion() != null ?
                              ((DtOrganizador) usuario).getDescripcion() : "Sin descripción" %>
                        </p>
                        <p class="mb-0"><strong>Sitio web:</strong>
                          <%= ((DtOrganizador) usuario).getSitioWeb() != null ?
                              ((DtOrganizador) usuario).getSitioWeb() : "No aplica" %>
                        </p>
                      <% } %>
                    </div>
                  </div>
                </div>

                <div class="mt-3">
                  <button class="btn btn-primary btn-block" onclick="editarPerfil()">Editar Perfil</button>
                  <form action="<%= request.getContextPath() %>/cerrar-sesion" method="post">
                    <button type="submit" class="btn btn-danger btn-block mt-2">Cerrar sesión</button>
                  </form>
                </div>
              </div>
            </div>
			
            <div class="d-flex gap-5 align-items-center justify-content-center mt-2">
              <div class="div-follow" onclick="window.location.href='<%= contextPath %>/usuarios?filtro=seguidores&nickname=<%= usuario.getNickname() %>'">
                  <h5>Seguidores</h5>
                  <h5><%= cantidadSeguidores %></h5>
              </div> 
              <div class="div-follow" onclick="window.location.href='<%= contextPath %>/usuarios?filtro=seguidos&nickname=<%= usuario.getNickname() %>'">
                  <h5>Seguidos</h5>
                  <h5><%= cantidadSeguidos %></h5>
              </div>
            </div>
            

            <!-- Acciones fuera de la card -->
            <div class="acciones-extra">
              <% if ("organizador".equalsIgnoreCase(tipo)) { %>
                <button class="btn btn-info" type="button" data-toggle="collapse" data-target="#verEdiciones">Ver mis ediciones</button>
              <% } else if ("asistente".equalsIgnoreCase(tipo)) { %>
                <button class="btn btn-info" type="button" data-toggle="collapse" data-target="#verRegistros">Ver mis registros</button>
              <% } %>
            </div>

            <!-- Collapses -->
            <div class="d-flex justify-content-center mt-3">
              <div style="width:100%; max-width:680px;">
                <% if ("organizador".equalsIgnoreCase(tipo)) { %>
                  <div id="verEdiciones" class="collapse">
                    <% if (listaAcciones == null || listaAcciones.isEmpty()) { %>
                      <div class="alert alert-secondary text-center">No posee ediciones registradas.</div>
                    <% } else { %>
                      <div class="list-group">
                        <% for (String e : listaAcciones) { %>
                          <a href="<%= request.getContextPath() %>/edicion?nombreEdicion=<%= java.net.URLEncoder.encode(e, "UTF-8") %>"
                             class="list-group-item list-group-item-action"><%= e %></a>
                        <% } %>
                      </div>
                    <% } %>
                  </div>
                <% } else if ("asistente".equalsIgnoreCase(tipo)) { %>
                  <div id="verRegistros" class="collapse">
                    <% if (listaAcciones == null || listaAcciones.isEmpty()) { %>
                      <div class="alert alert-secondary text-center">Aún no tiene registros.</div>
                    <% } else {
                         String nickname = usuario.getNickname();
                    %>
                      <div class="list-group">
                        <% for (String r : listaAcciones) {
                             String[] partes = r.split("—");
                             String nombreEdicion = partes[0].trim();
                        %>
                          <a href="<%= request.getContextPath() %>/consultaRegistro?nickname=<%= nickname %>&nombreEdicion=<%= java.net.URLEncoder.encode(nombreEdicion, "UTF-8") %>"
                             class="list-group-item list-group-item-action"><%= r %></a>
                        <% } %>
                      </div>
                    <% } %>
                  </div>
                <% } %>
              </div>
            </div>

          <% } else { %>
            <div class="alert alert-warning text-center w-100">No hay usuario logueado.</div>
          <% } %>
        </div>
      </div>
    </div>
  </div>

  <!-- scripts bootstrap -->
  <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/js/bootstrap.bundle.min.js"></script>

  <script>
    function editarPerfil() {
      window.location.href = "<%= request.getContextPath() %>/editar-perfil";
    }
  </script>
</body>
</html>
