<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="clienteServidor.publicar.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Consulta Registro</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>

	<jsp:include page="/WEB-INF/templates/navbar.jsp" />
    <div class="sidebar">
        <jsp:include page="/WEB-INF/templates/categorias.jsp"/>
    </div>
	<h1 class="mb-4 text-center">Consulta de Registro</h1>

	<%
	    DtRegistro registro = (DtRegistro) request.getAttribute("registro");
		Boolean asist = registro.isAsistio();
		String asistio = "";
		if(asist){
			asistio = "Sí";
		}else{
			asistio = "No";
		}
	    if (registro != null) {
	%>
	<div style="display: flex; flex-direction: column; align-items:center">
		<div>
			<div class="card shadow-sm p-4">
				<h4 class="mb-3">Información del Registro</h4>
				<ul class="list-group list-group-flush">
					<li class="list-group-item">
						<strong>Evento:</strong> <%= registro.getNombreEvento() %>
					</li>
					<li class="list-group-item">
						<strong>Edición:</strong> <%= registro.getNombreEdicion() %>
					</li>
					<li class="list-group-item">
						<strong>Fecha de alta:</strong> <%= registro.getFechaAlta().toXMLFormat().split("T")[0] %>
					</li>
					<li class="list-group-item">
						<strong>Costo:</strong> $<%= registro.getCosto() %>
					</li>
					<li class="list-group-item">
						<strong>Confirmó asistencia:</strong> <%= asistio %>
					</li>
				</ul>
							
			<%	if(asist){	%>
			<br>
			<p id="mensajePdf">Si lo deseas, puedes obtener una constancia de asistencia</p>
			<a href="generarPdf?nickname=<%= registro.getNickAsistente() %>&edicion=<%= registro.getNombreEdicion() %>" 
			   class="btn btn-primary" id="btnPdf" onclick="despedir();">
			   Descargar PDF
			</a>
			<% } %>
			</div>



			
			
			<div>
				<button class="btn btn-secondary mt-3" id="volverBtn" onclick="history.back();">
    				Volver
				</button>
			</div>
		</div>
	</div>
		
	<%
	    } else {
	%>
	</div>
	<div class="container">
		<div class="alert alert-warning mt-4">
			No se encontró información del registro.
		</div>
		 	<a href="javascript:history.back()" class="btn btn-secondary mt-3">Volver</a></div>
	<%
	    }
	%>


</body>
<script>
  function despedir() {
    const texto = document.getElementById("mensajePdf");
    const boton = document.getElementById("btnPdf");

    texto.style.display = "none";
    boton.style.display = "none";

    const gracias = document.createElement("p");
    gracias.innerText = "¡Gracias por participar!";
    gracias.classList.add("mt-3", "font-weight-bold", "text-success");

    texto.parentNode.appendChild(gracias);
  }
</script>

</html>