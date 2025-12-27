package logica;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import excepciones.EventoYaExisteException;

/**
 * javadoc commet.
 */
public class EventosController implements IEventosController { // Me recomendo el Eclipse
	private ManejadorEventos manejador = ManejadorEventos.getInstancia();
	private ManejadorUsuarios manejadorUsuarios = ManejadorUsuarios.getinstance();
	private ManejadorInstituciones manejadorInstituciones = ManejadorInstituciones.getInstance();

	// --------------- Altas ----------------

	/**
	 * javadoc commet.
	 */
	public void altaTipoDeRegistro(String nombreTipo, String nombreEdicion, String descripcion, int costo, int cupo)
			throws IOException {

		Edicion edicion = manejador.getEdicion(nombreEdicion);

		if (edicion != null) {
			if (costo < 0) {
				throw new IOException("El costo debe ser mayor o igual a 0.");
			}
			if (cupo <= 0) {
				throw new IOException("El cupo debe ser mayor a 0");
			}
			edicion.crearTipoRegistro(nombreTipo, descripcion, costo, cupo);
		} else {
			throw new IOException("No existe la edicion: " + nombreEdicion);
		}
	}

	/**
	 * javadoc commet.
	 */
	public boolean altaEvento(String nombre, String descripcion, String sigla, List<String> categorias,
			LocalDate fechaAlta, String img) throws EventoYaExisteException {

		boolean existe = manejador.existeEvento(nombre);
		if (existe) {
			throw new EventoYaExisteException("El evento (" + nombre + ") ya existe.");
		}

		// Mapeo las categorias
		Map<String, Categoria> mapCategorias = new HashMap<>();
		for (String nombreCategoria : categorias) {
			Categoria categoria = manejador.getCategoria(nombreCategoria);
			mapCategorias.put(nombreCategoria, categoria);
		}

		Evento nuevoEvento = new Evento(nombre, sigla, descripcion, mapCategorias, fechaAlta, img);
		manejador.agregarEvento(nuevoEvento);
		return true;
	}

	/**
	 * javadoc commet.
	 */
	public void altaEdicion(String nombreEvento, String nickname, String nombreEdicion, String sigla, String ciudad,
			String pais, LocalDate fechaIni, LocalDate fechaFin, LocalDate fechaAlta, String img)
			throws IllegalArgumentException {
		Evento evento = manejador.getEvento(nombreEvento);

		if (fechaIni == null || fechaFin == null) {
			throw new IllegalArgumentException("Ambas fechas son requeridas.");
		}

		if (fechaIni.isBefore(fechaAlta)) {
			throw new IllegalArgumentException("La fecha de inicio debe ser posterior o igual a la fecha actual.");
		}

		if (fechaIni.isAfter(fechaFin)) {
			throw new IllegalArgumentException("La fecha de inicio debe ser anterior a la fecha de fin.");
		}

		if (evento == null) {
			throw new IllegalArgumentException("El evento no existe");
		} else {
			boolean edicion = manejador.existeEdicion(nombreEdicion);
			if (edicion) {
				throw new IllegalArgumentException("Ya existe una edicion con ese nombre.");
			} else {
				Organizador org = manejadorUsuarios.getOrganizador(nickname);
				if (org == null) {
					throw new IllegalArgumentException("El organizador no existe");
				} else {
					Edicion nuevaEdicion = new Edicion(nombreEdicion, evento, org, sigla, fechaIni, fechaFin, ciudad,
							pais, fechaAlta, img);
					org.asociarEdicion(nombreEdicion, nuevaEdicion);
					evento.setEdicion(nuevaEdicion);
					manejador.agregarEdicion(nuevaEdicion);
				}
			}
		}
	}

	/**
	 * javadoc commet.
	 */
	public void altaEdicion(String nombreEvento, String nickname, String nombreEdicion, String sigla, String ciudad,
			String pais, Date fechaIni, Date fechaFin, String img) {
		try {

			if (fechaFin.before(fechaIni)) {
				throw new IllegalArgumentException("La fecha de inicio debe ser anterior a la fecha de fin.");
			}

			if (fechaIni.before(new Date())) {
				throw new IllegalArgumentException("La fecha de inicio debe ser posterior o igual a la fecha actual");
			}
			Evento evento = manejador.getEvento(nombreEvento);
			if (evento.isFinalizado()) {
				throw new IllegalArgumentException(
						"El Evento fue finalizado, no se podran crear mas ediciones del mismo.");
			}

			if (fechaIni == null || fechaFin == null) {
				throw new IllegalArgumentException("Ambas fechas son requeridas.");
			}
			LocalDate ini = fechaIni.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			LocalDate fin = fechaFin.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			altaEdicion(nombreEvento, nickname, nombreEdicion, sigla, ciudad, pais, ini, fin, LocalDate.now(), img);
		} catch (IllegalArgumentException e) {
			System.out.println("Error desconocido");
		}
	}

	/**
	 * javadoc commet.
	 */
	public void altaCategoria(String categoria) throws IllegalArgumentException {
		if (manejador.getCategoria(categoria) != null) {
			throw new IllegalArgumentException("La categoria ya existe");
		}
		;
		Categoria nuevaCategoria = new Categoria(categoria);
		manejador.agregarCategoria(nuevaCategoria);
	}

	// ----------- Listados -----------------

	/**
	 * javadoc commet.
	 */
	public List<String> listarEventos() {
		Map<String, Evento> eventos = manejador.getEventos();
		if (eventos.isEmpty()) {
			return new ArrayList<>();

		}
		return new ArrayList<>(eventos.keySet());
	}

	/**
	 * javadoc commet.
	 */
	public List<String> listarCategorias() {
		Map<String, Categoria> categorias = manejador.getCategorias();
		if (categorias == null) {
			// throw new IllegalArgumentException("No hay categorias disponibles");
			return new ArrayList<>();
		}
		return new ArrayList<>(categorias.keySet());
	}

	/**
	 * javadoc commet.
	 */
	public List<String> listarEdicionesEvento(String nombreEvento) {

		Evento evento = manejador.getEvento(nombreEvento);

		if (evento != null) {
			return evento.ListarEdiciones();
		}
		// EXCEPCION ACÁ
		return new ArrayList<>();
	}

	/**
	 * javadoc commet.
	 */
	public List<String> listarEdicionesEventoIngresadas(String nombreEvento) {

		Evento evento = manejador.getEvento(nombreEvento);

		if (evento != null) {
			return evento.ListarEdicionesIngresadas();
		}
		// EXCEPCION ACÁ
		return new ArrayList<>();
	}

	/**
	 * javadoc commet.
	 */
	public List<String> listarEdicionesEventoOrganizador(String nombreEvento, String organizador) {

		Evento evento = manejador.getEvento(nombreEvento);

		if (evento != null) {
			return evento.ListarEdicionesOrganizador(organizador);
		}
		// EXCEPCION ACÁ
		return new ArrayList<>();
	}

	/**
	 * javadoc commet.
	 */
	public List<String> listarOrganizadores() {
		Map<String, Organizador> organizadores = manejadorUsuarios.getOrganizadores();
		if (organizadores == null) {

			return new ArrayList<>();
		}
		return new ArrayList<>(organizadores.keySet());
	}

	/**
	 * javadoc commet.
	 */
	public List<String> listarTiposDeRegistroDeEdicion(String nombreEdicion) {
		Edicion edicion = manejador.getEdicion(nombreEdicion);

		if (edicion == null) {
			return new ArrayList<>();
		}

		Map<String, TipoRegistro> tiposRegistro = edicion.getTiposRegistro();
		return new ArrayList<>(tiposRegistro.keySet());
	}

	/**
	 * javadoc commet.
	 */
	public DTTipoRegistro consultaTipoDeRegistro(String nombreEdicion, String nombreTipoRegistro) {
		Edicion edicion = manejador.getEdicion(nombreEdicion);
		TipoRegistro tipoRegistro = edicion.getTipoRegistro(nombreTipoRegistro);
		String descripcion = tipoRegistro.getDescripcion();
		int precio = tipoRegistro.getCosto();
		int cupo = tipoRegistro.getCupo();
		int cantRegistros = tipoRegistro.getCantRegistros();
		DTTipoRegistro res = new DTTipoRegistro(nombreEdicion, nombreTipoRegistro, descripcion, precio, cupo,
				cantRegistros);
		return res;
	}

	/**
	 * javadoc commet.
	 */
	public String obtenerOrganizadorDeEdicion(String nombreEvento, String nombreEdicion) {
		Evento evento = manejador.getEvento(nombreEvento);
		if (evento == null) {
			return "";
		} else {
			Edicion edicion = evento.getEdicion(nombreEdicion);
			if (edicion == null) {
				return "";
			} else {
				Organizador org = edicion.getOrganizador();
				return (org != null) ? org.getNickname() : "";
			}
		}
	}

	/**
	 * javadoc commet.
	 */
	public String obtenerOrganizadorDeEdicion(String nombreEdicion) {
		Edicion edicion = manejador.getEdicion(nombreEdicion);
		if (edicion == null) {
			return "";
		} else {
			Organizador org = edicion.getOrganizador();
			return (org != null) ? org.getNickname() : "";
		}
	}

	/**
	 * javadoc commet.
	 */
	public List<DTRegistro> listarRegistrosDeEdicion(String nombreEvento, String nombreEdicion) {
		Evento evento = manejador.getEvento(nombreEvento);
		if (evento == null) {
			return new ArrayList<>(); // Si aniado todos los else queda muy largo
		}
		Edicion edicion = evento.getEdicion(nombreEdicion);
		if (edicion == null) {
			return new ArrayList<>();
		}

		// Edicion.getRegistros(): Map<nickAsistentes, Registro>s
		Map<String, Registro> regs = edicion.getRegistros();
		if (regs == null || regs.isEmpty()) {
			return new ArrayList<>();
		}

		List<DTRegistro> res = new ArrayList<>();
		for (Registro r : regs.values()) {
			if (r != null) {
				DTRegistro dtRegistro = new DTRegistro(r.getEdicion().getNombre(), r.getEdicion().getNombreEvento(),
						r.getCosto(), r.getFechaAlta(), r.getAsistente().getNickname(), r.isAsistio());
				res.add(dtRegistro);
			}
		}
		return res;

	}

	// ----------- Info -----------------

	/**
	 * javadoc commet.
	 */
	public DTEvento infoEvento(String nombreEvento) {
		Evento evento = manejador.getEvento(nombreEvento);
		if (evento != null) {
			return new DTEvento(evento);
		}
		throw new IllegalArgumentException("El evento no existe");
	}

	/**
	 * javadoc commet.
	 */
	public DTEdicion infoEdicion(String nombreEdicion) {
		Edicion edicion = manejador.getEdicion(nombreEdicion);
		if (edicion != null) {
			return new DTEdicion(edicion);
		}
		throw new IllegalArgumentException("La edicion no existe");
	}

	/**
	 * javadoc commet.
	 */
	public List<String> listarPatrociniosDeEdicion(String nombreEvento, String nombreEdicion) {
		Evento evento = manejador.getEvento(nombreEvento);
		if (evento == null) {
			return new ArrayList<>();
		}
		Edicion edicion = evento.getEdicion(nombreEdicion);
		if (edicion == null) {
			return new ArrayList<>();
		}
		Map<String, Patrocinio> patros = edicion.getPatrocinios();
		if (patros == null || patros.isEmpty()) {
			return new ArrayList<>();
		}
		return new ArrayList<>(patros.keySet());
	}

	/**
	 * javadoc commet.
	 */
	public List<String> listarCodigosDePatrocinioDeEdicion(String nombreEvento, String nombreEdicion) {
		Evento evento = manejador.getEvento(nombreEvento);
		if (evento == null) {
			return new ArrayList<>();
		}
		Edicion edicion = evento.getEdicion(nombreEdicion);
		if (edicion == null) {
			return new ArrayList<>();
		}
		Map<String, Patrocinio> patros = edicion.getPatrociniosPorCodigo();
		if (patros == null || patros.isEmpty()) {
			return new ArrayList<>();
		}
		return new ArrayList<>(patros.keySet());
	}

	// No se porque pero me da error si no hago dos altaEdicion
	/**
	 * javadoc commet.
	 */

	public void altaPatrocinio(String institucion, String edicion, String tipoRegistro, NivelPatrocinio nivel,
			int monto, int cupo, LocalDate fechaAlta, String codigo) throws IOException {

		if (cupo <= 0) {
			throw new IOException("El cupo debe ser mayor a 0.");
		}
		if (monto <= 0) {
			throw new IOException("El monto debe ser mayor a 0.");
		}
		if (codigo == "") {
			throw new IOException("El código no puede ser vacio.");
		}
		if (institucion == "") {
			throw new IOException("La institucion no puede ser vacia");
		}
		
		Edicion edic = manejador.getEdicion(edicion);
		if (edic == null) {
			throw new IOException("La edicion no existe");
		}

		if (edic.getFechaFin().isBefore(LocalDate.now())) {
			throw new IOException("La edicion ya finalizó.");
		}

		Institucion ins = manejadorInstituciones.obtenerInstitucion(institucion);
		TipoRegistro tipoReg = edic.getTipoRegistro(tipoRegistro);
		int costoEnTR = tipoReg.getCosto();
		if ((costoEnTR * cupo) > (monto / 5)) {
			throw new IOException("La cantidad de cupos gratuitos no puede exceder el 20% del aporte");
		}

		int disponibles = tipoReg.getCuposDisponibles();
		if (disponibles == 0) {
			throw new IOException("No se tienen cupos disponibles para ese tipo de registro");
		}
		if (cupo > disponibles) {
			tipoReg.anularDisponibilidadDeCupos();
			edic.crearPatrocinio(ins, tipoReg, nivel, monto, disponibles, fechaAlta, codigo);
			throw new IOException("La cantidad de cupos gratuitos supera a la cantidad de cupos disponibles en "
					+ "el tipo de registro seleccionado. " + "Por lo tanto, por disponibilidad se asignaron: "
					+ disponibles + " cupos.");
		}

		edic.crearPatrocinio(ins, tipoReg, nivel, monto, cupo, fechaAlta, codigo);
	}

	/**
	 * javadoc commet.
	 */
	public DTPatrocinio consultarPatrocinio(String nombreEdicion, String codigoPatrocinio) {
		Edicion edicion = manejador.getEdicion(nombreEdicion);
		Patrocinio patroc = edicion.getPatrocinioPorCodigo(codigoPatrocinio);
		Institucion ins = patroc.getInstitucion();
		String nombreInstitucion = ins.getNombre();
		DTPatrocinio patrocinio = edicion.getDTPatrocinio(nombreInstitucion, codigoPatrocinio);
		return patrocinio;
	}

	/**
	 * javadoc commet.
	 */
	public DTPatrocinio consultarPatrocinioPorInstitucion(String nombreEdicion, String InstPatrocinio) {
		Edicion edicion = manejador.getEdicion(nombreEdicion);
		Patrocinio patroc = edicion.getPatrocinio(InstPatrocinio);
		Institucion ins = patroc.getInstitucion();
		String nombreInstitucion = ins.getNombre();
		DTPatrocinio patrocinio = edicion.getDTPatrocinio(nombreInstitucion, InstPatrocinio);
		return patrocinio;
	}

	/**
	 * javadoc commet.
	 */
	public Map<String, DTPatrocinio> consultarPatrociniosDeEdicion(String nombreEdicion) throws FileNotFoundException {

		Map<String, DTPatrocinio> DTPatrocinios = new HashMap<>();

		Edicion edicion = manejador.getEdicion(nombreEdicion);
		if (edicion == null) {
			throw new FileNotFoundException("la edicion " + nombreEdicion + " no existe");
		}

		Map<String, Patrocinio> patrocinios = edicion.getPatrocinios();

		for (Patrocinio p : patrocinios.values()) {
			DTPatrocinio DTPat = p.getDT();
			DTPatrocinios.put(DTPat.getNombreInstitucion(), DTPat);
		}

		return DTPatrocinios;
	}

	/**
	 * javadoc commet.
	 */
	public void aceptarEdicion(String nombreEdicion) {
		manejador.getEdicion(nombreEdicion).setEstado(EstadoEdicion.ACEPTADA);
	}

	/**
	 * javadoc commet.
	 */
	public void rechazarEdicion(String nombreEdicion) {
		manejador.getEdicion(nombreEdicion).setEstado(EstadoEdicion.RECHAZADA);
	}

  /**
   * javadoc commet.
   */
  public boolean existeCodigoDePatrocinioEnEdicion(String codigoPatrocinio, String nombreEvento,
      String nombreEdicion) {
    List<String> codigosDePatrocinioDeEdicion = listarCodigosDePatrocinioDeEdicion(nombreEvento,
        nombreEdicion);
    return codigosDePatrocinioDeEdicion.contains(codigoPatrocinio);
  }
  
  
  public void finalizarEvento(String nombreEvento) {
	  manejador.getEvento(nombreEvento).setFinalizado(true);
  }
  
  public void archivarEdicionDeEvento(String nombreEdicion) {
	  
  }
  
  
  public String descargarConstanciaDeAsisitencia(Registro registroDeAsistente) {
	  return "funcionalidad no valida je.";
  }
  
  private void eliminarEdicion(String nombreEdicion) {
	  
  }
  
  private void guardarEdicionDB(String nombreEdicion) {
	  
  }
  
  public int incrementarVisita(String nombreEvento) {
	  Evento evento = manejador.getEvento(nombreEvento);
	  int visitas = evento.getVisitas()+1;
	  evento.setVisitas(visitas);
	  return visitas;
  };
  
  public void agregarVideo(String nombreEdicion, String video) throws IllegalArgumentException{
	  Edicion ed = manejador.getEdicion(nombreEdicion);
	  if(ed == null) {
		  throw new IllegalArgumentException("La edicion no existe");
	  }
	  ed.setVideoURL(video);
  };
  
  public List<DTEvento> conseguirEventosMasVisitados() {
	   
	    List<String> nombresEventos = this.listarEventos();
	    List<DTEvento> todos = new ArrayList<>();

	    for (String nombre : nombresEventos) {
	        try {
	            DTEvento evento = this.infoEvento(nombre);
	            if (evento != null) {
	                todos.add(evento);
	            }
	        } catch (Exception e) {
	            // Si hay algún error con un evento en particular, lo ignoramos
	        }
	    }

	    // Ordenamos la lista de mayor a menor cantidad de visitas
	    todos.sort((e1, e2) -> Integer.compare(e2.getVisitas(), e1.getVisitas()));

	    // Devolvemos solo los primeros 5 (o menos si hay menos de 5 eventos)
	    if (todos.size() > 5) {
	        return todos.subList(0, 5);
	    } else {
	        return todos;
	    }
	}
}
