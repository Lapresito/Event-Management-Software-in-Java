package logica;

import java.io.IOException;

public class InstitucionesController implements IInstitucionesController {

	public void altaInstitucion(String nombreInstitucion, String descripcion, String sitioWeb, String img)
			throws IOException {

		// --- Validaciones de campos obligatorios ---
		if (nombreInstitucion == null || nombreInstitucion.trim().isEmpty()) {
			throw new IOException("El nombre de la institución no puede estar vacío.");
		}

		if (descripcion == null || descripcion.trim().isEmpty()) {
			throw new IOException("La descripción no puede estar vacía.");
		}

		if (sitioWeb == null || sitioWeb.trim().isEmpty()) {
			throw new IOException("El sitio web no puede estar vacío.");
		}

		// --- Normalización de la imagen ---
		if (img == null || img.trim().isEmpty()) {
			img = "";
		}

		ManejadorInstituciones manejadorInstituciones = ManejadorInstituciones.getInstance();

		if (manejadorInstituciones.existeInstitucion(nombreInstitucion)) {
			// La institución ya existe
			throw new IOException("Ya existe institucion con ese nombre");
		}

		// No existe, entonces la creamos
		Institucion nuevaInstitucion = new Institucion(nombreInstitucion, descripcion, sitioWeb, img);
		manejadorInstituciones.addInstitucion(nuevaInstitucion);

	}

	public DTInstitucion[] getInstituciones() { // throws UsuarioNoExisteException
		ManejadorInstituciones manejadorInstituciones = ManejadorInstituciones.getInstance();
		Institucion[] insts = manejadorInstituciones.getInstituciones();
		if (insts != null) {
			DTInstitucion[] dtInstitucion = new DTInstitucion[insts.length];
			Institucion institucion;

			// Para separar lógica de presentación, no se deben devolver los Usuario,
			// sino los DataUsuario
			for (int i = 0; i < insts.length; i++) {
				institucion = insts[i];
				dtInstitucion[i] = new DTInstitucion(institucion.getNombre(), institucion.getDescripcion(),
						institucion.getSitioWeb(), institucion.getImg());
			}
			return dtInstitucion;
		} else {
			return null;
		}
	}

}
