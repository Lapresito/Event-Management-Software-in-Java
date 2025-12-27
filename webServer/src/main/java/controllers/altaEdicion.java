package controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.AccessDeniedException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import clienteServidor.publicar.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import utils.SistemasFactory;

/*
 * Servlet implementation class altaEdicion
 */
@WebServlet("/altaEdicion")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, // 1MB antes de guardar en disco
		maxFileSize = 1024 * 1024 * 5, // 5MB por archivo
		maxRequestSize = 1024 * 1024 * 10) // 10MB total
public class altaEdicion extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private IEventosControllerWebService sistemaEventos = SistemasFactory.getSistemaEventos();
	private CargarImagenesWebServices sistemaImagenes = SistemasFactory.getSistemaCargarImagenes();

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

			// Obtener lista de eventos para llenar el select
			List<String> listaEventos = sistemaEventos.listarEventos().getItem(); // nombres
			List<String> listaEventosNoFinalizados = new ArrayList<>();

			for (String nombreEvento : listaEventos) {
				DtEvento evento = sistemaEventos.infoEvento(nombreEvento);

				if (!evento.isFinalizado()) { // si el evento NO está finalizado
					listaEventosNoFinalizados.add(nombreEvento);
				}
			}

			req.setAttribute("eventosDisponibles", listaEventosNoFinalizados);
			req.getRequestDispatcher("/WEB-INF/pages/altaEdicion/altaEdicion.jsp").forward(req, resp);

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
		try {
			// Leer parámetros del formulario
			String nombreEdicion = req.getParameter("nombreEdicion");
			String siglaEdicion = req.getParameter("siglaEdicion");
			String nombreEvento = req.getParameter("nombreEvento");
			String pais = req.getParameter("paisEdicion");
			String ciudad = req.getParameter("ciudadEdicion");
			LocalDate fechaInicio = LocalDate.parse(req.getParameter("fechaIni"));
			LocalDate fechaFin = LocalDate.parse(req.getParameter("fechaFin"));
			String URLvideo = req.getParameter("video");

			HttpSession session = req.getSession(false); // no crea nueva
			DtUsuario usuario = (DtUsuario) session.getAttribute("usuario_logueado");

			// Procesar imagen usando Web Service
			Part imgPart = req.getPart("imgEvento");
			String rutaFinal = "";

			if (imgPart != null && imgPart.getSize() > 0 && imgPart.getSubmittedFileName() != null
					&& !imgPart.getSubmittedFileName().isBlank()) {

				String nombreImagen = Paths.get(imgPart.getSubmittedFileName()).getFileName().toString();

				System.out.println("getSubmittedFileName: " + imgPart.getSubmittedFileName());
				System.out.println("nombreImagen: " + nombreImagen);

				byte[] datosImagen = imgPart.getInputStream().readAllBytes();
				rutaFinal = sistemaImagenes.subirImagenEdiciones(nombreImagen, datosImagen);
			}
			String nickname = usuario.getNickname();
			int fechaIniYear = fechaInicio.getYear();
			int fechaIniMonth = fechaInicio.getMonthValue();
			int fechaIniDay = fechaInicio.getDayOfMonth();
			int fechaFinYear = fechaFin.getYear();
			int fechaFinMonth = fechaFin.getMonthValue();
			int fechaFinDay = fechaFin.getDayOfMonth();
			sistemaEventos.altaEdicion(nombreEvento, nickname, nombreEdicion, siglaEdicion, ciudad, pais, fechaIniYear,
					fechaIniMonth, fechaIniDay, fechaFinYear, fechaFinMonth, fechaFinDay, rutaFinal);
			System.out.println("Sali de sistemaEventos.altaEdicion");
			if (URLvideo != null && !URLvideo.isEmpty()) {
				sistemaEventos.agregarVideo(nombreEdicion, URLvideo);
			}
			System.out.println("Pase la parte de agregarVideo");
			// Redirigir a la página de detalle de la edición
			resp.sendRedirect(
					req.getContextPath() + "/edicion?nombreEdicion=" + URLEncoder.encode(nombreEdicion, "UTF-8"));

		} catch (Exception e) {
			e.printStackTrace();

			String mensajeError = e.getMessage();

			// Limpieza si es un SOAP Fault
			if (mensajeError != null) {
				int index = mensajeError.indexOf("server:");
				if (index != -1) {
					mensajeError = mensajeError.substring(index + 7).trim();
				}
				int pleaseIndex = mensajeError.indexOf("Please see the server log");
				if (pleaseIndex != -1) {
					mensajeError = mensajeError.substring(0, pleaseIndex).trim();
				}
			}

			req.setAttribute("error", mensajeError);

			req.setAttribute("nombreEdicion", req.getParameter("nombreEdicion"));
			req.setAttribute("siglaEdicion", req.getParameter("siglaEdicion"));
			req.setAttribute("nombreEvento", req.getParameter("nombreEvento"));
			req.setAttribute("paisEdicion", req.getParameter("paisEdicion"));
			req.setAttribute("ciudadEdicion", req.getParameter("ciudadEdicion"));
			req.setAttribute("fechaIni", req.getParameter("fechaIni"));
			req.setAttribute("fechaFin", req.getParameter("fechaFin"));
			req.setAttribute("video", req.getParameter("video"));

			doGet(req, resp);
		}
	}
}
