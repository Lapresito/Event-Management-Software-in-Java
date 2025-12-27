package controllers;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

import clienteServidor.publicar.IEventosControllerWebService;
import clienteServidor.publicar.NivelPatrocinio;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import utils.SistemasFactory;

@WebServlet("/altaPatrocinio")
public class altaPatrocinio extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private IEventosControllerWebService sistemaEventos = SistemasFactory.getSistemaEventos();

	public altaPatrocinio() {
		super();
	}

	private void processRequestGET(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try {

			HttpSession session = req.getSession(false);

			if (session == null || session.getAttribute("usuario_logueado") == null) {
				resp.sendRedirect(req.getContextPath() + "/iniciar-sesion");
				return;
			}

			String tipo = (String) session.getAttribute("tipo_usuario");

			boolean acceso = "organizador".equalsIgnoreCase(tipo);
			if (!acceso) {
				req.setAttribute("error", "Acceso No permitido.");
				req.setAttribute("descripcionError", "Para acceder a este sitio debe ser organizador.");
				throw new AccessDeniedException("Acceso No permitido.");
			}

			req.getRequestDispatcher("/WEB-INF/pages/altaPatrocinio/altaPatrocinio.jsp").forward(req, resp);

		} catch (IllegalArgumentException e) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
		} catch (SecurityException | AccessDeniedException e) {
			resp.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
		} catch (FileNotFoundException e) {
			resp.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
		} catch (IOException e) {
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
		} catch (Exception e) {
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	private void processRequestPOST(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");

		try {
			StringBuilder stringBuilder = new StringBuilder();
			try (BufferedReader reader = req.getReader()) {
				String line;
				while ((line = reader.readLine()) != null) {
					stringBuilder.append(line);
				}
			}
			String body = stringBuilder.toString();

			Gson gson = new Gson();
			Map<String, Object> patrocinio = gson.fromJson(body, Map.class);

			String evento = (String) patrocinio.get("evento");
			String edicion = (String) patrocinio.get("edicion");
			String tipoRegistro = (String) patrocinio.get("tipoRegistro");
			String institucion = (String) patrocinio.get("institucion");
			String codigo = (String) patrocinio.get("codigo");
			NivelPatrocinio nivel = NivelPatrocinio.valueOf(((String) patrocinio.get("nivel")).toUpperCase());

			int monto = ((Double) patrocinio.get("monto")).intValue();
			int registrosGratuitos = ((Double) patrocinio.get("registrosGratuitos")).intValue();

			sistemaEventos.altaPatrocinio(institucion, edicion, tipoRegistro, nivel, monto, registrosGratuitos, codigo);

			Map<String, String> respuesta = new HashMap<>();
			respuesta.put("status", "ok");
			respuesta.put("mensaje", "Alta de patrocinio exitosa");

			resp.setStatus(HttpServletResponse.SC_OK);
			resp.getWriter().write(new Gson().toJson(respuesta));

		} catch (Exception e) {

			e.printStackTrace();

			// MENSAJE ORIGINAL DEL SOAP
			String mensaje = e.getMessage();

			if (mensaje != null) {
				mensaje = mensaje.replace("Client received SOAP Fault from server:", "").trim();
				int idx = mensaje.indexOf("Please see");
				if (idx != -1) {
					mensaje = mensaje.substring(0, idx).trim();
				}

			} else {
				mensaje = "Error desconocido.";
			}

			Map<String, String> errorResp = new HashMap<>();
			errorResp.put("status", "error");
			errorResp.put("mensaje", mensaje);

			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			resp.getWriter().write(new Gson().toJson(errorResp));
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequestGET(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequestPOST(request, response);
	}
}
