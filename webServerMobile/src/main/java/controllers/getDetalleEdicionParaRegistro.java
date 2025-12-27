package controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.SistemasFactory;
import clienteServidor.publicar.*;

@WebServlet("/getDetalleEdicionParaRegistro")
public class getDetalleEdicionParaRegistro extends HttpServlet {
  private static final long serialVersionUID = 1L;
  
	private IEventosControllerWebService sistemaEventos = SistemasFactory.getSistemaEventos();
	private CargarImagenesWebServices sistemaImagenes = SistemasFactory.getSistemaCargarImagenes();
	private IUsuariosControllerWebService sistemaUsuarios = SistemasFactory.getSistemaUsuarios();
	private IInstitucionesControllerWebService sistemaInstituciones = SistemasFactory.getSistemaInstituciones();

  public getDetalleEdicionParaRegistro() {
    super();
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

    
    String edicion = request.getParameter("edicion");

    if (edicion == null || edicion.trim().isEmpty()) {
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      response.getWriter().write("{\"error\": \"Falta parámetro 'edicion'\"}");
      return;
    }

    try {
      DtEdicion dtEdicion = sistemaEventos.infoEdicion(edicion.trim());

      if (dtEdicion == null) {
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        response.getWriter().write("{\"error\": \"Edición no encontrada\"}");
        return;
      }

      Gson gson = new GsonBuilder()
          .registerTypeAdapter(LocalDate.class,
              (com.google.gson.JsonSerializer<LocalDate>) (src, typeOfSrc, context) -> src == null
                  ? null
                  : new com.google.gson.JsonPrimitive(src.format(DateTimeFormatter.ISO_LOCAL_DATE)))
          .create();

      response.setContentType("application/json");
      response.setCharacterEncoding("UTF-8");
      response.getWriter().write(gson.toJson(dtEdicion));

    }
    catch (Exception e) {
      e.printStackTrace();
      response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      response.getWriter().write("{\"error\": \"" + e.getMessage() + "\"}");
    }
  }
}