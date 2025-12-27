package controllers;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.EstadoSesion;
import clienteServidor.publicar.*;
import utils.SistemasFactory;


@WebServlet("/iniciar-sesion")
public class Login extends HttpServlet {
  private static final long serialVersionUID = 1L;
  
	private IEventosControllerWebService sistemaEventos = SistemasFactory.getSistemaEventos();
	private CargarImagenesWebServices sistemaImagenes = SistemasFactory.getSistemaCargarImagenes();
	private IUsuariosControllerWebService sistemaUsuarios = SistemasFactory.getSistemaUsuarios();
	private IInstitucionesControllerWebService sistemaInstituciones = SistemasFactory.getSistemaInstituciones();

  
  public Login() {
    super();
  }

  // GET: mostrar el formulario JSP
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/pages/login/login.jsp");
    dispatcher.forward(request, response);
  }

  // POST: procesar credenciales
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    HttpSession objSesion = request.getSession();
    String login = request.getParameter("login");
    String password = request.getParameter("password");
    EstadoSesion nuevoEstado;

    try {

      if (sistemaUsuarios.autenticarUsuario(login, password)) {
        nuevoEstado = EstadoSesion.LOGIN_CORRECTO;
        DtUsuario usuarioBase;
        boolean esEmail = login != null && login.contains("@");

        if (esEmail) {
          usuarioBase = sistemaUsuarios.obtenerUsuarioEmail(login);
        } else {
          if(sistemaUsuarios.esAsistente(login)) {
            usuarioBase = sistemaUsuarios.getAsistente(login);
          }else {
            usuarioBase = sistemaUsuarios.getOrganizador(login);
          }
        }

        String nickname = usuarioBase.getNickname();
        String tipo = sistemaUsuarios.obtenerTipoUsuario(nickname);

        // cargamos el tipo concreto de DtO
        if (tipo.equals("organizador")) {
          DtOrganizador organizador = sistemaUsuarios.getOrganizador(nickname);
          objSesion.setAttribute("usuario_logueado", organizador);
          objSesion.setAttribute("tipo_usuario", "organizador");
        } else if (tipo.equals("asistente")) {
          DtAsistente asistente = sistemaUsuarios.getAsistente(nickname);
          objSesion.setAttribute("usuario_logueado", asistente);
          objSesion.setAttribute("tipo_usuario", "asistente");
        } else {
          // caso inesperado
          nuevoEstado = EstadoSesion.LOGIN_INCORRECTO;
          objSesion.setAttribute("estado_sesion", nuevoEstado);
          response.sendRedirect(request.getContextPath() + "/iniciar-sesion");
          return;
        }

        // marcamos estado de sesión correcto
        objSesion.setAttribute("estado_sesion", nuevoEstado);

        // redirigimos al home
        response.sendRedirect(request.getContextPath() + "/home");
        return;

      } else {
        nuevoEstado = EstadoSesion.LOGIN_INCORRECTO;
      }

    }
    catch (UsuarioNoExisteException_Exception ex) {
      nuevoEstado = EstadoSesion.LOGIN_INCORRECTO;
    }

    objSesion.setAttribute("estado_sesion", nuevoEstado);
    response.sendRedirect(request.getContextPath() + "/iniciar-sesion");
  }
}
