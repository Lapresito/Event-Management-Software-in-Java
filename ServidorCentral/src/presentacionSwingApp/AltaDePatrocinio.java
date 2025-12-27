package presentacionSwingApp;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
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

import logica.DTInstitucion;
import logica.IEventosController;
import logica.IInstitucionesController;
import logica.NivelPatrocinio;

public class AltaDePatrocinio extends JInternalFrame {

	private IInstitucionesController controlIns;
	private IEventosController controlEvs;

	private static final long serialVersionUID = 1L;
	private JTextField textFieldMonto;
	private JTextField textFieldCupo;
	private JTextField textFieldCodigo;

	JComboBox<String> comboBoxEventos;
	JComboBox<String> comboBoxEdiciones;
	JComboBox<String> comboBoxInstituciones;
	JComboBox<NivelPatrocinio> comboBoxNiveles;
	JComboBox<String> comboBoxTiposDeRegistro;

	public AltaDePatrocinio(IEventosController IEv, IInstitucionesController IIns) {
		controlEvs = IEv;
		controlIns = IIns;

		setTitle("Alta de Patrocinio");
		setResizable(true);
		setIconifiable(true);
		setMaximizable(true);
		setClosable(true);
		setBounds(100, 100, 400, 350);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 51, 100, 98, 35, 0 };
		gridBagLayout.rowHeights = new int[] { 20, 0, 25, 25, 35, 35, 35, 35, 30, 30, 35, 59, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0,
				Double.MIN_VALUE };
		getContentPane().setLayout(gridBagLayout);

		JLabel lblSelecEvento = new JLabel("Seleccione Evento:");
		GridBagConstraints gbc_lblSelecEvento = new GridBagConstraints();
		gbc_lblSelecEvento.anchor = GridBagConstraints.EAST;
		gbc_lblSelecEvento.insets = new Insets(0, 0, 5, 5);
		gbc_lblSelecEvento.gridx = 0;
		gbc_lblSelecEvento.gridy = 1;
		getContentPane().add(lblSelecEvento, gbc_lblSelecEvento);

		comboBoxEventos = new JComboBox<>();
		GridBagConstraints gbc_comboBoxEventos = new GridBagConstraints();
		gbc_comboBoxEventos.gridwidth = 2;
		gbc_comboBoxEventos.insets = new Insets(0, 0, 5, 5);
		gbc_comboBoxEventos.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxEventos.gridx = 1;
		gbc_comboBoxEventos.gridy = 1;
		getContentPane().add(comboBoxEventos, gbc_comboBoxEventos);

		comboBoxEventos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String eventoSeleccionado = (String) comboBoxEventos.getSelectedItem();
				if (eventoSeleccionado != null) {
					comboBoxEdiciones.removeAllItems();
					List<String> ediciones = controlEvs.listarEdicionesEvento(eventoSeleccionado);
					for (String nombreEdicion : ediciones) {
						comboBoxEdiciones.addItem(nombreEdicion);
					}
					comboBoxEdiciones.setSelectedIndex(-1);
				}
			}
		});

		JLabel lblSelecEdicion = new JLabel("Seleccione Edición:");
		GridBagConstraints gbc_lblSelecEdicion = new GridBagConstraints();
		gbc_lblSelecEdicion.insets = new Insets(0, 0, 5, 5);
		gbc_lblSelecEdicion.anchor = GridBagConstraints.EAST;
		gbc_lblSelecEdicion.gridx = 0;
		gbc_lblSelecEdicion.gridy = 2;
		getContentPane().add(lblSelecEdicion, gbc_lblSelecEdicion);

		comboBoxEdiciones = new JComboBox<>();
		GridBagConstraints gbc_comboBoxEdiciones = new GridBagConstraints();
		gbc_comboBoxEdiciones.gridwidth = 2;
		gbc_comboBoxEdiciones.insets = new Insets(0, 0, 5, 5);
		gbc_comboBoxEdiciones.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxEdiciones.gridx = 1;
		gbc_comboBoxEdiciones.gridy = 2;
		getContentPane().add(comboBoxEdiciones, gbc_comboBoxEdiciones);

		comboBoxEdiciones.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String edicionSeleccionada = (String) comboBoxEdiciones.getSelectedItem();
				cargarTPs(edicionSeleccionada);
			}
		});

		JLabel lblTipoDeRegistro = new JLabel("Seleccione Tipo Registro:");
		GridBagConstraints gbc_lblTipoDeRegistro = new GridBagConstraints();
		gbc_lblTipoDeRegistro.insets = new Insets(0, 0, 5, 5);
		gbc_lblTipoDeRegistro.anchor = GridBagConstraints.EAST;
		gbc_lblTipoDeRegistro.gridx = 0;
		gbc_lblTipoDeRegistro.gridy = 3;
		getContentPane().add(lblTipoDeRegistro, gbc_lblTipoDeRegistro);

		comboBoxTiposDeRegistro = new JComboBox<>();
		GridBagConstraints gbc_comboBoxTiposDeRegistro = new GridBagConstraints();
		gbc_comboBoxTiposDeRegistro.gridwidth = 2;
		gbc_comboBoxTiposDeRegistro.insets = new Insets(0, 0, 5, 5);
		gbc_comboBoxTiposDeRegistro.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxTiposDeRegistro.gridx = 1;
		gbc_comboBoxTiposDeRegistro.gridy = 3;
		getContentPane().add(comboBoxTiposDeRegistro, gbc_comboBoxTiposDeRegistro);

		JLabel lblSeleccioneInstitucin = new JLabel("Seleccione Institución:");
		GridBagConstraints gbc_lblSeleccioneInstitucin = new GridBagConstraints();
		gbc_lblSeleccioneInstitucin.anchor = GridBagConstraints.EAST;
		gbc_lblSeleccioneInstitucin.insets = new Insets(0, 0, 5, 5);
		gbc_lblSeleccioneInstitucin.gridx = 0;
		gbc_lblSeleccioneInstitucin.gridy = 4;
		getContentPane().add(lblSeleccioneInstitucin, gbc_lblSeleccioneInstitucin);

		comboBoxInstituciones = new JComboBox<>();
		GridBagConstraints gbc_comboBoxInstituciones = new GridBagConstraints();
		gbc_comboBoxInstituciones.gridwidth = 2;
		gbc_comboBoxInstituciones.insets = new Insets(0, 0, 5, 5);
		gbc_comboBoxInstituciones.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxInstituciones.gridx = 1;
		gbc_comboBoxInstituciones.gridy = 4;
		getContentPane().add(comboBoxInstituciones, gbc_comboBoxInstituciones);

		JLabel lblInfoNivel = new JLabel("Nivel:");
		GridBagConstraints gbc_lblInfoNivel = new GridBagConstraints();
		gbc_lblInfoNivel.insets = new Insets(0, 0, 5, 5);
		gbc_lblInfoNivel.anchor = GridBagConstraints.EAST;
		gbc_lblInfoNivel.gridx = 0;
		gbc_lblInfoNivel.gridy = 5;
		getContentPane().add(lblInfoNivel, gbc_lblInfoNivel);

		comboBoxNiveles = new JComboBox<>();
		GridBagConstraints gbc_comboBoxNiveles = new GridBagConstraints();
		gbc_comboBoxNiveles.gridwidth = 2;
		gbc_comboBoxNiveles.insets = new Insets(0, 0, 5, 5);
		gbc_comboBoxNiveles.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxNiveles.gridx = 1;
		gbc_comboBoxNiveles.gridy = 5;
		getContentPane().add(comboBoxNiveles, gbc_comboBoxNiveles);

		JLabel lblInfoMonto = new JLabel("Monto:");
		GridBagConstraints gbc_lblInfoMonto = new GridBagConstraints();
		gbc_lblInfoMonto.insets = new Insets(0, 0, 5, 5);
		gbc_lblInfoMonto.anchor = GridBagConstraints.EAST;
		gbc_lblInfoMonto.gridx = 0;
		gbc_lblInfoMonto.gridy = 6;
		getContentPane().add(lblInfoMonto, gbc_lblInfoMonto);

		textFieldMonto = new JTextField();
		GridBagConstraints gbc_textFieldMonto = new GridBagConstraints();
		gbc_textFieldMonto.gridwidth = 2;
		gbc_textFieldMonto.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldMonto.fill = GridBagConstraints.BOTH;
		gbc_textFieldMonto.gridx = 1;
		gbc_textFieldMonto.gridy = 6;
		getContentPane().add(textFieldMonto, gbc_textFieldMonto);
		textFieldMonto.setColumns(10);

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

		JLabel lblCodigo = new JLabel("Código:");
		GridBagConstraints gbc_lblCodigo = new GridBagConstraints();
		gbc_lblCodigo.insets = new Insets(0, 0, 5, 5);
		gbc_lblCodigo.anchor = GridBagConstraints.EAST;
		gbc_lblCodigo.gridx = 0;
		gbc_lblCodigo.gridy = 8;
		getContentPane().add(lblCodigo, gbc_lblCodigo);

		textFieldCodigo = new JTextField();
		textFieldCodigo.setColumns(10);
		GridBagConstraints gbc_textFieldCodigo = new GridBagConstraints();
		gbc_textFieldCodigo.gridwidth = 2;
		gbc_textFieldCodigo.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldCodigo.fill = GridBagConstraints.BOTH;
		gbc_textFieldCodigo.gridx = 1;
		gbc_textFieldCodigo.gridy = 8;
		getContentPane().add(textFieldCodigo, gbc_textFieldCodigo);

		JButton btnAceptar = new JButton("Aceptar");
		GridBagConstraints gbc_btnAceptar = new GridBagConstraints();
		gbc_btnAceptar.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnAceptar.insets = new Insets(0, 0, 5, 5);
		gbc_btnAceptar.gridx = 1;
		gbc_btnAceptar.gridy = 9;
		getContentPane().add(btnAceptar, gbc_btnAceptar);

		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					// (
					// int monto, int cupo, LocalDate fechaAlta, String codigo)
					
					String ins = (String) comboBoxInstituciones.getSelectedItem();
					String ed = (String) comboBoxEdiciones.getSelectedItem();
					String tp = (String) comboBoxTiposDeRegistro.getSelectedItem();
					NivelPatrocinio nivel = (NivelPatrocinio) comboBoxNiveles.getSelectedItem();
					
					String montoStr = textFieldMonto.getText();
					
					String cupoStr = textFieldCupo.getText();
					String codigo = textFieldCodigo.getText();
					
					int monto = Integer.parseInt(montoStr);
					int cupo = Integer.parseInt(cupoStr);
					if (monto <=0) {throw new IllegalArgumentException("El monto debe ser mayor que 0.");}
					if (cupo <=0) {throw new IllegalArgumentException("El cupo debe ser mayor que 0.");}
					
					LocalDate fechaActual = LocalDate.now();
					
					controlEvs.altaPatrocinio(ins, ed, tp, nivel, monto, cupo, fechaActual, codigo);
					
					JOptionPane.showMessageDialog(AltaDePatrocinio.this, "El Patrocinio se ha creado con éxito", "Alta de Patrocinio",JOptionPane.INFORMATION_MESSAGE);
					limpiarFormulario();
					setVisible(false);
					
				}catch(Exception ex) {
					 JOptionPane.showMessageDialog(AltaDePatrocinio.this, ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
				}

			}
		});

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpiarFormulario();
				setVisible(false);
			}
		});
		GridBagConstraints gbc_btnCancelar = new GridBagConstraints();
		gbc_btnCancelar.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnCancelar.insets = new Insets(0, 0, 5, 5);
		gbc_btnCancelar.gridx = 2;
		gbc_btnCancelar.gridy = 9;
		getContentPane().add(btnCancelar, gbc_btnCancelar);

		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpiarFormulario();
				setVisible(false);
			}
		});

	}

	private void limpiarFormulario() {
		textFieldMonto.setText("");
		textFieldCodigo.setText("");
		textFieldCupo.setText("");
	}

	public void cargarEventosEInstituciones() {
		Vector<String> eventos = new Vector<>(controlEvs.listarEventos());
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(eventos);
		comboBoxEventos.setModel(model);
		SwingUtilities.invokeLater(() -> comboBoxEventos.setSelectedIndex(-1));

		DTInstitucion[] institucionesArr = controlIns.getInstituciones();
		Vector<String> instituciones = new Vector<>();

		if (institucionesArr != null) {
			for (DTInstitucion inst : institucionesArr) {
				instituciones.add(inst.getNombre());
			}
		}

		DefaultComboBoxModel<String> modelIns = new DefaultComboBoxModel<>(instituciones);
		comboBoxInstituciones.setModel(modelIns);
		SwingUtilities.invokeLater(() -> comboBoxInstituciones.setSelectedIndex(-1));

		DefaultComboBoxModel<NivelPatrocinio> modelNiveles = new DefaultComboBoxModel<>(NivelPatrocinio.values());
		comboBoxNiveles.setModel(modelNiveles);
		comboBoxNiveles.setSelectedIndex(-1);
	}

	private void cargarTPs(String nombreEdicion) {
		Vector<String> tiposDeRegistro = new Vector<>(controlEvs.listarTiposDeRegistroDeEdicion(nombreEdicion));
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(tiposDeRegistro);
		comboBoxTiposDeRegistro.setModel(model);
		SwingUtilities.invokeLater(() -> comboBoxTiposDeRegistro.setSelectedIndex(-1));
	}

}
