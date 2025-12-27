<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="jakarta.servlet.*"%>
<%@page import="java.util.List"%>
<%@ page import="clienteServidor.publicar.*" %>
<%@page import="java.net.URLEncoder"%>
<!doctype html>
<html>
<head>
    <meta charset="UTF-8">
    <title>eventos.uy</title>
    <link rel="stylesheet"
          href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

<style>
    .welcome {
        text-align: center;
        margin-top: 30px;
    }
    .welcome p {
        font-size: 1.1rem;
        color: #555;
    }
    .global-container {
        display: flex;
        justify-content: center;
        align-items: flex-start;
        gap: 20px;
        margin: 40px auto;
        max-width: 1200px;
    }
        .sidebar {
        width: 250px;
        flex-shrink: 0;
    }
    .main-content {
        flex: 1;
    }
    .carousel-item img {
        height: 400px;
        object-fit: cover;
    }
</style>

<jsp:include page="/WEB-INF/templates/navbar.jsp"/>

<div class="welcome">
    <h1>¡Bienvenido a eventos.uy!</h1>
    <p>Descubrí los mejores eventos en Uruguay y encontrá actividades para todos los gustos.</p>
</div>
<div class="sidebar">
    <jsp:include page="/WEB-INF/templates/categorias.jsp"/>
</div>

<div class="global-container">

    <div class="main-content">
        <!-- Carrusel de eventos del año actual -->
        <div id="carouselEventos" class="carousel slide mx-auto" data-ride="carousel" style="max-width: 800px;">
            <div class="carousel-inner">
                <%
                    List<DtEvento> eventos = (List<DtEvento>) request.getAttribute("eventosActuales");
                    if (eventos != null && !eventos.isEmpty()) {
                        for (int i = 0; i < eventos.size()-1; i++) {
                            DtEvento evento = eventos.get(i+1);
                            String url = request.getContextPath() + "/eventos?nombre=" +
                                URLEncoder.encode(evento.getNombre(), "UTF-8");
                            String imgSrc = (evento.getImg() != null && !evento.getImg().isEmpty())
                                ? evento.getImg()
                                : request.getContextPath() + "/media/imagenes/placeholderEvento-Edicion.png";
                %>
                <div class="carousel-item <%= (i == 0 ? "active" : "") %>">
                    <a href="<%= url %>" style="text-decoration: none; color: inherit;">
                        <img src="<%= imgSrc %>" class="d-block w-100" alt="<%= evento.getNombre() %>">
                        <div class="carousel-caption d-none d-md-block bg-dark bg-opacity-50 rounded p-2">
                            <h5><%= evento.getNombre() %></h5>
                            <p><%= evento.getDescripcion() %></p>
                        </div>
                    </a>
                </div>
                <%
                        }
                    } else {
                %>
                <div class="carousel-item active">
                    <img src="<%= request.getContextPath() + "/media/imagenes/placeholderEvento-Edicion.png" %>"
                         class="d-block w-100"
                         alt="Sin eventos">
                    <div class="carousel-caption d-none d-md-block bg-dark bg-opacity-50 rounded p-2">
                        <h5>Sin eventos disponibles</h5>
                    </div>
                </div>
                <% } %>
            </div>

            <a class="carousel-control-prev" href="#carouselEventos" role="button" data-slide="prev">
                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                <span class="sr-only">Anterior</span>
            </a>
            <a class="carousel-control-next" href="#carouselEventos" role="button" data-slide="next">
                <span class="carousel-control-next-icon" aria-hidden="true"></span>
                <span class="sr-only">Siguiente</span>
            </a>
        </div>
    </div>
</div>

</body>
</html>
