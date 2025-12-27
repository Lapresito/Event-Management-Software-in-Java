package presentacionSwingApp;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import logica.DTTipoRegistro;
import logica.Fabrica;
import logica.IEventosController;

public class ConsultaTipoRegistro extends JInternalFrame {
	private Fabrica fabrica = Fabrica.getInstance();
	private IEventosController sistemaEventos = fabrica.getIControladorEventos();
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textFieldEdicion;
	private JTextField textFieldCupos;
	private JTextField textFieldPrecio;
	private JTextField textFieldDescripcion;
	private JTextField textFieldRegistro;
	private JTextField textFieldRegistrados;

	private JComboBox<String> comboBoxEvento;
	private JComboBox<String> comboBoxEdicion;
	private JComboBox<String> comboBoxTiposDeRegistro;

	public boolean validarSeleccion(JComboBox<String> comboBoxEvento, JComboBox<String> comboBoxEdicion,
			JComboBox<String> comboBoxTipoDeRegistro) {

		String nombreEvento = (String) comboBoxEvento.getSelectedItem();
		String nombreEdicion = (String) comboBoxEdicion.getSelectedItem();
		String nombreTipoDeRegistro = (String) comboBoxTipoDeRegistro.getSelectedItem();

		if (nombreEvento == "Sin selección") {
			JOptionPane.showMessageDialog(this, "Debe seleccionar un evento", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		if (nombreEdicion == "Sin selección") {
			JOptionPane.showMessageDialog(this, "Debe seleccionar una edición", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		if (nombreTipoDeRegistro == "Sin selección") {
			JOptionPane.showMessageDialog(this, "Debe seleccionar un tipo de registro", "Error",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		return true; // pasó todas las validaciones
	}

	public ConsultaTipoRegistro(IEventosController IEv) {

		sistemaEventos = IEv;

		setMaximizable(true);
		setIconifiable(true);
		setClosable(true);
		setResizable(true);
		setTitle("Consulta de Tipo de registro");
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 33, 55, 30, 30, 60, 30, 63, 91, 30 };
		gridBagLayout.rowHeights = new int[] { 15, 30, 30, 30, 22, 10, 30, 0, 0, 0, 50, 30, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0 };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 1.0, 1.0,
				Double.MIN_VALUE };
		getContentPane().setLayout(gridBagLayout);

		JLabel lblNewLabel_1 = new JLabel("");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 4;
		gbc_lblNewLabel_1.gridy = 0;
		getContentPane().add(lblNewLabel_1, gbc_lblNewLabel_1);

		// --------------- Event select --------------- //

		JLabel lblEvento = new JLabel("Seleccionar un evento:");
		GridBagConstraints gbc_lblEvento = new GridBagConstraints();
		gbc_lblEvento.gridwidth = 4;
		gbc_lblEvento.anchor = GridBagConstraints.WEST;
		gbc_lblEvento.insets = new Insets(0, 0, 5, 5);
		gbc_lblEvento.gridx = 1;
		gbc_lblEvento.gridy = 1;
		getContentPane().add(lblEvento, gbc_lblEvento);

		comboBoxEvento = new JComboBox<>();
		GridBagConstraints gbc_comboBoxEvento = new GridBagConstraints();
		gbc_comboBoxEvento.gridwidth = 3;
		gbc_comboBoxEvento.insets = new Insets(0, 0, 5, 5);
		gbc_comboBoxEvento.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxEvento.gridx = 5;
		gbc_comboBoxEvento.gridy = 1;
		getContentPane().add(comboBoxEvento, gbc_comboBoxEvento);

		// --------------- Load Editions for selected Event --------------- //

		comboBoxEvento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String eventoSeleccionado = (String) comboBoxEvento.getSelectedItem();
				if (eventoSeleccionado != null) {
					comboBoxEdicion.removeAllItems();
					List<String> ediciones = sistemaEventos.listarEdicionesEvento(eventoSeleccionado);
					Collections.sort(ediciones);
					ediciones.addFirst("Sin selección");
					for (String nombreEdicion : ediciones) {
						comboBoxEdicion.addItem(nombreEdicion);
					}
					comboBoxEdicion.setSelectedIndex(0);
				}
			}
		});

		// --------------- Edition select --------------- //

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

		// --------------- Load TiposDeRegistro for selected Edition --------------- //

		comboBoxEdicion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String edicionSeleccionada = (String) comboBoxEdicion.getSelectedItem();
				cargarTPs(edicionSeleccionada);
			}
		});

		// --------------- TiposDeRegistro select --------------- //

		JLabel lblSeleccionarUnarUn = new JLabel("Seleccionar un Tipo de Registro:");
		GridBagConstraints gbc_lblSeleccionarUnarUn = new GridBagConstraints();
		gbc_lblSeleccionarUnarUn.gridwidth = 4;
		gbc_lblSeleccionarUnarUn.anchor = GridBagConstraints.WEST;
		gbc_lblSeleccionarUnarUn.insets = new Insets(0, 0, 5, 5);
		gbc_lblSeleccionarUnarUn.gridx = 1;
		gbc_lblSeleccionarUnarUn.gridy = 3;
		getContentPane().add(lblSeleccionarUnarUn, gbc_lblSeleccionarUnarUn);

		comboBoxTiposDeRegistro = new JComboBox<>();
		GridBagConstraints gbc_comboBoxTiposDeRegistro = new GridBagConstraints();
		gbc_comboBoxTiposDeRegistro.gridwidth = 3;
		gbc_comboBoxTiposDeRegistro.insets = new Insets(0, 0, 5, 5);
		gbc_comboBoxTiposDeRegistro.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxTiposDeRegistro.gridx = 5;
		gbc_comboBoxTiposDeRegistro.gridy = 3;
		getContentPane().add(comboBoxTiposDeRegistro, gbc_comboBoxTiposDeRegistro);

		// --------------- Request button --------------- //

		JButton btnConsultar = new JButton("Consultar");
		GridBagConstraints gbc_btnConsultar = new GridBagConstraints();
		gbc_btnConsultar.gridwidth = 2;
		gbc_btnConsultar.anchor = GridBagConstraints.EAST;
		gbc_btnConsultar.insets = new Insets(0, 0, 5, 5);
		gbc_btnConsultar.gridx = 6;
		gbc_btnConsultar.gridy = 4;
		getContentPane().add(btnConsultar, gbc_btnConsultar);

		// --------------- Request button ActionListener --------------- //

		btnConsultar.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				limpiarFormulario();

				if (!validarSeleccion(comboBoxEvento, comboBoxEdicion, comboBoxTiposDeRegistro)) {
					return;
				}

				String nombreEdicion = (String) comboBoxEdicion.getSelectedItem();
				String nombreTipoRegistro = (String) comboBoxTiposDeRegistro.getSelectedItem();

				try {
					DTTipoRegistro dto = sistemaEventos.consultaTipoDeRegistro(nombreEdicion, nombreTipoRegistro);

					int cupo = dto.getCupo();
					String cupoStr = Integer.toString(cupo);

					int precio = dto.getPrecio();
					String precioStr = Integer.toString(precio);

					String descripcion = dto.getDescripcion();
					int cantRegistrados = dto.getCantRegistros();
					String cantRegistradosStr = Integer.toString(cantRegistrados);

					textFieldEdicion.setText(nombreEdicion);
					textFieldCupos.setText(cupoStr);
					textFieldPrecio.setText(precioStr);
					textFieldDescripcion.setText(descripcion);
					textFieldRegistro.setText(nombreTipoRegistro);
					textFieldRegistrados.setText(cantRegistradosStr);

				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
				}
			}

		});

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

		// --------------- Slots text info --------------- //

		JLabel lblCupos = new JLabel("Cupos:");
		GridBagConstraints gbc_lblCupos = new GridBagConstraints();
		gbc_lblCupos.anchor = GridBagConstraints.WEST;
		gbc_lblCupos.insets = new Insets(0, 0, 5, 5);
		gbc_lblCupos.gridx = 6;
		gbc_lblCupos.gridy = 7;
		getContentPane().add(lblCupos, gbc_lblCupos);

		textFieldCupos = new JTextField();
		textFieldCupos.setEditable(false);
		textFieldCupos.setColumns(10);
		GridBagConstraints gbc_textFieldCupos = new GridBagConstraints();
		gbc_textFieldCupos.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldCupos.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldCupos.gridx = 7;
		gbc_textFieldCupos.gridy = 7;
		getContentPane().add(textFieldCupos, gbc_textFieldCupos);

		// --------------- Record text info --------------- //

		JLabel lblRegistro = new JLabel("Tipo Registro: ");
		GridBagConstraints gbc_lblRegistro = new GridBagConstraints();
		gbc_lblRegistro.anchor = GridBagConstraints.WEST;
		gbc_lblRegistro.insets = new Insets(0, 0, 5, 5);
		gbc_lblRegistro.gridx = 1;
		gbc_lblRegistro.gridy = 7;
		getContentPane().add(lblRegistro, gbc_lblRegistro);

		textFieldRegistro = new JTextField();
		textFieldRegistro.setEditable(false);
		textFieldRegistro.setColumns(10);
		GridBagConstraints gbc_textFieldRegistro = new GridBagConstraints();
		gbc_textFieldRegistro.gridwidth = 3;
		gbc_textFieldRegistro.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldRegistro.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldRegistro.gridx = 2;
		gbc_textFieldRegistro.gridy = 7;
		getContentPane().add(textFieldRegistro, gbc_textFieldRegistro);

		// --------------- Total Records text info --------------- //

		JLabel lblRegistrados = new JLabel("Registrados:");
		GridBagConstraints gbc_lblRegistrados = new GridBagConstraints();
		gbc_lblRegistrados.anchor = GridBagConstraints.WEST;
		gbc_lblRegistrados.insets = new Insets(0, 0, 5, 5);
		gbc_lblRegistrados.gridx = 6;
		gbc_lblRegistrados.gridy = 8;
		getContentPane().add(lblRegistrados, gbc_lblRegistrados);

		textFieldRegistrados = new JTextField();
		textFieldRegistrados.setEditable(false);
		textFieldRegistrados.setColumns(10);
		GridBagConstraints gbc_textFieldRegistrados = new GridBagConstraints();
		gbc_textFieldRegistrados.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldRegistrados.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldRegistrados.gridx = 7;
		gbc_textFieldRegistrados.gridy = 8;
		getContentPane().add(textFieldRegistrados, gbc_textFieldRegistrados);

		// --------------- Price text info --------------- //

		JLabel lblPrecio = new JLabel("Precio:");
		GridBagConstraints gbc_lblPrecio = new GridBagConstraints();
		gbc_lblPrecio.anchor = GridBagConstraints.WEST;
		gbc_lblPrecio.insets = new Insets(0, 0, 5, 5);
		gbc_lblPrecio.gridx = 1;
		gbc_lblPrecio.gridy = 8;
		getContentPane().add(lblPrecio, gbc_lblPrecio);

		textFieldPrecio = new JTextField();
		textFieldPrecio.setEditable(false);
		textFieldPrecio.setColumns(10);
		GridBagConstraints gbc_textFieldPrecio = new GridBagConstraints();
		gbc_textFieldPrecio.gridwidth = 3;
		gbc_textFieldPrecio.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldPrecio.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldPrecio.gridx = 2;
		gbc_textFieldPrecio.gridy = 8;
		getContentPane().add(textFieldPrecio, gbc_textFieldPrecio);

		// --------------- Edition text info --------------- //

		JLabel lblEdicion = new JLabel("Edicion:");
		GridBagConstraints gbc_lblEdicion = new GridBagConstraints();
		gbc_lblEdicion.anchor = GridBagConstraints.WEST;
		gbc_lblEdicion.insets = new Insets(0, 0, 5, 5);
		gbc_lblEdicion.gridx = 1;
		gbc_lblEdicion.gridy = 9;
		getContentPane().add(lblEdicion, gbc_lblEdicion);

		textFieldEdicion = new JTextField();
		textFieldEdicion.setEditable(false);
		GridBagConstraints gbc_textFieldEdicion = new GridBagConstraints();
		gbc_textFieldEdicion.gridwidth = 6;
		gbc_textFieldEdicion.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldEdicion.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldEdicion.gridx = 2;
		gbc_textFieldEdicion.gridy = 9;
		getContentPane().add(textFieldEdicion, gbc_textFieldEdicion);
		textFieldEdicion.setColumns(10);

		// --------------- Description text info --------------- //

		JLabel lblDescripcion = new JLabel("Descripción:");
		GridBagConstraints gbc_lblDescripcion = new GridBagConstraints();
		gbc_lblDescripcion.anchor = GridBagConstraints.WEST;
		gbc_lblDescripcion.insets = new Insets(0, 0, 5, 5);
		gbc_lblDescripcion.gridx = 1;
		gbc_lblDescripcion.gridy = 10;
		getContentPane().add(lblDescripcion, gbc_lblDescripcion);

		textFieldDescripcion = new JTextField();
		textFieldDescripcion.setEditable(false);
		GridBagConstraints gbc_textFieldDescripcion = new GridBagConstraints();
		gbc_textFieldDescripcion.gridwidth = 6;
		gbc_textFieldDescripcion.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldDescripcion.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldDescripcion.gridx = 2;
		gbc_textFieldDescripcion.gridy = 10;
		getContentPane().add(textFieldDescripcion, gbc_textFieldDescripcion);
		textFieldDescripcion.setColumns(10);
		// TODO Auto-generated constructor stub
	}

	public void cargarEventos() {
		Vector<String> eventos = new Vector<>(sistemaEventos.listarEventos());
		Collections.sort(eventos);
		eventos.addFirst("Sin selección");
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(eventos);
		comboBoxEvento.setModel(model);
		SwingUtilities.invokeLater(() -> comboBoxEvento.setSelectedIndex(0));
	}

	private void cargarTPs(String nombreEdicion) {
		Vector<String> tiposDeRegistro = new Vector<>(sistemaEventos.listarTiposDeRegistroDeEdicion(nombreEdicion));
		Collections.sort(tiposDeRegistro);
		tiposDeRegistro.add(0, "Sin selección");
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(tiposDeRegistro);
		comboBoxTiposDeRegistro.setModel(model);
		SwingUtilities.invokeLater(() -> comboBoxTiposDeRegistro.setSelectedIndex(0));
	}

	// --------------- Clean all --------------- //

	public void limpiarTodo() {
		limpiarFormulario();
		comboBoxEvento.setSelectedIndex(-1);
		comboBoxEdicion.setSelectedIndex(-1);
		comboBoxTiposDeRegistro.setSelectedIndex(-1);
		comboBoxEvento.removeAllItems();
		comboBoxEdicion.removeAllItems();
		comboBoxTiposDeRegistro.removeAllItems();
	};

	// --------------- Clean form --------------- //

	private void limpiarFormulario() {
		textFieldEdicion.setText("");
		textFieldRegistro.setText("");
		textFieldPrecio.setText("");
		textFieldDescripcion.setText("");
		textFieldCupos.setText("");
		textFieldRegistrados.setText("");
	}

	public void mostrarDatos(String nombreEdicion, String nombreTipoRegistro) {

		try {
			DTTipoRegistro dto = sistemaEventos.consultaTipoDeRegistro(nombreEdicion, nombreTipoRegistro);

			int cupo = dto.getCupo();
			String cupoStr = Integer.toString(cupo);

			int precio = dto.getPrecio();
			String precioStr = Integer.toString(precio);

			String descripcion = dto.getDescripcion();
			int cantRegistrados = dto.getCantRegistros();
			String cantRegistradosStr = Integer.toString(cantRegistrados);

			textFieldEdicion.setText(nombreEdicion);
			textFieldCupos.setText(cupoStr);
			textFieldPrecio.setText(precioStr);
			textFieldDescripcion.setText(descripcion);
			textFieldRegistro.setText(nombreTipoRegistro);
			textFieldRegistrados.setText(cantRegistradosStr);

		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
		}

	}
}
