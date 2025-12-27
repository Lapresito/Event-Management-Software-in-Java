package controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import clienteServidor.publicar.*;
import utils.SistemasFactory;

@WebServlet("/getDetalleEdicionParaRegistro")
public class getDetalleEdicionParaRegistro extends HttpServlet {
  private static final long serialVersionUID = 1L;
  
	private IEventosControllerWebService sistemaEventos = SistemasFactory.getSistemaEventos();
	private CargarImagenesWebServices sistemaImagenes = SistemasFactory.getSistemaCargarImagenes();
	private IUsuariosControllerWebService sistemaUsuarios = SistemasFactory.getSistemaUsuarios();
	private IInstitucionesControllerWebService sistemaInstituciones = SistemasFactory.getSistemaInstituciones();


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

      String edicion = request.getParameter("edicion");

      if (edicion == null || edicion.trim().isEmpty()) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.getWriter().write("{\"error\": \"Falta parámetro 'edicion'\"}");
        return;
      }

      try {
        // 🔹 Obtener la edición del WS
        DtEdicion wsEdicion = sistemaEventos.infoEdicion(edicion.trim());

        if (wsEdicion == null) {
          response.setStatus(HttpServletResponse.SC_NOT_FOUND);
          response.getWriter().write("{\"error\": \"Edición no encontrada\"}");
          return;
        }

        // 🔹 Crear un DTO plano para devolver como JSON
        Map<String, Object> dto = new HashMap<>();
        dto.put("nombreEdicion", wsEdicion.getNombreEdicion());
        dto.put("nombreEvento", wsEdicion.getNombreEvento());
        dto.put("organizador", wsEdicion.getOrganizador());
        dto.put("sigla", wsEdicion.getSigla());
        dto.put("ciudad", wsEdicion.getCiudad());
        dto.put("pais", wsEdicion.getPais());
        dto.put("estado", wsEdicion.getEstado());
        dto.put("imagen", wsEdicion.getImagen());
        dto.put("videoURL", wsEdicion.getVideoURL());

        // 🔹 Convertir fechas de XMLGregorianCalendar → String
        if (wsEdicion.getFechaAlta() != null)
          dto.put("fechaAlta", wsEdicion.getFechaAlta().toGregorianCalendar().toZonedDateTime().toLocalDate().toString());
        if (wsEdicion.getFechaIni() != null)
          dto.put("fechaIni", wsEdicion.getFechaIni().toGregorianCalendar().toZonedDateTime().toLocalDate().toString());
        if (wsEdicion.getFechaFin() != null)
          dto.put("fechaFin", wsEdicion.getFechaFin().toGregorianCalendar().toZonedDateTime().toLocalDate().toString());

        // 🔹 Serializar a JSON y enviar al frontend
        String json = new Gson().toJson(dto);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);

      } catch (Exception e) {
        e.printStackTrace();
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        response.getWriter().write("{\"error\": \"" + e.getMessage() + "\"}");
      }
    }
  }
