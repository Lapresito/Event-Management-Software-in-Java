package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import clienteServidor.publicar.*;
import utils.SistemasFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/edicion")
public class Ediciones extends HttpServlet {
  private static final long serialVersionUID = 1L;
  
	private IEventosControllerWebService sistemaEventos = SistemasFactory.getSistemaEventos();
	private CargarImagenesWebServices sistemaImagenes = SistemasFactory.getSistemaCargarImagenes();
	private IUsuariosControllerWebService sistemaUsuarios = SistemasFactory.getSistemaUsuarios();
	private IInstitucionesControllerWebService sistemaInstituciones = SistemasFactory.getSistemaInstituciones();
  
  public Ediciones() {
    super();
    // TODO Auto-generated constructor stub
  }

  private void processRequest(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    try {
      String nombreEdicion = req.getParameter("nombreEdicion");
      System.out.println("Nombre recibido: '" + nombreEdicion + "'");
      if (nombreEdicion == null || nombreEdicion.isEmpty()) {
        // Si falta el parámetro?
        // resp.sendRedirect("listaEdiciones.jsp");
        return;
      }

      DtEdicion edicion = sistemaEventos.infoEdicion(nombreEdicion);
      
      
      HttpSession session = req.getSession(false);
      EstadoEdicion estado = EstadoEdicion.valueOf(edicion.getEstado().toUpperCase());

      if (estado != EstadoEdicion.ACEPTADA) {

        if (session == null || session.getAttribute("usuario_logueado") == null) {
          req.setAttribute("error", "No permitido");
          req.setAttribute("descripcionError",
              "La edicion no es aceptada. No tiene permisos para acceder a la consulta.");
          resp.sendError(HttpServletResponse.SC_FORBIDDEN,
              "No tiene permiso para ver este recurso");
          return;
        }

        DtUsuario usuario = (DtUsuario) session.getAttribute("usuario_logueado");
        

        String nicknameUser = usuario.getNickname();
        String nicknameOrg = edicion.getOrganizador();
        
        if (!nicknameUser.equals(nicknameOrg)) {
          req.setAttribute("error", "No permitido");
          req.setAttribute("descripcionError",
              "Solo el organizador de esta edicion puede consultarla.");
          resp.sendError(HttpServletResponse.SC_FORBIDDEN,
              "No tiene permiso para ver este recurso");
          return;
        }
      }

      // Guardar el objeto en el request
      String base64 = sistemaImagenes.getImagenEdicionesBase64(edicion.getImagen());
      
      if (base64 == null || base64.isEmpty()) {
    	  edicion.setImagen("media/imagenes/placeholderEvento-Edicion.png"); 
      }else {
    	  String dataUrl = "data:image/png;base64," + base64;
    	  edicion.setImagen(dataUrl);    	  
      }
      req.setAttribute("edicion", edicion);
      

      List<String> tiposReg = sistemaEventos.listarTiposDeRegistroDeEdicion(nombreEdicion).getItem();
      req.setAttribute("listaTRegistro", tiposReg);

      List<DtRegistro> regs = sistemaEventos.listarRegistrosDeEdicion(edicion.getNombreEvento(),nombreEdicion).getItem();
      req.setAttribute("listaRegistros", regs);
      
	    List<DtRegistro> registrosAsistidos = new ArrayList<>();
	    regs.forEach(reg ->{
	    	if(reg.isAsistio()) {
	    		registrosAsistidos.add(reg);
	    	}
	    });
	    req.setAttribute("registrosAsistidos", registrosAsistidos);
      

      List<String> pats = sistemaEventos.listarPatrociniosDeEdicion(edicion.getNombreEvento(),nombreEdicion).getItem();
      req.setAttribute("listaPatrocinios", pats);

      // Reenviar la solicitud a la JSP
      req.getRequestDispatcher("/WEB-INF/pages/edicion/edicion.jsp").forward(req, resp);

    }
    catch (IllegalArgumentException e) {
      // e.printStackTrace();
      req.setAttribute("error", e.getMessage());
      resp.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
    }
  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    processRequest(request, response);
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    processRequest(request, response);
  }

}