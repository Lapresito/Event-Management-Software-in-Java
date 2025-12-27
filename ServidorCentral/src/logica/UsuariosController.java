package logica;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import excepciones.EmailRepetidoException;
import excepciones.NicknameRepetidoException;
import excepciones.UsuarioNoExisteException;

/**
 * Controlador de usuarios.
 *
 */
public class UsuariosController implements IUsuariosController {

	private ManejadorEventos manejadorEventos = ManejadorEventos.getInstancia();
	private ManejadorUsuarios manejadorUsuarios = ManejadorUsuarios.getinstance();

	/**
	 * javadoc commet.
	 */
	public UsuariosController() {
	}

	/**
	 * javadoc commet.
	 * 
	 * @throws IOException
	 */
	public void altaOrganizador(String nickname, String email, String nombre, String descripcion, String img,
			String password) throws NicknameRepetidoException, EmailRepetidoException, IOException {
		ManejadorUsuarios manejadorUsers = ManejadorUsuarios.getinstance();
		Usuario user = manejadorUsers.obtenerUsuario(nickname);

		if (nickname == null || nickname.trim().isEmpty()) {
			throw new IOException("El nickname no puede estar vacío.");
		}

		if (user != null) {
			throw new NicknameRepetidoException("El usuario con nickname " + nickname + " ya esta registrado");
		}

		if (descripcion == null || descripcion.trim().isEmpty()) {
			throw new IOException("La descripción no puede estar vacía.");
		}

		if (nombre == null || nombre.trim().isEmpty()) {
			throw new IOException("el nombre no puede estar vacío.");
		}

		if (manejadorUsers.existeEmail(email)) {
			throw new EmailRepetidoException("El usuario con correo " + email + " ya esta registrado");
		}

		Organizador org = new Organizador(nickname, email, nombre, descripcion, img, password);
		manejadorUsers.addOrganizador(org);
	}

	/**
	 * javadoc commet.
	 */
	public void ingresarSitioWeb(String nickname, String sitioWeb) {
		ManejadorUsuarios manejadorUsers = ManejadorUsuarios.getinstance();
		Usuario user = manejadorUsers.obtenerUsuario(nickname);
		if (user instanceof Organizador) { // no deberia ser asistente porque solo se llama despues de
			// organizador
			Organizador org = (Organizador) user;
			org.setSitioWeb(sitioWeb);
		}
	}

	/**
	 * javadoc commet.
	 */
	public void altaAsistente(String nickname, String email, String nombre, String apellido, LocalDate nacimiento,
			String img, String password) throws NicknameRepetidoException, EmailRepetidoException {
		ManejadorUsuarios manejadorUsers = ManejadorUsuarios.getinstance();
		Usuario user = manejadorUsers.obtenerUsuario(nickname);
		if (user != null) {
			throw new NicknameRepetidoException("El usuario con nickname " + nickname + " ya esta registrado");
		}

		if (nacimiento.isAfter(LocalDate.now()) || nacimiento.isEqual(LocalDate.now())) {
			throw new IllegalArgumentException(
					"La fecha de nacimiento del usuario:  " + nickname + " debe ser menor a la actual.");
		}

		if (manejadorUsers.existeEmail(email)) {
			throw new EmailRepetidoException("El usuario con correo " + email + " ya esta registrado");
		}
		Asistente asist = new Asistente(nickname, email, nombre, apellido, nacimiento, img, password);
		manejadorUsers.addAsistente(asist);
	}

	/**
	 * javadoc commet.
	 */
	public void ingresarInstitucion(String nickname, String nombreInstitucion) {
		ManejadorInstituciones manejadorInst = ManejadorInstituciones.getInstance();
		Institucion inst = manejadorInst.obtenerInstitucion(nombreInstitucion);

		ManejadorUsuarios manejadorUsers = ManejadorUsuarios.getinstance();
		Usuario user = manejadorUsers.obtenerUsuario(nickname);
		if (user instanceof Asistente) { // no deberia ser org porque solo se llama despues de dar de
			Asistente asist = (Asistente) user;
			asist.setInstitucion(inst);
		}

	}

	/**
	 * javadoc commet.
	 */
	public boolean esOrganizador(String nickname) {
		ManejadorUsuarios manejadorUsers = ManejadorUsuarios.getinstance();
		boolean existeOrganizador = manejadorUsers.esOrganizador(nickname);
		return existeOrganizador;
	}

	/**
	 * javadoc commet.
	 */
	public Boolean esAsistente(String nickname) {
		ManejadorUsuarios manejadorUsers = ManejadorUsuarios.getinstance();
		Usuario user = manejadorUsers.obtenerUsuario(nickname);
		return (user instanceof Asistente);
	}

	/**
	 * javadoc commet.
	 */
	public DTAsistente getAsistente(String nickname) {
		ManejadorUsuarios manejadorUsers = ManejadorUsuarios.getinstance();
		Asistente user = manejadorUsers.getAsistente(nickname);
		if (user == null) {
			return null;
		}

		String nombreInst = (user.getInstitucion() != null) ? user.getInstitucion().getNombre() : "";

		String[] registros = user.getRegistros().values().stream().map(r -> r.getEdicion().getNombre())
				.toArray(String[]::new);

		return new DTAsistente(user.getNickname(), user.getEmail(), user.getNombre(), user.getApellido(),
				user.getNacimiento(), nombreInst, registros, user.getImg(), user.getDTSeguidos(),
				user.getDTSeguidores());
	}

	/**
	 * javadoc commet.
	 */
	public DTOrganizador getOrganizador(String nickname) {
		ManejadorUsuarios manejadorUsers = ManejadorUsuarios.getinstance();
		Organizador userOrg = manejadorUsers.getOrganizador(nickname);
		if (userOrg == null) {
			return null;
		}

		DTEdicion[] eds = userOrg.getEdiciones().values().stream().map(Edicion::getDataEdicion)
				.toArray(DTEdicion[]::new);

		return new DTOrganizador(userOrg.getNickname(), userOrg.getEmail(), userOrg.getNombre(),
				userOrg.getDescripcion(), userOrg.getSitioWeb(), eds, userOrg.getImg(), userOrg.getDTSeguidos(),
				userOrg.getDTSeguidores());
	}

	/**
	 * javadoc commet.
	 */
	public DTUsuario[] getUsuarios() throws UsuarioNoExisteException {
		ManejadorUsuarios manejadorUsers = ManejadorUsuarios.getinstance();
		Usuario[] usrs = manejadorUsers.getUsuarios();

		if (usrs != null) {
			DTUsuario[] dtUser = new DTUsuario[usrs.length];
			Usuario usuario;

			// Para separar lógica de presentación, no se deben devolver los Usuario,
			// sino los DataUsuario
			for (int i = 0; i < usrs.length; i++) {
				usuario = usrs[i];
				dtUser[i] = new DTUsuario(usuario.getNickname(), usuario.getEmail(), usuario.getNombre(),
						usuario.getImg(), usuario.getDTSeguidos(), usuario.getDTSeguidores());
			}

			return dtUser;
		} else {
			throw new UsuarioNoExisteException("No existen usuarios registrados");
		}

	}

	/**
	 * javadoc commet.
	 */
	public boolean autenticarUsuario(String emailONick, String password) throws UsuarioNoExisteException {
		Usuario user = manejadorUsuarios.obtenerUsuarioEmail(emailONick);
		if (user == null) {
			user = manejadorUsuarios.getUsuario(emailONick);
			if (user == null) {
				throw new UsuarioNoExisteException("No existen usuarios registrados con ese correo o nickname.");
			}
		}
		return user.getPassword().equals(password);
	}

	/**
	 * javadoc commet.
	 */
	public DTUsuario obtenerUsuarioEmail(String email) throws UsuarioNoExisteException {
		Usuario usuario = manejadorUsuarios.obtenerUsuarioEmail(email);
		if (usuario == null) {
			throw new UsuarioNoExisteException("No existen usuarios registrados con ese correo.");
		}
		DTUsuario DTuser = new DTUsuario(usuario.getNickname(), usuario.getEmail(), usuario.getNombre(),
				usuario.getImg(), usuario.getDTSeguidos(), usuario.getDTSeguidores());
		return DTuser;
	}

	/**
	 * javadoc commet.
	 */
	public String obtenerTipoUsuario(String nick) {
		return manejadorUsuarios.tipoUsuario(nick);
	}

	// --------------- Altas ----------------

	// devuelve false cuando se llego al cupo de la edicion o el asistente ya esta
	// registrado en la misma.
	/**
	 * javadoc commet.
	 */
	public void altaRegistro(String nicknameAsistente, String nombreEdicion, String nombreTipoRegistro,
			LocalDate fechaAlta) throws IOException {

		Asistente asistente = manejadorUsuarios.getAsistente(nicknameAsistente);

		Edicion edicion = manejadorEventos.getEdicion(nombreEdicion);

		if (edicion.getFechaFin().isBefore(LocalDate.now())) {
			throw new IOException("La edicion ya finalizó.");
		}

		edicion.crearRegistro(asistente, nombreTipoRegistro, fechaAlta);

	}

	/**
	 * javadoc commet.
	 */
	public void altaRegistroConCodigo(String nicknameAsistente, String nombreEdicion, String nombreTipoRegistro,
			String codigo, LocalDate fechaAlta) throws IOException {

		Asistente asistente = manejadorUsuarios.getAsistente(nicknameAsistente);

    Edicion edicion = manejadorEventos.getEdicion(nombreEdicion);
    

    Institucion institucion = asistente.getInstitucion();
    if(institucion == null) {
      throw new IOException("El asistente debe pertenecer a la institucion del patrocinio que brinda este codigo.");
    }else {
      Patrocinio patrocinio = edicion.getPatrocinio(institucion.getNombre());
      if(patrocinio == null) {
        throw new IOException("Tu institucion no esta patrocinando el evento.");
      }else{
        if(!patrocinio.quedanCuposDisponibles()) {
          throw new IOException("No quedan cupos disponibles para este patrocinio.");
        }
      }
    }
    if (edicion.getFechaFin().isBefore(LocalDate.now())) {
      throw new IOException("La edicion ya finalizó.");
    }

    edicion.crearRegistroConCodigo(asistente, nombreTipoRegistro, codigo, fechaAlta);

	}

	// ---------------- Listas -------------
	/**
	 * javadoc commet.
	 */
	public List<DTAsistente> listarAsistentes() {

		Map<String, Asistente> asistentes = manejadorUsuarios.getAsistentes();
		List<DTAsistente> dtAsistentes = new ArrayList<>();

		for (Asistente a : asistentes.values()) {
			String nickname = a.getNickname();
			String email = a.getEmail();
			String nombre = a.getNombre();
			String img = a.getImg();
			String apellido = a.getApellido();
			LocalDate nacimiento = a.getNacimiento();
			String institucion = a.getInstitucion() != null ? a.getInstitucion().getNombre() : "";

			Map<String, Registro> registrosMap = a.getRegistros();
			String[] registros = registrosMap.values().stream().map(r -> r.getNombreEdicion()).toArray(String[]::new);

			dtAsistentes.add(new DTAsistente(nickname, email, nombre, apellido, nacimiento, institucion, registros, img,
					a.getDTSeguidos(), a.getDTSeguidores()));
		}

		dtAsistentes.sort((a1, a2) -> a1.getNickname().compareToIgnoreCase(a2.getNickname()));

		return dtAsistentes;
	}

	/**
	 * javadoc commet.
	 */
	public List<DTOrganizador> listarOrganizadores() {

		Map<String, Organizador> organizadores = manejadorUsuarios.getOrganizadores();
		List<DTOrganizador> dtOrganizadores = new ArrayList<>();

		for (Organizador o : organizadores.values()) {
			String nickname = o.getNickname();
			String email = o.getEmail();
			String nombre = o.getNombre();
			String img = o.getImg();
			String descripcion = o.getDescripcion();
			String sitioWeb = o.getSitioWeb();

			Map<String, Edicion> edicionesMap = o.getEdiciones();
			DTEdicion[] ediciones = edicionesMap.values().stream().map(DTEdicion::new).toArray(DTEdicion[]::new);

			dtOrganizadores.add(new DTOrganizador(nickname, email, nombre, descripcion, sitioWeb, ediciones, img,
					o.getDTSeguidos(), o.getDTSeguidores()));
		}

		dtOrganizadores.sort((o1, o2) -> o1.getNickname().compareToIgnoreCase(o2.getNickname()));

		return dtOrganizadores;
	}

	/**
	 * javadoc commet.
	 */
	public List<String> listarRegistrosAsistente(String nicknameAsistente) {
		Asistente asistente = manejadorUsuarios.getAsistente(nicknameAsistente);
		Map<String, Registro> registros = asistente.getRegistros();
		List<String> resultado = new ArrayList<>();

		for (Registro registro : registros.values()) {
			String nombreEdicion = registro.getEdicion().getNombre();
			String fecha = registro.getFecha().toString(); // o formateada si querés
			resultado.add(nombreEdicion + " — " + fecha);
		}

		return resultado;
	}

	/**
	 * javadoc commet.
	 */
	public Map<String, String> listarRegistrosYEdicionAsistente(String nicknameAsistente) {
		Asistente asistente = manejadorUsuarios.getAsistente(nicknameAsistente);
		Map<String, Registro> registros = asistente.getRegistros();
		Map<String, String> resultado = new HashMap<>(); // clave: edicion, valor: registro.toString()

		for (Registro registro : registros.values()) {
			String nombreEdicion = registro.getEdicion().getNombre(); // clave
			resultado.put(nombreEdicion, registro.toString()); // valor
		}

		return resultado;
	}

	/**
	 * javadoc commet.
	 */
	public DTRegistro infoRegistroDeAsistente(String nicknameAsistente, String nombreEdicion) {
		Asistente asistente = manejadorUsuarios.getAsistente(nicknameAsistente);
		Edicion edicion = manejadorEventos.getEdicion(nombreEdicion);
		Registro registro = asistente.getRegistro(nombreEdicion);
		if (asistente == null || edicion == null || registro == null) {
			return new DTRegistro("", "", 0, LocalDate.now(), "", false);
		}
		DTRegistro infoR = new DTRegistro(nombreEdicion, edicion.getNombreEvento(), registro.getCosto(),
				registro.getFecha(), nicknameAsistente, registro.isAsistio());
		return infoR;
	}

	/**
	 * javadoc commet.
	 */
	public void editarAsistente(String nickname, String nombre, String apellido, LocalDate nacimiento, String img,
			String password) {

    if (nacimiento.isAfter(LocalDate.now()) ||  nacimiento.isEqual(LocalDate.now())) {
      throw new IllegalArgumentException(
        "La fecha de nacimiento del usuario:  " + nickname + " debe ser menor a la actual.");
    }
    Asistente asistente = manejadorUsuarios.getAsistente(nickname);
    asistente.setNombre(nombre);
    asistente.setApellido(apellido);
    asistente.setNacimiento(nacimiento);
    asistente.setImg(img);
    if (password == null || password.isEmpty()) {
      asistente.setPassword(asistente.getPassword());
    } else {
      asistente.setPassword(password);
    }

	}

  /**
   * javadoc commet.
   */
  public void editarOrganizador(String nickname, String nombre, String descripcion, String sitioWeb,
      String img, String password) {
    Organizador organizador = manejadorUsuarios.getOrganizador(nickname);
    organizador.setNombre(nombre);
    organizador.setDescripcion(descripcion);
    organizador.setSitioWeb(sitioWeb);
    organizador.setImg(img);
    if (password == null || password.isEmpty()) {
      organizador.setPassword(organizador.getPassword());
    } else {
      organizador.setPassword(password);
    }
  }

	/**
	 * javadoc commet.
	 */
	public List<String> listarEdicionesAceptadasDeOrganizador(String nicknameOrg) {
		DTOrganizador organizador = getOrganizador(nicknameOrg);

		if (organizador == null) {
			return new ArrayList<>();
		} else if (organizador.getEdiciones() == null) {
			return new ArrayList<>();
		} else {
			DTEdicion[] ediciones = organizador.getEdiciones();

			for (DTEdicion ed : ediciones) {
				if (ed != null) {
					System.out.println("Edición: " + ed.getNombreEdicion());
					System.out.println("Estado: " + ed.getEstado());
				} else {
					System.out.println("Edición nula encontrada.");
				}
			}

			return Arrays.stream(organizador.getEdiciones())
					.filter(edicion -> edicion != null && edicion.getNombreEdicion() != null
							&& edicion.getEstado() == EstadoEdicion.ACEPTADA)
					.map(DTEdicion::getNombreEdicion).collect(Collectors.toList());
		}

	}

	/**
	 * javadoc commet.
	 */
	public List<String> listarEdicionesDeOrganizador(String nicknameOrg) {
		DTOrganizador organizador = getOrganizador(nicknameOrg);

    if (organizador == null) {
      return new ArrayList<>();
    } else if (organizador.getEdiciones() == null) {
      return new ArrayList<>();
    } else {
      return Arrays.stream(organizador.getEdiciones())
          .filter(edicion -> edicion != null && edicion.getNombreEdicion() != null)
          .map(DTEdicion::getNombreEdicion).collect(Collectors.toList());
    }
  }
  
  
  
  public boolean seguirAUsuario(String nicknameASeguir, String nicknameSeguidor) {
	  Usuario seguidor = manejadorUsuarios.getUsuario(nicknameSeguidor);
	  Usuario seguido = manejadorUsuarios.getUsuario(nicknameASeguir);
	  if(seguidor == null || seguido == null) {
		  return false;
	  }
	  return seguido.agregarSeguidor(seguidor) && seguidor.seguir(seguido);
  }
  
  public boolean dejarDeSeguirAUsuario(String nicknameADejarDeSeguir, String nicknameSeguidor) {
	  Usuario seguidor = manejadorUsuarios.getUsuario(nicknameSeguidor);
	  Usuario aDejarDeSeguir = manejadorUsuarios.getUsuario(nicknameADejarDeSeguir);
	  if(seguidor == null || aDejarDeSeguir == null) {
		  return false;
	  }
	  return seguidor.dejarDeSeguir(aDejarDeSeguir) && aDejarDeSeguir.eliminarSeguidor(seguidor);
  }
  
  public List<DTUsuario> getSeguidoresDeUsuario(String nickname) {
	  return manejadorUsuarios.getUsuario(nickname).getDTSeguidores();
	  
  }
  public  List<DTUsuario> getSeguidosDeUsuario(String nickname) {
	  return manejadorUsuarios.getUsuario(nickname).getDTSeguidos(); 
  }
  
  public void registrarAsistencia(String nickname, String nombreEdicion) throws IOException{
    Asistente asist = manejadorUsuarios.getAsistente(nickname);
    if(asist == null) {
        throw new IOException("El usuario con nickname " + nickname + " no es asistente o no existe.");
    }

    Registro reg = asist.getRegistro(nombreEdicion);
    if(reg == null) {
        throw new IOException("El usuario con nickname " + nickname + " no tiene registros para la edición " + nombreEdicion + ".");
    }
    
    Edicion ed = manejadorEventos.getEdicion(nombreEdicion);
    if(ed.getFechaIni().isAfter(LocalDate.now())) {
    	throw new IOException("La edición " + nombreEdicion + " aún no ha comenzado, no se puede confirmar la asistencia.");
    }
    reg.setAsistio(true);
  };
  
}
