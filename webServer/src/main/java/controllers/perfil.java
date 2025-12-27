package controllers;

import java.io.File;
import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import clienteServidor.publicar.*;
import utils.SistemasFactory;

@WebServlet("/perfil")
public class perfil extends HttpServlet {
  private static final long serialVersionUID = 1L;
  
	private IEventosControllerWebService sistemaEventos = SistemasFactory.getSistemaEventos();
	private CargarImagenesWebServices sistemaImagenes = SistemasFactory.getSistemaCargarImagenes();
	private IUsuariosControllerWebService sistemaUsuarios = SistemasFactory.getSistemaUsuarios();
	private IInstitucionesControllerWebService sistemaInstituciones = SistemasFactory.getSistemaInstituciones();
  
  public perfil() {
    super();
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    HttpSession session = request.getSession(false);

    if (session == null || session.getAttribute("usuario_logueado") == null) {
      response.sendRedirect(request.getContextPath() + "/iniciar-sesion");
      return;
    }


    
    DtUsuario usuario = (DtUsuario) session.getAttribute("usuario_logueado");
    String tipo = (String) session.getAttribute("tipo_usuario");

    if("asistente".equalsIgnoreCase(tipo)) {
    	usuario = (DtUsuario) sistemaUsuarios.getAsistente(usuario.getNickname());
    }else {
    	usuario = (DtUsuario) sistemaUsuarios.getOrganizador(usuario.getNickname());
    }
    


    List<String> listaAcciones = null;
    if ("asistente".equalsIgnoreCase(tipo)) {
      listaAcciones = sistemaUsuarios.listarRegistrosAsistente(usuario.getNickname()).getItem();
    } else if ("organizador".equalsIgnoreCase(tipo)) {
      listaAcciones = sistemaUsuarios.listarEdicionesDeOrganizador(usuario.getNickname()).getItem();
    }
    
	// Guardar el objeto en el request
	String base64 = sistemaImagenes.getImagenUsuariosBase64(usuario.getImg());
    if (base64 == null || base64.isEmpty()) {
    	usuario.setImg("media/imagenes/placeholderUsers.png"); 
      }else {
    	  String dataUrl = "data:image/png;base64," + base64;
    	  usuario.setImg(dataUrl);    	  
      }

    request.setAttribute("sistemaUsers", sistemaUsuarios);

    request.setAttribute("usuario", usuario);
    request.setAttribute("tipo", tipo);
    request.setAttribute("listaAcciones", listaAcciones);
  

    RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/pages/perfil/perfil.jsp");
    dispatcher.forward(request, response);
  }
}
