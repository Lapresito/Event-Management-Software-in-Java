<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="clienteServidor.publicar.*" %>

<!doctype html>
<html lang="es">
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>Consulta Patrocinio</title>

	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">

	<style>
		body {
			background-color: #f8f9fa;
		}

		h1 {
			font-size: 1.4rem;
			margin-top: 1rem;
			margin-bottom: 1rem;
			text-align: center;
			padding: 0 1rem;
		}

		.card {
			border-radius: 12px;
			box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
			overflow: hidden;
		}

		.card-header, .card-footer {
			padding: 10px 14px;
		}

		.card-body {
			padding: 16px;
			background-color: #fff;
			font-size: 0.95rem;
		}

		.card-body p {
			margin-bottom: 8px;
		}

		/* Mantengo tus colores */
		.Platino .card-header,
		.Platino .card-footer {
		    background: linear-gradient(135deg, #BCC6CC, #ffffff, #BCC6CC);
		}
		
		.Plata .card-header,
		.Plata .card-footer {
		    background: linear-gradient(135deg, #aaa9a9, #ffffff, #aaa9a9);
		}
		
		.Oro .card-header,
		.Oro .card-footer {
		    background: linear-gradient(135deg, #8f8600b0, #ffffff, #8f8600b0);
		}
		
		.Bronce .card-header,
		.Bronce .card-footer {
		    background: linear-gradient(135deg, #74533598, #ffffff, #74533598);
		}

		.Platino .card-body h5 { color: rgb(192, 192, 192); }
		.Plata .card-body h5 { color: #a19a9a; }
		.Oro .card-body h5 { color: #8f8600b0; }
		.Bronce .card-body h5 { color: #74533598; }

		/* Mobile-first */
		@media (max-width: 576px) {
			.card-header {
				display: block;
				text-align: center;
			}
		}
	</style>
</head>

<body>

	<jsp:include page="/WEB-INF/templates/navbar.jsp"/> 

	<%
		DtEdicion edicion = (DtEdicion) request.getAttribute("edicion");
		String inst = (String) request.getAttribute("institucionParam"); 	
		DtPatrocinio patrocinio = (DtPatrocinio) request.getAttribute("patrocinio");
	%>
	
	<h1>Consulta de Patrocinio</h1>

	<div class="container px-2 mb-4">

		<div class="card <%= patrocinio.getNivel() %>">

			<div class="card-header text-center">
				<p class="mb-1"><strong>Edición:</strong> <%= edicion.getNombreEdicion() %></p>
				<p class="mb-0"><strong>Institución:</strong> <%= inst %></p>
			</div>

			<div class="card-body">
				<h5 class="text-center mb-3">Información del patrocinio</h5>

				<p><strong>Evento:</strong> <%= edicion.getNombreEvento() %></p>
	            <p><strong>Edición:</strong> <%= patrocinio.getNombreEdicion() %></p>
	            <p><strong>Institución:</strong> <%= patrocinio.getNombreInstitucion() %></p>
	            <p><strong>Nivel:</strong> <%= patrocinio.getNivel() %></p>
	            <p><strong>Aporte:</strong> $<%= patrocinio.getMonto() %></p>
	            <p><strong>Tipo de Registro:</strong> <%= patrocinio.getDTtipoRegistro() %></p>
	            <p><strong>Cupos:</strong> <%= patrocinio.getCupos() %></p>
	            <p><strong>Cantidad de registros:</strong> <%= patrocinio.getCantRegistros() %></p>
	            <p><strong>Código:</strong> <%= patrocinio.getCodigo() %></p>
			</div>

			<div class="card-footer text-center">
				<p class="mb-0"><strong>Fecha de alta:</strong> <%= patrocinio.getFechaAlta().toXMLFormat().split("T")[0] %></p>
			</div>

		</div>

	</div>
	<div class="text-center">
	  <button class="btn btn-primary mt-3" onclick="history.back()">← Volver</button>
	</div>

	<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
	<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

</body>
</html>
