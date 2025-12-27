<%@page contentType="text/html" pageEncoding="UTF-8"%> 
<%@page import="java.util.List"%>
<%@ page import="clienteServidor.publicar.*" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <title>Usuarios</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" />
    <style>
        .main-container {
            display: flex;
            align-items: flex-start;
            gap: 30px;
            margin-top: 0;
        }

        .categories-sidebar {
            flex: 0 0 220px;
        }

        .users-section {
            flex: 1;
            display: flex;
            flex-direction: column;
        }

        h1 {
            text-align: center;
            margin-bottom: 1.5rem;
        }

        #error-container {
            display: flex;
            justify-content: center;
            align-items: center;
            text-align: center;
            width: auto;
        }

        .users-wrapper {
            display: flex;
            flex-direction: column;
            justify-content: space-between;
            min-height: 600px;
        }

        #usersContainer {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(180px, 1fr));
            gap: 1.5rem;
            justify-items: center;
            max-width: calc((220px + 1.5rem) * 4);
            margin: 0 auto;
        }

        #usersContainer.error-mode {
            display: flex;
            justify-content: center;
        }

        #usersContainer .card {
            width: 100%;
            max-width: 220px;
            cursor: pointer;
            transition: transform 0.2s ease, box-shadow 0.2s ease;
        }

        #usersContainer .card:hover {
            transform: scale(1.03);
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.15);
        }

        #usersContainer img {
            height: clamp(180px, 12.5vw, 280px);
            width: 100%;
            object-fit: cover;
            border-top-left-radius: 0.25rem;
            border-top-right-radius: 0.25rem;
        }

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
<jsp:include page="/WEB-INF/templates/navbar.jsp" />

<div class="container main-container">
    <div class="categories-sidebar">
        <jsp:include page="/WEB-INF/templates/categorias.jsp" />
    </div>

    <div class="users-section">
        <h1>Usuarios</h1>

        <div class="users-wrapper">
            <%
                DtUsuario usuarioLogueado = (DtUsuario) session.getAttribute("usuario_logueado");
                boolean logueado = (usuarioLogueado != null);

                IUsuariosControllerWebService sistemaUsuarios = (IUsuariosControllerWebService) request.getAttribute("sistemaUsers");

                DtUsuario[] usuarios = (DtUsuario[]) request.getAttribute("usuarios");
                String error = (String) request.getAttribute("error");
                boolean sinUsuarios = usuarios == null || usuarios.length == 0;
            %>

            <div id="usersContainer" class="<%= sinUsuarios ? "error-mode" : "" %>">
                <% if (sinUsuarios) { %>
                    <div id="error-container" class="alert alert-info mb-0">
                        <%= (error != null ? error : "No hay usuarios disponibles.") %>
                    </div>
                <% } else {
                    for (DtUsuario u : usuarios) {
                        String urlDestino = (logueado && usuarioLogueado.getNickname().equals(u.getNickname()))
                            ? request.getContextPath() + "/perfil"
                            : request.getContextPath() + "/usuarios?nickname=" + u.getNickname();

                        String imgSrc = (u.getImg() != null && !u.getImg().isEmpty()) 
                            ? u.getImg() 
                            : request.getContextPath() + "/media/images/defaultUser.png";
                %>
                
                <div class="card shadow-sm" onclick="window.location.href='<%= urlDestino %>';">
                    <img src="<%= imgSrc %>" alt="Imagen de <%= u.getNombre() %>" class="card-img-top" />
                    <div class="card-body p-2 d-flex flex-column justify-content-center align-items-center">
                        <h6 class="card-title mb-1"><%= u.getNickname() %></h6>
                        <% if (logueado && !usuarioLogueado.getNickname().equals(u.getNickname())) {
                            boolean loSigue = false;
                            try {
                                List<DtUsuario> seguidos = sistemaUsuarios.getSeguidosDeUsuario(usuarioLogueado.getNickname()).getItem();
                                if (seguidos != null) loSigue = seguidos.stream().anyMatch(s -> s.getNickname().equals(u.getNickname()));
                            } catch(Exception e){ }
                        %>
                        <form action="<%= request.getContextPath() %>/usuarios" method="post">
                            <input type="hidden" name="accion" value="<%= loSigue ? "dejarDeSeguir" : "seguir" %>">
                            <input type="hidden" name="nickname" value="<%= u.getNickname() %>">
                            <input type="hidden" name="redirectUrl" value="<%= request.getContextPath() + "/usuarios" %>">
                            <button type="submit" class="btn btn-sm <%= loSigue ? "btn-danger" : "btn-primary" %>">
                                <%= loSigue ? "Dejar de seguir" : "Seguir" %>
                            </button>
                        </form>
                        <% } %>
                    </div>
                </div>

                <% } } %>
            </div>
        </div>

        <!-- Paginación fija -->
        <div id="pagination" class="d-flex justify-content-center mt-4"></div>
    </div>
</div>

<script>
document.addEventListener("DOMContentLoaded", function () {
    const cards = document.querySelectorAll("#usersContainer .card");
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
