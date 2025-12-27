<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="clienteServidor.publicar.*" %>
<script defer src="<%= request.getContextPath() %>/media/js/categorias.js"></script>

<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

  <style>
    body {
      background-color: #f8f9fa;
    }

	/* Sidebar fijo con scroll si se pasa de alto */
	#sidebar {
	  position: fixed;
	  top: 72px; /* justo debajo de la navbar */
	  left: 10px; /* margen chiquito del borde izquierdo */
	  width: 220px;
	  background-color: #f8f9fa;
	  border: 1px solid #ddd;
	  border-radius: 8px;
	  padding: 10px 0;
	  z-index: 1050;
	  height: fit-content;
	  max-height: calc(100vh - 82px); /* límite para no pasarse del alto visible */
	  overflow-y: auto; /* permite hacer scroll si se excede */
	  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.08);
	}
	
	#sidebar {
  scrollbar-width: none; /* Firefox */
  -ms-overflow-style: none; /* Internet Explorer y Edge antiguos */
}

#sidebar::-webkit-scrollbar {
  display: none; /* Chrome, Safari y Edge nuevos */
}
	
    #main {
      margin-left: 250px !important;
      padding: 20px !important;
      gap: 0 !important;
    }

    h5, h4 {
      text-align: center !important;
      font-size: 1.05rem !important;
      font-weight: 600 !important;
    }

    .list-group-item {
      font-size: 0.88rem !important;
      border: none !important;
    }

    .list-group-item:hover {
      background-color: #e9ecef;
      cursor: pointer !important;
    }

    .list-group-item.active {
      background-color: #0d6efd !important;
      border-color: #0d6efd !important;
      color: white !important;
    }

    /* Estilo sutil del margen con el color del navbar */
    .navbar-margin {
      background-color: #343a40 !important; /* mismo color que navbar-dark bg-dark */
      height: 8px !important;
      width: 100% !important;
    }
  </style>
</head>

<body>
  <%
    jakarta.servlet.http.HttpSession sesion = (jakarta.servlet.http.HttpSession) request.getSession(false);
    DtUsuario usuario = null;
    String tipoUsuario = null;

    if (sesion != null) {
      usuario = (DtUsuario) sesion.getAttribute("usuario_logueado");
      tipoUsuario = (String) sesion.getAttribute("tipo_usuario");
    }
  %>

  <div id="sidebar">
    <% if (usuario != null && tipoUsuario != null) { %>
      <div class="mb-3">
        <h5 class="text-center border-bottom pb-2">Acciones</h5>

        <% if ("organizador".equalsIgnoreCase(tipoUsuario)) { %>
          <div class="list-group">
            <a href="<%= request.getContextPath() %>/altaEvento" class="list-group-item list-group-item-action">Alta Evento</a>
            <a href="<%= request.getContextPath() %>/altaEdicion" class="list-group-item list-group-item-action">Alta Edición Evento</a>
            <a href="<%= request.getContextPath() %>/altaTipoRegistro" class="list-group-item list-group-item-action">Alta Tipo Registro</a>
            <a href="<%= request.getContextPath() %>/altaInstitucion" class="list-group-item list-group-item-action">Alta Institución</a>
            <a href="<%= request.getContextPath() %>/altaPatrocinio" class="list-group-item list-group-item-action">Alta Patrocinio</a>
          </div>
        <% } else if ("asistente".equalsIgnoreCase(tipoUsuario)) { %>
          <div class="list-group">
            <a href="<%= request.getContextPath() %>/altaRegistro" class="list-group-item list-group-item-action">Registrarse a un evento</a>
          </div>
        <% } %>
      </div>
    <% } %>

    <%-- Categorías solo si existen --%>
    <div id="categoriasContainer" style="display: none;">
      <h4 class="border-bottom pb-2">Categorías</h4>
      <ul id="lista" class="list-group"></ul>
    </div>
  </div>

  <script>
    // Script para ocultar el bloque si no hay categorías
    document.addEventListener("DOMContentLoaded", () => {
      const lista = document.getElementById("lista");
      const contenedor = document.getElementById("categoriasContainer");
      
      // Suponiendo que categorias.js las carga dinámicamente:
      // Esperamos un poquito y si sigue vacío, no mostramos el bloque.
      setTimeout(() => {
        if (!lista || lista.children.length === 0) {
          contenedor.style.display = "none";
        } else {
          contenedor.style.display = "block";
        }
      }, 500);
    });
  </script>
</body>
</html>
