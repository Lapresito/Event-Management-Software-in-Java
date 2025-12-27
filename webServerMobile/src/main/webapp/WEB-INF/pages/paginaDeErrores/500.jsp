<%@ page isErrorPage="true" contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Error interno del servidor - 500</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        section { display: flex; justify-content: center; align-items: center; margin: 0; }
        .card-500 {
            max-width: 700px; width: 100%; border-radius: 16px; box-shadow: 0 6px 20px rgba(0,0,0,0.15);
            text-align: center; padding: 30px 20px;
            background: linear-gradient(135deg, #FDEBD0, #ffffff, #FDEBD0);
        }
        .card-500 h1 { font-size: 5rem; margin-bottom: 10px; color: #E74C3C; }
        .card-500 h3 { margin-bottom: 15px; color: #333; }
        .card-500 p { margin-bottom: 12px; color: #555; font-size: 0.95rem; }
        .btn-home { background-color: #E74C3C; color: #fff; border: none; padding: 10px 25px; border-radius: 6px; transition: transform 0.2s ease, background-color 0.2s ease; }
        .btn-home:hover { background-color: #c0392b; transform: scale(1.05); }
        .info-detail { text-align: left; margin-top: 20px; font-size: 0.9rem; color: #444; }
        .info-detail span { font-weight: bold; }
        .stack-trace { background-color: #f8f8f8; border: 1px solid #ddd; padding: 10px; margin-top: 10px; font-family: monospace; font-size: 0.85rem; overflow-x: auto; }
    </style>
</head>
<body>
    <jsp:include page="/WEB-INF/templates/navbar.jsp"/> 
    <section class="container mt-4">
        <div class="card-500">
            <h1>500</h1>
            <h3>Error interno del servidor</h3>
            <p>Ocurrió un error inesperado en el servidor.</p>
            <a href="<%=request.getContextPath()%>/home" class="btn btn-home mb-3">Volver al inicio</a>
            <div class="info-detail">
              <p><span>Mensaje de error:</span> <%= request.getAttribute("error") != null ? request.getAttribute("error") : request.getAttribute("javax.servlet.error.message") %></p>
              <p><span>Descripción:</span><%= request.getAttribute("descripcionError") != null ? request.getAttribute("descripcionError") : request.getAttribute("javax.servlet.error.message") %></p>
            </div>
        </div>
    </section>
</body>
</html>
