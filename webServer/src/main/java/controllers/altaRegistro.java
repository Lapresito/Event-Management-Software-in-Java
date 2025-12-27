package controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.time.LocalDate;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import clienteServidor.publicar.*;
import utils.SistemasFactory;

@WebServlet("/altaRegistro")
public class altaRegistro extends HttpServlet {
  private static final long serialVersionUID = 1L;
  
	private IEventosControllerWebService sistemaEventos = SistemasFactory.getSistemaEventos();
	private CargarImagenesWebServices sistemaImagenes = SistemasFactory.getSistemaCargarImagenes();
	private IUsuariosControllerWebService sistemaUsuarios = SistemasFactory.getSistemaUsuarios();
	private IInstitucionesControllerWebService sistemaInstituciones = SistemasFactory.getSistemaInstituciones();
  

  public altaRegistro() {
    super();
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    try {

      // Validacion de acceso
      HttpSession session = req.getSession(false);

      if (session == null || session.getAttribute("usuario_logueado") == null) {
        resp.sendRedirect(req.getContextPath() + "/iniciar-sesion");
        return;
      }

      String tipo = (String) session.getAttribute("tipo_usuario");

	 String nombreEventoSeleccionado = req.getParameter("evento");
	 String nombreEdicionSeleccionada = req.getParameter("edicion");
	 String error = req.getParameter("error");
	 
	 req.setAttribute("error", error);
     req.setAttribute("eventoSeleccionado", nombreEdicionSeleccionada);
     req.setAttribute("edicionSeleccionado", nombreEventoSeleccionado);
      
      boolean acceso = "asistente".equalsIgnoreCase(tipo);
      if (!acceso) {
        req.setAttribute("error", "Acceso No permitido.");
        req.setAttribute("descripcionError", "Para Acceder a este sito tiene que ser asistente.");
        throw new AccessDeniedException("Acceso No permitido.");
      }

      List<String> eventos = sistemaEventos.listarEventos().getItem();

      req.setAttribute("eventos", eventos);
      req.getRequestDispatcher("/WEB-INF/pages/altaRegistro/altaRegistro.jsp").forward(req, resp);
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
      resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
    }
    catch (Exception e) { // cualquier otra excepción inesperada → 500
      resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
    }
  }

@Override
protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    try {
        HttpSession objSesion = request.getSession(false);
        if (objSesion == null || objSesion.getAttribute("usuario_logueado") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        DtAsistente usuario_logueado = (DtAsistente) objSesion.getAttribute("usuario_logueado");
        String nicknameAsistente = usuario_logueado.getNickname();
        String institucionDeAsistente = usuario_logueado.getInstitucion();

        String nombreEvento = request.getParameter("eventos");
        String nombreEdicion = request.getParameter("ediciones");
        String codigo = request.getParameter("codigoPatrocinio");
        String tipoRegistro = request.getParameter("tipoRegistro");

        // Guardamos para repoblar el formulario en caso de error
        request.setAttribute("eventoSeleccionado", nombreEvento);
        request.setAttribute("edicionSeleccionada", nombreEdicion);
        request.setAttribute("tipoRegistroSeleccionado", tipoRegistro);
        request.setAttribute("codigoIngresado", codigo);

        // Validaciones
        if (nombreEvento == null || nombreEvento.isEmpty() ||
            nombreEdicion == null || nombreEdicion.isEmpty()) {
            request.setAttribute("error", "Debe seleccionar un evento y una edición.");
            List<String> eventos = sistemaEventos.listarEventos().getItem();
            request.setAttribute("eventos", eventos);
            request.getRequestDispatcher("/WEB-INF/pages/altaRegistro/altaRegistro.jsp").forward(request, response);
            return;
        }

        if (codigo == null || codigo.trim().isEmpty()) {
            // Registro sin código
            try {
                sistemaUsuarios.altaRegistro(nicknameAsistente, nombreEdicion, tipoRegistro);
            } catch (IOException_Exception e) {
                request.setAttribute("error", e.getMessage());
                List<String> eventos = sistemaEventos.listarEventos().getItem();
                request.setAttribute("eventos", eventos);
                request.getRequestDispatcher("/WEB-INF/pages/altaRegistro/altaRegistro.jsp").forward(request, response);
                return;
            }

            response.sendRedirect(request.getContextPath() + "/perfil");
            return;
        } else {
            // Registro con código
            boolean existeCodigo = sistemaEventos.existeCodigoDePatrocinioEnEdicion(codigo, nombreEvento, nombreEdicion);
            if (!existeCodigo) {
                request.setAttribute("error", "El código ingresado no es válido para esta edición.");
            } else {
                DtPatrocinio patrocinio = sistemaEventos.consultarPatrocinio(nombreEdicion, codigo);
                String tipoDelCodigo = patrocinio.getDTtipoRegistro();
                String institucionDelPatrocinio = patrocinio.getNombreInstitucion();

                if (!tipoDelCodigo.equals(tipoRegistro)) {
                    request.setAttribute("error", "El tipo de registro seleccionado no corresponde al código ingresado.");
                } else if (!institucionDelPatrocinio.equals(institucionDeAsistente)) {
                    request.setAttribute("error",
                            "El asistente " + nicknameAsistente +
                            " no pertenece a la institución que patrocina la edición con el código " + codigo);
                } else {
                    try {
                        sistemaUsuarios.altaRegistroConCodigo(nicknameAsistente, nombreEdicion, tipoRegistro, codigo);
                        response.sendRedirect(request.getContextPath() + "/perfil");
                        return;
                    } catch (IOException_Exception e) {
                        request.setAttribute("error", e.getMessage());
                    }
                }
            }

            // Si hubo error, volvemos al formulario
            List<String> eventos = sistemaEventos.listarEventos().getItem();
            request.setAttribute("eventos", eventos);
            request.getRequestDispatcher("/WEB-INF/pages/altaRegistro/altaRegistro.jsp").forward(request, response);
        }

    } catch (Exception e) {
        request.setAttribute("error", "Ocurrió un error: " + e.getMessage());
        List<String> eventos = sistemaEventos.listarEventos().getItem();
        request.setAttribute("eventos", eventos);
        request.getRequestDispatcher("/WEB-INF/pages/altaRegistro/altaRegistro.jsp").forward(request, response);
    }
}

}