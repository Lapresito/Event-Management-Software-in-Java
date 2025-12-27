package presentacionSwingApp;


import javax.swing.JInternalFrame;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;

import javax.swing.JTextField;
import javax.swing.SwingConstants;



import excepciones.UsuarioNoExisteException;
import logica.DTUsuario;
import logica.DTOrganizador;
import logica.DTAsistente;
import logica.DTEdicion;
import logica.IUsuariosController;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class ConsultaDeUsuario extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private IUsuariosController controlUsr;
	private Principal principal;
	
	private JLabel lblSelecUsuario;
	private JLabel lblInfoCorreo;
	private JLabel lblApellido;
	private JLabel lblDescripcion;
	private JLabel lblSitioWeb;
	private JLabel lblFechaNacimiento;
	private JLabel lblRegistros;
	private JLabel lblInstitucion;
	private JLabel lblEdciciones;
	
	private JTextField textFieldApellido;
	private JTextField textFieldSitioWeb;
	private JTextField textFieldCorreo;
	private JTextField textFieldNacimiento;
	private JTextField textFieldInstitucion;
	
	private JComboBox<String> comboBoxUsuarios;
	private JComboBox<DTEdicion> comboBoxEdiciones;
	private JComboBox<String> comboBoxRegistros;
	private JScrollPane scrollPane;
	private JTextArea textAreaDescripcion;


	/**
	 * Create the frame.
	 */
	public ConsultaDeUsuario(IUsuariosController icu, Principal princ) {
		
		// Se inicializa con el controlador de usuarios
        controlUsr = icu;
        this.principal = princ;
        
		setTitle("Consulta de Usuario");
		setResizable(true);
		setIconifiable(true);
		setMaximizable(true);
		setClosable(true);
		setBounds(100, 100, 483, 373);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{51, 100, 98, 35, 0};
		gridBagLayout.rowHeights = new int[]{20, 0, 0, 30, 50, 0, 30, 0, 0, 30, 35, 59, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		
		lblSelecUsuario = new JLabel("Seleccione Usuario:");
		GridBagConstraints gbc_lblSelecUsuario = new GridBagConstraints();
		gbc_lblSelecUsuario.anchor = GridBagConstraints.EAST;
		gbc_lblSelecUsuario.insets = new Insets(0, 0, 5, 5);
		gbc_lblSelecUsuario.gridx = 0;
		gbc_lblSelecUsuario.gridy = 1;
		getContentPane().add(lblSelecUsuario, gbc_lblSelecUsuario);
		
		comboBoxUsuarios = new JComboBox<String>();
		comboBoxUsuarios.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	String seleccionado = (String) comboBoxUsuarios.getSelectedItem();
		        if (seleccionado != "--No existen usuarios--" &&  seleccionado!= null) {
		        	System.out.println(seleccionado);
		        	
		        	DTUsuario user = controlUsr.getAsistente(seleccionado);
		        	if (user == null) {
			        	user = controlUsr.getOrganizador(seleccionado);
		        	}
		        	
		        	if (user != null) {
		        		cmdConsultaUsuarioActionPerformed(user);
			
		        	}
		        }
		    }
		});
		GridBagConstraints gbc_comboBoxUsuarios = new GridBagConstraints();
		gbc_comboBoxUsuarios.gridwidth = 2;
		gbc_comboBoxUsuarios.insets = new Insets(0, 0, 5, 5);
		gbc_comboBoxUsuarios.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxUsuarios.gridx = 1;
		gbc_comboBoxUsuarios.gridy = 1;
		getContentPane().add(comboBoxUsuarios, gbc_comboBoxUsuarios);
		
		lblInfoCorreo = new JLabel("Correo:");
		GridBagConstraints gbc_lblInfoCorreo = new GridBagConstraints();
		gbc_lblInfoCorreo.insets = new Insets(0, 0, 5, 5);
		gbc_lblInfoCorreo.anchor = GridBagConstraints.EAST;
		gbc_lblInfoCorreo.gridx = 0;
		gbc_lblInfoCorreo.gridy = 2;
		lblInfoCorreo.setVisible(false);
		getContentPane().add(lblInfoCorreo, gbc_lblInfoCorreo);
		
		textFieldCorreo = new JTextField();
		textFieldCorreo.setEditable(false);
		GridBagConstraints gbc_textFieldCorreo = new GridBagConstraints();
		gbc_textFieldCorreo.gridwidth = 2;
		gbc_textFieldCorreo.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldCorreo.fill = GridBagConstraints.BOTH;
		gbc_textFieldCorreo.gridx = 1;
		gbc_textFieldCorreo.gridy = 2;
		textFieldCorreo.setVisible(false);
		getContentPane().add(textFieldCorreo, gbc_textFieldCorreo);
		textFieldCorreo.setColumns(10);
		
		lblApellido = new JLabel("Apellido:");
		GridBagConstraints gbc_lblApellido = new GridBagConstraints();
		gbc_lblApellido.insets = new Insets(0, 0, 5, 5);
		gbc_lblApellido.anchor = GridBagConstraints.EAST;
		gbc_lblApellido.gridx = 0;
		gbc_lblApellido.gridy = 3;
		lblApellido.setVisible(false);
		getContentPane().add(lblApellido, gbc_lblApellido);
		
		textFieldApellido = new JTextField();
		textFieldApellido.setEditable(false);
		GridBagConstraints gbc_textFieldApellido = new GridBagConstraints();
		gbc_textFieldApellido.gridwidth = 2;
		gbc_textFieldApellido.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldApellido.fill = GridBagConstraints.BOTH;
		gbc_textFieldApellido.gridx = 1;
		gbc_textFieldApellido.gridy = 3;
		textFieldApellido.setVisible(false);
		getContentPane().add(textFieldApellido, gbc_textFieldApellido);
		textFieldApellido.setColumns(10);
		
		lblDescripcion = new JLabel("Descripción:");
		GridBagConstraints gbc_lblDescripcion = new GridBagConstraints();
		gbc_lblDescripcion.insets = new Insets(0, 0, 5, 5);
		gbc_lblDescripcion.anchor = GridBagConstraints.EAST;
		gbc_lblDescripcion.gridx = 0;
		gbc_lblDescripcion.gridy = 4;
		lblDescripcion.setVisible(false);
		getContentPane().add(lblDescripcion, gbc_lblDescripcion);
		
		lblSitioWeb = new JLabel("Sitio Web:");
		lblSitioWeb.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblSitioWeb = new GridBagConstraints();
		gbc_lblSitioWeb.insets = new Insets(0, 0, 5, 5);
		gbc_lblSitioWeb.anchor = GridBagConstraints.EAST;
		gbc_lblSitioWeb.gridx = 0;
		gbc_lblSitioWeb.gridy = 5;
		lblSitioWeb.setVisible(false);
		getContentPane().add(lblSitioWeb, gbc_lblSitioWeb);
		
		scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 2;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 4;
		getContentPane().add(scrollPane, gbc_scrollPane);
		scrollPane.setVisible(false);
		
		textAreaDescripcion = new JTextArea();
		textAreaDescripcion.setLineWrap(true);
		textAreaDescripcion.setEditable(false);
		scrollPane.setViewportView(textAreaDescripcion);
		textAreaDescripcion.setVisible(false);
		
		textFieldSitioWeb = new JTextField();
		textFieldSitioWeb.setEditable(false);
		GridBagConstraints gbc_textFieldSitioWeb = new GridBagConstraints();
		gbc_textFieldSitioWeb.gridwidth = 2;
		gbc_textFieldSitioWeb.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldSitioWeb.fill = GridBagConstraints.BOTH;
		gbc_textFieldSitioWeb.gridx = 1;
		gbc_textFieldSitioWeb.gridy = 5;
		textFieldSitioWeb.setVisible(false);
		getContentPane().add(textFieldSitioWeb, gbc_textFieldSitioWeb);
		textFieldSitioWeb.setColumns(10);
		
		lblFechaNacimiento = new JLabel("Fecha de Nacimiento");
		GridBagConstraints gbc_lblFechaNacimiento = new GridBagConstraints();
		gbc_lblFechaNacimiento.insets = new Insets(0, 0, 5, 5);
		gbc_lblFechaNacimiento.anchor = GridBagConstraints.EAST;
		gbc_lblFechaNacimiento.gridx = 0;
		gbc_lblFechaNacimiento.gridy = 6;
		lblFechaNacimiento.setVisible(false);
		getContentPane().add(lblFechaNacimiento, gbc_lblFechaNacimiento);
		
		textFieldNacimiento = new JTextField();
		textFieldNacimiento.setEditable(false);
		GridBagConstraints gbc_textFieldNacimiento = new GridBagConstraints();
		gbc_textFieldNacimiento.gridwidth = 2;
		gbc_textFieldNacimiento.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldNacimiento.fill = GridBagConstraints.BOTH;
		gbc_textFieldNacimiento.gridx = 1;
		gbc_textFieldNacimiento.gridy = 6;
		textFieldNacimiento.setVisible(false);
		getContentPane().add(textFieldNacimiento, gbc_textFieldNacimiento);
		textFieldNacimiento.setColumns(10);
		
		lblRegistros = new JLabel("Registros:");
		GridBagConstraints gbc_lblRegistros = new GridBagConstraints();
		gbc_lblRegistros.insets = new Insets(0, 0, 5, 5);
		gbc_lblRegistros.anchor = GridBagConstraints.EAST;
		gbc_lblRegistros.gridx = 0;
		gbc_lblRegistros.gridy = 8;
		lblRegistros.setVisible(false);
		
		lblInstitucion = new JLabel("Institución:");
		GridBagConstraints gbc_lblInstitucion = new GridBagConstraints();
		gbc_lblInstitucion.fill = GridBagConstraints.VERTICAL;
		gbc_lblInstitucion.anchor = GridBagConstraints.EAST;
		gbc_lblInstitucion.insets = new Insets(0, 0, 5, 5);
		gbc_lblInstitucion.gridx = 0;
		gbc_lblInstitucion.gridy = 7;
		lblInstitucion.setVisible(false);
		getContentPane().add(lblInstitucion, gbc_lblInstitucion);
		
		textFieldInstitucion = new JTextField();
		textFieldInstitucion.setEditable(false);
		GridBagConstraints gbc_textFieldInstitucion = new GridBagConstraints();
		gbc_textFieldInstitucion.gridwidth = 2;
		gbc_textFieldInstitucion.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldInstitucion.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldInstitucion.gridx = 1;
		gbc_textFieldInstitucion.gridy = 7;
		textFieldInstitucion.setVisible(false);
		getContentPane().add(textFieldInstitucion, gbc_textFieldInstitucion);
		textFieldInstitucion.setColumns(10);
		getContentPane().add(lblRegistros, gbc_lblRegistros);
		
		comboBoxRegistros = new JComboBox<String>();							//al seleccionar tiene que ir a consulta de registros
		comboBoxRegistros.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	if (e.getSource() != comboBoxRegistros) return;
		        String nombreEdicion = (String) comboBoxRegistros.getSelectedItem();
		        String asist = (String) comboBoxUsuarios.getSelectedItem();
		        
	        	DTAsistente user = controlUsr.getAsistente(asist);
	        	if (user != null) {
	        		String nickUsuario = user.getNickname();		
	        		if (nombreEdicion != "--El asistente no tiene registros--" && nombreEdicion != "--No existen usuarios--" && nombreEdicion != null ) {
	        			principal.mostrarDatosRegistro(nickUsuario, nombreEdicion);
	        		}
	        	}
		    }
		});
		GridBagConstraints gbc_comboBoxRegistros = new GridBagConstraints();
		gbc_comboBoxRegistros.gridwidth = 2;
		gbc_comboBoxRegistros.insets = new Insets(0, 0, 5, 5);
		gbc_comboBoxRegistros.fill = GridBagConstraints.BOTH;
		gbc_comboBoxRegistros.gridx = 1;
		gbc_comboBoxRegistros.gridy = 8;
		comboBoxRegistros.setVisible(false);
		getContentPane().add(comboBoxRegistros, gbc_comboBoxRegistros);
		
		lblEdciciones = new JLabel("Ediciones:");
		GridBagConstraints gbc_lblEdciciones = new GridBagConstraints();
		gbc_lblEdciciones.insets = new Insets(0, 0, 5, 5);
		gbc_lblEdciciones.anchor = GridBagConstraints.EAST;
		gbc_lblEdciciones.gridx = 0;
		gbc_lblEdciciones.gridy = 9;
		lblEdciciones.setVisible(false);
		getContentPane().add(lblEdciciones, gbc_lblEdciciones);
		
		comboBoxEdiciones = new JComboBox<DTEdicion>();						//al seleccionar tiene que ir a consulta de ediciones
		comboBoxEdiciones.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        DTEdicion seleccionado = (DTEdicion) comboBoxEdiciones.getSelectedItem();
		        if (seleccionado != null) {
		        	if(seleccionado.getNombreEdicion() != "--El organizador no tiene ediciones--") {
		        		principal.mostrarDatosEdicion(seleccionado);
		        	}	
		        }
		    }
		});
		GridBagConstraints gbc_comboBoxEdiciones = new GridBagConstraints();
		gbc_comboBoxEdiciones.gridwidth = 2;
		gbc_comboBoxEdiciones.insets = new Insets(0, 0, 5, 5);
		gbc_comboBoxEdiciones.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxEdiciones.gridx = 1;
		gbc_comboBoxEdiciones.gridy = 9;
		comboBoxEdiciones.setVisible(false);
		getContentPane().add(comboBoxEdiciones, gbc_comboBoxEdiciones);
		
		JButton btnReset = new JButton("Reset");
		btnReset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                limpiarFormulario();
            }
        });
		GridBagConstraints gbc_btnReset = new GridBagConstraints();
		gbc_btnReset.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnReset.insets = new Insets(0, 0, 5, 5);
		gbc_btnReset.gridx = 1;
		gbc_btnReset.gridy = 10;
		getContentPane().add(btnReset, gbc_btnReset);
		
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
		gbc_btnCancelar.gridy = 10;
		getContentPane().add(btnCancelar, gbc_btnCancelar);

	}
	private void limpiarFormulario() {
        textFieldApellido.setText("");
        textFieldCorreo.setText("");
        textAreaDescripcion.setText("");
        textFieldSitioWeb.setText("");
        textFieldNacimiento.setText("");
        textFieldInstitucion.setText("");
                
        lblInfoCorreo.setVisible(false);
    	lblApellido.setVisible(false);
    	lblDescripcion.setVisible(false);
    	lblSitioWeb.setVisible(false);
    	lblFechaNacimiento.setVisible(false);
    	lblRegistros.setVisible(false);
    	lblInstitucion.setVisible(false);
    	lblEdciciones.setVisible(false);
    	
    	textFieldApellido.setVisible(false);
    	textFieldSitioWeb.setVisible(false);
    	textFieldCorreo.setVisible(false);
    	textAreaDescripcion.setVisible(false);
    	textFieldNacimiento.setVisible(false);
    	textFieldInstitucion.setVisible(false);
    	        
    	scrollPane.setVisible(false);
    	
        comboBoxEdiciones.setVisible(false);
        //comboBoxEdiciones.removeAllItems();
        comboBoxRegistros.setVisible(false);
        //comboBoxRegistros.removeAllItems();

	}
	
	public void cmdConsultaUsuarioActionPerformed(DTUsuario seleccionado) {
		limpiarFormulario();
				
		if(controlUsr.esOrganizador(seleccionado.getNickname())) {
			lblInfoCorreo.setVisible(true);
			textFieldCorreo.setText(seleccionado.getEmail());
			textFieldCorreo.setVisible(true);
			DTOrganizador organizador = controlUsr.getOrganizador(seleccionado.getNickname());
			
			lblSitioWeb.setVisible(true);
			textFieldSitioWeb.setText(organizador.getSitioWeb());
			textFieldSitioWeb.setVisible(true);
			
			lblDescripcion.setVisible(true);
			scrollPane.setVisible(true);
			textAreaDescripcion.setText(organizador.getDescripcion());
			textAreaDescripcion.setVisible(true);

			lblEdciciones.setVisible(true);
			DefaultComboBoxModel<DTEdicion> modeleds = new DefaultComboBoxModel<>(organizador.getEdiciones());
			comboBoxEdiciones.setModel(modeleds);
			if(organizador.getEdiciones().length == 0) {
				comboBoxEdiciones.removeAllItems();
				comboBoxEdiciones.addItem(new DTEdicion("--El organizador no tiene ediciones--", ""));
			}
			modeleds.insertElementAt(null, 0); 
			comboBoxEdiciones.setVisible(true);
			
		} else if (controlUsr.esAsistente(seleccionado.getNickname())){ //es Asistente
			lblInfoCorreo.setVisible(true);
			textFieldCorreo.setText(seleccionado.getEmail());
			textFieldCorreo.setVisible(true);
			DTAsistente asistente = controlUsr.getAsistente(seleccionado.getNickname());
			
			lblApellido.setVisible(true);
			textFieldApellido.setText(asistente.getApellido());
			textFieldApellido.setVisible(true);
			
			lblFechaNacimiento.setVisible(true);
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			textFieldNacimiento.setText(asistente.getNacimiento().format(formatter));  
			textFieldNacimiento.setVisible(true);
			
			lblInstitucion.setVisible(true);
			textFieldInstitucion.setText(asistente.getInstitucion());
			textFieldInstitucion.setVisible(true);
			
			lblRegistros.setVisible(true);
			DefaultComboBoxModel<String> modelregs = new DefaultComboBoxModel<>(asistente.getRegistros());
			comboBoxRegistros.setModel(modelregs);
			if(asistente.getRegistros().length == 0) {
				comboBoxRegistros.removeAllItems();
				comboBoxRegistros.addItem("--El asistente no tiene registros--");
				//comboBoxRegistros.setSelectedItem("--El asistente no tiene registros--");
			} else {
				//modelregs.insertElementAt("--Sin Selección--",0);
				//comboBoxRegistros.setSelectedItem("--Sin Selección--");
			}
			
			comboBoxRegistros.setVisible(true);

		}
		
	}
	public void cargarUsuarios() {
	    DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
	    comboBoxUsuarios.removeAllItems(); // limpiar antes

	    try {
	        DTUsuario[] usuarios = controlUsr.getUsuarios();

	        // Ordenar por nombre
	        //List.sort(usuarios, Comparator.comparing(
	            //DtUsuario::getNombre, String.CASE_INSENSITIVE_ORDER
	        //));

	        if (usuarios.length != 0 ) {
	            // Mensaje inicial de "sin selección"
	            model.addElement("--Sin Selección--");

	            // Agregar todos los usuarios
	            for (DTUsuario u : usuarios) {
	                model.addElement(u.getNickname());
	            }
	        } else {
	            // No hay usuarios
	            model.addElement("--No existen usuarios--");
	        }

	    } catch (UsuarioNoExisteException e) {
	        // No hay usuarios
	        model.addElement("--No existen usuarios--");
	    }

	    comboBoxUsuarios.setModel(model);
	    comboBoxUsuarios.setSelectedIndex(0); // mostrar mensaje inicial
	}


}
