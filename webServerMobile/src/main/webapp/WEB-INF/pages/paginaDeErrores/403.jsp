
<%@ page isErrorPage="true" contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Prohibido - 403</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        section { display: flex; justify-content: center; align-items: center; margin: 0; }
        .card-403 {
            max-width: 550px; width: 100%; border-radius: 16px; box-shadow: 0 6px 20px rgba(0,0,0,0.15);
            text-align: center; padding: 30px 20px;
            background: linear-gradient(135deg, #D6EAF8, #ffffff, #D6EAF8);
        }
        .card-403:hover { transform: scale(1.02); box-shadow: 0 10px 30px rgba(0,0,0,0.2); }
        .card-403 h1 { font-size: 6rem; margin-bottom: 15px; color: #3498DB; }
        .card-403 h3 { margin-bottom: 15px; color: #333; }
        .card-403 p { margin-bottom: 12px; color: #555; font-size: 0.95rem; }
        .btn-home { background-color: #3498DB; color: #fff; border: none; padding: 10px 25px; border-radius: 6px; transition: transform 0.2s ease, background-color 0.2s ease; }
        .btn-home:hover { background-color: #2980b9; transform: scale(1.05); }
        .info-detail { text-align: left; margin-top: 20px; font-size: 0.9rem; color: #444; }
        .info-detail span { font-weight: bold; }
    </style>
</head>
<body>
    <jsp:include page="/WEB-INF/templates/navbar.jsp"/> 
    <section class="container mt-4">
        <div class="card-403">
            <h1>403</h1>
            <h3>Prohibido</h3>
            <p>No tienes permisos para acceder a esta página.</p>
            <a href="<%=request.getContextPath()%>/home" class="btn btn-home mb-3">Volver al inicio</a>
            <div class="info-detail">
              <p><span>Mensaje de error:</span> <%= request.getAttribute("error") != null ? request.getAttribute("error") : request.getAttribute("javax.servlet.error.message") %></p>
              <p><span>Descripción:</span><%= request.getAttribute("descripcionError") != null ? request.getAttribute("descripcionError") : request.getAttribute("javax.servlet.error.message") %></p>
            </div>
        </div>
    </section>
</body>
</html>
