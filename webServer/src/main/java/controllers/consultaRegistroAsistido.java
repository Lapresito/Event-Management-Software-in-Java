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
 * Servlet implementation class Registros
 */
@WebServlet("/consultaRegistroAsistido")
public class consultaRegistroAsistido extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private IEventosControllerWebService sistemaEventos = SistemasFactory.getSistemaEventos();
	private CargarImagenesWebServices sistemaImagenes = SistemasFactory.getSistemaCargarImagenes();
	private IUsuariosControllerWebService sistemaUsuarios = SistemasFactory.getSistemaUsuarios();
	private IInstitucionesControllerWebService sistemaInstituciones = SistemasFactory.getSistemaInstituciones();
	  

	
	public consultaRegistroAsistido() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			
			// Validacion de acceso
			HttpSession session = req.getSession(false);

			if (session == null || session.getAttribute("usuario_logueado") == null) {
				resp.sendRedirect(req.getContextPath() + "/iniciar-sesion");
				return;
			}
			
			DtUsuario usuario = (DtUsuario) session.getAttribute("usuario_logueado");
			
			String nombreEdicion = req.getParameter("nombreEdicion");
			String nickname = req.getParameter("nickname");
	
			String tipo = (String) session.getAttribute("tipo_usuario");
			
			if (nombreEdicion == null || nombreEdicion.isEmpty()) {
				req.setAttribute("error", "Faltan parámetros");
				req.setAttribute("descripcionError", "Son necesarios los parametros para identificar las ediciones y sus registros.");
				throw new IllegalArgumentException("Faltan parámetros");
			}
			
			DtEdicion edicion = sistemaEventos.infoEdicion(nombreEdicion);
			
			
			
			if (nickname == null || nickname.isEmpty()) {//quiero ver todos los registros
				
				boolean acceso = usuario !=null ;
				if (!acceso) {
					req.setAttribute("error", "Acceso No permitido.");
					req.setAttribute("descripcionError", "Para Acceder a este sito tiene que ser organizador de la edicion.");
					throw new AccessDeniedException("Acceso No permitido.");
				}
				
				
				
				List<DtRegistro> registros = sistemaEventos.listarRegistrosDeEdicion(edicion.getNombreEvento(), edicion.getNombreEdicion()).getItem();
			    List<DtRegistro> registrosAsistidos = new ArrayList<>();
			    registros.forEach(reg ->{
			    	if(reg.isAsistio()) {
			    		registrosAsistidos.add(reg);
			    	}
			    });
			    req.setAttribute("registrosAsistidos", registrosAsistidos);
				
				req.setAttribute("edicionParam", edicion.getNombreEdicion());
				req.setAttribute("eventoParam", edicion.getNombreEvento());
				req.setAttribute("registros", registrosAsistidos);
				req.getRequestDispatcher("/WEB-INF/pages/consultaRegistro/consultaRegistroPorEdicion.jsp").forward(req, resp);

						
			}else {

				boolean acceso = usuario !=null  ;
				if (!acceso) {
					req.setAttribute("error", "Acceso No permitido.");
					req.setAttribute("descripcionError", "El usuario actual logueado solo puede consultar sus registros.");
					throw new AccessDeniedException("Acceso No permitido.");
				}
				
				DtRegistro registro = sistemaUsuarios.infoRegistroDeAsistente(nickname, nombreEdicion);
				req.setAttribute("registro", registro);
				req.getRequestDispatcher("/WEB-INF/pages/consultaRegistro/consultaRegistro.jsp").forward(req, resp);
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

  /**
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
   */
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    // TODO Auto-generated method stub
    doGet(request, response);
  }

}
