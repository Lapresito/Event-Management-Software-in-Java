const selectEventos = document.getElementById("EventosDisponibles");
const selectEdiciones = document.getElementById("edicionesOrganizadas");
const selectTiposDeRegistro = document.getElementById("tiposDeRegistroDeEdicion");
const selectInstituciones = document.getElementById("institucion");
const selectNivel = document.getElementById("nivel");
const inputMonto = document.getElementById("monto");
const inputCodigo = document.getElementById("codigo");
const inputRegistrosGratuitos = document.getElementById("registrosGratuitos");
const errorMsg = document.getElementById("errorMsg");

const mostrarMensaje =(mensaje, error)=>{
	errorMsg.textContent = mensaje;
	errorMsg.className = error? "alert-danger alert text-center" : "alert-success alert text-center"
	errorMsg.style.display = 'block'
}

async function validarCampos(){
		const numbers = /^[0-9]+$/;
		
		const evento = selectEventos.value;
		if (evento === "null") {  // null, "" o undefined
			throw new Error('No hay evento seleccionado');
			
		}

		const edicion = selectEdiciones.value;  
		if (edicion === "null") {  // null, "" o undefined
			throw new Error('No hay edición seleccionada');
		}

		const TRegistros = selectTiposDeRegistro.value;  
		if (TRegistros === "null") {  // null, "" o undefined
			throw new Error('No hay Tipo de registro seleccionado');
		}

		const intitucion = selectInstituciones.value;  
		if (intitucion === "null") {  // null, "" o undefined
			throw new Error('No hay institucion seleccionada');
		}

		const monto = inputMonto.value;
		if (monto==="") {
			throw new Error('Ingrese un aporte económico.');
		}
		if (+monto <= 0) {
			throw new Error('El aporte económico debe ser mayor a 0.');
		}
		if (!numbers.test(monto)) {
			throw new Error('Ingrese solo números enteros para el aporte económico.');
		}

		const codigo = inputCodigo.value;
		if (codigo==="") {
			throw new Error('Ingrese un código.');
		}

		const registrosGratuitos = inputRegistrosGratuitos.value;
		if (registrosGratuitos==="") {
			throw new Error('Ingrese una cantidad de registros gratuitos.');
		}
		if (+registrosGratuitos <= 0) {
			throw new Error('El cupo de registros gratuitos debe ser mayor a 0.');
		}
		if (!numbers.test(registrosGratuitos)) {
			throw new Error('Ingrese solo números enteros para el cupo de registros gratuitos.');
		}
		

}
		
async function CargarEventosDisponibles(){
	const eventos = await fetch('/webServer/datosJson?evento=all')
		.then(response => response.json())

	selectEventos.innerHTML = "";

	if(eventos.length === 0){
		selectEventos.add(new Option("No hay eventos disponibles.", null));
		selectEventos.disabled  = true;
		return
	}

	selectEventos.add(new Option("Seleccione un evento", null));

	eventos.forEach(ev => {
		selectEventos.add (new Option(ev, ev));
	});
	selectEventos.disabled  = false;
}

async function CargarEdicionesOrganizadasDeEvento(nombreEvento){
	
	selectEdiciones.innerHTML = "";
	selectTiposDeRegistro.innerHTML = "";
	
	selectTiposDeRegistro.disabled = true;
	inputRegistrosGratuitos.disabled = true;
	selectInstituciones.disabled = true
	selectNivel.disabled = true
	inputMonto.disabled = true
	inputRegistrosGratuitos.disabled = true
	
	if(nombreEvento == "null"){
		selectEdiciones.innerHTML = "";
		selectEdiciones.disabled  = true;
		return
	}

	console.log(nombreEvento)
	const ediciones = await fetch(`/webServer/datosJson?evento=${nombreEvento}&edicion=all&organizador=${org}`).then(response => response.json())


	if(ediciones.length ===0){
		selectEdiciones.add(new Option("Aún no hay ediciones organizadas por ti", null));
		selectEdiciones.disabled  = true;
		return
	}
	
	selectEdiciones.add(new Option("Seleccione una edición", null));
	
	ediciones.forEach(ed => {
		selectEdiciones.add (new Option(ed, ed));
	});

	selectEdiciones.disabled  = false;
}
		
async function CargarTiposDeRegistroDeEdicion(nombreEdicion){
	selectTiposDeRegistro.innerHTML = "";

	if(nombreEdicion == "null"){
		selectTiposDeRegistro.innerHTML = "";
		selectTiposDeRegistro.disabled  = true;
		return
	}
	
	const tipos = await fetch(`/webServer/datosJson?edicion=${nombreEdicion}&tiposRegistro=all`)
		.then(response => response.json())

	if(tipos.length ===0){
		selectTiposDeRegistro.add(new Option("Aún no hay tipos de registros para la edicion: "+nombreEdicion, null));
		selectTiposDeRegistro.disabled  = true;
		return
	}
	
	selectTiposDeRegistro.add(new Option("Seleccione un tipo de registro", null));
	
	tipos.forEach(t => {
		selectTiposDeRegistro.add (new Option(t, t));
	});
	selectTiposDeRegistro.disabled  = false;
}
		
async function CargarInstituciones(){

	selectInstituciones.innerHTML = "";
	
	const instituciones = await fetch(`/webServer/datosJson?institucion=all`)
		.then(response => response.json())

	if(instituciones.length ===0){
		selectInstituciones.add(new Option("No hay instituciones disponibles", null));
		selectInstituciones.disabled  = true;
		return
	}
	
	selectInstituciones.add(new Option("Seleccione una institucion", null));
	
	instituciones.forEach(i => {
		selectInstituciones.add (new Option(i, i));
	});
	selectInstituciones.disabled  = true;
}
		
async function altaPatrocinio() {
    try {
        await validarCampos();

        const patrocinio = {
            evento: selectEventos.value,
            edicion: selectEdiciones.value,
            tipoRegistro: selectTiposDeRegistro.value,
            institucion: selectInstituciones.value,
            nivel: selectNivel.value,
            monto: +inputMonto.value,
            codigo: inputCodigo.value,
            registrosGratuitos: +inputRegistrosGratuitos.value
        };

        const response = await fetch("/webServer/altaPatrocinio", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(patrocinio)
        });

        const data = await response.json();

        if (!response.ok) {
            mostrarMensaje(data.mensaje || "Error en el servidor", true);
            return;
        }

        mostrarMensaje(data.mensaje, false)

        selectInstituciones.selectedIndex = 0;
        selectInstituciones.disabled = true

        selectNivel.selectedIndex = 0;
        selectNivel.disabled = true

        inputMonto.value = ""
        inputMonto.disabled = true

        inputCodigo.value = ""
        inputCodigo.disabled = true

        selectTiposDeRegistro.selectedIndex = 0;

        inputRegistrosGratuitos.value = ""
        inputRegistrosGratuitos.disabled = true

    } catch (error) {
        console.log(error)
        mostrarMensaje(error.message, true)
    }
}

async function cargarEventsListeners(){
	selectEventos.addEventListener("change", async (e)=>{
		await CargarEdicionesOrganizadasDeEvento(e.target.value);
	})

	selectEdiciones.addEventListener("change", async (e)=>{
		await CargarTiposDeRegistroDeEdicion(e.target.value);

		selectInstituciones.disabled = true
		selectNivel.disabled = true
		inputMonto.disabled = true
		inputCodigo.disabled = true
		inputRegistrosGratuitos.disabled = true
	})

	selectTiposDeRegistro.addEventListener("change", (e)=>{
		if (e.target.value != "null"){
			selectInstituciones.disabled = false
			selectNivel.disabled = false
			inputMonto.disabled = false
			inputCodigo.disabled = false
			inputRegistrosGratuitos.disabled = false
		}else{
			selectInstituciones.disabled = true
			selectNivel.disabled = true
			inputMonto.disabled = true
			inputCodigo.disabled = true
			inputRegistrosGratuitos.disabled = true
		}
	})
		
	const btnAceptar = document.getElementById("Aceptar");
	btnAceptar.addEventListener( "click", async (e)=>{
		e.preventDefault();
		await altaPatrocinio();
	})

	const btnCancelar = document.getElementById("Cancelar");
	btnCancelar.addEventListener( "click", async (e)=>{
		e.preventDefault();
		window.history.back();

	})
}
		
		
cargarEventsListeners();
CargarEventosDisponibles();
CargarInstituciones();