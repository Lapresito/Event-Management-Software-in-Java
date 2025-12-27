package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import clienteServidor.publicar.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.SistemasFactory;


@WebServlet("/home")
public class Home extends HttpServlet {
  private static final long serialVersionUID = 1L;

	private IEventosControllerWebService sistemaEventos = SistemasFactory.getSistemaEventos();
	private CargarImagenesWebServices sistemaImagenes = SistemasFactory.getSistemaCargarImagenes();
	private IUsuariosControllerWebService sistemaUsuarios = SistemasFactory.getSistemaUsuarios();
	private IInstitucionesControllerWebService sistemaInstituciones = SistemasFactory.getSistemaInstituciones();
  
  private void processRequest(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    try {
      List<String> nombresEventos = sistemaEventos.listarEventos().getItem();
      List<DtEvento> eventosActuales = new ArrayList<>();

      for (String nombre : nombresEventos) {
        try {
          DtEvento evento = sistemaEventos.infoEvento(nombre);
          if (!evento.isFinalizado() && !evento.getEdiciones().isEmpty()) {
      	    // Guardar el objeto en el request
      	    String base64 = sistemaImagenes.getImagenEventosBase64(evento.getImg());
			      if (base64 == null || base64.isEmpty()) {
			    	  evento.setImg("media/imagenes/placeholderEvento-Edicion.png"); 
			      }else {
			    	  String dataUrl = "data:image/png;base64," + base64;
			    	  evento.setImg(dataUrl);    	  
			      }
        	  eventosActuales.add(evento);        	  
          }
        }
        catch (Exception ignored) {
        }
      }

      eventosActuales.sort(Comparator.comparing(DtEvento::getNombre));
      req.setAttribute("eventosActuales", eventosActuales);

    }
    catch (Exception e) {
      e.printStackTrace();
    }

    req.getRequestDispatcher("/WEB-INF/pages/home/home.jsp").forward(req, resp);
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {

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


  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    processRequest(request, response);
  }
}
