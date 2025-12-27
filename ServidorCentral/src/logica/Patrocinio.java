package logica;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlSchemaType;

/**
 * javadoc commet.
 */
public class Patrocinio {
  private String codigo;
  private int monto;
  private NivelPatrocinio nivel;
  private int cupo;
  private int cantRegistros;
  private LocalDate fechaAlta;
  private Edicion edicion;
  private Institucion institucion;
  private TipoRegistro tipoRegistro;
  private List<Registro> registros = new ArrayList<>();

  
  public DTPatrocinio getDT() {
    return new DTPatrocinio(this);
  }

  public String getCodigo() {
    return codigo;
  }

  public void setCodigo(String codigo) {
    this.codigo = codigo;
  }

  public int getMonto() {
    return monto;
  }

  public void setMonto(int monto) {
    this.monto = monto;
  }

  public NivelPatrocinio getNivel() {
    return nivel;
  }

  public void setNivel(NivelPatrocinio nivel) {
    this.nivel = nivel;
  }

  public int getCupo() {
    return cupo;
  }

  public void setCupo(int cupo) {
    this.cupo = cupo;
  }

  public LocalDate getFechaAlta() {
    return fechaAlta;
  }

  public void setFechaAlta(LocalDate fechaAlta) {
    this.fechaAlta = fechaAlta;
  }

  public Edicion getEdicion() {
    return edicion;
  }

  public void setEdicion(Edicion edicion) {
    this.edicion = edicion;
  }

  public Institucion getInstitucion() {
    return institucion;
  }

  public void setInstitucion(Institucion institucion) {
    this.institucion = institucion;
  }

  // Getter y Setter para tipoRegistro
  public TipoRegistro getTipoRegistro() {
    return tipoRegistro;
  }

  public void setTipoRegistro(TipoRegistro tipoRegistro) {
    this.tipoRegistro = tipoRegistro;
  }

  /**
   * javadoc commet.
   */
  public void asociarRegistro(Registro nuevoRegistro) {
    registros.add(nuevoRegistro);
  }

  public List<Registro> getRegistros() {
    return registros;
  }

  /**
   * javadoc commet.
   */
  public void setRegistros(Registro registro) {
    registros.add(registro);
  }

  /**
   * javadoc commet.
   */
  public Patrocinio(Institucion institucion, Edicion edicion, TipoRegistro tipoRegistro,
      NivelPatrocinio nivel, int monto, int cupo, LocalDate fechaAlta, String codigo) {
    this.institucion = institucion;
    this.edicion = edicion;
    this.tipoRegistro = tipoRegistro;
    this.nivel = nivel;
    this.monto = monto;
    this.cupo = cupo;
    this.cantRegistros = 0;
    this.codigo = codigo;
    this.fechaAlta = (fechaAlta != null) ? fechaAlta : LocalDate.now();
  }

  public int getCantRegistros() {
    return cantRegistros;
  }

  /**
   * javadoc commet.
   */
  public void sumarRegistro() {
    if (cantRegistros < cupo) {
      cantRegistros += 1;
    }
  }

  /**
   * javadoc commet.
   */
  public boolean quedanCuposDisponibles() {
    return cantRegistros != cupo;
  }

}
