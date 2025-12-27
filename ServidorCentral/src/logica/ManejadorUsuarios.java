package logica;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * javadoc commet.
 */
public class ManejadorUsuarios {
  private Map<String, Usuario> usuarios; // la clave es el nickname, igual que en los mapas de org y
  private Map<String, Usuario> usuariosPorEmail; // es para buscar los emails
  private Map<String, Organizador> organizadores;
  private Map<String, Asistente> asistentes;
  private static ManejadorUsuarios instancia = null;

  private ManejadorUsuarios() {
    usuarios = new HashMap<String, Usuario>();
    usuariosPorEmail = new HashMap<String, Usuario>();
    organizadores = new HashMap<String, Organizador>();
    asistentes = new HashMap<String, Asistente>();
  }

  /**
   * javadoc commet.
   */
  public static ManejadorUsuarios getinstance() {
    if (instancia == null) {
      instancia = new ManejadorUsuarios();
    }
    return instancia;
  }

  /**
   * javadoc commet.
   */
  public boolean esOrganizador(String nickname) {
    boolean existe = organizadores.containsKey(nickname);
    return existe;
  }

  /**
   * javadoc commet.
   */
  public String tipoUsuario(String nick) {
    if (organizadores.containsKey(nick)) {
      return "organizador";
    }
    if (asistentes.containsKey(nick)) {
      return "asistente";
    }
    return "desconocido";
  }

  /**
   * javadoc commet.
   */
  public void addOrganizador(Organizador usu) {
    String nick = usu.getNickname();
    String email = usu.getEmail();
    usuarios.put(nick, usu);
    organizadores.put(nick, usu);
    usuariosPorEmail.put(email, usu);
  }

  /**
   * javadoc commet.
   */
  public Organizador getOrganizador(String nick) {
    return organizadores.get(nick);
  }

  /**
   * javadoc commet.
   */
  public void addAsistente(Asistente usu) {
    String nick = usu.getNickname();
    String email = usu.getEmail();
    usuarios.put(nick, usu);
    asistentes.put(nick, usu);
    usuariosPorEmail.put(email, usu);
  }

  /**
   * javadoc commet.
   */
  public Asistente getAsistente(String nick) {
    return asistentes.get(nick);
  }

  /**
   * javadoc commet.
   */
  public Usuario obtenerUsuario(String nick) {
    return ((Usuario) usuarios.get(nick));
  }

  /**
   * javadoc commet.
   */
  public Usuario obtenerUsuarioEmail(String email) {
    return ((Usuario) usuariosPorEmail.get(email));
  }

  /**
   * javadoc commet.
   */
  public Usuario[] getUsuarios() { // devuelve un array de usuarios
    if (usuarios.isEmpty()) {
      return null;
    } else {
      Collection<Usuario> usrs = usuarios.values();
      Object[] object = usrs.toArray();
      Usuario[] usuarios = new Usuario[object.length];
      for (int i = 0; i < object.length; i++) {
        usuarios[i] = (Usuario) object[i];
      }

      return usuarios;
    }
  }

  /**
   * javadoc commet.
   */
  public Boolean existeEmail(String email) {
    if (usuarios.isEmpty()) {
      return false;
    } else {
      return usuariosPorEmail.containsKey(email);
    }
  }

  /**
   * javadoc commet.
   */
  public Usuario getUsuario(String nicknameUsuario) {
    return usuarios.get(nicknameUsuario);
  }

  public Map<String, Asistente> getAsistentes() {
    return asistentes;
  }

  public Map<String, Organizador> getOrganizadores() {
    return organizadores;
  }

  /**
   * javadoc commet.
   */
  public void limpiarManejador() {
    usuarios.clear();
    usuariosPorEmail.clear();
    organizadores.clear();
    asistentes.clear();
  }
}
