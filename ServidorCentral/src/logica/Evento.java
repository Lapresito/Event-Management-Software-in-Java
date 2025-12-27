package logica;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import jakarta.xml.bind.annotation.XmlSchemaType;

/**
 * javadoc commet.
 */
public class Evento {

  private String nombre;
  private String sigla;
  private String descripcion;
  private LocalDate fechaAlta;
  private Map<String, Edicion> ediciones = new HashMap<>();
  private Map<String, Categoria> categorias = new HashMap<>();
  private String img;
  private boolean finalizado;
  private int visitas;
  
  

  // Constructor
  /**
   * javadoc commet.
   */
  public Evento(String nombre, String sigla, String descripcion, Map<String, Categoria> categorias, LocalDate fechaAlta, String img) {
    this.nombre = nombre;
    this.sigla = sigla;
    this.descripcion = descripcion;
    this.fechaAlta = (fechaAlta != null) ? fechaAlta : LocalDate.now();
    this.categorias = categorias;
    this.setImg(img);
    this.finalizado = false;
    this.visitas = 0;
  }

  // Listar los nombres de las ediciones

  /**
   * javadoc commet.
   */
  public List<String> ListarEdiciones() {
    List<String> listaEdiciones = new ArrayList<>(ediciones.keySet());
    return listaEdiciones;
  }

  /**
   * javadoc commet.
   */
  public List<String> ListarEdicionesIngresadas() {
    Map<String, Edicion> ingresadas = this.ediciones.entrySet().stream()
        .filter(entry -> entry.getValue().getEstado() == EstadoEdicion.INGRESADA)
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    List<String> listaEdiciones = new ArrayList<>(ingresadas.keySet());
    return listaEdiciones;
  }

  /**
   * javadoc commet.
   */
  public List<String> ListarEdicionesOrganizador(String organizador) {
    Map<String, Edicion> porOrganizador = this.ediciones.entrySet().stream()
        .filter(entry -> entry.getValue().getOrganizador().getNickname() == organizador)
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    List<String> listaEdiciones = new ArrayList<>(porOrganizador.keySet());
    return listaEdiciones;
  }

  // Listar los nombres de las categorias
  /**
   * javadoc commet.
   */
  public List<String> ListarCategorias() {
    List<String> listaCategorias = new ArrayList<>(categorias.keySet());
    return listaCategorias;
  }

  // Getter y setters
  public Map<String, Edicion> getEdiciones() {
    return ediciones;
  }

  /**
   * javadoc commet.
   */
  public Edicion getEdicion(String nombreEdicion) {
    return ediciones.get(nombreEdicion);
  }

  /**
   * javadoc commet.
   */
  public void setEdicion(Edicion nuevaEdicion) {
    ediciones.put(nuevaEdicion.getNombre(), nuevaEdicion);
  }

  public Map<String, Categoria> getCategorias() {
    return categorias;
  }

  /**
   * javadoc commet.
   */
  public Categoria getCategoria(String nombreCategoria) {
    return categorias.get(nombreCategoria);
  }

  /**
   * javadoc commet.
   */
  public void setCategoria(Categoria nuevaCategoria) {
    categorias.put(nuevaCategoria.getNombre(), nuevaCategoria);
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nuevoNombre) {
    this.nombre = nuevoNombre;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String nuevaDesc) {
    this.nombre = nuevaDesc;
  }

  public String getSigla() {
    return sigla;
  }

  public void setSigla(String nuevaSigla) {
    this.nombre = nuevaSigla;
  }

  public LocalDate getFechaAlta() {
    return fechaAlta;
  }

  
  public DTEvento getDTEvento() {
    return new DTEvento(this);
  }

  /**
   * javadoc commet.
   */
  public void agregarEdicion(Edicion edicion) {
    ediciones.put(edicion.getNombre(), edicion);
  }

  public String getImg() {
    return img;
  }

  public void setImg(String img) {
    this.img = img;
  }

  public int getVisitas() {
	return visitas;
  }

  public void setVisitas(int visitas) {
	this.visitas = visitas;
  }

  public boolean isFinalizado() {
	return finalizado;
  }

  public void setFinalizado(boolean finalizado) {
	this.finalizado = finalizado;
  }
}
