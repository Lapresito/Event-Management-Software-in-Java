package logica;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;

/**
 * javadoc commet.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public enum EstadoEdicion {
  INGRESADA("Ingresada"), ACEPTADA("Aceptada"), RECHAZADA("Rechazada");

  private final String displayName;

  EstadoEdicion(String displayName) {
    this.displayName = displayName;
  }

  @Override
  public String toString() {
    return displayName;
  }
}
