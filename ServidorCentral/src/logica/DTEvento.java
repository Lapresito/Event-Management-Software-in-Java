package logica;

import java.time.LocalDate;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlSchemaType;



/**
 * DTEvento
 */
public class DTEvento {
  private String nombre;
  private String descripcion;
  private List<String> categorias;
  private List<String> ediciones;
  private String sigla;
  private LocalDate fechaAlta;
  private String Img;
  private boolean finalizado;
  private int visitas;

  /**
   * Constructor DTEvento
   */
  public DTEvento(Evento evento) {
    this.nombre = evento.getNombre();
    this.Img = evento.getImg();
    this.descripcion = evento.getDescripcion();
    this.categorias = evento.ListarCategorias();
    this.ediciones = evento.ListarEdiciones();
    this.sigla = evento.getSigla();
    this.fechaAlta = evento.getFechaAlta();
    this.finalizado = evento.isFinalizado();
    this.visitas = evento.getVisitas();
  }

  /**
   * Constructor DTEvento sin argumentos
   */
  public DTEvento() {
  }

  // ===== Getters =====
  public String getNombre() {
      return nombre;
  }
  
  public String getDescripcion() {
      return descripcion;
  }

  public List<String> getCategorias() {
      return categorias;
  }

  public List<String> getEdiciones() {
      return ediciones;
  }

  public String getSigla() {
      return sigla;
  }

  public LocalDate getFechaAlta() {
      return fechaAlta;
  }

  public String getImg() {
      return Img;
  }

  // ===== Setters =====
  public void setNombre(String nombre) {
      this.nombre = nombre;
  }

  public void setDescripcion(String descripcion) {
      this.descripcion = descripcion;
  }

  public void setCategorias(List<String> categorias) {
      this.categorias = categorias;
  }

  public void setEdiciones(List<String> ediciones) {
      this.ediciones = ediciones;
  }

  public void setSigla(String sigla) {
      this.sigla = sigla;
  }

  public void setFechaAlta(LocalDate fechaAlta) {
      this.fechaAlta = fechaAlta;
  }

  public void setImg(String img) {
      this.Img = img;
  }

  public boolean isFinalizado() {
	return finalizado;
  }

  public void setFinalizado(boolean finalizado) {
	this.finalizado = finalizado;
  }

  public int getVisitas() {
	return visitas;
  }

  public void setVisitas(int visitas) {
	this.visitas = visitas;
  }
}
