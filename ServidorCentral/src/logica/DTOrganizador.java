package logica;
import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;


/**
 * DTOrganizador
 */
public class DTOrganizador extends DTUsuario {
  private String descripcion;
  private String sitioWeb;
  private DTEdicion[] ediciones;

  /**
   * Constructor de DTOrganizador
   */
  public DTOrganizador(String nickname, String email, String nombre, String desc, String web, DTEdicion[] eds, String img, List<DTUsuario> seguidos, List<DTUsuario> seguidores) {
    super(nickname, email, nombre, img, seguidos, seguidores);
    setDescripcion(desc);
    setSitioWeb(web);
    setEdiciones(eds);
  }

  /**
   * Constructor de DTOrganizador sin argumentos
   */
  public DTOrganizador() {
      super();
  }


  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public String getSitioWeb() {
    return sitioWeb;
  }

  public void setSitioWeb(String sitioWeb) {
    this.sitioWeb = sitioWeb;
  }

  public DTEdicion[] getEdiciones() {
    return ediciones;
  }

  public void setEdiciones(DTEdicion[] ediciones) {
    this.ediciones = ediciones;
  }

}
