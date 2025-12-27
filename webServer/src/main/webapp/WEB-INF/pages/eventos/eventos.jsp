<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@ page import="clienteServidor.publicar.*" %>
<%
    String baseServer = request.getContextPath();
    List<DtEvento> eventosDTO = (List<DtEvento>) request.getAttribute("eventosDTO");
    if (eventosDTO == null) eventosDTO = Collections.emptyList();
    String placeholderImg = "/webServer/media/imagenes/placeholder.png";
    String categoria = (String) request.getAttribute("cat");
%>
<!doctype html>
<html>
<head>
    <meta charset="UTF-8"/>
    <title>Eventos</title>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        /* Contenedor principal: sidebar izquierda + contenido derecha */
        .main-container {
            display: flex;
            align-items: flex-start;
            gap: 30px;
        }

        /* Sidebar */
        .categories-sidebar {
            flex: 0 0 220px;
        }

        /* Sección derecha: título + grid */
        .events-section {
            flex: 1;
            display: flex;
            flex-direction: column;
        }

        .events-section h1 {
            text-align: center;
            margin-top: 0;
            margin-bottom: 1.5rem;
        }

        /* Grid de cards */
        .events-grid {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(220px, 1fr));
            gap: 15px;
            min-height: calc(2 * (280px));
        }

        .ev-card-img {
            width: 100%;
            height: 160px;
            object-fit: cover;
            border-top-left-radius: .25rem;
            border-top-right-radius: .25rem;
        }

        .ev-card {
    		transition: transform .08s ease-in-out;
    		height: auto;
        }

        .ev-card:hover {
            transform: translateY(-2px);
        }

        .ev-title {
            font-size: 1.05rem;
            font-weight: 600;
            margin-bottom: .25rem;
        }

        .ev-desc {
            color: #6c757d;
            font-size: .95rem;
            margin: 0;
        }

        a.text-decoration-none.text-reset {
            display: block;
            height: 100%;
        }

        /* Responsive: sidebar arriba en pantallas chicas */
        @media (max-width: 768px) {
            .main-container {
                flex-direction: column;
            }
            .categories-sidebar {
                width: 100%;
                margin-bottom: 20px;
            }
        }
    </style>
</head>
<body>
    <jsp:include page="/WEB-INF/templates/navbar.jsp"/>

    <div class="container my-4 main-container">

        <!-- Sidebar izquierda -->
        <div class="categories-sidebar">
            <jsp:include page="/WEB-INF/templates/categorias.jsp"/>
        </div>

        <!-- Parte derecha -->
        <div class="events-section">
            <h1 class="text-center">
                Eventos <%= categoria == "" ? "" : "de " + categoria %>
            </h1>

            <div class="events-grid" id="eventsContainer">
                <% if (eventosDTO.isEmpty()) { %>
                    <div class="col-12 w-100">
                        <div class="alert alert-info mb-0">No hay eventos para mostrar.</div>
                    </div>
                <% } else {
                    for (Object eo : eventosDTO) {
                        String nombre = "—";
                        try {
                            Object v = eo.getClass().getMethod("getNombre").invoke(eo);
                            if (v != null && !v.toString().trim().isEmpty()) nombre = v.toString();
                        } catch (Exception ignore) {}

                        String detalleHref = baseServer + "/eventos?nombre=" 
                            + java.net.URLEncoder.encode(nombre, java.nio.charset.StandardCharsets.UTF_8.toString());

                        String img = placeholderImg;
                        try {
                            Object v = eo.getClass().getMethod("getImg").invoke(eo);
                            if (v != null && !v.toString().trim().isEmpty()) img = v.toString();
                        } catch (Exception ignore) {}

                        String desc = "—";
                        try {
                            Object v = eo.getClass().getMethod("getDescripcion").invoke(eo);
                            if (v != null && !v.toString().trim().isEmpty()) desc = v.toString();
                        } catch (Exception ignore) {}
                %>
                    <a href="<%= detalleHref %>" class="text-decoration-none text-reset">
                        <div class="card ev-card shadow-sm">
                            <img class="ev-card-img" src="<%= img %>" alt="Imagen de <%= nombre %>">
                            <div class="card-body" style="height: 100px">
                                <div class="ev-title"><%= nombre %></div>
                                <p class="ev-desc"><%= desc %></p>
                            </div>
                        </div>
                    </a>
                <% } } %>
            </div>
            <div id="pagination" class="d-flex justify-content-center mt-4"></div>
        </div>
    </div>
    <script>
document.addEventListener("DOMContentLoaded", function () {
	const cards = document.querySelectorAll("#eventsContainer > a");
    const cardsPerPage = 8;
    let currentPage = 1;
    const totalPages = Math.ceil(cards.length / cardsPerPage);
    const paginationContainer = document.getElementById("pagination");

    function showPage(page) {
    	cards.forEach(card => card.style.display = "none");

    	const start = (page - 1) * cardsPerPage;
    	const end = start + cardsPerPage;

    	for (let i = start; i < end && i < cards.length; i++) {
    	    cards[i].style.display = "block";
    	}
        const buttons = paginationContainer.querySelectorAll("button.page-btn");
        buttons.forEach((btn, index) => {
            btn.classList.toggle("btn-primary", index + 1 === page);
            btn.classList.toggle("btn-outline-primary", index + 1 !== page);
        });
    }

    function renderPagination() {
        paginationContainer.innerHTML = "";

        const prevBtn = document.createElement("button");
        prevBtn.textContent = "Anterior";
        prevBtn.className = "btn btn-outline-secondary mx-1";
        prevBtn.disabled = currentPage === 1;
        prevBtn.onclick = () => { if (currentPage > 1) { currentPage--; showPage(currentPage); renderPagination(); } };
        paginationContainer.appendChild(prevBtn);

        for (let i = 1; i <= totalPages; i++) {
            const btn = document.createElement("button");
            btn.textContent = i;
            btn.className = "btn page-btn mx-1 " + (i === currentPage ? "btn-primary" : "btn-outline-primary");
            btn.onclick = () => { currentPage = i; showPage(currentPage); renderPagination(); };
            paginationContainer.appendChild(btn);
        }

        const nextBtn = document.createElement("button");
        nextBtn.textContent = "Siguiente";
        nextBtn.className = "btn btn-outline-secondary mx-1";
        nextBtn.disabled = currentPage === totalPages;
        nextBtn.onclick = () => { if (currentPage < totalPages) { currentPage++; showPage(currentPage); renderPagination(); } };
        paginationContainer.appendChild(nextBtn);
    }

    if (cards.length > 0) {
        showPage(currentPage);
        renderPagination();
    }
});
</script>

</body>
</html>
