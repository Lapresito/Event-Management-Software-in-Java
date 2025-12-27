package controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.AccessDeniedException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import clienteServidor.publicar.*;
import utils.SistemasFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;


@WebServlet("/altaEvento")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, // 1MB antes de guardar en disco
		maxFileSize = 1024 * 1024 * 5, // 5MB por archivo
		maxRequestSize = 1024 * 1024 * 10) // 10MB total
public class altaEvento extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private IEventosControllerWebService sistemaEventos = SistemasFactory.getSistemaEventos();
	private CargarImagenesWebServices sistemaImagenes = SistemasFactory.getSistemaCargarImagenes();
	private IUsuariosControllerWebService sistemaUsuarios = SistemasFactory.getSistemaUsuarios();
	private IInstitucionesControllerWebService sistemaInstituciones = SistemasFactory.getSistemaInstituciones();

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

			// cargo categorías para el formulario
			List<String> categorias = sistemaEventos.listarCategorias().getItem();
			req.setAttribute("categorias", categorias);
			req.getRequestDispatcher("/WEB-INF/pages/eventos/altaEvento.jsp").forward(req, resp);
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
		String nombre = trim(req.getParameter("nombre"));
		String sigla = trim(req.getParameter("sigla"));
		String descripcion = trim(req.getParameter("descripcion"));
		String[] cats = req.getParameterValues("categorias");
		List<String> categorias = (cats == null) ? Collections.emptyList() : Arrays.asList(cats);
		// Procesar imagen usando Web Service
		Part imgPart = req.getPart("imgEvento");
		String rutaFinal = "";

		if (imgPart != null && imgPart.getSize() > 0 && imgPart.getSubmittedFileName() != null
				&& !imgPart.getSubmittedFileName().isBlank()) {

			String nombreImagen = Paths.get(imgPart.getSubmittedFileName()).getFileName().toString();

			System.out.println("getSubmittedFileName: " + imgPart.getSubmittedFileName());
			System.out.println("nombreImagen: " + nombreImagen);

			byte[] datosImagen = imgPart.getInputStream().readAllBytes();
			rutaFinal = sistemaImagenes.subirImagenEventos(nombreImagen, datosImagen);
		}

		// Validaciones
		if (isBlank(nombre) || isBlank(sigla) || isBlank(descripcion)) {
			setError(req, "Completá nombre, sigla y descripción.");
			doGet(req, resp);
			return;
		}
		if (categorias.isEmpty()) {
			setError(req, "Seleccioná al menos una categoría.");
			doGet(req, resp);
			return;
		}

		try {
			StringArray categoriasST = new StringArray();
			categoriasST.getItem().addAll(categorias);

			sistemaEventos.altaEvento(nombre, sigla, descripcion, categoriasST, rutaFinal);
			String destino = req.getContextPath() + "/eventos?nombre="
					+ URLEncoder.encode(nombre, StandardCharsets.UTF_8);
			resp.sendRedirect(destino);
		} catch (EventoYaExisteException_Exception e) {
			setError(req, "Ya existe un evento con ese nombre.");
			doGet(req, resp);
		} catch (IllegalArgumentException e) {
			setError(req, e.getMessage());
			doGet(req, resp);
		}
	}

	private static boolean isBlank(String strParam) {
		return strParam == null || strParam.trim().isEmpty();
	}

	private static String trim(String strParam) {
		return strParam == null ? null : strParam.trim();
	}

	private static void setError(HttpServletRequest req, String msg) {
		req.setAttribute("error", msg);
	}
}
