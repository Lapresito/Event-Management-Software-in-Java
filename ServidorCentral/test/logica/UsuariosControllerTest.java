package logica;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import excepciones.EmailRepetidoException;
import excepciones.NicknameRepetidoException;
import excepciones.UsuarioNoExisteException;

class UsuariosControllerTest {
	private IUsuariosController controladorU = new UsuariosController();
	private ManejadorUsuarios mu = ManejadorUsuarios.getinstance();
	private ManejadorEventos me = ManejadorEventos.getInstancia();
	private IInstitucionesController controladorI = new InstitucionesController();


	@Test
	void testAltaInstitucionExitoso() throws Exception {
		ManejadorInstituciones mi = ManejadorInstituciones.getInstance();
		controladorI.altaInstitucion("Institucion1", "DescInstitucion", "webInstitucion", "");
		Institucion inst = mi.obtenerInstitucion("Institucion1");
		assertEquals("Institucion1", inst.getNombre());
	}

	@Test
	void testAltaOrganizadorExitoso() throws Exception {
		controladorU.altaOrganizador("nick1", "mail1@dom.com", "Pepe", "Organizador", "", "PasswordOrg");
		assertNotNull(mu.getOrganizador("nick1"));
	}

	@Test
	void testEsOrganizador() throws Exception {
		controladorU.altaOrganizador("nickesorg", "mailesorg@dom.com", "Pepe", "Organizador", "", "PasswordOrg");
		assertTrue(controladorU.esOrganizador("nickesorg"));
	}

	@Test
	void testIngresarSitioWeb() throws Exception {
		Organizador org = mu.getOrganizador("nick1");
		controladorU.ingresarSitioWeb(org.getNickname(), "miWeb");
		assertEquals("miWeb", org.getSitioWeb());
	}
	@Test
	void testAltaOrganizadorNicknameRepetido() throws Exception {
		controladorU.altaOrganizador("nick2", "mail2@dom.com", "Pepe", "Organizador", "", "PasswordOrg");
		assertThrows(NicknameRepetidoException.class, () -> {
			controladorU.altaOrganizador("nick2", "otro@dom.com", "Otro", "desc", "img", "PasswordOrg2" ); // Se que no es unica la img y la pass, pero por prolijidad
		});
	}


	@Test
	void testAltaOrganizadorEmailRepetido() throws Exception {
		controladorU.altaOrganizador("nick3", "mail3@dom.com", "Pepe", "Organizador", "", "PasswordOrg");
		assertThrows(EmailRepetidoException.class, () -> {
			controladorU.altaOrganizador("nick4", "mail3@dom.com", "Otro", "desc", "img", "PasswordOrg2");
		});
	}
	@Test
	void testAltaAsistenteExitoso() throws Exception {
		controladorU.altaAsistente("nickasist1", "mailasist1@dom.com", "NombreAsist1", "Apellido",
				LocalDate.now().minusYears(20), "", "PasswordAsis");
		assertNotNull(mu.getAsistente("nickasist1"));
	}

	@Test
	void testEsAsistente() throws Exception {
		controladorU.altaAsistente("nickesasist", "mailesasist1@dom.com", "NombreAsist1", "Apellido",
				LocalDate.now().minusYears(20), "", "PasswordAsis");
		assertTrue(controladorU.esAsistente("nickesasist"));
	}

	@Test
	void testIngresarInstitucion() throws Exception {
		controladorU.altaAsistente("nickasist5", "mailasist5@dom.com", "NombreAsist1", "Apellido",
				LocalDate.now().minusYears(20), "", "PasswordAsis1");
		Asistente asist = mu.getAsistente("nickasist5");
		controladorI.altaInstitucion("Institucion2", "DescInstitucion", "webInstitucion", "");

		controladorU.ingresarInstitucion(asist.getNickname(), "Institucion2");
		assertEquals("Institucion2", asist.getInstitucion().getNombre());
	}

	@Test
	void testAltaAsistenteNicknameRepetido() throws Exception {
		controladorU.altaAsistente("nickasist2", "mailasist2@dom.com", "NombreAsist2", "Apelllido",
				LocalDate.now().minusYears(20), "", "PasswordAsis2");
		assertThrows(NicknameRepetidoException.class, () -> {
			controladorU.altaAsistente("nickasist2", "otroasist2@dom.com", "OtroNombreAsist2", "Gonzalez",
					LocalDate.now().minusYears(20), "", "OtroPasswordAsis");
		});
	}
	
	@Test
	void testEditarAsistente() throws Exception{
		mu.limpiarManejador();
		Asistente asistente = new Asistente("nickgetasist1", "correo@dom.com", "Pepe", "Gómez",
				LocalDate.of(2000, 1, 1), "", "PasswordAsis");
		mu.addAsistente(asistente);
		controladorU.editarAsistente("nickgetasist1","Nuevonombre", "nuevoapellido", LocalDate.now().minusYears(2), "", "PasswordAsis");
		assertEquals("nuevoapellido", asistente.getApellido());
		assertEquals("Nuevonombre", asistente.getNombre());
		assertEquals(LocalDate.now().minusYears(2), asistente.getNacimiento());

	}
	
	@Test
	void testEditarOrganizador() throws Exception{
		mu.limpiarManejador();
		Organizador org = new Organizador("nick1", "mail1@dom.com", "Pepe", "Organizador","", "PasswordPepe");
		mu.addOrganizador(org);
		controladorU.editarOrganizador("nick1","Nuevonombre", "nuevaDesc", "nuevaWeb","", "PasswordPepe" );
		assertEquals("nuevaWeb", org.getSitioWeb());
		assertEquals("Nuevonombre", org.getNombre());
		assertEquals("nuevaDesc", org.getDescripcion());

	}

	@Test
	void testGetAsistenteConInstitucionYRegistros() throws Exception {
		mu.limpiarManejador();

		Organizador org = new Organizador("nick1", "mail1@dom.com", "Pepe", "Organizador","", "PasswordPepe");
		Institucion inst = new Institucion("Facultad de Ingeniería", "www.fing.edu.uy", "desc", "");
		Asistente asistente = new Asistente("nickgetasist1", "correo@dom.com", "Pepe", "Gómez",
				LocalDate.of(2000, 1, 1),"", "PasswordAsis");
		asistente.setInstitucion(inst);

		Edicion ed = new Edicion("nombreEdicion", null, org, "sigla1", LocalDate.now(), LocalDate.now(), "ciudad",
				"pais", LocalDate.now(),"");
		Registro reg = new Registro(ed, LocalDate.now(), 0);
		asistente.asociarRegistro(ed.getNombre(), reg);

		mu.addAsistente(asistente);

		// Act
		DTAsistente dto = controladorU.getAsistente("nickgetasist1");

		// Assert
		assertNotNull(dto);
		assertEquals("Gómez", dto.getApellido());
		assertEquals("Facultad de Ingeniería", dto.getInstitucion());
		assertEquals(1, dto.getRegistros().length);
		assertEquals("nombreEdicion", dto.getRegistros()[0]);
	}
	
	@Test
    void testGetOrganizadorExistente() {
        Organizador org = new Organizador("nickOrg", "mail@dom.com", "Juan", "Descripcion","", "PasswordJuan");
        org.setSitioWeb("www.organizador.com");

        Evento ev = new Evento("nombreEv1","sigla", "descripcion", null, LocalDate.now(),"");
        Edicion ed = new Edicion("nombreEdicion", ev, org, "sigla1", LocalDate.now(), LocalDate.now(), "ciudad",
				"pais", LocalDate.now(), "");
        org.getEdiciones().put("nombreEdicion", ed);

        mu.getOrganizadores().put("nickOrg", org);

        DTOrganizador dto = controladorU.getOrganizador("nickOrg");

        assertNotNull(dto);
        assertEquals("nickOrg", dto.getNickname());
        assertEquals("mail@dom.com", dto.getEmail());
        assertEquals("Juan", dto.getNombre());
        assertEquals("Descripcion", dto.getDescripcion());
        assertEquals("www.organizador.com", dto.getSitioWeb());
        assertEquals(1, dto.getEdiciones().length);
        assertEquals("nombreEdicion", dto.getEdiciones()[0].getNombreEdicion());
    }

	@Test
	void testGetAsistenteSinInstitucion() {
		Asistente asistente = new Asistente("nickasistsininst", "correosininst2@dom.com", "Ana", "Pérez",
				LocalDate.of(1999, 5, 20),"", "PasswordPepe");
		mu.addAsistente(asistente);

		DTAsistente dto = controladorU.getAsistente("nickasistsininst");

		assertNotNull(dto);
		assertEquals("", dto.getInstitucion());
	}

	@Test
	void testAltaAsistenteEmailRepetido() throws Exception {
		controladorU.altaAsistente("nickasist3", "mailasist3@dom.com", "NombreAsist3", "Apellido",
				LocalDate.now().minusYears(20),"", "PasswordAsis");
		assertThrows(EmailRepetidoException.class, () -> {
			controladorU.altaAsistente("nickasist4", "mailasist3@dom.com", "NombreAsist3", "Apellido",
					LocalDate.now().minusYears(20),"", "PasswordAsis");
		});
	}
	
	@Test
    void testGetUsuariosConUsuarios() throws Exception {
		mu.limpiarManejador();
        Asistente u1 = new Asistente("nickasistgetusuarios", "correoswefwst2@dom.com", "Ana", "Pérez",
				LocalDate.of(1999, 5, 20),"", "PasswordAsis");
        Organizador u2 = new Organizador("nickorggetusuarios", "mailwef1@dom.com", "Pepe", "Organizador","", "PasswordOrg");

        mu.addAsistente(u1);
        mu.addOrganizador(u2);

        DTUsuario[] dtus = controladorU.getUsuarios();

        assertNotNull(dtus);
        assertEquals(2, dtus.length);

        assertEquals("nickorggetusuarios", dtus[0].getNickname());
        assertEquals("mailwef1@dom.com", dtus[0].getEmail());
        assertEquals("Pepe", dtus[0].getNombre());

        assertEquals("nickasistgetusuarios", dtus[1].getNickname());
        assertEquals("correoswefwst2@dom.com", dtus[1].getEmail());
        assertEquals("Ana", dtus[1].getNombre());
    }
	
	@Test
    void testInfoRegistroDeAsistenteOk() {
		mu.limpiarManejador();
		me.clearManejador();
        Asistente asistente = new Asistente("nickasistinforegistro", "correoswefwst2@dom.com", "Ana", "Pérez",
				LocalDate.of(1999, 5, 20),"", "PasswordAsis");
        mu.addAsistente(asistente);

        Evento ev = new Evento("nombreEv1","sigla", "descripcion", null, LocalDate.now(), "");
        Edicion ed = new Edicion("nombreEdicion", ev, null, "sigla1", LocalDate.now(), LocalDate.now(), "ciudad",
				"pais", LocalDate.now(), "");
        
        me.agregarEdicion(ed);
        me.agregarEvento(ev);

        Registro reg = new Registro(ed, LocalDate.of(2024, 5, 10), 0);
        asistente.asociarRegistro("nombreEdicion", reg);

        DTRegistro info = controladorU.infoRegistroDeAsistente("nickasistinforegistro", "nombreEdicion");

        assertNotNull(info);
        assertEquals("nombreEdicion", info.getNombreEdicion());
        assertEquals("nombreEv1", info.getNombreEvento());
        assertEquals(0, info.getCosto());
        assertEquals(LocalDate.of(2024, 5, 10), info.getFechaAlta());
    }
	
	@Test
    void testListarRegistrosAsistenteConRegistros() {
		mu.limpiarManejador();
		
        // Crear asistente con registros
        Asistente asistente = new Asistente("nickasistlistar", "correoswefwst2@dom.com", "Ana", "Pérez",
				LocalDate.of(1999, 5, 20),"", "PasswordAsis");
        
        Edicion ed = new Edicion("nombreEdicion", null, null, "sigla1", LocalDate.now(), LocalDate.now(), "ciudad",
				"pais", LocalDate.now(), "");
        
        me.agregarEdicion(ed);
        
        Edicion ed2 = new Edicion("nombreEdicion2", null, null, "sigla1", LocalDate.now(), LocalDate.now(), "ciudad",
				"pais", LocalDate.now(), "");
        
        me.agregarEdicion(ed2);
        

        Registro reg1 = new Registro(ed, LocalDate.of(2024, 5, 10), 0);
        asistente.asociarRegistro("nombreEdicion", reg1);
        Registro reg2 = new Registro(ed2, LocalDate.of(2024, 5, 10), 0);
        asistente.asociarRegistro("nombreEdicion2", reg2);
                     
        mu.addAsistente(asistente);

        List<String> resultado = controladorU.listarRegistrosAsistente("nickasistlistar");

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertTrue(resultado.contains(reg1.toString()));
        assertTrue(resultado.contains(reg2.toString()));
    }
	
	// nuevos test 
	@Test
	void testGetAsistenteNoExiste() {
	    assertThrows(UsuarioNoExisteException.class, () -> {
	        controladorU.getAsistente("nicknoxiste");
	    });
	}

	@Test
	void testGetOrganizadorNoExiste() {
	    assertThrows(UsuarioNoExisteException.class, () -> {
	        controladorU.getOrganizador("nicknoxiste");
	    });
	}

	@Test
	void testListarRegistrosAsistenteSinRegistros() {
	    Asistente asistente = new Asistente("nicksinreg", "sin@reg.com", "Sin", "Registros",
	            LocalDate.of(1999, 5, 20), "", "Password123");
	    mu.addAsistente(asistente);
	    
	    List<String> resultado = controladorU.listarRegistrosAsistente("nicksinreg");
	    
	    assertNotNull(resultado);
	    assertEquals(0, resultado.size());
	}
	@Test
	void testAltaRegistroExitoso() throws Exception {
	    mu.limpiarManejador();
	    me.clearManejador();
	    
	    Asistente asistente = new Asistente("nickaltareg", "correo@dom.com", "Ana", "Pérez",
	            LocalDate.of(1999, 5, 20), "", "Password123");
	    mu.addAsistente(asistente);
	    
	    Organizador org = new Organizador("nickorg", "org@dom.com", "Juan", "Desc", "", "PassOrg");
	    mu.addOrganizador(org);
	    
	    Evento ev = new Evento("eventoTest", "ET", "descripcion", null, LocalDate.now(), "");
	    Edicion ed = new Edicion("edicionTest", ev, org, "ET2024", LocalDate.now(), 
	            LocalDate.now().plusDays(30), "Montevideo", "Uruguay", LocalDate.now(), "");
	    
	    me.agregarEvento(ev);
	    me.agregarEdicion(ed);
	    
	    controladorU.altaRegistro("nickaltareg", "edicionTest", "General", LocalDate.now());
	    
	    DTAsistente dto = controladorU.getAsistente("nickaltareg");
	    assertEquals(1, dto.getRegistros().length);
	}

	@Test
	void testListarOrganizadoresConDatos() throws Exception {
	    mu.limpiarManejador();
	    
	    Organizador org1 = new Organizador("nickorg1", "org1@dom.com", "Juan", "Desc1", "", "Pass1");
	    Organizador org2 = new Organizador("nickorg2", "org2@dom.com", "Pedro", "Desc2", "", "Pass2");
	    
	    mu.addOrganizador(org1);
	    mu.addOrganizador(org2);
	    
	    List<DTOrganizador> resultado = controladorU.listarOrganizadores();
	    
	    assertNotNull(resultado);
	    assertEquals(2, resultado.size());
	}

	@Test
	void testListarOrganizadoresVacio() {
	    mu.limpiarManejador();
	    
	    List<DTOrganizador> resultado = controladorU.listarOrganizadores();
	    
	    assertNotNull(resultado);
	    assertEquals(0, resultado.size());
	}

	@Test
	void testListarAsistentesConDatos() throws Exception {
	    mu.limpiarManejador();
	    
	    Asistente asist1 = new Asistente("nickasist1", "asist1@dom.com", "Ana", "López",
	            LocalDate.of(1995, 3, 15), "", "Pass1");
	    Asistente asist2 = new Asistente("nickasist2", "asist2@dom.com", "Maria", "González",
	            LocalDate.of(1998, 7, 22), "", "Pass2");
	    
	    mu.addAsistente(asist1);
	    mu.addAsistente(asist2);
	    
	    List<DTAsistente> resultado = controladorU.listarAsistentes();
	    
	    assertNotNull(resultado);
	    assertEquals(2, resultado.size());
	}

	@Test
	void testListarAsistentesVacio() {
	    mu.limpiarManejador();
	    
	    List<DTAsistente> resultado = controladorU.listarAsistentes();
	    
	    assertNotNull(resultado);
	    assertEquals(0, resultado.size());
	}

	@Test
	void testListarRegistrosYEdicionAsistenteConDatos() {
	    mu.limpiarManejador();
	    me.clearManejador();
	    
	    Asistente asistente = new Asistente("nickasistmap", "correo@dom.com", "Ana", "Pérez",
	            LocalDate.of(1999, 5, 20), "", "Password123");
	    
	    Evento ev = new Evento("evento1", "EV1", "desc", null, LocalDate.now(), "");
	    Edicion ed1 = new Edicion("edicion1", ev, null, "ED1", LocalDate.now(), 
	            LocalDate.now(), "ciudad", "pais", LocalDate.now(), "");
	    Edicion ed2 = new Edicion("edicion2", ev, null, "ED2", LocalDate.now(), 
	            LocalDate.now(), "ciudad", "pais", LocalDate.now(), "");
	    
	    me.agregarEvento(ev);
	    me.agregarEdicion(ed1);
	    me.agregarEdicion(ed2);
	    
	    Registro reg1 = new Registro(ed1, LocalDate.now(), 100);
	    Registro reg2 = new Registro(ed2, LocalDate.now(), 200);
	    
	    asistente.asociarRegistro("edicion1", reg1);
	    asistente.asociarRegistro("edicion2", reg2);
	    
	    mu.addAsistente(asistente);
	    
	    Map<String, String> resultado = controladorU.listarRegistrosYEdicionAsistente("nickasistmap");
	    
	    assertNotNull(resultado);
	    assertEquals(2, resultado.size());
	    assertTrue(resultado.containsKey("edicion1"));
	    assertTrue(resultado.containsKey("edicion2"));
	}

	@Test
	void testAutenticarUsuarioExitoso() throws Exception {
	    mu.limpiarManejador();
	    
	    controladorU.altaOrganizador("nickauth", "auth@dom.com", "Usuario", "Desc", "", "password123");
	    
	    boolean resultado = controladorU.autenticarUsuario("auth@dom.com", "password123");
	    
	    assertTrue(resultado);
	}

	@Test
	void testAutenticarUsuarioPasswordIncorrecta() throws Exception {
	    mu.limpiarManejador();
	    
	    controladorU.altaOrganizador("nickauth2", "auth2@dom.com", "Usuario", "Desc", "", "password123");
	    
	    boolean resultado = controladorU.autenticarUsuario("auth2@dom.com", "passwordincorrecta");
	    
	    assertFalse(resultado);
	}

	@Test
	void testAutenticarUsuarioNoExiste() {
	    assertThrows(UsuarioNoExisteException.class, () -> {
	        controladorU.autenticarUsuario("noexiste@dom.com", "password");
	    });
	}

	@Test
	void testObtenerUsuarioEmailExitoso() throws Exception {
	    mu.limpiarManejador();
	    
	    controladorU.altaAsistente("nickemail", "email@dom.com", "Nombre", "Apellido",
	            LocalDate.of(1990, 1, 1), "", "Pass123");
	    
	    DTUsuario usuario = controladorU.obtenerUsuarioEmail("email@dom.com");
	    
	    assertNotNull(usuario);
	    assertEquals("nickemail", usuario.getNickname());
	    assertEquals("email@dom.com", usuario.getEmail());
	}

	@Test
	void testObtenerUsuarioEmailNoExiste() {
	    assertThrows(UsuarioNoExisteException.class, () -> {
	        controladorU.obtenerUsuarioEmail("noexiste@dom.com");
	    });
	}

	@Test
	void testObtenerTipoUsuarioOrganizador() throws Exception {
	    mu.limpiarManejador();
	    
	    controladorU.altaOrganizador("nicktipo1", "tipo1@dom.com", "Org", "Desc", "", "Pass123");
	    
	    String tipo = controladorU.obtenerTipoUsuario("nicktipo1");
	    
	    assertEquals("Organizador", tipo);
	}

	@Test
	void testObtenerTipoUsuarioAsistente() throws Exception {
	    mu.limpiarManejador();
	    
	    controladorU.altaAsistente("nicktipo2", "tipo2@dom.com", "Asist", "Apellido",
	            LocalDate.of(1995, 5, 5), "", "Pass123");
	    
	    String tipo = controladorU.obtenerTipoUsuario("nicktipo2");
	    
	    assertEquals("Asistente", tipo);
	}

	@Test
	void testListarEdicionesAceptadasDeOrganizador() throws Exception {
	    mu.limpiarManejador();
	    me.clearManejador();
	    
	    Organizador org = new Organizador("nickediciones", "ediciones@dom.com", "Org", "Desc", "", "Pass");
	    mu.addOrganizador(org);
	    
	    Evento ev = new Evento("evento1", "EV1", "desc", null, LocalDate.now(), "");
	    Edicion ed = new Edicion("edicionAceptada", ev, org, "EA2024", LocalDate.now(), 
	            LocalDate.now(), "ciudad", "pais", LocalDate.now(), "");
	    ed.setEstado(EstadoEdicion.ACEPTADA);
	    
	    org.getEdiciones().put("edicionAceptada", ed);
	    me.agregarEvento(ev);
	    me.agregarEdicion(ed);
	    
	    List<String> resultado = controladorU.listarEdicionesAceptadasDeOrganizador("nickediciones");
	    
	    assertNotNull(resultado);
	    assertTrue(resultado.size() > 0);
	}

	@Test
	void testAltaRegistroConCodigoExitoso() throws Exception {
	    mu.limpiarManejador();
	    me.clearManejador();
	    
	    Asistente asistente = new Asistente("nickcodigo", "codigo@dom.com", "Ana", "Pérez",
	            LocalDate.of(1999, 5, 20), "", "Password123");
	    mu.addAsistente(asistente);
	    
	    Organizador org = new Organizador("nickorgcodigo", "orgcodigo@dom.com", "Juan", "Desc", "", "PassOrg");
	    mu.addOrganizador(org);
	    
	    Evento ev = new Evento("eventoCodigoTest", "ECT", "descripcion", null, LocalDate.now(), "");
	    Edicion ed = new Edicion("edicionCodigoTest", ev, org, "ECT2024", LocalDate.now(), 
	            LocalDate.now().plusDays(30), "Montevideo", "Uruguay", LocalDate.now(), "");
	    
	    me.agregarEvento(ev);
	    me.agregarEdicion(ed);
	    
	    controladorU.altaRegistroConCodigo("nickcodigo", "edicionCodigoTest", "Premium", "CODIGO123", LocalDate.now());
	    
	    DTAsistente dto = controladorU.getAsistente("nickcodigo");
	    assertEquals(1, dto.getRegistros().length);
	}

	@Test
	void testListarEdicionesDeOrganizador() throws Exception {
	    mu.limpiarManejador();
	    me.clearManejador();
	    
	    Organizador org = new Organizador("nicklistediciones", "listediciones@dom.com", "Org", "Desc", "", "Pass");
	    mu.addOrganizador(org);
	    
	    Evento ev = new Evento("evento1", "EV1", "desc", null, LocalDate.now(), "");
	    Edicion ed1 = new Edicion("edicion1", ev, org, "ED1", LocalDate.now(), 
	            LocalDate.now(), "ciudad", "pais", LocalDate.now(), "");
	    Edicion ed2 = new Edicion("edicion2", ev, org, "ED2", LocalDate.now(), 
	            LocalDate.now(), "ciudad", "pais", LocalDate.now(), "");
	    
	    org.getEdiciones().put("edicion1", ed1);
	    org.getEdiciones().put("edicion2", ed2);
	    
	    me.agregarEvento(ev);
	    me.agregarEdicion(ed1);
	    me.agregarEdicion(ed2);
	    
	    List<String> resultado = controladorU.listarEdicionesDeOrganizador("nicklistediciones");
	    
	    assertNotNull(resultado);
	    assertEquals(2, resultado.size());
	}

	@Test
	void testSeguirAUsuarioExitoso() throws Exception {
	    mu.limpiarManejador();
	    
	    Asistente seguidor = new Asistente("seguidor", "seguidor@dom.com", "Seguidor", "Apellido",
	            LocalDate.of(1995, 1, 1), "", "Pass1");
	    Asistente seguido = new Asistente("seguido", "seguido@dom.com", "Seguido", "Apellido",
	            LocalDate.of(1996, 2, 2), "", "Pass2");
	    
	    mu.addAsistente(seguidor);
	    mu.addAsistente(seguido);
	    
	    boolean resultado = controladorU.seguirAUsuario("seguido", "seguidor");
	    
	    assertTrue(resultado);
	}

	@Test
	void testDejarDeSeguirAUsuarioExitoso() throws Exception {
	    mu.limpiarManejador();
	    
	    Asistente seguidor = new Asistente("seguidor2", "seguidor2@dom.com", "Seguidor", "Apellido",
	            LocalDate.of(1995, 1, 1), "", "Pass1");
	    Asistente seguido = new Asistente("seguido2", "seguido2@dom.com", "Seguido", "Apellido",
	            LocalDate.of(1996, 2, 2), "", "Pass2");
	    
	    mu.addAsistente(seguidor);
	    mu.addAsistente(seguido);
	    
	    controladorU.seguirAUsuario("seguido2", "seguidor2");
	    boolean resultado = controladorU.dejarDeSeguirAUsuario("seguido2", "seguidor2");
	    
	    assertTrue(resultado);
	}

	@Test
	void testGetSeguidoresDeUsuario() throws Exception {
	    mu.limpiarManejador();
	    
	    Asistente usuario = new Asistente("usuario", "usuario@dom.com", "Usuario", "Apellido",
	            LocalDate.of(1995, 1, 1), "", "Pass1");
	    Asistente seguidor1 = new Asistente("seguidor1", "seguidor1@dom.com", "Seguidor1", "Apellido",
	            LocalDate.of(1996, 2, 2), "", "Pass2");
	    
	    mu.addAsistente(usuario);
	    mu.addAsistente(seguidor1);
	    
	    controladorU.seguirAUsuario("usuario", "seguidor1");
	    
	    List<DTUsuario> seguidores = controladorU.getSeguidoresDeUsuario("usuario");
	    
	    assertNotNull(seguidores);
	    assertEquals(1, seguidores.size());
	}

	@Test
	void testGetSeguidosDeUsuario() throws Exception {
	    mu.limpiarManejador();
	    
	    Asistente usuario = new Asistente("usuarioseguidos", "usuarioseguidos@dom.com", "Usuario", "Apellido",
	            LocalDate.of(1995, 1, 1), "", "Pass1");
	    Asistente seguido1 = new Asistente("seguido1", "seguidouno@dom.com", "Seguido1", "Apellido",
	            LocalDate.of(1996, 2, 2), "", "Pass2");
	    
	    mu.addAsistente(usuario);
	    mu.addAsistente(seguido1);
	    
	    controladorU.seguirAUsuario("seguido1", "usuarioseguidos");
	    
	    List<DTUsuario> seguidos = controladorU.getSeguidosDeUsuario("usuarioseguidos");
	    
	    assertNotNull(seguidos);
	    assertEquals(1, seguidos.size());
	}

	@Test
	void testRegistrarAsistenciaExitoso() throws Exception {
	    mu.limpiarManejador();
	    me.clearManejador();
	    
	    Asistente asistente = new Asistente("nickasistencia", "asistencia@dom.com", "Ana", "Pérez",
	            LocalDate.of(1999, 5, 20), "", "Password123");
	    mu.addAsistente(asistente);
	    
	    Organizador org = new Organizador("nickorgasist", "orgasist@dom.com", "Juan", "Desc", "", "PassOrg");
	    mu.addOrganizador(org);
	    
	    Evento ev = new Evento("eventoAsist", "EA", "descripcion", null, LocalDate.now(), "");
	    Edicion ed = new Edicion("edicionAsist", ev, org, "EA2024", LocalDate.now(), 
	            LocalDate.now().plusDays(30), "Montevideo", "Uruguay", LocalDate.now(), "");
	    
	    me.agregarEvento(ev);
	    me.agregarEdicion(ed);
	    
	    Registro reg = new Registro(ed, LocalDate.now(), 0);
	    asistente.asociarRegistro("edicionAsist", reg);
	    
	    controladorU.registrarAsistencia("nickasistencia", "edicionAsist");
	    
	    // Verificar que se registró la asistencia
	    DTRegistro registro = controladorU.infoRegistroDeAsistente("nickasistencia", "edicionAsist");
	    assertNotNull(registro);
	}
	@Test
	void testEsOrganizadorSiendoAsistente() throws Exception {
		controladorU.altaAsistente("nickNoOrg", "mailnoorg@dom.com", "Pepe", "Asistente",
				LocalDate.now().minusYears(20), "", "PasswordAsis");
		assertFalse(controladorU.esOrganizador("nickNoOrg"));
	}

	@Test
	void testEsAsistenteSiendoOrganizador() throws Exception {
		controladorU.altaOrganizador("nickNoAsist", "mailnoasist@dom.com", "Pepe", "Organizador", "", "PasswordOrg");
		assertFalse(controladorU.esAsistente("nickNoAsist"));
	}

	@Test
	void testEsOrganizadorUsuarioNoExiste() {
		assertFalse(controladorU.esOrganizador("nickNoExiste"));
	}

	@Test
	void testEsAsistenteUsuarioNoExiste() {
		assertFalse(controladorU.esAsistente("nickNoExiste"));
	}
    
    @Test
    void testAltaOrganizadorNicknameVacio() {
        assertThrows(Exception.class, () -> {
            controladorU.altaOrganizador("", "mail@dom.com", "Nombre", "Desc", "", "Pass123");
        });
    }

    @Test
    void testAltaOrganizadorNicknameNull() {
        assertThrows(Exception.class, () -> {
            controladorU.altaOrganizador(null, "mail@dom.com", "Nombre", "Desc", "", "Pass123");
        });
    }

    @Test
    void testAltaOrganizadorNombreVacio() {
        assertThrows(Exception.class, () -> {
            controladorU.altaOrganizador("nick", "mail@dom.com", "", "Desc", "", "Pass123");
        });
    }

    @Test
    void testAltaOrganizadorDescripcionVacia() {
        assertThrows(Exception.class, () -> {
            controladorU.altaOrganizador("nick", "mail@dom.com", "Nombre", "", "", "Pass123");
        });
    }

    @Test
    void testAltaAsistenteFechaNacimientoFutura() {
        assertThrows(IllegalArgumentException.class, () -> {
            controladorU.altaAsistente("nick", "mail@dom.com", "Nombre", "Apellido",
                    LocalDate.now().plusDays(1), "", "Pass123");
        });
    }

    @Test
    void testAltaAsistenteFechaNacimientoHoy() {
        assertThrows(IllegalArgumentException.class, () -> {
            controladorU.altaAsistente("nick", "mail@dom.com", "Nombre", "Apellido",
                    LocalDate.now(), "", "Pass123");
        });
    }
    
    @Test
    void testAutenticarUsuarioPorNickname() throws Exception {
        mu.limpiarManejador();
        controladorU.altaOrganizador("nickauthtest", "authtest@dom.com", "Usuario", "Desc", "", "password123");
        
        boolean resultado = controladorU.autenticarUsuario("nickauthtest", "password123");
        
        assertTrue(resultado);
    }

    @Test
    void testAutenticarUsuarioPasswordIncorrectaPorNick() throws Exception {
        mu.limpiarManejador();
        controladorU.altaOrganizador("nickauthtest2", "authtest2@dom.com", "Usuario", "Desc", "", "password123");
        
        boolean resultado = controladorU.autenticarUsuario("nickauthtest2", "incorrecta");
        
        assertFalse(resultado);
    }

    
    @Test
    void testAltaRegistroEdicionFinalizada() throws Exception {
        mu.limpiarManejador();
        me.clearManejador();
        
        Asistente asistente = new Asistente("nickregfin", "regfin@dom.com", "Ana", "Pérez",
                LocalDate.of(1999, 5, 20), "", "Password123");
        mu.addAsistente(asistente);
        
        Organizador org = new Organizador("nickorgfin", "orgfin@dom.com", "Juan", "Desc", "", "PassOrg");
        mu.addOrganizador(org);
        
        Evento ev = new Evento("eventoFin", "EF", "descripcion", null, LocalDate.now(), "");
        Edicion ed = new Edicion("edicionFin", ev, org, "EF2020", 
                LocalDate.now().minusDays(60), LocalDate.now().minusDays(30), 
                "Montevideo", "Uruguay", LocalDate.now().minusDays(60), "");
        
        me.agregarEvento(ev);
        me.agregarEdicion(ed);
        
        assertThrows(Exception.class, () -> {
            controladorU.altaRegistro("nickregfin", "edicionFin", "General", LocalDate.now());
        });
    }

    @Test
    void testAltaRegistroConCodigoEdicionFinalizada() throws Exception {
        mu.limpiarManejador();
        me.clearManejador();
        
        Asistente asistente = new Asistente("nickcodfin", "codfin@dom.com", "Ana", "Pérez",
                LocalDate.of(1999, 5, 20), "", "Password123");
        mu.addAsistente(asistente);
        
        Organizador org = new Organizador("nickorgcodfin", "orgcodfin@dom.com", "Juan", "Desc", "", "PassOrg");
        mu.addOrganizador(org);
        
        Evento ev = new Evento("eventoCodFin", "ECF", "descripcion", null, LocalDate.now(), "");
        Edicion ed = new Edicion("edicionCodFin", ev, org, "ECF2020", 
                LocalDate.now().minusDays(60), LocalDate.now().minusDays(30), 
                "Montevideo", "Uruguay", LocalDate.now().minusDays(60), "");
        
        me.agregarEvento(ev);
        me.agregarEdicion(ed);
        
        assertThrows(Exception.class, () -> {
            controladorU.altaRegistroConCodigo("nickcodfin", "edicionCodFin", "Premium", "CODIGO123", LocalDate.now());
        });
    }

    @Test
    void testEditarAsistenteFechaFutura() {
        mu.limpiarManejador();
        Asistente asistente = new Asistente("nickeditfecha", "editfecha@dom.com", "Pepe", "Gómez",
                LocalDate.of(2000, 1, 1), "", "PasswordAsis");
        mu.addAsistente(asistente);
        
        assertThrows(IllegalArgumentException.class, () -> {
            controladorU.editarAsistente("nickeditfecha", "Nuevonombre", "nuevoapellido", 
                    LocalDate.now().plusDays(1), "", "PasswordAsis");
        });
    }

    @Test
    void testEditarAsistenteFechaHoy() {
        mu.limpiarManejador();
        Asistente asistente = new Asistente("nickedithoy", "edithoy@dom.com", "Pepe", "Gómez",
                LocalDate.of(2000, 1, 1), "", "PasswordAsis");
        mu.addAsistente(asistente);
        
        assertThrows(IllegalArgumentException.class, () -> {
            controladorU.editarAsistente("nickedithoy", "Nuevonombre", "nuevoapellido", 
                    LocalDate.now(), "", "PasswordAsis");
        });
    }

    @Test
    void testEditarAsistentePasswordVacia() {
        mu.limpiarManejador();
        Asistente asistente = new Asistente("nickeditpass", "editpass@dom.com", "Pepe", "Gómez",
                LocalDate.of(2000, 1, 1), "", "PasswordOriginal");
        mu.addAsistente(asistente);
        
        controladorU.editarAsistente("nickeditpass", "Nuevonombre", "nuevoapellido", 
                LocalDate.of(1990, 1, 1), "", "");
        
        assertEquals("PasswordOriginal", asistente.getPassword());
    }

    @Test
    void testEditarAsistentePasswordNull() {
        mu.limpiarManejador();
        Asistente asistente = new Asistente("nickeditpassnull", "editpassnull@dom.com", "Pepe", "Gómez",
                LocalDate.of(2000, 1, 1), "", "PasswordOriginal");
        mu.addAsistente(asistente);
        
        controladorU.editarAsistente("nickeditpassnull", "Nuevonombre", "nuevoapellido", 
                LocalDate.of(1990, 1, 1), "", null);
        
        assertEquals("PasswordOriginal", asistente.getPassword());
    }


    @Test
    void testEditarOrganizadorPasswordVacia() {
        mu.limpiarManejador();
        Organizador org = new Organizador("nickeditorgpass", "editorgpass@dom.com", "Pepe", "Organizador", 
                "", "PasswordOriginal");
        mu.addOrganizador(org);
        
        controladorU.editarOrganizador("nickeditorgpass", "Nuevonombre", "nuevaDesc", "nuevaWeb", "", "");
        
        assertEquals("PasswordOriginal", org.getPassword());
    }

    @Test
    void testEditarOrganizadorPasswordNull() {
        mu.limpiarManejador();
        Organizador org = new Organizador("nickeditorgpassnull", "editorgpassnull@dom.com", "Pepe", 
                "Organizador", "", "PasswordOriginal");
        mu.addOrganizador(org);
        
        controladorU.editarOrganizador("nickeditorgpassnull", "Nuevonombre", "nuevaDesc", "nuevaWeb", "", null);
        
        assertEquals("PasswordOriginal", org.getPassword());
    }
    
    @Test
    void testListarEdicionesAceptadasOrganizadorNoExiste() {
        mu.limpiarManejador();
        List<String> resultado = controladorU.listarEdicionesAceptadasDeOrganizador("noexiste");
        assertNotNull(resultado);
        assertEquals(0, resultado.size());
    }

    @Test
    void testListarEdicionesAceptadasSinEdiciones() throws Exception {
        mu.limpiarManejador();
        controladorU.altaOrganizador("nicksineds", "sineds@dom.com", "Org", "Desc", "", "Pass123");
        
        List<String> resultado = controladorU.listarEdicionesAceptadasDeOrganizador("nicksineds");
        
        assertNotNull(resultado);
        assertEquals(0, resultado.size());
    }

    
    @Test
    void testListarEdicionesDeOrganizadorNoExiste() {
        mu.limpiarManejador();
        List<String> resultado = controladorU.listarEdicionesDeOrganizador("noexiste");
        assertNotNull(resultado);
        assertEquals(0, resultado.size());
    }

    @Test
    void testListarEdicionesDeOrganizadorSinEdiciones() throws Exception {
        mu.limpiarManejador();
        controladorU.altaOrganizador("nicksineds2", "sineds2@dom.com", "Org", "Desc", "", "Pass123");
        
        List<String> resultado = controladorU.listarEdicionesDeOrganizador("nicksineds2");
        
        assertNotNull(resultado);
        assertEquals(0, resultado.size());
    }

    
    @Test
    void testSeguirAUsuarioSeguidorNoExiste() {
        mu.limpiarManejador();
        Asistente seguido = new Asistente("seguido", "seguido@dom.com", "Seguido", "Apellido",
                LocalDate.of(1996, 2, 2), "", "Pass2");
        mu.addAsistente(seguido);
        
        boolean resultado = controladorU.seguirAUsuario("seguido", "noexiste");
        
        assertFalse(resultado);
    }

    @Test
    void testSeguirAUsuarioSeguidoNoExiste() {
        mu.limpiarManejador();
        Asistente seguidor = new Asistente("seguidor", "seguidor@dom.com", "Seguidor", "Apellido",
                LocalDate.of(1995, 1, 1), "", "Pass1");
        mu.addAsistente(seguidor);
        
        boolean resultado = controladorU.seguirAUsuario("noexiste", "seguidor");
        
        assertFalse(resultado);
    }

    @Test
    void testDejarDeSeguirAUsuarioSeguidorNoExiste() {
        mu.limpiarManejador();
        Asistente seguido = new Asistente("seguido", "seguido@dom.com", "Seguido", "Apellido",
                LocalDate.of(1996, 2, 2), "", "Pass2");
        mu.addAsistente(seguido);
        
        boolean resultado = controladorU.dejarDeSeguirAUsuario("seguido", "noexiste");
        
        assertFalse(resultado);
    }

    @Test
    void testDejarDeSeguirAUsuarioSeguidoNoExiste() {
        mu.limpiarManejador();
        Asistente seguidor = new Asistente("seguidor", "seguidor@dom.com", "Seguidor", "Apellido",
                LocalDate.of(1995, 1, 1), "", "Pass1");
        mu.addAsistente(seguidor);
        
        boolean resultado = controladorU.dejarDeSeguirAUsuario("noexiste", "seguidor");
        
        assertFalse(resultado);
    }
    
    @Test
    void testRegistrarAsistenciaAsistenteNoExiste() {
        mu.limpiarManejador();
        
        assertThrows(Exception.class, () -> {
            controladorU.registrarAsistencia("noexiste", "edicion");
        });
    }

    @Test
    void testRegistrarAsistenciaSinRegistro() throws Exception {
        mu.limpiarManejador();
        
        Asistente asistente = new Asistente("nicksinreg", "sinreg@dom.com", "Ana", "Pérez",
                LocalDate.of(1999, 5, 20), "", "Password123");
        mu.addAsistente(asistente);
        
        assertThrows(Exception.class, () -> {
            controladorU.registrarAsistencia("nicksinreg", "edicionNoExiste");
        });
    }
    
    @Test
    void testInfoRegistroDeAsistenteConAsistenteNull() {
        mu.limpiarManejador();
        me.clearManejador();
        
        DTRegistro info = controladorU.infoRegistroDeAsistente("noexiste", "edicion");
        
        assertNotNull(info);
        assertEquals("", info.getNombreEdicion());
    }

    @Test
    void testInfoRegistroDeAsistenteConEdicionNull() {
        mu.limpiarManejador();
        me.clearManejador();
        
        Asistente asistente = new Asistente("nickinfo", "info@dom.com", "Ana", "Pérez",
                LocalDate.of(1999, 5, 20), "", "Password123");
        mu.addAsistente(asistente);
        
        DTRegistro info = controladorU.infoRegistroDeAsistente("nickinfo", "edicionNoExiste");
        
        assertNotNull(info);
        assertEquals("", info.getNombreEdicion());
    }

    @Test
    void testInfoRegistroDeAsistenteConRegistroNull() {
        mu.limpiarManejador();
        me.clearManejador();
        
        Asistente asistente = new Asistente("nickinfo2", "info2@dom.com", "Ana", "Pérez",
                LocalDate.of(1999, 5, 20), "", "Password123");
        mu.addAsistente(asistente);
        
        Evento ev = new Evento("evento", "EV", "desc", null, LocalDate.now(), "");
        Edicion ed = new Edicion("edicion", ev, null, "ED", LocalDate.now(), 
                LocalDate.now(), "ciudad", "pais", LocalDate.now(), "");
        me.agregarEvento(ev);
        me.agregarEdicion(ed);
        
        DTRegistro info = controladorU.infoRegistroDeAsistente("nickinfo2", "edicion");
        
        assertNotNull(info);
        assertEquals("", info.getNombreEdicion());
    }

    
    @Test
    void testIngresarSitioWebConAsistente() throws Exception {
        mu.limpiarManejador();
        
        controladorU.altaAsistente("nickasist", "asist@dom.com", "Nombre", "Apellido",
                LocalDate.of(1990, 1, 1), "", "Pass123");
        
        // No debe hacer nada, pero no debe fallar
        controladorU.ingresarSitioWeb("nickasist", "miWeb");
        
        Asistente asist = mu.getAsistente("nickasist");
        assertNotNull(asist);
    }

    
    @Test
    void testIngresarInstitucionConOrganizador() throws Exception {
        mu.limpiarManejador();
        
        controladorU.altaOrganizador("nickorg", "org@dom.com", "Org", "Desc", "", "Pass123");
        controladorI.altaInstitucion("Institucion", "Desc", "web", "");
        
        controladorU.ingresarInstitucion("nickorg", "Institucion");
        
        Organizador org = mu.getOrganizador("nickorg");
        assertNotNull(org);
    }
}
	

