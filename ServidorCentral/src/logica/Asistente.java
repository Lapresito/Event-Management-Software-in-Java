package logica;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * javadoc commet.
 */
public class Asistente extends Usuario {
	private String apellido;
	private LocalDate nacimiento;

	private Institucion institucion;
	private Map<String, Registro> registros; // clave: nombre de la edicion

	/**
	 * javadoc commet.
	 */
	public Asistente(String nickname, String email, String nombre, String apellido, LocalDate nacimiento, String img,
			String password) {
		super(nickname, email, nombre, img, password); // llama al constructor de Usuario
		this.apellido = apellido;
		this.nacimiento = nacimiento;
		this.institucion = null;
		this.registros = new HashMap<>();

	}

	public DTAsistente getDTAsistente() {

		String nombreInstitucion = institucion != null ? institucion.getNombre() : "";
		return new DTAsistente(this.getNickname(), this.getEmail(), this.getNombre(), apellido, nacimiento,
				nombreInstitucion, registros.keySet().toArray(new String[0]), this.getImg(), new ArrayList<DTUsuario>(),
				new ArrayList<DTUsuario>());
	}

	// Getters y Setters
	public String getApellido() {
		return apellido;
	}

	public LocalDate getNacimiento() {
		return nacimiento;
	}

	public void setApellido(String apellidoParam) {
		apellido = apellidoParam;
	}

	public void setNacimiento(LocalDate nac) {
		nacimiento = nac;
	}

	public Institucion getInstitucion() {
		return institucion;
	}

	public void setInstitucion(Institucion institucion) {
		this.institucion = institucion;
	}

	/**
	 * javadoc commet.
	 */
	public void asociarRegistro(String nombreEdicion, Registro reg) {
		registros.put(nombreEdicion, reg);
	}

	/**
	 * javadoc commet.
	 */
	public Registro getRegistro(String nombreEdicion) {
		return registros.get(nombreEdicion);
	}

	public Map<String, Registro> getRegistros() {
		return registros;
	}

	@Override
	public void setNombre(String nombreParam) {
		this.nombre = nombreParam;
	}

}
