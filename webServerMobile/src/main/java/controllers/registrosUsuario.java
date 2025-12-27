package controllers;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import clienteServidor.publicar.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import utils.SistemasFactory;

@WebServlet("/registros")
public class registrosUsuario extends HttpServlet {
    private static final long serialVersionUID = 1L;

	private IEventosControllerWebService sistemaEventos = SistemasFactory.getSistemaEventos();
	private CargarImagenesWebServices sistemaImagenes = SistemasFactory.getSistemaCargarImagenes();
	private IUsuariosControllerWebService sistemaUsuarios = SistemasFactory.getSistemaUsuarios();
	private IInstitucionesControllerWebService sistemaInstituciones = SistemasFactory.getSistemaInstituciones();

    public registrosUsuario() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            HttpSession session = req.getSession(false);
            if (session == null || session.getAttribute("usuario_logueado") == null) {
                resp.sendRedirect(req.getContextPath() + "/iniciar-sesion");
                return;
            }

            DtUsuario usuario = (DtUsuario) session.getAttribute("usuario_logueado");
            String tipo = (String) session.getAttribute("tipo_usuario");

            String nicknameParam = req.getParameter("nickname");
            if (nicknameParam == null || nicknameParam.trim().isEmpty()) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Nickname requerido");
                return;
            }

            if (!nicknameParam.equals(usuario.getNickname())) {
                resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Acceso no permitido");
                return;
            }

            // Nuevo: leer param 'asistidos'
            String asistidosParam = req.getParameter("asistidos");
            boolean mostrarAsistidos = asistidosParam != null && asistidosParam.equalsIgnoreCase("true");

            List<String> listaBruta = sistemaUsuarios.listarRegistrosAsistente(nicknameParam).getItem();
            List<DtRegistro> listaRegistros = new ArrayList<>();

            for (String raw : listaBruta) {
                if (raw == null || raw.isBlank()) continue;

                String[] partes = raw.split(" — ");

                if (partes.length != 2) {
                    System.out.println("Formato inválido de registro: " + raw);
                    continue;
                }

                String nombreEdicion = partes[0].trim();

                try {
                    DtRegistro infoReg = sistemaUsuarios.infoRegistroDeAsistente(nicknameParam, nombreEdicion);

                    // Mantengo lo existente pero sumo la lógica asistidos/no asistidos
                    if (mostrarAsistidos && infoReg.isAsistio()) {
                        listaRegistros.add(infoReg);
                    }

                    if (!mostrarAsistidos && !infoReg.isAsistio()) {
                        listaRegistros.add(infoReg);
                    }

                } catch (Exception ex) {
                    System.out.println("Error obteniendo info de registro para edición: " + nombreEdicion);
                    ex.printStackTrace();
                }
            }

            req.setAttribute("registrosNoAsistidos", listaRegistros);
            req.setAttribute("nickname", nicknameParam);
            req.setAttribute("tipo", tipo);
            req.setAttribute("mostrarAsistidos", mostrarAsistidos); // Nuevo

            req.getRequestDispatcher("/WEB-INF/pages/consultaRegistro/registros.jsp").forward(req, resp);

        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
