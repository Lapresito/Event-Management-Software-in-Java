<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="jakarta.servlet.*" %>
<%@ page import="clienteServidor.publicar.*" %>

<%--@page errorPage="/WEB-INF/500.jsp"--%>
<!doctype html>
<html>
<head>
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
	<title>eventos.uy</title>
	<style>
		
		/* Card principal */
		h1{
			padding:3rem;
		}
		
		
		
		.card {
		    border-radius: 10px;
		    overflow: hidden; /* para que los bordes redondeados corten el contenido */
		    box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
		    
		}
		
		/* Header con flex */
		.card-header {
		    display: flex;
		    justify-content: space-between; /* los separa equitativamente */
		    align-items: center;
		    font-weight: 500;
		    padding: 12px 20px;
		    box-shadow: inset 0 -2px 6px rgba(255, 255, 255, 0.2); /* leve brillo interno */
		    border-bottom: 1px solid rgba(255, 255, 255, 0.3);
		}
		
		.card-header p {
		    margin: 0;
		    font-size: 1rem;
		}
		
		/* Cuerpo */
		.card-body {
		    padding: 25px;
		    background-color: #fff;
		}
		
		.card-body h5 {
		    margin-bottom: 20px;
		    font-weight: 600;
		}
		
		.card-body p {
		    margin-bottom: 8px;
		    color: #333;
		}
		
		
		/* Footer */
		.card-footer {
		    border-top: 1px solid #dee2e6;
		    font-size: 0.95rem;
		    color: #555;
		    padding: 12px 20px;
		}

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
		

		.Plata .card-body h5 {
			color: #a19a9a;
		}

		.Platino .card-body h5{
			color: rgb(192, 192, 192);
		}

		.Oro .card-body h5{
			color: #8f8600b0;
		}

		.Bronce .card-body h5{
			color: #74533598;
		}
		
		/* Responsive */
		@media (max-width: 768px) {
		    .card-header {
		        flex-direction: column;
		        align-items: flex-start;
		    }
		    .card-header p {
		        margin-bottom: 5px;
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
		DtEdicion edicion = (DtEdicion) request.getAttribute("edicion");
		String inst = (String) request.getAttribute("institucionParam"); 	
		DtPatrocinio patrocinio = (DtPatrocinio) request.getAttribute("patrocinio");
	%>
	
	<h1 align="center">Consulta De Patrocinio</h1>
	
	<section class="container mt-4" >
		
		<div class="card mb-3 <%=patrocinio.getNivel()%>">
		
			<div class="card text-center">
			  <div class="card-header" >
				<p align="center">Edicion: <spam><%=edicion.getNombreEdicion()%></spam></p>
				<p align="center">Institucion: <spam><%=inst %></spam></p>	
			  </div>
			  <div class="card-body">
			    <h5>Información del patrocinio</h5>
			    
		        <p><strong>Evento:</strong> <%=edicion.getNombreEvento() %></p>
	            <p><strong>Edición:</strong> <%=patrocinio.getNombreEdicion() %></p>
	            <p><strong>Institución:</strong> <%=patrocinio.getNombreInstitucion() %></p>
	            <p><strong>Nivel:</strong> <%=patrocinio.getNivel() %></p>
	            <p><strong>Aporte: </strong><%=patrocinio.getMonto() %> $</p>
	            
	            
	            <p><strong>Tipo de Registro:</strong> <%=patrocinio.getDTtipoRegistro()%></p>
	            
	            
	           	<p><strong>Cupos:</strong> <%=patrocinio.getCupos() %></p>
	            <p><strong>Cantidad de registros:</strong> <%=patrocinio.getCantRegistros() %></p>
	            <p><strong>Código:</strong> <%=patrocinio.getCodigo() %></p>
	            
			  </div>
			  <div class="card-footer text-body-secondary">
	            <p><strong>Fecha de alta:</strong> <%=patrocinio.getFechaAlta().toXMLFormat().split("T")[0] %></p>
			  </div>
			</div>

		  
		</div>
		
	</section>

	
	
	<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
	<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
	
</body>
