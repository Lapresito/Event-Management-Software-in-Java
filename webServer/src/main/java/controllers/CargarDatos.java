package controllers;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import clienteServidor.publicar.*;
import utils.SistemasFactory;
/**
 * Servlet implementation class CargaDatos
 */
@WebServlet("/CargarDatos")
public class CargarDatos extends HttpServlet {
  private static final long serialVersionUID = 1L;

  private CargarDatosWebService sistemaCargarDatos = SistemasFactory.getSistemaCargarDatos();
  /**
   * @see HttpServlet#HttpServlet()
   */
  public CargarDatos() {
    super();
    // TODO Auto-generated constructor stub
  }

  private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	 sistemaCargarDatos.cargarDatos();
	 resp.sendRedirect(req.getContextPath() + "/home"); // o la ruta de tu home
  }

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
   */
  @Override protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    // TODO Auto-generated method stub
    processRequest(request, response);
  }

  /**
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
   */
  @Override protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    // TODO Auto-generated method stub
    processRequest(request, response);
  }

}
