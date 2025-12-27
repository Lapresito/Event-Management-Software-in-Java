package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import jakarta.xml.ws.BindingProvider;
import clienteServidor.publicar.*;


public class SistemasFactory {
	
	private static Properties config = new Properties();
	
	public static void main(String[] args) {
		// No se requiere modificar el main
	}
	
	private static void cargarProperties() {
		try {
        	String home = System.getProperty("user.home");
        	String configAdress = home + "/eventosUy/.properties";
        	
        	// Reemplazamos la carga repetida por una función auxiliar
        	FileInputStream fis = new FileInputStream(configAdress);
        	config.load(fis);
        	fis.close();
        	System.out.println("Address:");
        	System.out.println(configAdress);
        	System.out.println("\n Archivo properties cargado desde el home correctamente");
		}catch(IOException e){
			throw new RuntimeException("Error, no se encontro properties en home", e);
		}
	}
	
	public static IEventosControllerWebService getSistemaEventos() {
		cargarProperties(); // Cargar la configuración
		
		Integer puerto = Integer.parseInt(config.getProperty("server.port"));
		String host = config.getProperty("server.host");
		String pathEventos = config.getProperty("eventos.url");
		
		// 1. URL del WSDL para la inicialización (CON ?wsdl)
		String wsdlURLString = "http://"+host+":"+puerto+pathEventos+"?wsdl";
		URL wsdlURL;
		
		try {
		    wsdlURL = new URL(wsdlURLString);
		} catch (java.net.MalformedURLException e) {
		    throw new RuntimeException("URL del WSDL mal formada: " + wsdlURLString, e);
		}

		// *** CORRECCIÓN CRÍTICA: Instanciar con la URL dinámica ***
		IEventosControllerWebServiceService serviceEventos = new IEventosControllerWebServiceService(wsdlURL);
		IEventosControllerWebService sistemaEventos = serviceEventos.getIEventosControllerWebServicePort();
		
		// 2. URL del Endpoint para las llamadas (SIN ?wsdl)
		String endpointURL = "http://"+host+":"+puerto+pathEventos;
		
		BindingProvider bp = (BindingProvider) sistemaEventos;
		
	    bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpointURL);
		
	    return sistemaEventos;
	}
	
	public static IUsuariosControllerWebService getSistemaUsuarios() {
		cargarProperties();
		
		Integer puerto = Integer.parseInt(config.getProperty("server.port"));
		String host = config.getProperty("server.host");
		String pathUsuarios = config.getProperty("usuarios.url");
		
		String wsdlURLString = "http://"+host+":"+puerto+pathUsuarios+"?wsdl";
		URL wsdlURL;
		
		try {
		    wsdlURL = new URL(wsdlURLString);
		} catch (java.net.MalformedURLException e) {
		    throw new RuntimeException("URL del WSDL mal formada: " + wsdlURLString, e);
		}
		
		IUsuariosControllerWebServiceService serviceUsuarios = new IUsuariosControllerWebServiceService(wsdlURL);
		IUsuariosControllerWebService sistemaUsuarios = serviceUsuarios.getIUsuariosControllerWebServicePort();
		
		String endpointURL = "http://"+host+":"+puerto+pathUsuarios;
		
		BindingProvider bp = (BindingProvider) sistemaUsuarios;
		
	    bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpointURL);
		
	    return sistemaUsuarios;
	}
	
	public static IInstitucionesControllerWebService getSistemaInstituciones() {
		cargarProperties();
		
		Integer puerto = Integer.parseInt(config.getProperty("server.port"));
		String host = config.getProperty("server.host");
		String pathInstituciones = config.getProperty("instituciones.url");
		
		String wsdlURLString = "http://"+host+":"+puerto+pathInstituciones+"?wsdl";
		URL wsdlURL;
		
		try {
		    wsdlURL = new URL(wsdlURLString);
		} catch (java.net.MalformedURLException e) {
		    throw new RuntimeException("URL del WSDL mal formada: " + wsdlURLString, e);
		}
		
		IInstitucionesControllerWebServiceService serviceInstituciones = new IInstitucionesControllerWebServiceService(wsdlURL);
		IInstitucionesControllerWebService sistemaInstituciones = serviceInstituciones.getIInstitucionesControllerWebServicePort();
		
		String endpointURL = "http://"+host+":"+puerto+pathInstituciones;
		
		BindingProvider bp = (BindingProvider) sistemaInstituciones;
		
	    bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpointURL);
		
	    return sistemaInstituciones;
	}
	
	public static CargarDatosWebService getSistemaCargarDatos() {
		cargarProperties();
		
		Integer puerto = Integer.parseInt(config.getProperty("server.port"));
		String host = config.getProperty("server.host");
		String pathCargarDatos = config.getProperty("cargarDatos.url");
		
		String wsdlURLString = "http://"+host+":"+puerto+pathCargarDatos+"?wsdl";
		URL wsdlURL;
		
		try {
		    wsdlURL = new URL(wsdlURLString);
		} catch (java.net.MalformedURLException e) {
		    throw new RuntimeException("URL del WSDL mal formada: " + wsdlURLString, e);
		}
		
		CargarDatosWebServiceService serviceCargarDatos = new CargarDatosWebServiceService(wsdlURL);
		CargarDatosWebService sistemaCargarDatos = serviceCargarDatos.getCargarDatosWebServicePort();
		
		String endpointURL = "http://"+host+":"+puerto+pathCargarDatos;
		
		BindingProvider bp = (BindingProvider) sistemaCargarDatos;
		
	    bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpointURL);
		
	    return sistemaCargarDatos;
	}
	
	public static CargarImagenesWebServices getSistemaCargarImagenes() {
		cargarProperties();
		
		Integer puerto = Integer.parseInt(config.getProperty("server.port"));
		String host = config.getProperty("server.host");
		String pathCargarImagenes = config.getProperty("cargarImagenes.url");
		
		String wsdlURLString = "http://"+host+":"+puerto+pathCargarImagenes+"?wsdl";
		URL wsdlURL;
		
		try {
		    wsdlURL = new URL(wsdlURLString);
		} catch (java.net.MalformedURLException e) {
		    throw new RuntimeException("URL del WSDL mal formada: " + wsdlURLString, e);
		}
		
		CargarImagenesWebServicesService serviceImagenes = new CargarImagenesWebServicesService(wsdlURL);
		CargarImagenesWebServices sistemaImagenes = serviceImagenes.getCargarImagenesWebServicesPort();
		
		String endpointURL = "http://"+host+":"+puerto+pathCargarImagenes;
		
		BindingProvider bp = (BindingProvider) sistemaImagenes;
		
	    bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpointURL);
		
	    return sistemaImagenes;
	}
}