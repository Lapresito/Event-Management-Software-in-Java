package logica;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * javadoc commet.
 */
public class Organizador extends Usuario {
  private String descripcion;
  private String sitioWeb;

  private Map<String, Edicion> ediciones; // clave: nombre de la edicion

  /**
   * javadoc commet.
   */
  public Organizador(String nickname, String email, String nombre, String descripcion, String img,
      String password) {
    super(nickname, email, nombre, img, password); // llama al constructor de Usuario
    this.setDescripcion(descripcion);
    this.setSitioWeb(""); // Se puede agregar luego pero es opcional

    this.ediciones = new HashMap<>();
  }
  
  public DTOrganizador getDTOrganizador(){
	 List<DTEdicion> ediciones = new ArrayList<>();

	 this.getEdiciones().forEach((nombre, edicion)->{
		 ediciones.add(edicion.getDataEdicion());
	 });
	 
	 return new DTOrganizador(this.getNickname(),  this.getEmail(), this.getNombre(), descripcion, sitioWeb, ediciones.toArray(new DTEdicion[0]), this.getImg(), new ArrayList<DTUsuario>(), new ArrayList<DTUsuario>());
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public String getSitioWeb() {
    return sitioWeb;
  }

  public void setSitioWeb(String sitioWeb) {
    this.sitioWeb = sitioWeb;
  }

  /**
   * javadoc commet.
   */
  public void asociarEdicion(String nombreEdicion, Edicion edicion) {
    ediciones.put(nombreEdicion, edicion);
  }

  /**
 * javadoc commet.
 */
  public Edicion getEdicion(String nombreEdicion) {
    return ediciones.get(nombreEdicion);
  }

  public Map<String, Edicion> getEdiciones() {
    return ediciones;
  }

}
