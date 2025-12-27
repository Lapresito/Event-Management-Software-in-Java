package logica;

import excepciones.EmailRepetidoException;
import excepciones.NicknameRepetidoException;
import excepciones.UsuarioNoExisteException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * javadoc commet.
 */
public interface IUsuariosController {

  /**
   * javadoc commet.
   */
  public abstract void altaOrganizador(String nickname, String email, String nombre,
      String descripcion, String img, String password)
      throws NicknameRepetidoException, EmailRepetidoException, IOException;

  /**
   * javadoc commet.
   */
  public abstract void ingresarSitioWeb(String nickname, String sitioWeb);

  /**
   * javadoc commet.
   */
  public abstract void altaRegistro(String nicknameAsistente, String nombreEdicion,
      String nombreTipoRegistro, LocalDate fechaAlta) throws IOException;

  /**
   * javadoc commet.
   */
  public abstract void altaAsistente(String nickname, String email, String nombre, String apellido,
      LocalDate nacimiento, String img, String password)
      throws NicknameRepetidoException, EmailRepetidoException;
  
  /**
   * javadoc commet.
   */
  public abstract void ingresarInstitucion(String nickname, String nombreInstitucion);

  /**
   * javadoc commet.
   */
  public abstract boolean esOrganizador(String nickname);

  /**
   * javadoc commet.
   */
  public abstract Boolean esAsistente(String nickname);

  /**
   * javadoc commet.
   */
  public abstract DTAsistente getAsistente(String nickname);

  /**
   * javadoc commet.
   */
  public abstract DTOrganizador getOrganizador(String nickname);

  /**
   * javadoc commet.
   */
  public abstract DTUsuario[] getUsuarios() throws UsuarioNoExisteException;

  /**
   * javadoc commet.
   */
  public abstract List<DTOrganizador> listarOrganizadores();
  
  /**
   * javadoc commet.
   */
  public abstract List<DTAsistente> listarAsistentes();
  
  /**
   * javadoc commet.
   */
  public abstract List<String> listarRegistrosAsistente(String nicknameAsistente);

  /**
   * javadoc commet.
   */
  public abstract Map<String, String> listarRegistrosYEdicionAsistente(String nicknameAsistente);

  /**
   * javadoc commet.
   */
  public abstract DTRegistro infoRegistroDeAsistente(String nicknameAsistente,
      String nombreEdicion);

  /**
   * javadoc commet.
   */
  public abstract void editarAsistente(String nickname, String nombre, String apellido,
      LocalDate nacimiento, String img, String password);

  /**
   * javadoc commet.
   */
  public abstract void editarOrganizador(String nickname, String nombre, String descripcion,
      String sitioWeb, String img, String password);

  /**
   * javadoc commet.
   */
  public abstract boolean autenticarUsuario(String email, String password)
      throws UsuarioNoExisteException;
  
  /**
   * javadoc commet.
   */
  public abstract DTUsuario obtenerUsuarioEmail(String email) throws UsuarioNoExisteException;

  /**
   * javadoc commet.
   */
  public abstract String obtenerTipoUsuario(String nick);

  /**
   * javadoc commet.
   */
  public abstract List<String> listarEdicionesAceptadasDeOrganizador(String nicknameOrganizador);

  /**
   * javadoc commet.
   */
  public abstract void altaRegistroConCodigo(String nicknameAsistente, String nombreEdicion,
      String nombreTipoRegistro, String codigo, LocalDate fechaAlta) throws IOException;

  /**
   * javadoc commet.
   */
  public abstract List<String> listarEdicionesDeOrganizador(String nicknameOrg);
  
  public abstract boolean seguirAUsuario(String nicknameASeguir, String nicknameSeguidor);
  public abstract boolean dejarDeSeguirAUsuario(String nicknameASeguir, String nicknameSeguidor);
  public abstract List<DTUsuario> getSeguidoresDeUsuario(String nickname);
  public abstract List<DTUsuario> getSeguidosDeUsuario(String nickname);
  
  public abstract void registrarAsistencia(String nickname, String nombreEdicion) throws IOException;
  
}
