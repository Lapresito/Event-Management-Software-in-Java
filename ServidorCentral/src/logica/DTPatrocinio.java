package logica;

import java.time.LocalDate;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlSchemaType;

/**
 * DTPatrocinio
 */
public class DTPatrocinio {

  private String nombreInstitucion;
  private String nombreEdicion;
  private int monto;
  private String codigo;
  private LocalDate fechaAlta;
  private int cupos;
  private int cantRegistros;
  private NivelPatrocinio nivel;
  private String DTtipoRegistro;

  /**
   * Constructor de DTPatrocinio.
   */
  public DTPatrocinio(String nombreInstitucion, int monto, String codigo, LocalDate fechaAlta, int cupos, int cantRegistros, NivelPatrocinio nivel) {
    this.nombreInstitucion = nombreInstitucion;
    this.monto = monto;
    this.codigo = codigo;
    this.fechaAlta = (fechaAlta != null) ? fechaAlta : LocalDate.now();
    this.cupos = cupos;
    this.cantRegistros = cantRegistros;
    this.nivel = nivel;
  }

  /**
   * Constructor de DTPatrocinio.
   */
  public DTPatrocinio(Patrocinio patrocinio) {
    this.nombreInstitucion = patrocinio.getInstitucion().getNombre();
    this.nombreEdicion = patrocinio.getEdicion().getNombre();
    this.monto = patrocinio.getMonto();
    this.codigo = patrocinio.getCodigo();
    this.fechaAlta = patrocinio.getFechaAlta();
    this.cupos = patrocinio.getCupo();
    this.cantRegistros = patrocinio.getRegistros().size();
    this.nivel = patrocinio.getNivel();
    TipoRegistro tipoRegistro = patrocinio.getTipoRegistro();
    this.setDTtipoRegistro(tipoRegistro.getNombre());
  }

  /**
   * Constructor de DTPatrocinio sin argumentos.
   */
  public DTPatrocinio() {
  }


  // ===== Getters =====
  public String getNombreInstitucion() {
      return nombreInstitucion;
  }

  public String getNombreEdicion() {
      return nombreEdicion;
  }

  public int getMonto() {
      return monto;
  }

  public String getCodigo() {
      return codigo;
  }

  public LocalDate getFechaAlta() {
      return fechaAlta;
  }

  public int getCupos() {
      return cupos;
  }

  public int getCantRegistros() {
      return cantRegistros;
  }

  public NivelPatrocinio getNivel() {
      return nivel;
  }

  public String getDTtipoRegistro() {
      return DTtipoRegistro;
  }

  // ===== Setters =====
  public void setNombreInstitucion(String nombreInstitucion) {
      this.nombreInstitucion = nombreInstitucion;
  }

  public void setNombreEdicion(String nombreEdicion) {
      this.nombreEdicion = nombreEdicion;
  }

  public void setMonto(int monto) {
      this.monto = monto;
  }

  public void setCodigo(String codigo) {
      this.codigo = codigo;
  }

  public void setFechaAlta(LocalDate fechaAlta) {
      this.fechaAlta = fechaAlta;
  }

  public void setCupos(int cupos) {
      this.cupos = cupos;
  }

  public void setCantRegistros(int cantRegistros) {
      this.cantRegistros = cantRegistros;
  }

  public void setNivel(NivelPatrocinio nivel) {
      this.nivel = nivel;
  }

  public void setDTtipoRegistro(String string) {
      this.DTtipoRegistro = string;
  }
}
