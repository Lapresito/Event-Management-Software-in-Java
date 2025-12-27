package presentacionSwingApp;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import logica.IEventosController;

public class AltaDeTipoDeRegistro extends JInternalFrame {

	private IEventosController IEvController;

	private static final long serialVersionUID = 1L;

	private JTextField textFieldNombre;
	private JTextField textFieldDescripcion;
	private JTextField textFieldCupo;
	private JTextField textFieldCosto;

	private JComboBox<String> comboBoxEvento;
	private JComboBox<String> comboBoxEdicion;

	// --------------- Validation --------------- //

	public boolean validarCampos(String nombreTipoRegistro, String descripcion, String nombreEdicion, String costoStr,
			String cupoStr) {

		String nombreEvento = (String) comboBoxEvento.getSelectedItem();

		if (nombreEvento == "Sin selección") {
			JOptionPane.showMessageDialog(null, "Ingrese un evento.", "Error", JOptionPane.ERROR_MESSAGE);
		}

		if (nombreTipoRegistro.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Ingrese un nombre para el Tipo de Registro.", "Error",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		if (descripcion.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Ingrese una descripción.", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		if (nombreEdicion == null || nombreEdicion.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Seleccione una Edición.", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		if (costoStr.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Ingrese un costo.", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		if (cupoStr.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Ingrese el cupo.", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		try {
			Integer.parseInt(costoStr);
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(null, "Costo debe ser un número sin decimales.", "Error",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
		try {
			Integer.parseInt(cupoStr);
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(null, "Cupo debe ser un número sin decimales.", "Error",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		return true;
	}

	public AltaDeTipoDeRegistro(IEventosController IEv) {

		IEvController = IEv;
		setTitle("Alta de Tipo de Registro");
		setResizable(true);
		setIconifiable(true);
		setMaximizable(true);
		setClosable(true);
		setBounds(100, 100, 400, 350);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 51, 100, 98, 35, 0 };
		gridBagLayout.rowHeights = new int[] { 20, 0, 0, 0, 35, 35, 35, 35, 30, 30, 35, 59, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0,
				Double.MIN_VALUE };
		getContentPane().setLayout(gridBagLayout);

		// --------------- Event select --------------- //

		JLabel lblSelecEvento = new JLabel("Seleccione Evento:");
		GridBagConstraints gbc_lblSelecEvento = new GridBagConstraints();
		gbc_lblSelecEvento.anchor = GridBagConstraints.EAST;
		gbc_lblSelecEvento.insets = new Insets(0, 0, 5, 5);
		gbc_lblSelecEvento.gridx = 0;
		gbc_lblSelecEvento.gridy = 1;
		getContentPane().add(lblSelecEvento, gbc_lblSelecEvento);

		comboBoxEvento = new JComboBox<>();
		GridBagConstraints gbc_comboBoxEvento = new GridBagConstraints();
		gbc_comboBoxEvento.gridwidth = 2;
		gbc_comboBoxEvento.insets = new Insets(0, 0, 5, 5);
		gbc_comboBoxEvento.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxEvento.gridx = 1;
		gbc_comboBoxEvento.gridy = 1;
		getContentPane().add(comboBoxEvento, gbc_comboBoxEvento);

		// --------------- Load editions into comboBoxEdicion --------------- //

		comboBoxEvento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String eventoSeleccionado = (String) comboBoxEvento.getSelectedItem();
				if (eventoSeleccionado != null) {
					comboBoxEdicion.removeAllItems();
					List<String> ediciones = IEvController.listarEdicionesEvento(eventoSeleccionado);

					Collections.sort(ediciones, String.CASE_INSENSITIVE_ORDER);
					ediciones.addFirst("Sin selección");
					for (String nombreEdicion : ediciones) {
						comboBoxEdicion.addItem(nombreEdicion);
					}
					comboBoxEdicion.setSelectedIndex(0);
				}
			}
		});

		// --------------- Edition select --------------- //

		JLabel lblSelecEdicion = new JLabel("Seleccione Edición:");
		GridBagConstraints gbc_lblSelecEdicion = new GridBagConstraints();
		gbc_lblSelecEdicion.insets = new Insets(0, 0, 5, 5);
		gbc_lblSelecEdicion.anchor = GridBagConstraints.EAST;
		gbc_lblSelecEdicion.gridx = 0;
		gbc_lblSelecEdicion.gridy = 2;
		getContentPane().add(lblSelecEdicion, gbc_lblSelecEdicion);

		comboBoxEdicion = new JComboBox<>();
		GridBagConstraints gbc_comboBoxEdicion = new GridBagConstraints();
		gbc_comboBoxEdicion.gridwidth = 2;
		gbc_comboBoxEdicion.insets = new Insets(0, 0, 5, 5);
		gbc_comboBoxEdicion.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxEdicion.gridx = 1;
		gbc_comboBoxEdicion.gridy = 2;
		getContentPane().add(comboBoxEdicion, gbc_comboBoxEdicion);
		comboBoxEdicion.addItem("Sin selección");
		comboBoxEdicion.setSelectedIndex(0);

		// --------------- Name label --------------- //

		JLabel lblInfoNombre = new JLabel("Nombre:");
		GridBagConstraints gbc_lblInfoNombre = new GridBagConstraints();
		gbc_lblInfoNombre.insets = new Insets(0, 0, 5, 5);
		gbc_lblInfoNombre.anchor = GridBagConstraints.EAST;
		gbc_lblInfoNombre.gridx = 0;
		gbc_lblInfoNombre.gridy = 4;
		getContentPane().add(lblInfoNombre, gbc_lblInfoNombre);

		textFieldNombre = new JTextField();
		GridBagConstraints gbc_textFieldNombre = new GridBagConstraints();
		gbc_textFieldNombre.gridwidth = 2;
		gbc_textFieldNombre.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldNombre.fill = GridBagConstraints.BOTH;
		gbc_textFieldNombre.gridx = 1;
		gbc_textFieldNombre.gridy = 4;
		getContentPane().add(textFieldNombre, gbc_textFieldNombre);
		textFieldNombre.setColumns(10);

		// --------------- Description label --------------- //

		JLabel lblInfoDescripcion = new JLabel("Descripción:");
		GridBagConstraints gbc_lblInfoDescripcion = new GridBagConstraints();
		gbc_lblInfoDescripcion.insets = new Insets(0, 0, 5, 5);
		gbc_lblInfoDescripcion.anchor = GridBagConstraints.EAST;
		gbc_lblInfoDescripcion.gridx = 0;
		gbc_lblInfoDescripcion.gridy = 5;
		getContentPane().add(lblInfoDescripcion, gbc_lblInfoDescripcion);

		textFieldDescripcion = new JTextField();
		GridBagConstraints gbc_textFieldDescripcion = new GridBagConstraints();
		gbc_textFieldDescripcion.gridwidth = 2;
		gbc_textFieldDescripcion.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldDescripcion.fill = GridBagConstraints.BOTH;
		gbc_textFieldDescripcion.gridx = 1;
		gbc_textFieldDescripcion.gridy = 5;
		getContentPane().add(textFieldDescripcion, gbc_textFieldDescripcion);
		textFieldDescripcion.setColumns(10);

		// --------------- Cost label --------------- //

		JLabel lblCosto = new JLabel("Costo:");
		GridBagConstraints gbc_lblCosto = new GridBagConstraints();
		gbc_lblCosto.insets = new Insets(0, 0, 5, 5);
		gbc_lblCosto.anchor = GridBagConstraints.EAST;
		gbc_lblCosto.gridx = 0;
		gbc_lblCosto.gridy = 6;
		getContentPane().add(lblCosto, gbc_lblCosto);

		textFieldCosto = new JTextField();
		textFieldCosto.setColumns(10);
		GridBagConstraints gbc_textFieldCosto = new GridBagConstraints();
		gbc_textFieldCosto.gridwidth = 2;
		gbc_textFieldCosto.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldCosto.fill = GridBagConstraints.BOTH;
		gbc_textFieldCosto.gridx = 1;
		gbc_textFieldCosto.gridy = 6;
		getContentPane().add(textFieldCosto, gbc_textFieldCosto);

		// --------------- Slot label --------------- //

		JLabel lblCupo = new JLabel("Cupo:");
		GridBagConstraints gbc_lblCupo = new GridBagConstraints();
		gbc_lblCupo.insets = new Insets(0, 0, 5, 5);
		gbc_lblCupo.anchor = GridBagConstraints.EAST;
		gbc_lblCupo.gridx = 0;
		gbc_lblCupo.gridy = 7;
		getContentPane().add(lblCupo, gbc_lblCupo);

		textFieldCupo = new JTextField();
		textFieldCupo.setColumns(10);
		GridBagConstraints gbc_textFieldCupo = new GridBagConstraints();
		gbc_textFieldCupo.gridwidth = 2;
		gbc_textFieldCupo.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldCupo.fill = GridBagConstraints.BOTH;
		gbc_textFieldCupo.gridx = 1;
		gbc_textFieldCupo.gridy = 7;
		getContentPane().add(textFieldCupo, gbc_textFieldCupo);

		// --------------- Accept button --------------- //

		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				String costoStr = textFieldCosto.getText().trim();
				String cupoStr = textFieldCupo.getText().trim();
				String nombreTipoRegistro = textFieldNombre.getText();
				String nombreEdicion = (String) comboBoxEdicion.getSelectedItem();
				String descripcion = textFieldDescripcion.getText();

				if (!validarCampos(nombreTipoRegistro, descripcion, nombreEdicion, costoStr, cupoStr)) {
					return;
				}

				int costo = Integer.parseInt(textFieldCosto.getText());
				int cupo = Integer.parseInt(textFieldCupo.getText());

				try {
					if (nombreEdicion == "Sin selección") {
						throw new IOException("Seleccione una edición");
					}
					IEvController.altaTipoDeRegistro(nombreTipoRegistro, nombreEdicion, descripcion, costo, cupo);
					JOptionPane.showMessageDialog(null, "Tipo de registro agregado con éxito.");
					setVisible(false);
					limpiarFormulario();
				} catch (IOException err) {
					JOptionPane.showMessageDialog(null, err.getMessage());
				}catch (Exception err) {
					JOptionPane.showMessageDialog(AltaDeTipoDeRegistro.this, err.getMessage(),"Error",JOptionPane.INFORMATION_MESSAGE);

				}
			}

		});
		GridBagConstraints gbc_btnAceptar = new GridBagConstraints();
		gbc_btnAceptar.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnAceptar.insets = new Insets(0, 0, 5, 5);
		gbc_btnAceptar.gridx = 1;
		gbc_btnAceptar.gridy = 9;
		getContentPane().add(btnAceptar, gbc_btnAceptar);

		// --------------- Cancel button --------------- //

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpiarTodo();
				setVisible(false);
			}
		});
		GridBagConstraints gbc_btnCancelar = new GridBagConstraints();
		gbc_btnCancelar.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnCancelar.insets = new Insets(0, 0, 5, 5);
		gbc_btnCancelar.gridx = 2;
		gbc_btnCancelar.gridy = 9;
		getContentPane().add(btnCancelar, gbc_btnCancelar);

	}

	// --------------- Clean all --------------- //

	public void limpiarTodo() {
		limpiarFormulario();
		comboBoxEvento.removeAllItems();
		comboBoxEdicion.removeAllItems();
	}

	// --------------- Clean form --------------- //

	private void limpiarFormulario() {
		textFieldNombre.setText("");
		textFieldDescripcion.setText("");
		textFieldCosto.setText("");
		textFieldCupo.setText("");
		comboBoxEvento.setSelectedIndex(0);
		comboBoxEdicion.setSelectedIndex(0);
	}

	// --------------- Load data --------------- //

	public void cargarEventos() {
		Vector<String> eventos = new Vector<>(IEvController.listarEventos());
		Collections.sort(eventos);
		eventos.addFirst("Sin selección");
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(eventos);
		comboBoxEvento.setModel(model);

		List<String> emptyList = new ArrayList<>();
		Vector<String> ediciones = new Vector<>(emptyList);

		ediciones.addFirst("Sin selección");
		DefaultComboBoxModel<String> model2 = new DefaultComboBoxModel<>(ediciones);
		comboBoxEdicion.setModel(model2);
		SwingUtilities.invokeLater(() -> comboBoxEdicion.setSelectedIndex(0));
	}

}
