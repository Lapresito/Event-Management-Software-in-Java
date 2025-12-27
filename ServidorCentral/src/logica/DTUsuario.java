package logica;


import java.util.List;
import java.util.Map;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;

/**
 * Datatype para transportar la información de un usuario entre capa lógica y de presentación. En
 * Java los datatypes se definen con setters y getters, y se denominan JavaBean.
 */
public class DTUsuario {

  private String nickname;
  private String email;
  private String nombre;
  private String img;
  private List<DTUsuario> seguidos;
  private List<DTUsuario> seguidores;

  /**
   * Constructor DTUsuario
   */
  public DTUsuario(String nickname, String email, String nombre, String img, List<DTUsuario> seguidos, List<DTUsuario> seguidores) {
    this.setNickname(nickname);
    this.setEmail(email);
    this.setNombre(nombre);
    this.setImg(img);
    this.setSeguidos(seguidos);
    this.setSeguidores(seguidores);
  }

  /**
   * Constructor DTUsuario sin argumentos
   */
  public DTUsuario() {
  }

  /**
   * Sirve para mostrar textualmente la información del usuario, por ejemplo en un ComboBox.
   */
  public String toString() {
    return getNickname() + " (" + getNombre() + ")";
  }

// ===== Getters =====
  public String getNickname() {
      return nickname;
  }

  public String getEmail() {
      return email;
  }

  public String getNombre() {
      return nombre;
  }

  public String getImg() {
      return img;
  }

  // ===== Setters =====
  public void setNickname(String nickname) {
      this.nickname = nickname;
  }

  public void setEmail(String email) {
      this.email = email;
  }

  public void setNombre(String nombre) {
      this.nombre = nombre;
  }

  public void setImg(String img) {
      this.img = img;
  }

  public List<DTUsuario> getSeguidos() {
	return seguidos;
  }

  public void setSeguidos(List<DTUsuario> seguidos) {
	this.seguidos = seguidos;
  }

  public List<DTUsuario> getSeguidores() {
	return seguidores;
  }

  public void setSeguidores(List<DTUsuario> seguidores) {
	this.seguidores = seguidores;
  }

}
