package controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.AccessDeniedException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

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



/**
 * Servlet implementation class altaInstitucion
 */
@WebServlet("/altaInstitucion")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, // 1MB antes de guardar en disco
		maxFileSize = 1024 * 1024 * 5, // 5MB por archivo
		maxRequestSize = 1024 * 1024 * 10) // 10MB total
public class altaInstitucion extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private IEventosControllerWebService sistemaEventos = SistemasFactory.getSistemaEventos();
	private CargarImagenesWebServices sistemaImagenes = SistemasFactory.getSistemaCargarImagenes();
	private IUsuariosControllerWebService sistemaUsuarios = SistemasFactory.getSistemaUsuarios();
	private IInstitucionesControllerWebService sistemaInstituciones = SistemasFactory.getSistemaInstituciones();

	public altaInstitucion() {
		super();
		// TODO Auto-generated constructor stub
	}

	private void processRequestGET(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
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

			req.getRequestDispatcher("/WEB-INF/pages/altaInstitucion/altaInstitucion.jsp").forward(req, resp);

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

	private void processRequestPOST(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("application/json");
		PrintWriter out = resp.getWriter();

		try {

			// Levantar JSON con los datos
			Part dataPart = req.getPart("institucionData");
			InputStream inputStream = dataPart.getInputStream();
			String json = new String(inputStream.readAllBytes(), "UTF-8");

			Gson gson = new Gson();
			Map<String, String> institucionMap = gson.fromJson(json, Map.class);

			String nombre = institucionMap.get("nombre");
			String descripcion = institucionMap.get("descripcion");
			String web = institucionMap.get("web");

			Part imgPart = req.getPart("institucionImg");

			String rutaFinal = "";

			if (imgPart != null && imgPart.getSize() > 0 && imgPart.getSubmittedFileName() != null
					&& !imgPart.getSubmittedFileName().isBlank()) {

				String nombreImagen = Paths.get(imgPart.getSubmittedFileName()).getFileName().toString();

				System.out.println("getSubmittedFileName: " + imgPart.getSubmittedFileName());
				System.out.println("nombreImagen: " + nombreImagen);

				byte[] datosImagen = imgPart.getInputStream().readAllBytes();
				rutaFinal = sistemaImagenes.subirImagenInstituciones(nombreImagen, datosImagen);
			}

			// Pasar null si no se subió imagen
			sistemaInstituciones.altaInstitucion(nombre, descripcion, web, rutaFinal);

			// Respuesta exitosa
			Map<String, String> success = new HashMap<>();
			success.put("status", "ok");
			success.put("mensaje", "Institución registrada correctamente");
			out.write(gson.toJson(success));

		} catch (Exception e) {
			e.printStackTrace();
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			resp.setContentType("application/json; charset=UTF-8");

			Map<String, String> errorResp = new HashMap<>();
			errorResp.put("status", "error");

			String mensajeError = e.getMessage();
			if (mensajeError == null || mensajeError.isEmpty()) {
				mensajeError = "Error inesperado en el servidor.";
			}
			errorResp.put("mensaje", mensajeError);

			out.write(new Gson().toJson(errorResp));
		}

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequestGET(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequestPOST(request, response);
	}

}
