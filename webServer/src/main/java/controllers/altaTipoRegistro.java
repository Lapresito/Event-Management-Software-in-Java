package controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;

import clienteServidor.publicar.*;
import utils.SistemasFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class altaTipoRegistro
 */
@WebServlet("/altaTipoRegistro")
public class altaTipoRegistro extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private IEventosControllerWebService sistemaEventos = SistemasFactory.getSistemaEventos();
	private CargarImagenesWebServices sistemaImagenes = SistemasFactory.getSistemaCargarImagenes();
	private IUsuariosControllerWebService sistemaUsuarios = SistemasFactory.getSistemaUsuarios();
	private IInstitucionesControllerWebService sistemaInstituciones = SistemasFactory.getSistemaInstituciones();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public altaTipoRegistro() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {

			// Validacion de acceso
			HttpSession session = req.getSession(false);

			if (session == null || session.getAttribute("usuario_logueado") == null) {
				resp.sendRedirect(req.getContextPath() + "/iniciar-sesion");
				return;
			}

			String tipo = (String) session.getAttribute("tipo_usuario");

			boolean acceso = "organizador".equalsIgnoreCase(tipo);
			if (!acceso) {
				req.setAttribute("error", "Acceso No permitido.");
				req.setAttribute("descripcionError", "Para Acceder a este sito tiene que ser organizador.");
				throw new AccessDeniedException("Acceso No permitido.");
			}

			DtUsuario usuario = (DtUsuario) session.getAttribute("usuario_logueado");
			List<String> edicionesOrganizadas = sistemaUsuarios.listarEdicionesDeOrganizador(usuario.getNickname())
					.getItem();

			List<String> edicionesFiltradas = new ArrayList<>();

			// Fecha actual
			java.util.Calendar hoy = java.util.Calendar.getInstance();
			int diaHoy = hoy.get(java.util.Calendar.DAY_OF_MONTH);
			int mesHoy = hoy.get(java.util.Calendar.MONTH) + 1;
			int anioHoy = hoy.get(java.util.Calendar.YEAR);

			for (String edic : edicionesOrganizadas) {
				DtEdicion edicion = sistemaEventos.infoEdicion(edic);
				EstadoEdicion estado = EstadoEdicion.valueOf(edicion.getEstado().toUpperCase());

				// Obtener fecha fin
				int dia = edicion.getFechaFin().getDay();
				int mes = edicion.getFechaFin().getMonth();
				int anio = edicion.getFechaFin().getYear();

				// Validar fechaFin >= hoy
				boolean fechaValida = false;
				if (anio > anioHoy) {
					fechaValida = true;
				} else if (anio == anioHoy) {
					if (mes > mesHoy) {
						fechaValida = true;
					} else if (mes == mesHoy && dia >= diaHoy) {
						fechaValida = true;
					}
				}

				if ((estado == EstadoEdicion.ACEPTADA ||estado == EstadoEdicion.INGRESADA ) && fechaValida) {
					edicionesFiltradas.add(edic);
				}
			}

			req.setAttribute("edicionesOrganizadas", edicionesFiltradas);

			req.getRequestDispatcher("/WEB-INF/pages/altaTipoRegistro/altaTipoRegistro.jsp").forward(req, resp);

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

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String nombreEdicion = req.getParameter("edicion");
		String nombreTipo = req.getParameter("nombreTipo");
		String descripcion = req.getParameter("descTipo");
		String costoStr = req.getParameter("costo");
		String cupoStr = req.getParameter("cupo");

		try {
			int costo = Integer.parseInt(costoStr);
			int cupo = Integer.parseInt(cupoStr);

			// Llamar al controlador para crear el tipo de registro
			sistemaEventos.altaTipoDeRegistro(nombreTipo, nombreEdicion, descripcion, costo, cupo);

			req.setAttribute("mensaje", "Tipo de registro creado exitosamente.");

		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("error", e.getMessage());
			req.setAttribute("edicionSeleccionada", nombreEdicion);
			req.setAttribute("nombreTipo", nombreTipo);
			req.setAttribute("descTipo", descripcion);
			req.setAttribute("costo", costoStr);
			req.setAttribute("cupo", cupoStr);
		}

		// Volver al formulario (ya sea con mensaje de éxito o error)
		doGet(req, resp);
	}
}
