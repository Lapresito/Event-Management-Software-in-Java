<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="clienteServidor.publicar.*" %>

<!doctype html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Tipos de Registro de Edición</title>
    <meta name="viewport" content="width=device-width, initial-scale=1"> 
    
    <!-- Bootstrap -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">

    <style>
        h1 {
            padding: 3rem;
        }

        .card {
            background-color: #fff;
            border-radius: 12px;
            box-shadow: 0 2px 6px rgba(0,0,0,0.1);
            transition: transform 0.3s ease, box-shadow 0.3s ease;
        }

        .card:hover {
            transform: scale(1.03);
            box-shadow: 0 6px 20px rgba(0,0,0,0.15);
            text-decoration:none;
        }

        .card-title {
            font-size: 1rem;
        }

        .card p {
            margin-bottom: 0.5rem;
            color: black;
        }

        .row {
            justify-content: center;
        }

        .TipoRegistro .card-header,
        .TipoRegistro .card-footer,
        .TipoRegistro .card-body {
            background: linear-gradient(135deg, #c0e0ff, #ffffff, #c0e0ff);
            background-size: 200% 200%;
            transition: background-position 0.8s ease;
        }

        .TipoRegistro:hover .card-header,
        .TipoRegistro:hover .card-footer,
        .TipoRegistro:hover .card-body {
            background-position: 100% 0;
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
    	String eventoParam = (String) request.getAttribute("evento");
        List<DtTipoRegistro> tipos = (List<DtTipoRegistro>) request.getAttribute("DtTipoRegistros");
        
    %>

    <h1 class="text-center">Tipos de Registro de Edición: <%= edicionParam %></h1>

    <%
        if (tipos == null || tipos.isEmpty()) {
    %>
        <p class="text-center mt-4">No hay tipos de registro disponibles para esta edición.</p>
    <%
        } else {
    %>
        <p class="text-center mt-2">Cantidad de tipos de registro: <%= tipos.size() %></p>

        <section class="container mt-4">
            <div class="row">
                <%
                    for (DtTipoRegistro tr : tipos) {
                %>
	                <div class="col-12 col-sm-6 col-md-4 mb-4">
	                    <a href="/webServer/tipoRegistro?evento=<%=eventoParam %>&edicion=<%=tr.getNombreEdicion()%>&tipoRegistro=<%=tr.getNombreTipoRegistro()%>" 
                           class="card text-center h-100 TipoRegistro">
	                        <div class="card-header">
	                            <p><strong>Nombre:</strong> <%=tr.getNombreTipoRegistro()%></p>
	                        </div>
	                        <div class="card-body">
	                            <p><strong>Evento:</strong> <%= eventoParam %></p>
	                            <p><strong>Edición:</strong> <%= tr.getNombreEdicion() %></p>
	                            <p><strong>Descripción:</strong> <%= tr.getDescripcion() %></p>
	                            <p><strong>Costo:</strong> $<%= tr.getPrecio() %></p>
	                            <p><strong>Cupo:</strong> <%= tr.getCupo() %></p>
	                        </div>
	                        <div class="card-footer text-body-secondary">
	                            <!-- Puedes agregar fecha de creación o info extra si querés -->
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
