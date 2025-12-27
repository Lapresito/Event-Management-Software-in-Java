package controllers;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import clienteServidor.publicar.*;

import model.EstadoSesion;
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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/pages/login/login.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession objSesion = request.getSession();

        // Inicializamos si no existe
        if (objSesion.getAttribute("fue_org") == null) {
            objSesion.setAttribute("fue_org", "false");
        }

        String login = request.getParameter("login");
        String password = request.getParameter("password");

        EstadoSesion nuevoEstado;

        try {
            // 1. Autenticación base
            if (sistemaUsuarios.autenticarUsuario(login, password)) {

                nuevoEstado = EstadoSesion.LOGIN_CORRECTO;

                DtUsuario usuarioBase;
                boolean esEmail = login != null && login.contains("@");

                // Cargamos usuario correctamente según nickname/email + organizador/asistente
                if (esEmail) {
                    usuarioBase = sistemaUsuarios.obtenerUsuarioEmail(login);
                } else {
                    boolean esOrg = sistemaUsuarios.esOrganizador(login);

                    if (esOrg) {
                        usuarioBase = sistemaUsuarios.getOrganizador(login);
                    } else {
                        usuarioBase = sistemaUsuarios.getAsistente(login);
                    }
                }

                String nickname = usuarioBase.getNickname();
                String tipo = sistemaUsuarios.obtenerTipoUsuario(nickname);

                // 2. Lógica según tipo de usuario
                if (tipo.equals("organizador")) {

                    // Organizador NO puede entrar
                    objSesion.setAttribute("fue_org", "true");
                    response.sendRedirect(request.getContextPath() + "/iniciar-sesion");
                    return;
                }

                if (tipo.equals("asistente")) {

                    // Login válido → guardar en sesión
                    objSesion.setAttribute("usuario_logueado", usuarioBase);
                    objSesion.setAttribute("tipo_usuario", "asistente");
                    objSesion.setAttribute("estado_sesion", EstadoSesion.LOGIN_CORRECTO);

                    // Redireccionar al home
                    response.sendRedirect(request.getContextPath() + "/home");
                    return;
                }

                // Cualquier otro tipo → inválido
                nuevoEstado = EstadoSesion.LOGIN_INCORRECTO;
                objSesion.setAttribute("estado_sesion", nuevoEstado);
                response.sendRedirect(request.getContextPath() + "/iniciar-sesion");
                return;

            } else {
                // Autenticación falló
                nuevoEstado = EstadoSesion.LOGIN_INCORRECTO;
            }

        } catch (UsuarioNoExisteException_Exception ex) {
            nuevoEstado = EstadoSesion.LOGIN_INCORRECTO;
        }

        // Error general
        objSesion.setAttribute("estado_sesion", nuevoEstado);
        response.sendRedirect(request.getContextPath() + "/iniciar-sesion");
    }
}
