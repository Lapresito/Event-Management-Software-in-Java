<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="clienteServidor.publicar.*" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="java.nio.charset.StandardCharsets" %>

<%
    List<DtRegistro> registros = (List<DtRegistro>) request.getAttribute("registrosNoAsistidos");
    String nickname = (String) request.getAttribute("nickname");
    boolean mostrarAsistidos = request.getAttribute("mostrarAsistidos") != null && (boolean) request.getAttribute("mostrarAsistidos");
    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><%= mostrarAsistidos ? "Registros asistidos" : "Registros no asistidos" %></title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">

    <style>
        body {
            font-family: 'Segoe UI', Roboto, sans-serif;
            margin: 0;
            background-color: #f4f5f7;
            color: #222;
            padding-bottom: 60px;
        }

        h2 {
            font-size: 1.5rem;
            margin: 20px 0 5px;
            text-align: center;
            color: #222;
        }

        .subtitle {
            font-size: 1rem;
            color: #666;
            text-align: center;
            margin-bottom: 20px;
        }

        .container-cards {
            max-width: 500px;
            margin: 0 auto;
            padding: 0 16px;
        }

        .card {
            background: #fff;
            border-radius: 14px;
            padding: 16px 18px;
            margin-bottom: 16px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.05);
            transition: transform 0.2s ease, box-shadow 0.2s ease;
        }

        .card:hover {
            transform: translateY(-3px);
            box-shadow: 0 5px 14px rgba(0,0,0,0.08);
        }

        .card-title {
            font-size: 1.1rem;
            font-weight: 600;
            margin-bottom: 8px;
            color: #333;
        }

        .item-row {
            font-size: 0.95rem;
            margin-bottom: 6px;
            color: #333;
        }

        .item-row strong {
            color: #555;
        }

        /* Botón "Ver registro" */
        .btn-ver {
            display: inline-block;
            margin-top: 10px;
            padding: 10px 14px;
            background: #e9e9e9;
            border-radius: 8px;
            font-size: 0.9rem;
            text-decoration: none;
            color: #000;
            border: 1px solid #d0d0d0;
            transition: all 0.2s ease;
        }

        .btn-ver:hover {
            background: #d0d0d0;
        }

        .btn-ver:active {
            background: #bfbfbf;
        }

        .no-data {
            text-align: center;
            margin-top: 50px;
            font-size: 1.1rem;
            color: #888;
            padding: 0 20px;
        }

        /* Botón "Volver" azul */
        .btn-volver {
            display: block;
            width: 90%;
            max-width: 400px;
            margin: 30px auto 0;
            padding: 14px;
            background: #0275d8;
            color: white;
            text-align: center;
            border-radius: 10px;
            font-size: 1rem;
            font-weight: 500;
            text-decoration: none;
            border: 1px solid #0266ba;
            transition: all 0.2s ease;
        }

        .btn-volver:hover {
            background: #0266ba;
        }

        .btn-volver:active {
            background: #0156a0;
        }
    </style>
</head>

<body>
    <jsp:include page="/WEB-INF/templates/navbar.jsp"/>

    <h2><%= mostrarAsistidos ? "Registros asistidos" : "Registros no asistidos" %></h2>
    <div class="subtitle">@<%= nickname %></div>

    <div class="container-cards">
        <%
            if (registros == null || registros.isEmpty()) {
        %>
            <p class="no-data">
                <%= mostrarAsistidos ? "No tenés registros asistidos." : "No tenés registros pendientes." %>
            </p>
        <%
            } else {
                for (DtRegistro r : registros) {
        %>
                <div class="card">
                    <div class="card-title"><%= r.getNombreEvento() %></div>
                    <div class="item-row"><strong>Edición:</strong> <%= r.getNombreEdicion() %></div>
                    <div class="item-row"><strong>Fecha de alta:</strong> <%= r.getFechaAlta().toXMLFormat().split("T")[0] %></div>
                    <div class="item-row"><strong>Costo:</strong> $<%= r.getCosto() %></div>

                    <a class="btn-ver"
                       href="<%= request.getContextPath() %>/consultaRegistro?nickname=<%= nickname %>&nombreEdicion=<%= URLEncoder.encode(r.getNombreEdicion(), StandardCharsets.UTF_8.toString()) %>">
                        Ver registro
                    </a>
                </div>
        <%
                }
            }
        %>
    </div>

    <a href="<%= request.getContextPath() %>/perfil?nickname=<%= nickname %>" class="btn-volver">Volver al perfil</a>

    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
