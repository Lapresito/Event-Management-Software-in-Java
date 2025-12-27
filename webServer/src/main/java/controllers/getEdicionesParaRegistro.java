package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
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
    String evento = request.getParameter("evento");

    if (evento == null || evento.isEmpty()) {
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      response.getWriter().write("{\"error\": \"Evento no especificado\"}");
      return;
    }

    List<String> ediciones = sistemaEventos.listarEdicionesEvento(evento).getItem();
    List<String> edicionesAceptadas = new ArrayList<>();

    // Obtener fecha de hoy
    Calendar hoy = Calendar.getInstance();
    int diaHoy = hoy.get(Calendar.DAY_OF_MONTH);
    int mesHoy = hoy.get(Calendar.MONTH) + 1; // OJO: Calendar usa 0-11
    int anioHoy = hoy.get(Calendar.YEAR);

    for (String edic : ediciones) {
        DtEdicion edicion = sistemaEventos.infoEdicion(edic);
        EstadoEdicion estado = EstadoEdicion.valueOf(edicion.getEstado().toUpperCase());

        // Fecha de fin de la edición
        int dia = edicion.getFechaFin().getDay();
        int mes = edicion.getFechaFin().getMonth();
        int anio = edicion.getFechaFin().getYear();

        // Comparar fechaFin >= hoy
        boolean fechaValida = false;

        if (anio > anioHoy) {
            fechaValida = true;
        } else if (anio == anioHoy) {
            if (mes > mesHoy) {
                fechaValida = true;
            } else if (mes == mesHoy && dia >= diaHoy) {
                fechaValida = true;
            }
        }

        if (estado == EstadoEdicion.ACEPTADA && fechaValida) {
            edicionesAceptadas.add(edic);
        }
    }

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
