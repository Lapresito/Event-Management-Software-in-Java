package servidor.publicar;

import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;

/**
 * DTUsuario → Dtusuario (versión para Web Services)
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class DtUsuario {

    private String nickname;
    private String email;
    private String nombre;
    private String img;
    private List<DtUsuario> seguidos;
    private List<DtUsuario> seguidores;

    /**
     * Constructor vacío requerido por JAXB
     */
    public DtUsuario() {
    }

    /**
     * Constructor que recibe el DTO de la lógica
     */
    public DtUsuario(logica.DTUsuario dto) {
        this.nickname = dto.getNickname();
        this.email = dto.getEmail();
        this.nombre = dto.getNombre();
        this.img = dto.getImg();

        // Nota: si querés evitar recursividad infinita, podés dejar estas listas en null
        // o implementar una conversión más controlada (por ejemplo, solo nicknames).
        this.seguidos = null;
        this.seguidores = null;
    }

    // ===== Getters =====
    public String getNickname() {
        return nickname;
    }

    public String getEmail() {
        return email;
    }

    public String getNombre() {
        return nombre;
    }

    public String getImg() {
        return img;
    }

    public List<DtUsuario> getSeguidos() {
        return seguidos;
    }

    public List<DtUsuario> getSeguidores() {
        return seguidores;
    }

    // ===== Setters =====
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void setSeguidos(List<DtUsuario> seguidos) {
        this.seguidos = seguidos;
    }

    public void setSeguidores(List<DtUsuario> seguidores) {
        this.seguidores = seguidores;
    }

    @Override
    public String toString() {
        return getNickname() + " (" + getNombre() + ")";
    }
}
