package logica;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.xml.bind.annotation.XmlSchemaType;

/**
 * clase usuario.
 */

public class Usuario {

	// Atributos privados

	private String nickname;
	private String email;
	protected String nombre;
	private String img;
	private String password;
	private LocalDate fechaAlta;
	private Map<String, Usuario> seguidos;
	private Map<String, Usuario> seguidores;

	// Constructor
	/**
	 * consturctor.
	 */
	public Usuario(String nickname, String email, String nombre, String img, String password) {
		this.nickname = nickname;
		this.email = email;
		this.nombre = nombre;
		this.img = img;
		this.password = password;
		setFechaAlta(LocalDate.now());
		seguidos = new HashMap<>();
		seguidores = new HashMap<>();
	}

	// Getters y Setters

	public String getNickname() {
		return nickname;
	}

	public String getEmail() {
		return email;
	}

	public String getNombre() {
		return nombre;
	}

	public Map<String, Usuario> getSeguidos() {
		return seguidos;
	}

	public Map<String, Usuario> getSeguidores() {
		return seguidores;
	}

	public void setEmail(String emailParam) {
		email = emailParam;
	}

	public void setNombre(String nombreUsuario) {
		nombre = nombreUsuario;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public LocalDate getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(LocalDate fecha) {
		fechaAlta = fecha;
	}

	public void setSeguidos(Map<String, Usuario> listaSeguidos) {
		this.seguidos = listaSeguidos;
	}

	public void setSeguidores(Map<String, Usuario> listaSeguidores) {
		this.seguidores = listaSeguidores;
	}

	public boolean seguir(Usuario Usuario) {
		if (seguidos.containsKey(Usuario.getNickname()) || Usuario.getNickname() == this.nickname) {
			return false;
		}
		seguidos.put(Usuario.getNickname(), Usuario);
		return true;
	}

	public boolean dejarDeSeguir(Usuario Usuario) {
		if (seguidos.containsKey(Usuario.getNickname())) {
			seguidos.remove(Usuario.getNickname());
			return true;
		}
		return false;
	}

	public boolean agregarSeguidor(Usuario Usuario) {
		if (seguidores.containsKey(Usuario.getNickname()) || Usuario.getNickname() == this.nickname) {
			return false;
		}
		seguidores.put(Usuario.getNickname(), Usuario);
		return true;
	}

	public boolean eliminarSeguidor(Usuario Usuario) {
		if (seguidores.containsKey(Usuario.getNickname())) {
			seguidores.remove(Usuario.getNickname());
			return true;
		}
		return false;
	}

	public List<DTUsuario> getDTSeguidores() {

		List<DTUsuario> DTUsuarios = new ArrayList<>();
		seguidores.forEach((nombre, user) -> {
			if (user instanceof Asistente) {
				Asistente asistente = (Asistente) user;
				DTUsuarios.add(asistente.getDTAsistente());
			} else {
				Organizador organizador = (Organizador) user;
				DTUsuarios.add(organizador.getDTOrganizador());
			}
		});

		return DTUsuarios;
	}

	public List<DTUsuario> getDTSeguidos() {
		List<DTUsuario> DTUsuarios = new ArrayList<>();
		seguidos.forEach((nombre, user) -> {
			if (user instanceof Asistente) {
				Asistente asistente = (Asistente) user;
				DTUsuarios.add(asistente.getDTAsistente());
			} else {
				Organizador organizador = (Organizador) user;
				DTUsuarios.add(organizador.getDTOrganizador());
			}
		});

		return DTUsuarios;
	}

}
