package servidor.publicar;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;
import java.util.Properties;

import com.sun.net.httpserver.HttpServer;

import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.xml.ws.Endpoint;

@WebService
public class CargarImagenesWebServices {
	
	private Endpoint endpoint = null;
	
    private static Properties config = new Properties();

    static {
        try {
          	String home = System.getProperty("user.home");
        	String configAdress = home + "/eventosUy/.properties";
        	
        	FileInputStream fis = new FileInputStream(configAdress);
        						config.load(fis);
        						fis.close();
        						System.out.println("Adress:");
        						System.out.println(configAdress);
        						System.out.println("\n Archivo properties cargado desde el home correctamente");
        } catch (IOException e) {
            throw new RuntimeException("No se encontró el archivo properties", e);
        }
    }
    String home = System.getProperty("user.home");
    private final String BASE_PATH = home+"/TprogImagenes/";

    private final String DIR_USUARIOS = BASE_PATH + "/usuarios/";
    private final String DIR_INSTITUCIONES = BASE_PATH + "/instituciones/";
    private final String DIR_EVENTOS = BASE_PATH + "/eventos/";
    private final String DIR_EDICIONES = BASE_PATH + "/ediciones/";

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
    
    public CargarImagenesWebServices() {
        new File(DIR_USUARIOS).mkdirs();
        new File(DIR_INSTITUCIONES).mkdirs();
        new File(DIR_EVENTOS).mkdirs();
        new File(DIR_EDICIONES).mkdirs();
        
        copiarImagenesPorDefecto();
    }

    // ----------------- Subida de imágenes -----------------
    @WebMethod
    public String subirImagenUsuarios(String nombreArchivo, byte[] datos) {
        return subirImagen(DIR_USUARIOS, "usuarios", nombreArchivo, datos);
    }

    @WebMethod
    public String subirImagenInstituciones(String nombreArchivo, byte[] datos) {
        return subirImagen(DIR_INSTITUCIONES, "instituciones", nombreArchivo, datos);
    }

    @WebMethod
    public String subirImagenEventos(String nombreArchivo, byte[] datos) {
        return subirImagen(DIR_EVENTOS, "eventos", nombreArchivo, datos);
    }

    @WebMethod
    public String subirImagenEdiciones(String nombreArchivo, byte[] datos) {
        return subirImagen(DIR_EDICIONES, "ediciones", nombreArchivo, datos);
    }

    private String subirImagen(String dir, String carpeta, String nombreArchivo, byte[] datos) {
        try {
            File folder = new File(dir);
            if (!folder.exists()) folder.mkdirs();

            String nombreUnico = System.currentTimeMillis() + "_" + nombreArchivo;

            File file = new File(dir, nombreUnico);
            try (FileOutputStream fos = new FileOutputStream(file)) {
                fos.write(datos);
            }

            return nombreUnico;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // ----------------- Descarga de imágenes como byte[] -----------------
    @WebMethod
    public byte[] getImagenUsuarios(String nombreArchivo) {
        return obtenerImagen(DIR_USUARIOS, nombreArchivo);
    }

    @WebMethod
    public byte[] getImagenInstituciones(String nombreArchivo) {
        return obtenerImagen(DIR_INSTITUCIONES, nombreArchivo);
    }

    @WebMethod
    public byte[] getImagenEventos(String nombreArchivo) {
        return obtenerImagen(DIR_EVENTOS, nombreArchivo);
    }

    @WebMethod
    public byte[] getImagenEdiciones(String nombreArchivo) {
        return obtenerImagen(DIR_EDICIONES, nombreArchivo);
    }
    
    @WebMethod(exclude = true)
    private byte[] obtenerImagen(String dir, String nombreArchivo) {
        try {
            File file = new File(dir, nombreArchivo);
            if (!file.exists()) return null;
            return Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // ----------------- Descarga de imágenes como Base64 -----------------
    @WebMethod
    public String getImagenUsuariosBase64(String nombreArchivo) {
        return obtenerImagenBase64(DIR_USUARIOS, nombreArchivo);
    }

    @WebMethod
    public String getImagenInstitucionesBase64(String nombreArchivo) {
        return obtenerImagenBase64(DIR_INSTITUCIONES, nombreArchivo);
    }

    @WebMethod
    public String getImagenEventosBase64(String nombreArchivo) {
        return obtenerImagenBase64(DIR_EVENTOS, nombreArchivo);
    }

    @WebMethod
    public String getImagenEdicionesBase64(String nombreArchivo) {
        return obtenerImagenBase64(DIR_EDICIONES, nombreArchivo);
    }

    @WebMethod(exclude = true)
    private String obtenerImagenBase64(String dir, String nombreArchivo) {
        File file = new File(dir, nombreArchivo);
        System.out.println("Buscando archivo: " + file.getAbsolutePath() + " | Existe? " + file.exists());
        byte[] datos = obtenerImagen(dir, nombreArchivo);
        if (datos == null) {
            System.out.println("No se encontró la imagen");
            return null;
        }
        return Base64.getEncoder().encodeToString(datos);
    }
    
    @WebMethod(exclude = true)
    private void copiarImagenesPorDefecto() {
        System.out.println("ruta base de base interna de imagenes: "+BASE_PATH);
        String baseInterna = "classes/servidor/imagenesCargaDatos"; 
        //Usuarios
        copiarSiNoExiste(baseInterna + "/IMG-US01.jpg", DIR_USUARIOS);
        copiarSiNoExiste(baseInterna + "/IMG-US03.jpeg", DIR_USUARIOS);
        copiarSiNoExiste(baseInterna + "/IMG-US04.jpeg", DIR_USUARIOS);
        copiarSiNoExiste(baseInterna + "/IMG-US06.png", DIR_USUARIOS);
        copiarSiNoExiste(baseInterna + "/IMG-US07.jpeg", DIR_USUARIOS);
        copiarSiNoExiste(baseInterna + "/IMG-US08.jpeg", DIR_USUARIOS);
        copiarSiNoExiste(baseInterna + "/IMG-US09.jpeg", DIR_USUARIOS);
        copiarSiNoExiste(baseInterna + "/IMG-US11.png", DIR_USUARIOS);
        copiarSiNoExiste(baseInterna + "/IMG-US12.png", DIR_USUARIOS);
        copiarSiNoExiste(baseInterna + "/IMG-US13.jpeg", DIR_USUARIOS);
        copiarSiNoExiste(baseInterna + "/IMG-US14.jpeg", DIR_USUARIOS);
        copiarSiNoExiste(baseInterna + "/IMG-US15.jpeg", DIR_USUARIOS);

        //Eventos
        copiarSiNoExiste(baseInterna + "/IMG-EV02.jpeg", DIR_EVENTOS);
        copiarSiNoExiste(baseInterna + "/IMG-EV03.jpeg", DIR_EVENTOS);
        copiarSiNoExiste(baseInterna + "/IMG-EV04.png", DIR_EVENTOS);
        copiarSiNoExiste(baseInterna + "/IMG-EV05.png", DIR_EVENTOS);
        copiarSiNoExiste(baseInterna + "/IMG-EV06.png", DIR_EVENTOS);
        copiarSiNoExiste(baseInterna + "/IMG-EV08.jpeg", DIR_EVENTOS);

        //Ediciones
        copiarSiNoExiste(baseInterna + "/IMG-EDEV01.jpeg", DIR_EDICIONES);
        copiarSiNoExiste(baseInterna + "/IMG-EDEV02.png", DIR_EDICIONES);
        copiarSiNoExiste(baseInterna + "/IMG-EDEV03.jpeg", DIR_EDICIONES);
        copiarSiNoExiste(baseInterna + "/IMG-EDEV04.jpeg", DIR_EDICIONES);
        copiarSiNoExiste(baseInterna + "/IMG-EDEV05.jpeg", DIR_EDICIONES);
        copiarSiNoExiste(baseInterna + "/IMG-EDEV06.jpeg", DIR_EDICIONES);
        copiarSiNoExiste(baseInterna + "/IMG-EDEV07.jpeg", DIR_EDICIONES);
        copiarSiNoExiste(baseInterna + "/IMG-EDEV08.jpeg", DIR_EDICIONES);
        copiarSiNoExiste(baseInterna + "/IMG-EDEV11.jpeg", DIR_EDICIONES);
        copiarSiNoExiste(baseInterna + "/IMG-EDEV12.jpeg", DIR_EDICIONES);
    }

    @WebMethod(exclude = true)
    private void copiarSiNoExiste(String rutaInterna, String destino) {
        try {
            File origen = new File(rutaInterna);
            if (!origen.exists()) {
                System.err.println("⚠️ No se encontró el archivo: " + origen.getAbsolutePath());
                return;
            }

            File carpetaDestino = new File(destino);
            carpetaDestino.mkdirs();

            File destinoArchivo = new File(carpetaDestino, origen.getName());
            if (!destinoArchivo.exists()) {
                Files.copy(origen.toPath(), destinoArchivo.toPath());
                System.out.println("✅ Copiada imagen por defecto: " + destinoArchivo.getAbsolutePath());
            } else {
                System.out.println("ℹ️ Ya existía la imagen: " + destinoArchivo.getAbsolutePath());
            }
        } catch (Exception e) {
            System.err.println("⚠️ Error copiando imágenes por defecto: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
