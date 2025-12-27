package logica;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ManejadorInstituciones {
  private Map<String, Institucion> instituciones;
  private static ManejadorInstituciones instancia = null;

  private ManejadorInstituciones() {
    instituciones = new HashMap<String, Institucion>();
  }

  public static ManejadorInstituciones getInstance() {
    if (instancia == null)
      instancia = new ManejadorInstituciones();
    return instancia;
  }

  public void addInstitucion(Institucion ins) {
    String nombre = ins.getNombre();
    instituciones.put(nombre, ins);
  }

  public Institucion obtenerInstitucion(String nombreInstitucion) {
    return instituciones.get(nombreInstitucion);
  }

  public boolean existeInstitucion(String nombre) {
    return instituciones.containsKey(nombre);
  }

  public Institucion[] getInstituciones() { // devuelve un array de instituciones
    if (instituciones.isEmpty()) {
      return null;
    } else {
      Collection<Institucion> insts = instituciones.values();
      Object[] object = insts.toArray();
      Institucion[] instituciones = new Institucion[object.length];
      for (int i = 0; i < object.length; i++) {
        instituciones[i] = (Institucion) object[i];
      }

      return instituciones;
    }
  }

  public void clearManejador() {
    instituciones.clear();
  }

}
