package servidor.publicar;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import logica.DTPatrocinio;

/**
 * DTTipoRegistro → Dttiporegistro (versión para Web Services)
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class DtTipoRegistro {

    private String nombreEdicion;
    private String nombreTipoRegistro;
    private String descripcion;
    private int precio;
    private int cupo;
    private int cantRegistros;
    private List<DtPatrocinio> patrocinios;

    /**
     * Constructor vacío requerido por JAXB
     */
    public DtTipoRegistro() {
    }

    /**
     * Constructor que recibe un DTTipoRegistro de la lógica
     */
    public DtTipoRegistro(logica.DTTipoRegistro dto) {
        this.nombreEdicion = dto.getNombreEdicion();
        this.nombreTipoRegistro = dto.getNombreTipoRegistro();
        this.descripcion = dto.getDescripcion();
        this.precio = dto.getPrecio();
        this.cupo = dto.getCupo();
        this.cantRegistros = dto.getCantRegistros();

        if (dto.getPatrocinios() != null && !dto.getPatrocinios().isEmpty()) {
            this.patrocinios = new ArrayList<>();
            for (logica.DTPatrocinio p : dto.getPatrocinios()) {
                this.patrocinios.add(new DtPatrocinio(p));
            }
        }
    }
    // ===== Getters =====
    public String getNombreEdicion() {
        return nombreEdicion;
    }

    public String getNombreTipoRegistro() {
        return nombreTipoRegistro;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getPrecio() {
        return precio;
    }

    public int getCupo() {
        return cupo;
    }

    public int getCantRegistros() {
        return cantRegistros;
    }

    public List<DtPatrocinio> getPatrocinios() {
        return patrocinios;
    }

    // ===== Setters =====
    public void setNombreEdicion(String nombreEdicion) {
        this.nombreEdicion = nombreEdicion;
    }

    public void setNombreTipoRegistro(String nombreTipoRegistro) {
        this.nombreTipoRegistro = nombreTipoRegistro;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public void setCupo(int cupo) {
        this.cupo = cupo;
    }

    public void setCantRegistros(int cantRegistros) {
        this.cantRegistros = cantRegistros;
    }

    public void setPatrocinios(List<DtPatrocinio> patrocinios) {
        this.patrocinios = patrocinios;
    }

    @Override
    public String toString() {
        return nombreTipoRegistro + " (" + nombreEdicion + ")";
    }
}
