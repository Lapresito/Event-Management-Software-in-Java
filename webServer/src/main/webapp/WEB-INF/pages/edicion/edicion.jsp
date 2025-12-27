<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="clienteServidor.publicar.*" %>

<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Consulta de Edición</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/css/bootstrap.min.css" rel="stylesheet">
  <style>
    .card-horizontal{
      display: flex;
      flex-direction: row !important;
      flex-wrap: wrap;
    }
    .card-horizontal img {
      object-fit: cover;
      width: 100%;
      height: 100%;
      max-height: 350px;
      border-radius: .25rem 0 0 .25rem;
    }
    @media (max-width: 768px) {
      .card-horizontal {
        flex-direction: column;
      }
      .card-horizontal img {
        border-radius: .25rem .25rem 0 0;
      }
    }
    .linkRegistros,
	.linkPatrocinios,
	.linkTiposRegistros {
	    color: #aaa;              /* gris claro */
	    font-size: 1em;        
	    text-decoration: none;    /* sin subrayado */
	    margin-left: 8px;         /* pequeño espacio respecto al texto "Patrocinios" */
	    transition: color 0.2s, text-decoration 0.2s;
	}
	
	.linkRegistros:hover,
	.linkPatrocinios:hover,
	.linkTiposRegistros:hover {
	    color: #555;              /* gris más oscuro al pasar el mouse */
	    text-decoration: underline; /* lo subraya al pasar el mouse */
	    cursor: pointer;
	}
  </style>
</head>

<body class="bg-light">
  
  <jsp:include page="/WEB-INF/templates/navbar.jsp"/>

  <div class="container-fluid my-4">
    <div class="row">
      <!-- Sidebar -->
      <div class="col-md-3 col-lg-2">
        <jsp:include page="/WEB-INF/templates/categorias.jsp"/>
      </div>

      <!-- Contenido principal -->
      <div class="col-md-9 col-lg-10">
        <h2 class="text-center mb-4">Consulta de Edición</h2>

        <%
          DtUsuario usuario = (DtUsuario) session.getAttribute("usuario_logueado");
          String tipoUsuario = (String) session.getAttribute("tipo_usuario");
          boolean logueado = (usuario != null);
          DtEdicion edicion = (DtEdicion) request.getAttribute("edicion");
          int maxPatrocinos = 1;
          int maxRegistros = 1;
          int maxTiposRegistros = 1;
      
    %>

        <!-- Card principal -->
        <div class="card shadow mx-auto">
          <div class="d-flex flex-wrap flex-md-nowrap card-horizontal">
            <div class="flex-shrink-0" style="width: 40%;">
              <img 
                src="<%= (edicion.getImagen() != null && !edicion.getImagen().isEmpty()) 
                        ? edicion.getImagen() 
                        : request.getContextPath() + "/media/imagenes/placeholder.png" %>"
                alt="<%= edicion.getNombreEdicion() %>" 
                class="img-fluid h-100 w-100">
            </div>

            <div class="flex-grow-1 p-4">
              <h3 class="card-title mb-3"><%= edicion.getNombreEdicion() %></h3>
              
              <div class="row">
                <div class="col-md-6">
                  <p><strong>Evento:</strong> <%= edicion.getNombreEvento() %></p>
                  <p><strong>Organizador:</strong> <%= edicion.getOrganizador() %></p>
                </div>
                <div class="col-md-6">
                  <p><strong>Ciudad:</strong> <%= edicion.getCiudad() %></p>
                  <p><strong>País:</strong> <%= edicion.getPais() %></p>
                </div>
              </div>

              <p><strong>Fechas:</strong> 
                <%= edicion.getFechaIni().toXMLFormat().split("T")[0] %> — 
                <%= edicion.getFechaFin().toXMLFormat().split("T")[0] %>
              </p>

              <% if (usuario != null && usuario.getNickname().equals(edicion.getOrganizador())) { %>
                <p><strong>Estado:</strong> <%= edicion.getEstado() %></p>
              <% } %>
            </div>
          </div>
        </div>

        <!-- Video promocional -->
        <%
          String videoURL = edicion.getVideoURL();
          if (videoURL != null && !videoURL.trim().isEmpty()) {
            String embedUrl = videoURL;
            if (videoURL.contains("youtube.com/watch?v=")) {
              embedUrl = videoURL.replace("watch?v=", "embed/");
            } else if (videoURL.contains("youtu.be/")) {
              embedUrl = videoURL.replace("youtu.be/", "www.youtube.com/embed/");
            }
        %>
			<div class="mt-4 d-flex justify-content-center">
			  <div style="width: 80%;">
			
			    <!-- Botón que muestra u oculta el video -->
			    <div class="text-center mb-3">
			      <button class="btn btn-outline-primary" type="button" 
			              onclick="toggleVideo()">Ver video promocional</button>
			    </div>
			
			    <!-- Contenedor del video, oculto por defecto -->
			    <div id="videoContainer" class="embed-responsive embed-responsive-16by9 rounded shadow-sm" style="display: none;">
			      <iframe class="embed-responsive-item rounded" 
			              src="<%= embedUrl %>" 
			              allowfullscreen></iframe>
			    </div>
			  </div>
			</div>
			
			<script>
			  function toggleVideo() {
			    const container = document.getElementById("videoContainer");
			    const button = event.target;
			    if (container.style.display === "none") {
			      container.style.display = "block";
			      button.textContent = "Ocultar video";
			    } else {
			      container.style.display = "none";
			      button.textContent = "Ver video promocional";
			    }
			  }
			</script>
        <%
          }
        %>


    <!-- Bloques inferiores -->
    <div class="mt-4">
      <!-- Patrocinios -->
      <hr>
      <h5>Patrocinios</h5>
      <div class="list-group">
        <%
        	List<String> patrocinios = (List<String>) request.getAttribute("listaPatrocinios");        
        
            if (patrocinios != null && !patrocinios.isEmpty()) {
                for (String p : patrocinios.subList(0, Math.min(maxPatrocinos, patrocinios.size()))) {
        %>
              <button class="list-group-item list-group-item-action"
                onclick="window.location.href='/webServer/patrocinios?evento=<%= edicion.getNombreEvento() %>&edicion=<%= edicion.getNombreEdicion() %>&institucion=<%= p %>'">
                Patrocinio <%= p %> — <%= edicion.getNombreEvento() %> (<%= edicion.getNombreEdicion() %>)
              </button>
        <%
                }
                
                %>
                    <a class="linkPatrocinios" href ="/webServer/patrocinios?edicion=<%=edicion.getNombreEdicion()%>">ver todos (<%=patrocinios.size()%> en total)</a>                
                <%  
             
            } else {
        %>
          <div class="text-muted">No hay patrocinios</div>
        <%
          }
        %>
      </div>

      <!-- Tipos de registro -->
      <hr>
      <h5>Tipos de Registro</h5>
      <div class="list-group">
        <%
          List<String> tipos = (List<String>) request.getAttribute("listaTRegistro");
          if (tipos != null && !tipos.isEmpty()) {
            for (String tr : tipos.subList(0, Math.min(maxTiposRegistros, tipos.size()))) {
        %>
              <button class="list-group-item list-group-item-action"
                onclick="window.location.href='/webServer/tipoRegistro?evento=<%= edicion.getNombreEvento() %>&edicion=<%= edicion.getNombreEdicion() %>&tipoRegistro=<%= tr %>'">
                <%= tr %> — <%= edicion.getNombreEvento() %> (<%= edicion.getNombreEdicion() %>)
              </button>
        <%
            }
            
            %>
            <a class="linkTiposRegistros" href ="/webServer/tipoRegistro?edicion=<%=edicion.getNombreEdicion()%>">ver todos (<%=tipos.size()%> en total)</a>                
        	<%  
          } else {
        %>
          <div class="text-muted">No hay tipos de registro</div>
        <%
          }
        %>
      </div>

          <%
            if ("organizador".equalsIgnoreCase(tipoUsuario) 
                && usuario != null 
                && edicion.getOrganizador().equals(((DtOrganizador)usuario).getNickname())) {

          		List<DtRegistro> registros = (List<DtRegistro>) request.getAttribute("listaRegistros");
	      		%>
		          <hr>
		          <h5>Registros</h5>
		          <div class="list-group">
	          	<%
	            if (registros != null && !registros.isEmpty()) {
	              for (DtRegistro r : registros.subList(0, Math.min(maxRegistros, registros.size()))) {
		          		%>
		                <button class="list-group-item list-group-item-action"
		                  onclick="window.location.href='/webServer/consultaRegistro?nickname=<%= r.getNickAsistente() %>&nombreEdicion=<%= edicion.getNombreEdicion() %>'">
		                  Registro de <%= r.getNickAsistente() %>
		                </button>
		              	<a class="linkRegistros" href ="/webServer/consultaRegistro?nombreEdicion=<%=edicion.getNombreEdicion()%>">ver todos (<%=registros.size()%> en total)</a>                
		          		<%
              		}
            	} else {
		          %>
		          <div class="text-muted">No hay registros en esta edición</div>
		          <%
		        }
		          %>
	          	</div>
      <%
	        } else if ("asistente".equalsIgnoreCase(tipoUsuario)) {
	          String nick = ((DtAsistente)usuario).getNickname();
	          List<DtRegistro> registros = (List<DtRegistro>) request.getAttribute("listaRegistros");
	          boolean yaRegistrado = false;
	          for (DtRegistro reg : registros) {
	            if (reg.getNickAsistente().equals(nick)) { yaRegistrado = true; break; }
	          }
      			%>
	          <hr>
	          <h5>Registro</h5>
	          <% if (yaRegistrado) { %>
		            <a href="/webServer/consultaRegistro?nickname=<%= nick %>&nombreEdicion=<%= edicion.getNombreEdicion() %>" 
		              class="btn btn-success mt-2">
		              Ya estás registrado
		            </a>
		          <% } else { %>
		            <p>No estás registrado en esta edición</p>
		          <% } %>
		      	<%
        	}
          
          if(logueado){
	      		%>
		          <hr>
		          <h5>RegistrosAsistidos</h5>
		          <div class="list-group">
	          	<%
	          	List<DtRegistro> registros = (List<DtRegistro>) request.getAttribute("registrosAsistidos");
	          
	            if (registros != null && !registros.isEmpty()) {
	              for (DtRegistro r : registros.subList(0, Math.min(maxRegistros, registros.size()))) {
		          		%>
		                <button class="list-group-item list-group-item-action"
		                  onclick="window.location.href='/webServer/consultaRegistro?nickname=<%= r.getNickAsistente() %>&nombreEdicion=<%= edicion.getNombreEdicion() %>'">
		                  Registro de <%= r.getNickAsistente() %>
		                </button>
		              	<a class="linkRegistros" href ="/webServer/consultaRegistroAsistido?nombreEdicion=<%=edicion.getNombreEdicion()%>">ver todos (<%=registros.size()%> en total)</a>                
		          		<%
		          		
              		}
            	}else{
  		          %>
  		          <div class="text-muted">No hay registros Asistidos en esta edición</div>
  		          <%
            	}
	      
          }
      %>
      	  <div class="text-center mt-4">
            <button class="btn btn-secondary" onclick="if (document.referrer) { window.location = document.referrer; } else { window.location.href = '<%= request.getContextPath() %>/inicio'; }">
              Volver
            </button>
          </div>
      
      
      
    </div>
  </div>

</body>
</html>
