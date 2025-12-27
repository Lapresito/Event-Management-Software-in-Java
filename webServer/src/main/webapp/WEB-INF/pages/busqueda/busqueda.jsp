<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@page import="javax.xml.datatype.XMLGregorianCalendar"%>
<%@ page import="clienteServidor.publicar.*" %>

<%
    List<DtEvento> resultadosEventos = (List<DtEvento>) request.getAttribute("resultadosEventos");
    List<DtEdicion> resultadosEdiciones = (List<DtEdicion>) request.getAttribute("resultadosEdiciones");
    String query = (String) request.getAttribute("query");

    if (resultadosEventos == null) resultadosEventos = new ArrayList<>();
    if (resultadosEdiciones == null) resultadosEdiciones = new ArrayList<>();

    // Obtener parámetro de orden
    String orden = request.getParameter("orden");
    if (orden == null) orden = "fechaDesc";

    if (orden.equals("nombreAsc")) {
        resultadosEventos.sort(
            Comparator.comparing(DtEvento::getNombre, String.CASE_INSENSITIVE_ORDER)
        );
    } else if (orden.equals("nombreDesc")) {
        resultadosEventos.sort(
            Comparator.comparing(DtEvento::getNombre, String.CASE_INSENSITIVE_ORDER).reversed()
        );
    } else { // fechaDesc por defecto
        resultadosEventos.sort((a, b) -> {
            XMLGregorianCalendar fa = a.getFechaAlta();
            XMLGregorianCalendar fb = b.getFechaAlta();

            if (fa == null && fb == null) return 0;
            if (fa == null) return 1;
            if (fb == null) return -1;

            Date da = fa.toGregorianCalendar().getTime();
            Date db = fb.toGregorianCalendar().getTime();

            return db.compareTo(da); // DESC
        });
    }

    /***************************************
     *        ORDENAR EDICIONES
     ***************************************/
    if (orden.equals("nombreAsc")) {
        resultadosEdiciones.sort(
            Comparator.comparing(DtEdicion::getNombreEdicion, String.CASE_INSENSITIVE_ORDER)
        );
    } else if (orden.equals("nombreDesc")) {
        resultadosEdiciones.sort(
            Comparator.comparing(DtEdicion::getNombreEdicion, String.CASE_INSENSITIVE_ORDER).reversed()
        );
    } else { // fechaDesc
        resultadosEdiciones.sort((a, b) -> {
            XMLGregorianCalendar fa = a.getFechaAlta();
            XMLGregorianCalendar fb = b.getFechaAlta();

            if (fa == null && fb == null) return 0;
            if (fa == null) return 1;
            if (fb == null) return -1;

            Date da = fa.toGregorianCalendar().getTime();
            Date db = fb.toGregorianCalendar().getTime();

            return db.compareTo(da); // DESC
        });
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Resultados de búsqueda</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        .main-container {
            display: flex;
            align-items: flex-start;
            gap: 30px;
        }
        .categories-sidebar {
            flex: 0 0 220px;
        }
        .search-section {
            flex: 1;
            display: flex;
            flex-direction: column;
        }
        .search-section h3 {
            text-align: center;
            margin-bottom: 1.5rem;
        }
        .results-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
            gap: 20px;
        }
        .results-column {
            background-color: #f8f9fa;
            border-radius: .5rem;
            padding: 1.25rem;
            box-shadow: 0 1px 3px rgba(0,0,0,0.1);
        }
        .results-column h4 {
            text-align: center;
            margin-bottom: 1rem;
            border-bottom: 2px solid #dee2e6;
            padding-bottom: .5rem;
        }
        .hover-card {
            display: block;
            padding: 0.75rem 1rem;
            border: 1px solid #dee2e6;
            border-radius: .5rem;
            text-decoration: none;
            color: #212529;
            background-color: #fff;
            box-shadow: 0 1px 2px rgba(0,0,0,0.05);
            transition: all .15s ease-in-out;
            margin-bottom: .6rem;
        }
        .hover-card:hover {
            background-color: #f0f8ff;
            transform: translateY(-2px);
            box-shadow: 0 2px 6px rgba(0,0,0,0.12);
            text-decoration: none;
        }
        .hover-card strong {
            font-size: 1rem;
            font-weight: 600;
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
    <jsp:include page="/WEB-INF/templates/navbar.jsp"/>

    <div class="container my-4 main-container">
        
        <div class="categories-sidebar">
            <jsp:include page="/WEB-INF/templates/categorias.jsp"/>
        </div>

        <div class="search-section">
            <%
		    String queryParam = request.getParameter("query");
		    boolean queryVacio = (queryParam == null || queryParam.trim().isEmpty());
			%>
			
			<% if (queryVacio) { %>
			    <h3>Resultado de búsqueda</h3>
			<% } else { %>
			    <h3>Resultados de búsqueda para <%= queryParam %></h3>
			<% } %>

            <div class="results-grid">

                <!-- Eventos -->
                <div class="results-column">
                    <h4>Eventos</h4>

                    <% if (resultadosEventos.isEmpty()) { %>
                        <p>No se encontraron eventos.</p>
                    <% } else { %>
                        <% for (DtEvento e : resultadosEventos) { %>
                            <a href="<%= request.getContextPath() %>/eventos?nombre=<%= java.net.URLEncoder.encode(e.getNombre(), java.nio.charset.StandardCharsets.UTF_8.toString()) %>"
                               class="hover-card">
                                <strong><%= e.getNombre() %></strong>
                            </a>
                        <% } %>
                    <% } %>
                </div>

                <!-- Ediciones -->
                <div class="results-column">
                    <h4>Ediciones</h4>

                    <% if (resultadosEdiciones.isEmpty()) { %>
                        <p>No se encontraron ediciones.</p>
                    <% } else { %>
                        <% for (DtEdicion ed : resultadosEdiciones) { %>
                            <a href="<%= request.getContextPath() %>/edicion?nombreEdicion=<%= java.net.URLEncoder.encode(ed.getNombreEdicion(), java.nio.charset.StandardCharsets.UTF_8.toString()) %>"
                               class="hover-card">
                                <strong><%= ed.getNombreEdicion() %></strong>
                            </a>
                        <% } %>
                    <% } %>
                </div>

            </div>
        </div>
    </div>
</body>
</html>
