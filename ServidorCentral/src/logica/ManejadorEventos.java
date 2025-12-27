package logica;

import java.util.HashMap;
import java.util.Map;

/**
 * javadoc commet.
 */
public class ManejadorEventos {

  private Map<String, Evento> eventos = new HashMap<>();
  private Map<String, Edicion> ediciones = new HashMap<>();
  private Map<String, Categoria> categorias = new HashMap<>();
  private static ManejadorEventos Instancia;

  // Constructor privado
  private ManejadorEventos() {
  }

  /**
   * javadoc commet.
   */
  public static ManejadorEventos getInstancia() {
    if (Instancia == null) {
      Instancia = new ManejadorEventos();
    }
    return Instancia;
  }

  /**
   * javadoc commet.
   */
  public boolean existeEvento(String nombre) {
    return eventos.containsKey(nombre);
  }

  /**
   * javadoc commet.
   */
  public boolean existeEdicion(String nombre) {
    return ediciones.containsKey(nombre);
  }

  /**
   * javadoc commet.
   */
  public boolean existeCategoria(String nombre) {
    return categorias.containsKey(nombre);
  }

  public Map<String, Evento> getEventos() {
    return eventos;
  }
  


  public Map<String, Edicion> getEdiciones() {
    return ediciones;
  }

  public Map<String, Categoria> getCategorias() {
    return categorias;
  }

  /**
   * javadoc commet.
   */
  public Evento getEvento(String nombre) {
    if (existeEvento(nombre)) {
      return eventos.get(nombre);
    }
    return null;
  }

  /**
   * javadoc commet.
   */
  public Edicion getEdicion(String nombre) {
    if (existeEdicion(nombre)) {
      return ediciones.get(nombre);
    }
    return null;
  }

  /**
   * javadoc commet.
   */
  public Categoria getCategoria(String nombre) {
    if (existeCategoria(nombre)) {
      return categorias.get(nombre);
    }
    return null;
  }

  /**
   * javadoc commet.
   */
  public void eliminarEvento(String evento) {
    eventos.remove(evento);
  }
  
  /**
   * javadoc commet.
   */
  public void eliminarEvento(Evento evento) {
    eventos.remove(evento.getNombre());
  }

  /**
   * javadoc commet.
   */
  public void eliminarEdicion(String edicion) {
    ediciones.remove(edicion);
  }
  
  /**
   * javadoc commet.
   */
  public void eliminarEdicion(Edicion edicion) {
    ediciones.remove(edicion.getNombre());
  }
  
  /**
   * javadoc commet.
   */
  public void eliminarCategoria(String categoria) {
    categorias.remove(categoria);
  }

  /**
   * javadoc commet.
   */
  public void eliminarCategoria(Categoria categoria) {
    categorias.remove(categoria.getNombre());
  }

  /**
   * javadoc commet.
   */
  public void agregarEvento(Evento evento) {
    eventos.put(evento.getNombre(), evento);
  }

  /**
   * javadoc commet.
   */
  public void agregarEdicion(Edicion edicion) {
    ediciones.put(edicion.getNombre(), edicion);
  }

  /**
   * javadoc commet.
   */
  public void agregarCategoria(Categoria categoria) {
    categorias.put(categoria.getNombre(), categoria);
  }

  /**
   * javadoc commet.
   */
  public void clearManejador() {
    eventos.clear();
    ediciones.clear();
    categorias.clear();
  }

}
