<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="clienteServidor.publicar.*" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Consulta Registro</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- Bootstrap -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>

<jsp:include page="/WEB-INF/templates/navbar.jsp" />

<div class="container mt-3">

    <% 
        String exito = (String) request.getAttribute("exito");
        if (exito != null) { 
    %>
        <div class="alert alert-success text-center">
            <%= exito %>
        </div>
    <% } %>
    
    <% if (request.getAttribute("error") != null) { %>
	    <div class="alert alert-danger text-center">
	        <%= request.getAttribute("error") %>
	    </div>
    <% } %>

    <h4 class="text-center mb-3">Consulta de Registro</h4>

    <%
        DtRegistro registro = (DtRegistro) request.getAttribute("registro");
        if (registro != null) {
    %>

    <div class="card shadow-sm">
        <div class="card-body p-3">
            <h5 class="text-center mb-3">Información del Registro</h5>

            <ul class="list-group list-group-flush fs-6">
                <li class="list-group-item px-2 py-2"><strong>Evento:</strong><br> <%= registro.getNombreEvento() %></li>
                <li class="list-group-item px-2 py-2"><strong>Edición:</strong><br> <%= registro.getNombreEdicion() %></li>
                <li class="list-group-item px-2 py-2"><strong>Fecha de Alta:</strong><br><%= registro.getFechaAlta().getDay() %>/<%= registro.getFechaAlta().getMonth() %>/<%= registro.getFechaAlta().getYear() %></li>
                <li class="list-group-item px-2 py-2"><strong>Costo:</strong><br> $<%= registro.getCosto() %></li>
                <li class="list-group-item px-2 py-2">
                    <% if (registro.isAsistio()) { %>
                        <span class="badge badge-success px-2 py-1">Asistencia Confirmada</span>
                    <% } else { %>
                        <span class="badge badge-warning px-2 py-1">No Confirmó Asistencia</span>
                    <% } %>
                </li>
            </ul>

            <% if (!registro.isAsistio()) { %>
                <form action="consultaRegistro" method="post">
                    <input type="hidden" name="accion" value="confirmar">
                    <input type="hidden" name="nickname" value="<%= ((DtUsuario) session.getAttribute("usuario_logueado")).getNickname() %>">
                    <input type="hidden" name="nombreEdicion" value="<%= request.getParameter("nombreEdicion") %>">

                    <button type="submit" class="btn btn-primary btn-block mt-2">
                        Confirmar Asistencia
                    </button>
                </form>
            <% } %>

            <a href="<%= request.getContextPath() %>/home" class="btn btn-secondary btn-block mt-3">
			    Volver
			</a>

        </div>
    </div>

    <% } else { %>

    <div class="alert alert-warning text-center mt-4">
        No se encontró información del registro.
    </div>

   <a href="<%= request.getContextPath() %>/home" class="btn btn-secondary btn-block mt-3">
	    Volver
	</a>


    <% } %>

</div>

</body>

<!-- Bootstrap JS -->
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</html>
