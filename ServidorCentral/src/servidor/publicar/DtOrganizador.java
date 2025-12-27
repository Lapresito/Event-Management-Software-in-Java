package servidor.publicar;

import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;

/**
 * DTOrganizador → Dtorganizador (versión para Web Services)
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class DtOrganizador extends DtUsuario {

    private String descripcion;
    private String sitioWeb;
    private DtEdicion[] ediciones;

    /**
     * Constructor vacío requerido por JAXB
     */
    public DtOrganizador() {
        super();
    }

    /**
     * Constructor que recibe un DTOrganizador de la lógica
     */
    public DtOrganizador(logica.DTOrganizador dto) {
        super(dto);
        this.descripcion = dto.getDescripcion();
        this.sitioWeb = dto.getSitioWeb();

        if (dto.getEdiciones() != null) {
            this.ediciones = new DtEdicion[dto.getEdiciones().length];
            for (int i = 0; i < dto.getEdiciones().length; i++) {
                this.ediciones[i] = new DtEdicion(dto.getEdiciones()[i]);
            }
        }
    }

    // ===== Getters =====
    public String getDescripcion() {
        return descripcion;
    }

    public String getSitioWeb() {
        return sitioWeb;
    }

    public DtEdicion[] getEdiciones() {
        return ediciones;
    }

    // ===== Setters =====
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setSitioWeb(String sitioWeb) {
        this.sitioWeb = sitioWeb;
    }

    public void setEdiciones(DtEdicion[] ediciones) {
        this.ediciones = ediciones;
    }
}
