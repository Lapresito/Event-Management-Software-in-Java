package servidor.publicar;

import java.util.Date;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;

/**
 * DTPatrocinio → Dtpatrocinio (versión para Web Services)
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class DtPatrocinio {

    private String nombreInstitucion;
    private String nombreEdicion;
    private int monto;
    private String codigo;
    private Date fechaAlta;
    private int cupos;
    private int cantRegistros;
    private String nivel;
    private String DTtipoRegistro;

    /**
     * Constructor vacío requerido por JAXB
     */
    public DtPatrocinio() {
    }

    /**
     * Constructor que recibe un DTPatrocinio de la lógica
     */
    public DtPatrocinio(logica.DTPatrocinio dto) {
        this.nombreInstitucion = dto.getNombreInstitucion();
        this.nombreEdicion = dto.getNombreEdicion();
        this.monto = dto.getMonto();
        this.codigo = dto.getCodigo();

        if (dto.getFechaAlta() != null)
            this.fechaAlta = java.sql.Date.valueOf(dto.getFechaAlta());

        this.cupos = dto.getCupos();
        this.cantRegistros = dto.getCantRegistros();
        this.nivel = (dto.getNivel() != null) ? dto.getNivel().toString() : null;

        if (dto.getDTtipoRegistro() != null)
            this.DTtipoRegistro = dto.getDTtipoRegistro();
    }

    // ===== Getters =====
    public String getNombreInstitucion() {
        return nombreInstitucion;
    }

    public String getNombreEdicion() {
        return nombreEdicion;
    }

    public int getMonto() {
        return monto;
    }

    public String getCodigo() {
        return codigo;
    }

    public Date getFechaAlta() {
        return fechaAlta;
    }

    public int getCupos() {
        return cupos;
    }

    public int getCantRegistros() {
        return cantRegistros;
    }

    public String getNivel() {
        return nivel;
    }

    public String getDTtipoRegistro() {
        return DTtipoRegistro;
    }

    // ===== Setters =====
    public void setNombreInstitucion(String nombreInstitucion) {
        this.nombreInstitucion = nombreInstitucion;
    }

    public void setNombreEdicion(String nombreEdicion) {
        this.nombreEdicion = nombreEdicion;
    }

    public void setMonto(int monto) {
        this.monto = monto;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public void setCupos(int cupos) {
        this.cupos = cupos;
    }

    public void setCantRegistros(int cantRegistros) {
        this.cantRegistros = cantRegistros;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public void setDTtipoRegistro(String DTtipoRegistro) {
        this.DTtipoRegistro = DTtipoRegistro;
    }

    @Override
    public String toString() {
        return nombreInstitucion + " → " + nombreEdicion;
    }
}
