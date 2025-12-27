package servidor.publicar;

import java.util.Date;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;

/**
 * DTRegistro → Dtregistro (versión para Web Services)
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class DtRegistro {

    private String nombreEdicion;
    private String nombreEvento;
    private Date fechaAlta;
    private int costo;
    private String nickAsistente;
    private boolean asistio;

    /**
     * Constructor vacío requerido por JAXB
     */
    public DtRegistro() {
    }

    /**
     * Constructor que recibe un DTRegistro de la lógica
     */
    public DtRegistro(logica.DTRegistro dto) {
        this.nombreEdicion = dto.getNombreEdicion();
        this.nombreEvento = dto.getNombreEvento();
        this.costo = dto.getCosto();
        this.nickAsistente = dto.getNickAsistente();
        this.asistio = dto.isAsistio();

        if (dto.getFechaAlta() != null)
            this.fechaAlta = java.sql.Date.valueOf(dto.getFechaAlta());
    }

    // ===== Getters =====
    public String getNombreEdicion() {
        return nombreEdicion;
    }

    public String getNombreEvento() {
        return nombreEvento;
    }

    public Date getFechaAlta() {
        return fechaAlta;
    }

    public int getCosto() {
        return costo;
    }

    public String getNickAsistente() {
        return nickAsistente;
    }

    public boolean isAsistio() {
        return asistio;
    }

    // ===== Setters =====
    public void setNombreEdicion(String nombreEdicion) {
        this.nombreEdicion = nombreEdicion;
    }

    public void setNombreEvento(String nombreEvento) {
        this.nombreEvento = nombreEvento;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public void setCosto(int costo) {
        this.costo = costo;
    }

    public void setNickAsistente(String nickAsistente) {
        this.nickAsistente = nickAsistente;
    }

    public void setAsistio(boolean asistio) {
        this.asistio = asistio;
    }

    @Override
    public String toString() {
        return nickAsistente + " - " + nombreEvento + " (" + nombreEdicion + ")";
    }
}
