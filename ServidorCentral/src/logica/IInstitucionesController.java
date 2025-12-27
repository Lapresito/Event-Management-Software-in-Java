package logica;

import java.io.IOException;

/**
 * javadoc commet.
 */
public interface IInstitucionesController {

  /**
   * javadoc commet.
   */
  public abstract DTInstitucion[] getInstituciones();

  /**
   * javadoc commet.
   */
  public abstract void altaInstitucion(String nonmbreInstitucion, String descripcion,
      String sitioWeb, String img) throws IOException;

}
