package logica;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import logica.CargaDatos;

class CargaDatosTest {
	private CargaDatos carga = new CargaDatos(); 

	@Test
	void testCargaDatos() {
		assertTrue(carga.cargar());
	}
	
	
	@Test
	void testCargaDatosFalse() {
		carga.cargar();
		assertFalse(carga.cargar());
	}

}
