package logica;

import java.time.LocalDate;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlSchemaType;

/**
 * DTRegistro
 */
public class DTRegistro {
  private String nombreEdicion;
  private String nombreEvento;
  private LocalDate fechaAlta;
  private int costo;
  private String nickAsistente;
  private boolean asistio;


  /**
   * Constructor de DTRegistro
   */
  public DTRegistro(String nombreEdicion, String nombreEvento, int costo, LocalDate fechaAlta, String nick, boolean asistio) {
    this.nombreEdicion = nombreEdicion;
    this.nombreEvento = nombreEvento;
    this.costo = costo;
    this.fechaAlta = fechaAlta;
    this.nickAsistente = nick;
    this.asistio = asistio;
  }

  /**
   * Constructor de DTRegistro sin argumentos
   */
  public DTRegistro() {
  }

  // ===== Getters =====
  public String getNombreEdicion() {
      return nombreEdicion;
  }

  public String getNombreEvento() {
      return nombreEvento;
  }

  public LocalDate getFechaAlta() {
      return fechaAlta;
  }

  public int getCosto() {
      return costo;
  }

  public String getNickAsistente() {
      return nickAsistente;
  }

  // ===== Setters =====
  public void setNombreEdicion(String nombreEdicion) {
      this.nombreEdicion = nombreEdicion;
  }

  public void setNombreEvento(String nombreEvento) {
      this.nombreEvento = nombreEvento;
  }

  public void setFechaAlta(LocalDate fechaAlta) {
      this.fechaAlta = fechaAlta;
  }

  public void setCosto(int costo) {
      this.costo = costo;
  }

  public void setNickAsistente(String nickAsistente) {
      this.nickAsistente = nickAsistente;
  }

  public boolean isAsistio() {
	return asistio;
  }

  public void setAsistio(boolean asistio) {
	this.asistio = asistio;
  }
  
  public String generarPDFAsistencia() {
	  return "funcionalidad pendiente je ";
  }
}
