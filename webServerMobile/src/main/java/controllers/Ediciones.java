package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import clienteServidor.publicar.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.xml.ws.soap.SOAPFaultException;
import utils.SistemasFactory;

@WebServlet("/edicion")
public class Ediciones extends HttpServlet {
  private static final long serialVersionUID = 1L;
  
	private IEventosControllerWebService sistemaEventos = SistemasFactory.getSistemaEventos();
	private CargarImagenesWebServices sistemaImagenes = SistemasFactory.getSistemaCargarImagenes();
	private IUsuariosControllerWebService sistemaUsuarios = SistemasFactory.getSistemaUsuarios();
	private IInstitucionesControllerWebService sistemaInstituciones = SistemasFactory.getSistemaInstituciones();
  
  public Ediciones() {
    super();
    // TODO Auto-generated constructor stub
  }

  private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    try {
	    //Solo accedo si estoy logueado, de lo contrario se redirige al login.
	    HttpSession session = req.getSession(false);
	    if (session == null || session.getAttribute("usuario_logueado") == null) {
	    	//resp.sendRedirect(req.getContextPath() + "/login");
	    	//return;
	    }
    	
	    String nombreEdicion = req.getParameter("nombreEdicion");
	    if (nombreEdicion == null || nombreEdicion.isEmpty()) {
	    	req.setAttribute("error", "Parametros incorrectos");
	    	req.setAttribute("descripcionError", "Los parametros de busqueda no son correctos.");
	    	resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Los parametros de busqueda no son correctos.");
	    	return;
	    }

	      
	    DtUsuario usuario = (DtUsuario) session.getAttribute("usuario_logueado") != null ? (DtUsuario) session.getAttribute("usuario_logueado"):null;
	    DtEdicion edicion = null;
	    try {
	        edicion = sistemaEventos.infoEdicion(nombreEdicion);
	    } catch (SOAPFaultException  e) {
	    	String mensaje = e.getFault().getFaultString();
	        if (mensaje != null && mensaje.contains("La edicion no existe")) {
	            req.setAttribute("error", "La edición no existe");
	            req.setAttribute("descripcionError", "La edición especificada no se encuentra registrada.");
	            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "La edición no existe");
	            return;
	        } else {
	            req.setAttribute("error", "Error al recuperar la edición");
	            req.setAttribute("descripcionError", "Ocurrió un problema al intentar obtener la edición solicitada.");
	            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error en la llamada al servidor SOAP");
	            return;
	        }
	    }

	    //si no es aceptada, solo el org la puede ver
	    EstadoEdicion estado = EstadoEdicion.valueOf(edicion.getEstado().toUpperCase());

	    if (estado != EstadoEdicion.ACEPTADA && usuario.getNickname() != edicion.getOrganizador()) {
	    	req.setAttribute("error", "No permitido");
    		req.setAttribute("descripcionError", "Solo el organizador de esta edicion puede consultarla debido a que aun no está aceptada.");
    		resp.sendError(HttpServletResponse.SC_FORBIDDEN, "No tiene permiso para ver este recurso");
    		return;
	    }
	      
	    //Tipos de registros
	    List<String> tiposRegistros = sistemaEventos.listarTiposDeRegistroDeEdicion(nombreEdicion).getItem();
	      
	    //Registros con asistencia
	    List<DtRegistro> registros = sistemaEventos.listarRegistrosDeEdicion(edicion.getNombreEvento(),nombreEdicion).getItem();
	    List<DtRegistro> registrosAsistidos = new ArrayList<>();
	    registros.forEach(reg ->{
	    	if(reg.isAsistio()) {
	    		registrosAsistidos.add(reg);
	    	}
	    });
	      
	    //Patrocinios de edicion
	    List<String> patrocinios = sistemaEventos.listarPatrociniosDeEdicion(edicion.getNombreEvento(),nombreEdicion).getItem();
	     System.out.println(patrocinios);
	      
	     
	      // Guardar el objeto en el request
	      String base64 = sistemaImagenes.getImagenEdicionesBase64(edicion.getImagen());
	      
	      if (base64 == null || base64.isEmpty()) {
	    	  edicion.setImagen("media/imagenes/placeholderEvento-Edicion.png"); 
	      }else {
	    	  String dataUrl = "data:image/png;base64," + base64;
	    	  edicion.setImagen(dataUrl);    	  
	      }
	     
	    //Envio todo por el request
	    req.setAttribute("edicion", edicion);
	    req.setAttribute("tiposRegistros", tiposRegistros);
	    req.setAttribute("registrosAsistidos", registrosAsistidos);
	    req.setAttribute("listaPatrocinios", patrocinios);
	    
	    System.out.println(tiposRegistros);
	    System.out.println(registrosAsistidos);
	    System.out.println(patrocinios);
	
	    // Reenviar la solicitud a la JSP
	    req.getRequestDispatcher("/WEB-INF/pages/edicion/edicion.jsp").forward(req, resp);
	    return;
    
    }catch (IllegalArgumentException e) {
    	req.setAttribute("error", e.getMessage());
    	resp.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
    }catch (IOException e) {
    	req.setAttribute("error", e.getMessage() != null? e.getMessage() : "error desconocido");
    	resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
    }
  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    // Verificar si el usuario está logueado
    Object usuario = request.getSession().getAttribute("usuario_logueado");

    if (usuario == null) {
        // 403 Forbidden
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.sendRedirect(request.getContextPath() + "/iniciar-sesion");
        return;
    }

    processRequest(request, response);
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    processRequest(request, response);
  }

}