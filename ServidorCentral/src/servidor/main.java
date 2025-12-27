package servidor;

import java.awt.EventQueue;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.util.Properties;


import com.sun.net.httpserver.HttpServer;


import jakarta.xml.ws.Endpoint;
import presentacionSwingApp.Principal;
import servidor.publicar.*;

import java.util.Properties;

public class main {

	private static Properties config = new Properties();

	
	public static void main(String[] args) throws IOException {
		try {
        	String home = System.getProperty("user.home");
        	String configAdress = home + "/eventosUy/.properties";
        	
        	FileInputStream fis = new FileInputStream(configAdress);
        						config.load(fis);
        						fis.close();
        						System.out.println("Address:");
        						System.out.println(configAdress);
        						System.out.println("\n Archivo properties cargado desde el home correctamente");
		}catch(IOException e){
			throw new RuntimeException("Error, no se encontro properties en home", e);
		}
		
		try {

        	
			IEventosControllerWebService eventos = new IEventosControllerWebService();
			IInstitucionesControllerWebService instituciones = new IInstitucionesControllerWebService();
			IUsuariosControllerWebService usuarios = new IUsuariosControllerWebService();
			CargarDatosWebService cargaDatos = new CargarDatosWebService();
			CargarImagenesWebServices cargaImagenes = new CargarImagenesWebServices();
			
			Integer puerto = Integer.parseInt(config.getProperty("server.port"));
			String host = config.getProperty("server.host");
			String path = config.getProperty("server.contextPath");
			HttpServer server = HttpServer.create(new InetSocketAddress(puerto), 0);
			
			eventos.publicar(server, config.getProperty("eventos.url"));
			instituciones.publicar(server, config.getProperty("instituciones.url"));
			usuarios.publicar(server, config.getProperty("usuarios.url"));
			cargaDatos.publicar(server, config.getProperty("cargarDatos.url"));
			cargaImagenes.publicar(server, config.getProperty("cargarImagenes.url"));
			
			// 4. Iniciar el servidor HTTP
			server.start();
			System.out.println("Servidor iniciado en: http://"+host+":"+ puerto+path);
			
			Principal.main(args);

		}catch(IOException e) {
			
		}
	}


}