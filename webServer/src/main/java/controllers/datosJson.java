package controllers;

import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import clienteServidor.publicar.*;
import utils.SistemasFactory;


//Servlet para devolver datos json para recargar dinamicamente los select de los forms 
//PARMAS 
/*evento=all & edicion=null & organizador=null & tiposRegistro=null & institucion=null
 *  -> Retorna todos los eventos 
evento=ev & edicion=all & organizador=null & tiposRegistro=null & institucion=null
 -> Retorna todos las ediciones de un evento 
evento=ev & edicion=all & organizador=org & tiposRegistro=null & institucion=null
 -> Retorna todos las ediciones de un evento organizadas por org 
evento=null & edicion=ed & organizador=null & tiposRegistro=all & institucion=null
 -> Retorna todos los tipos de registro de una edición 
evento=null & edicion=null & organizador=null & tiposRegistro=null & institucion=inst
 -> Retorna todos las instituciones
*/
//categorias=all

@WebServlet("/datosJson")
public class datosJson extends HttpServlet {
  private static final long serialVersionUID = 1L;
  
  private IEventosControllerWebService sistemaEventos = SistemasFactory.getSistemaEventos();
  private CargarImagenesWebServices sistemaImagenes = SistemasFactory.getSistemaCargarImagenes();
  private IUsuariosControllerWebService sistemaUsuarios = SistemasFactory.getSistemaUsuarios();
  private IInstitucionesControllerWebService sistemaInstituciones = SistemasFactory.getSistemaInstituciones();

  private final Gson gson = new Gson();

  public datosJson() {
    super();
  }

  private void processRequestGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    resp.setContentType("application/json");
    resp.setCharacterEncoding("UTF-8");

    // Obtener parámetros
    String eventoParam = req.getParameter("evento");
    String edicionParam = req.getParameter("edicion");
    String organizadorParam = req.getParameter("organizador");
    String tiposRegistroParam = req.getParameter("tiposRegistro");
    String institucionParam = req.getParameter("institucion");
    String categoriasParam = req.getParameter("categorias");
    String existeUserMailParam = req.getParameter("existeMail");
    String existeUserNickParam = req.getParameter("existeNick");

    // Normalizar parámetros vacíos
    eventoParam = (eventoParam != null && !eventoParam.trim().isEmpty()) ? eventoParam.trim()
        : null;
    edicionParam = (edicionParam != null && !edicionParam.trim().isEmpty()) ? edicionParam.trim()
        : null;
    organizadorParam = (organizadorParam != null && !organizadorParam.trim().isEmpty())
        ? organizadorParam.trim()
        : null;
    tiposRegistroParam = (tiposRegistroParam != null && !tiposRegistroParam.trim().isEmpty())
        ? tiposRegistroParam.trim()
        : null;
    institucionParam = (institucionParam != null && !institucionParam.trim().isEmpty())
        ? institucionParam.trim()
        : null;
    categoriasParam = (categoriasParam != null && !categoriasParam.trim().isEmpty())
        ? categoriasParam.trim()
        : null;
    existeUserMailParam = (existeUserMailParam != null && !existeUserMailParam.trim().isEmpty())
            ? existeUserMailParam.trim()
            : null;
    existeUserNickParam = (existeUserNickParam != null && !existeUserNickParam.trim().isEmpty())
            ? existeUserNickParam.trim()
            : null;

    List<String> respuesta = new ArrayList<>();

    try {
      // TODOS LAS CATEGORIAS
      if ("all".equals(categoriasParam)) {
        respuesta = sistemaEventos.listarCategorias().getItem();
        resp.getWriter().write(gson.toJson(respuesta));
        return;
      }

      // TODOS LOS EVENTOS
      if ("all".equals(eventoParam) && edicionParam == null && organizadorParam == null
          && tiposRegistroParam == null && institucionParam == null) {
        respuesta = sistemaEventos.listarEventos().getItem();
        resp.getWriter().write(gson.toJson(respuesta));
        return;
      }

   // TODAS LAS EDICIONES DE UN EVENTO (solo aceptadas y no finalizadas)
      if (eventoParam != null && "all".equals(edicionParam) && organizadorParam == null
          && tiposRegistroParam == null && institucionParam == null) {

          List<String> ediciones = sistemaEventos.listarEdicionesEvento(eventoParam).getItem();
          List<String> edicionesFiltradas = new ArrayList<>();

          // Fecha actual
          java.util.Calendar hoy = java.util.Calendar.getInstance();
          int diaHoy = hoy.get(java.util.Calendar.DAY_OF_MONTH);
          int mesHoy = hoy.get(java.util.Calendar.MONTH) + 1;
          int anioHoy = hoy.get(java.util.Calendar.YEAR);

          for (String edic : ediciones) {
              DtEdicion edicion = sistemaEventos.infoEdicion(edic);
              EstadoEdicion estado = EstadoEdicion.valueOf(edicion.getEstado().toUpperCase());

              // Obtener fecha fin
              int dia = edicion.getFechaFin().getDay();
              int mes = edicion.getFechaFin().getMonth();
              int anio = edicion.getFechaFin().getYear();

              // Calcular si fechaFin >= hoy
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
                  edicionesFiltradas.add(edic);
              }
          }

          resp.getWriter().write(gson.toJson(edicionesFiltradas));
          return;
      }


   // EDICIONES DE UN EVENTO ORGANIZADAS POR UN ORGANIZADOR (solo aceptadas y no finalizadas)
      if (eventoParam != null && "all".equals(edicionParam) && organizadorParam != null 
              && tiposRegistroParam == null && institucionParam == null) {

          List<String> ediciones = sistemaEventos.listarEdicionesEvento(eventoParam).getItem();
          List<String> edicionesOrganizadas = new ArrayList<>();

          // Fecha actual
          java.util.Calendar hoy = java.util.Calendar.getInstance();
          int diaHoy = hoy.get(java.util.Calendar.DAY_OF_MONTH);
          int mesHoy = hoy.get(java.util.Calendar.MONTH) + 1;
          int anioHoy = hoy.get(java.util.Calendar.YEAR);

          String organizador = organizadorParam;

          for (String edic : ediciones) {
              DtEdicion edicion = sistemaEventos.infoEdicion(edic);
              EstadoEdicion estado = EstadoEdicion.valueOf(edicion.getEstado().toUpperCase());

              // Obtener fecha fin
              int dia = edicion.getFechaFin().getDay();
              int mes = edicion.getFechaFin().getMonth();
              int anio = edicion.getFechaFin().getYear();

              // Validar fecha fin >= hoy
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

              if (organizador.equals(edicion.getOrganizador()) 
                      && estado == EstadoEdicion.ACEPTADA 
                      && fechaValida) {
                  edicionesOrganizadas.add(edic);
              }
          }

          resp.getWriter().write(gson.toJson(edicionesOrganizadas));
          return;
      }


      // TIPOS DE REGISTRO DE UNA EDICIÓN
      if (eventoParam == null && edicionParam != null && "all".equals(tiposRegistroParam)
          && organizadorParam == null && institucionParam == null) {
        respuesta = sistemaEventos.listarTiposDeRegistroDeEdicion(edicionParam).getItem();
        resp.getWriter().write(gson.toJson(respuesta));
        return;
      }

      // TODAS LAS INSTITUCIONES
      if (eventoParam == null && edicionParam == null && tiposRegistroParam == null && organizadorParam == null && institucionParam != null) {
    	  
        DtInstitucion[] institucionesDt = sistemaInstituciones.getInstituciones().getItem().toArray(new DtInstitucion[0]);

        if (institucionesDt != null) {
          respuesta = Arrays.stream(institucionesDt).map(DtInstitucion::getNombre)
              .collect(Collectors.toList());
        }
        resp.getWriter().write(gson.toJson(respuesta));

        return;
      }
      
      // True si existe nick
      if (eventoParam == null && edicionParam == null && tiposRegistroParam == null && organizadorParam == null && institucionParam == null && existeUserMailParam == null && existeUserNickParam != null) {
    	    DtUsuario user = null;

    	    try {
    	        user = sistemaUsuarios.getAsistente(existeUserNickParam);
    	    } catch (com.sun.xml.ws.fault.ServerSOAPFaultException e) {
    	        // Ignorar: puede no ser asistente
    	    }

    	    if (user == null) {
    	        try {
    	            user = sistemaUsuarios.getOrganizador(existeUserNickParam);
    	        } catch (com.sun.xml.ws.fault.ServerSOAPFaultException e) {
    	            // Ignorar: puede no ser organizador
    	        }
    	    }

    	    boolean existe = user != null;

    	    resp.setContentType("application/json;charset=UTF-8");
    	    resp.getWriter().write(gson.toJson(existe));
    	    return;
      }
      
      // True si existe correo
      if (eventoParam == null && edicionParam == null && tiposRegistroParam == null && organizadorParam == null && institucionParam == null && existeUserMailParam != null && existeUserNickParam == null) {

	      boolean existe;
	      try {
	          DtUsuario user = sistemaUsuarios.obtenerUsuarioEmail(existeUserMailParam);
	          existe = (user != null);
	      } catch (UsuarioNoExisteException_Exception e) {
	          existe = false;
	      }

	      resp.setContentType("application/json;charset=UTF-8");
	      resp.getWriter().write(gson.toJson(existe));
	      return;
    	  
      }

      // CASO DEFAULT: respuesta vacía
      resp.getWriter().write(gson.toJson(respuesta));

    }
    catch (Exception e) {
      e.printStackTrace();
      resp.getWriter().write(gson.toJson(respuesta));
    }
  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    processRequestGet(request, response);
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    doGet(request, response);
  }
}
