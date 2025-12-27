package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.SistemasFactory;
import clienteServidor.publicar.*;

/**
 * Servlet implementation class getEdicionesParaRegistro
 */
@WebServlet("/getEdicionesParaRegistro")
public class getEdicionesParaRegistro extends HttpServlet {
  private static final long serialVersionUID = 1L;
  
	private IEventosControllerWebService sistemaEventos = SistemasFactory.getSistemaEventos();
	private CargarImagenesWebServices sistemaImagenes = SistemasFactory.getSistemaCargarImagenes();
	private IUsuariosControllerWebService sistemaUsuarios = SistemasFactory.getSistemaUsuarios();
	private IInstitucionesControllerWebService sistemaInstituciones = SistemasFactory.getSistemaInstituciones();

  /**
   * @see HttpServlet#HttpServlet()
   */
  public getEdicionesParaRegistro() {
    super();
    // TODO Auto-generated constructor stub
  }

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    
    // Verificar si el usuario está logueado
    Object usuario = request.getSession().getAttribute("usuario_logueado");

    if (usuario == null) {
        // 403 Forbidden
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.sendRedirect(request.getContextPath() + "/iniciar-sesion");
        return;
    }

    
    String evento = request.getParameter("evento");

    
    if (evento == null || evento.isEmpty()) {
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      response.getWriter().write("{\"error\": \"Evento no especificado\"}");
      return;
    }

	List<String> ediciones = sistemaEventos.listarEdicionesEvento(evento).getItem();
	List<String> edicionesAceptadas = new ArrayList<>();
	ediciones.forEach(edic->{
		DtEdicion edicion = sistemaEventos.infoEdicion(edic);
		EstadoEdicion estado = EstadoEdicion.valueOf(edicion.getEstado().toUpperCase());
		if(estado == EstadoEdicion.ACEPTADA) edicionesAceptadas.add(edic);
	});
		
	String json = new Gson().toJson(edicionesAceptadas);

    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    response.getWriter().write(json);
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
