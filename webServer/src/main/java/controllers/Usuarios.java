package controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import clienteServidor.publicar.*;
import utils.SistemasFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/usuarios")
public class Usuarios extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private IEventosControllerWebService sistemaEventos = (IEventosControllerWebService) SistemasFactory.getSistemaEventos();
	private CargarImagenesWebServices sistemaImagenes = (CargarImagenesWebServices) SistemasFactory.getSistemaCargarImagenes();
	private IUsuariosControllerWebService sistemaUsuarios = (IUsuariosControllerWebService) SistemasFactory.getSistemaUsuarios();
	private IInstitucionesControllerWebService sistemaInstituciones = (IInstitucionesControllerWebService) SistemasFactory.getSistemaInstituciones();

	public Usuarios() {
		super();
		// TODO Auto-generated constructor stub
	}

	private void refrescarUsuarioEnSesion(HttpServletRequest req, String nickname) {
		try {
			String tipo = sistemaUsuarios.obtenerTipoUsuario(nickname);
			if ("Asistente".equals(tipo)) {
				DtAsistente actualizado = sistemaUsuarios.getAsistente(nickname);
				req.getSession().setAttribute("usuario_logueado", actualizado);
			} else if ("Organizador".equals(tipo)) {
				DtOrganizador actualizado = sistemaUsuarios.getOrganizador(nickname);
				req.getSession().setAttribute("usuario_logueado", actualizado);
			}
			System.out.println("[Sesion actualizada] Usuario logueado: " + nickname);
		} catch (Exception e) {
			System.err.println("[Error] No se pudo refrescar el usuario en sesión: " + e.getMessage());
		}
	}

	private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			
			req.setAttribute("sistemaUsers", sistemaUsuarios);
			Object usuarioObj = req.getSession().getAttribute("usuario_logueado");
			if (usuarioObj != null) {
				DtUsuario usuarioLogueado = (DtUsuario) usuarioObj;
				String tipo = sistemaUsuarios.obtenerTipoUsuario(usuarioLogueado.getNickname());
				if ("Asistente".equals(tipo)) {
					DtAsistente actualizado = sistemaUsuarios.getAsistente(usuarioLogueado.getNickname());
					req.getSession().setAttribute("usuario_logueado", actualizado);
				} else if ("Organizador".equals(tipo)) {
					DtOrganizador actualizado = sistemaUsuarios.getOrganizador(usuarioLogueado.getNickname());
					req.getSession().setAttribute("usuario_logueado", actualizado);
				}
			}

			String nickname = req.getParameter("nickname");
			req.setAttribute("nickname", nickname);

			String filtro = req.getParameter("filtro");

			if (nickname == null || nickname.isEmpty() || filtro != null) {
				DtUsuario[] usuarios = new DtUsuario[0];

				try {
					if (filtro != null && nickname != null && !nickname.isEmpty()) {
						if (filtro.equals("seguidores")) {
							usuarios = sistemaUsuarios.getSeguidoresDeUsuario(nickname).getItem()
									.toArray(new DtUsuario[0]);
							req.setAttribute("tituloFiltro", "Seguidores de " + nickname);

							if (usuarios.length == 0) {
								req.setAttribute("error", "Este usuario no tiene seguidores.");
							}

						} else if (filtro.equals("seguidos")) {
							usuarios = sistemaUsuarios.getSeguidosDeUsuario(nickname).getItem()
									.toArray(new DtUsuario[0]);
							req.setAttribute("tituloFiltro", "Seguidos por " + nickname);

							if (usuarios.length == 0) {
								req.setAttribute("error", "Este usuario no sigue a nadie.");
							}

						} else {
							usuarios = new DtUsuario[0];
						}
					} else {
						usuarios = sistemaUsuarios.getUsuarios().getItem().toArray(new DtUsuario[0]);
					}
				} catch (Exception e) {
					req.setAttribute("error", "Ha ocurrido un error");
				}

				Arrays.sort(usuarios, Comparator.comparing(DtUsuario::getNombre, String.CASE_INSENSITIVE_ORDER));

				for (DtUsuario u : usuarios) {
					// Guardar el objeto en el request
					String base64 = sistemaImagenes.getImagenUsuariosBase64(u.getImg());
					if (base64 == null || base64.isEmpty()) {
						u.setImg("media/imagenes/placeholderUsers.png");
					} else {
						String dataUrl = "data:image/png;base64," + base64;
						u.setImg(dataUrl);
					}
				}

				req.setAttribute("usuarios", usuarios);
				req.getRequestDispatcher("/WEB-INF/pages/usuarios/usuarios.jsp").forward(req, resp);
				return;
			}

			Boolean esAsistente = sistemaUsuarios.esAsistente(nickname);
			Boolean esOrganizador = sistemaUsuarios.esOrganizador(nickname);
			if (!esAsistente && !esOrganizador) {
				req.getRequestDispatcher("/WEB-INF/pages/consultaUsuario/noExisteUsuario.jsp").forward(req, resp);
			} else if (esAsistente) {
				DtAsistente asistente = sistemaUsuarios.getAsistente(nickname);
				req.setAttribute("asistente", asistente);

				DtUsuarioArray seguidores = sistemaUsuarios.getSeguidoresDeUsuario(nickname);
				DtUsuarioArray seguidos = sistemaUsuarios.getSeguidosDeUsuario(nickname);

				req.setAttribute("seguidores", seguidores);
				req.setAttribute("seguidos", seguidos);

				int cantidadSeguidores = (seguidores != null && seguidores.getItem() != null)
						? seguidores.getItem().size()
						: 0;
				int cantidadSeguidos = (seguidos != null && seguidos.getItem() != null) ? seguidos.getItem().size() : 0;

				req.setAttribute("cantidadSeguidores", cantidadSeguidores);
				req.setAttribute("cantidadSeguidos", cantidadSeguidos);

				// Guardar el objeto en el request
				String base64 = sistemaImagenes.getImagenUsuariosBase64(asistente.getImg());
				if (base64 == null || base64.isEmpty()) {
					asistente.setImg("media/imagenes/placeholderUsers.png");
				} else {
					String dataUrl = "data:image/png;base64," + base64;
					asistente.setImg(dataUrl);
				}

				req.getRequestDispatcher("/WEB-INF/pages/consultaUsuario/consultaUsuario.jsp").forward(req, resp);
			} else {
				DtOrganizador organizador = sistemaUsuarios.getOrganizador(nickname);
				List<String> ediciones = sistemaUsuarios.listarEdicionesAceptadasDeOrganizador(nickname).getItem();

				req.setAttribute("organizador", organizador);
				req.setAttribute("ediciones", ediciones);

				DtUsuarioArray seguidores = sistemaUsuarios.getSeguidoresDeUsuario(nickname);
				DtUsuarioArray seguidos = sistemaUsuarios.getSeguidosDeUsuario(nickname);

				req.setAttribute("seguidores", seguidores);
				req.setAttribute("seguidos", seguidos);

				int cantidadSeguidores = (seguidores != null && seguidores.getItem() != null)
						? seguidores.getItem().size()
						: 0;
				int cantidadSeguidos = (seguidos != null && seguidos.getItem() != null) ? seguidos.getItem().size() : 0;

				req.setAttribute("cantidadSeguidores", cantidadSeguidores);
				req.setAttribute("cantidadSeguidos", cantidadSeguidos);

				// Guardar el objeto en el request
				String base64 = sistemaImagenes.getImagenUsuariosBase64(organizador.getImg());
				if (base64 == null || base64.isEmpty()) {
					organizador.setImg("media/imagenes/placeholderUsers.png");
				} else {
					String dataUrl = "data:image/png;base64," + base64;
					organizador.setImg(dataUrl);
				}

				req.getRequestDispatcher("/WEB-INF/pages/consultaUsuario/consultaUsuario.jsp").forward(req, resp);
			}
		} catch (IllegalArgumentException e) { // 400
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
		} catch (SecurityException e) { // 403
			resp.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
		} catch (AccessDeniedException e) { // 403
			resp.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
		} catch (FileNotFoundException e) { // 404
			req.setAttribute("error", e.getMessage());
			resp.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
		} catch (IOException e) { // 500
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
		} catch (Exception e) { // cualquier otra excepción inesperada → 500
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			Object usuarioObj = req.getSession().getAttribute("usuario_logueado");
			if (usuarioObj == null) {
				resp.sendRedirect(req.getContextPath() + "/login");
				return;
			}

			DtUsuario usuarioLogueado = (DtUsuario) usuarioObj;

			String accion = req.getParameter("accion");
			String nickname = req.getParameter("nickname");

			if (accion == null || nickname == null || nickname.isEmpty()) {
				resp.sendRedirect(req.getContextPath() + "/usuarios");
				return;
			}

			String usuarioLogeadoNickname = usuarioLogueado.getNickname();

			DtUsuarioArray seguidos = sistemaUsuarios.getSeguidosDeUsuario(usuarioLogeadoNickname);

			boolean exito;

			if ("seguir".equals(accion)) {
				exito = sistemaUsuarios.seguirAUsuario(nickname, usuarioLogeadoNickname);
			} else {
				exito = sistemaUsuarios.dejarDeSeguirAUsuario(nickname, usuarioLogeadoNickname);
			}

			refrescarUsuarioEnSesion(req, usuarioLogeadoNickname);

			String redirectUrl = req.getParameter("redirectUrl");
			resp.sendRedirect(redirectUrl);

		} catch (Exception e) {
			e.printStackTrace();
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

}
