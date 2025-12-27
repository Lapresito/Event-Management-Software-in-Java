package logica;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * javadoc commet.
 */
public class TipoRegistro {
  private String nombre;
  private String descripcion;
  private int costo;
  private int cupo;
  private Edicion edicion;
  private List<Registro> registros = new ArrayList<>();
  private Map<String, Patrocinio> patrocinios = new HashMap<>();
  private int cuposDisponibles;

  /**
   * javadoc commet.
   */
  public TipoRegistro(String nombreTipo, String descripcion, int costo, int cupo, Edicion edicion) {
    this.nombre = nombreTipo;
    this.setDescripcion(descripcion);
    this.setCosto(costo);
    this.setCupo(cupo);
    this.setEdicion(edicion);
    this.cuposDisponibles = cupo;
  }

  public DTTipoRegistro getDT() {
    return new DTTipoRegistro(this);
  }

  public String getNombre() {
    return nombre;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public int getCosto() {
    return costo;
  }

  public void setCosto(int costo) {
    this.costo = costo;
  }

  public int getCupo() {
    return cupo;
  }

  public void setCupo(int cupo) {
    this.cupo = cupo;
  }

  public Edicion getEdicion() {
    return edicion;
  }

  public void setEdicion(Edicion edicion) {
    this.edicion = edicion;
  }

  public int getCantRegistros() {
    return registros.size();
  }

  /**
   * javadoc commet.
   */
  public boolean tieneCupoDisponible() {
    return cuposDisponibles > 0;
  }

  public int getCuposDisponibles() {
    return cuposDisponibles;
  }

  /**
   * javadoc commet.
   */
  public void anularDisponibilidadDeCupos() {
    cuposDisponibles = 0;
  }

  /**
   * javadoc commet.
   */
  public void decrementarCupo() {
    if (cuposDisponibles > 0) {
      cuposDisponibles = cuposDisponibles - 1;
    }
  }

  /**
   * javadoc commet.
   */
  public void agregarPatrocinio(Patrocinio patroc) {
    String codigo = patroc.getCodigo();
    patrocinios.put(codigo, patroc);
  }

  public Map<String, Patrocinio> getPatrocinios() {
    return patrocinios;
  }
  
  public List<DTPatrocinio> getDTPatrocinios() {
	  List<DTPatrocinio> DTPatrocinios = new ArrayList<>();
	  patrocinios.forEach((codigo, patrocinio)->{
		  DTPatrocinios.add(patrocinio.getDT());  
	  });
	  return DTPatrocinios;
  }
}
