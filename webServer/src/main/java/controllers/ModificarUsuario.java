package controllers;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.AccessDeniedException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.ZoneId;

import javax.imageio.ImageIO;
import javax.xml.datatype.XMLGregorianCalendar;

import clienteServidor.publicar.*;
import utils.SistemasFactory;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;


@WebServlet("/editar-perfil") @MultipartConfig(fileSizeThreshold = 1024 * 1024, // 1MB
    maxFileSize = 1024 * 1024 * 5, // 5MB
    maxRequestSize = 1024 * 1024 * 10 // 10MB
)
public class ModificarUsuario extends HttpServlet {
  private static final long serialVersionUID = 1L;
  
	private IEventosControllerWebService sistemaEventos = SistemasFactory.getSistemaEventos();
	private CargarImagenesWebServices sistemaImagenes = SistemasFactory.getSistemaCargarImagenes();
	private IUsuariosControllerWebService sistemaUsuarios = SistemasFactory.getSistemaUsuarios();
	private IInstitucionesControllerWebService sistemaInstituciones = SistemasFactory.getSistemaInstituciones();

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    HttpSession session = request.getSession(false);
    if (session == null || session.getAttribute("usuario_logueado") == null) {
      response.sendRedirect(request.getContextPath() + "/iniciar-sesion");
      return;
    }

    DtUsuario usuario = (DtUsuario) session.getAttribute("usuario_logueado");
    String tipoUsuario = (String) session.getAttribute("tipo_usuario");

    // Guardar el objeto en el request
    String base64 = sistemaImagenes.getImagenUsuariosBase64(usuario.getImg());
    if (base64 == null || base64.isEmpty()) {
  	  usuario.setImg("media/imagenes/placeholderUsers.png"); 
    }else {
  	  String dataUrl = "data:image/png;base64," + base64;
  	  usuario.setImg(dataUrl);    	  
    }
    
    request.setAttribute("usuario", usuario);
    request.setAttribute("tipo_usuario", tipoUsuario);

    request.getRequestDispatcher("/WEB-INF/pages/editarPerfil/editarPerfil.jsp").forward(request,
        response);
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    HttpSession session = request.getSession(false);
    if (session == null || session.getAttribute("usuario_logueado") == null) {
      response.sendRedirect(request.getContextPath() + "/iniciar-sesion");
      return;
    }

    String tipoUsuario = (String) session.getAttribute("tipo_usuario");
    DtUsuario usuarioSesion = (DtUsuario) session.getAttribute("usuario_logueado");
    String nickname = usuarioSesion.getNickname();

    // campos comunes
    String nombreParam = request.getParameter("nombre");
    String passwordParam = request.getParameter("password");
    String passwordConfirm = request.getParameter("passwordConfirm");

    // validar confirmacion de password en servidor (si se envio pw)
    if (passwordParam != null && !passwordParam.trim().isEmpty()) {
      if (passwordConfirm == null || !passwordParam.equals(passwordConfirm)) {
        // podrías setear mensaje en session si querés mostrar feedback
        session.setAttribute("mensaje_editar", "Las contraseñas no coinciden.");
        response.sendRedirect(request.getContextPath() + "/editar-perfil");
        return;
      }
    } else {
      passwordParam = null; // indica "no cambiar"
    }

    // manejo de imagen subida (Part)
    String rutaFinal = "";
    Part imgPart = request.getPart("imagen");
    
    try {
      
		// --- Manejo de imagen ---
        if (imgPart != null && imgPart.getSize() > 0) {
          String nombreImagen = Paths.get(imgPart.getSubmittedFileName()).getFileName().toString();
          
          // Leer bytes de la imagen
          byte[] datosImagen = imgPart.getInputStream().readAllBytes();
          
          // Llamar al Web Service de imágenes
          rutaFinal = sistemaImagenes.subirImagenUsuarios(nombreImagen, datosImagen);
        		
      }
        
   
    }
    catch (IllegalStateException ise) {
      // file too big o request size excedida
      ise.printStackTrace();
      session.setAttribute("mensaje_editar", "La imagen es demasiado grande.");
      response.sendRedirect(request.getContextPath() + "/editar-perfil");
      return;
    }

    boolean huboCambio = false;

    // calcular valores finales (si param vacio -> mantener actual)
    String nombreFinal = (nombreParam == null || nombreParam.trim().isEmpty())
        ? usuarioSesion.getNombre()
        : nombreParam.trim();
    if (!equalsNullableTrim(nombreFinal, usuarioSesion.getNombre()))
      huboCambio = true;

    if (!equalsNullableTrim(rutaFinal, usuarioSesion.getImg()))
      huboCambio = true;

    // password: si passwordParam == null -> mantener; si no null -> cambiar
    if (passwordParam != null) {
      huboCambio = true;
    }

    if ("asistente".equalsIgnoreCase(tipoUsuario) && usuarioSesion instanceof DtAsistente) {
      DtAsistente asistente = (DtAsistente) usuarioSesion;

      String apellidoParam = request.getParameter("apellido");
      String fechaNacParam = request.getParameter("nacimiento");

      String apellidoFinal = (apellidoParam == null || apellidoParam.trim().isEmpty())
          ? asistente.getApellido()
          : apellidoParam.trim();
      if (!equalsNullableTrim(apellidoFinal, asistente.getApellido()))
        huboCambio = true;

      
      XMLGregorianCalendar  nacimientoFinal = asistente.getNacimiento();
      if (fechaNacParam != null && !fechaNacParam.trim().isEmpty()) {

        if (!((asistente.getNacimiento() == null && nacimientoFinal == null)
            || (asistente.getNacimiento() != null
                && asistente.getNacimiento().equals(nacimientoFinal)))) {
          huboCambio = true;
        }
      }

      if (!huboCambio) {
        // si no hubo ningún cambio, redirigir sin llamar al backend
        session.setAttribute("mensaje_editar", "No se detectaron cambios.");
        response.sendRedirect(request.getContextPath() + "/editar-perfil");
        return;
      }

      
      if (false) {
    		  //nacimientoFinal.isAfter(LocalDate.now())) {
          // si no hubo ningún cambio, redirigir sin llamar al backend
          session.setAttribute("mensaje_editar", "la fecha de nacimiento no puede ser mayor a la actua");
          response.sendRedirect(request.getContextPath() + "/editar-perfil");
          return;
      }
     
      
      // Llamar al controlador con valores finales (los que no cambiaron se pasan
      // igual que antes)
      sistemaUsuarios.editarAsistente(nickname, nombreFinal, apellidoFinal, nacimientoFinal.getYear(), nacimientoFinal.getMonth(), nacimientoFinal.getDay(), rutaFinal, passwordParam==null? "":passwordParam);

    } else if ("organizador".equalsIgnoreCase(tipoUsuario)
        && usuarioSesion instanceof DtOrganizador) {
      DtOrganizador organizador = (DtOrganizador) usuarioSesion;

      String descripcionParam = request.getParameter("descripcion");
      String sitioWebParam = request.getParameter("sitioWeb");

      String descripcionFinal = (descripcionParam == null || descripcionParam.trim().isEmpty())
          ? organizador.getDescripcion()
          : descripcionParam.trim();
      if (!equalsNullableTrim(descripcionFinal, organizador.getDescripcion()))
        huboCambio = true;

      String sitioWebFinal = (sitioWebParam == null || sitioWebParam.trim().isEmpty())
          ? organizador.getSitioWeb()
          : sitioWebParam.trim();
      if (!equalsNullableTrim(sitioWebFinal, organizador.getSitioWeb()))
        huboCambio = true;

      if (!huboCambio) {
        session.setAttribute("mensaje_editar", "No se detectaron cambios.");
        response.sendRedirect(request.getContextPath() + "/perfil");
        return;
      }
      sistemaUsuarios.editarOrganizador(nickname, nombreFinal, descripcionFinal, sitioWebFinal, rutaFinal, passwordParam==null? "":passwordParam);
    } else {
      // por defecto, si no es ninguno de los tipos esperados
      session.setAttribute("mensaje_editar", "Tipo de usuario inválido.");
      response.sendRedirect(request.getContextPath() + "/editar-perfil");
      return;
    }

    // actualizar sesión con datos actualizados desde controlador
    DtUsuario usuarioActualizado = "asistente".equalsIgnoreCase(tipoUsuario)
        ? sistemaUsuarios.getAsistente(nickname)
        : sistemaUsuarios.getOrganizador(nickname);

    session.setAttribute("usuario_logueado", usuarioActualizado);
    session.setAttribute("mensaje_editar", "Perfil actualizado correctamente.");

    response.sendRedirect(request.getContextPath() + "/perfil");
  }

  // helper: compara nullables con trim
  private boolean equalsNullableTrim(String firstElem, String secondElem) {
    if (firstElem == null && secondElem == null)
      return true;
    if (firstElem == null)
      return secondElem.trim().isEmpty();
    if (secondElem == null)
      return firstElem.trim().isEmpty();
    return firstElem.trim().equals(secondElem.trim());
  }
}
