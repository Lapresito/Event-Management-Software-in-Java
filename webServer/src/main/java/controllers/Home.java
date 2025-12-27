package controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import clienteServidor.publicar.*;
import utils.SistemasFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


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

      eventosActuales = eventosActuales.stream()
              .sorted(Comparator.comparingInt(DtEvento::getVisitas).reversed())
              .limit(6)
              .collect(Collectors.toList());
      
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
    processRequest(request, response);
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    processRequest(request, response);
  }
}
