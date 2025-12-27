package servidor.publicar;

import java.util.Date;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;

/**
 * DTEvento → Dtevento (versión para Web Services)
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class DtEvento {

    private String nombre;
    private String descripcion;
    private List<String> categorias;
    private List<String> ediciones;
    private String sigla;
    private Date fechaAlta;
    private String img;
    private boolean finalizado;
    private int visitas;

    /**
     * Constructor vacío requerido por JAXB
     */
    public DtEvento() {
    }

    /**
     * Constructor que recibe un DTEvento de la lógica
     */
    public DtEvento(logica.DTEvento dto) {
        this.nombre = dto.getNombre();
        this.descripcion = dto.getDescripcion();
        this.categorias = dto.getCategorias();
        this.ediciones = dto.getEdiciones();
        this.sigla = dto.getSigla();

        if (dto.getFechaAlta() != null)
            this.fechaAlta = java.sql.Date.valueOf(dto.getFechaAlta());

        this.img = dto.getImg();
        this.finalizado = dto.isFinalizado();
        this.visitas = dto.getVisitas();
    }

    // ===== Getters =====
    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public List<String> getCategorias() {
        return categorias;
    }

    public List<String> getEdiciones() {
        return ediciones;
    }

    public String getSigla() {
        return sigla;
    }

    public Date getFechaAlta() {
        return fechaAlta;
    }

    public String getImg() {
        return img;
    }

    public boolean isFinalizado() {
        return finalizado;
    }

    public int getVisitas() {
        return visitas;
    }

    // ===== Setters =====
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setCategorias(List<String> categorias) {
        this.categorias = categorias;
    }

    public void setEdiciones(List<String> ediciones) {
        this.ediciones = ediciones;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void setFinalizado(boolean finalizado) {
        this.finalizado = finalizado;
    }

    public void setVisitas(int visitas) {
        this.visitas = visitas;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
