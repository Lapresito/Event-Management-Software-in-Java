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

    <!-- Bootstrap Mobile First -->
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="stylesheet"
          href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">

    <style>
        body {
            background-color: #f8f9fa;
        }
        .welcome {
            text-align: center;
            margin-top: 15px;
            padding: 0 15px;
        }
        .welcome h1 {
            font-size: 1.6rem;
            font-weight: bold;
        }
        .welcome p {
            font-size: 1rem;
            color: #555;
            margin-top: 5px;
        }

        /* Carrusel optimizado para móvil */
        #carouselEventos {
            width: 100%;
            margin-top: 15px;
        }

        .carousel-item img {
            height: 250px;
            object-fit: cover;
        }

        /* Mostrar caption en mobile de forma más legible */
        .carousel-caption {
            background: rgba(0,0,0,0.55);
            border-radius: 8px;
            padding: 6px;
        }
        .carousel-caption h5 {
            font-size: 1rem;
            margin-bottom: 3px;
        }
        .carousel-caption p {
            font-size: 0.75rem;
            margin: 0;
        }
    </style>
</head>
<body>

<jsp:include page="/WEB-INF/templates/navbar.jsp"/>

<div class="welcome">
    <h1>¡Bienvenido a eventos.uy!</h1>
    <p>Descubrí los mejores eventos en Uruguay 👇</p>
</div>

<!-- Carrusel de eventos -->
<div id="carouselEventos" class="carousel slide" data-ride="carousel">
    <div class="carousel-inner">
        <%
            List<DtEvento> eventos = (List<DtEvento>) request.getAttribute("eventosActuales");
            if (eventos != null && !eventos.isEmpty()) {
                for (int i = 0; i < eventos.size(); i++) {
                    DtEvento evento = eventos.get(i);
                    String url = request.getContextPath() + "/eventos?nombre=" +
                            URLEncoder.encode(evento.getNombre(), "UTF-8");
                    String imgSrc = (evento.getImg() != null && !evento.getImg().isEmpty())
                        ? evento.getImg()
                        : request.getContextPath() + "/media/imagenes/placeholder.png";
        %>
        <div class="carousel-item <%= (i == 0 ? "active" : "") %>">
            <a href="<%= url %>" style="text-decoration: none; color: inherit;">
                <img src="<%= imgSrc %>" class="d-block w-100" alt="<%= evento.getNombre() %>">
                <div class="carousel-caption">
                    <h5><%= evento.getNombre() %></h5>
                    <p><%= evento.getDescripcion() %></p>
                </div>
            </a>
        </div>
        <%      }
            } else { %>

        <div class="carousel-item active">
            <img src="<%= request.getContextPath() + "/media/imagenes/placeholder.png" %>"
                 class="d-block w-100" alt="Sin eventos">
            <div class="carousel-caption">
                <h5>Sin eventos disponibles</h5>
            </div>
        </div>

        <% } %>
    </div>

    <a class="carousel-control-prev" href="#carouselEventos" role="button" data-slide="prev">
        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
    </a>
    <a class="carousel-control-next" href="#carouselEventos" role="button" data-slide="next">
        <span class="carousel-control-next-icon" aria-hidden="true"></span>
    </a>
</div>

<!-- JS -->
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

</body>
</html>
