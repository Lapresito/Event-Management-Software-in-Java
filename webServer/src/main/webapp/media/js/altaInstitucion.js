const inputInstitucion = document.getElementById("nombre");
const inputDescripcion = document.getElementById("descripcion");
const inputSitioWeb = document.getElementById("sitioWeb");
const inputIMG = document.getElementById("img");
const preview = document.getElementById("preview");
const errorMsg = document.getElementById("errorMsg");

async function validarCampos(){

		const institucion = inputInstitucion.value;
		if (institucion==="") {
			errorMsg.textContent = 'Ingrese un nombre para la institución.'
			errorMsg.className = "alert-danger alert text-center"
			errorMsg.style.display = 'block'
			return;
		}

		const descripcion = inputDescripcion.value;
		if (descripcion==="") {
			errorMsg.textContent = 'Ingrese una descripción.'
			errorMsg.className = "alert-danger alert text-center"
			errorMsg.style.display = 'block'
			return;
		}

		const web = inputSitioWeb.value;
        function esWebRegex(url) {
            const pattern = /^(https?:\/\/)?([a-zA-Z0-9-]+\.)+[a-zA-Z]{2,}(:\d+)?(\/.*)?$/;
            return pattern.test(url);
        }
		if (esWebRegex(web)) {
			if (descripcion==="") {
				errorMsg.style.display = 'block'
				errorMsg.className = "alert-danger alert text-center"
				errorMsg.textContent = 'Ingrese una web valida.'
				return;
			}
		}
}


async function altaInstitucion(){
    
	try{
    	const institucion = {
			nombre: inputInstitucion.value,
			descripcion: inputDescripcion.value,
			web: inputSitioWeb.value,
			img: inputIMG.value,
		}

        const formData = new FormData();
        formData.append("institucionData", JSON.stringify(institucion));
        formData.append("institucionImg", inputIMG.files[0]); // archivo
		console.log(inputIMG.files[0]);
        
        const response = await fetch("/webServer/altaInstitucion", {
            method: "POST",
            body: formData
        });
		
		console.log(response);
        if (response.ok) {
			const respuesta = await response.json();
			errorMsg.textContent = respuesta.mensaje;	
			errorMsg.className = "alert-success alert text-center"		
			errorMsg.style.display = 'block'
            inputInstitucion.value = "";
            inputDescripcion.value = "";
            inputSitioWeb.value = "";
            inputIMG.value = "";
			preview.src = "/webServer/media/imagenes/placeholderEvento-Edicion.png"; 
			return;
		} else {
            const errorData = await response.json();
			errorMsg.textContent = errorData.mensaje;
			errorMsg.className = "alert-danger alert text-center"
			errorMsg.style.display = 'block'
            return
		}

	} catch (error) {
		alert(error.message)
	}

}

inputIMG.addEventListener("change", (e) => {
    const file = e.target.files[0];
    if (!file) return;

    const reader = new FileReader();
    reader.onload = function(event) {
        preview.src = event.target.result; // muestra la imagen en el <img>
    };
    reader.readAsDataURL(file);
});

const btnAceptar = document.getElementById("Aceptar");
btnAceptar.addEventListener( "click", async (e)=>{
    e.preventDefault();
    await altaInstitucion();
})

const btnCancelar = document.getElementById("Cancelar");
btnCancelar.addEventListener( "click", async (e)=>{
    e.preventDefault();
    window.history.back();

})



