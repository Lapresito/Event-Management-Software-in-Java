<%@page contentType="text/html" pageEncoding="UTF-8"%> 
<%@ page import="java.util.List" %>
<%@ page import="clienteServidor.publicar.*" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Consulta de Usuario</title>
    <link
        rel="stylesheet"
        href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
    />
    <style>
        .usuario-detalle .card {
            max-width: 900px;
            width: 100%;
            border-radius: 15px;
            overflow: hidden;
        }

        .imgUser {
            width: 300px;
            height: 100%;
            object-fit: cover;
            border-right: 1px solid #dee2e6;
        }

        .card-content {
            flex: 1;
            padding: 30px;
        }

        .card-content h4 {
            margin-bottom: 5px;
        }

        .list-group-item-action {
            border: none;
            background-color: #f8f9fa;
            margin-bottom: 6px;
            text-align: left;
        }

        .list-group-item-action:hover {
            background-color: #e9ecef;
        }

        .volver-container {
            display: flex;
            justify-content: center;
            margin-top: 25px;
            margin-bottom: 60px;
        }

        .div-follow {
            cursor: pointer;
            padding: 4px;
        }
        .div-follow:hover {
            background-color: #F5F5F5;
            border-radius: 10%;
        }
    </style>
</head>
<body>
<%
DtUsuario usuarioLogueado = (DtUsuario) session.getAttribute("usuario_logueado");
boolean logueado = (usuarioLogueado != null);

IUsuariosControllerWebService sistemaUsuarios = (IUsuariosControllerWebService) request.getAttribute("sistemaUsers");


if (logueado) {
    try {
        DtUsuario usuarioActualizado = null;

        if (usuarioLogueado instanceof DtAsistente) {
            usuarioActualizado = sistemaUsuarios.getAsistente(usuarioLogueado.getNickname());
        } else if (usuarioLogueado instanceof DtOrganizador) {
            usuarioActualizado = sistemaUsuarios.getOrganizador(usuarioLogueado.getNickname());
        }

        if (usuarioActualizado != null) {
            session.setAttribute("usuario_logueado", usuarioActualizado);
            usuarioLogueado = usuarioActualizado;
        }

    } catch (Exception e) {
        System.out.println("[JSP Detalle] Error al refrescar usuario: " + e.getMessage());
    }
}
%>

<jsp:include page="/WEB-INF/templates/navbar.jsp" />

<div class="container-fluid my-4">
    <div class="row">
        <!-- Sidebar -->
        <div class="col-md-3 col-lg-2">
            <jsp:include page="/WEB-INF/templates/categorias.jsp"/>
        </div>

        <!-- Contenido principal -->
        <div class="col-md-9 col-lg-10">
            <h1 class="text-center mt-3 mb-4">Consulta de Usuario</h1>

            <% 
            DtAsistente asistente = (DtAsistente) request.getAttribute("asistente");
            DtOrganizador organizador = (DtOrganizador) request.getAttribute("organizador");
            String contextPath = request.getContextPath();
            %>

            <div class="d-flex justify-content-center mb-5">
            <% 
            DtUsuario usuarioConsulta = (asistente != null) ? asistente : organizador;

            if (usuarioConsulta != null) {
                String imgSrc = (usuarioConsulta.getImg() != null && !usuarioConsulta.getImg().isEmpty())
                    ? usuarioConsulta.getImg()
                    : contextPath + "/media/images/defaultUser.png";

                // Consultar valores actualizados
                int cantidadSeguidores = 0;
                int cantidadSeguidos = 0;

                try {
                    cantidadSeguidores = sistemaUsuarios.getSeguidoresDeUsuario(usuarioConsulta.getNickname()).getItem().size();
                    cantidadSeguidos = sistemaUsuarios.getSeguidosDeUsuario(usuarioConsulta.getNickname()).getItem().size();
                } catch (Exception e) {
                    System.out.println("[JSP Detalle] Error al obtener contadores: " + e.getMessage());
                }
            %>

            <div class="usuario-detalle">
                <div class="card shadow-sm d-flex flex-row flex-wrap">
                    <img src="<%= imgSrc %>" alt="Imagen de <%= usuarioConsulta.getNombre() %>" class="imgUser" />
                    <div class="card-content">
                        <h4><%= usuarioConsulta.getNombre() %> <%= (usuarioConsulta instanceof DtAsistente ? ((DtAsistente)usuarioConsulta).getApellido() : "") %></h4>
                        <p class="text-muted mb-3"><%= usuarioConsulta.getEmail() %></p>
                        <hr>
                        <p><strong>Nickname:</strong> <%= usuarioConsulta.getNickname() %></p>

                        <% if (usuarioConsulta instanceof DtAsistente) { %>
                            <p><strong>Fecha de nacimiento:</strong> <%= ((DtAsistente)usuarioConsulta).getNacimiento().toXMLFormat().split("T")[0] %></p>
                        
                        	<p><strong>Institución:</strong> <%= ((DtAsistente)usuarioConsulta).getInstitucion()%></p>
                        
                        <% } else if (usuarioConsulta instanceof DtOrganizador) { %>
                            <p><strong>Descripción:</strong> <%= ((DtOrganizador)usuarioConsulta).getDescripcion() %></p>
                            <% if (((DtOrganizador)usuarioConsulta).getSitioWeb() != null && !((DtOrganizador)usuarioConsulta).getSitioWeb().isEmpty()) { %>
                                <p><strong>Sitio Web:</strong> 
                                    <a href="<%= ((DtOrganizador)usuarioConsulta).getSitioWeb() %>" target="_blank">
                                        <%= ((DtOrganizador)usuarioConsulta).getSitioWeb() %>
                                    </a>
                                </p>
                            <% } %>
                        <% } %>
                    </div>
                </div>

                <div class="d-flex gap-5 align-items-center justify-content-center mt-2">
                    <div class="gap-3 d-flex">
                        <div class="div-follow" onclick="window.location.href='<%= contextPath %>/usuarios?filtro=seguidores&nickname=<%= usuarioConsulta.getNickname() %>'">
                            <h5>Seguidores</h5>
                            <h5><%= cantidadSeguidores %></h5>
                        </div> 
                        <div class="div-follow" onclick="window.location.href='<%= contextPath %>/usuarios?filtro=seguidos&nickname=<%= usuarioConsulta.getNickname() %>'">
                            <h5>Seguidos</h5>
                            <h5><%= cantidadSeguidos %></h5>
                        </div>
                    </div>

                    <% 
                    if (logueado && !usuarioLogueado.getNickname().equals(usuarioConsulta.getNickname())) {
                        boolean loSigue = false;
                        try {
                            List<DtUsuario> seguidos = sistemaUsuarios.getSeguidosDeUsuario(usuarioLogueado.getNickname()).getItem();
                            if (seguidos != null) {
                                loSigue = seguidos.stream().anyMatch(s -> s.getNickname().equals(usuarioConsulta.getNickname()));
                            }
                        } catch (Exception e) {
                            System.out.println("[JSP Detalle] Error verificando seguimiento: " + e.getMessage());
                        }
                    %>

                    <form action="<%= contextPath %>/usuarios" method="post">
                        <input type="hidden" name="accion" value="<%= loSigue ? "dejarDeSeguir" : "seguir" %>">
                        <input type="hidden" name="nickname" value="<%= usuarioConsulta.getNickname() %>">
                        <input type="hidden" name="redirectUrl" value="<%= contextPath + "/usuarios?nickname=" + usuarioConsulta.getNickname() %>">
                        <button type="submit" class="btn <%= loSigue ? "btn-danger" : "btn-primary" %>" style="width: 135px;">
                            <%= loSigue ? "Dejar de seguir" : "Seguir" %>
                        </button>
                    </form>
                    <% } %>
                </div>
            </div>

            <% } // fin if usuarioConsulta %>
            </div>

            <div class="volver-container">
                <button class="btn btn-secondary px-5" onclick="window.location.href='<%= contextPath %>/usuarios'">
                    Volver
                </button>
            </div>
        </div>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
