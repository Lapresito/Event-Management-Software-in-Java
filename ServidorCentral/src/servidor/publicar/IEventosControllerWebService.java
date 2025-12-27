package servidor.publicar;

import logica.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sun.net.httpserver.HttpServer;

import excepciones.EventoYaExisteException;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;
import jakarta.jws.soap.SOAPBinding.ParameterStyle;
import jakarta.jws.soap.SOAPBinding.Style;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import jakarta.xml.ws.Endpoint;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@WebService
@SOAPBinding(style = Style.RPC, parameterStyle = ParameterStyle.WRAPPED)
public class IEventosControllerWebService {
    Fabrica fabrica = Fabrica.getInstance();
    IEventosController controladorEventos = fabrica.getIControladorEventos();

    private Endpoint endpoint = null;

    public IEventosControllerWebService() {}

    @WebMethod(exclude = true)
    public void publicar(HttpServer server, String context) {
    	endpoint = Endpoint.create(this);
    	endpoint.publish(server.createContext(context));
    }

    @WebMethod(exclude = true)
    public Endpoint getEndpoint() {
        return endpoint;
    }

    // ---------------- Altas ----------------
    @WebMethod
    public void altaCategoria(String categoria)
    throws IllegalArgumentException {
        controladorEventos.altaCategoria(categoria);
    }
    
    @WebMethod
    public void altaEdicion(String nombreEvento, String nickname, String nombreEdicion, String sigla, String ciudad, String pais, 
    		int yearIni,int monthIni,int dayIni,
    		int yearFin,int monthFin,int dayFin,
    		String img) 
    throws IllegalArgumentException {
    	LocalDate fechaIni = LocalDate.of(yearIni,monthIni,dayIni);
    	LocalDate fechaFin = LocalDate.of(yearFin,monthFin,dayFin);
        controladorEventos.altaEdicion(nombreEvento, nickname, nombreEdicion, sigla, ciudad, pais, fechaIni, fechaFin, LocalDate.now(), img);
    }

    @WebMethod
    public boolean altaEvento(String nombre, String descripcion, String sigla, String[] categorias, String img) 
    throws EventoYaExisteException {
        List<String> catList = new ArrayList<>();
        for(String c : categorias) catList.add(c);
        return controladorEventos.altaEvento(nombre, descripcion, sigla, catList, LocalDate.now(), img);
    }

    @WebMethod
    public void altaTipoDeRegistro(String nombreTipo, String nombreEdicion, String descripcion, int costo, int cupo) 
    throws IOException {
        controladorEventos.altaTipoDeRegistro(nombreTipo, nombreEdicion, descripcion, costo, cupo);
    }

    // ---------------- Listados ----------------
    @WebMethod
    public String[] listarEventos() {
        List<String> lista = controladorEventos.listarEventos();
        return lista.toArray(new String[0]);
    }

    @WebMethod
    public String[] listarCategorias() {
        List<String> lista = controladorEventos.listarCategorias();
        return lista.toArray(new String[0]);
    }

    @WebMethod
    public String[] listarEdicionesEvento(String nombreEvento) {
        List<String> lista = controladorEventos.listarEdicionesEvento(nombreEvento);
        return lista.toArray(new String[0]);
    }

    @WebMethod
    public String[] listarEdicionesEventoIngresadas(String nombreEvento) {
        List<String> lista = controladorEventos.listarEdicionesEventoIngresadas(nombreEvento);
        return lista.toArray(new String[0]);
    }

    @WebMethod
    public String[] listarEdicionesEventoOrganizador(String nombreEvento, String organizador) {
        List<String> lista = controladorEventos.listarEdicionesEventoOrganizador(nombreEvento, organizador);
        return lista.toArray(new String[0]);
    }

    @WebMethod
    public String[] listarTiposDeRegistroDeEdicion(String nombreEdicion) {
        List<String> lista = controladorEventos.listarTiposDeRegistroDeEdicion(nombreEdicion);
        return lista.toArray(new String[0]);
    }

    @WebMethod
    public DtTipoRegistro consultaTipoDeRegistro(String nombreEdicion, String nombreTipoRegistro) {
    	DTTipoRegistro Treg = controladorEventos.consultaTipoDeRegistro(nombreEdicion, nombreTipoRegistro);
        return new DtTipoRegistro(Treg);
    }

    @WebMethod
    public String obtenerOrganizadorDeEdicion(String nombreEdicion) {
        return controladorEventos.obtenerOrganizadorDeEdicion(nombreEdicion);
    }

    @WebMethod
    public DtRegistro[] listarRegistrosDeEdicion(String nombreEvento, String nombreEdicion) {
        List<DTRegistro> lista = controladorEventos.listarRegistrosDeEdicion(nombreEvento, nombreEdicion);
        List<DtRegistro> listaFinal = new ArrayList<>();
        lista.forEach(dt->{
        	listaFinal.add(new DtRegistro(dt));
        });
        return listaFinal.toArray(new DtRegistro[0]);
    }

    @WebMethod
    public String[] listarPatrociniosDeEdicion(String nombreEvento, String nombreEdicion) {
        List<String> lista = controladorEventos.listarPatrociniosDeEdicion(nombreEvento, nombreEdicion);
        return lista.toArray(new String[0]);
    }

    @WebMethod
    public String[] listarCodigosDePatrocinioDeEdicion(String nombreEvento, String nombreEdicion) {
        List<String> lista = controladorEventos.listarCodigosDePatrocinioDeEdicion(nombreEvento, nombreEdicion);
        return lista.toArray(new String[0]);
    }

    @WebMethod
    public boolean existeCodigoDePatrocinioEnEdicion(String codigoPatrocinio, String nombreEvento, String nombreEdicion) {
        return controladorEventos.existeCodigoDePatrocinioEnEdicion(codigoPatrocinio, nombreEvento, nombreEdicion);
    }

    @WebMethod
    public void aceptarEdicion(String nombreEdicion) {
        controladorEventos.aceptarEdicion(nombreEdicion);
    }

    @WebMethod
    public void rechazarEdicion(String nombreEdicion) {
        controladorEventos.rechazarEdicion(nombreEdicion);
    }

    @WebMethod
    public void altaPatrocinio(String institucion, String edicion, String tipoRegistro, NivelPatrocinio nivel, int monto, int cupo, String codigo) 
    throws IOException {
        controladorEventos.altaPatrocinio(institucion, edicion, tipoRegistro, nivel, monto, cupo, LocalDate.now(), codigo);
    }

    // ---------------- Info ----------------
    @WebMethod
    public DtEvento infoEvento(String nombreEvento) {
        return new DtEvento(controladorEventos.infoEvento(nombreEvento));
    }

    @WebMethod
    public DtEdicion infoEdicion(String nombreEdicion) {
    	return new DtEdicion(controladorEventos.infoEdicion(nombreEdicion));
    }

    @WebMethod
    public DtPatrocinio consultarPatrocinio(String nombreEdicion, String codigoPatrocinio) {
        return new DtPatrocinio(controladorEventos.consultarPatrocinio(nombreEdicion, codigoPatrocinio));
    }

    @WebMethod
    public DtPatrocinio consultarPatrocinioPorInstitucion(String nombreEdicion, String codigoPatrocinio) {
        return new DtPatrocinio(controladorEventos.consultarPatrocinioPorInstitucion(nombreEdicion, codigoPatrocinio));
    }

    @WebMethod
    public DtPatrocinio[] consultarPatrociniosDeEdicion(String nombreEdicion) 
    throws FileNotFoundException {
        Map<String,DTPatrocinio> map = controladorEventos.consultarPatrociniosDeEdicion(nombreEdicion);
        DtPatrocinio[] arr = new DtPatrocinio[map.size()];
        int i = 0;
        for(DTPatrocinio p : map.values()){
            arr[i++] = new DtPatrocinio(p);
        }
        return arr;
    }
    
    @WebMethod
    public void finalizarEvento(String nombreEvento) {
    	controladorEventos.finalizarEvento(nombreEvento);
    }
    
    @WebMethod
    public void archivarEdicionDeEvento(String nombreEdicion) {
    	controladorEventos.archivarEdicionDeEvento(nombreEdicion);
    }
    
    @WebMethod
    public String descargarConstanciaDeAsisitencia(Registro registroDeAsistente) {
    	return controladorEventos.descargarConstanciaDeAsisitencia(registroDeAsistente);
    }
    
    @WebMethod
    public int incrementarVisita(String nombreEvento) {
    	return controladorEventos.incrementarVisita(nombreEvento);
    };
    @WebMethod
    public void agregarVideo(String nombreEdicion, String video) throws IllegalArgumentException{
    	controladorEventos.agregarVideo(nombreEdicion, video);
    };

    
    
    
    
}
