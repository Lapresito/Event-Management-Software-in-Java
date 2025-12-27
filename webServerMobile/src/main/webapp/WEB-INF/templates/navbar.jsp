<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="jakarta.servlet.*" %>
<%@ page import="clienteServidor.publicar.*" %>
<%
	jakarta.servlet.http.HttpSession sesion = (jakarta.servlet.http.HttpSession) request.getSession(false);
	DtUsuario usuario = null;
	String tipo = null;
    String baseServer = "/webServerMobile";
    Object usuarioLogueado = session.getAttribute("usuario_logueado");
    boolean logueado = (usuarioLogueado != null);
    if (sesion != null) {
      usuario = (DtUsuario) sesion.getAttribute("usuario_logueado");
    }
    
%>

<nav class="navbar navbar-dark bg-dark">
  <div class="container d-flex justify-content-between align-items-center">

    <!-- Menu hamburguesa -->
    <button class="navbar-toggler" type="button" data-toggle="collapse"
            data-target="#navbarContent" aria-controls="navbarContent"
            aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>

    <!-- Brand centrado -->
    <a class="navbar-brand mx-auto" href="<%= baseServer %>/home">
      eventos.uy
    </a>

    <!-- Icono usuario -->
    <a class="nav-link text-light p-2"
       href="<%= logueado ? baseServer + "/perfil" : baseServer + "/iniciar-sesion" %>">
		<svg xmlns="http://www.w3.org/2000/svg" width="26" height="26" fill="white" viewBox="0 0 16 16">
		  <path d="M11 6a3 3 0 1 1-6 0 3 3 0 0 1 6 0"/>
		  <path d="M2 0a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2zm12 1a1 1 0 0 1 1 1v12a1 1 0 0 1-1 1v-1c0-1-1-4-6-4s-6 3-6 4v1a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1z"/>
		</svg>
    </a>
  </div>

  <!-- Contenido del menú hamburguesa -->
  <div class="collapse navbar-collapse" id="navbarContent">
    <ul class="navbar-nav mt-2">

      <% if (logueado) { %>
        <li class="nav-item">
          <a class="nav-link" href="<%= baseServer %>/registros?nickname=<%= usuario.getNickname() %>">Consulta de Registro</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="<%= baseServer %>/eventos">Consulta de Edición</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="<%= baseServer %>/registros?nickname=<%= usuario.getNickname() %>&asistidos=true">Asistencia</a>
        </li>
      <% } else { %>
        <li class="nav-item">
          <a class="nav-link" href="<%= baseServer %>/iniciar-sesion">Iniciar sesión</a>
        </li>
      <% } %>
    </ul>
  </div>
</nav>