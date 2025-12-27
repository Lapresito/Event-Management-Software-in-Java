package controllers;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;


import clienteServidor.publicar.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.SistemasFactory;


//se utiliza Locale.ROOT para que las opereaciones no dependan del idioma
@WebServlet("/eventos")
public class Eventos extends HttpServlet {
  private static final long serialVersionUID = 1L;
  
	private IEventosControllerWebService sistemaEventos = SistemasFactory.getSistemaEventos();
	private CargarImagenesWebServices sistemaImagenes = SistemasFactory.getSistemaCargarImagenes();
	private IUsuariosControllerWebService sistemaUsuarios = SistemasFactory.getSistemaUsuarios();
	private IInstitucionesControllerWebService sistemaInstituciones = SistemasFactory.getSistemaInstituciones();

	private void renderLista(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {

			// Soporto q= (viene desde la navbar)
			String query = req.getParameter("query");
			String cat = req.getParameter("categoria");
			cat = (cat != null && !cat.trim().isEmpty()) ? cat.trim() : null;

			// Traigo nombres y filtro por q (si viene)
			List<String> nombres = new ArrayList<>();
			try {
				StringArray response = sistemaEventos.listarEventos();

				if (response != null && response.getItem() != null) {
					nombres.addAll(response.getItem());
					nombres.sort(Comparator.naturalOrder());

				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}

			if (query != null && !query.trim().isEmpty()) {
				String queryquery = query.toLowerCase(Locale.ROOT);
				nombres.removeIf(n -> n == null || !n.toLowerCase(Locale.ROOT).contains(queryquery));
			}

			List<DtEvento> dtoList = new ArrayList<>();

			if (cat != null) {
				for (String n : nombres) {
					DtEvento evento = sistemaEventos.infoEvento(n);
					if (evento != null && evento.getCategorias().contains(cat)) {
						  // Guardar el objeto en el request
					      String base64 = sistemaImagenes.getImagenEventosBase64(evento.getImg());
					      if (base64 == null || base64.isEmpty()) {
					    	  evento.setImg("media/imagenes/placeholderEvento-Edicion.png"); 
					      }else {
					    	  String dataUrl = "data:image/png;base64," + base64;
					    	  evento.setImg(dataUrl);    	  
					      }
					      dtoList.add(evento);
					}
				}
			} else {
				for (String n : nombres) {
					DtEvento evento = sistemaEventos.infoEvento(n);
					if (evento != null) {
						
					  // Guardar el objeto en el request
				      String base64 = sistemaImagenes.getImagenEventosBase64(evento.getImg());
				      if (base64 == null || base64.isEmpty()) {
				    	  evento.setImg("media/imagenes/placeholderEvento-Edicion.png"); 
				      }else {
				    	  String dataUrl = "data:image/png;base64," + base64;
				    	  evento.setImg(dataUrl);    	  
				      }
				      dtoList.add(evento);
					}
				}
			}

			dtoList.forEach(e -> System.out.println(e.getNombre()));

			req.setAttribute("q", query);
			req.setAttribute("eventosDTO", dtoList);
			req.setAttribute("cat", cat == null ? "" : cat);
			req.getRequestDispatcher("/WEB-INF/pages/eventos/eventos.jsp").forward(req, resp);

		} catch (Exception e) {
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400 u otro código según corresponda
		}
	}

	private void renderDetalle(HttpServletRequest req, HttpServletResponse resp, String nombre)
			throws ServletException, IOException {
		DtEvento evento = sistemaEventos.infoEvento(nombre);

		if (evento == null) {
			req.setAttribute("error", "El evento no existe o no se encontró");
			resp.sendError(HttpServletResponse.SC_NOT_FOUND, "No existe el evento: " + nombre);
			return;
		}

		List<String> ediciones = evento.getEdiciones();
		List<DtEdicion> edicionesAceptadas = new ArrayList<>();
		List<DtEdicion> edicionesIngresadas = new ArrayList<>();
		List<DtEdicion> edicionesRechazadas = new ArrayList<>();

		  // Guardar el objeto en el request
	      String base64 = sistemaImagenes.getImagenEventosBase64(evento.getImg());
	      if (base64 == null || base64.isEmpty()) {
	    	  evento.setImg("media/imagenes/placeholderEvento-Edicion.png"); 
	      }else {
	    	  String dataUrl = "data:image/png;base64," + base64;
	    	  evento.setImg(dataUrl);    	  
	      }
		
		ediciones.forEach(ed -> {
			DtEdicion edicion = sistemaEventos.infoEdicion(ed);
		    EstadoEdicion estado = EstadoEdicion.valueOf(edicion.getEstado().toUpperCase());

			if (estado == EstadoEdicion.ACEPTADA)
				edicionesAceptadas.add(edicion);
			if (estado == EstadoEdicion.INGRESADA)
				edicionesIngresadas.add(edicion);
			if (estado == EstadoEdicion.RECHAZADA)
				edicionesRechazadas.add(edicion);

		});

		req.setAttribute("evento", evento);
		req.setAttribute("edicionesAceptadas", edicionesAceptadas);
		req.setAttribute("edicionesIngresadas", edicionesIngresadas);
		req.setAttribute("edicionesRechazadas", edicionesRechazadas);
		sistemaEventos.incrementarVisita(evento.getNombre());
		req.getRequestDispatcher("/WEB-INF/pages/eventos/consultaEvento.jsp").forward(req, resp);
	}

	private void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String nombre = req.getParameter("nombre");
		if (nombre != null && !nombre.trim().isEmpty()) {
			renderDetalle(req, resp, nombre);
		} else {
			renderLista(req, resp);
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	  // Verificar si el usuario está logueado
    Object usuario = req.getSession().getAttribute("usuario_logueado");

    if (usuario == null) {
        // 403 Forbidden
        resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
        resp.sendRedirect(req.getContextPath() + "/iniciar-sesion");
        return;
    }

		process(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

	// Util por si lo necesitás desde JSP
	public static String linkDetalle(String ctx, String nombre) {
		return ctx + "/eventos?nombre=" + URLEncoder.encode(nombre, StandardCharsets.UTF_8);
	}
}
