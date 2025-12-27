package servidor.publicar;

import java.util.Date;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlSchemaType;

/**
 * DTEdicion → Dtedicion (versión para Web Services)
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class DtEdicion {

    private String nombreEdicion;
    private String nombreEvento;
    private String organizador;
    private String sigla;
    private String ciudad;
    private String pais;
    private Date fechaAlta;
    private Date fechaIni;
    private Date fechaFin;
    private String imagen;
    private String estado;
    private String videoURL;

    /**
     * Constructor vacío requerido por JAXB
     */
    public DtEdicion() {
    }

    /**
     * Constructor que recibe un DTEdicion de la lógica
     */
    public DtEdicion(logica.DTEdicion dto) {
        this.nombreEdicion = dto.getNombreEdicion();
        this.nombreEvento = dto.getNombreEvento();
        this.organizador = dto.getOrganizador();
        this.sigla = dto.getSigla();
        this.ciudad = dto.getCiudad();
        this.pais = dto.getPais();

        if (dto.getFechaAlta() != null)
            this.fechaAlta = java.sql.Date.valueOf(dto.getFechaAlta());
        if (dto.getFechaIni() != null)
            this.fechaIni = java.sql.Date.valueOf(dto.getFechaIni());
        if (dto.getFechaFin() != null)
            this.fechaFin = java.sql.Date.valueOf(dto.getFechaFin());

        this.imagen = dto.getImagen();
        this.estado = (dto.getEstado() != null) ? dto.getEstado().toString() : null;
        this.videoURL = dto.getVideoURL();
    }

    // ===== Getters =====
    public String getNombreEdicion() {
        return nombreEdicion;
    }

    public String getNombreEvento() {
        return nombreEvento;
    }

    public String getOrganizador() {
        return organizador;
    }

    public String getSigla() {
        return sigla;
    }

    public String getCiudad() {
        return ciudad;
    }

    public String getPais() {
        return pais;
    }

    public Date getFechaAlta() {
        return fechaAlta;
    }

    public Date getFechaIni() {
        return fechaIni;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public String getImagen() {
        return imagen;
    }

    public String getEstado() {
        return estado;
    }

    public String getVideoURL() {
        return videoURL;
    }

    // ===== Setters =====
    public void setNombreEdicion(String nombreEdicion) {
        this.nombreEdicion = nombreEdicion;
    }

    public void setNombreEvento(String nombreEvento) {
        this.nombreEvento = nombreEvento;
    }

    public void setOrganizador(String organizador) {
        this.organizador = organizador;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public void setFechaIni(Date fechaIni) {
        this.fechaIni = fechaIni;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    @Override
    public String toString() {
        return getNombreEdicion();
    }
}
