package controllers;

import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import clienteServidor.publicar.*;
import utils.SistemasFactory;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import jakarta.xml.ws.soap.SOAPFaultException;

@WebServlet("/registrar")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024,  // 1MB
    maxFileSize = 1024 * 1024 * 5,    // 5MB
    maxRequestSize = 1024 * 1024 * 10 // 10MB
)
public class register extends HttpServlet {
    private static final long serialVersionUID = 1L;

	private IEventosControllerWebService sistemaEventos = SistemasFactory.getSistemaEventos();
	private CargarImagenesWebServices sistemaImagenes = SistemasFactory.getSistemaCargarImagenes();
	private IUsuariosControllerWebService sistemaUsuarios = SistemasFactory.getSistemaUsuarios();
	private IInstitucionesControllerWebService sistemaInstituciones = SistemasFactory.getSistemaInstituciones();

    public register() {
        super();
    }

    // GET → muestra el formulario JSP
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        DtInstitucion[] instituciones = obtenerInstituciones();
        request.setAttribute("instituciones", instituciones);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/pages/register/register.jsp");
        dispatcher.forward(request, response);
    }

    // POST → procesa el registro
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String tipo = request.getParameter("tipo");
        String nickname = request.getParameter("nickname");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // La variable 'nombre' se asigna según el tipo de usuario
        String nombre = null;

        // --- Manejo de imagen ---
        Part imgPart = request.getPart("imagen");
        String rutaFinal = "";

        if (imgPart != null && imgPart.getSize() > 0) {
            try {
                String nombreImagen = Paths.get(imgPart.getSubmittedFileName()).getFileName().toString();
                byte[] datosImagen = imgPart.getInputStream().readAllBytes();
                rutaFinal = sistemaImagenes.subirImagenUsuarios(nombreImagen, datosImagen);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            if ("organizador".equalsIgnoreCase(tipo)) {

                nombre = request.getParameter("nombreOrg"); // Nombre del Organizador
                String descripcion = request.getParameter("descripcion");
                String sitioWeb = request.getParameter("sitioWeb");

                // Validación básica de campos no nulos (aunque el required del JSP ayuda)
                if (nombre == null || nombre.trim().isEmpty() || descripcion == null || descripcion.trim().isEmpty()) {
                    mostrarError(request, response, "Faltan campos obligatorios del Organizador.");
                    return;
                }

                sistemaUsuarios.altaOrganizador(nickname, email, nombre, descripcion, rutaFinal, password);

                if (sitioWeb != null && !sitioWeb.trim().isEmpty()) {
                    sistemaUsuarios.ingresarSitioWeb(nickname, sitioWeb.trim());
                }

            } else { // tipo = asistente

                nombre = request.getParameter("nombreAsistente"); // Nombre del Asistente
                String apellido = request.getParameter("apellido");
                String fechaNacimiento = request.getParameter("fechaNacimiento");
                String institucion = request.getParameter("institucion");

                // Validación básica de campos no nulos
                 if (nombre == null || nombre.trim().isEmpty() || apellido == null || apellido.trim().isEmpty()) {
                    mostrarError(request, response, "Faltan campos obligatorios del Asistente (Nombre/Apellido).");
                    return;
                }

                LocalDate nacimiento = null;
                if (fechaNacimiento != null && !fechaNacimiento.trim().isEmpty()) {
                    try {
                        nacimiento = LocalDate.parse(fechaNacimiento);
                    } catch (DateTimeParseException ex) {
                        mostrarError(request, response, "Formato de fecha inválido.");
                        return;
                    }
                }

                if (nacimiento == null) {
                    mostrarError(request, response, "Debe ingresar una fecha de nacimiento válida.");
                    return;
                }

                // ✅ Validación antes del WS
                if (!nacimiento.isBefore(LocalDate.now())) {
                    mostrarError(request, response, "La fecha de nacimiento debe ser anterior a la actual.");
                    return;
                }

                sistemaUsuarios.altaAsistente(
                    nickname,
                    email,
                    nombre, // Usa el nombre del asistente
                    apellido,
                    nacimiento.getYear(),
                    nacimiento.getMonthValue(),
                    nacimiento.getDayOfMonth(),
                    rutaFinal,
                    password
                );

                if (institucion != null && !institucion.trim().isEmpty()) {
                    sistemaUsuarios.ingresarInstitucion(nickname, institucion.trim());
                }
            }

            // Mensaje de éxito
            request.getSession().setAttribute("mensaje_registro", "Registro exitoso. ¡Ahora puedes iniciar sesión!");
            request.getSession().setAttribute("tipo_mensaje", "info");
            response.sendRedirect(request.getContextPath() + "/iniciar-sesion");

        } catch (NicknameRepetidoException_Exception | EmailRepetidoException_Exception e) {
            mostrarError(request, response, e.getMessage());
        } catch (SOAPFaultException e) {

            String mensaje = e.getFault() != null && e.getFault().getFaultString() != null
                    ? e.getFault().getFaultString()
                    : "Error en el servidor SOAP.";
            mostrarError(request, response, mensaje);
        } catch (Exception e) {
            e.printStackTrace();
            mostrarError(request, response, "Error al registrar usuario: " + e.getMessage());
        }
    }

    private DtInstitucion[] obtenerInstituciones() {
        try {
            DtInstitucionArray institucionesArr = sistemaInstituciones.getInstituciones();
            if (institucionesArr != null && institucionesArr.getItem() != null) {
                return institucionesArr.getItem().toArray(new DtInstitucion[0]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new DtInstitucion[0];
    }

    private void mostrarError(HttpServletRequest request, HttpServletResponse response, String mensaje)
            throws ServletException, IOException {

        DtInstitucion[] instituciones = obtenerInstituciones();

        request.setAttribute("instituciones", instituciones);
        request.setAttribute("mensaje_registro", mensaje);
        request.setAttribute("tipo_mensaje", "error");

        // Al mostrar el error, necesitamos que los valores previamente ingresados
        // se mantengan en el formulario para el repopulado.
        // El JSP ya se encarga de esto leyendo de request.getParameter().

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/pages/register/register.jsp");
        dispatcher.forward(request, response);
    }
}
