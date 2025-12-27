package logica;

import java.time.LocalDate;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * DTEdicion
 */
public class DTEdicion {
  private String nombreEdicion;
  private String nombreEvento;
  private String organizador;
  private String sigla;
  private String ciudad;
  private String pais;
  private LocalDate fechaAlta;
  private LocalDate fechaIni;
  private LocalDate fechaFin;
  private String imagen;
  private EstadoEdicion estado;
  private String videoURL;

  // Faltan listas con DTregistros, TiposRegistros, y DTpatrocinios

  /**
   * Constructor de DTEdicion sin arumentos
   */
  public DTEdicion() {
  }

  /**
   * Constructor de DTEdicion 
   */
  public DTEdicion(String nombre, String evento) {
    nombreEdicion = nombre;
    setNombreEvento(evento);
  }

  /**
   * Constructor de DTEdicion 
   */
  public DTEdicion(Edicion edicion) {
    nombreEdicion = edicion.getNombre();
    nombreEvento = edicion.getEvento().getNombre();
    organizador = edicion.getOrganizador().getNickname();
    sigla = edicion.getSigla();
    ciudad = edicion.getCiudad();
    pais = edicion.getPais();
    fechaAlta = edicion.getFechaAlta();
    fechaIni = edicion.getFechaIni();
    fechaFin = edicion.getFechaFin();
    imagen = edicion.getImg();
    estado = edicion.getEstado();
    videoURL = edicion.getVideoURL();
  }

  /**
   * javadoc commet.
   */
  public String toString() {
    return getNombreEdicion();
  }

  // ===== Getters =====
  public String getNombreEdicion() {
      return nombreEdicion;
  }

  public String getNombreEvento() {
      return nombreEvento;
  }

  public String getOrganizador() {
      return organizador;
  }

  public String getSigla() {
      return sigla;
  }

  public String getCiudad() {
      return ciudad;
  }

  public String getPais() {
      return pais;
  }

  public LocalDate getFechaAlta() {
      return fechaAlta;
  }

  public LocalDate getFechaIni() {
      return fechaIni;
  }

  public LocalDate getFechaFin() {
      return fechaFin;
  }

  public String getImagen() {
      return imagen;
  }

  public EstadoEdicion getEstado() {
      return estado;
  }

  // ===== Setters =====
  public void setNombreEdicion(String nombreEdicion) {
      this.nombreEdicion = nombreEdicion;
  }

  public void setNombreEvento(String nombreEvento) {
      this.nombreEvento = nombreEvento;
  }

  public void setOrganizador(String organizador) {
      this.organizador = organizador;
  }

  public void setSigla(String sigla) {
      this.sigla = sigla;
  }

  public void setCiudad(String ciudad) {
      this.ciudad = ciudad;
  }

  public void setPais(String pais) {
      this.pais = pais;
  }

  public void setFechaAlta(LocalDate fechaAlta) {
      this.fechaAlta = fechaAlta;
  }

  public void setFechaIni(LocalDate fechaIni) {
      this.fechaIni = fechaIni;
  }

  public void setFechaFin(LocalDate fechaFin) {
      this.fechaFin = fechaFin;
  }

  public void setImagen(String imagen) {
      this.imagen = imagen;
  }

  public void setEstado(EstadoEdicion estado) {
      this.estado = estado;
  }

  public String getVideoURL() {
	return videoURL;
  }

  public void setVideoURL(String videoURL) {
	this.videoURL = videoURL;
  }
}