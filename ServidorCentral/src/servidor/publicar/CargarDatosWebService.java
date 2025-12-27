package servidor.publicar;

import logica.CargaDatos;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

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
public class CargarDatosWebService {
	
	private Endpoint endpoint = null;
	
	public CargarDatosWebService() {}

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
    public void cargarDatos() {
    	new CargaDatos().cargar();
    }


}
