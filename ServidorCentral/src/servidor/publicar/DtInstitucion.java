package servidor.publicar;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;

/**
 * DTInstitucion → Dtinstitucion (versión para Web Services)
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class DtInstitucion {

    private String nombre;
    private String descripcion;
    private String sitioWeb;
    private String img;

    /**
     * Constructor vacío requerido por JAXB
     */
    public DtInstitucion() {
    }

    /**
     * Constructor que recibe un DTInstitucion de la lógica
     */
    public DtInstitucion(logica.DTInstitucion dto) {
        this.nombre = dto.getNombre();
        this.descripcion = dto.getDescripcion();
        this.sitioWeb = dto.getSitioWeb();
        this.img = dto.getImg();
    }

    // ===== Getters =====
    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getSitioWeb() {
        return sitioWeb;
    }

    public String getImg() {
        return img;
    }

    // ===== Setters =====
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setSitioWeb(String sitioWeb) {
        this.sitioWeb = sitioWeb;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
