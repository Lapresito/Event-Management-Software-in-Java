package presentacionSwingApp;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JTextField;

import logica.DTEvento;
import logica.Fabrica;
import logica.IEventosController;
import javax.swing.DefaultComboBoxModel;

public class ConsultaDeEvento extends JInternalFrame {
	private Fabrica fabrica = Fabrica.getInstance(); 
	private IEventosController sistemaEventos = fabrica.getIControladorEventos();

	private static final long serialVersionUID = 1L;
	private JTextField textFieldNombre;
	private JTextField textFieldSigla;
	private JTextField textFieldFechaAlta;

	private JComboBox<String> comboBoxCategoria;
	private JComboBox<String> comboBoxEdicion;
	private JComboBox<String> comboBoxEventos;
	private JTextField textFieldVisitas;

	/**
	 * Create the frame.
	 */
	public ConsultaDeEvento() {
		setTitle("Consulta de Evento");
		setResizable(true);
		setIconifiable(true);
		setMaximizable(true);
		setClosable(true);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{51, 100, 98, 35, 0};
		gridBagLayout.rowHeights = new int[]{20, 0, 30, 30, 30, 0, 30, 35, 59, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		
		
		// ---------------------  LISTA EVENTOS ----------------------------------------
		JLabel lblSelecEvento = new JLabel("Seleccione Evento:");
		GridBagConstraints gbc_lblSelecEvento = new GridBagConstraints();
		gbc_lblSelecEvento.anchor = GridBagConstraints.EAST;
		gbc_lblSelecEvento.insets = new Insets(0, 0, 5, 5);
		gbc_lblSelecEvento.gridx = 0;
		gbc_lblSelecEvento.gridy = 1;
		getContentPane().add(lblSelecEvento, gbc_lblSelecEvento);
	
		comboBoxEventos = new JComboBox<>();
		actualizarComboDeEventos();
		comboBoxEventos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {            	
            	try {
            		String eventoSeleccionado = (String) comboBoxEventos.getSelectedItem();
            		if (eventoSeleccionado == "Sin selección" || eventoSeleccionado == null) {
            			limpiarFormulario();
            		}else{
            			DTEvento evento = sistemaEventos.infoEvento(eventoSeleccionado);
            			textFieldNombre.setText(evento.getNombre());
            			textFieldSigla.setText(evento.getSigla());
            			textFieldFechaAlta.setText(evento.getFechaAlta().toString());
            			textFieldVisitas.setText(String.valueOf(evento.getVisitas()));           			
            			comboBoxCategoria.removeAllItems();
            			List<String> categorias = evento.getCategorias();
            			for (String cat : categorias) {
            				comboBoxCategoria.addItem(cat);
            			}
            			
            			comboBoxEdicion.removeAllItems();
            			List<String> ediciones = evento.getEdiciones();
            			if (ediciones.isEmpty()) {
            				comboBoxEdicion.addItem("No contiene ediciones aún.");
            			}else {
            				for (String ed : ediciones) {
            					comboBoxEdicion.addItem(ed);
            				}		        		
            			}
            		}
            	}catch(IllegalArgumentException ex) {
            		JOptionPane.showMessageDialog(ConsultaDeEvento.this, ex.getMessage(), "Consulta de Evento", JOptionPane.ERROR_MESSAGE);
            	}
            }
        });
		
		
		
		
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.gridwidth = 2;
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 1;
		gbc_comboBox.gridy = 1;
		getContentPane().add(comboBoxEventos, gbc_comboBox);
		
		
		// ---------------------  CAMPO NOMBRE ----------------------------------------
		JLabel lblInfoNombre = new JLabel("Nombre:");
		GridBagConstraints gbc_lblInfoNombre = new GridBagConstraints();
		gbc_lblInfoNombre.insets = new Insets(0, 0, 5, 5);
		gbc_lblInfoNombre.anchor = GridBagConstraints.EAST;
		gbc_lblInfoNombre.gridx = 0;
		gbc_lblInfoNombre.gridy = 2;
		getContentPane().add(lblInfoNombre, gbc_lblInfoNombre);
		
		textFieldNombre = new JTextField();
		textFieldNombre.setEditable(false);
		GridBagConstraints gbc_textFieldNombre = new GridBagConstraints();
		gbc_textFieldNombre.gridwidth = 2;
		gbc_textFieldNombre.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldNombre.fill = GridBagConstraints.BOTH;
		gbc_textFieldNombre.gridx = 1;
		gbc_textFieldNombre.gridy = 2;
		getContentPane().add(textFieldNombre, gbc_textFieldNombre);
		textFieldNombre.setColumns(10);
		
		
		// ---------------------  CAMPO SIGLA ----------------------------------------
		JLabel lblInfoSigla = new JLabel("Sigla:");
		GridBagConstraints gbc_lblInfoSigla = new GridBagConstraints();
		gbc_lblInfoSigla.insets = new Insets(0, 0, 5, 5);
		gbc_lblInfoSigla.anchor = GridBagConstraints.EAST;
		gbc_lblInfoSigla.gridx = 0;
		gbc_lblInfoSigla.gridy = 3;
		getContentPane().add(lblInfoSigla, gbc_lblInfoSigla);
		
		textFieldSigla = new JTextField();
		textFieldSigla.setEditable(false);
		GridBagConstraints gbc_textFieldSigla = new GridBagConstraints();
		gbc_textFieldSigla.gridwidth = 2;
		gbc_textFieldSigla.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldSigla.fill = GridBagConstraints.BOTH;
		gbc_textFieldSigla.gridx = 1;
		gbc_textFieldSigla.gridy = 3;
		getContentPane().add(textFieldSigla, gbc_textFieldSigla);
		textFieldSigla.setColumns(10);
		
		
		// ---------------------  CAMPO FECHA ALTA ----------------------------------------
		
		JLabel lblInfoFechaAlta = new JLabel("Fecha de Alta:");
		GridBagConstraints gbc_lblInfoFechaAlta = new GridBagConstraints();
		gbc_lblInfoFechaAlta.insets = new Insets(0, 0, 5, 5);
		gbc_lblInfoFechaAlta.anchor = GridBagConstraints.EAST;
		gbc_lblInfoFechaAlta.gridx = 0;
		gbc_lblInfoFechaAlta.gridy = 4;
		getContentPane().add(lblInfoFechaAlta, gbc_lblInfoFechaAlta);
		
		textFieldFechaAlta = new JTextField();
		textFieldFechaAlta.setEditable(false);
		GridBagConstraints gbc_textFieldFechaAlta = new GridBagConstraints();
		gbc_textFieldFechaAlta.gridwidth = 2;
		gbc_textFieldFechaAlta.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldFechaAlta.fill = GridBagConstraints.BOTH;
		gbc_textFieldFechaAlta.gridx = 1;
		gbc_textFieldFechaAlta.gridy = 4;
		getContentPane().add(textFieldFechaAlta, gbc_textFieldFechaAlta);
		textFieldFechaAlta.setColumns(10);
		


		// ---------------------  LISTA Categorias ----------------------------------------
		
		JLabel lblVerCategoria = new JLabel("Categorias:");
		GridBagConstraints gbc_lblVerCategoria = new GridBagConstraints();
		gbc_lblVerCategoria.anchor = GridBagConstraints.EAST;
		gbc_lblVerCategoria.insets = new Insets(0, 0, 5, 5);
		gbc_lblVerCategoria.gridx = 0;
		gbc_lblVerCategoria.gridy = 5;
		getContentPane().add(lblVerCategoria, gbc_lblVerCategoria);
		
		comboBoxCategoria = new JComboBox<>();
		GridBagConstraints gbc_comboBoxCategoria = new GridBagConstraints();
		gbc_comboBoxCategoria.gridwidth = 2;
		gbc_comboBoxCategoria.insets = new Insets(0, 0, 5, 5);
		gbc_comboBoxCategoria.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxCategoria.gridx = 1;
		gbc_comboBoxCategoria.gridy = 5;
		getContentPane().add(comboBoxCategoria, gbc_comboBoxCategoria);
		
		
		// ---------------------  LISTA Ediciones ----------------------------------------
		
		JLabel lblVerEdicion = new JLabel("Ediciones:");
		GridBagConstraints gbc_lblVerEdicion = new GridBagConstraints();
		gbc_lblVerEdicion.anchor = GridBagConstraints.EAST;
		gbc_lblVerEdicion.insets = new Insets(0, 0, 5, 5);
		gbc_lblVerEdicion.gridx = 0;
		gbc_lblVerEdicion.gridy = 6;
		getContentPane().add(lblVerEdicion, gbc_lblVerEdicion);
		
		
		
		// ----------------- BOTON Cancelar -------------------------------------
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                limpiarFormulario();
                dispose();
            }
        });
		
		comboBoxEdicion = new JComboBox<>();
		GridBagConstraints gbc_comboBoxEdicion = new GridBagConstraints();
		gbc_comboBoxEdicion.gridwidth = 2;
		gbc_comboBoxEdicion.insets = new Insets(0, 0, 5, 5);
		gbc_comboBoxEdicion.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxEdicion.gridx = 1;
		gbc_comboBoxEdicion.gridy = 6;
		getContentPane().add(comboBoxEdicion, gbc_comboBoxEdicion);
		
		JLabel lblVisitas = new JLabel("Visitas:");
		GridBagConstraints gbc_lblVisitas = new GridBagConstraints();
		gbc_lblVisitas.anchor = GridBagConstraints.EAST;
		gbc_lblVisitas.insets = new Insets(0, 0, 5, 5);
		gbc_lblVisitas.gridx = 0;
		gbc_lblVisitas.gridy = 7;
		getContentPane().add(lblVisitas, gbc_lblVisitas);
		
		textFieldVisitas = new JTextField();
		textFieldVisitas.setEditable(false);
		GridBagConstraints gbc_textFieldVisitas = new GridBagConstraints();
		gbc_textFieldVisitas.gridwidth = 2;
		gbc_textFieldVisitas.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldVisitas.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldVisitas.gridx = 1;
		gbc_textFieldVisitas.gridy = 7;
		getContentPane().add(textFieldVisitas, gbc_textFieldVisitas);
		textFieldVisitas.setColumns(10);
		GridBagConstraints gbc_btnCancelar = new GridBagConstraints();
		gbc_btnCancelar.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnCancelar.insets = new Insets(0, 0, 0, 5);
		gbc_btnCancelar.gridx = 2;
		gbc_btnCancelar.gridy = 8;
		getContentPane().add(btnCancelar, gbc_btnCancelar);

	}
		        
		        
		        
	public void actualizarComboDeEventos() {
		comboBoxEventos.removeAllItems();
		List<String> ListaEventos = new ArrayList<>();
		ListaEventos.add("Sin selección");
		List<String> eventos =sistemaEventos.listarEventos();
		Collections.sort(eventos);
		ListaEventos.addAll(eventos);
    	for (String ev : ListaEventos) {
    		comboBoxEventos.addItem(ev);
        }
    	comboBoxEventos.setSelectedItem("Sin selección");
	}
	private void limpiarFormulario() {
        textFieldNombre.setText("");
        textFieldSigla.setText("");
        textFieldFechaAlta.setText("");
    	comboBoxCategoria.removeAllItems();
    	comboBoxEdicion.removeAllItems();
	}

}
