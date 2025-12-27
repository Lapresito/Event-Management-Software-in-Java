package logica;

import java.util.HashMap;
import java.util.Map;

/**
 * javadoc commet.
 */
public class Institucion {
  private String nombre;
  private String descripcion;
  private String sitioWeb;
  private Map<String, Asistente> asistentes = new HashMap<String, Asistente>();
  private Map<String, Patrocinio> patrocinios = new HashMap<>();
  private String img;

  /**
   * javadoc commet.
   */
  public Institucion(String nombre, String descripcion, String sitioWeb, String img) {
    this.nombre = nombre;
    this.setDescripcion(descripcion);
    this.setSitioWeb(sitioWeb);
    this.img = img;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  /**
   * javadoc commet.
   */
  public Asistente getAsistente(String nicknameAsistente) {
    return asistentes.get(nicknameAsistente);
  }
  /**
   * javadoc commet.
   */

  public void agregarPatrocinio(Patrocinio patrocinio) {
    String codigo = patrocinio.getCodigo();
    patrocinios.put(codigo, patrocinio);
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

  public String getImg() {
    return img;
  }

  public void setImg(String img) {
    this.img = img;
  }

}
