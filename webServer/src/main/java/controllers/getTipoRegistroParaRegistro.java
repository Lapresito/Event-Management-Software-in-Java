package controllers;

import java.io.IOException;
import java.util.List;

import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import clienteServidor.publicar.*;
import utils.SistemasFactory;

/**
 * Servlet implementation class getTipoRegistroParaRegistro
 */
@WebServlet("/getTipoRegistroParaRegistro")
public class getTipoRegistroParaRegistro extends HttpServlet {
  private static final long serialVersionUID = 1L;
  
	private IEventosControllerWebService sistemaEventos = SistemasFactory.getSistemaEventos();
	private CargarImagenesWebServices sistemaImagenes = SistemasFactory.getSistemaCargarImagenes();
	private IUsuariosControllerWebService sistemaUsuarios = SistemasFactory.getSistemaUsuarios();
	private IInstitucionesControllerWebService sistemaInstituciones = SistemasFactory.getSistemaInstituciones();

  /**
   * @see HttpServlet#HttpServlet()
   */
  public getTipoRegistroParaRegistro() {
    super();
    // TODO Auto-generated constructor stub
  }

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    // TODO Auto-generated method stub
    String edicion = request.getParameter("edicion");

    if (edicion == null || edicion.trim().isEmpty()) {
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      response.getWriter().write("{\"error\": \"Falta parámetro 'edicion'\"}");
      return;
    }

    try {
      List<String> tiposRegistro = sistemaEventos.listarTiposDeRegistroDeEdicion(edicion).getItem();

      String json = new Gson().toJson(tiposRegistro);

      response.setContentType("application/json");
      response.setCharacterEncoding("UTF-8");
      response.getWriter().write(json);

    }
    catch (Exception e) {
      e.printStackTrace();
      response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      response.getWriter().write("{\"error\": \"" + e.getMessage() + "\"}");
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
