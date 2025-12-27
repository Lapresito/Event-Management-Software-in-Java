<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Alta de tipo de registro</title>
    <link
      rel="stylesheet"
      href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
    />
</head>
<body>

<!-- navbar -->
<jsp:include page="/WEB-INF/templates/navbar.jsp"/>

<div class="container mt-3">
    <h1 class="text-center mb-4">Alta De Tipo de Registro</h1>

    <% if (request.getAttribute("error") != null) { %>
        <div class="alert alert-danger text-center"><%= request.getAttribute("error") %></div>
    <% } else if (request.getAttribute("mensaje") != null) { %>
        <div class="alert alert-success text-center"><%= request.getAttribute("mensaje") %></div>
    <% } %>

    <form action="${pageContext.request.contextPath}/altaTipoRegistro" method="post">
        <fieldset enabled>
            <div class="mb-3">
                <label for="edicion" class="form-label">Seleccione una edición*</label>
                <select id="edicion" name="edicion" class="form-control" required>
                    <%
                        List<String> ediciones = (List<String>) request.getAttribute("edicionesOrganizadas");
                        String edicionSeleccionada = (String) request.getAttribute("edicionSeleccionada");
                        if (ediciones != null && !ediciones.isEmpty()) {
                            for (String e : ediciones) {
                    %>
                        <option value="<%= e %>" <%= e.equals(edicionSeleccionada) ? "selected" : "" %>>
                            <%= e %>
                        </option>
                    <%
                            }
                        } else {
                    %>
                        <option disabled selected>No hay ediciones disponibles</option>
                    <%
                        }
                    %>
                </select>
            </div>

            <div class="mb-4">
                <label for="nombreTipo" class="form-label">Nombre*</label>
                <input 
                    type="text" 
                    id="nombreTipo" 
                    name="nombreTipo" 
                    class="form-control" 
                    required 
                    placeholder="Ej.: VIP"
                    value="<%= request.getAttribute("nombreTipo") != null ? request.getAttribute("nombreTipo") : "" %>">
            </div>

            <div class="row mb-4">
                <div class="col-6">
                    <label for="costo" class="form-label">Costo*</label>
                    <input 
                        type="number" 
                        id="costo" 
                        name="costo" 
                        class="form-control" 
                        required 
                        placeholder="Ej.: 500"
                        value="<%= request.getAttribute("costo") != null ? request.getAttribute("costo") : "" %>">
                </div>

                <div class="col-6">
                    <label for="cupo" class="form-label">Cupo*</label>
                    <input 
                        type="number" 
                        id="cupo" 
                        name="cupo" 
                        class="form-control" 
                        required 
                        placeholder="Ej.: 650"
                        value="<%= request.getAttribute("cupo") != null ? request.getAttribute("cupo") : "" %>">
                </div>
            </div>

            <div class="mb-4">
                <label for="descTipo" class="form-label">Descripción*</label>
                <textarea 
                    id="descTipo" 
                    name="descTipo" 
                    rows="6" 
                    class="form-control" 
                    required><%= request.getAttribute("descTipo") != null ? request.getAttribute("descTipo") : "" %></textarea>
            </div>

            <div class="row mb-3 justify-content-end">
                <div class="col-2">
                    <button type="submit" id="Aceptar" class="btn btn-primary w-100">Aceptar</button>
                </div>
                <div class="col-2">
                    <a href="${pageContext.request.contextPath}/home" class="btn btn-secondary w-100">Cancelar</a>
                </div>
            </div>
        </fieldset>
    </form>
</div>

</body>
</html>
