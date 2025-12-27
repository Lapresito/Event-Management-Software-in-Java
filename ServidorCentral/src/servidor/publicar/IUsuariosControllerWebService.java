package servidor.publicar;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sun.net.httpserver.HttpServer;

import excepciones.EmailRepetidoException;
import excepciones.NicknameRepetidoException;
import excepciones.UsuarioNoExisteException;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;
import jakarta.jws.soap.SOAPBinding.ParameterStyle;
import jakarta.jws.soap.SOAPBinding.Style;
import jakarta.xml.ws.Endpoint;
import logica.DTAsistente;
import logica.DTOrganizador;
import logica.DTRegistro;
import logica.DTUsuario;
import logica.Fabrica;
import logica.IUsuariosController;

@WebService
@SOAPBinding(style = Style.RPC, parameterStyle = ParameterStyle.WRAPPED)
public class IUsuariosControllerWebService {
	Fabrica fabrica = Fabrica.getInstance();
	IUsuariosController controladorUsuarios = fabrica.getIUsuariosController();

	private Endpoint endpoint = null;

	public IUsuariosControllerWebService() {
	}

	// Operaciones las cuales quiero publicar

	@WebMethod(exclude = true)
	public void publicar(HttpServer server, String context){
    	endpoint = Endpoint.create(this);
    	endpoint.publish(server.createContext(context));
	}

	@WebMethod(exclude = true)
	public Endpoint getEndpoint() {
		return endpoint;
	}

	// ---------------- Métodos WebService ----------------

	@WebMethod
	public void altaOrganizador(String nickname, String email, String nombre, String descripcion, String img,
			String password) throws NicknameRepetidoException, EmailRepetidoException, IOException {
		controladorUsuarios.altaOrganizador(nickname, email, nombre, descripcion, img, password);
	}

	@WebMethod
	public void ingresarSitioWeb(String nickname, String sitioWeb) {
		controladorUsuarios.ingresarSitioWeb(nickname, sitioWeb);
	}

	@WebMethod
	public void altaRegistro(String nicknameAsistente, String nombreEdicion, String nombreTipoRegistro)
			throws IOException {

		controladorUsuarios.altaRegistro(nicknameAsistente, nombreEdicion, nombreTipoRegistro, LocalDate.now());
	}

	@WebMethod
	public void altaAsistente(String nickname, String email, String nombre, String apellido, int anio, int mes, int dia,
			String img, String password) throws NicknameRepetidoException, EmailRepetidoException {
		LocalDate nacimiento = LocalDate.of(anio, mes, dia);
		controladorUsuarios.altaAsistente(nickname, email, nombre, apellido, nacimiento, img, password);
	}

	@WebMethod
	public void ingresarInstitucion(String nickname, String nombreInstitucion) {
		controladorUsuarios.ingresarInstitucion(nickname, nombreInstitucion);
	}

	@WebMethod
	public boolean esOrganizador(String nickname) {
		return controladorUsuarios.esOrganizador(nickname);
	}

	@WebMethod
	public Boolean esAsistente(String nickname) {
		return controladorUsuarios.esAsistente(nickname);
	}

    @WebMethod
    public DtAsistente getAsistente(String nickname) {
        return new DtAsistente(controladorUsuarios.getAsistente(nickname));
    }

    @WebMethod
    public DtOrganizador getOrganizador(String nickname) {
        return new DtOrganizador(controladorUsuarios.getOrganizador(nickname));
    }

    @WebMethod
    public DtUsuario[] getUsuarios() throws UsuarioNoExisteException {
    	
    	List<DtUsuario> users = new ArrayList<>();
    	DTUsuario[] usersLogica = controladorUsuarios.getUsuarios();
    	for(DTUsuario u : usersLogica) {
    		users.add(new DtUsuario(u));
    	}
    	return users.toArray(new DtUsuario[0]);
    }

    @WebMethod
    public DtOrganizador[] listarOrganizadores() {
        List<DtOrganizador> organizadores = new ArrayList<>();
        List<DTOrganizador> orgsLogica = controladorUsuarios.listarOrganizadores();
        for (DTOrganizador o : orgsLogica) {
            organizadores.add(new DtOrganizador(o));
        }
        return organizadores.toArray(new DtOrganizador[0]);
    }

    @WebMethod
    public DtAsistente[] listarAsistentes() {
        List<DtAsistente> asistentes = new ArrayList<>();
        List<DTAsistente> asistLogica = controladorUsuarios.listarAsistentes();
        for (DTAsistente a : asistLogica) {
            asistentes.add(new DtAsistente(a));
        }
        return asistentes.toArray(new DtAsistente[0]);
    }

	@WebMethod
	public String[] listarRegistrosAsistente(String nicknameAsistente) {
		return controladorUsuarios.listarRegistrosAsistente(nicknameAsistente).toArray(new String[0]);
	}

	@WebMethod
	public EntradaMap[] listarRegistrosYEdicionAsistente(String nicknameAsistente) {
		// Convertimos Map a array de DTRegistro con clave + valor si querés
		Map<String, String> map = controladorUsuarios.listarRegistrosYEdicionAsistente(nicknameAsistente);
		EntradaMap[] arr = new EntradaMap[map.size()];
		int i = 0;
		for (Map.Entry<String, String> e : map.entrySet()) {
			arr[i++] = new EntradaMap(e.getKey(), e.getValue());
		}
		return arr;
	}

    @WebMethod
    public DtRegistro infoRegistroDeAsistente(String nicknameAsistente, String nombreEdicion) {
        return new DtRegistro(controladorUsuarios.infoRegistroDeAsistente(nicknameAsistente, nombreEdicion));
    }

	@WebMethod
	public void editarAsistente(String nickname, String nombre, String apellido, int anio, int mes, int dia, String img,
			String password) {
		LocalDate nacimiento = LocalDate.of(anio, mes, dia);
		controladorUsuarios.editarAsistente(nickname, nombre, apellido, nacimiento, img, password);
	}

	@WebMethod
	public void editarOrganizador(String nickname, String nombre, String descripcion, String sitioWeb, String img,
			String password) {
		controladorUsuarios.editarOrganizador(nickname, nombre, descripcion, sitioWeb, img, password);
	}

	@WebMethod
	public boolean autenticarUsuario(String email, String password) throws UsuarioNoExisteException {
		return controladorUsuarios.autenticarUsuario(email, password);
	}

    @WebMethod
    public DtUsuario obtenerUsuarioEmail(String email) throws UsuarioNoExisteException {
        return new DtUsuario(controladorUsuarios.obtenerUsuarioEmail(email));
    }

	@WebMethod
	public String obtenerTipoUsuario(String nick) {
		return controladorUsuarios.obtenerTipoUsuario(nick);
	}

	@WebMethod
	public String[] listarEdicionesAceptadasDeOrganizador(String nicknameOrganizador) {
		return controladorUsuarios.listarEdicionesAceptadasDeOrganizador(nicknameOrganizador).toArray(new String[0]);
	}

	@WebMethod
	public void altaRegistroConCodigo(String nicknameAsistente, String nombreEdicion, String nombreTipoRegistro,
			String codigo) throws IOException {
		controladorUsuarios.altaRegistroConCodigo(nicknameAsistente, nombreEdicion, nombreTipoRegistro, codigo,
				LocalDate.now());
	}

    @WebMethod
    public String[] listarEdicionesDeOrganizador(String nicknameOrg) {
        return controladorUsuarios.listarEdicionesDeOrganizador(nicknameOrg).toArray(new String[0]);
    }
    
    
    @WebMethod
    public boolean seguirAUsuario(String nicknameASeguir, String nicknameSeguidor) {
  	  return controladorUsuarios.seguirAUsuario(nicknameASeguir, nicknameSeguidor);
    }
    
    @WebMethod
    public boolean dejarDeSeguirAUsuario(String nicknameADejarDeSeguir, String nicknameSeguidor) {
    	return controladorUsuarios.dejarDeSeguirAUsuario(nicknameADejarDeSeguir, nicknameSeguidor);
    }
    
    @WebMethod
    public DtUsuario[] getSeguidoresDeUsuario(String nickname) {
        List<DtUsuario> seguidores = new ArrayList<>();
        List<DTUsuario> seguidoresLogica = controladorUsuarios.getSeguidoresDeUsuario(nickname);

        for (DTUsuario u : seguidoresLogica) {
            seguidores.add(new DtUsuario(u));
        }

        return seguidores.toArray(new DtUsuario[0]);
    }

    @WebMethod
    public DtUsuario[] getSeguidosDeUsuario(String nickname) {
        List<DtUsuario> seguidos = new ArrayList<>();
        List<DTUsuario> seguidosLogica = controladorUsuarios.getSeguidosDeUsuario(nickname);

        for (DTUsuario u : seguidosLogica) {
            seguidos.add(new DtUsuario(u));
        }

        return seguidos.toArray(new DtUsuario[0]);
    }
    
    @WebMethod
    public  void registrarAsistencia(String nickname, String nombreEdicion) throws IOException {
  	  	controladorUsuarios.registrarAsistencia(nickname, nombreEdicion);
    }
    
}
