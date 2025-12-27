package servidor.publicar;

import logica.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.sun.net.httpserver.HttpServer;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;
import jakarta.jws.soap.SOAPBinding.ParameterStyle;
import jakarta.jws.soap.SOAPBinding.Style;
import jakarta.xml.ws.Endpoint;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;



@WebService
@SOAPBinding(style = Style.RPC, parameterStyle = ParameterStyle.WRAPPED)
public class IInstitucionesControllerWebService {
	Fabrica fabrica = Fabrica.getInstance();
	IInstitucionesController controladorEventos = fabrica.getIInstitucionesController();
	
	private Endpoint endpoint = null;
	
	public IInstitucionesControllerWebService() {}

	//Operaciones las cuales quiero publicar
	
	@WebMethod(exclude = true)
	public void publicar(HttpServer server, String context){
    	endpoint = Endpoint.create(this);
    	endpoint.publish(server.createContext(context));
	}
	
	@WebMethod(exclude = true)
	public Endpoint getEndpoint() {
		return endpoint;
	}
	
	 // ---------------- Métodos WebService ----------------

    @WebMethod
    public DtInstitucion[] getInstituciones() {
    	List<DtInstitucion> instituciones = new ArrayList<>();
    	DTInstitucion[] instLogica = controladorEventos.getInstituciones();
    	for(DTInstitucion i : instLogica) {
    		instituciones.add(new DtInstitucion(i));
    	}
    	return instituciones.toArray(new DtInstitucion[0]);
    	
    }

    @WebMethod
    public void altaInstitucion(String nombreInstitucion, String descripcion, String sitioWeb, String img)
    throws IOException {
    	controladorEventos.altaInstitucion(nombreInstitucion, descripcion, sitioWeb, img);
    }
}
