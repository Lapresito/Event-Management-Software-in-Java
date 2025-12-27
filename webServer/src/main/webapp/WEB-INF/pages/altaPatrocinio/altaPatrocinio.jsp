<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="clienteServidor.publicar.*" %>
<!doctype html>
<html>
<head>
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
	<title>eventos.uy</title>
	<style>
		#preview {
		width: 150px;
		height: 150px;
		object-fit: cover;
		}
	</style>

	
</head>
<body>
	<jsp:include page="/WEB-INF/templates/navbar.jsp"/> 
		<% 
			DtUsuario usuario = (DtUsuario) session.getAttribute("usuario_logueado");
		%>
		<script>
  			const org = "<%= usuario.getNickname() %>";
		</script>
<div class="container mt-3">
        <h1 class="text-center mb-4">Alta De Patrocinio</h1>

		<div id="errorMsg" class="alert text-center" style="display:none;">
		 
		</div>

        <form id="formulario">
            <fieldset enabled>
                <div class="mb-3">
                    <label for="EventosDisponibles" class="form-label">Seleccione Un Evento*</label>
                    <select id="EventosDisponibles" class="form-control">
                        <!-- opciones dinamicas -->
                    </select>
                </div>

                <div class="mb-3">
                    <label for="edicionesOrganizadas" class="form-label">Seleccione Una edicion*</label>
                    <select id="edicionesOrganizadas" class="form-control" disabled="true">
                        <!-- Opciones dinamicas -->
                    </select>
                </div>

                <div class="mb-3">
                    <label for="tiposDeRegistroDeEdicion" class="form-label">Seleccione Un Tipo de registro*</label>
                    <select id="tiposDeRegistroDeEdicion" class="form-control" disabled="true">
                        <!-- Opciones dinamicas -->
                    </select>
                </div>

                <div class="row mb-4">
                    <div class=" col-8" >
                        <label for="institucion" class="form-label">Seleccione Una institucion*</label>
                        <select id="institucion" class="form-control" disabled="true">
                            <!-- Opciones dinamicas -->
                        </select>
                    </div>
    
                    <div class="col-4">
                        <label for="nivel" class="form-label">Seleccione Un nivel*</label>
                        <select id="nivel" class="form-control" disabled="true">
                           <option value="Platino">Platino</option>
                           <option value="Oro">Oro</option>
                           <option value="Plata">Plata</option>
                           <option value="Bronce">Bronce</option>
                        </select>
                    </div>
                </div>

                <div class="row">
                    <div class="col-6 mb-4">
                        <label for="monto" class="form-label">Aporte económico*</label>
                        <input type="number" id="monto" class="form-control" placeholder="Ej.: 5000" disabled="true">
                    </div>
                    
                    <div class=" col-6 mb-4">
                        <label for="registrosGratuitos" class="form-label">Cantidad de registros gratuitos*</label>
                        <input type="number" id="registrosGratuitos" class="form-control" placeholder="Ej.: 200" disabled="true">
                    </div>
                </div>
                
                <div class="mb-4">
                     <label for="monto" class="form-label">Código de patrocinio*</label>
                     <input type="text" id=codigo class="form-control mb-4" placeholder="Ej.: ABCDE" disabled="true">
                </div>
                
                <div class="row mb-3 justify-content-end d-flex">
                    <div class="col-2">
                        <button type="submit" id="Aceptar" class="btn btn-primary w-100">Aceptar</button>
                    </div>
                    <div class="col-2">    
                        <button id="Cancelar" class="btn btn-primary w-100">Cancelar</button>
                    </div>
                </div>
            </fieldset>
    </form>
</div>

	<script src="<%= request.getContextPath() %>/media/js/altaPatrocinio.js"></script>
	<script  src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
	<script  src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
	<script  src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
	
	
</body>
