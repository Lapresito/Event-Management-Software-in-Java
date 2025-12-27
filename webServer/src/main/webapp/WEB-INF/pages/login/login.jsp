<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.EstadoSesion" %>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Login</title>
  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body class="bg-light">

  <!-- navbar -->
  <jsp:include page="/WEB-INF/templates/navbar.jsp"/>

  <!-- Contenido principal -->
  <main class="d-flex align-items-center justify-content-center vh-100">
    <div class="card shadow-sm" style="max-width: 400px; width: 100%;">
      <div class="card-body">
        <h4 class="card-title mb-4 text-center">Iniciar sesión</h4>

        <% 
           EstadoSesion estado = (EstadoSesion) session.getAttribute("estado_sesion");
           if (estado != null && estado == EstadoSesion.LOGIN_INCORRECTO) { 
        %>
           <div class="alert alert-danger text-center">
              Usuario o contraseña incorrectos.
           </div>
        <% } %>

        <% 
           // Mostrar aviso si ya hay un usuario logueado
           Object usuario = session.getAttribute("usuario_logueado");
           if (usuario != null) { 
        %>
           <div class="alert alert-info text-center">
              Usted ya está logueado. Ir a <a href="perfil.jsp">Perfil</a>.
           </div>
        <% } else { %>

        <form action="${pageContext.request.contextPath}/iniciar-sesion" method="post">
          <div class="form-group">
            <label for="login">Correo o nickname</label>
            <input  class="form-control" id="login" name="login"
                   placeholder="tucorreo@ejemplo.com" required>
          </div>

          <div class="form-group">
            <label for="password">Contraseña</label>
            <input type="password" class="form-control" id="password" name="password"
                   placeholder="Tu contraseña" required>
          </div>

          <button type="submit" class="btn btn-primary btn-block">Entrar</button>

          <p class="mt-2 text-center small">
            ¿No tienes cuenta? <a href="./registrar">Regístrate</a>
          </p>
        </form>

        <% } %>
      </div>
    </div>
  </main>

</body>
</html>
