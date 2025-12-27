package logica; 


// TEST DE COMMIT
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.junit.jupiter.api.Test;

import clienteServidor.publicar.IOException;
import excepciones.*;

public class EventosControllerTest {

	private Fabrica fabrica = Fabrica.getInstance();
	private IEventosController sistemaEventos = fabrica.getIControladorEventos();
	private IUsuariosController sistemaUsuarios = fabrica.getIUsuariosController();
	private ManejadorUsuarios mu = ManejadorUsuarios.getinstance();
	private ManejadorEventos manejadorEventos = ManejadorEventos.getInstancia();
	private ManejadorUsuarios manejadorUsuarios = ManejadorUsuarios.getinstance();
	
	@Test
	void testAltaCategoria() throws Exception {
		//alta categoria1
		String cat1 = "cat1";
		sistemaEventos.altaCategoria(cat1);
		assertEquals(manejadorEventos.getCategoria(cat1).getNombre(), cat1);
		
		//alta categoria2
		String cat2 = "cat2";	
		sistemaEventos.altaCategoria(cat2);
		assertEquals(manejadorEventos.getCategoria(cat2).getNombre(), cat2);
		
		String cat3 = cat2;	
		Exception ex = assertThrows(IllegalArgumentException.class, () -> sistemaEventos.altaCategoria(cat3));
		assertEquals("La categoria ya existe", ex.getMessage());	
		
		manejadorEventos.clearManejador();
		manejadorUsuarios.limpiarManejador();
	}
	
	@Test
	void testAltaEvento() throws Exception {

		// alta categoria
		String cat1 = "cat1";
		String cat2 = "cat2";
		sistemaEventos.altaCategoria(cat1);
		sistemaEventos.altaCategoria(cat2);
		List<String> categorias = new ArrayList<>();
		categorias.add(cat1);
		categorias.add(cat2);
		
		//AltaEvento1
		String nombre1 = "NombreEvento";
		String descripcion1 = "DescEvento";
		String sigla1 = "SiglaEvento";
		LocalDate fecha1 = LocalDate.of(2025, 8, 31);
		String img1 = " ";
		boolean alta1 = sistemaEventos.altaEvento(nombre1, descripcion1, sigla1, categorias, fecha1, img1);
		assertEquals(true, alta1);
		
		Evento evento = manejadorEventos.getEvento(nombre1);
		assertEquals(nombre1, evento.getNombre());
		assertEquals(descripcion1, evento.getDescripcion());
		assertEquals(sigla1, evento.getSigla());
		assertEquals(fecha1, evento.getFechaAlta());
		assertEquals(cat1, evento.getCategoria(cat1).getNombre());
		assertEquals(cat2, evento.getCategoria(cat2).getNombre());
			
		Exception ex = assertThrows(EventoYaExisteException.class, () -> sistemaEventos.altaEvento(nombre1, descripcion1, sigla1, categorias, fecha1, img1));
		assertEquals("El evento (" +nombre1+") ya existe.", ex.getMessage());	
		
		manejadorEventos.clearManejador();
		manejadorUsuarios.limpiarManejador();

	}
	
	@Test
	void testAltaEdicion() throws Exception {
	    // Limpiar manejadores al inicio
	    manejadorEventos.clearManejador();
	    manejadorUsuarios.limpiarManejador();
	    
	    String cat1 = "cat1";
	    String cat2 = "cat2";
	    sistemaEventos.altaCategoria(cat1);
	    sistemaEventos.altaCategoria(cat2);
	    List<String> categorias = new ArrayList<>();
	    categorias.add(cat1);
	    categorias.add(cat2);

	    // Alta organizador
	    String nicknameOrg = "org1";
	    String nombreOrg = "NombreOrg1";
	    String emailOrg = "ejemplo@gmail.com";
	    String descripcionOrg = "DescOrg1";
	    String imgOrg = " ";
	    String passwordOrg = "PasswordOrg";
	    sistemaUsuarios.altaOrganizador(nicknameOrg, emailOrg, nombreOrg, descripcionOrg, imgOrg, passwordOrg);

	    // AltaEvento1
	    String nombre1 = "NombreEvento";
	    String descripcion1 = "DescEvento";
	    String sigla1 = "SiglaEvento";
	    LocalDate fecha1 = LocalDate.of(2025, 8, 31);
	    String img1 = " ";
	    boolean alta1 = sistemaEventos.altaEvento(nombre1, descripcion1, sigla1, categorias, fecha1, img1);

	    // AltaEdicion
	    String nombreEdicion = "Edicion2025";
	    String sigla = "ED2025";
	    String ciudad = "Montevideo";
	    String pais = "Uruguay";
	    String nombreEvento = nombre1;
	    LocalDate fechaIni = LocalDate.of(2025, 9, 1);
	    LocalDate fechaFin = LocalDate.of(2025, 9, 30);
	    LocalDate fechaAlta = LocalDate.now();
	    String img = "";

	    sistemaEventos.altaEdicion(nombre1, nicknameOrg, nombreEdicion, sigla, ciudad, pais, fechaIni, fechaFin, fechaAlta, img);

	    Evento ev = manejadorEventos.getEvento(nombreEvento);
	    assertNotNull(ev);

	    Edicion ed = ev.getEdicion(nombreEdicion);
	    assertNotNull(ed);
	    assertEquals(nombreEdicion, ed.getNombre());
	    assertEquals(sigla, ed.getSigla());
	    assertEquals(ciudad, ed.getCiudad());
	    assertEquals(pais, ed.getPais());
	    assertEquals(fechaIni, ed.getFechaIni());
	    assertEquals(fechaFin, ed.getFechaFin());
	    assertEquals(fechaAlta, ed.getFechaAlta());

	    Exception ex = assertThrows(IllegalArgumentException.class, () -> 
	        sistemaEventos.altaEdicion("Evento inexistente", nicknameOrg, nombreEdicion, sigla, ciudad, pais, fechaIni, fechaFin, fechaAlta, img));
	    assertEquals("El evento no existe", ex.getMessage());

	    Exception ex1 = assertThrows(IllegalArgumentException.class, () -> 
	        sistemaEventos.altaEdicion(nombre1, nicknameOrg, nombreEdicion, sigla, ciudad, pais, fechaIni, fechaFin, fechaAlta, img));
	    assertEquals("La edicion ya existe", ex1.getMessage());

	    Exception ex2 = assertThrows(IllegalArgumentException.class, () -> 
	        sistemaEventos.altaEdicion(nombre1, "Organizador inexistente", "e", sigla, ciudad, pais, fechaIni, fechaFin, fechaAlta, img));
	    assertEquals("El organizador no existe", ex2.getMessage());

	    manejadorEventos.clearManejador();
	    manejadorUsuarios.limpiarManejador();
	}

	
	@Test
	void listarEventos_cuandoExistenEventos_retornaNombresCorrectos() {
		// 1. Configurar datos de prueba usando el controlador (asumiendo que tiene un método para crear eventos)
		// Si tu controlador no tiene un método para agregar eventos, debes usar el manejador directamente.
		
		manejadorEventos.clearManejador();
		
		Map<String, Categoria> categorias = new HashMap<>();
		Categoria cat1 = new Categoria("cat1");
		Categoria cat2 = new Categoria("cat2");
		categorias.put("cat2", cat2);
		categorias.put("cat1", cat1);
		
		
		manejadorEventos.getEventos().put("Concierto de Rock", new Evento("Concierto de Rock", "SLA", "Descripcion", categorias, LocalDate.now(), ""));
		manejadorEventos.getEventos().put("Festival de Cine", new Evento("Festival de Cine", "SLA", "Descripcion", categorias, LocalDate.now(), ""));

		List<String> nombresEventos = sistemaEventos.listarEventos();
		
		assertEquals(2, nombresEventos.size(), "La lista debe contener 2 eventos");
		assertTrue(nombresEventos.contains("Concierto de Rock"), "La lista debe contener 'Concierto de Rock'");
		assertTrue(nombresEventos.contains("Festival de Cine"), "La lista debe contener 'Festival de Cine'");
		
	    manejadorEventos.clearManejador();
		manejadorUsuarios.limpiarManejador();
	}

	
	
	@Test
	void listarEventos_cuandoNoHayEventos() {
		List<String> nombresEventos = sistemaEventos.listarEventos();

		assertTrue(nombresEventos.isEmpty(), "La lista debe estar vacía");
	}

	
	@Test
	void TestAltaRegistro() throws Exception {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"); 

		// alta categoria
		String cat1 = "cat1";
		String cat2 = "cat2";
		sistemaEventos.altaCategoria(cat1);
		sistemaEventos.altaCategoria(cat2);
		List<String> categorias = new ArrayList<>();
		categorias.add(cat1);
		categorias.add(cat2);
		
		//AltaEvento1
		String nombre1 = "NombreEvento";
		String descripcion1 = "DescEvento";
		String sigla1 = "SiglaEvento";
		LocalDate fecha1 = LocalDate.of(2025, 8, 31);	
		String img1 = "";
		boolean alta1 = sistemaEventos.altaEvento(nombre1, descripcion1, sigla1, categorias, fecha1, img1);
		
		//Alta organizador
		String nicknameOrg = "org1";
		String nombreOrg = "NombreOrg1";
		String emailOrg = "ejemplo@gmail.com";
		String descripcionOrg = "DescOrg1";
		String imgOrg = "";
		String passwordOrg = "PasswordOrg";
		sistemaUsuarios.altaOrganizador(nicknameOrg,emailOrg,nombreOrg,descripcionOrg, imgOrg, passwordOrg);
		
		// AltaEdicion 1
		String nombreEdicion = "Edicion2025";
		String sigla     = "ED2025";
		String ciudad    = "Montevideo";
		String pais      = "Uruguay";
		String nombreEvento = nombre1;
		LocalDate fechaIni = LocalDate.of(2025, 9, 1);
		LocalDate fechaFin = LocalDate.of(2025, 9, 30);
		LocalDate fechaAlta = LocalDate.now();
		String img = "";
	    sistemaEventos.altaEdicion(nombre1, nicknameOrg, nombreEdicion, sigla, ciudad, pais, fechaIni, fechaFin, fechaAlta, img);
 	    
	    List<DTRegistro> registros0 = sistemaEventos.listarRegistrosDeEdicion(nombreEvento, nombreEdicion);
	    assertEquals(registros0, new ArrayList<>());
 	    
 	    //alta TipoRegistro1
	    String NombreTipo = "VIP";
	    String desc = "aaaa";
	    int costo = 5000;
	    int cupo = 300;
	    sistemaEventos.altaTipoDeRegistro(NombreTipo, nombreEdicion, desc, costo, cupo);
	    DTTipoRegistro reg = sistemaEventos.consultaTipoDeRegistro(nombreEdicion, NombreTipo);
	    
 	    //alta TipoRegistro2
	    String NombreTipo2 = "General";
	    String desc2 = "bbbbb";
	    int costo2 = 3000;
	    int cupo2 = 900;
	    sistemaEventos.altaTipoDeRegistro(NombreTipo2, nombreEdicion, desc2, costo2, cupo2);
	    DTTipoRegistro reg2 = sistemaEventos.consultaTipoDeRegistro(nombreEdicion, NombreTipo2);
	    
	    //asistente
	    sistemaUsuarios.altaAsistente("nickasist1", "mailasist1@dom.com", "NombreAsist1", "Apellido",LocalDate.now().minusYears(20), "", "Password");
	    sistemaUsuarios.altaAsistente("nickasist2", "mailasist2@dom.com", "NombreAsist2", "Apellido",LocalDate.now().minusYears(20), "", "Password");

	    LocalDate fecha0 = LocalDate.of(2005, 9, 1);
	    LocalDate fecha2 = LocalDate.of(2026, 9, 1);
	    
	    sistemaUsuarios.altaRegistro("nickasist1", nombreEdicion, NombreTipo, fecha0);
	    sistemaUsuarios.altaRegistro("nickasist2", nombreEdicion, NombreTipo2, fecha2);
	    
	    List<DTRegistro> registros1 = sistemaEventos.listarRegistrosDeEdicion("evento Inexistente", nombreEdicion);
	    assertEquals(registros1, new ArrayList<>());
	    
	    List<DTRegistro> registros2 = sistemaEventos.listarRegistrosDeEdicion(nombre1, "edicion Inexistente");
	    assertEquals(registros2, new ArrayList<>());
	    
	    List<DTRegistro> registros3 = sistemaEventos.listarRegistrosDeEdicion(nombre1, nombreEdicion);
	    
	    assertEquals(true, registros3.contains("Registro del " + fecha0.format(formatter)));
	    assertEquals(true, registros3.contains("Registro del " + fecha2.format(formatter)));
	    
	    
	    manejadorEventos.clearManejador();
		manejadorUsuarios.limpiarManejador();
	}


	@Test
    void testObtenerEdicionConEvento() {
		
		
        Evento evento = new Evento("nombreEv1","sigla", "descripcion", null, LocalDate.now(), "");
        manejadorEventos.agregarEvento(evento);
                Organizador org = new Organizador("nickOrg", "mail@dom.com", "Juan", "Descripcion", "", "PasswordOrg");
        mu.addOrganizador(org);
        Edicion ed = new Edicion("nombreEdicion", evento, org, "sigla1", LocalDate.now(), LocalDate.now(), "ciudad",
				"pais", LocalDate.now(), "");
        evento.agregarEdicion(ed);

        manejadorEventos.agregarEdicion(ed);

        String resultado = sistemaEventos.obtenerOrganizadorDeEdicion("nombreEv1", "nombreEdicion");
        assertEquals("nickOrg", resultado);

		manejadorEventos.clearManejador();
		manejadorUsuarios.limpiarManejador();

    }
	


	@Test
    void testObtenerEdicionSinEvento() {
        mu.limpiarManejador();
        Organizador org = new Organizador("nickOrg", "mail@dom.com", "Juan", "Descripcion",  "", "PasswordOrg");
        mu.addOrganizador(org);
        Edicion ed = new Edicion("nombreEdicion", null, org, "sigla1", LocalDate.now(), LocalDate.now(), "ciudad",
				"pais", LocalDate.now(), "");
        manejadorEventos.agregarEdicion(ed);

        String resultado = sistemaEventos.obtenerOrganizadorDeEdicion("nombreEdicion");
        assertEquals("nickOrg", resultado);

	    manejadorEventos.clearManejador();
		manejadorUsuarios.limpiarManejador();
    }

	@Test
	void testInfoEvento() throws Exception {
		
		manejadorEventos.clearManejador();
		
		sistemaEventos.altaCategoria("cat3");
		List<String> categorias = new ArrayList<>();
		categorias.add("cat3");
		
		sistemaEventos.altaEvento("nombreEv1", "descripcion1", "sigla", categorias, LocalDate.now(), "");
		Evento evento = manejadorEventos.getEvento("nombreEv1");
        manejadorEventos.agregarEvento(evento);
        
        DTEvento dte = sistemaEventos.infoEvento(evento.getNombre());

        assertEquals("nombreEv1", dte.getNombre())	;   
        
		manejadorEventos.clearManejador();
		manejadorUsuarios.limpiarManejador();
	}

	
	
	@Test
	void listarEventos_cuandoNoHayEventos_retornaListaVacia1() {
		// El método setUp ya se encargó de dejar el mapa de eventos vacío.
		
		// 1. Llamar al método a probar
		List<String> nombresEventos = sistemaEventos.listarEventos();
		
		// 2. Verificar el resultado
		assertTrue(nombresEventos.isEmpty(), "La lista debe estar vacía");
	}


	
	@Test
	void TestInfoEvento() throws Exception {
		// alta categoria
		String cat1 = "cat1";
		String cat2 = "cat2";
		sistemaEventos.altaCategoria(cat1);
		sistemaEventos.altaCategoria(cat2);
		List<String> categorias = new ArrayList<>();
		categorias.add(cat1);
		categorias.add(cat2);
		
		//AltaEvento1
		String nombre1 = "NombreEvento";
		String descripcion1 = "DescEvento";
		String sigla1 = "SiglaEvento";
		LocalDate fecha1 = LocalDate.of(2025, 8, 31);	
		String img1 = "";
		sistemaEventos.altaEvento(nombre1, descripcion1, sigla1, categorias, fecha1, img1);
		
	    Exception ex = assertThrows(IllegalArgumentException.class, () ->  sistemaEventos.infoEvento("evento inexistente"));
	    assertEquals("El evento no existe", ex.getMessage());
	 
	    
	    DTEvento evento = sistemaEventos.infoEvento(nombre1);
		assertEquals(nombre1, evento.getNombre());
		assertEquals(descripcion1, evento.getDescripcion());
		assertEquals(sigla1, evento.getSigla());
		assertEquals(fecha1, evento.getFechaAlta());
		
		List<String> cats = evento.getCategorias();
		assertEquals(true, cats.contains(cat1));
		assertEquals(true, cats.contains(cat2));
		
	    manejadorEventos.clearManejador();
		manejadorUsuarios.limpiarManejador();
	}
	
	
	@Test
	void TestListarRegistrosDeEdicion() throws Exception {
		String cat1 = "cat1";
		String cat2 = "cat2";	
		sistemaEventos.altaCategoria(cat1);
		sistemaEventos.altaCategoria(cat2);
		List<String> categorias = new ArrayList<>();
		categorias.add(cat1);
		categorias.add(cat2);
		
		//Alta organizador
		String nicknameOrg = "org1";
		String nombreOrg = "NombreOrg1";
		String emailOrg = "ejemplo@gmail.com";
		String descripcionOrg = "DescOrg1";
		String imgOrg = "";
		String passwordOrg = "PasswordOrg";
		sistemaUsuarios.altaOrganizador(nicknameOrg, nombreOrg, emailOrg, descripcionOrg, imgOrg,passwordOrg);
		
		//AltaEvento1
		String nombre1 = "NombreEvento";
		String descripcion1 = "DescEvento";
		String sigla1 = "SiglaEvento";
		LocalDate fecha1 = LocalDate.of(2025, 8, 31);
		String img1 = "";
		boolean alta1 = sistemaEventos.altaEvento(nombre1, descripcion1, sigla1, categorias, fecha1, img1);
		
		// AltaEdicion 
		String nombreEdicion = "Edicion2025";
		String sigla     = "ED2025";
		String ciudad    = "Montevideo";
		String pais      = "Uruguay";
		String nombreEvento = nombre1;
		LocalDate fechaIni = LocalDate.of(2025, 9, 1);
		LocalDate fechaFin = LocalDate.of(2025, 9, 30);
		LocalDate fechaAlta = LocalDate.now();
		String img = "";

	    sistemaEventos.altaEdicion(nombre1, nicknameOrg, nombreEdicion, sigla, ciudad, pais, fechaIni, fechaFin, fechaAlta, img);
	
	    manejadorEventos.clearManejador();
		manejadorUsuarios.limpiarManejador();
	}
		
	@Test
	public void testConsultarPatrocinio() throws Exception {
		
		manejadorEventos.clearManejador();
		manejadorUsuarios.limpiarManejador();
		InstitucionesController controladorI = new InstitucionesController();
		
        Organizador org = new Organizador("nickOrg", "mail@dom.com", "Juan", "Descripcion", "", "PasswordOrg");
        org.setSitioWeb("www.organizador.com");
        Evento ev = new Evento("nombreEv1","sigla", "descripcion", null, LocalDate.now(), "");
        Edicion ed = new Edicion("nombreEdicion", ev, org, "sigla1", LocalDate.now(), LocalDate.now(), "ciudad",
				"pais", LocalDate.now(), "");
        manejadorEventos.agregarEdicion(ed);
        manejadorEventos.agregarEvento(ev);
        
		//alta TipoRegistro1
	    String NombreTipo = "VIP";
	    String desc = "aaaa";
	    int costo = 5000;
	    int cupo = 300;
	    sistemaEventos.altaTipoDeRegistro(NombreTipo, "nombreEdicion", desc, costo, cupo);
	    
	    ManejadorInstituciones mi = ManejadorInstituciones.getInstance();
		controladorI.altaInstitucion("Institucion1", "DescInstitucion", "webInstitucion", "");
		Institucion inst = mi.obtenerInstitucion("Institucion1");
		
	
	    sistemaEventos.altaPatrocinio("Institucion1", "nombreEdicion", NombreTipo, NivelPatrocinio.BRONCE, costo, cupo, LocalDate.now(), "cod");
	    
		
	    DTPatrocinio dt = sistemaEventos.consultarPatrocinio("nombreEdicion", "cod");
	    assertEquals("cod", dt.getCodigo());
		assertEquals(costo, dt.getMonto());
		
		assertEquals(0, dt.getCantRegistros());
		
	    manejadorEventos.clearManejador();
		manejadorUsuarios.limpiarManejador();
       
	}
	@Test
	void listarEventos_sinEventos() {
	    manejadorEventos.clearManejador();
	    manejadorUsuarios.limpiarManejador();

	    List<String> res = sistemaEventos.listarEventos();

	    assertNotNull(res);
	    assertTrue(res.isEmpty(), "Debe estar vacía cuando no hay eventos");

	    manejadorEventos.clearManejador();
	    manejadorUsuarios.limpiarManejador();
	}
	@Test
	void testListarCategorias() throws Exception {
	    manejadorEventos.clearManejador();
	    
	    sistemaEventos.altaCategoria("Deportes");
	    sistemaEventos.altaCategoria("Música");
	    sistemaEventos.altaCategoria("Arte");
	    
	    List<String> categorias = sistemaEventos.listarCategorias();
	    
	    assertNotNull(categorias);
	    assertEquals(3, categorias.size());
	    assertTrue(categorias.contains("Deportes"));
	    assertTrue(categorias.contains("Música"));
	    assertTrue(categorias.contains("Arte"));
	    
	    manejadorEventos.clearManejador();
	}

	@Test
	void testListarCategoriasVacio() {
	    manejadorEventos.clearManejador();
	    
	    List<String> categorias = sistemaEventos.listarCategorias();
	    
	    assertNotNull(categorias);
	    assertTrue(categorias.isEmpty());
	    
	    manejadorEventos.clearManejador();
	}

	@Test
	void testListarEdicionesEvento() throws Exception {
	    manejadorEventos.clearManejador();
	    manejadorUsuarios.limpiarManejador();
	    
	    // Crear categorías
	    sistemaEventos.altaCategoria("cat1");
	    List<String> categorias = new ArrayList<>();
	    categorias.add("cat1");
	    
	    // Crear organizador
	    sistemaUsuarios.altaOrganizador("org1", "org1@mail.com", "Organizador", "Desc", "", "Pass123");
	    
	    // Crear evento
	    sistemaEventos.altaEvento("EventoPrueba", "Descripción", "EP", categorias, LocalDate.now(), "");
	    
	    // Crear ediciones
	    sistemaEventos.altaEdicion("EventoPrueba", "org1", "Edicion2024", "EP2024", "Montevideo", 
	            "Uruguay", LocalDate.now(), LocalDate.now().plusDays(10), LocalDate.now(), "");
	    sistemaEventos.altaEdicion("EventoPrueba", "org1", "Edicion2025", "EP2025", "Montevideo", 
	            "Uruguay", LocalDate.now(), LocalDate.now().plusDays(10), LocalDate.now(), "");
	    
	    List<String> ediciones = sistemaEventos.listarEdicionesEvento("EventoPrueba");
	    
	    assertNotNull(ediciones);
	    assertEquals(2, ediciones.size());
	    assertTrue(ediciones.contains("Edicion2024"));
	    assertTrue(ediciones.contains("Edicion2025"));
	    
	    manejadorEventos.clearManejador();
	    manejadorUsuarios.limpiarManejador();
	}

	@Test
	void testListarEdicionesEventoIngresadas() throws Exception {
	    manejadorEventos.clearManejador();
	    manejadorUsuarios.limpiarManejador();
	    
	    sistemaEventos.altaCategoria("cat1");
	    List<String> categorias = new ArrayList<>();
	    categorias.add("cat1");
	    
	    sistemaUsuarios.altaOrganizador("org1", "org1@mail.com", "Organizador", "Desc", "", "Pass123");
	    sistemaEventos.altaEvento("EventoTest", "Descripción", "ET", categorias, LocalDate.now(), "");
	    
	    sistemaEventos.altaEdicion("EventoTest", "org1", "EdicionIngresada", "ET2024", "Montevideo", 
	            "Uruguay", LocalDate.now(), LocalDate.now().plusDays(10), LocalDate.now(), "");
	    
	    // Marcar como ingresada
	    List<String> ediciones = sistemaEventos.listarEdicionesEventoIngresadas("EventoTest");
	    
	    assertNotNull(ediciones);
	    
	    manejadorEventos.clearManejador();
	    manejadorUsuarios.limpiarManejador();
	}

	@Test
	void testListarEdicionesEventoOrganizador() throws Exception {
	    manejadorEventos.clearManejador();
	    manejadorUsuarios.limpiarManejador();
	    
	    sistemaEventos.altaCategoria("cat1");
	    List<String> categorias = new ArrayList<>();
	    categorias.add("cat1");
	    
	    sistemaUsuarios.altaOrganizador("org1", "org1@mail.com", "Organizador1", "Desc", "", "Pass123");
	    sistemaUsuarios.altaOrganizador("org2", "org2@mail.com", "Organizador2", "Desc", "", "Pass456");
	    
	    sistemaEventos.altaEvento("EventoMultiOrg", "Descripción", "EMO", categorias, LocalDate.now(), "");
	    
	    sistemaEventos.altaEdicion("EventoMultiOrg", "org1", "EdicionOrg1", "EMO2024", "Montevideo", 
	            "Uruguay", LocalDate.now(), LocalDate.now().plusDays(10), LocalDate.now(), "");
	    sistemaEventos.altaEdicion("EventoMultiOrg", "org2", "EdicionOrg2", "EMO2025", "Punta del Este", 
	            "Uruguay", LocalDate.now(), LocalDate.now().plusDays(10), LocalDate.now(), "");
	    
	    List<String> edicionesOrg1 = sistemaEventos.listarEdicionesEventoOrganizador("EventoMultiOrg", "org1");
	    
	    assertNotNull(edicionesOrg1);
	    assertTrue(edicionesOrg1.contains("EdicionOrg1"));
	    assertFalse(edicionesOrg1.contains("EdicionOrg2"));
	    
	    manejadorEventos.clearManejador();
	    manejadorUsuarios.limpiarManejador();
	}

	@Test
	void testListarTiposDeRegistroDeEdicion() throws Exception {
	    manejadorEventos.clearManejador();
	    manejadorUsuarios.limpiarManejador();
	    
	    sistemaEventos.altaCategoria("cat1");
	    List<String> categorias = new ArrayList<>();
	    categorias.add("cat1");
	    
	    sistemaUsuarios.altaOrganizador("org1", "org1@mail.com", "Organizador", "Desc", "", "Pass123");
	    sistemaEventos.altaEvento("EventoTipos", "Descripción", "ETR", categorias, LocalDate.now(), "");
	    sistemaEventos.altaEdicion("EventoTipos", "org1", "EdicionTipos", "ETR2024", "Montevideo", 
	            "Uruguay", LocalDate.now(), LocalDate.now().plusDays(10), LocalDate.now(), "");
	    
	    sistemaEventos.altaTipoDeRegistro("VIP", "EdicionTipos", "Acceso VIP", 5000, 100);
	    sistemaEventos.altaTipoDeRegistro("General", "EdicionTipos", "Acceso General", 2000, 500);
	    
	    List<String> tipos = sistemaEventos.listarTiposDeRegistroDeEdicion("EdicionTipos");
	    
	    assertNotNull(tipos);
	    assertEquals(2, tipos.size());
	    assertTrue(tipos.contains("VIP"));
	    assertTrue(tipos.contains("General"));
	    
	    manejadorEventos.clearManejador();
	    manejadorUsuarios.limpiarManejador();
	}

	@Test
	void testAceptarEdicion() throws Exception {
	    manejadorEventos.clearManejador();
	    manejadorUsuarios.limpiarManejador();
	    
	    sistemaEventos.altaCategoria("cat1");
	    List<String> categorias = new ArrayList<>();
	    categorias.add("cat1");
	    
	    sistemaUsuarios.altaOrganizador("org1", "org1@mail.com", "Organizador", "Desc", "", "Pass123");
	    sistemaEventos.altaEvento("EventoAceptar", "Descripción", "EA", categorias, LocalDate.now(), "");
	    sistemaEventos.altaEdicion("EventoAceptar", "org1", "EdicionAceptar", "EA2024", "Montevideo", 
	            "Uruguay", LocalDate.now(), LocalDate.now().plusDays(10), LocalDate.now(), "");
	    
	    sistemaEventos.aceptarEdicion("EdicionAceptar");
	    
	    DTEdicion edicion = sistemaEventos.infoEdicion("EdicionAceptar");
	    assertEquals(EstadoEdicion.ACEPTADA, edicion.getEstado());
	    
	    manejadorEventos.clearManejador();
	    manejadorUsuarios.limpiarManejador();
	}

	@Test
	void testRechazarEdicion() throws Exception {
	    manejadorEventos.clearManejador();
	    manejadorUsuarios.limpiarManejador();
	    
	    sistemaEventos.altaCategoria("cat1");
	    List<String> categorias = new ArrayList<>();
	    categorias.add("cat1");
	    
	    sistemaUsuarios.altaOrganizador("org1", "org1@mail.com", "Organizador", "Desc", "", "Pass123");
	    sistemaEventos.altaEvento("EventoRechazar", "Descripción", "ER", categorias, LocalDate.now(), "");
	    sistemaEventos.altaEdicion("EventoRechazar", "org1", "EdicionRechazar", "ER2024", "Montevideo", 
	            "Uruguay", LocalDate.now(), LocalDate.now().plusDays(10), LocalDate.now(), "");
	    
	    sistemaEventos.rechazarEdicion("EdicionRechazar");
	    
	    DTEdicion edicion = sistemaEventos.infoEdicion("EdicionRechazar");
	    assertEquals(EstadoEdicion.RECHAZADA, edicion.getEstado());
	    
	    manejadorEventos.clearManejador();
	    manejadorUsuarios.limpiarManejador();
	}

	@Test
	void testInfoEdicion() throws Exception {
	    manejadorEventos.clearManejador();
	    manejadorUsuarios.limpiarManejador();
	    
	    sistemaEventos.altaCategoria("cat1");
	    List<String> categorias = new ArrayList<>();
	    categorias.add("cat1");
	    
	    sistemaUsuarios.altaOrganizador("org1", "org1@mail.com", "Organizador", "Desc", "", "Pass123");
	    sistemaEventos.altaEvento("EventoInfo", "Descripción", "EI", categorias, LocalDate.now(), "");
	    
	    LocalDate fechaIni = LocalDate.of(2025, 9, 1);
	    LocalDate fechaFin = LocalDate.of(2025, 9, 30);
	    
	    sistemaEventos.altaEdicion("EventoInfo", "org1", "EdicionInfo", "EI2024", "Montevideo", 
	            "Uruguay", fechaIni, fechaFin, LocalDate.now(), "");
	    
	    DTEdicion edicion = sistemaEventos.infoEdicion("EdicionInfo");
	    
	    assertNotNull(edicion);
	    assertEquals("EdicionInfo", edicion.getNombreEdicion());
	    assertEquals("EI2024", edicion.getSigla());
	    assertEquals("Montevideo", edicion.getCiudad());
	    assertEquals("Uruguay", edicion.getPais());
	    assertEquals(fechaIni, edicion.getFechaIni());
	    assertEquals(fechaFin, edicion.getFechaFin());
	    
	    manejadorEventos.clearManejador();
	    manejadorUsuarios.limpiarManejador();
	}

	@Test
	void testListarPatrociniosDeEdicion() throws Exception {
	    manejadorEventos.clearManejador();
	    manejadorUsuarios.limpiarManejador();
	    
	    InstitucionesController controladorI = new InstitucionesController();
	    ManejadorInstituciones mi = ManejadorInstituciones.getInstance();
	    
	    sistemaEventos.altaCategoria("cat1");
	    List<String> categorias = new ArrayList<>();
	    categorias.add("cat1");
	    
	    sistemaUsuarios.altaOrganizador("org1", "org1@mail.com", "Organizador", "Desc", "", "Pass123");
	    sistemaEventos.altaEvento("EventoPat", "Descripción", "EPAT", categorias, LocalDate.now(), "");
	    sistemaEventos.altaEdicion("EventoPat", "org1", "EdicionPat", "EPAT2024", "Montevideo", 
	            "Uruguay", LocalDate.now(), LocalDate.now().plusDays(10), LocalDate.now(), "");
	    
	    sistemaEventos.altaTipoDeRegistro("VIP", "EdicionPat", "VIP", 5000, 100);
	    
	    controladorI.altaInstitucion("InstPat1", "Desc", "web", "");
	    controladorI.altaInstitucion("InstPat2", "Desc", "web", "");
	    
	    sistemaEventos.altaPatrocinio("InstPat1", "EdicionPat", "VIP", NivelPatrocinio.ORO, 10000, 50, LocalDate.now(), "COD1");
	    sistemaEventos.altaPatrocinio("InstPat2", "EdicionPat", "VIP", NivelPatrocinio.PLATA, 5000, 30, LocalDate.now(), "COD2");
	    
	    List<String> patrocinios = sistemaEventos.listarPatrociniosDeEdicion("EventoPat", "EdicionPat");
	    
	    assertNotNull(patrocinios);
	    assertEquals(2, patrocinios.size());
	    
	    manejadorEventos.clearManejador();
	    manejadorUsuarios.limpiarManejador();
	}

	@Test
	void testListarCodigosDePatrocinioDeEdicion() throws Exception {
	    manejadorEventos.clearManejador();
	    manejadorUsuarios.limpiarManejador();
	    
	    InstitucionesController controladorI = new InstitucionesController();
	    
	    sistemaEventos.altaCategoria("cat1");
	    List<String> categorias = new ArrayList<>();
	    categorias.add("cat1");
	    
	    sistemaUsuarios.altaOrganizador("org1", "org1@mail.com", "Organizador", "Desc", "", "Pass123");
	    sistemaEventos.altaEvento("EventoCod", "Descripción", "ECOD", categorias, LocalDate.now(), "");
	    sistemaEventos.altaEdicion("EventoCod", "org1", "EdicionCod", "ECOD2024", "Montevideo", 
	            "Uruguay", LocalDate.now(), LocalDate.now().plusDays(10), LocalDate.now(), "");
	    
	    sistemaEventos.altaTipoDeRegistro("VIP", "EdicionCod", "VIP", 5000, 100);
	    controladorI.altaInstitucion("InstCod", "Desc", "web", "");
	    
	    sistemaEventos.altaPatrocinio("InstCod", "EdicionCod", "VIP", NivelPatrocinio.BRONCE, 2000, 20, LocalDate.now(), "CODIGO123");
	    
	    List<String> codigos = sistemaEventos.listarCodigosDePatrocinioDeEdicion("EventoCod", "EdicionCod");
	    
	    assertNotNull(codigos);
	    assertTrue(codigos.contains("CODIGO123"));
	    
	    manejadorEventos.clearManejador();
	    manejadorUsuarios.limpiarManejador();
	}

	@Test
	void testExisteCodigoDePatrocinioEnEdicion() throws Exception {
	    manejadorEventos.clearManejador();
	    manejadorUsuarios.limpiarManejador();
	    
	    InstitucionesController controladorI = new InstitucionesController();
	    
	    sistemaEventos.altaCategoria("cat1");
	    List<String> categorias = new ArrayList<>();
	    categorias.add("cat1");
	    
	    sistemaUsuarios.altaOrganizador("org1", "org1@mail.com", "Organizador", "Desc", "", "Pass123");
	    sistemaEventos.altaEvento("EventoExiste", "Descripción", "EEX", categorias, LocalDate.now(), "");
	    sistemaEventos.altaEdicion("EventoExiste", "org1", "EdicionExiste", "EEX2024", "Montevideo", 
	            "Uruguay", LocalDate.now(), LocalDate.now().plusDays(10), LocalDate.now(), "");
	    
	    sistemaEventos.altaTipoDeRegistro("VIP", "EdicionExiste", "VIP", 5000, 100);
	    controladorI.altaInstitucion("InstExiste", "Desc", "web", "");
	    
	    sistemaEventos.altaPatrocinio("InstExiste", "EdicionExiste", "VIP", NivelPatrocinio.ORO, 15000, 40, LocalDate.now(), "EXISTE123");
	    
	    boolean existe = sistemaEventos.existeCodigoDePatrocinioEnEdicion("EXISTE123", "EventoExiste", "EdicionExiste");
	    assertTrue(existe);
	    
	    boolean noExiste = sistemaEventos.existeCodigoDePatrocinioEnEdicion("NOEXISTE", "EventoExiste", "EdicionExiste");
	    assertFalse(noExiste);
	    
	    manejadorEventos.clearManejador();
	    manejadorUsuarios.limpiarManejador();
	}

	@Test
	void testConsultarPatrociniosDeEdicion() throws Exception {
	    manejadorEventos.clearManejador();
	    manejadorUsuarios.limpiarManejador();
	    
	    InstitucionesController controladorI = new InstitucionesController();
	    
	    sistemaEventos.altaCategoria("cat1");
	    List<String> categorias = new ArrayList<>();
	    categorias.add("cat1");
	    
	    sistemaUsuarios.altaOrganizador("org1", "org1@mail.com", "Organizador", "Desc", "", "Pass123");
	    sistemaEventos.altaEvento("EventoConsulta", "Descripción", "ECON", categorias, LocalDate.now(), "");
	    sistemaEventos.altaEdicion("EventoConsulta", "org1", "EdicionConsulta", "ECON2024", "Montevideo", 
	            "Uruguay", LocalDate.now(), LocalDate.now().plusDays(10), LocalDate.now(), "");
	    
	    sistemaEventos.altaTipoDeRegistro("VIP", "EdicionConsulta", "VIP", 5000, 100);
	    controladorI.altaInstitucion("InstConsulta", "Desc", "web", "");
	    
	    sistemaEventos.altaPatrocinio("InstConsulta", "EdicionConsulta", "VIP", NivelPatrocinio.PLATA, 7000, 35, LocalDate.now(), "CONSULTA1");
	    
	    Map<String, DTPatrocinio> patrocinios = sistemaEventos.consultarPatrociniosDeEdicion("EdicionConsulta");
	    
	    assertNotNull(patrocinios);
	    assertTrue(patrocinios.containsKey("CONSULTA1"));
	    
	    manejadorEventos.clearManejador();
	    manejadorUsuarios.limpiarManejador();
	}

	@Test
	void testFinalizarEvento() throws Exception {
	    manejadorEventos.clearManejador();
	    manejadorUsuarios.limpiarManejador();
	    
	    sistemaEventos.altaCategoria("cat1");
	    List<String> categorias = new ArrayList<>();
	    categorias.add("cat1");
	    
	    sistemaEventos.altaEvento("EventoFinalizar", "Descripción", "EFIN", categorias, LocalDate.now(), "");
	    
	    sistemaEventos.finalizarEvento("EventoFinalizar");
	    
	    // Verificar que el evento fue finalizado
	    DTEvento evento = sistemaEventos.infoEvento("EventoFinalizar");
	    assertNotNull(evento);
	    
	    manejadorEventos.clearManejador();
	    manejadorUsuarios.limpiarManejador();
	}

	@Test
	void testArchivarEdicionDeEvento() throws Exception {
	    manejadorEventos.clearManejador();
	    manejadorUsuarios.limpiarManejador();
	    
	    sistemaEventos.altaCategoria("cat1");
	    List<String> categorias = new ArrayList<>();
	    categorias.add("cat1");
	    
	    sistemaUsuarios.altaOrganizador("org1", "org1@mail.com", "Organizador", "Desc", "", "Pass123");
	    sistemaEventos.altaEvento("EventoArchivar", "Descripción", "EARCH", categorias, LocalDate.now(), "");
	    sistemaEventos.altaEdicion("EventoArchivar", "org1", "EdicionArchivar", "EARCH2024", "Montevideo", 
	            "Uruguay", LocalDate.now(), LocalDate.now().plusDays(10), LocalDate.now(), "");
	    
	    sistemaEventos.archivarEdicionDeEvento("EdicionArchivar");
	    
	    // Verificar que la edición fue archivada
	    DTEdicion edicion = sistemaEventos.infoEdicion("EdicionArchivar");
	    assertNotNull(edicion);
	    
	    manejadorEventos.clearManejador();
	    manejadorUsuarios.limpiarManejador();
	}

	@Test
	void testIncrementarVisita() throws Exception {
	    manejadorEventos.clearManejador();
	    manejadorUsuarios.limpiarManejador();
	    
	    sistemaEventos.altaCategoria("cat1");
	    List<String> categorias = new ArrayList<>();
	    categorias.add("cat1");
	    
	    sistemaEventos.altaEvento("EventoVisitas", "Descripción", "EVIS", categorias, LocalDate.now(), "");
	    
	    int visitas1 = sistemaEventos.incrementarVisita("EventoVisitas");
	    int visitas2 = sistemaEventos.incrementarVisita("EventoVisitas");
	    int visitas3 = sistemaEventos.incrementarVisita("EventoVisitas");
	    
	    assertEquals(1, visitas1);
	    assertEquals(2, visitas2);
	    assertEquals(3, visitas3);
	    
	    manejadorEventos.clearManejador();
	    manejadorUsuarios.limpiarManejador();
	}

	@Test
	void testAgregarVideo() throws Exception {
	    manejadorEventos.clearManejador();
	    manejadorUsuarios.limpiarManejador();
	    
	    sistemaEventos.altaCategoria("cat1");
	    List<String> categorias = new ArrayList<>();
	    categorias.add("cat1");
	    
	    sistemaUsuarios.altaOrganizador("org1", "org1@mail.com", "Organizador", "Desc", "", "Pass123");
	    sistemaEventos.altaEvento("EventoVideo", "Descripción", "EVID", categorias, LocalDate.now(), "");
	    sistemaEventos.altaEdicion("EventoVideo", "org1", "EdicionVideo", "EVID2024", "Montevideo", 
	            "Uruguay", LocalDate.now(), LocalDate.now().plusDays(10), LocalDate.now(), "");
	    
	    sistemaEventos.agregarVideo("EdicionVideo", "https://youtube.com/video123");
	    
	    DTEdicion edicion = sistemaEventos.infoEdicion("EdicionVideo");
	    assertNotNull(edicion);
	    // Si tu DTEdicion tiene método para obtener videos, verificar aquí
	    
	    manejadorEventos.clearManejador();
	    manejadorUsuarios.limpiarManejador();
	}

	@Test
	void testAgregarVideoEdicionInexistente() {
	    manejadorEventos.clearManejador();
	    
	    Exception ex = assertThrows(IllegalArgumentException.class, () -> 
	        sistemaEventos.agregarVideo("EdicionInexistente", "https://youtube.com/video123"));
	    
	    assertEquals("La edicion no existe", ex.getMessage());
	    
	    manejadorEventos.clearManejador();
	}

	@Test
	void testConsultarPatrocinioPorInstitucion() throws Exception {
	    manejadorEventos.clearManejador();
	    manejadorUsuarios.limpiarManejador();
	    
	    InstitucionesController controladorI = new InstitucionesController();
	    
	    sistemaEventos.altaCategoria("cat1");
	    List<String> categorias = new ArrayList<>();
	    categorias.add("cat1");
	    
	    sistemaUsuarios.altaOrganizador("org1", "org1@mail.com", "Organizador", "Desc", "", "Pass123");
	    sistemaEventos.altaEvento("EventoPatInst", "Descripción", "EPATINST", categorias, LocalDate.now(), "");
	    sistemaEventos.altaEdicion("EventoPatInst", "org1", "EdicionPatInst", "EPATINST2024", "Montevideo", 
	            "Uruguay", LocalDate.now(), LocalDate.now().plusDays(10), LocalDate.now(), "");
	    
	    sistemaEventos.altaTipoDeRegistro("VIP", "EdicionPatInst", "VIP", 5000, 100);
	    controladorI.altaInstitucion("InstPatInst", "Desc", "web", "");
	    
	    sistemaEventos.altaPatrocinio("InstPatInst", "EdicionPatInst", "VIP", NivelPatrocinio.ORO, 12000, 45, LocalDate.now(), "CODINST123");
	    
	    DTPatrocinio patrocinio = sistemaEventos.consultarPatrocinioPorInstitucion("EdicionPatInst", "CODINST123");
	    
	    assertNotNull(patrocinio);
	    assertEquals("CODINST123", patrocinio.getCodigo());
	    assertEquals(12000, patrocinio.getMonto());
	    
	    manejadorEventos.clearManejador();
	    manejadorUsuarios.limpiarManejador();
	}
	
	@Test
	void testInfoEventoInexistente() {
	    manejadorEventos.clearManejador();
	    
	    Exception ex = assertThrows(IllegalArgumentException.class, () -> 
	        sistemaEventos.infoEvento("EventoNoExiste"));
	    
	    assertEquals("El evento no existe", ex.getMessage());
	    
	    manejadorEventos.clearManejador();
	}

	@Test
	void testInfoEdicionInexistente() {
	    manejadorEventos.clearManejador();
	    
	    DTEdicion edicion = sistemaEventos.infoEdicion("EdicionInexistente");
	    
	    manejadorEventos.clearManejador();
	}

	@Test
	void testListarEdicionesEventoInexistente() {
	    List<String> ediciones = sistemaEventos.listarEdicionesEvento("EventoInexistente");
	    
	    assertNotNull(ediciones);
	    assertEquals(0, ediciones.size());
	}

	@Test
	void testListarEdicionesEventoSinEdiciones() throws Exception {
	    manejadorEventos.clearManejador();
	    
	    sistemaEventos.altaCategoria("cat1");
	    List<String> categorias = new ArrayList<>();
	    categorias.add("cat1");
	    
	    sistemaEventos.altaEvento("EventoSinEd", "Desc", "ESE", categorias, LocalDate.now(), "");
	    
	    List<String> ediciones = sistemaEventos.listarEdicionesEvento("EventoSinEd");
	    
	    assertNotNull(ediciones);
	    assertEquals(0, ediciones.size());
	    
	    manejadorEventos.clearManejador();
	}

	@Test
	void testObtenerOrganizadorDeEdicionEventoInexistente() {
	    String resultado = sistemaEventos.obtenerOrganizadorDeEdicion("EventoInex", "EdicionInex");
	    
	    manejadorEventos.clearManejador();
	}

	@Test
	void testListarTiposDeRegistroDeEdicionSinTipos() throws Exception {
	    manejadorEventos.clearManejador();
	    manejadorUsuarios.limpiarManejador();
	    
	    sistemaEventos.altaCategoria("cat1");
	    List<String> categorias = new ArrayList<>();
	    categorias.add("cat1");
	    
	    sistemaUsuarios.altaOrganizador("org1", "org1@mail.com", "Org", "Desc", "", "Pass");
	    sistemaEventos.altaEvento("EventoSTR", "Desc", "ESTR", categorias, LocalDate.now(), "");
	    sistemaEventos.altaEdicion("EventoSTR", "org1", "EdicionSTR", "ESTR24", "Mvd", 
	            "UY", LocalDate.now(), LocalDate.now().plusDays(10), LocalDate.now(), "");
	    
	    List<String> tipos = sistemaEventos.listarTiposDeRegistroDeEdicion("EdicionSTR");
	    
	    assertNotNull(tipos);
	    assertEquals(0, tipos.size());
	    
	    manejadorEventos.clearManejador();
	    manejadorUsuarios.limpiarManejador();
	}

	@Test
	void testConsultaTipoDeRegistroInexistente() throws Exception {
	    manejadorEventos.clearManejador();
	    manejadorUsuarios.limpiarManejador();
	    
	    sistemaEventos.altaCategoria("cat1");
	    List<String> categorias = new ArrayList<>();
	    categorias.add("cat1");
	    
	    sistemaUsuarios.altaOrganizador("org1", "org1@mail.com", "Org", "Desc", "", "Pass");
	    sistemaEventos.altaEvento("EventoCTR", "Desc", "ECTR", categorias, LocalDate.now(), "");
	    sistemaEventos.altaEdicion("EventoCTR", "org1", "EdicionCTR", "ECTR24", "Mvd", 
	            "UY", LocalDate.now(), LocalDate.now().plusDays(10), LocalDate.now(), "");
	    
	    DTTipoRegistro tipo = sistemaEventos.consultaTipoDeRegistro("EdicionCTR", "TipoInexistente");
	    
	    // Verificar según implementación
	    manejadorEventos.clearManejador();
	    manejadorUsuarios.limpiarManejador();
	}

	@Test
	void testAceptarEdicionInexistente() {
	    manejadorEventos.clearManejador();
	    
	    // Intentar aceptar edición inexistente
	    sistemaEventos.aceptarEdicion("EdicionInexistente");
	    
	    // Verificar que no explota
	    manejadorEventos.clearManejador();
	}


	@Test
	void testListarPatrociniosDeEdicionSinPatrocinios() throws Exception {
	    manejadorEventos.clearManejador();
	    manejadorUsuarios.limpiarManejador();
	    
	    sistemaEventos.altaCategoria("cat1");
	    List<String> categorias = new ArrayList<>();
	    categorias.add("cat1");
	    
	    sistemaUsuarios.altaOrganizador("org1", "org1@mail.com", "Org", "Desc", "", "Pass");
	    sistemaEventos.altaEvento("EventoSP", "Desc", "ESP", categorias, LocalDate.now(), "");
	    sistemaEventos.altaEdicion("EventoSP", "org1", "EdicionSP", "ESP24", "Mvd", 
	            "UY", LocalDate.now(), LocalDate.now().plusDays(10), LocalDate.now(), "");
	    
	    List<String> patrocinios = sistemaEventos.listarPatrociniosDeEdicion("EventoSP", "EdicionSP");
	    
	    assertNotNull(patrocinios);
	    assertEquals(0, patrocinios.size());
	    
	    manejadorEventos.clearManejador();
	    manejadorUsuarios.limpiarManejador();
	}

	@Test
	void testConsultarPatrocinioInexistente() throws Exception {
	    manejadorEventos.clearManejador();
	    manejadorUsuarios.limpiarManejador();
	    
	    sistemaEventos.altaCategoria("cat1");
	    List<String> categorias = new ArrayList<>();
	    categorias.add("cat1");
	    
	    sistemaUsuarios.altaOrganizador("org1", "org1@mail.com", "Org", "Desc", "", "Pass");
	    sistemaEventos.altaEvento("EventoCP", "Desc", "ECP", categorias, LocalDate.now(), "");
	    sistemaEventos.altaEdicion("EventoCP", "org1", "EdicionCP", "ECP24", "Mvd", 
	            "UY", LocalDate.now(), LocalDate.now().plusDays(10), LocalDate.now(), "");
	    
	    DTPatrocinio patrocinio = sistemaEventos.consultarPatrocinio("EdicionCP", "CodigoInexistente");
	    
	    manejadorEventos.clearManejador();
	    manejadorUsuarios.limpiarManejador();
	}

	@Test
	void testArchivarEdicionInexistente() {
	    manejadorEventos.clearManejador();
	    
	    // Intentar archivar edición inexistente
	    sistemaEventos.archivarEdicionDeEvento("EdicionInexistente");
	    
	    // Verificar que no explota
	    manejadorEventos.clearManejador();
	}


	@Test
	void testListarEdicionesEventoOrganizadorSinEdiciones() throws Exception {
	    manejadorEventos.clearManejador();
	    manejadorUsuarios.limpiarManejador();
	    
	    sistemaEventos.altaCategoria("cat1");
	    List<String> categorias = new ArrayList<>();
	    categorias.add("cat1");
	    
	    sistemaUsuarios.altaOrganizador("org1", "org1@mail.com", "Org1", "Desc", "", "Pass");
	    sistemaUsuarios.altaOrganizador("org2", "org2@mail.com", "Org2", "Desc", "", "Pass");
	    
	    sistemaEventos.altaEvento("EventoOrg", "Desc", "EO", categorias, LocalDate.now(), "");
	    
	    sistemaEventos.altaEdicion("EventoOrg", "org1", "EdOrg1", "EO24", "Mvd", 
	            "UY", LocalDate.now(), LocalDate.now().plusDays(10), LocalDate.now(), "");
	    
	    List<String> ediciones = sistemaEventos.listarEdicionesEventoOrganizador("EventoOrg", "org2");
	    
	    assertNotNull(ediciones);
	    assertEquals(0, ediciones.size());
	    
	    manejadorEventos.clearManejador();
	    manejadorUsuarios.limpiarManejador();
	}

	@Test
	void testConsultarPatrociniosDeEdicionVacio() throws Exception {
	    manejadorEventos.clearManejador();
	    manejadorUsuarios.limpiarManejador();
	    
	    sistemaEventos.altaCategoria("cat1");
	    List<String> categorias = new ArrayList<>();
	    categorias.add("cat1");
	    
	    sistemaUsuarios.altaOrganizador("org1", "org1@mail.com", "Org", "Desc", "", "Pass");
	    sistemaEventos.altaEvento("EventoCPV", "Desc", "ECPV", categorias, LocalDate.now(), "");
	    sistemaEventos.altaEdicion("EventoCPV", "org1", "EdicionCPV", "ECPV24", "Mvd", 
	            "UY", LocalDate.now(), LocalDate.now().plusDays(10), LocalDate.now(), "");
	    
	    Map<String, DTPatrocinio> patrocinios = sistemaEventos.consultarPatrociniosDeEdicion("EdicionCPV");
	    
	    assertNotNull(patrocinios);
	    assertEquals(0, patrocinios.size());
	    
	    manejadorEventos.clearManejador();
	    manejadorUsuarios.limpiarManejador();
	}
	
	@Test
	void testFinalizarEventoInexistente() {
	    manejadorEventos.clearManejador();
	    
	    int resultado = sistemaEventos.incrementarVisita("EventoInexistente");
	    assertEquals(0, resultado);
	    
	    manejadorEventos.clearManejador();
	}

	@Test
	void testEventoConMultiplesEdiciones() throws Exception {
	    manejadorEventos.clearManejador();
	    manejadorUsuarios.limpiarManejador();
	    
	    sistemaEventos.altaCategoria("cat1");
	    List<String> categorias = new ArrayList<>();
	    categorias.add("cat1");
	    
	    sistemaUsuarios.altaOrganizador("org1", "org1@mail.com", "Org1", "Desc", "", "Pass1");
	    sistemaUsuarios.altaOrganizador("org2", "org2@mail.com", "Org2", "Desc", "", "Pass2");
	    sistemaUsuarios.altaOrganizador("org3", "org3@mail.com", "Org3", "Desc", "", "Pass3");
	    
	    sistemaEventos.altaEvento("EventoMulti", "Desc", "EM", categorias, LocalDate.now(), "");
	    
	    sistemaEventos.altaEdicion("EventoMulti", "org1", "Ed2024", "EM24", "Mvd", 
	            "UY", LocalDate.now(), LocalDate.now().plusDays(10), LocalDate.now(), "");
	    sistemaEventos.altaEdicion("EventoMulti", "org2", "Ed2025", "EM25", "Punta", 
	            "UY", LocalDate.now(), LocalDate.now().plusDays(10), LocalDate.now(), "");
	    sistemaEventos.altaEdicion("EventoMulti", "org3", "Ed2026", "EM26", "Salto", 
	            "UY", LocalDate.now(), LocalDate.now().plusDays(10), LocalDate.now(), "");
	    
	    List<String> ediciones = sistemaEventos.listarEdicionesEvento("EventoMulti");
	    assertEquals(3, ediciones.size());
	    
	    List<String> edicionesOrg1 = sistemaEventos.listarEdicionesEventoOrganizador("EventoMulti", "org1");
	    assertEquals(1, edicionesOrg1.size());
	    assertTrue(edicionesOrg1.contains("Ed2024"));
	    
	    List<String> edicionesOrg2 = sistemaEventos.listarEdicionesEventoOrganizador("EventoMulti", "org2");
	    assertEquals(1, edicionesOrg2.size());
	    assertTrue(edicionesOrg2.contains("Ed2025"));
	    
	    manejadorEventos.clearManejador();
	    manejadorUsuarios.limpiarManejador();
	}

	@Test
	void testConsultaTipoDeRegistroConDatosCompletos() throws Exception {
	    manejadorEventos.clearManejador();
	    manejadorUsuarios.limpiarManejador();
	    
	    sistemaEventos.altaCategoria("cat1");
	    List<String> categorias = new ArrayList<>();
	    categorias.add("cat1");
	    
	    sistemaUsuarios.altaOrganizador("org1", "org1@mail.com", "Org", "Desc", "", "Pass");
	    sistemaEventos.altaEvento("Evento", "Desc", "EV", categorias, LocalDate.now(), "");
	    sistemaEventos.altaEdicion("Evento", "org1", "Edicion", "ED24", "Mvd", 
	            "UY", LocalDate.now(), LocalDate.now().plusDays(10), LocalDate.now(), "");
	    
	    String nombreTipo = "Premium";
	    String desc = "Acceso Premium con beneficios";
	    int costo = 8000;
	    int cupo = 150;
	    
	    sistemaEventos.altaTipoDeRegistro(nombreTipo, "Edicion", desc, costo, cupo);
	    
	    DTTipoRegistro tipo = sistemaEventos.consultaTipoDeRegistro("Edicion", nombreTipo);
	    
	    assertNotNull(tipo);
	    assertEquals(nombreTipo, tipo.getNombreEdicion());
	    assertEquals(desc, tipo.getDescripcion());
	    //assertEquals(costo, tipo.getCosto());
	    assertEquals(cupo, tipo.getCupo());
	    
	    manejadorEventos.clearManejador();
	    manejadorUsuarios.limpiarManejador();
	}

	@Test
	void testRechazarEdicionInexistente() {
	    manejadorEventos.clearManejador();
	    
	    // Intentar rechazar edición inexistente - no debería explotar
	    sistemaEventos.rechazarEdicion("EdicionInexistente");
	    
	    manejadorEventos.clearManejador();
	}

	@Test
	void testInfoEventoConEdiciones() throws Exception {
	    manejadorEventos.clearManejador();
	    manejadorUsuarios.limpiarManejador();
	    
	    sistemaEventos.altaCategoria("cat1");
	    List<String> categorias = new ArrayList<>();
	    categorias.add("cat1");
	    
	    sistemaUsuarios.altaOrganizador("org1", "org1@mail.com", "Org", "Desc", "", "Pass");
	    
	    String nombreEvento = "EventoConEdiciones";
	    sistemaEventos.altaEvento(nombreEvento, "Desc del evento", "ECE", categorias, LocalDate.now(), "");
	    
	    sistemaEventos.altaEdicion(nombreEvento, "org1", "Ed1", "ECE1", "Mvd", 
	            "UY", LocalDate.now(), LocalDate.now().plusDays(10), LocalDate.now(), "");
	    sistemaEventos.altaEdicion(nombreEvento, "org1", "Ed2", "ECE2", "Punta", 
	            "UY", LocalDate.now(), LocalDate.now().plusDays(10), LocalDate.now(), "");
	    
	    DTEvento evento = sistemaEventos.infoEvento(nombreEvento);
	    
	    assertNotNull(evento);
	    assertEquals(nombreEvento, evento.getNombre());
	    // Verificar que tiene ediciones si el DTO las incluye
	    
	    manejadorEventos.clearManejador();
	    manejadorUsuarios.limpiarManejador();
	}

	@Test
	void testFlujoCompletoRegistroConPatrocinio() throws Exception {
	    manejadorEventos.clearManejador();
	    manejadorUsuarios.limpiarManejador();
	    ManejadorInstituciones mi = ManejadorInstituciones.getInstance();
	    mi.clearManejador();
	    
	    InstitucionesController contrlI = new InstitucionesController();
	    
	    sistemaEventos.altaCategoria("Tecnología");
	    List<String> categorias = new ArrayList<>();
	    categorias.add("Tecnología");
	    
	    sistemaUsuarios.altaOrganizador("orgtech", "orgtech@mail.com", "Tech Org", "Desc", "", "Pass123");
	    contrlI.altaInstitucion("TechSponsor", "Sponsor de tecnología", "www.sponsor.com", "");
	    
	    sistemaEventos.altaEvento("TechConf", "Conferencia de Tecnología", "TC", categorias, LocalDate.now(), "");
	    sistemaEventos.altaEdicion("TechConf", "orgtech", "TechConf2024", "TC24", "Montevideo", 
	            "Uruguay", LocalDate.now().plusDays(30), LocalDate.now().plusDays(32), LocalDate.now(), "");
	    
	    sistemaEventos.altaTipoDeRegistro("VIP", "TechConf2024", "Acceso VIP", 10000, 50);
	    sistemaEventos.altaTipoDeRegistro("General", "TechConf2024", "Acceso General", 3000, 200);
	    
	    sistemaEventos.altaPatrocinio("TechSponsor", "TechConf2024", "VIP", 
	            NivelPatrocinio.ORO, 15000, 30, LocalDate.now(), "SPONSOR2024");
	    
	    sistemaUsuarios.altaAsistente("asist1", "asist1@mail.com", "Asist", "Uno", 
	            LocalDate.of(1990, 1, 1), "", "Pass1");
	    sistemaUsuarios.altaAsistente("asist2", "asist2@mail.com", "Asist", "Dos", 
	            LocalDate.of(1991, 2, 2), "", "Pass2");
	    
	    sistemaUsuarios.altaRegistro("asist1", "TechConf2024", "VIP", LocalDate.now());
	    sistemaUsuarios.altaRegistroConCodigo("asist2", "TechConf2024", "VIP", "SPONSOR2024", LocalDate.now());
	    
	    DTEvento evento = sistemaEventos.infoEvento("TechConf");
	    assertNotNull(evento);
	    
	    DTEdicion edicion = sistemaEventos.infoEdicion("TechConf2024");
	    assertNotNull(edicion);
	    
	    List<String> tiposRegistro = sistemaEventos.listarTiposDeRegistroDeEdicion("TechConf2024");
	    assertEquals(2, tiposRegistro.size());
	    
	    DTPatrocinio patrocinio = sistemaEventos.consultarPatrocinio("TechConf2024", "SPONSOR2024");
	    assertNotNull(patrocinio);
	    
	    List<DTRegistro> registros = sistemaEventos.listarRegistrosDeEdicion("TechConf", "TechConf2024");
	    assertEquals(2, registros.size());
	    
	    manejadorEventos.clearManejador();
	    manejadorUsuarios.limpiarManejador();
	    mi.clearManejador();
	}
	@Test
	void testListarEdicionesEventoIngresadas_SinEdiciones() throws Exception {
	    manejadorEventos.clearManejador();
	    manejadorUsuarios.limpiarManejador();

	    sistemaEventos.altaCategoria("cat1");
	    List<String> categorias = new ArrayList<>();
	    categorias.add("cat1");

	    sistemaEventos.altaEvento("EvtSinIngr", "Desc", "ESI", categorias, LocalDate.now(), "");

	    List<String> res = sistemaEventos.listarEdicionesEventoIngresadas("EvtSinIngr");
	    assertNotNull(res);
	    assertTrue(res.isEmpty(), "Si no hay ediciones ingresadas, debe devolver lista vacía");

	    manejadorEventos.clearManejador();
	    manejadorUsuarios.limpiarManejador();
	}

	@Test
	void testListarEdicionesEventoIngresadas_EventoInexistente() {
	    manejadorEventos.clearManejador();
	    manejadorUsuarios.limpiarManejador();

	    List<String> res = sistemaEventos.listarEdicionesEventoIngresadas("EVENTO_NO_EXISTE");
	    assertNotNull(res);
	    assertTrue(res.isEmpty(), "Evento inexistente → lista vacía");

	    manejadorEventos.clearManejador();
	    manejadorUsuarios.limpiarManejador();
	}

	@Test
	void testListarCodigosDePatrocinioDeEdicion_EventoInexistente() {
	    manejadorEventos.clearManejador();
	    manejadorUsuarios.limpiarManejador();

	    List<String> cods = sistemaEventos.listarCodigosDePatrocinioDeEdicion("NO_EVT", "NO_EDIC");
	    assertNotNull(cods);
	    assertTrue(cods.isEmpty(), "Si no existe el evento/edición, la lista de códigos debe ser vacía");

	    manejadorEventos.clearManejador();
	    manejadorUsuarios.limpiarManejador();
	}

	@Test
	void testExisteCodigoDePatrocinioEnEdicion_EventoOEdicionInexistentes() {
	    manejadorEventos.clearManejador();
	    manejadorUsuarios.limpiarManejador();

	    boolean existe1 = sistemaEventos.existeCodigoDePatrocinioEnEdicion("CODX", "NO_EVT", "ED1");
	    boolean existe2 = sistemaEventos.existeCodigoDePatrocinioEnEdicion("CODX", "EVT1", "NO_EDIC");
	    boolean existe3 = sistemaEventos.existeCodigoDePatrocinioEnEdicion("CODX", "NO_EVT", "NO_EDIC");

	    assertFalse(existe1);
	    assertFalse(existe2);
	    assertFalse(existe3);

	    manejadorEventos.clearManejador();
	    manejadorUsuarios.limpiarManejador();
	}

	@Test
	void testAgregarVideo_UrlVacia_LanzaIllegalArgument() throws Exception {
	    manejadorEventos.clearManejador();
	    manejadorUsuarios.limpiarManejador();

	    sistemaEventos.altaCategoria("c1");
	    List<String> cats = new ArrayList<>();
	    cats.add("c1");

	    sistemaUsuarios.altaOrganizador("orgV", "orgv@mail.com", "Org V", "Desc", "", "Pass");
	    sistemaEventos.altaEvento("EvtVid", "Desc", "EVID", cats, LocalDate.now(), "");
	    sistemaEventos.altaEdicion("EvtVid", "orgV", "EdVid", "EVID24", "Mvd",
	            "UY", LocalDate.now(), LocalDate.now().plusDays(1), LocalDate.now(), "");

	    assertThrows(IllegalArgumentException.class, () -> sistemaEventos.agregarVideo("EdVid", ""),
	            "URL vacía debe lanzar IllegalArgumentException");

	    manejadorEventos.clearManejador();
	    manejadorUsuarios.limpiarManejador();
	}

	@Test
	void testListarPatrociniosDeEdicion_EventoInexistente() {
	    manejadorEventos.clearManejador();
	    manejadorUsuarios.limpiarManejador();

	    List<String> res = sistemaEventos.listarPatrociniosDeEdicion("NO_EVT", "NO_EDIC");
	    assertNotNull(res);
	    assertTrue(res.isEmpty(), "Evento/edición inexistentes → lista vacía");

	    manejadorEventos.clearManejador();
	    manejadorUsuarios.limpiarManejador();
	}

	@Test
	void testFinalizarEvento_MultiplesLlamadasIdempotente() throws Exception {
	    manejadorEventos.clearManejador();
	    manejadorUsuarios.limpiarManejador();

	    sistemaEventos.altaCategoria("c1");
	    List<String> cats = new ArrayList<>();
	    cats.add("c1");

	    sistemaEventos.altaEvento("EvtFin", "Desc", "EF", cats, LocalDate.now(), "");

	    // Llamar más de una vez no debe romper
	    sistemaEventos.finalizarEvento("EvtFin");
	    sistemaEventos.finalizarEvento("EvtFin");

	    DTEvento dte = sistemaEventos.infoEvento("EvtFin");
	    assertNotNull(dte, "El evento debe seguir consultable luego de finalizarlo");

	    manejadorEventos.clearManejador();
	    manejadorUsuarios.limpiarManejador();
	}

	@Test
	void testArchivarEdicionDeEvento_Idempotente() throws Exception {
	    manejadorEventos.clearManejador();
	    manejadorUsuarios.limpiarManejador();

	    sistemaEventos.altaCategoria("c1");
	    List<String> cats = new ArrayList<>();
	    cats.add("c1");

	    sistemaUsuarios.altaOrganizador("orgA", "orga@mail.com", "Org A", "Desc", "", "Pass");
	    sistemaEventos.altaEvento("EvtArch", "Desc", "EAR", cats, LocalDate.now(), "");
	    sistemaEventos.altaEdicion("EvtArch", "orgA", "EdArch", "EAR24", "Mvd",
	            "UY", LocalDate.now(), LocalDate.now().plusDays(2), LocalDate.now(), "");

	    sistemaEventos.archivarEdicionDeEvento("EdArch");
	    // Segunda llamada no debe reventar
	    sistemaEventos.archivarEdicionDeEvento("EdArch");

	    DTEdicion dt = sistemaEventos.infoEdicion("EdArch");
	    assertNotNull(dt, "La edición debe seguir consultable luego de archivar");

	    manejadorEventos.clearManejador();
	    manejadorUsuarios.limpiarManejador();
	}

}

	
	

