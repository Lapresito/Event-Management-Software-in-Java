package logica;

/**
 * javadoc commet.
 */
public class Fabrica {

  private static Fabrica instancia;

  private Fabrica() {
  };

  /**
   * javadoc commet.
   */
  public static Fabrica getInstance() {
    if (instancia == null) {
      instancia = new Fabrica();
    }
    return instancia;
  }

  public IUsuariosController getIUsuariosController() {
    return new UsuariosController();
  }

  public IInstitucionesController getIInstitucionesController() {
    return new InstitucionesController();
  }

  public IEventosController getIControladorEventos() {
    return new EventosController();
  }
}