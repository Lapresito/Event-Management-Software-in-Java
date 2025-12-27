<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="jakarta.servlet.*" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/media/styles/altaRegistro.css">
<title>Alta Registro</title>
</head>
<body>
	<jsp:include page="/WEB-INF/templates/navbar.jsp"/> 
	<h2 class="text-center">Alta de Registro</h2>
	
	<% if (request.getAttribute("error") != null) { %>
    <div class="alert alert-danger text-center" role="alert">
        <%= request.getAttribute("error") %>
    </div>
<% } %>

	<div class="container formContainer">
		<form method="post" action="${pageContext.request.contextPath}/altaRegistro" id="form">
	 		<div class="d-flex flex-column">
	 			<label for="eventos">Seleccioná un evento</label>
				<select name="eventos" id="eventos" class="form-control">
  				<option value="">-</option>
  				<%
    				String eventoSeleccionado = (String) request.getAttribute("eventoSeleccionado");
	 				String edicionSeleccionado = (String) request.getAttribute("edicionSeleccionado");
    				List<String> eventos = (List<String>) request.getAttribute("eventos");
    				for (String ev : eventos) {
        			String selected = ev.equals(eventoSeleccionado) ? "selected" : "";
  				%>
        			<option value="<%= ev %>" <%= selected %>><%= ev %></option>
 				 <%
    			}
  				%>
				</select>
				
				<label for="ediciones" class="mt-3">Seleccioná una edición</label>
                <select class="form-control" name="ediciones" id="ediciones">
                    <option value="">-</option>
                    
                </select>
                
                <div id="detalleEdicion" class="mt-4" style="display:none;">
    				<h4>Detalles de la edición</h4>
    				<div id="infoEdicion"></div>

    				<h5 class="mt-3">Tipos de registro disponibles</h5>
    				<div id="tiposRegistro"></div>
    				<h5 class="mt-3">Forma de registro</h5>
    				<div id="formaRegistro"></div>
				</div>
	 			<button type="submit" class="btn btn-primary mt-3">Registrarse a edición</button>
	 		</div>
	 	</form>
	</div>
	
<script>
const eventosSelect = document.getElementById("eventos");
const edicionesSelect = document.getElementById("ediciones");
const detalleEdicion = document.getElementById("detalleEdicion");
const infoEdicion = document.getElementById("infoEdicion");
const tiposRegistro = document.getElementById("tiposRegistro");
const formaRegistroContainer = document.getElementById("formaRegistro");

eventosSelect.addEventListener("change", function() {
    const evento = this.value;
    edicionesSelect.innerHTML = '<option value="">Cargando...</option>';

    if (evento === "") {
        edicionesSelect.innerHTML = '<option value="">Seleccione un evento</option>';
        return;
    }

	    fetch('getEdicionesParaRegistro?evento=' + encodeURIComponent(evento))
	    .then(response => response.json())
	    .then(data => {
	        edicionesSelect.innerHTML = ''; // limpiamos
	
	        if (data.length === 0) {
	            // Sin ediciones disponibles
	            const option = document.createElement("option");
	            option.value = "";
	            option.textContent = "No hay ediciones activas";
	            option.disabled = true;
	            option.selected = true;
	            edicionesSelect.appendChild(option);
	
	            detalleEdicion.style.display = "none"; // ocultamos detalle por si acaso
	            return;
	        }
	
	        // Si hay ediciones, mostramos el selector normal
	        edicionesSelect.innerHTML = '<option value="">Seleccione una edición</option>';
	        data.forEach(ed => {
	            const option = document.createElement("option");
	            option.value = ed;
	            option.textContent = ed;
	            edicionesSelect.appendChild(option);
	        });
	    })
        .catch(err => {
            console.error("Error al obtener las ediciones:", err);
            edicionesSelect.innerHTML = '<option value="">Error al cargar</option>';
        });
});

edicionesSelect.addEventListener("change", function() {
    const edicion = this.value;

    if (!edicion) {
        detalleEdicion.style.display = "none";
        return;
    }

    fetch('getDetalleEdicionParaRegistro?edicion=' + encodeURIComponent(edicion))
        .then(response => response.json())
        .then(data => {
            detalleEdicion.style.display = "block";

            infoEdicion.innerHTML =
                "<p style='margin-bottom:5px'><strong>Nombre de la edición:</strong> " + data.nombreEdicion + "</p>" +
                "<p style='margin-bottom:5px'><strong>Evento:</strong> " + data.nombreEvento + "</p>" +
                "<p style='margin-bottom:5px'><strong>Organizador:</strong> " + data.organizador + "</p>" +
                "<p style='margin-bottom:5px'><strong>Sigla:</strong> " + data.sigla + "</p>" +
                "<p style='margin-bottom:5px'><strong>Ciudad:</strong> " + data.ciudad + "</p>" +
                "<p style='margin-bottom:5px'><strong>País:</strong> " + data.pais + "</p>" +
                "<p style='margin-bottom:5px'><strong>Fecha de alta:</strong> " + data.fechaAlta + "</p>" +
                "<p style='margin-bottom:5px'><strong>Fecha de inicio:</strong> " + data.fechaIni + "</p>" +
                "<p style='margin-bottom:5px'><strong>Fecha de fin:</strong> " + data.fechaFin + "</p>" +
                "<p style='margin-bottom:5px'><strong>Estado:</strong> " + data.estado + "</p>";

            fetch('getTipoRegistroParaRegistro?edicion=' + encodeURIComponent(edicion))
                .then(res => res.json())
                .then(tipos => {
                    tiposRegistro.innerHTML = "";

                    if (tipos.length === 0) {
                        tiposRegistro.innerHTML = `<p class="text-muted">No hay tipos de registro disponibles</p>`;
                    } else {
                        tipos.forEach((tr, index) => {
                            const label = document.createElement("label");
                            label.style.display = "block";
                            label.style.marginBottom = "5px";

                            const radio = document.createElement("input");
                            radio.type = "radio";
                            radio.name = "tipoRegistro";
                            radio.value = tr;

                            if (index === 0) {
                                radio.checked = true;
                            }

                            label.appendChild(radio);
                            label.appendChild(document.createTextNode(" " + tr));

                            tiposRegistro.appendChild(label);
                        });
                    }

                    formaRegistroContainer.innerHTML = `
    					<label style="display:block; margin-bottom:5px">
        					<input type="radio" name="formaRegistro" value="general" checked> Registro general
    					</label>
    					<label style="display:block; margin-bottom:5px">
        					<input type="radio" name="formaRegistro" value="codigo"> Código de patrocinio
    					</label>
    					<div id="codigoInputContainer" style="display:none; margin-top:5px;">
        					<input type="text" id="codigoPatrocinio" class="form-control" placeholder="Ingresá tu código" name="codigoPatrocinio">
    					</div>
					`;

                    const radiosForma = document.getElementsByName("formaRegistro");
                    const codigoContainer = document.getElementById("codigoInputContainer");

                    radiosForma.forEach(radio => {
                        radio.addEventListener("change", () => {
                            if (radio.value === "codigo" && radio.checked) {
                                codigoContainer.style.display = "block";
                            } else {
                                codigoContainer.style.display = "none";
                            }
                        });
                    });
                })
                .catch(err => {
                    console.error("Error al obtener tipos de registro:", err);
                    tiposRegistro.innerHTML = `<p class="text-muted">Error al cargar tipos de registro</p>`;
                });
        })
        .catch(err => {
            console.error("Error al obtener detalle de la edición:", err);
            detalleEdicion.style.display = "none";
        });
});
</script>

<script>
const form = document.getElementById("form");

form.addEventListener("submit", function(event) {
    const eventoSeleccionado = eventosSelect.value;
    const edicionSeleccionada = edicionesSelect.value;

    let mensajesError = [];

    if (!eventoSeleccionado) {
        mensajesError.push("Por favor seleccioná un evento.");
    }

    if (!edicionSeleccionada) {
        mensajesError.push("Por favor seleccioná una edición.");
    }   

    if (mensajesError.length > 0) {
        event.preventDefault();
        alert(mensajesError.join("\n"));
    }
});
</script>

<script>
window.addEventListener("DOMContentLoaded", () => {
    // Traemos los valores desde JSP
    const eventoSel = '<%= request.getAttribute("eventoSeleccionado") != null ? request.getAttribute("eventoSeleccionado") : "" %>';
    const edicionSel = '<%= request.getAttribute("edicionSeleccionada") != null ? request.getAttribute("edicionSeleccionada") : "" %>';
    const tipoSel = '<%= request.getAttribute("tipoRegistroSeleccionado") != null ? request.getAttribute("tipoRegistroSeleccionado") : "" %>';
    const codigoIngresado = '<%= request.getAttribute("codigoIngresado") != null ? request.getAttribute("codigoIngresado") : "" %>';

    const eventosSelect = document.getElementById("eventos");
    const edicionesSelect = document.getElementById("ediciones");

    // Selecciona evento si hay uno guardado
    if (eventoSel && eventosSelect) {
        eventosSelect.value = eventoSel;
        eventosSelect.dispatchEvent(new Event("change"));

        // Espera a que las ediciones se carguen (usa un pequeño delay)
        const waitForEdiciones = setInterval(() => {
            if (edicionesSelect && edicionesSelect.options.length > 1) { // asume que se cargaron las opciones
                if (edicionSel) {
                    edicionesSelect.value = edicionSel;
                    edicionesSelect.dispatchEvent(new Event("change"));
                }
                clearInterval(waitForEdiciones);
            }
        }, 100); // revisa cada 100ms
    }

    // Selecciona el modo "código" si se ingresó un código
    if (codigoIngresado) {
        const radioCodigo = document.querySelector('input[name="formaRegistro"][value="codigo"]');
        const codigoInputContainer = document.getElementById("codigoInputContainer");
        const codigoInput = document.getElementById("codigoPatrocinio");

        if (radioCodigo) radioCodigo.checked = true;
        if (codigoInputContainer) codigoInputContainer.style.display = "block";
        if (codigoInput) codigoInput.value = codigoIngresado;
    }
});
</script>

</body>
</html>