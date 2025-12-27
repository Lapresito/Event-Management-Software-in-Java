package logica;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * javadoc commet.
 */
public class Edicion {
	private Map<String, TipoRegistro> tiposRegistro = new HashMap<>();
	private Map<String, Patrocinio> patrocinios = new HashMap<>();
	private Map<String, Patrocinio> patrociniosPorCodigo = new HashMap<>();
	private Map<String, Registro> registros = new HashMap<>(); // clave es el nick del asistente
	private Evento evento;
	private Organizador organizador;
	private String nombre;
	private String sigla;
	private LocalDate fechaIni;
	private LocalDate fechaFin;
	private LocalDate fechaAlta;
	private String ciudad;
	private String pais;
	private EstadoEdicion estado;
	private String img;
	private String videoURL;

	// Constructor
	/**
	 * javadoc commet.
	 */
	public Edicion(String nombreEdicion, Evento evento, Organizador org, String sigla, LocalDate fechaIni,
			LocalDate fechaFin, String ciudad, String pais, LocalDate fechaAlta, String img) {
		this.nombre = nombreEdicion;
		this.evento = evento;
		this.organizador = org;
		this.sigla = sigla;
		this.fechaIni = fechaIni;
		this.fechaFin = fechaFin;
		this.ciudad = ciudad;
		this.pais = pais;
		this.fechaAlta = (fechaAlta != null) ? fechaAlta : LocalDate.now();
		this.estado = EstadoEdicion.INGRESADA;
		this.setImg(img);
		this.setVideoURL("");
	}

	/**
	 * javadoc commet.
	 */
	public void crearTipoRegistro(String nombreTipo, String descripcion, int costo, int cupo) throws IOException {
		if (tiposRegistro.containsKey(nombreTipo)) {
			throw new IOException("Ya existe un Tipo de Registro con este nombre");
		}
		TipoRegistro tipoRegistro = new TipoRegistro(nombreTipo, descripcion, costo, cupo, this);
		tiposRegistro.put(nombreTipo, tipoRegistro);

	}

	public DTEdicion getDataEdicion() {
		return new DTEdicion(this);
	}

	// Getters y setters
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nuevoNombre) {
		this.nombre = nuevoNombre;
	}

	public Evento getEvento() {
		return evento;
	}

	public Organizador getOrganizador() {
		return organizador;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String nuevaSigla) {
		this.sigla = nuevaSigla;
	}

	public LocalDate getFechaIni() {
		return fechaIni;
	}

	public void setFechaIni(LocalDate nuevaFechaIni) {
		this.fechaIni = nuevaFechaIni;
	}

	public LocalDate getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(LocalDate nuevaFechaFin) {
		this.fechaFin = nuevaFechaFin;
	}

	public LocalDate getFechaAlta() {
		return fechaAlta;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String nuevaCiudad) {
		this.ciudad = nuevaCiudad;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String nuevoPais) {
		this.pais = nuevoPais;
	}

	public TipoRegistro getTipoRegistro(String nombre) {
		return tiposRegistro.get(nombre);
	}

	public Map<String, TipoRegistro> getTiposRegistro() {
		return tiposRegistro;
	}

	public void setRegistro(TipoRegistro nuevoRegistro, String asistente) {
		tiposRegistro.put(asistente, nuevoRegistro);
	}

	public Patrocinio getPatrocinio(String institucion) {
		return patrocinios.get(institucion);
	}

	public Map<String, Patrocinio> getPatrocinios() {
		return patrocinios;
	}

	public void setPatrocinio(Patrocinio nuevoPatrocinio, String institucion) {
		patrocinios.put(institucion, nuevoPatrocinio);
	}

	public String getNombreEvento() {
		return evento.getNombre();
	}

	public Map<String, Registro> getRegistros() {
		return registros;
	}

	public boolean estaRegistrado(Asistente asistente) {
		return registros.containsKey(asistente.getNickname());
	}

	public void crearRegistro(Asistente asistente, String nombreTipoRegistro, LocalDate fechaAlta) throws IOException {
		TipoRegistro tipoRegistro = tiposRegistro.get(nombreTipoRegistro);
		if (tipoRegistro == null) {
			throw new IOException("No existe TipoRegistro");
		}

		if (estaRegistrado(asistente)) {
			throw new IOException("El usuario ya esta registrado.");
		}

		if (!tipoRegistro.tieneCupoDisponible()) {
			throw new IOException("No quedan cupos disponibles");
		}

		Registro nuevoRegistro = new Registro(this, fechaAlta, tipoRegistro.getCosto());
		nuevoRegistro.asociarTipoRegistro(tipoRegistro);
		nuevoRegistro.asociarAsistente(asistente);
		registros.put(asistente.getNickname(), nuevoRegistro);
		asistente.asociarRegistro(this.getNombre(), nuevoRegistro);
		tipoRegistro.decrementarCupo();

	}

	public void crearPatrocinio(Institucion institucion, TipoRegistro tipoRegistro, NivelPatrocinio nivel, int monto,
			int cupo, LocalDate fechaAlta, String codigo) throws IllegalArgumentException {

		if (patrociniosPorCodigo.containsKey(codigo)) {
			throw new IllegalArgumentException("Ya existe Patrocinio con ese codigo para esta edicion");
		}

		String nombreInstitucion = institucion.getNombre();
		if (patrocinios.containsKey(nombreInstitucion)) {
			throw new IllegalArgumentException("Ya existe Patrocinio para esta institución en esta edición");
		}

		Patrocinio patrocinio = new Patrocinio(institucion, this, tipoRegistro, nivel, monto, cupo, fechaAlta, codigo);

		patrocinios.put(nombreInstitucion, patrocinio);
		patrociniosPorCodigo.put(codigo, patrocinio);

		tipoRegistro.agregarPatrocinio(patrocinio);
		institucion.agregarPatrocinio(patrocinio);
	}

	public DTPatrocinio getDTPatrocinio(String codigoPatrocinio, String nombreInstitucion) {
		Patrocinio patrocinio = patrocinios.get(codigoPatrocinio);
		DTPatrocinio res = new DTPatrocinio(patrocinio);
		return res;
	}

	public Patrocinio getPatrocinioPorCodigo(String codigo) {
		Patrocinio patrocinio = patrociniosPorCodigo.get(codigo);
		return patrocinio;
	}

	public Map<String, Patrocinio> getPatrociniosPorCodigo() {
		return this.patrociniosPorCodigo;
	}

	public EstadoEdicion getEstado() {
		return estado;
	}

	public void setEstado(EstadoEdicion estado) {
		this.estado = estado;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public void crearRegistroConCodigo(Asistente asistente, String nombreTipoRegistro, String codigo,
			LocalDate fechaAlta) throws IOException {

		TipoRegistro tipoRegistro = tiposRegistro.get(nombreTipoRegistro);
		if (tipoRegistro == null) {
			throw new IOException("No existe TipoRegistro");
		}

		if (estaRegistrado(asistente)) {
			throw new IOException("El usuario ya esta registrado.");
		}

		Patrocinio patrocinio = this.getPatrocinioPorCodigo(codigo);

		if (!patrocinio.quedanCuposDisponibles()) {
			throw new IOException("No quedan cupos disponibles");
		}
		patrocinio.sumarRegistro();

		Registro nuevoRegistro = new Registro(this, fechaAlta, 0);

		nuevoRegistro.asociarAsistente(asistente);
		asistente.asociarRegistro(this.getNombre(), nuevoRegistro);

		nuevoRegistro.asociarTipoRegistro(tipoRegistro);
		patrocinio.asociarRegistro(nuevoRegistro);

		registros.put(asistente.getNickname(), nuevoRegistro);
	}

	public String getVideoURL() {
		return videoURL;
	}

	public void setVideoURL(String videoURL) {
		this.videoURL = videoURL;
	};
}
