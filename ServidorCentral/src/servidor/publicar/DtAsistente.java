package servidor.publicar;

import java.util.Date;
import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlSchemaType;

/**
 * DTAsistente → Dtasistente (versión para Web Services)
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class DtAsistente extends DtUsuario {
    private String apellido;
    private Date nacimiento;
    private String institucion;
    private String[] registros; // nickname de usuarios registrados

    /**
     * Constructor vacío requerido por JAXB
     */
    public DtAsistente() {
        super();
    }

    /**
     * Constructor que recibe el DTO de la lógica
     */
    public DtAsistente(logica.DTAsistente dto) {
        super(dto); // usa el constructor del padre (DTUsuario → Dtusuario)
        this.apellido = dto.getApellido();
        this.nacimiento = (dto.getNacimiento() != null) ? java.sql.Date.valueOf(dto.getNacimiento()) : null;
        this.institucion = dto.getInstitucion();
        this.registros = dto.getRegistros();
    }

    // Getters y Setters
    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Date getNacimiento() {
        return nacimiento;
    }

    public void setNacimiento(Date nacimiento) {
        this.nacimiento = nacimiento;
    }

    public String getInstitucion() {
        return institucion;
    }

    public void setInstitucion(String institucion) {
        this.institucion = institucion;
    }

    public String[] getRegistros() {
        return registros;
    }

    public void setRegistros(String[] registros) {
        this.registros = registros;
    }
}
