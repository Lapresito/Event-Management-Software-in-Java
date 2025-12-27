package presentacionSwingApp;

//import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.time.*;
import java.util.Date;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingConstants;

import excepciones.NicknameRepetidoException;
import excepciones.EmailRepetidoException;
import java.util.regex.Pattern;

import javax.swing.JSpinner;
import javax.swing.JComboBox;

import logica.DTInstitucion;
import logica.IUsuariosController;
import logica.IInstitucionesController;
import logica.Fabrica;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class AltaDeUsuario extends JInternalFrame {
	
	// Controlador de usuarios que se utilizará para las acciones del JFrame
    private IUsuariosController controlUsr;
    private IInstitucionesController controlInst;

	private static final long serialVersionUID = 1L;
	private JTextField textFieldNickname;
	private JTextField textFieldNombre;
	private JTextField textFieldCorreo;
	private JLabel lblIngreseNickname;
	private JLabel lblIngreseCorreo;
	private JRadioButton rdbtnSelecOrganizador;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JRadioButton rdbtnSelecAsistente;
	private JLabel lblSelecTipoUsuario;
	private JButton btnAceptar;
	private JButton btnCancelar;
	private JTextField textFieldSitioWeb;
	private JLabel lblDescripcion;
	private JLabel lblSitioWeb;
	private JLabel lblApellido;
	private JLabel lblNacimiento;
	private JSpinner spinnerFechaNacimiento;
	private JTextField textFieldApellido;
	private JComboBox<DTInstitucion> comboBoxInstituciones;
	private JLabel lblInstitucion;
	private JTextArea textAreaDescripcion;
	private JScrollPane scrollPane;


	/**
	 * Create the frame.
	 */
	public AltaDeUsuario(IUsuariosController icu) {
		
		// Se inicializa con el controlador de usuarios
        controlUsr = icu;
        
        Fabrica fab = Fabrica.getInstance();
        controlInst = fab.getIInstitucionesController();
        
		setTitle("Alta de Usuario");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setResizable(true);
		setIconifiable(true);
		setMaximizable(true);
		setClosable(true);
		setBounds(100, 100, 491, 377);
		GridBagLayout gridBagLayout = new GridBagLayout();

		gridBagLayout.columnWidths = new int[] { 41, 125, 140, 140, 58, 0 };
		gridBagLayout.rowHeights = new int[] { 15, 30, 30, 30, 30, 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 1.0, 1.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 1.0, 0.0, 0.0 };

		getContentPane().setLayout(gridBagLayout);

		lblIngreseNickname = new JLabel("Nickname:");
		GridBagConstraints gbc_lblIngreseNickname = new GridBagConstraints();
		gbc_lblIngreseNickname.insets = new Insets(0, 0, 5, 5);
		gbc_lblIngreseNickname.anchor = GridBagConstraints.EAST;
		gbc_lblIngreseNickname.gridx = 1;
		gbc_lblIngreseNickname.gridy = 1;
		getContentPane().add(lblIngreseNickname, gbc_lblIngreseNickname);

		textFieldNickname = new JTextField();
		textFieldNickname.setColumns(10);
		GridBagConstraints gbc_textFieldNickname = new GridBagConstraints();
		gbc_textFieldNickname.gridwidth = 2;
		gbc_textFieldNickname.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldNickname.fill = GridBagConstraints.BOTH;
		gbc_textFieldNickname.gridx = 2;
		gbc_textFieldNickname.gridy = 1;
		getContentPane().add(textFieldNickname, gbc_textFieldNickname);

		JLabel lblIngreseNombre = new JLabel("Nombre:");
		lblIngreseNombre.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_lblIngreseNombre = new GridBagConstraints();
		gbc_lblIngreseNombre.anchor = GridBagConstraints.EAST;
		gbc_lblIngreseNombre.insets = new Insets(0, 0, 5, 5);
		gbc_lblIngreseNombre.gridx = 1;
		gbc_lblIngreseNombre.gridy = 2;
		getContentPane().add(lblIngreseNombre, gbc_lblIngreseNombre);

		textFieldNombre = new JTextField();
		textFieldNombre.setColumns(10);
		GridBagConstraints gbc_textFieldNombre = new GridBagConstraints();
		gbc_textFieldNombre.gridwidth = 2;
		gbc_textFieldNombre.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldNombre.fill = GridBagConstraints.BOTH;
		gbc_textFieldNombre.gridx = 2;
		gbc_textFieldNombre.gridy = 2;
		getContentPane().add(textFieldNombre, gbc_textFieldNombre);

		lblIngreseCorreo = new JLabel("Correo:");
		GridBagConstraints gbc_lblIngreseCorreo = new GridBagConstraints();
		gbc_lblIngreseCorreo.insets = new Insets(0, 0, 5, 5);
		gbc_lblIngreseCorreo.anchor = GridBagConstraints.EAST;
		gbc_lblIngreseCorreo.gridx = 1;
		gbc_lblIngreseCorreo.gridy = 3;
		getContentPane().add(lblIngreseCorreo, gbc_lblIngreseCorreo);

		textFieldCorreo = new JTextField();
		GridBagConstraints gbc_textFieldCorreo = new GridBagConstraints();
		gbc_textFieldCorreo.gridwidth = 2;
		gbc_textFieldCorreo.fill = GridBagConstraints.BOTH;
		gbc_textFieldCorreo.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldCorreo.gridx = 2;
		gbc_textFieldCorreo.gridy = 3;
		getContentPane().add(textFieldCorreo, gbc_textFieldCorreo);
		textFieldCorreo.setColumns(10);

		lblSelecTipoUsuario = new JLabel("Tipo de Usuario:");
		GridBagConstraints gbc_lblSelecTipoUsuario = new GridBagConstraints();
		gbc_lblSelecTipoUsuario.anchor = GridBagConstraints.EAST;
		gbc_lblSelecTipoUsuario.gridwidth = 2;
		gbc_lblSelecTipoUsuario.insets = new Insets(0, 0, 5, 5);
		gbc_lblSelecTipoUsuario.gridx = 0;
		gbc_lblSelecTipoUsuario.gridy = 4;
		getContentPane().add(lblSelecTipoUsuario, gbc_lblSelecTipoUsuario);

		rdbtnSelecOrganizador = new JRadioButton("Organizador");
		rdbtnSelecOrganizador.setSelected(true);
		buttonGroup.add(rdbtnSelecOrganizador);
		GridBagConstraints gbc_rdbtnSelecOrganizador = new GridBagConstraints();
		gbc_rdbtnSelecOrganizador.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnSelecOrganizador.gridx = 2;
		gbc_rdbtnSelecOrganizador.gridy = 4;
		getContentPane().add(rdbtnSelecOrganizador, gbc_rdbtnSelecOrganizador);

		
		rdbtnSelecOrganizador.addActionListener(e -> {					//Para cambiar los campos segun el usuario a ingresar
			lblDescripcion.setVisible(true);
			lblSitioWeb.setVisible(true);
			scrollPane.setVisible(true);
			textAreaDescripcion.setVisible(true);
			textFieldSitioWeb.setVisible(true);
			
			lblNacimiento.setVisible(false);
			lblApellido.setVisible(false);
			textFieldApellido.setVisible(false);
			textFieldApellido.setText("");
			spinnerFechaNacimiento.setVisible(false);
			spinnerFechaNacimiento.setValue(new Date());
			lblInstitucion.setVisible(false);
			comboBoxInstituciones.setVisible(false);
		});
		

		rdbtnSelecAsistente = new JRadioButton("Asistente");
		buttonGroup.add(rdbtnSelecAsistente);
		GridBagConstraints gbc_rdbtnSelecAsistente = new GridBagConstraints();
		gbc_rdbtnSelecAsistente.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnSelecAsistente.gridx = 3;
		gbc_rdbtnSelecAsistente.gridy = 4;
		getContentPane().add(rdbtnSelecAsistente, gbc_rdbtnSelecAsistente);

		
		rdbtnSelecAsistente.addActionListener(e -> {					//Para cambiar los campos según el usuario a ingresar
			lblDescripcion.setVisible(false);
			lblSitioWeb.setVisible(false);
			textAreaDescripcion.setText("");							//se vacian los campos al cambiar entre uno y otro?
			textFieldSitioWeb.setText("");
			textAreaDescripcion.setVisible(false);
			scrollPane.setVisible(false);
			textFieldSitioWeb.setVisible(false);
			
			lblNacimiento.setVisible(true);
			lblApellido.setVisible(true);
			textFieldApellido.setVisible(true);
			spinnerFechaNacimiento.setVisible(true);
			lblInstitucion.setVisible(true);
			comboBoxInstituciones.setVisible(true);
			
		});
		
		lblApellido = new JLabel("Apellido:");
		lblApellido.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_lblApellido = new GridBagConstraints();
		gbc_lblApellido.anchor = GridBagConstraints.EAST;
		gbc_lblApellido.insets = new Insets(0, 0, 5, 5);
		gbc_lblApellido.gridx = 1;
		gbc_lblApellido.gridy = 5;
		lblApellido.setVisible(false);
		getContentPane().add(lblApellido, gbc_lblApellido);
		
		textFieldApellido = new JTextField();
		GridBagConstraints gbc_textFieldApellido = new GridBagConstraints();
		gbc_textFieldApellido.gridwidth = 2;
		gbc_textFieldApellido.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldApellido.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldApellido.gridx = 2;
		gbc_textFieldApellido.gridy = 5;
		textFieldApellido.setVisible(false);
		getContentPane().add(textFieldApellido, gbc_textFieldApellido);
		textFieldApellido.setColumns(10);
		
		lblNacimiento = new JLabel("Fecha de nacimiento:");
		GridBagConstraints gbc_lblNacimiento = new GridBagConstraints();
		gbc_lblNacimiento.anchor = GridBagConstraints.EAST;
		gbc_lblNacimiento.insets = new Insets(0, 0, 5, 5);
		gbc_lblNacimiento.gridx = 1;
		gbc_lblNacimiento.gridy = 6;
		lblNacimiento.setVisible(false);
		getContentPane().add(lblNacimiento, gbc_lblNacimiento);
		
		spinnerFechaNacimiento = new JSpinner(new SpinnerDateModel());
		JSpinner.DateEditor de_spinnerFechaNacimiento = new JSpinner.DateEditor(spinnerFechaNacimiento, "dd/MM/yyyy");
		spinnerFechaNacimiento.setEditor(de_spinnerFechaNacimiento);
		GridBagConstraints gbc_spinnerFechaNacimiento = new GridBagConstraints();
		gbc_spinnerFechaNacimiento.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinnerFechaNacimiento.gridwidth = 2;
		gbc_spinnerFechaNacimiento.insets = new Insets(0, 0, 5, 5);
		gbc_spinnerFechaNacimiento.gridx = 2;
		gbc_spinnerFechaNacimiento.gridy = 6;
		spinnerFechaNacimiento.setVisible(false);
		getContentPane().add(spinnerFechaNacimiento, gbc_spinnerFechaNacimiento);
		spinnerFechaNacimiento.setToolTipText("Formato: DD/MM/AAAA");
		
		lblDescripcion = new JLabel("Descripción:");
		GridBagConstraints gbc_lblDescripcion = new GridBagConstraints();
		gbc_lblDescripcion.insets = new Insets(0, 0, 5, 5);
		gbc_lblDescripcion.anchor = GridBagConstraints.EAST;
		gbc_lblDescripcion.gridx = 1;
		gbc_lblDescripcion.gridy = 7;
		getContentPane().add(lblDescripcion, gbc_lblDescripcion);
		
		scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 2;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 2;
		gbc_scrollPane.gridy = 7;
		getContentPane().add(scrollPane, gbc_scrollPane);
		
		textAreaDescripcion = new JTextArea();
		textAreaDescripcion.setLineWrap(true);
		scrollPane.setViewportView(textAreaDescripcion);
		
		lblSitioWeb = new JLabel("Sitio Web (Opcional):");
		GridBagConstraints gbc_lblSitioWeb = new GridBagConstraints();
		gbc_lblSitioWeb.insets = new Insets(0, 0, 5, 5);
		gbc_lblSitioWeb.anchor = GridBagConstraints.EAST;
		gbc_lblSitioWeb.gridx = 1;
		gbc_lblSitioWeb.gridy = 8;
		getContentPane().add(lblSitioWeb, gbc_lblSitioWeb);
		
		textFieldSitioWeb = new JTextField();
		GridBagConstraints gbc_textFieldSitioWeb = new GridBagConstraints();
		gbc_textFieldSitioWeb.gridwidth = 2;
		gbc_textFieldSitioWeb.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldSitioWeb.fill = GridBagConstraints.BOTH;
		gbc_textFieldSitioWeb.gridx = 2;
		gbc_textFieldSitioWeb.gridy = 8;
		getContentPane().add(textFieldSitioWeb, gbc_textFieldSitioWeb);
		textFieldSitioWeb.setColumns(10);
		
		lblInstitucion = new JLabel("Institución (Opcional):");
		GridBagConstraints gbc_lblInstitucion = new GridBagConstraints();
		gbc_lblInstitucion.insets = new Insets(0, 0, 5, 5);
		gbc_lblInstitucion.anchor = GridBagConstraints.EAST;
		gbc_lblInstitucion.gridx = 1;
		gbc_lblInstitucion.gridy = 9;
		lblInstitucion.setVisible(false);
		getContentPane().add(lblInstitucion, gbc_lblInstitucion);
		
		comboBoxInstituciones = new JComboBox<DTInstitucion>();
		GridBagConstraints gbc_comboBoxInstituciones = new GridBagConstraints();
		gbc_comboBoxInstituciones.gridwidth = 2;
		gbc_comboBoxInstituciones.insets = new Insets(0, 0, 5, 5);
		gbc_comboBoxInstituciones.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxInstituciones.gridx = 2;
		gbc_comboBoxInstituciones.gridy = 9;
		comboBoxInstituciones.setVisible(false);
		getContentPane().add(comboBoxInstituciones, gbc_comboBoxInstituciones);
		

		btnAceptar = new JButton("Aceptar");
		btnAceptar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
					cmdAltaUsuarioActionPerformed(arg0);
            }
        });
		GridBagConstraints gbc_btnAceptar = new GridBagConstraints();
		gbc_btnAceptar.insets = new Insets(0, 0, 0, 5);
		gbc_btnAceptar.gridx = 2;
		gbc_btnAceptar.gridy = 10;
		getContentPane().add(btnAceptar, gbc_btnAceptar);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpiarFormulario();
				setVisible(false);
			}
		});
		GridBagConstraints gbc_btnCancelar = new GridBagConstraints();
		gbc_btnCancelar.insets = new Insets(0, 0, 0, 5);
		gbc_btnCancelar.gridx = 3;
		gbc_btnCancelar.gridy = 10;
		getContentPane().add(btnCancelar, gbc_btnCancelar);
		
		

	}

	private void limpiarFormulario() {

        textFieldNombre.setText("");
        textFieldNickname.setText("");
        textFieldCorreo.setText("");
        rdbtnSelecOrganizador.setSelected(true);
        
        lblDescripcion.setVisible(true);
		lblSitioWeb.setVisible(true);
		scrollPane.setVisible(true);
		textAreaDescripcion.setVisible(true);
		textFieldSitioWeb.setVisible(true);
		
		lblNacimiento.setVisible(false);
		lblApellido.setVisible(false);
		textFieldApellido.setVisible(false);
		textFieldApellido.setText("");
		spinnerFechaNacimiento.setVisible(false);
		spinnerFechaNacimiento.setValue(new Date());
		lblInstitucion.setVisible(false);
		comboBoxInstituciones.setVisible(false);
   
        
		textAreaDescripcion.setText("");
        textFieldSitioWeb.setText("");

	}
	
	protected void cmdAltaUsuarioActionPerformed(ActionEvent arg0) {

        // Obtengo datos de los controles Swing
		String nicknameU = this.textFieldNickname.getText();
		String emailU = this.textFieldCorreo.getText();
        String nombreU = this.textFieldNombre.getText();
        String apellidoU = this.textFieldApellido.getText();
        String descripcionU = this.textAreaDescripcion.getText();
        String sitioWebU = this.textFieldSitioWeb.getText();
        Boolean esOrganizador = this.rdbtnSelecOrganizador.isSelected();
        Date fechaSeleccionada = (Date) spinnerFechaNacimiento.getValue();
        LocalDate fecha = fechaSeleccionada.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        DTInstitucion institucionU = (DTInstitucion) comboBoxInstituciones.getSelectedItem();

        if (checkFormulario()) {
        	if(esOrganizador){
        		try {
        			controlUsr.altaOrganizador(nicknameU, emailU, nombreU, descripcionU,"","");
        			controlUsr.ingresarSitioWeb(nicknameU, sitioWebU);
        			
        			// Muestro éxito de la operación
                    JOptionPane.showMessageDialog(this, "El Usuario se ha creado con éxito", "Alta de Usuario",
                            JOptionPane.INFORMATION_MESSAGE);
                    limpiarFormulario();
                    
        		} catch (NicknameRepetidoException e) {
        			// Muestro error de registro
                    JOptionPane.showMessageDialog(this, e.getMessage(), "Alta de Usuario", JOptionPane.ERROR_MESSAGE);
        		} catch (EmailRepetidoException e2) {
        			JOptionPane.showMessageDialog(this, e2.getMessage(), "Alta de Usuario", JOptionPane.ERROR_MESSAGE);
        		}catch (IOException e3) {
        			JOptionPane.showMessageDialog(this, e3.getMessage(), "Alta de Usuario", JOptionPane.ERROR_MESSAGE);
        		}
        	} else {
        		try {
        			controlUsr.altaAsistente(nicknameU, emailU, nombreU, apellidoU, fecha,"","");
        			
        			if(institucionU != null) {
        				controlUsr.ingresarInstitucion(nicknameU, institucionU.getNombre());
        			}
        			// Muestro éxito de la operación
                    JOptionPane.showMessageDialog(this, "El Usuario se ha creado con éxito", "Alta de Usuario",
                            JOptionPane.INFORMATION_MESSAGE);
                    limpiarFormulario();
                    
        		} catch (NicknameRepetidoException e) {
        			// Muestro error de registro
                    JOptionPane.showMessageDialog(this, e.getMessage(), "Alta de Usuario", JOptionPane.ERROR_MESSAGE);
        		}  catch (EmailRepetidoException e2) {
        			JOptionPane.showMessageDialog(this, e2.getMessage(), "Alta de Usuario", JOptionPane.ERROR_MESSAGE);
        		}
        
        
            }
           
        }
    }
	
	private boolean checkFormulario() {							//revisa campos vacios, formato de mail y fecha de nacimiento
		String nicknameU = this.textFieldNickname.getText();
		String emailU = this.textFieldCorreo.getText();
        String nombreU = this.textFieldNombre.getText();
        String apellidoU = this.textFieldApellido.getText();
        String descripcionU = this.textAreaDescripcion.getText();
        Date fechaSeleccionada = (Date) spinnerFechaNacimiento.getValue();
        LocalDate fecha = fechaSeleccionada.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Boolean esOrganizador = this.rdbtnSelecOrganizador.isSelected();
        
        final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        final Pattern PATRON_EMAIL = Pattern.compile(EMAIL_REGEX);
        
        if (nombreU.isEmpty() || emailU.isEmpty() || nicknameU.isEmpty()) {
	            JOptionPane.showMessageDialog(this, "No puede haber campos no opcionales vacíos", "Alta Usuario",
	                    JOptionPane.ERROR_MESSAGE);
	            return false;
	    }
        
        if (!PATRON_EMAIL.matcher(emailU).matches()) {
        	JOptionPane.showMessageDialog(this, "El formato del correo no es válido", "Alta Usuario",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if(esOrganizador) {
        	if (descripcionU.isEmpty()) {
	            JOptionPane.showMessageDialog(this, "No puede haber campos no opcionales vacíos", "Alta Usuario",
	                    JOptionPane.ERROR_MESSAGE);
	            return false;
        	}	
        }
        
        if(!esOrganizador) {
        	if (apellidoU.isEmpty()) {
	            JOptionPane.showMessageDialog(this, "No puede haber campos no opcionales vacíos", "Alta Usuario",
	                    JOptionPane.ERROR_MESSAGE);
	            return false;
        	}
        	if (fecha.isAfter(LocalDate.now())) {
        		JOptionPane.showMessageDialog(this, "La fecha debe ser anterior a la actual", "Alta Usuario",
	                    JOptionPane.ERROR_MESSAGE);
	            return false;
        	}
        	if (fecha.isBefore(LocalDate.now().minusYears(130))) {
        		JOptionPane.showMessageDialog(this, "El usuario no puede tener más de 130 años", "Alta Usuario",
	                    JOptionPane.ERROR_MESSAGE);
	            return false;
        	}
        }
	        return true;
    }
	
	public void cargarInstituciones() {
		if(controlInst.getInstituciones() != null) {
        DefaultComboBoxModel<DTInstitucion> model;
            model = new DefaultComboBoxModel<DTInstitucion>(controlInst.getInstituciones());
            comboBoxInstituciones.setModel(model);
            DTInstitucion def = new DTInstitucion("--Sin Selección--","","","");
            model.insertElementAt(def, 0); 
            comboBoxInstituciones.setSelectedIndex(0);
        }
		else {
			DTInstitucion vacio = new DTInstitucion("--No existen Instituciones--","","","");
			//DefaultComboBoxModel<DTInstitucion> model = new DefaultComboBoxModel<DTInstitucion>();
			//model.insertElementAt(vacio, 0); 
            comboBoxInstituciones.addItem(vacio);
		}
	}
}
