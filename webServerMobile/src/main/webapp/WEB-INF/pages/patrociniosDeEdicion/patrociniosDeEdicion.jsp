<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.Map" %>
<%@ page import="clienteServidor.publicar.*" %>

<!doctype html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Patrocinios de Edición</title>
    <meta name="viewport" content="width=device-width, initial-scale=1"> <!-- Responsive meta -->
    
    <!-- Bootstrap -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">

    <style>
    		/* Card principal */
		h1{
			padding:3rem;
		}
        /* CARD */
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

        /* Botón dentro de la card */
        .card .btn {
            color: black;
            border: none;
            padding: 10px 20px;
            border-radius: 5px;
            cursor: pointer;
            transition: transform 0.2s ease, background-color 0.2s ease;
        }

        .card-title {
            font-size: 1rem;
        }

        .card p {
            margin-bottom: 0.5rem;
            color:black;
        }
        .card .card-body h5 {
            margin-bottom: 0.5rem;
            color:black;
        }

		.Platino .card-header,
		.Platino .card-footer,
		.Platino .card-body .btn {
		    background: linear-gradient(135deg, #bcc6cc, #ffffff, #bcc6cc);
		    background-size: 200% 200%;
		    transition: background-position 0.8s ease;
		}
		
		.Platino:hover .card-header,
		.Platino:hover .card-footer,
		.Platino:hover .card-body .btn {
		    background-position: 100% 0;
		}
		
		/* ---- PLATA ---- */
		.Plata .card-header,
		.Plata .card-footer,
		.Plata .card-body .btn {
		    background: linear-gradient(135deg, #d0d0d0, #ffffff, #d0d0d0);
		    background-size: 200% 200%;
		    transition: background-position 0.8s ease;
		}
		
		.Plata:hover .card-header,
		.Plata:hover .card-footer,
		.Plata:hover .card-body .btn {
		    background-position: 100% 0;
		}
		
		/* ---- ORO ---- */
		.Oro .card-header,
		.Oro .card-footer,
		.Oro .card-body .btn {
		    background: linear-gradient(135deg, #d4c66a, #fff7c0, #d4c66a);
		    background-size: 200% 200%;
		    transition: background-position 0.8s ease;
		}
		
		.Oro:hover .card-header,
		.Oro:hover .card-footer,
		.Oro:hover .card-body .btn {
		    background-position: 100% 0;
		}
		
		/* ---- BRONCE ---- */
		.Bronce .card-header,
		.Bronce .card-footer,
		.Bronce .card-body .btn {
		    background: linear-gradient(135deg, #b07a4d, #ffe7d0, #b07a4d);
		    background-size: 200% 200%;
		    transition: background-position 0.8s ease;
		}
		
		.Bronce:hover .card-header,
		.Bronce:hover .card-footer,
		.Bronce:hover .card-body .btn {
		    background-position: 100% 0;
		}
				

        /* Asegura espacio entre tarjetas */
        .row {
            justify-content: center;
        }
    </style>
</head>
<body>
    <jsp:include page="/WEB-INF/templates/navbar.jsp"/> 

    <%
        String edicionParam = (String) request.getAttribute("edicionParam");
        Map<String, DtPatrocinio> patrocinios = (Map<String, DtPatrocinio>) request.getAttribute("mapPatrocinios");
    %>

    <h3 class="text-center">Patrocinios de <%= edicionParam %></h3>

    <%
        if (patrocinios == null || patrocinios.isEmpty()) {
    %>
        <p class="text-center mt-4">No hay patrocinios disponibles para esta edición.</p>
    <%
        } else {
    %>
        <p class="text-center mt-2">Cantidad de patrocinios: <%= patrocinios.size() %></p>

        <section class="container mt-4">
            <div class="row">
                <%
                    for (Map.Entry<String, DtPatrocinio> entry : patrocinios.entrySet()) {
                        DtPatrocinio p = entry.getValue();
                %>
	                    <div class="col-12 col-sm-6 col-md-4 mb-4">
	                        <a  href="/webServerMobile/patrocinios?edicion=<%=p.getNombreEdicion()%>&institucion=<%=p.getNombreInstitucion()%>" class="card text-center h-100 <%= p.getNivel()%>">
	                            <div class="card-header">
	                                <p>Institución: <%= p.getNombreInstitucion() %></p>
	                            </div>
	                            <div class="card-body">
	                                <h5 class="card-title">
	                                    Cupos Disponibles: <%= p.getCupos() - p.getCantRegistros() %> / <%= p.getCupos() %>
	                                </h5>

	                            </div>
	                            <div class="card-footer text-body-secondary">
	                                <p>Fecha de alta: <%= p.getFechaAlta() %></p>
	                            </div>
	                        </a>
	                    </div>
                <%
                    } // fin for
                %>
            </div> <!-- row -->
        </section>
    <%
        } // fin else
    %>
	<div class="text-center">
	  <button class="btn btn-primary mt-3" onclick="history.back()">← Volver</button>
	</div>

    <!-- Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
