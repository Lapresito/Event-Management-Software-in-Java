package logica;

import java.time.LocalDate;
import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * DTAsistente
 */
public class DTAsistente extends DTUsuario {
  private String apellido;
  private LocalDate nacimiento;
  private String institucion;
  private String[] registros; // nickname de usarios registrados

  /**
   * Constructor DTAsistente.
   */
  public DTAsistente(String nickname, String email, String nombre, String apellido,LocalDate nacimiento, String institucion, String[] registros, String img, List<DTUsuario> seguidos, List<DTUsuario> seguidores) {
    super(nickname, email, nombre, img, seguidos, seguidores);
    this.setApellido(apellido);
    this.setNacimiento(nacimiento);
    this.setInstitucion(institucion);
    this.setRegistros(registros);
  }

  /**
   * Constructor DTAsistente sin argumenos.
   */
  public DTAsistente() {
    super();
  }

  public String getApellido() {
    return apellido;
  }

  public void setApellido(String apellido) {
    this.apellido = apellido;
  }

  public LocalDate getNacimiento() {
    return nacimiento;
  }

  public void setNacimiento(LocalDate nacimiento) {
    this.nacimiento = nacimiento;
  }

  public String getInstitucion() {
    return institucion;
  }

  public void setInstitucion(String institucion) {
    this.institucion = institucion;
  }

  public String[] getRegistros() {
    return registros;
  }

  public void setRegistros(String[] registros) {
    this.registros = registros;
  }

}
