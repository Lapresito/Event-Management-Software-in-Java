package presentacionSwingApp;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import logica.CargaDatos;
import logica.DTEdicion;
import logica.Fabrica;
import logica.IEventosController;
import logica.IInstitucionesController;
import logica.IUsuariosController;

public class Principal {

	private Fabrica fabrica = Fabrica.getInstance();
	private IEventosController sistemaEventos = fabrica.getIControladorEventos();
	private IUsuariosController ICU = fabrica.getIUsuariosController();
	private IInstitucionesController IIN = fabrica.getIInstitucionesController();

	private JFrame frmEventosuy;

	private AltaDeUsuario altaUsuarioInternalFrame;

	private ConsultaDeUsuario conultaDeUsuarioInternalFrame;
	private ConsultaDeEdicionDeEvento consEdicionOrganizadorInternalFrame;

	private ModificarDatosDeUsuario modificarDatosDeUsuarioInternalFrame;

	private ConsultaDeEdicionDeEvento consultaDeEdicionInternalFrame;

	private AltaDeTipoDeRegistro altaDeTipoDeRegistroInternalFrame;
	private AltaDePatrocinio altaDePatrocinioInternalFrame;

	private ConsultaDeEvento consultaDeEventoInternalFrame;

	private RegistroAEdicionDeEvento registroAEdicionDeEventoInternalFrame;

	private AltaCategoria altaCategoriaInternalFrame;

	private ConsultaDeRegistro consultaDeRegistroInternalFrame;

	private AltaDeEdicionDeEvento altaDeEdicionDeEventoInternalFrame;
	private AceptarRechazarEdiciones aceptarRechazarEdicionesInternalFrame;
	private AltaDeInstitucion altaDeInstitucionInternalFrame;
	private AltaDeEvento altaEventoInternalFrame;
	private ConsultaDePatrocinio consultaPatrocinioInternalFrame;
	private ConsultaTipoRegistro consultaTipoRegistroInternalFrame;

	private ConsultaDeRegistro consRegistroAsistenteInternalFrame;

	private ConsultaTipoRegistro consTipoRegistroEdicionInternalFrame;
	private EventosMasVistos eventosMasVistos;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Principal window = new Principal();
					window.frmEventosuy.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	/**
	 * Create the application.
	 */
	public Principal() {
		initialize();

		consultaDeRegistroInternalFrame = new ConsultaDeRegistro(ICU);
		consultaDeRegistroInternalFrame.setSize(500, 300);
		consultaDeRegistroInternalFrame.setLocation(137, 11);
		consultaDeRegistroInternalFrame.setVisible(false);

		consRegistroAsistenteInternalFrame = new ConsultaDeRegistro(ICU);
		consRegistroAsistenteInternalFrame.setSize(500, 300);
		consRegistroAsistenteInternalFrame.setLocation(137, 11);
		consRegistroAsistenteInternalFrame.setVisible(false);

		altaDeEdicionDeEventoInternalFrame = new AltaDeEdicionDeEvento(sistemaEventos, ICU);
		altaDeEdicionDeEventoInternalFrame.setBounds(0, 0, 500, 340);
		altaDeEdicionDeEventoInternalFrame.setVisible(false);

		altaDeInstitucionInternalFrame = new AltaDeInstitucion(IIN);
		altaDeInstitucionInternalFrame.setSize(422, 256);
		altaDeInstitucionInternalFrame.setLocation(97, 11);
		altaDeInstitucionInternalFrame.setVisible(false);

		altaEventoInternalFrame = new AltaDeEvento();
		altaEventoInternalFrame.setSize(450, 300);
		altaEventoInternalFrame.setLocation(1, 1);
		altaEventoInternalFrame.setVisible(false);

		altaUsuarioInternalFrame = new AltaDeUsuario(ICU);
		altaUsuarioInternalFrame.setSize(483, 266);
		altaUsuarioInternalFrame.setLocation(17, 0);
		altaUsuarioInternalFrame.setVisible(false);

		conultaDeUsuarioInternalFrame = new ConsultaDeUsuario(ICU, this);
		conultaDeUsuarioInternalFrame.setSize(387, 340);
		conultaDeUsuarioInternalFrame.setLocation(153, 0);
		conultaDeUsuarioInternalFrame.setVisible(false);

		altaEventoInternalFrame = new AltaDeEvento();
		altaEventoInternalFrame.setSize(550, 300);
		altaEventoInternalFrame.setLocation(1, 1);
		altaEventoInternalFrame.setVisible(false);

		altaUsuarioInternalFrame = new AltaDeUsuario(ICU);
		altaUsuarioInternalFrame.setSize(483, 266); // chequear tamanio
		altaUsuarioInternalFrame.setLocation(41, 0);
		altaUsuarioInternalFrame.setVisible(false);

		modificarDatosDeUsuarioInternalFrame = new ModificarDatosDeUsuario(ICU);
		modificarDatosDeUsuarioInternalFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		modificarDatosDeUsuarioInternalFrame.setSize(387, 261);
		modificarDatosDeUsuarioInternalFrame.setLocation(41, 11);
		modificarDatosDeUsuarioInternalFrame.setVisible(false);

		consEdicionOrganizadorInternalFrame = new ConsultaDeEdicionDeEvento(this);
		consEdicionOrganizadorInternalFrame.setSize(387, 277);
		consEdicionOrganizadorInternalFrame.setLocation(137, 11);
		consEdicionOrganizadorInternalFrame.setVisible(false);

		consultaPatrocinioInternalFrame = new ConsultaDePatrocinio(sistemaEventos);
		consultaPatrocinioInternalFrame.setSize(450, 300);
		consultaPatrocinioInternalFrame.setLocation(1, 1);
		consultaPatrocinioInternalFrame.setVisible(false);

		altaDeTipoDeRegistroInternalFrame = new AltaDeTipoDeRegistro(sistemaEventos);
		altaDeTipoDeRegistroInternalFrame.setSize(400, 350);
		altaDeTipoDeRegistroInternalFrame.setLocation(21, 11);
		altaDeTipoDeRegistroInternalFrame.setVisible(false);

		altaDePatrocinioInternalFrame = new AltaDePatrocinio(sistemaEventos, IIN);
		altaDePatrocinioInternalFrame.setSize(400, 350);
		altaDePatrocinioInternalFrame.setLocation(21, 11);
		altaDePatrocinioInternalFrame.setVisible(false);

		consultaDeEdicionInternalFrame = new ConsultaDeEdicionDeEvento(this);
		consultaDeEdicionInternalFrame.setSize(520, 280);  
		consultaDeEdicionInternalFrame.setLocation(100, 50);
		consultaDeEdicionInternalFrame.setVisible(false);
		
		
		aceptarRechazarEdicionesInternalFrame = new AceptarRechazarEdiciones(this);
		aceptarRechazarEdicionesInternalFrame.setSize(387, 277);
		aceptarRechazarEdicionesInternalFrame.setLocation(137, 11);
		aceptarRechazarEdicionesInternalFrame.setVisible(false);

		consultaDeEventoInternalFrame = new ConsultaDeEvento();
		consultaDeEventoInternalFrame.setSize(372, 300);
		consultaDeEventoInternalFrame.setLocation(21, 11);
		consultaDeEventoInternalFrame.setVisible(false);

		registroAEdicionDeEventoInternalFrame = new RegistroAEdicionDeEvento(sistemaEventos, ICU);
		registroAEdicionDeEventoInternalFrame.setSize(500, 250);
		registroAEdicionDeEventoInternalFrame.setLocation(21, 11);
		registroAEdicionDeEventoInternalFrame.setVisible(false);

		altaCategoriaInternalFrame = new AltaCategoria(sistemaEventos);
		altaCategoriaInternalFrame.setSize(372, 240);
		altaCategoriaInternalFrame.setLocation(21, 11);
		altaCategoriaInternalFrame.setVisible(false);

		consultaTipoRegistroInternalFrame = new ConsultaTipoRegistro(sistemaEventos);
		consultaTipoRegistroInternalFrame.setSize(450, 300);
		consultaTipoRegistroInternalFrame.setLocation(1, 1);
		consultaTipoRegistroInternalFrame.setVisible(false);

		consTipoRegistroEdicionInternalFrame = new ConsultaTipoRegistro(sistemaEventos);
		consTipoRegistroEdicionInternalFrame.setSize(450, 300);
		consTipoRegistroEdicionInternalFrame.setLocation(1, 1);
		consTipoRegistroEdicionInternalFrame.setVisible(false);
		
		eventosMasVistos = new EventosMasVistos(sistemaEventos);
		eventosMasVistos.setSize(620, 380);  
		eventosMasVistos.setLocation(100, 50);
		eventosMasVistos.setVisible(false); 
		frmEventosuy.getContentPane().setLayout(null);

		frmEventosuy.getContentPane().add(eventosMasVistos);

		frmEventosuy.getContentPane().add(consultaDeEventoInternalFrame);
		frmEventosuy.getContentPane().add(altaUsuarioInternalFrame);
		frmEventosuy.getContentPane().add(conultaDeUsuarioInternalFrame);
		frmEventosuy.getContentPane().add(modificarDatosDeUsuarioInternalFrame);
		frmEventosuy.getContentPane().add(registroAEdicionDeEventoInternalFrame);
		frmEventosuy.getContentPane().add(altaCategoriaInternalFrame);
		frmEventosuy.getContentPane().add(altaDePatrocinioInternalFrame);

		frmEventosuy.getContentPane().add(altaDeTipoDeRegistroInternalFrame);
		frmEventosuy.getContentPane().add(consultaDeEdicionInternalFrame);
		frmEventosuy.getContentPane().add(aceptarRechazarEdicionesInternalFrame);
		frmEventosuy.getContentPane().add(consultaDeRegistroInternalFrame);
		frmEventosuy.getContentPane().add(altaDeEdicionDeEventoInternalFrame);
		frmEventosuy.getContentPane().add(altaDeInstitucionInternalFrame);

		frmEventosuy.getContentPane().add(altaEventoInternalFrame);
		frmEventosuy.getContentPane().add(consultaPatrocinioInternalFrame);
		frmEventosuy.getContentPane().add(consultaTipoRegistroInternalFrame);
	

		frmEventosuy.getContentPane().add(consEdicionOrganizadorInternalFrame);
		frmEventosuy.getContentPane().add(consRegistroAsistenteInternalFrame);

		frmEventosuy.getContentPane().add(altaEventoInternalFrame);
		frmEventosuy.getContentPane().add(consultaPatrocinioInternalFrame);
		frmEventosuy.getContentPane().add(consultaTipoRegistroInternalFrame);
		frmEventosuy.getContentPane().add(consTipoRegistroEdicionInternalFrame);

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		// Se crea el Frame con las dimensiones indicadas.
		frmEventosuy = new JFrame();
		frmEventosuy.setTitle("Eventos.uy 1.0");
		frmEventosuy.setBounds(100, 100, 613, 404);
		frmEventosuy.setSize(1000, 700);
		frmEventosuy.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmEventosuy.setLocationRelativeTo(null);

		// Se crea una barra de menú (JMenuBar) con dos menú (JMenu) desplegables.
		// Cada menú contiene diferentes opciones (JMenuItem), los cuales tienen un
		// evento asociado que permite realizar una acción una vez se seleccionan.
		JMenuBar menuBar = new JMenuBar();
		frmEventosuy.setJMenuBar(menuBar);

		JMenu menuSistema = new JMenu("Sistema");
		menuBar.add(menuSistema);

		JMenuItem menuItemCargaDatos = new JMenuItem("Carga Datos");
		menuItemCargaDatos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CargaDatos carga = new CargaDatos();
				carga.cargar();
			}
		});
		menuSistema.add(menuItemCargaDatos);

		JMenuItem menuItemSalir = new JMenuItem("Salir");
		menuItemSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Salgo de la aplicación
				frmEventosuy.setVisible(false);
				frmEventosuy.dispose();
			}
		});
		menuSistema.add(menuItemSalir);

		JMenu menuUsuarios = new JMenu("Usuarios");
		menuBar.add(menuUsuarios);

		JMenuItem menuItemAltaDeUsuario = new JMenuItem("Alta De Usuario");
		menuItemAltaDeUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Muestro el InternalFrame para registrar un usuario
				altaUsuarioInternalFrame.cargarInstituciones();
				altaUsuarioInternalFrame.setVisible(true);
				altaUsuarioInternalFrame.toFront();
			}
		});
		menuUsuarios.add(menuItemAltaDeUsuario);

		JMenuItem menuItemConsultaDeUsuario = new JMenuItem("Consulta de Usuario");
		menuItemConsultaDeUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Muestro el InternalFrame para consultar un usuario
				conultaDeUsuarioInternalFrame.cargarUsuarios();
				conultaDeUsuarioInternalFrame.setVisible(true);
				conultaDeUsuarioInternalFrame.toFront();
			}
		});
		menuUsuarios.add(menuItemConsultaDeUsuario);

		JMenuItem menuItemModificarDatosDeUsuario = new JMenuItem("Modificar Datos de Usuario");
		menuItemModificarDatosDeUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Muestro el InternalFrame para modificar un usuario
				modificarDatosDeUsuarioInternalFrame.cargarUsuarios();
				modificarDatosDeUsuarioInternalFrame.setVisible(true);
				modificarDatosDeUsuarioInternalFrame.toFront();
			}
		});
		menuUsuarios.add(menuItemModificarDatosDeUsuario);

		JMenu menuEventos = new JMenu("Eventos");
		menuBar.add(menuEventos);

		JMenu submenuEvento = new JMenu("Evento");
		menuEventos.add(submenuEvento);

		JMenuItem menuItemAltaDeEvento = new JMenuItem("Alta de Evento");
		submenuEvento.add(menuItemAltaDeEvento);
		menuItemAltaDeEvento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Muestro el InternalFrame para dar de alta el evento
				altaEventoInternalFrame.setVisible(true);
				altaEventoInternalFrame.actualizarCategorias();
			}
		});

		JMenuItem menuItemConsultaDeEvento = new JMenuItem("Consulta de Evento");
		submenuEvento.add(menuItemConsultaDeEvento);
		
		
		menuItemConsultaDeEvento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Muestro el InternalFrame para consultar un evento
				consultaDeEventoInternalFrame.actualizarComboDeEventos();
				consultaDeEventoInternalFrame.setVisible(true);
			}
		});
		JMenuItem menuItemTopEventos = new JMenuItem("Eventos más vistos");
		submenuEvento.add(menuItemTopEventos);
		menuItemTopEventos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Muestro el InternalFrame 
				eventosMasVistos.setVisible(true);
			}
		});
		
		JMenu submenuEdicion = new JMenu("Edición");
		menuEventos.add(submenuEdicion);

		
		JMenuItem menuItemAltaDeEdicionDeEvento = new JMenuItem("Alta de Edición de Evento");
		menuItemAltaDeEdicionDeEvento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				altaDeEdicionDeEventoInternalFrame.setVisible(true);
			}
		});
		submenuEdicion.add(menuItemAltaDeEdicionDeEvento);

		JMenuItem menuItemConsultaDeEdicionDeEvento = new JMenuItem("Consulta de Edición de Evento");
		menuItemConsultaDeEdicionDeEvento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Muestro el InternalFrame para consultar una edicion de evento
				consultaDeEdicionInternalFrame.recargarEventos();
				consultaDeEdicionInternalFrame.setVisible(true);
				consultaDeEdicionInternalFrame.toFront();
			}
		});
		
		JMenuItem menuItemRechazarAceptarEdicion = new JMenuItem("Aceptar/Rechazar ediciones");
		menuItemRechazarAceptarEdicion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				aceptarRechazarEdicionesInternalFrame.recargarEventos();
				aceptarRechazarEdicionesInternalFrame.setVisible(true);
				aceptarRechazarEdicionesInternalFrame.toFront();
			}
		});

		submenuEdicion.add(menuItemConsultaDeEdicionDeEvento);
		submenuEdicion.add(menuItemRechazarAceptarEdicion);

		JMenu submenuPatrocinio = new JMenu("Patrocinio");
		menuEventos.add(submenuPatrocinio);

		JMenuItem menuItemAltaDePatrocinio = new JMenuItem("Alta de Patrocinio");
		submenuPatrocinio.add(menuItemAltaDePatrocinio);
		menuItemAltaDePatrocinio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Muestro el InternalFrame para Alta de Patrocinio
				altaDePatrocinioInternalFrame.cargarEventosEInstituciones();
				altaDePatrocinioInternalFrame.setVisible(true);
				altaDePatrocinioInternalFrame.toFront();
			}
		});

		JMenuItem menuItemConsultaDePatrocinio = new JMenuItem("Consulta de Patrocinio");
		submenuPatrocinio.add(menuItemConsultaDePatrocinio);
		menuItemConsultaDePatrocinio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				consultaPatrocinioInternalFrame.addInternalFrameListener(new InternalFrameAdapter() {
					public void internalFrameClosed(InternalFrameEvent e) {
						consultaPatrocinioInternalFrame.limpiarTodo();
					}
				});
				
				consultaPatrocinioInternalFrame.actualizarCombos();
				consultaPatrocinioInternalFrame.setVisible(true);
				consultaPatrocinioInternalFrame.toFront();
			}
		});

		JMenu submenuTipoDeRegistro = new JMenu("Tipo de Registro");
		menuEventos.add(submenuTipoDeRegistro);

		JMenuItem menuItemAltaDeTipoDeRegistro = new JMenuItem("Alta de Tipo de Registro");
		submenuTipoDeRegistro.add(menuItemAltaDeTipoDeRegistro);

		menuItemAltaDeTipoDeRegistro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				altaDeTipoDeRegistroInternalFrame.addInternalFrameListener(new InternalFrameAdapter() {
					public void internalFrameClosed(InternalFrameEvent e) {
						altaDeTipoDeRegistroInternalFrame.limpiarTodo();
					}
				});
				// Muestro el InternalFrame para Alta de Tipo de Registro
				altaDeTipoDeRegistroInternalFrame.cargarEventos();
				altaDeTipoDeRegistroInternalFrame.setVisible(true);
				altaDeTipoDeRegistroInternalFrame.toFront();
			}
		});

		JMenuItem menuItemConsultaDeTipoDeRegistro = new JMenuItem("Consulta de Tipo de Registro");
		submenuTipoDeRegistro.add(menuItemConsultaDeTipoDeRegistro);
		menuItemConsultaDeTipoDeRegistro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				consultaTipoRegistroInternalFrame.addInternalFrameListener(new InternalFrameAdapter() {
					public void internalFrameClosed(InternalFrameEvent e) {
						consultaTipoRegistroInternalFrame.limpiarTodo();
					}
				});
				consultaTipoRegistroInternalFrame.cargarEventos();
				consultaTipoRegistroInternalFrame.setVisible(true);
				consultaTipoRegistroInternalFrame.toFront();
			}
		});

		JMenu submenuRegistro = new JMenu("Registro");
		menuEventos.add(submenuRegistro);

		JMenuItem menuItemRegistroAEdicionDeEvento = new JMenuItem("Registro a Edición de Evento");
		submenuRegistro.add(menuItemRegistroAEdicionDeEvento);
		menuItemRegistroAEdicionDeEvento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Muestro el InternalFrame para consultar un evento
				registroAEdicionDeEventoInternalFrame.cargarEventosYAsistentes();
				registroAEdicionDeEventoInternalFrame.setVisible(true);
				registroAEdicionDeEventoInternalFrame.toFront();
			}
		});

		JMenuItem menuItemConsultaDeRegistro = new JMenuItem("Consulta de Registro");
		menuItemConsultaDeRegistro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				consultaDeRegistroInternalFrame.cargarAsistentes();
				consultaDeRegistroInternalFrame.setVisible(true);
				consultaDeRegistroInternalFrame.toFront();
			}
		});
		submenuRegistro.add(menuItemConsultaDeRegistro);

		JMenu submenuCategorias = new JMenu("Categorías");
		menuEventos.add(submenuCategorias);
		JMenuItem menuItemAltaCategoria = new JMenuItem("AltaCategoria");
		submenuCategorias.add(menuItemAltaCategoria);
		menuItemAltaCategoria.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Muestro el InternalFrame para consultar un evento
				altaCategoriaInternalFrame.setVisible(true);
				altaCategoriaInternalFrame.toFront();
			}
		});

		JMenu menuInstituciones = new JMenu("Instituciones");
		menuBar.add(menuInstituciones);

		JMenuItem MenuItemAltaInstitucion = new JMenuItem("Alta de Institución");
		MenuItemAltaInstitucion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				altaDeInstitucionInternalFrame.setVisible(true);
				altaDeInstitucionInternalFrame.toFront();
			}
		});
		menuInstituciones.add(MenuItemAltaInstitucion);

	}

	public void mostrarDatosEdicion(DTEdicion edicion) {
		consEdicionOrganizadorInternalFrame.mostrarEdicion(edicion);
		consEdicionOrganizadorInternalFrame.setVisible(true);
	}

	public void mostrarDatosRegistro(String nickUsuario, String nombreEdicion) {

		consRegistroAsistenteInternalFrame.mostrarRegistro(nickUsuario, nombreEdicion);
		consRegistroAsistenteInternalFrame.setVisible(true);

	}

	public void mostrarDatosTipoRegistro(String nombreEdicion, String nombreTipo) {
		consTipoRegistroEdicionInternalFrame.mostrarDatos(nombreEdicion, nombreTipo);
		consTipoRegistroEdicionInternalFrame.setVisible(true);
	}

	public void mostrarDatosPatrocinio(String nombreEdicion, String patrocinio) {
		consultaPatrocinioInternalFrame.mostrarDatos(nombreEdicion, patrocinio);
		consultaPatrocinioInternalFrame.setVisible(true);
	}
}
