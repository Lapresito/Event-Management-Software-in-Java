package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import clienteServidor.publicar.*;
import utils.SistemasFactory;


/**
 * Servlet implementation class Buscar
 */
@WebServlet("/busqueda")
public class Buscar extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
	private IEventosControllerWebService sistemaEventos = SistemasFactory.getSistemaEventos();
	private CargarImagenesWebServices sistemaImagenes = SistemasFactory.getSistemaCargarImagenes();
	private IUsuariosControllerWebService sistemaUsuarios = SistemasFactory.getSistemaUsuarios();
	private IInstitucionesControllerWebService sistemaInstituciones = SistemasFactory.getSistemaInstituciones();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String query = request.getParameter("query");
        if (query == null || query.trim().isEmpty()) {
            // En este caso, traemos todos
            query = ""; // lo dejamos vacío para que no falle ninguna comparación
        }


        query = query.trim();
        
        HttpSession session = request.getSession(false);
        DtUsuario usuario = (DtUsuario) session.getAttribute("usuario_logueado");

        List<DtEvento> resultadosEventos = new ArrayList<>();
        List<DtEdicion> resultadosEdiciones = new ArrayList<>();

        // Traemos todos los nombres de eventos
        List<String> nombresEventos = sistemaEventos.listarEventos().getItem();

        for (String nombreEvento : nombresEventos) {
        	DtEvento evento = sistemaEventos.infoEvento(nombreEvento);

            // Si el nombre del evento coincide con la búsqueda, lo agregamos
            if (contieneIgnorandoTildes(nombreEvento, query)||contieneIgnorandoTildes(evento.getDescripcion(), query)|| query.isEmpty()) {
                try {
                    if(!evento.isFinalizado()) {
                    	resultadosEventos.add(evento);                    	
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            // Traemos las ediciones del evento actual
            List<String> nombresEdiciones = sistemaEventos.listarEdicionesEvento(nombreEvento).getItem();

            for (String nombreEdicion : nombresEdiciones) {
            	DtEdicion ed = sistemaEventos.infoEdicion(nombreEdicion);
                if (contieneIgnorandoTildes(nombreEdicion, query) || query.isEmpty()) {
                    try {
                        EstadoEdicion estado = EstadoEdicion.valueOf(ed.getEstado().toUpperCase());
                        if(estado ==  EstadoEdicion.ACEPTADA) {
                        	resultadosEdiciones.add(ed);                        	
                        } else {
                        	if(usuario!= null && usuario.getNickname().equals(ed.getOrganizador())) {
                        		resultadosEdiciones.add(ed);
                        	}
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        // Enviamos los resultados al JSP
        request.setAttribute("resultadosEventos", resultadosEventos);
        request.setAttribute("resultadosEdiciones", resultadosEdiciones);
        request.setAttribute("query", query);

        request.getRequestDispatcher("/WEB-INF/pages/busqueda/busqueda.jsp").forward(request, response);
    }

    // --- Método auxiliar para comparar strings ignorando mayúsculas y tildes ---
    private boolean contieneIgnorandoTildes(String texto, String subtexto) {
        if (texto == null || subtexto == null) return false;
        String normalizadoTexto = normalizar(texto);
        String normalizadoSubtexto = normalizar(subtexto);
        return normalizadoTexto.contains(normalizadoSubtexto);
    }

    private String normalizar(String s) {
        // Elimina tildes y pasa a minúsculas
        return java.text.Normalizer.normalize(s, java.text.Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .toLowerCase();
    }
}
