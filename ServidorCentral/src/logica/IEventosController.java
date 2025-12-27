package logica;

import excepciones.EventoYaExisteException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * javadoc commet.
 */
public interface IEventosController {

  // --------------- Altas ----------------

  /**
   * javadoc commet.
   */
  public abstract void altaCategoria(String categoria) throws IllegalArgumentException;

  /**
   * javadoc commet.
   */
  public abstract void altaEdicion(String nombreEvento, String nickname, String nombreEdicion,
      String sigla, String ciudad, String pais, LocalDate fechaIni, LocalDate fechaFin,
      LocalDate fechaAlta, String img) throws IllegalArgumentException;

  /**
   * javadoc commet.
   */
  public abstract boolean altaEvento(String nombre, String descripcion, String sigla,
      List<String> categorias, LocalDate fechaAlta, String img) throws EventoYaExisteException;

  /**
   * javadoc commet.
   */
  public abstract void altaTipoDeRegistro(String nombreTipo, String nombreEdicion,
      String descripcion, int costo, int cupo) throws IOException;
  // ----------- Listados -----------------

  /**
   * javadoc commet.
   */
  public abstract List<String> listarEventos();

  /**
   * javadoc commet.
   */
  public abstract List<String> listarCategorias();

  /**
   * javadoc commet.
   */
  public abstract List<String> listarEdicionesEvento(String nombreEvento);

  /**
   * javadoc commet.
   */
  public abstract List<String> listarEdicionesEventoIngresadas(String nombreEvento);

  /**
   * javadoc commet.
   */
  public abstract List<String> listarEdicionesEventoOrganizador(String nombreEvento,
      String organizador);

  /**
   * javadoc commet.
   */
  public abstract List<String> listarTiposDeRegistroDeEdicion(String nombreEdicion);

  /**
   * javadoc commet.
   */
  public abstract DTTipoRegistro consultaTipoDeRegistro(String nombreEdicion,
      String nombreTipoRegistro);

  /**
   * javadoc commet.
   */
  public abstract String obtenerOrganizadorDeEdicion(String nombreEvento, String nombreEdicion);

  /**
   * javadoc commet.
   */
  public abstract String obtenerOrganizadorDeEdicion(String nombreEdicion);

  /**
   * javadoc commet.
   */
  public abstract List<DTRegistro> listarRegistrosDeEdicion(String nombreEvento,
      String nombreEdicion);

  /**
   * javadoc commet.
   */
  public abstract List<String> listarPatrociniosDeEdicion(String nombreEvento,
      String nombreEdicion);

  /**
   * javadoc commet.
   */
  public abstract List<String> listarCodigosDePatrocinioDeEdicion(String nombreEvento,
      String nombreEdicion);

  /**
   * javadoc commet.
   */
  public abstract boolean existeCodigoDePatrocinioEnEdicion(String codigoPatrocinio,
      String nombreEvento, String nombreEdicion);

  /**
   * javadoc commet.
   */
  public abstract void aceptarEdicion(String nombreEdicion);

  /**
   * javadoc commet.
   */
  public abstract void rechazarEdicion(String nombreEdicion);

  /**
   * javadoc commet.
   */
  public abstract void altaPatrocinio(String institucion, String edicion, String tipoRegistro,
      NivelPatrocinio nivel, int monto, int cupo, LocalDate fechaAlta, String codigo)
      throws IOException;
  // ----------- Info -----------------

  /**
   * javadoc commet.
   */
  public abstract DTEvento infoEvento(String nombreEvento);

  /**
   * javadoc commet.
   */
  public abstract DTEdicion infoEdicion(String nombreEdicion);

  /**
   * javadoc commet.
   */
  public abstract DTPatrocinio consultarPatrocinio(String nombreEdicion, String codigoPatrocinio);

  /**
   * javadoc commet.
   */
  public abstract DTPatrocinio consultarPatrocinioPorInstitucion(String nombreEdicion,
      String codigoPatrocinio);

  /**
   * javadoc commet.
   */
  public abstract Map<String, DTPatrocinio> consultarPatrociniosDeEdicion(String nombreEdicion)
      throws FileNotFoundException;

  
  public abstract void finalizarEvento(String nombreEvento);
  
  public abstract void archivarEdicionDeEvento(String nombreEdicion);
  
  public abstract String descargarConstanciaDeAsisitencia(Registro registroDeAsistente);
  
  public abstract int incrementarVisita(String nombreEvento);
  
  
  public abstract void agregarVideo(String nombreEdicion, String video) throws IllegalArgumentException;
  public List<DTEvento> conseguirEventosMasVisitados();
}
