package logica;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;


class InstitucionesControllerTest {
	
	private InstitucionesController contrlI = new InstitucionesController();
    private ManejadorInstituciones mi = ManejadorInstituciones.getInstance();

	@Test
	void testGetInstitucionesOk() {
		mi.clearManejador();
		Institucion inst1 = new Institucion("Facultad Ingeniería", "desc?", "web", "");
        Institucion inst2 = new Institucion("Facultad Ciencias", "desc2", "web2", "");

        mi.addInstitucion(inst1);
        mi.addInstitucion(inst2);

        // Act
        DTInstitucion[] resultado = contrlI.getInstituciones();

        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.length);
        assertEquals("Facultad Ciencias", resultado[0].getNombre());
        assertEquals("Facultad Ingeniería", resultado[1].getNombre());
	}
	@Test
	void testAltaInstitucionOk() throws Exception {
	    mi.clearManejador();

	    contrlI.altaInstitucion("Facultad Ingeniería", "Desc", "web", "");

	    Institucion inst = mi.obtenerInstitucion("Facultad Ingeniería");
	    assertNotNull(inst);
	    assertEquals("Facultad Ingeniería", inst.getNombre());
	}
	@Test
	void testObtenerInstitucionInexistente() {
	    mi.clearManejador();

	    Institucion inst = mi.obtenerInstitucion("NoExiste");
	    assertNull(inst, "Debe devolver null si la institución no existe");
	}
	
//NUEVOS
	@Test
	void testGetInstitucionesMultiples() throws Exception {
	    mi.clearManejador();
	    
	    contrlI.altaInstitucion("Inst1", "Desc1", "web1", "");
	    contrlI.altaInstitucion("Inst2", "Desc2", "web2", "");
	    contrlI.altaInstitucion("Inst3", "Desc3", "web3", "");
	    
	    DTInstitucion[] resultado = contrlI.getInstituciones();
	    
	    assertNotNull(resultado);
	    assertEquals(3, resultado.length);
	    
	    mi.clearManejador();
	}
	
	@Test
	void testAltaInstitucionSinImagen() throws Exception {
	    mi.clearManejador();
	    
	    contrlI.altaInstitucion("InstSinImg", "Descripción", "web.com", "");
	    
	    Institucion inst = mi.obtenerInstitucion("InstSinImg");
	    assertNotNull(inst);
	    assertEquals("InstSinImg", inst.getNombre());
	    
	    mi.clearManejador();
	}
	@Test
	void testGetInstitucionesOrdenadoAlfabeticamente() throws Exception {
	    ManejadorInstituciones mi = ManejadorInstituciones.getInstance();
	    InstitucionesController contrlI = new InstitucionesController();
	    mi.clearManejador();
	    
	    contrlI.altaInstitucion("Zebra Inc", "Desc", "web", "");
	    contrlI.altaInstitucion("Alpha Corp", "Desc", "web", "");
	    contrlI.altaInstitucion("Beta Ltd", "Desc", "web", "");
	    
	    DTInstitucion[] instituciones = contrlI.getInstituciones();
	    
	    assertEquals(3, instituciones.length);
	    // Verificar orden alfabético
	    assertEquals("Alpha Corp", instituciones[0].getNombre());
	    assertEquals("Beta Ltd", instituciones[1].getNombre());
	    assertEquals("Zebra Inc", instituciones[2].getNombre());
	    
	    mi.clearManejador();
	}

	@Test
	void testAltaInstitucionConCamposVacios() throws Exception {
	    ManejadorInstituciones mi = ManejadorInstituciones.getInstance();
	    InstitucionesController contrlI = new InstitucionesController();
	    mi.clearManejador();
	    
	    contrlI.altaInstitucion("InstVacia", "", "", "");
	    
	    Institucion inst = mi.obtenerInstitucion("InstVacia");
	    assertNotNull(inst);
	    assertEquals("InstVacia", inst.getNombre());
	    
	    mi.clearManejador();
	}

}
