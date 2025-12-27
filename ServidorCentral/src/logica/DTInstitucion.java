package logica;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;



/**
 * DTInstitucion
 */
public class DTInstitucion {
  private String nombre;
  private String descripcion;
  private String sitioWeb;
  private String img;

  /**
   * Constructor DTInstitucion
   */
  public DTInstitucion(String nombre, String descripcion, String sitioWeb, String img) {
    this.setNombre(nombre);
    this.setDescripcion(descripcion);
    this.setSitioWeb(sitioWeb);
    this.setImg(img);
  }

  /**
   * Constructor DTInstitucion sin argumentos
   */
  public DTInstitucion() {
  }

  /**
   * javadoc commet.
   */
  public String toString() {
    return getNombre();
  }
  
  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getImg() {
    return img;
  }

  public void setImg(String img) {
    this.img = img;
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
}
