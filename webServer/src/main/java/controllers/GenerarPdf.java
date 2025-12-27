package controllers;

import java.io.IOException;
import java.io.OutputStream;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

import clienteServidor.publicar.*;
import utils.SistemasFactory;

@WebServlet("/generarPdf")
public class GenerarPdf extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
	private IEventosControllerWebService sistemaEventos = SistemasFactory.getSistemaEventos();
	private CargarImagenesWebServices sistemaImagenes = SistemasFactory.getSistemaCargarImagenes();
	private IUsuariosControllerWebService sistemaUsuarios = SistemasFactory.getSistemaUsuarios();
	private IInstitucionesControllerWebService sistemaInstituciones = SistemasFactory.getSistemaInstituciones();
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String nombreEdicion = req.getParameter("edicion");
        String nickname = req.getParameter("nickname");
        DtEdicion edicion1 = sistemaEventos.infoEdicion(nombreEdicion);
        // Obtener imagen (puede venir como Base64 en byte[] según asumimos)
        String base64edicion = sistemaImagenes.getImagenEdicionesBase64(edicion1.getImagen());

        // Datos del registro y asistente
        DtRegistro registro = null;
        DtAsistente asistente = null;
        try {
            registro = sistemaUsuarios.infoRegistroDeAsistente(nickname, nombreEdicion);
            asistente = sistemaUsuarios.getAsistente(nickname);
        } catch (Exception e) {
            // si el WS lanza SOAPFault o similar, respondemos 500 con mensaje claro
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al obtener datos del servicio: " + e.getMessage());
            return;
        }

        if (registro == null || asistente == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Registro o asistente no encontrado");
            return;
        }

        // Datos para el certificado
        String nombreCompleto = asistente.getNombre() + " " + asistente.getApellido();
        String evento = registro.getNombreEvento();
        String edicion = nombreEdicion;
        String fecha = registro.getFechaAlta().toXMLFormat().split("T")[0];

        resp.setContentType("application/pdf");
        resp.setHeader("Content-Disposition", "attachment; filename=Constancia-Asistencia.pdf");

        // Documento A4 con márgenes cómodos
        Document document = new Document(PageSize.A4, 50, 50, 60, 50);
        OutputStream out = resp.getOutputStream();

        try {
            PdfWriter writer = PdfWriter.getInstance(document, out);
            document.open();

            // =================== BORDE PUNTEADO (pro) ===================
            PdfContentByte canvas = writer.getDirectContent();
            canvas.saveState();
            canvas.setLineWidth(1f);
            canvas.setLineDash(4f, 4f); // punteado
            float margin = 36f;
            float left = margin;
            float bottom = margin;
            float width = document.getPageSize().getWidth() - 2 * margin;
            float height = document.getPageSize().getHeight() - 2 * margin;
            canvas.rectangle(left, bottom, width, height);
            canvas.stroke();
            canvas.restoreState();
            // ==========================================================

            // Fuentes estándar iText (Times)
            Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 26, Font.BOLD);
            Font italicFont = new Font(Font.FontFamily.TIMES_ROMAN, 13, Font.ITALIC);
            Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 13, Font.BOLD);
            Font smallItalic = new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.ITALIC);

            // ====== TÍTULO ======
            Paragraph titulo = new Paragraph("CERTIFICADO DE ASISTENCIA\n\n", titleFont);
            titulo.setAlignment(Element.ALIGN_CENTER);
            titulo.setSpacingAfter(10f);
            document.add(titulo);

         // ====== IMAGEN DE LA EDICIÓN (Base64 STRING) ======
            boolean imagenCargada = false;

            if (base64edicion != null && !base64edicion.isEmpty()) {
                try {
                    // Decodificamos el String Base64 a bytes
                    byte[] decoded = java.util.Base64.getDecoder().decode(base64edicion);

                    // Crear imagen desde los bytes
                    Image img = Image.getInstance(decoded);

                    img.scaleToFit(180, 120);
                    img.setAlignment(Image.ALIGN_CENTER);
                    img.setSpacingAfter(12f);

                    document.add(img);
                    imagenCargada = true;
                } catch (Exception e) {
                    imagenCargada = false;
                }
            }

            if (!imagenCargada) {
                document.add(new Paragraph("\n La edición no tiene imagen.\n", italicFont));
            }



            // ====== TEXTO "verso" (enunciado) con los datos en BOLD dentro del mismo párrafo ======
            Paragraph textoVerso = new Paragraph();
            textoVerso.setAlignment(Element.ALIGN_CENTER);
            textoVerso.setLeading(18f); // interlineado para que quede ligero
            textoVerso.setSpacingBefore(10f);
            textoVerso.setSpacingAfter(20f);

            // Construcción del verso: combinar chunks italic + bold para datos
            Chunk c1 = new Chunk("Se deja constancia de que ", italicFont);
            Chunk cNombre = new Chunk(nombreCompleto, boldFont);
            Chunk c2 = new Chunk(" participó y asistió a la edición ", italicFont);
            Chunk cEd = new Chunk("\"" + edicion + "\"", boldFont);
            Chunk c3 = new Chunk(" del evento ", italicFont);
            Chunk cEv = new Chunk("\"" + evento + "\"", boldFont);
            Chunk c4 = new Chunk(". La inscripción fue registrada el día ", italicFont);
            Chunk cFecha = new Chunk(fecha + ".", boldFont);

            textoVerso.add(c1);
            textoVerso.add(cNombre);
            textoVerso.add(c2);
            textoVerso.add(cEd);
            textoVerso.add(c3);
            textoVerso.add(cEv);
            textoVerso.add(c4);
            textoVerso.add(cFecha);

            document.add(textoVerso);

            // Un pequeño espacio antes del pie
            document.add(new Paragraph("\n\n"));

            // ====== CIERRE ======
            Paragraph pie = new Paragraph("Eventos.uy – Taller de Programación – Grupo 10", smallItalic);
            pie.setAlignment(Element.ALIGN_CENTER);
            pie.setSpacingBefore(10f);
            document.add(pie);

            document.close();
        } catch (DocumentException e) {
            throw new IOException(e.getMessage(), e);
        }
    }
}
