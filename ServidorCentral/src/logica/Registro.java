package logica;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import jakarta.xml.bind.annotation.XmlSchemaType;

/**
 * javadoc commet.
 */
public class Registro {
  private LocalDate fechaAlta;
  private int costo;
  private TipoRegistro tipoRegistro;
  private Edicion edicion;
  private Asistente asistente;
  private Patrocinio patrocinio;
  private boolean asistio;

  /**
   * javadoc commet.
   */
  public Registro(Edicion edic, LocalDate fechaAlta, int costo) {
    this.fechaAlta = (fechaAlta != null) ? fechaAlta : LocalDate.now();
    this.edicion = edic;
    this.costo = costo;
    this.asistio = false;
  }

  /**
   * javadoc commet.
   */
  public void asociarTipoRegistro(TipoRegistro tipoReg) {
    setTipoRegistro(tipoReg);
  }

  public int getCosto() {
    return costo;
  }

  public void setCosto(int costo) {
    this.costo = costo;
  }

  public LocalDate getFechaAlta() {
    return fechaAlta;
  }

  public Edicion getEdicion() {
    return edicion;
  }

  /**
   * javadoc commet.
   */
  public void asociarEdicion(Edicion edic) {
    this.edicion = edic;
  }

  /**
   * javadoc commet.
   */
  public void asociarAsistente(Asistente asist) {
    setAsistente(asist);
    String nombreEdicion = edicion.getNombre(); // primero tiene que estar cargada la edicion
    asist.asociarRegistro(nombreEdicion, this);
  }

  public String getNombreEdicion() {
    return edicion.getNombre();
  }

  public LocalDate getFecha() {
    return fechaAlta;
  }

  @Override
  public String toString() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    return "Registro del " + fechaAlta.format(formatter);
  }

  public Asistente getAsistente() {
    return asistente;
  }

  public void setAsistente(Asistente asistente) {
    this.asistente = asistente;
  }

  public Patrocinio getPatrocinio() {
    return patrocinio;
  }

  public void setPatrocinio(Patrocinio patrocinio) {
    this.patrocinio = patrocinio;
  }

  public TipoRegistro getTipoRegistro() {
    return tipoRegistro;
  }

  public void setTipoRegistro(TipoRegistro tipoRegistro) {
    this.tipoRegistro = tipoRegistro;
  }

  public boolean isAsistio() {
	return asistio;
  }

  public void setAsistio(boolean asistio) {
	this.asistio = asistio;
  }

}
