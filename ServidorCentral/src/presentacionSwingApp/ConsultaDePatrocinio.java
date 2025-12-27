package presentacionSwingApp;

import javax.swing.JInternalFrame;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JSeparator;
import javax.swing.JDesktopPane;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import logica.DTPatrocinio;
import logica.DTTipoRegistro;
import logica.Fabrica;
import logica.IEventosController;
import logica.NivelPatrocinio;

public class ConsultaDePatrocinio extends JInternalFrame {
	private static final long serialVersionUID = 1L;
	private JTextField textFieldInstitucion;
	private JTextField textFieldCupos;
	private JTextField textFieldMonto;
	private JTextField textFieldRegistros;
	private JTextField textFieldCodigo;
	private JTextField textFieldNivel;
	private JTextField textFieldFecha;
	
	JComboBox<String> comboBoxEvento= new JComboBox<>();;
	JComboBox<String> comboBoxEdicion= new JComboBox<>();;
	JComboBox<String> comboBoxPatrocinio= new JComboBox<>();;

	private Fabrica fabrica = Fabrica.getInstance();
	private IEventosController sistemaEventos = fabrica.getIControladorEventos();

	public ConsultaDePatrocinio(IEventosController IEv) {
		setResizable(true);
		
		sistemaEventos = IEv;
		
		setMaximizable(true);
		setIconifiable(true);
		setClosable(true);
		setTitle("Consulta de Patrocinio");
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {33, 55, 30, 30, 60, 30, 63, 91, 30};
		gridBagLayout.rowHeights = new int[] {15, 30, 30, 30, 22, 10, 30, 0, 0, 0, 50, 30, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		
		// ---------------------  LISTA EVENTOS ----------------------------------------
		JLabel lblNewLabel_1 = new JLabel("");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 4;
		gbc_lblNewLabel_1.gridy = 0;
		getContentPane().add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		JLabel lblNewLabel = new JLabel("Seleccionar un evento:");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.gridwidth = 4;
		gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 1;
		getContentPane().add(lblNewLabel, gbc_lblNewLabel);
		
		comboBoxEvento = new JComboBox<>();
		actualizarCombos();
		
		GridBagConstraints gbc_comboBoxEvento = new GridBagConstraints();
		gbc_comboBoxEvento.gridwidth = 3;
		gbc_comboBoxEvento.insets = new Insets(0, 0, 5, 5);
		gbc_comboBoxEvento.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxEvento.gridx = 5;
		gbc_comboBoxEvento.gridy = 1;
		getContentPane().add(comboBoxEvento, gbc_comboBoxEvento);
		
		comboBoxEvento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actualizarComboDeEdiciones();
				actualizarComboDePatrocinios();
				
			}
		});
		
		// ---------------------  LISTA EDICIONES ----------------------------------------
		JLabel lblSeleccionarUnaEdicion = new JLabel("Seleccionar una edicion:");
		GridBagConstraints gbc_lblSeleccionarUnaEdicion = new GridBagConstraints();
		gbc_lblSeleccionarUnaEdicion.gridwidth = 4;
		gbc_lblSeleccionarUnaEdicion.anchor = GridBagConstraints.WEST;
		gbc_lblSeleccionarUnaEdicion.insets = new Insets(0, 0, 5, 5);
		gbc_lblSeleccionarUnaEdicion.gridx = 1;
		gbc_lblSeleccionarUnaEdicion.gridy = 2;
		getContentPane().add(lblSeleccionarUnaEdicion, gbc_lblSeleccionarUnaEdicion);
		
		comboBoxEdicion = new JComboBox<>();
		GridBagConstraints gbc_comboBoxEdicion = new GridBagConstraints();
		gbc_comboBoxEdicion.gridwidth = 3;
		gbc_comboBoxEdicion.insets = new Insets(0, 0, 5, 5);
		gbc_comboBoxEdicion.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxEdicion.gridx = 5;
		gbc_comboBoxEdicion.gridy = 2;
		getContentPane().add(comboBoxEdicion, gbc_comboBoxEdicion);

		comboBoxEdicion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actualizarComboDePatrocinios();

			}
		});
		
		// ---------------------  LISTA DE PATROCINIOS ----------------------------------------
		JLabel lblSeleccionarPatrocinio = new JLabel("Seleccionar un patrocinio:");
		GridBagConstraints gbc_lblSeleccionarPatrocinio= new GridBagConstraints();
		gbc_lblSeleccionarPatrocinio.gridwidth = 4;
		gbc_lblSeleccionarPatrocinio.anchor = GridBagConstraints.WEST;
		gbc_lblSeleccionarPatrocinio.insets = new Insets(0, 0, 5, 5);
		gbc_lblSeleccionarPatrocinio.gridx = 1;
		gbc_lblSeleccionarPatrocinio.gridy = 3;
		getContentPane().add(lblSeleccionarPatrocinio, gbc_lblSeleccionarPatrocinio);
		
		comboBoxPatrocinio = new JComboBox<>();
		GridBagConstraints gbc_comboBoxPatrocinio = new GridBagConstraints();
		gbc_comboBoxPatrocinio.gridwidth = 3;
		gbc_comboBoxPatrocinio.insets = new Insets(0, 0, 5, 5);
		gbc_comboBoxPatrocinio.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxPatrocinio.gridx = 5;
		gbc_comboBoxPatrocinio.gridy = 3;
		getContentPane().add(comboBoxPatrocinio, gbc_comboBoxPatrocinio);
		

		// ---------------------  BTN COSULTAR ----------------------------------------
		JButton btnConsultar = new JButton("Consultar");
		GridBagConstraints gbc_btnConsultar = new GridBagConstraints();
		gbc_btnConsultar.gridwidth = 2;
		gbc_btnConsultar.anchor = GridBagConstraints.EAST;
		gbc_btnConsultar.insets = new Insets(0, 0, 5, 5);
		gbc_btnConsultar.gridx = 6;
		gbc_btnConsultar.gridy = 4;
		getContentPane().add(btnConsultar, gbc_btnConsultar);
		
		btnConsultar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nombreEdicion = (String) comboBoxEdicion.getSelectedItem();
				String patrocinio = (String) comboBoxPatrocinio.getSelectedItem();
				
				mostrarDatos(nombreEdicion, patrocinio);
				
			}
		

		});
		
		// ---------------------  SEPARADOR ----------------------------------------
		JSeparator separator = new JSeparator();
		separator.setForeground(new Color(0, 0, 0));
		GridBagConstraints gbc_separator = new GridBagConstraints();
		gbc_separator.fill = GridBagConstraints.HORIZONTAL;
		gbc_separator.anchor = GridBagConstraints.WEST;
		gbc_separator.gridwidth = 7;
		gbc_separator.insets = new Insets(0, 0, 5, 5);
		gbc_separator.gridx = 1;
		gbc_separator.gridy = 5;
		getContentPane().add(separator, gbc_separator);
		
		
		// ---------------------  INFO iNSTITUCION ----------------------------------------
		JLabel lblInstitucion = new JLabel("Institucion: ");
		GridBagConstraints gbc_lblInstitucion = new GridBagConstraints();
		gbc_lblInstitucion.anchor = GridBagConstraints.EAST;
		gbc_lblInstitucion.insets = new Insets(0, 0, 5, 5);
		gbc_lblInstitucion.gridx = 1;
		gbc_lblInstitucion.gridy = 7;
		getContentPane().add(lblInstitucion, gbc_lblInstitucion);
		
		textFieldInstitucion = new JTextField();
		textFieldInstitucion.setEditable(false);
		GridBagConstraints gbc_textFieldInstitucion = new GridBagConstraints();
		gbc_textFieldInstitucion.gridwidth = 6;
		gbc_textFieldInstitucion.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldInstitucion.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldInstitucion.gridx = 2;
		gbc_textFieldInstitucion.gridy = 7;
		getContentPane().add(textFieldInstitucion, gbc_textFieldInstitucion);
		textFieldInstitucion.setColumns(10);
		
		// --------------------- INFO CUPOS ----------------------------------------
		JLabel lblCupos = new JLabel("Cupos:");
		GridBagConstraints gbc_lblCupos = new GridBagConstraints();
		gbc_lblCupos.anchor = GridBagConstraints.WEST;
		gbc_lblCupos.insets = new Insets(0, 0, 5, 5);
		gbc_lblCupos.gridx = 6;
		gbc_lblCupos.gridy = 8;
		getContentPane().add(lblCupos, gbc_lblCupos);
		
		textFieldCupos = new JTextField();
		textFieldCupos.setEditable(false);
		textFieldCupos.setColumns(10);
		GridBagConstraints gbc_textFieldCupos = new GridBagConstraints();
		gbc_textFieldCupos.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldCupos.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldCupos.gridx = 7;
		gbc_textFieldCupos.gridy = 8;
		getContentPane().add(textFieldCupos, gbc_textFieldCupos);
		
		// ---------------------  INFO MONTOS ----------------------------------------
		JLabel lblMonto = new JLabel("Monto:");
		GridBagConstraints gbc_lblMonto = new GridBagConstraints();
		gbc_lblMonto.anchor = GridBagConstraints.WEST;
		gbc_lblMonto.insets = new Insets(0, 0, 5, 5);
		gbc_lblMonto.gridx = 1;
		gbc_lblMonto.gridy = 8;
		getContentPane().add(lblMonto, gbc_lblMonto);
		
		textFieldMonto = new JTextField();
		textFieldMonto.setEditable(false);
		textFieldMonto.setColumns(10);
		GridBagConstraints gbc_textFieldMonto = new GridBagConstraints();
		gbc_textFieldMonto.gridwidth = 3;
		gbc_textFieldMonto.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldMonto.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldMonto.gridx = 2;
		gbc_textFieldMonto.gridy = 8;
		getContentPane().add(textFieldMonto, gbc_textFieldMonto);
		
		
		// ---------------------  LISTA DE REGISTROS DE PATROCINIO ----------------------------------------
		JLabel lblRegistros = new JLabel("Registros: ");
		GridBagConstraints gbc_lblRegistros= new GridBagConstraints();
		gbc_lblRegistros.anchor = GridBagConstraints.WEST;
		gbc_lblRegistros.insets = new Insets(0, 0, 5, 5);
		gbc_lblRegistros.gridx = 6;
		gbc_lblRegistros.gridy = 9;
		getContentPane().add(lblRegistros, gbc_lblRegistros);
		
		textFieldRegistros = new JTextField();
		textFieldRegistros.setEditable(false);
		textFieldRegistros.setColumns(10);
		GridBagConstraints gbc_textFieldRegistros = new GridBagConstraints();
		gbc_textFieldRegistros.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldRegistros.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldRegistros.gridx = 7;
		gbc_textFieldRegistros.gridy = 9;
		getContentPane().add(textFieldRegistros, gbc_textFieldRegistros);
		
		// ---------------------  INFO CODIGO ----------------------------------------
		JLabel lblCodigo = new JLabel("Codigo:");
		GridBagConstraints gbc_lblCodigo = new GridBagConstraints();
		gbc_lblCodigo.anchor = GridBagConstraints.WEST;
		gbc_lblCodigo.insets = new Insets(0, 0, 5, 5);
		gbc_lblCodigo.gridx = 1;
		gbc_lblCodigo.gridy = 9;
		getContentPane().add(lblCodigo, gbc_lblCodigo);
		
		textFieldCodigo = new JTextField();
		textFieldCodigo.setEditable(false);
		textFieldCodigo.setColumns(10);
		GridBagConstraints gbc_textFieldCodigo = new GridBagConstraints();
		gbc_textFieldCodigo.gridwidth = 3;
		gbc_textFieldCodigo.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldCodigo.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldCodigo.gridx = 2;
		gbc_textFieldCodigo.gridy = 9;
		getContentPane().add(textFieldCodigo, gbc_textFieldCodigo);
		
		// --------------------- INFO NIVEL ----------------------------------------
		JLabel lblNivel = new JLabel("Nivel");
		GridBagConstraints gbc_lblNivel= new GridBagConstraints();
		gbc_lblNivel.anchor = GridBagConstraints.WEST;
		gbc_lblNivel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNivel.gridx = 6;
		gbc_lblNivel.gridy = 10;
		getContentPane().add(lblNivel, gbc_lblNivel);
		
		textFieldNivel = new JTextField();
		textFieldNivel.setEditable(false);
		textFieldNivel.setColumns(10);
		GridBagConstraints gbc_textFieldNivel = new GridBagConstraints();
		gbc_textFieldNivel.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldNivel.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldNivel.gridx = 7;
		gbc_textFieldNivel.gridy = 10;
		getContentPane().add(textFieldNivel, gbc_textFieldNivel);
		
		// --------------------- INFO FECHA ----------------------------------------
		JLabel lblFecha = new JLabel("Fecha:");
		GridBagConstraints gbc_lblFecha = new GridBagConstraints();
		gbc_lblFecha.anchor = GridBagConstraints.WEST;
		gbc_lblFecha.insets = new Insets(0, 0, 5, 5);
		gbc_lblFecha.gridx = 1;
		gbc_lblFecha.gridy = 10;
		getContentPane().add(lblFecha, gbc_lblFecha);
		
		textFieldFecha = new JTextField();
		textFieldFecha.setEditable(false);
		textFieldFecha.setColumns(10);
		GridBagConstraints gbc_textFieldFecha = new GridBagConstraints();
		gbc_textFieldFecha.gridwidth = 3;
		gbc_textFieldFecha.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldFecha.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldFecha.gridx = 2;
		gbc_textFieldFecha.gridy = 10;
		getContentPane().add(textFieldFecha, gbc_textFieldFecha);
		// TODO Auto-generated constructor stub
	}

	public void mostrarDatos(String nombreEdicion, String patrocinio) {
		limpiarFormulario();
		String ev = (String) comboBoxEvento.getSelectedItem();
		
		try {
			if (ev == null || ev == "Sin selección") throw new IllegalArgumentException("Seleccione un evento para la consulta.");
			if (ev == "No hay eventos disponibles") throw new IllegalArgumentException("No hay eventos disponibles para cosultar.");
			if (nombreEdicion == null ||nombreEdicion == "" || nombreEdicion == "No hay Ediciones disponibles") throw new IllegalArgumentException("No hay ediciones disponibles para cosultar.");
			if (patrocinio == null|| patrocinio == "No hay Patrocinios disponibles") throw new IllegalArgumentException("No hay patrocinios disponibles para cosultar.");
			
			DTPatrocinio dtp = sistemaEventos.consultarPatrocinioPorInstitucion(nombreEdicion, patrocinio);
			
			String nombreInstitucion = dtp.getNombreInstitucion();
			int monto = dtp.getMonto();
			String codigo = dtp.getCodigo();
			LocalDate fecha = dtp.getFechaAlta();
			int cupos = dtp.getCupos();
			int registros = dtp.getCantRegistros();
			NivelPatrocinio nivel = dtp.getNivel();
			String nivelStr = nivel.name();
			
			String montoStr = String.valueOf(monto);
			String fechaStr = fecha.toString();
			String cuposStr = String.valueOf(cupos);
			String cantRegistrosStr = String.valueOf(registros);
			
			textFieldInstitucion.setText(nombreInstitucion);
			textFieldMonto.setText(montoStr);
			textFieldCodigo.setText(codigo);
			textFieldFecha.setText(fechaStr);
			textFieldCupos.setText(cuposStr);
			textFieldRegistros.setText(cantRegistrosStr);
			textFieldNivel.setText(nivelStr);


		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
		}
		
	}
	
	private void limpiarFormulario() {
		textFieldInstitucion.setText("");
		textFieldCupos.setText("");
		textFieldMonto.setText("");
		textFieldRegistros.setText("");
		textFieldCodigo.setText("");
		textFieldNivel.setText("");
		textFieldFecha.setText("");
		
	}
	
	public void actualizarCombos() {
		actualizarComboDeEventos();
		actualizarComboDeEdiciones();
		actualizarComboDePatrocinios();
	}
	
	private void actualizarComboDeEventos() {
		comboBoxEvento.removeAllItems();
		
		List<String> evs = sistemaEventos.listarEventos();
		if (evs.isEmpty()) {
			comboBoxEvento.addItem("No hay eventos disponibles");
			comboBoxEvento.setSelectedItem("No hay eventos disponibles");
		}else {
			List<String> ListaEventos = new ArrayList<>();
			ListaEventos.add("Sin selección");
			ListaEventos.addAll(evs);
			for (String ev : ListaEventos) {
				comboBoxEvento.addItem(ev);
			}
			comboBoxEvento.setSelectedItem("Sin selección");		
		}
	}	
	
	private void actualizarComboDeEdiciones() {
		comboBoxEdicion.removeAllItems();
		String ev = (String) comboBoxEvento.getSelectedItem();
		
		if (ev == "Sin selección") {
		   	comboBoxEdicion.addItem("Seleccione un evento");
	    	comboBoxEdicion.setSelectedItem("Seleccione un evento");
		}else if(ev == "No hay eventos disponibles"){
			comboBoxEdicion.removeAllItems();
		}else {
			List<String> eds = sistemaEventos.listarEdicionesEvento(ev);			
			if (eds.isEmpty()) {
				comboBoxEdicion.addItem("No hay Ediciones disponibles");
				comboBoxEdicion.setSelectedItem("No hay Ediciones disponibles");
			}else {
				List<String> ListaEdiciones = new ArrayList<>();
				ListaEdiciones.addAll(eds);
				for (String ed : ListaEdiciones) {
					comboBoxEdicion.addItem(ed);
				}
			}
		}
	}


	
	private void actualizarComboDePatrocinios() {
		comboBoxPatrocinio.removeAllItems();
		String ev = (String)  comboBoxEvento.getSelectedItem();
		String ed = (String) comboBoxEdicion.getSelectedItem();
		
		if (ed == "Seleccione un evento" || ed == "No hay Ediciones disponibles" || ev == "Sin selección" || ev == "No hay eventos disponibles") {
			comboBoxPatrocinio.removeAllItems();
		}else {
			List<String> pats = sistemaEventos.listarPatrociniosDeEdicion(ev, ed);
			
			if (pats.isEmpty()) {
				comboBoxPatrocinio.addItem("No hay Patrocinios disponibles");
				comboBoxPatrocinio.setSelectedItem("No hay Patrocinios disponibles");
			}else {
				List<String> ListaPatrocinios = new ArrayList<>();
				ListaPatrocinios.addAll(pats);
				for (String pat : ListaPatrocinios) {
					comboBoxPatrocinio.addItem(pat);
				}
			}
		}
    	
	}
	
	
	// --------------- Clean all --------------- //

	public void limpiarTodo() {
		limpiarFormulario();
		comboBoxEvento.removeAllItems();
		comboBoxEdicion.removeAllItems();
	}
}
