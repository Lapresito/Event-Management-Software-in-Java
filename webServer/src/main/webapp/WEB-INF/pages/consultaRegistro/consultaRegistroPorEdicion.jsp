<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="clienteServidor.publicar.*" %>

<!doctype html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Registros de Edición</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Bootstrap -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">

    <style>
        h1 {
            padding: 2rem;
            font-size: 1.8rem;
        }

        /* ===== CARD GENERAL ===== */
        .card {
            background-color: #fff;
            border-radius: 10px;
            box-shadow: 0 2px 6px rgba(0,0,0,0.1);
            transition: transform 0.25s ease, box-shadow 0.25s ease;
            text-decoration: none;
            height: 100%;
            font-size: 0.9rem;
        }

        .card:hover {
            transform: translateY(-4px);
            box-shadow: 0 6px 16px rgba(0,0,0,0.12);
            text-decoration: none;
        }

        .card-header {
            background-color: #f8f9fa;
            border-bottom: 1px solid #e0e0e0;
            padding: 0.6rem 0.8rem;
        }

        .card-header h5 {
            margin: 0;
            font-size: 1rem;
            color: #333;
            font-weight: 600;
        }

        .card-body {
            padding: 0.9rem;
        }

        .card-body p {
            margin-bottom: 0.4rem;
            color: #444;
            font-size: 0.9rem;
        }

        .card-body strong {
            color: #222;
        }

        .row {
            justify-content: center;
        }

        /* ===== RESPONSIVE GRID ===== */
        @media (min-width: 1200px) {
            .col-xl-3 {
                flex: 0 0 25%;
                max-width: 25%;
            }
        }
    </style>
</head>

<body>
    <jsp:include page="/WEB-INF/templates/navbar.jsp"/> 
    <div class="sidebar">
        <jsp:include page="/WEB-INF/templates/categorias.jsp"/>
    </div>
    <%
        String edicionParam = (String) request.getAttribute("edicionParam");
        String eventoParam = (String) request.getAttribute("eventoParam");
        List<DtRegistro> registros = (List<DtRegistro>) request.getAttribute("registros");
    %>

    <h1 class="text-center">Registros de Edición: <%= edicionParam %></h1>

    <%
        if (registros == null || registros.isEmpty()) {
    %>
        <p class="text-center mt-4">No hay registros disponibles para esta edición.</p>
    <%
        } else {
    %>
        <p class="text-center mt-2">Cantidad de registros: <%= registros.size() %></p>

        <section class="container mt-4">
            <div class="row">
                <%
                    for (DtRegistro r : registros) {
                %>
                    <div class="col-12 col-sm-6 col-md-4 col-xl-3 mb-4">
                        <a href="/webServer/consultaRegistro?nickname=<%= r.getNickAsistente() %>&nombreEdicion=<%= edicionParam %>" 
                           class="card text-center">
                            <div class="card-header">
                                <h5><%= r.getNickAsistente() %></h5>
                            </div>
                            <div class="card-body">
                                <p><strong>Evento:</strong> <%= eventoParam %></p>
                                <p><strong>Edición:</strong> <%= edicionParam %></p>
                                <p><strong>Fecha de alta:</strong> <%= r.getFechaAlta().toXMLFormat().split("T")[0] %></p>
                                <p><strong>Costo:</strong> $<%= r.getCosto() %></p>
                                <%-- if (r.getCodigoPatrocinio() != null && !r.getCodigoPatrocinio().isEmpty()) { --%>
                                   <%-- <% %><p><strong>Código de patrocinio:</strong> = r.getCodigoPatrocinio()</p> --%>
                                <%-- } --%>
                            </div>
                        </a>
                    </div>
                <%
                    }
                %>
            </div>
        </section>
    <%
        }
    %>

    <!-- Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
