package presentacionSwingApp;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import excepciones.UsuarioNoExisteException;
import logica.DTAsistente;
import logica.DTOrganizador;
import logica.DTUsuario;
import logica.IUsuariosController;

public class ModificarDatosDeUsuario extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JTextField textFieldNombre;
	private JTextField textFieldNacimiento;
	private JTextField textFieldApellido;
	private JTextField textFieldSitioWeb;
	private JTextField textFieldDescripcion;

	private JLabel lblInfoNombre;
	private JLabel lblNacimiento;
	private JLabel lblApellido;
	private JLabel lblSitioWeb;
	private JLabel lblDescripcion;

	JComboBox<String> comboBoxUsuario;

	private IUsuariosController controlUsr;

	public ModificarDatosDeUsuario(IUsuariosController icu) {
		controlUsr = icu;

		setTitle("Modificar Datos de Usuario");
		setResizable(true);
		setMaximizable(true);
		setIconifiable(true);
		setClosable(true);
		setBounds(100, 100, 450, 300);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 51, 100, 98, 35, 0 };
		gridBagLayout.rowHeights = new int[] { 20, 0, 0, 30, 30, 30, 20, 31, -21, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		getContentPane().setLayout(gridBagLayout);

		JLabel lblSelecUsuario = new JLabel("Seleccione Usuario:");
		GridBagConstraints gbc_lblSelecUsuario = new GridBagConstraints();
		gbc_lblSelecUsuario.anchor = GridBagConstraints.EAST;
		gbc_lblSelecUsuario.insets = new Insets(0, 0, 5, 5);
		gbc_lblSelecUsuario.gridx = 0;
		gbc_lblSelecUsuario.gridy = 1;
		getContentPane().add(lblSelecUsuario, gbc_lblSelecUsuario);

		comboBoxUsuario = new JComboBox<>();
		GridBagConstraints gbc_comboBoxUsuario = new GridBagConstraints();
		gbc_comboBoxUsuario.gridwidth = 2;
		gbc_comboBoxUsuario.insets = new Insets(0, 0, 5, 5);
		gbc_comboBoxUsuario.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxUsuario.gridx = 1;
		gbc_comboBoxUsuario.gridy = 1;
		getContentPane().add(comboBoxUsuario, gbc_comboBoxUsuario);

		comboBoxUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String usuarioSeleccionado = (String) comboBoxUsuario.getSelectedItem();
				if (usuarioSeleccionado == null)
					return;
				if (usuarioSeleccionado != null) {
					boolean esOrganizador = controlUsr.esOrganizador(usuarioSeleccionado);
					lblInfoNombre.setVisible(true);
					textFieldNombre.setVisible(true);
					if (esOrganizador) {
						DTOrganizador Organizador = controlUsr.getOrganizador(usuarioSeleccionado);

						textFieldDescripcion.setVisible(true);
						textFieldSitioWeb.setVisible(true);

						textFieldNombre.setText(Organizador.getNombre());
						textFieldSitioWeb.setText(Organizador.getSitioWeb());
						textFieldDescripcion.setText(Organizador.getDescripcion());

						lblDescripcion.setVisible(true);
						lblSitioWeb.setVisible(true);

						textFieldApellido.setVisible(false);
						textFieldNacimiento.setVisible(false);

						lblApellido.setVisible(false);
						lblNacimiento.setVisible(false);

					} else {
						DTAsistente Asistente = controlUsr.getAsistente(usuarioSeleccionado);
						String nombre = Asistente.getNombre();

						textFieldNombre.setText(nombre);
						textFieldApellido.setText(Asistente.getApellido());
						textFieldNacimiento.setText(Asistente.getNacimiento().toString());

						textFieldDescripcion.setVisible(false);
						textFieldSitioWeb.setVisible(false);

						lblDescripcion.setVisible(false);
						lblSitioWeb.setVisible(false);

						textFieldApellido.setVisible(true);
						textFieldNacimiento.setVisible(true);

						lblApellido.setVisible(true);
						lblNacimiento.setVisible(true);
					}
				}
			}
		});

		lblInfoNombre = new JLabel("Nombre:");
		GridBagConstraints gbc_lblInfoNombre = new GridBagConstraints();
		gbc_lblInfoNombre.insets = new Insets(0, 0, 5, 5);
		gbc_lblInfoNombre.anchor = GridBagConstraints.EAST;
		gbc_lblInfoNombre.gridx = 0;
		gbc_lblInfoNombre.gridy = 3;
		getContentPane().add(lblInfoNombre, gbc_lblInfoNombre);

		textFieldNombre = new JTextField();
		textFieldNombre.setText("Nombre Actual");
		GridBagConstraints gbc_textFieldNombre = new GridBagConstraints();
		gbc_textFieldNombre.gridwidth = 2;
		gbc_textFieldNombre.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldNombre.fill = GridBagConstraints.BOTH;
		gbc_textFieldNombre.gridx = 1;
		gbc_textFieldNombre.gridy = 3;
		getContentPane().add(textFieldNombre, gbc_textFieldNombre);
		textFieldNombre.setColumns(10);

		lblApellido = new JLabel("Apellido:");
		GridBagConstraints gbc_lblApellido = new GridBagConstraints();
		gbc_lblApellido.insets = new Insets(0, 0, 5, 5);
		gbc_lblApellido.anchor = GridBagConstraints.EAST;
		gbc_lblApellido.gridx = 0;
		gbc_lblApellido.gridy = 4;
		getContentPane().add(lblApellido, gbc_lblApellido);

		textFieldApellido = new JTextField();
		textFieldApellido.setText("Apellido Actual");
		GridBagConstraints gbc_textFieldApellido = new GridBagConstraints();
		gbc_textFieldApellido.gridwidth = 2;
		gbc_textFieldApellido.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldApellido.fill = GridBagConstraints.BOTH;
		gbc_textFieldApellido.gridx = 1;
		gbc_textFieldApellido.gridy = 4;
		getContentPane().add(textFieldApellido, gbc_textFieldApellido);
		textFieldApellido.setColumns(10);

		lblDescripcion = new JLabel("Descripcion:");
		GridBagConstraints gbc_lblDescripcion = new GridBagConstraints();
		gbc_lblDescripcion.insets = new Insets(0, 0, 5, 5);
		gbc_lblDescripcion.anchor = GridBagConstraints.EAST;
		gbc_lblDescripcion.gridx = 0;
		gbc_lblDescripcion.gridy = 4;
		getContentPane().add(lblDescripcion, gbc_lblDescripcion);

		textFieldDescripcion = new JTextField();
		textFieldDescripcion.setText("Descripcion");
		GridBagConstraints gbc_textFieldDescripcion = new GridBagConstraints();
		gbc_textFieldDescripcion.gridwidth = 2;
		gbc_textFieldDescripcion.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldDescripcion.fill = GridBagConstraints.BOTH;
		gbc_textFieldDescripcion.gridx = 1;
		gbc_textFieldDescripcion.gridy = 4;
		getContentPane().add(textFieldDescripcion, gbc_textFieldDescripcion);
		textFieldDescripcion.setColumns(10);

		lblNacimiento = new JLabel("Nacimiento");
		GridBagConstraints gbc_lblNacimiento = new GridBagConstraints();
		gbc_lblNacimiento.insets = new Insets(0, 0, 5, 5);
		gbc_lblNacimiento.anchor = GridBagConstraints.EAST;
		gbc_lblNacimiento.gridx = 0;
		gbc_lblNacimiento.gridy = 5;
		getContentPane().add(lblNacimiento, gbc_lblNacimiento);

		textFieldNacimiento = new JTextField();
		textFieldNacimiento.setText("Nacimiento Actual");
		GridBagConstraints gbc_textFieldNacimiento = new GridBagConstraints();
		gbc_textFieldNacimiento.fill = GridBagConstraints.BOTH;
		gbc_textFieldNacimiento.gridwidth = 2;
		gbc_textFieldNacimiento.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldNacimiento.gridx = 1;
		gbc_textFieldNacimiento.gridy = 5;
		getContentPane().add(textFieldNacimiento, gbc_textFieldNacimiento);
		textFieldNacimiento.setColumns(10);

		lblSitioWeb = new JLabel("Sitio Web:");
		GridBagConstraints gbc_lblSitioWeb = new GridBagConstraints();
		gbc_lblSitioWeb.insets = new Insets(0, 0, 5, 5);
		gbc_lblSitioWeb.anchor = GridBagConstraints.EAST;
		gbc_lblSitioWeb.gridx = 0;
		gbc_lblSitioWeb.gridy = 5;
		getContentPane().add(lblSitioWeb, gbc_lblSitioWeb);

		textFieldSitioWeb = new JTextField();
		textFieldSitioWeb.setText("Sitio web ");
		GridBagConstraints gbc_textFieldSitioWeb = new GridBagConstraints();
		gbc_textFieldSitioWeb.fill = GridBagConstraints.BOTH;
		gbc_textFieldSitioWeb.gridwidth = 2;
		gbc_textFieldSitioWeb.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldSitioWeb.gridx = 1;
		gbc_textFieldSitioWeb.gridy = 5;
		getContentPane().add(textFieldSitioWeb, gbc_textFieldSitioWeb);
		textFieldNacimiento.setColumns(10);

		JButton btnAceptar = new JButton("Aceptar");
		GridBagConstraints gbc_btnAceptar = new GridBagConstraints();
		gbc_btnAceptar.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnAceptar.insets = new Insets(0, 0, 5, 5);
		gbc_btnAceptar.gridx = 1;
		gbc_btnAceptar.gridy = 7;
		getContentPane().add(btnAceptar, gbc_btnAceptar);

		btnAceptar.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				String usuarioSeleccionado = (String) comboBoxUsuario.getSelectedItem();

				if (usuarioSeleccionado != null) {
					boolean esOrganizador = controlUsr.esOrganizador(usuarioSeleccionado);
					if (esOrganizador) {
						String nombre = textFieldNombre.getText();
						String descripcion = textFieldDescripcion.getText();
						String sitioWeb = textFieldSitioWeb.getText();
						controlUsr.editarOrganizador(usuarioSeleccionado, nombre, descripcion, sitioWeb,"","");
						JOptionPane.showMessageDialog(null, "Modificación exitosa.");
						setVisible(false);
						limpiarFormularioOrg();
					} else {
						String nombre = textFieldNombre.getText();
						String apellido = textFieldApellido.getText();
						String nacimientoStr = textFieldNacimiento.getText();
						DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
						LocalDate fechaNacimiento = LocalDate.parse(nacimientoStr, formatter);

						controlUsr.editarAsistente(usuarioSeleccionado, nombre, apellido, fechaNacimiento,"","");
						JOptionPane.showMessageDialog(null, "Modificación exitosa.");
						setVisible(false);
						limpiarFormularioAsist();
					}
				} else {
					setVisible(false);
				}
				comboBoxUsuario.setSelectedIndex(-1);
				limpiarFormularioAsist();
				limpiarFormularioOrg();
			}

		});

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpiarFormularioAsist();
				limpiarFormularioOrg();
				setVisible(false);
			}
		});
		GridBagConstraints gbc_btnCancelar = new GridBagConstraints();
		gbc_btnCancelar.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnCancelar.insets = new Insets(0, 0, 5, 5);
		gbc_btnCancelar.gridx = 2;
		gbc_btnCancelar.gridy = 7;
		getContentPane().add(btnCancelar, gbc_btnCancelar);

		textFieldNombre.setVisible(false);
		textFieldNacimiento.setVisible(false);
		textFieldApellido.setVisible(false);
		textFieldSitioWeb.setVisible(false);
		textFieldDescripcion.setVisible(false);
		lblInfoNombre.setVisible(false);
		lblNacimiento.setVisible(false);
		lblApellido.setVisible(false);
		lblSitioWeb.setVisible(false);
		lblDescripcion.setVisible(false);

	}

	public void cargarUsuarios() {
		DTUsuario[] usrsArray = null;
		try {
			usrsArray = controlUsr.getUsuarios();
			Arrays.sort(usrsArray, Comparator.comparing(
        		    DTUsuario::getNombre, String.CASE_INSENSITIVE_ORDER
        		));
		} catch (UsuarioNoExisteException e) {
			e.printStackTrace();
		}

		Vector<String> usuarios = new Vector<>();
		if (usrsArray != null) {
			for (DTUsuario usr : usrsArray) {
				usuarios.add(usr.getNickname());
			}
		}

		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(usuarios);
		comboBoxUsuario.setModel(model);
		SwingUtilities.invokeLater(() -> comboBoxUsuario.setSelectedIndex(-1));
	}

	private void limpiarFormularioOrg() {
		textFieldNombre.setText("");
		textFieldDescripcion.setText("");
		textFieldSitioWeb.setText("");
	};

	private void limpiarFormularioAsist() {
		textFieldNombre.setText("");
		textFieldApellido.setText("");
		textFieldNacimiento.setText("");
	}
}
