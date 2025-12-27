package presentacionSwingApp;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField; // Importa JFormattedTextField
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JSpinner.DateEditor;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;

import logica.DTOrganizador;
import logica.Fabrica;
import logica.IEventosController;
import logica.IUsuariosController;

public class AltaDeEdicionDeEvento extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JTextField textFieldNombreEdicion;
	private JTextField textFieldSigla;
	private JTextField textFieldCiudad;
	private JTextField textFieldPais;

	private JLabel lblIngreseNombreEdicion;
	private JLabel lblIngreseSigla;
	private JLabel lblIngreseCiudad;
	private JLabel lblIngresePais;
	private JLabel lblIngreseFechaIni;
	private JLabel lblIngreseFechaFin;
	// ComboBox
	private JComboBox<String> comboBoxEvento;
	private JComboBox<String> comboBoxOrganizador;
	// Spinners
	private JSpinner spinnerFechaIni;
	private JSpinner spinnerFechaFin;

	private IEventosController ctrlEvento = Fabrica.getInstance().getIControladorEventos(); // Controlador de eventos
	private IUsuariosController ctrlUsuario = Fabrica.getInstance().getIUsuariosController();// Controlador de usuario

	private JButton btnAceptar;
	private JButton btnCancelar;
	private JPanel panel;

	public AltaDeEdicionDeEvento(IEventosController ctrlEvento, IUsuariosController ctrlUsuario) {
		this.ctrlEvento = ctrlEvento;
		this.ctrlUsuario = ctrlUsuario;

		setTitle("Alta de Edicion de Evento");
		setResizable(true);
		setIconifiable(true);
		setMaximizable(true);
		setClosable(true);
		setBounds(100, 100, 450, 300);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 51, 100, 98, 35, 0 };
		gridBagLayout.rowHeights = new int[] { 20, 0, 0, 30, 30, 30, 30, 35, 59, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE, 0.0,
				0.0 };
		getContentPane().setLayout(gridBagLayout);

		panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		getContentPane().add(panel, gbc_panel);

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

		JLabel lblSelecOrganizador = new JLabel("Seleccione Organizador:");
		GridBagConstraints gbc_lblSelecOrganizador = new GridBagConstraints();
		gbc_lblSelecOrganizador.anchor = GridBagConstraints.EAST;
		gbc_lblSelecOrganizador.insets = new Insets(0, 0, 5, 5);
		gbc_lblSelecOrganizador.gridx = 0;
		gbc_lblSelecOrganizador.gridy = 2;
		getContentPane().add(lblSelecOrganizador, gbc_lblSelecOrganizador);

		comboBoxOrganizador = new JComboBox<>();
		GridBagConstraints gbc_comboBoxOrganizador = new GridBagConstraints();
		gbc_comboBoxOrganizador.gridwidth = 2;
		gbc_comboBoxOrganizador.insets = new Insets(0, 0, 5, 5);
		gbc_comboBoxOrganizador.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxOrganizador.gridx = 1;
		gbc_comboBoxOrganizador.gridy = 2;
		getContentPane().add(comboBoxOrganizador, gbc_comboBoxOrganizador);

		lblIngreseNombreEdicion = new JLabel("Nombre de Edicion:");
		GridBagConstraints gbc_lblIngreseNombreEdicion = new GridBagConstraints();
		gbc_lblIngreseNombreEdicion.insets = new Insets(0, 0, 5, 5);
		gbc_lblIngreseNombreEdicion.anchor = GridBagConstraints.EAST;
		gbc_lblIngreseNombreEdicion.gridx = 0;
		gbc_lblIngreseNombreEdicion.gridy = 3;
		getContentPane().add(lblIngreseNombreEdicion, gbc_lblIngreseNombreEdicion);

		textFieldNombreEdicion = new JTextField();
		textFieldNombreEdicion.setColumns(10);
		GridBagConstraints gbc_textFieldNombreEdicion = new GridBagConstraints();
		gbc_textFieldNombreEdicion.gridwidth = 2;
		gbc_textFieldNombreEdicion.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldNombreEdicion.fill = GridBagConstraints.BOTH;
		gbc_textFieldNombreEdicion.gridx = 1;
		gbc_textFieldNombreEdicion.gridy = 3;
		getContentPane().add(textFieldNombreEdicion, gbc_textFieldNombreEdicion);

		lblIngreseSigla = new JLabel("Sigla:");
		GridBagConstraints gbc_lblIngreseSigla = new GridBagConstraints();
		gbc_lblIngreseSigla.insets = new Insets(0, 0, 5, 5);
		gbc_lblIngreseSigla.anchor = GridBagConstraints.EAST;
		gbc_lblIngreseSigla.gridx = 0;
		gbc_lblIngreseSigla.gridy = 4;
		getContentPane().add(lblIngreseSigla, gbc_lblIngreseSigla);

		textFieldSigla = new JTextField();
		textFieldSigla.setColumns(10);
		GridBagConstraints gbc_textFieldSigla = new GridBagConstraints();
		gbc_textFieldSigla.gridwidth = 2;
		gbc_textFieldSigla.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldSigla.fill = GridBagConstraints.BOTH;
		gbc_textFieldSigla.gridx = 1;
		gbc_textFieldSigla.gridy = 4;
		getContentPane().add(textFieldSigla, gbc_textFieldSigla);

		lblIngreseCiudad = new JLabel("Ciudad:");
		GridBagConstraints gbc_lblIngreseCiudad = new GridBagConstraints();
		gbc_lblIngreseCiudad.insets = new Insets(0, 0, 5, 5);
		gbc_lblIngreseCiudad.anchor = GridBagConstraints.EAST;
		gbc_lblIngreseCiudad.gridx = 0;
		gbc_lblIngreseCiudad.gridy = 5;
		getContentPane().add(lblIngreseCiudad, gbc_lblIngreseCiudad);

		textFieldCiudad = new JTextField();
		textFieldCiudad.setColumns(10);
		GridBagConstraints gbc_textFieldCiudad = new GridBagConstraints();
		gbc_textFieldCiudad.gridwidth = 2;
		gbc_textFieldCiudad.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldCiudad.fill = GridBagConstraints.BOTH;
		gbc_textFieldCiudad.gridx = 1;
		gbc_textFieldCiudad.gridy = 5;
		getContentPane().add(textFieldCiudad, gbc_textFieldCiudad);

		lblIngresePais = new JLabel("Pais:");
		GridBagConstraints gbc_lblIngresePais = new GridBagConstraints();
		gbc_lblIngresePais.insets = new Insets(0, 0, 5, 5);
		gbc_lblIngresePais.anchor = GridBagConstraints.EAST;
		gbc_lblIngresePais.gridx = 0;
		gbc_lblIngresePais.gridy = 6;
		getContentPane().add(lblIngresePais, gbc_lblIngresePais);

		textFieldPais = new JTextField();
		textFieldPais.setColumns(10);
		GridBagConstraints gbc_textFieldPais = new GridBagConstraints();
		gbc_textFieldPais.gridwidth = 2;
		gbc_textFieldPais.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldPais.fill = GridBagConstraints.BOTH;
		gbc_textFieldPais.gridx = 1;
		gbc_textFieldPais.gridy = 6;
		getContentPane().add(textFieldPais, gbc_textFieldPais);

		lblIngreseFechaIni = new JLabel("Fecha Inicio:");
		GridBagConstraints gbc_lblFechaIni = new GridBagConstraints();
		gbc_lblFechaIni.insets = new Insets(0, 0, 5, 5);
		gbc_lblFechaIni.anchor = GridBagConstraints.EAST;
		gbc_lblFechaIni.gridx = 0;
		gbc_lblFechaIni.gridy = 7;
		getContentPane().add(lblIngreseFechaIni, gbc_lblFechaIni);

		// Para la fecha ini
		spinnerFechaIni = new JSpinner(new SpinnerDateModel());
		DateEditor editorIni = new JSpinner.DateEditor(spinnerFechaIni, "dd/MM/yyyy");
		spinnerFechaIni.setEditor(editorIni);

		editorIni.getFormat().setLenient(false);

		editorIni.getTextField().setEditable(true); // permitir teclado
		// CORRECCIÓN AQUÍ: Usar JFormattedTextField.COMMIT
		editorIni.getTextField().setFocusLostBehavior(JFormattedTextField.COMMIT);

		GridBagConstraints gbc_spinnerIni = new GridBagConstraints();
		gbc_spinnerIni.gridwidth = 2;
		gbc_spinnerIni.insets = new Insets(0, 0, 5, 5);
		gbc_spinnerIni.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinnerIni.gridx = 1;
		gbc_spinnerIni.gridy = 7;
		getContentPane().add(spinnerFechaIni, gbc_spinnerIni);

		lblIngreseFechaFin = new JLabel("Fecha Fin:");
		GridBagConstraints gbc_lblFechaFin = new GridBagConstraints();
		gbc_lblFechaFin.insets = new Insets(0, 0, 5, 5);
		gbc_lblFechaFin.anchor = GridBagConstraints.EAST;
		gbc_lblFechaFin.gridx = 0;
		gbc_lblFechaFin.gridy = 8;
		getContentPane().add(lblIngreseFechaFin, gbc_lblFechaFin);

		// Para la fecha fin
		spinnerFechaFin = new JSpinner(new SpinnerDateModel());
		DateEditor editorFin = new JSpinner.DateEditor(spinnerFechaFin, "dd/MM/yyyy");
		spinnerFechaFin.setEditor(editorFin);

		editorFin.getFormat().setLenient(false);

		editorFin.getTextField().setEditable(true); // permitir teclado
		// CORRECCIÓN AQUÍ: Usar JFormattedTextField.COMMIT
		editorFin.getTextField().setFocusLostBehavior(JFormattedTextField.COMMIT);
		GridBagConstraints gbc_spinnerFin = new GridBagConstraints();
		gbc_spinnerFin.gridwidth = 2;
		gbc_spinnerFin.insets = new Insets(0, 0, 5, 5);
		gbc_spinnerFin.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinnerFin.gridx = 1;
		gbc_spinnerFin.gridy = 8;
		getContentPane().add(spinnerFechaFin, gbc_spinnerFin);

		btnAceptar = new JButton("Aceptar");
		GridBagConstraints gbc_btnAceptar = new GridBagConstraints();
		gbc_btnAceptar.insets = new Insets(0, 0, 5, 5);
		gbc_btnAceptar.gridx = 1;
		gbc_btnAceptar.gridy = 9;
		getContentPane().add(btnAceptar, gbc_btnAceptar);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpiarFormulario();
				setVisible(false);
			}
		});

		GridBagConstraints gbc_btnCancelar = new GridBagConstraints();
		gbc_btnCancelar.insets = new Insets(0, 0, 5, 5);
		gbc_btnCancelar.gridx = 2;
		gbc_btnCancelar.gridy = 9;
		getContentPane().add(btnCancelar, gbc_btnCancelar);
		activarEventos(); // Activador de eventos
		cargarComboBox();
	}

	private void activarEventos() { // Se encarga de activar los eventos
		btnAceptar.addActionListener(e -> onAceptar());
		btnCancelar.addActionListener(e -> {
			limpiarFormulario();
			setVisible(false); // no libera recursos, se hara mas tarde en el limpiarformulario
		});
		// Para que la fechaFin no pueda ser anterior que la fechaIni

		// Recarga los Eventos
		comboBoxEvento.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
			public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent e) {
				recargarEventos();
			}

			public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent e) {
			}

			public void popupMenuCanceled(javax.swing.event.PopupMenuEvent e) {
			}
		});
		// Recarga los Organizadores
		comboBoxOrganizador.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
			public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent e) {
				recargarOrganizadores();
			}

			public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent e) {
			}

			public void popupMenuCanceled(javax.swing.event.PopupMenuEvent e) {
			}
		});
	}

	private void recargarEventos() {
		List<String> eventos = ctrlEvento.listarEventos();
		Collections.sort(eventos);
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(eventos.toArray(new String[0]));
		comboBoxEvento.setModel(model);
		comboBoxEvento.setSelectedIndex(eventos.isEmpty() ? -1 : 0);
	}

	private void recargarOrganizadores() {
		List<DTOrganizador> dtOrgs = ctrlUsuario.listarOrganizadores();
		List<String> orgs = dtOrgs.stream().map(DTOrganizador::getNickname).toList();
		Collections.sort(orgs);
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(orgs.toArray(new String[0]));
		comboBoxOrganizador.setModel(model);
		comboBoxOrganizador.setSelectedIndex(orgs.isEmpty() ? -1 : 0);
	}

	// Para mostrar los eventos y organizadores al abrir la ventana en las comboBox
	private void cargarComboBox() {
		try {
			// Eventos
			List<String> eventos = ctrlEvento.listarEventos();
			Collections.sort(eventos);
			DefaultComboBoxModel<String> modelEv = // Con JComboBox<String> no funciona
					new DefaultComboBoxModel<String>(eventos.toArray(new String[0]));
			comboBoxEvento.setModel(modelEv);

			// Organizadores
			List<DTOrganizador> dtOrgs = ctrlUsuario.listarOrganizadores();
			List<String> orgs = dtOrgs.stream().map(DTOrganizador::getNickname).toList();
			Collections.sort(orgs);
			DefaultComboBoxModel<String> modelOrg = new DefaultComboBoxModel<String>(orgs.toArray(new String[0]));
			comboBoxOrganizador.setModel(modelOrg);

			// Selección inicial y habilitar Aceptar sólo si hay datos
			boolean ok = !eventos.isEmpty() && !orgs.isEmpty(); // Si las ComboBox no están vacías
			comboBoxEvento.setSelectedIndex(ok ? 0 : -1); // Si hay un evento seleccionado
			comboBoxOrganizador.setSelectedIndex(ok ? 0 : -1); // Si hay un organizador seleccionado
			btnAceptar.setEnabled(true);
		} catch (Exception ex) {
		}
	}

	private void onAceptar() {
		try {
			((JSpinner.DefaultEditor) spinnerFechaIni.getEditor()).commitEdit();
			((JSpinner.DefaultEditor) spinnerFechaFin.getEditor()).commitEdit();
		} catch (java.text.ParseException | IllegalArgumentException pe) {
			JOptionPane.showMessageDialog(this, "Fecha inválida. Usá formato dd/MM/aaaa y una fecha existente.",
					"Error en fecha", JOptionPane.ERROR_MESSAGE);
			return;
		}

		String txtIni = ((JSpinner.DateEditor) spinnerFechaIni.getEditor()).getTextField().getText().trim();
		String txtFin = ((JSpinner.DateEditor) spinnerFechaFin.getEditor()).getTextField().getText().trim();

		// Para forzar el formato dd/MM/yyyy
		if (!txtIni.matches("\\d{2}/\\d{2}/\\d{4}") || !txtFin.matches("\\d{2}/\\d{2}/\\d{4}")) {
			JOptionPane.showMessageDialog(this, "Formato inválido. Usá dd/MM/aaaa.", "Error en fecha",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT);
		final LocalDate iniLD, finLD;
		try {
			iniLD = LocalDate.parse(txtIni, FMT);
			finLD = LocalDate.parse(txtFin, FMT);
		} catch (DateTimeParseException ex) {
			JOptionPane.showMessageDialog(this,
					"Fecha inválida. Verificá día/mes/año (dd/MM/aaaa) y que la fecha exista.", "Error en fecha",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		// Lee los datos
		String evento = (String) comboBoxEvento.getSelectedItem();
		String organizador = (String) comboBoxOrganizador.getSelectedItem();
		String nombreEdicion = textFieldNombreEdicion.getText().trim();
		String sigla = textFieldSigla.getText().trim();
		String ciudad = textFieldCiudad.getText().trim();
		String pais = textFieldPais.getText().trim();

		// Chequeos
		StringBuilder err = new StringBuilder();
		if (evento == null || evento.isEmpty())
			err.append("• Seleccione un evento.\n");
		if (organizador == null || organizador.isEmpty())
			err.append("• Seleccione un organizador.\n");
		if (nombreEdicion.isEmpty())
			err.append("• Ingrese el nombre de la edición.\n");
		if (sigla.isEmpty())
			err.append("• Ingrese la sigla.\n");
		if (ciudad.isEmpty())
			err.append("• Ingrese la ciudad.\n");
		if (pais.isEmpty())
			err.append("• Ingrese el país.\n");
		if (iniLD == null)
			err.append("• Ingrese la fecha de inicio.\n");
		if (finLD == null)
			err.append("• Ingrese la fecha de fin.\n");
		if (iniLD != null && finLD != null && finLD.isBefore(iniLD))
			err.append("• La fecha de fin no puede ser anterior a la de inicio.\n");

		if (err.length() > 0) {
			JOptionPane.showMessageDialog(this, err.toString(), "Datos incompletos", JOptionPane.WARNING_MESSAGE);
			return;
		}

		// Llama al controlador
		try {
			ctrlEvento.altaEdicion(evento, organizador, nombreEdicion, sigla, ciudad, pais, iniLD, finLD,
					LocalDate.now(), "");
			JOptionPane.showMessageDialog(this, "Edición creada correctamente.", "OK", JOptionPane.INFORMATION_MESSAGE);
			limpiarFormulario();
			// setVisible(false);
			dispose(); // Asi cierra la ventana

		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "No se pudo crear la edición:\n" + ex.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	// Limpia el formulario
	private void limpiarFormulario() {
		textFieldNombreEdicion.setText("");
		textFieldSigla.setText("");
		textFieldCiudad.setText("");
		textFieldPais.setText("");
		if (comboBoxEvento.getItemCount() > 0)
			comboBoxEvento.setSelectedIndex(-1); // Deselecciona el Evento
		if (comboBoxOrganizador.getItemCount() > 0)
			comboBoxOrganizador.setSelectedIndex(-1);// Deselecciona el Organizador

		Date now = new Date();
		spinnerFechaIni.setValue(now); // Reinicia al valor actual
		spinnerFechaFin.setValue(now); // Reinicia al valor actual
	}
}