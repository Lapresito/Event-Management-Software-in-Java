<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%--@page errorPage="/WEB-INF/500.jsp"--%>
<!doctype html>
<html>
<head>
    <meta charset="UTF-8" />
	<title>eventos.uy</title>
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
	<script defer src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
	<script defer src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
	<script defer src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
	<script defer src="<%= request.getContextPath() %>/media/js/altaInstitucion.js"></script>
	<style>
		input[type="file"].form-control {
		  background-color: #fff;
		  border: 1px solid #ced4da;
		  border-radius: 0.375rem;
		  padding:0;
		}
		
		input[type="file"].form-control::file-selector-button {
		  height: 100%;
		  border: none;
		  background-color: #f8f9fa;
		  color: #212529;
		  padding: 0 1rem;
		  border-right: 1px solid #ced4da;
		  cursor: pointer;
		}
		
		input[type="file"].form-control::file-selector-button:hover {
		  background-color: #e9ecef;
		}
	</style>
		
	
</head>
<body>
	<jsp:include page="/WEB-INF/templates/navbar.jsp"/> 

    <div class="container mt-3">
            <h1 class="text-center mb-4">Alta De Institución</h1>

			<div id="errorMsg" class="alert text-center" style="display:none;">
			 
			</div>

            <form id="institucionForm">
                <fieldset enabled>
                    <div class="mb-4" >
                        <label for="nombreInstitucion" class="form-label">Nombre*</label>
                        <input required type="text" id="nombre" class="form-control" placeholder="Ej.: Facultad De Ingeniería">
                    </div>

                    <div class="mb-4">
                        <label for="descripcion" class="form-label">Descripcion*</label>
                        <textarea required id="descripcion" name="descripcion" rows="5" class="form-control"></textarea>
                    </div>

                    <div class="mb-4" >
                        <label for="sitioWeb" class="form-label">Sitio web*</label>
                        <input required type="text" id="sitioWeb" class="form-control" placeholder="Ej.: www.fing.com">
                    </div>

                    <div class="row mb-3 align-items-center">
                        <div class="col-2">
                            <!-- Imagen de evento (placeholder inicial) -->
                            <img id="preview" src="${pageContext.request.contextPath}/media/imagenes/placeholderEvento-Edicion.png" class="rounded-3 img-thumbnail mb-0" alt="imagen de institucion">
                            
                        </div>
                        <div class="col-10 mx-auto ">
                            <!-- Input de carga -->
							<input type="file" class="form-control" id="img" aria-describedby="inputGroupFileAddon04" aria-label="Upload"  accept="image/*">
                        </div>  
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

</body>
</html>
