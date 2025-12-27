package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import utils.SistemasFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import clienteServidor.publicar.*;

/**
 * Servlet implementation class tipoRegistro
 */
@WebServlet("/tipoRegistro")
public class tipoRegistro extends HttpServlet {
  private static final long serialVersionUID = 1L;
  
	private IEventosControllerWebService sistemaEventos = SistemasFactory.getSistemaEventos();
	private CargarImagenesWebServices sistemaImagenes = SistemasFactory.getSistemaCargarImagenes();
	private IUsuariosControllerWebService sistemaUsuarios = SistemasFactory.getSistemaUsuarios();
	private IInstitucionesControllerWebService sistemaInstituciones = SistemasFactory.getSistemaInstituciones();

  /**
   * @see HttpServlet#HttpServlet()
   */
  public tipoRegistro() {
    super();
    // TODO Auto-generated constructor stub
  }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			String nombreEdicion = req.getParameter("edicion");
			System.out.println("Nombre de edicion recibido: '" + nombreEdicion + "'");
			String nombreTReg = req.getParameter("tipoRegistro");
			HttpSession session = req.getSession(false);
			DtUsuario usuario = (DtUsuario) session.getAttribute("usuario_logueado");
			
	
			if (nombreEdicion == null) {//Validar que Tengo Algun parametro
				req.setAttribute("error", "Faltan parámetros");
				req.setAttribute("descripcionError", "Son necesarios los parametros para identificar las ediciones y tipos de registros.");
				throw new IllegalArgumentException("Faltan parámetros");
				
			}else if(nombreEdicion != null && nombreTReg == null) {//Si tengo edicion y no institucion, voy a los patrocinios de una edicion
				
			
				DtEdicion edicion = sistemaEventos.infoEdicion(nombreEdicion);
				
				//Validacion de acceso
				EstadoEdicion estado = EstadoEdicion.valueOf(edicion.getEstado().toUpperCase());
				boolean acceso = (estado== EstadoEdicion.ACEPTADA) || (usuario != null && usuario.getNickname().equals(edicion.getOrganizador()));
				if(!acceso) {
					req.setAttribute("error", "No permitido");
	        		req.setAttribute("descripcionError", "La edicion no es aceptada. No tiene permisos para acceder a la consulta de sus Tipos de Registros.");
	                resp.sendError(HttpServletResponse.SC_FORBIDDEN, "No tiene permiso para ver este recurso");
	                return;
				}
				
				
				List<String> TRegistros = sistemaEventos.listarTiposDeRegistroDeEdicion(nombreEdicion).getItem();
				List<DtTipoRegistro> DtTipoRegistros = new ArrayList<>();
				
				TRegistros.forEach(Tipo ->{
					DtTipoRegistro TipoRegistro = sistemaEventos.consultaTipoDeRegistro(nombreEdicion, Tipo);
					DtTipoRegistros.add(TipoRegistro);
				});
				req.setAttribute("edicionParam", nombreEdicion);
				req.setAttribute("DtTipoRegistros", DtTipoRegistros);
				req.setAttribute("evento", edicion.getNombreEvento());
				req.getRequestDispatcher("/WEB-INF/pages/tipoRegistro/tiposDeRegistrosPorEdicion.jsp").forward(req, resp);
				return;
			}else if (nombreEdicion != null && nombreTReg != null) {//Si tengo edicion e institucion, voy a el especifico

				
				DtEdicion edicion = sistemaEventos.infoEdicion(nombreEdicion);
				
				//Validacion de acceso
				EstadoEdicion estado = EstadoEdicion.valueOf(edicion.getEstado().toUpperCase());
				boolean acceso = (estado == EstadoEdicion.ACEPTADA) || (usuario != null && usuario.getNickname().equals(edicion.getOrganizador()));
				if(!acceso) {
					req.setAttribute("error", "No permitido");
	        		req.setAttribute("descripcionError", "La edicion no es aceptada. No tiene permisos para acceder a la consulta de sus Tipos de Registros.");
	                resp.sendError(HttpServletResponse.SC_FORBIDDEN, "No tiene permiso para ver este recurso");
	                return;
				}
				
				DtTipoRegistro tReg = sistemaEventos.consultaTipoDeRegistro(nombreEdicion, nombreTReg);
				
				// Guardar el objeto en el request
				req.setAttribute("tipoRegistro", tReg);
				
				// Reenviar la solicitud a la JSP
				req.getRequestDispatcher("/WEB-INF/pages/tipoRegistro/tipoRegistro.jsp").forward(req, resp);
				
				return;
			}
			
		 }catch (IllegalArgumentException e) { // 400
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

}
