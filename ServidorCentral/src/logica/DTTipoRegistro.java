package logica;

import java.util.List;
import java.util.Map;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;

/**
 * DTTipoRegistro
 */
public class DTTipoRegistro {

  private String nombreEdicion;
  private String nombreTipoRegistro;
  private String descripcion;
  private int precio;
  private int cupo;
  private int cantRegistros;
  private List<DTPatrocinio> patrocinios;

  /**
   * Constructor de DTTipoRegistro
   */
  public DTTipoRegistro(String nombreEdicion, String nombreTipoRegistro, String descripcion, int precio, int cupo, int cantRegistros) {
    this.nombreEdicion = nombreEdicion;
    this.nombreTipoRegistro = nombreTipoRegistro;
    this.descripcion = descripcion;
    this.precio = precio;
    this.cupo = cupo;
    this.cantRegistros = cantRegistros;
  }

  /**
   * Constructor de DTTipoRegistro
   */
  public DTTipoRegistro(TipoRegistro tipoRegistro) {
    this.nombreEdicion = tipoRegistro.getEdicion().getNombre();
    this.nombreTipoRegistro = tipoRegistro.getNombre();
    this.descripcion = tipoRegistro.getDescripcion();
    this.precio = tipoRegistro.getCosto();
    this.cupo = tipoRegistro.getCupo();
    this.cantRegistros = tipoRegistro.getCantRegistros();
    this.patrocinios = tipoRegistro.getDTPatrocinios();
  }

    /**
   * Constructor de DTTipoRegistro sin argumentos
   */
  public DTTipoRegistro() {
  }

 // ===== Getters =====
  public String getNombreEdicion() {
      return nombreEdicion;
  }

  public String getNombreTipoRegistro() {
      return nombreTipoRegistro;
  }

  public String getDescripcion() {
      return descripcion;
  }

  public int getPrecio() {
      return precio;
  }

  public int getCupo() {
      return cupo;
  }

  public int getCantRegistros() {
      return cantRegistros;
  }

  public List<DTPatrocinio> getPatrocinios() {
      return patrocinios;
  }

  // ===== Setters =====
  public void setNombreEdicion(String nombreEdicion) {
      this.nombreEdicion = nombreEdicion;
  }

  public void setNombreTipoRegistro(String nombreTipoRegistro) {
      this.nombreTipoRegistro = nombreTipoRegistro;
  }

  public void setDescripcion(String descripcion) {
      this.descripcion = descripcion;
  }

  public void setPrecio(int precio) {
      this.precio = precio;
  }

  public void setCupo(int cupo) {
      this.cupo = cupo;
  }

  public void setCantRegistros(int cantRegistros) {
      this.cantRegistros = cantRegistros;
  }

  public void setPatrocinios(List<DTPatrocinio> patrocinios) {
      this.patrocinios = patrocinios;
  }
}
