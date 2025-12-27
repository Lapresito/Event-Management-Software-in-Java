package logica;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;

/**
 * javadoc commet.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public enum NivelPatrocinio {
  PLATINO("Platino"), PLATA("Plata"), ORO("Oro"), BRONCE("Bronce");

  private final String displayName;

  NivelPatrocinio(String displayName) {
    this.displayName = displayName;
  }

  @Override
  public String toString() {
    return displayName;
  }
}