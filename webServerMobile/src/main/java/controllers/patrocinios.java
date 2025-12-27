package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.SistemasFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import clienteServidor.publicar.*;

@WebServlet("/patrocinios") // Ruta /patrocinios?edicion=NOMBREEDICION&institucion=NOMBREINSTITUCION
public class patrocinios extends HttpServlet {
  private static final long serialVersionUID = 1L;
  
	private IEventosControllerWebService sistemaEventos = SistemasFactory.getSistemaEventos();
	private CargarImagenesWebServices sistemaImagenes = SistemasFactory.getSistemaCargarImagenes();
	private IUsuariosControllerWebService sistemaUsuarios = SistemasFactory.getSistemaUsuarios();
	private IInstitucionesControllerWebService sistemaInstituciones = SistemasFactory.getSistemaInstituciones();
	
  public patrocinios() {
    super();
    // TODO Auto-generated constructor stub
  }

  private void processRequestGET(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    try {
      // Obtener los parámetros del request
      String edicionParam = req.getParameter("edicion");
      String institucionParam = req.getParameter("institucion");
      req.setAttribute("edicionParam", edicionParam);
      req.setAttribute("institucionParam", institucionParam);

      // Validacion de acceso
      boolean acceso = true;
      if (!acceso) {
        req.setAttribute("error", "Acceso No permitido.");
        req.setAttribute("descripcionError",
            "Para Acceder a este sito tiene que tener los permisos correspondientes.");
        throw new AccessDeniedException("Acceso No permitido.");
      }

      if (edicionParam == null) {// Validar que Tengo Algun parametro
        req.setAttribute("error", "Faltan parámetros");
        req.setAttribute("descripcionError",
            "Son necesarios los parametros para identificar las ediciones y sus patrocinios.");
        throw new IllegalArgumentException("Faltan parámetros");

      } else if (edicionParam != null && institucionParam == null) {// Si tengo edicion y no
                                                                    // institucion, voy a los
                                                                    // patrocinios de una edicion
    	  
    	
    	List<DtPatrocinio> listaPatrocinios = sistemaEventos.consultarPatrociniosDeEdicion(edicionParam).getItem();
    	Map<String, DtPatrocinio> patrocinios = new HashMap<>();
    	  
    	listaPatrocinios.forEach(Patrocinio -> {
    		patrocinios.put(Patrocinio.getNombreInstitucion(), Patrocinio);
    	});
    	
        req.setAttribute("mapPatrocinios", patrocinios);
        req.getRequestDispatcher("/WEB-INF/pages/patrociniosDeEdicion/patrociniosDeEdicion.jsp")
            .forward(req, resp);
        return;

      } else if (edicionParam != null && institucionParam != null) {// Si tengo edicion e
                                                                    // institucion, voy a el
                                                                    // patrocinio especifico
    	  

	   	List<DtPatrocinio> listaPatrocinios = sistemaEventos.consultarPatrociniosDeEdicion(edicionParam).getItem();
    	Map<String, DtPatrocinio> patrocinios = new HashMap<>();
    	  
    	listaPatrocinios.forEach(Patrocinio -> {
    		patrocinios.put(Patrocinio.getNombreInstitucion(), Patrocinio);
    	});
    	
        DtEdicion edicion = sistemaEventos.infoEdicion(edicionParam);
        DtPatrocinio patrocinio = patrocinios.get(institucionParam);
        if (patrocinio == null) {
          throw new FileNotFoundException("no se encontro el patrocinio de edicion: " + edicionParam
              + " e institucion: " + institucionParam);
        }

        req.setAttribute("patrocinio", patrocinio);
        req.setAttribute("edicion", edicion);
        req.getRequestDispatcher("/WEB-INF/pages/consultaPatrocinio/consultaPatrocinio.jsp")
            .forward(req, resp);

        return;
      }

    }
    catch (IllegalArgumentException e) { // 400
      resp.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
    }
    catch (SecurityException e) { // 403
      resp.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
    }
    catch (AccessDeniedException e) { // 403
      resp.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
    }
    catch (FileNotFoundException e) { // 404
      req.setAttribute("error", e.getMessage());
      resp.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
    }
    catch (IOException e) { // 500
    	req.setAttribute("error", e.getMessage());
      resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
    }
    catch (Exception e) { // cualquier otra excepción inesperada → 500
      resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
    }

  }

  private void processRequestPOST(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

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
