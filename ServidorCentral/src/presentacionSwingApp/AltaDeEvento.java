package presentacionSwingApp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JInternalFrame;
import java.awt.GridBagLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.DefaultListModel;

import excepciones.EventoYaExisteException;

import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.Insets;

import logica.Fabrica;
import logica.IEventosController;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import java.awt.Color;

public class AltaDeEvento extends JInternalFrame {
	private Fabrica fabrica = Fabrica.getInstance(); 
	private IEventosController sistemaEventos = fabrica.getIControladorEventos();
	
	
	private static final long serialVersionUID = 1L;
	private JTextField nombreEvento;
	private JTextField siglaEvento;
	private JTextArea descripcionEvento;
	
    private JList<String> listaCategorias;

	


	
	public AltaDeEvento() {
		setMaximizable(true);
		setIconifiable(true);
		setClosable(true);
		setTitle("Alta De Nuevo Evento");
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.rowHeights = new int[] {19, 30, 36, 34, 30, 0, 0, 36, 30, 0};
		gridBagLayout.columnWidths = new int[] {30, 60, 50, 50, 36, 30, 15, 30, 15, 60, 30};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 1.0, 0.0, 0.0};
		getContentPane().setLayout(gridBagLayout);
		
		JLabel lblIngreseLosSiguientes = new JLabel("Ingrese los siguientes datos del nuevo evento:");
		GridBagConstraints gbc_lblIngreseLosSiguientes = new GridBagConstraints();
		gbc_lblIngreseLosSiguientes.gridwidth = 11;
		gbc_lblIngreseLosSiguientes.insets = new Insets(0, 0, 5, 0);
		gbc_lblIngreseLosSiguientes.gridx = 0;
		gbc_lblIngreseLosSiguientes.gridy = 1;
		getContentPane().add(lblIngreseLosSiguientes, gbc_lblIngreseLosSiguientes);
		
		
		//----------------------------------- Nombre del Evento -------------------------------
		
		JLabel lblNombreDelEvento = new JLabel("Nombre:");
		GridBagConstraints gbc_lblNombreDelEvento = new GridBagConstraints();
		gbc_lblNombreDelEvento.anchor = GridBagConstraints.WEST;
		gbc_lblNombreDelEvento.insets = new Insets(0, 0, 5, 5);
		gbc_lblNombreDelEvento.gridx = 1;
		gbc_lblNombreDelEvento.gridy = 2;
		getContentPane().add(lblNombreDelEvento, gbc_lblNombreDelEvento);
		
		nombreEvento = new JTextField();
		GridBagConstraints gbc_nombreEvento = new GridBagConstraints();
		gbc_nombreEvento.gridwidth = 8;
		gbc_nombreEvento.insets = new Insets(0, 0, 5, 5);
		gbc_nombreEvento.fill = GridBagConstraints.HORIZONTAL;
		gbc_nombreEvento.gridx = 2;
		gbc_nombreEvento.gridy = 2;
		getContentPane().add(nombreEvento, gbc_nombreEvento);
		nombreEvento.setColumns(10);
		
		//----------------------------------- Sigla del Evento -------------------------------
		JLabel lblSiglas = new JLabel("Sigla:");
		GridBagConstraints gbc_lblSiglas = new GridBagConstraints();
		gbc_lblSiglas.anchor = GridBagConstraints.WEST;
		gbc_lblSiglas.insets = new Insets(0, 0, 5, 5);
		gbc_lblSiglas.gridx = 1;
		gbc_lblSiglas.gridy = 3;
		getContentPane().add(lblSiglas, gbc_lblSiglas);
		
		siglaEvento = new JTextField();
		GridBagConstraints gbc_siglaEvento = new GridBagConstraints();
		gbc_siglaEvento.gridwidth = 8;
		gbc_siglaEvento.insets = new Insets(0, 0, 5, 5);
		gbc_siglaEvento.fill = GridBagConstraints.HORIZONTAL;
		gbc_siglaEvento.gridx = 2;
		gbc_siglaEvento.gridy = 3;
		getContentPane().add(siglaEvento, gbc_siglaEvento);
		siglaEvento.setColumns(10);
		
		
		//----------------------------------- Descripcion del Evento -------------------------------
		JLabel lblDescripcion = new JLabel("Descripción:");
		GridBagConstraints gbc_lblDescripcion = new GridBagConstraints();
		gbc_lblDescripcion.anchor = GridBagConstraints.WEST;
		gbc_lblDescripcion.insets = new Insets(0, 0, 5, 5);
		gbc_lblDescripcion.gridx = 1;
		gbc_lblDescripcion.gridy = 4;
		getContentPane().add(lblDescripcion, gbc_lblDescripcion);
		
		descripcionEvento = new JTextArea();
		GridBagConstraints gbc_descripcionEvento = new GridBagConstraints();
		gbc_descripcionEvento.gridheight = 3;
		gbc_descripcionEvento.gridwidth = 8;
		gbc_descripcionEvento.insets = new Insets(0, 0, 5, 5);
		gbc_descripcionEvento.fill = GridBagConstraints.BOTH;
		gbc_descripcionEvento.gridx = 2;
		gbc_descripcionEvento.gridy = 4;
		getContentPane().add(descripcionEvento, gbc_descripcionEvento);
		
		
		
		
		//-------------------Lista de categorias Disponibleas para seleccionar----------------------
		
		JLabel lblNewLabel = new JLabel("Seleccione almenos una categoría");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridwidth = 3;
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 7;
		getContentPane().add(lblNewLabel, gbc_lblNewLabel);
		
		JLabel contadorCategoriasSeleccionadas = new JLabel("Categorias seleccionadas : 0");
		contadorCategoriasSeleccionadas.setForeground(Color.GRAY);
		GridBagConstraints gbc_contadorCategoriasSeleccionadas = new GridBagConstraints();
		gbc_contadorCategoriasSeleccionadas.anchor = GridBagConstraints.WEST;
		gbc_contadorCategoriasSeleccionadas.gridwidth = 3;
		gbc_contadorCategoriasSeleccionadas.insets = new Insets(0, 0, 5, 5);
		gbc_contadorCategoriasSeleccionadas.gridx = 1;
		gbc_contadorCategoriasSeleccionadas.gridy = 8;
		getContentPane().add(contadorCategoriasSeleccionadas, gbc_contadorCategoriasSeleccionadas);
		
		
		
		
		List<String> categoriasDisponibles = sistemaEventos.listarCategorias();
		
		
		listaCategorias = new JList<>(categoriasDisponibles.toArray(new String[0]));
		listaCategorias.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		
		listaCategorias.addListSelectionListener(e -> {
		    int cantCategoriasSeleccionadas = listaCategorias.getSelectedIndices().length;
		    contadorCategoriasSeleccionadas.setText("Categorias seleccionadas : "+cantCategoriasSeleccionadas);
		});
		
		JScrollPane scrollCategorias = new JScrollPane(listaCategorias);
		scrollCategorias.setPreferredSize(new Dimension(200, 80));
		
		GridBagConstraints gbc_listaCategorias = new GridBagConstraints();
		gbc_listaCategorias.gridwidth = 6;
		gbc_listaCategorias.insets = new Insets(0, 0, 5, 5);
		gbc_listaCategorias.fill = GridBagConstraints.BOTH;
		gbc_listaCategorias.gridx = 4;
		gbc_listaCategorias.gridy = 7;
		getContentPane().add(scrollCategorias, gbc_listaCategorias);
		
		
		
		
		
		//----------------------- Botón aceptar // alta evento -------------------------------
		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {//eventoClick. alta evento
				
				try {
					//levanto y valido el nombre
					String nombre = nombreEvento.getText().trim();
					if(nombre.isEmpty()){
						throw new NullPointerException("El Campo de nombre no puede quedar vacío.");
					}
					
					//levanto y valido la sigla
			        String sigla = siglaEvento.getText().trim();
					if(sigla.isEmpty()){
						throw new NullPointerException("El Campo de sigla no puede quedar vacío.");
					}
					
			        //levanto la descripcion
			        String descripcion = descripcionEvento.getText().trim();
			        
			        //levanto y valido las categorias
			        List<String> categoriasSeleccionadas = listaCategorias.getSelectedValuesList();
				    if (categoriasSeleccionadas.isEmpty()) {
				    	throw new IOException("Seleccionr almenos una categoría");
				    }
				    
				    boolean alta = sistemaEventos.altaEvento(nombre, descripcion, sigla, categoriasSeleccionadas, null,"");
				    
				    if (!alta) {
				    	throw new IllegalArgumentException("Error desconocido");
				    }
			        JOptionPane.showMessageDialog(AltaDeEvento.this,"El evento se creó correctamente","Éxito",JOptionPane.INFORMATION_MESSAGE);
			        limpiarFormulario();
			        
				}catch(EventoYaExisteException ex) {
					JOptionPane.showMessageDialog(AltaDeEvento.this, ex.getMessage(), "Alta de Evento", JOptionPane.ERROR_MESSAGE);
				}catch(NullPointerException ex) {
					JOptionPane.showMessageDialog(AltaDeEvento.this, ex.getMessage(), "Alta de Evento", JOptionPane.ERROR_MESSAGE);
				}catch(IOException ex) {
					JOptionPane.showMessageDialog(AltaDeEvento.this, ex.getMessage(), "Alta de Evento", JOptionPane.ERROR_MESSAGE);
				}catch(IllegalArgumentException ex) {
					JOptionPane.showMessageDialog(AltaDeEvento.this, ex.getMessage(), "Alta de Evento", JOptionPane.ERROR_MESSAGE);
				}

			}
		});
		
		
		GridBagConstraints gbc_btnAceptar = new GridBagConstraints();
		gbc_btnAceptar.anchor = GridBagConstraints.EAST;
		gbc_btnAceptar.gridwidth = 3;
		gbc_btnAceptar.insets = new Insets(0, 0, 5, 5);
		gbc_btnAceptar.gridx = 4;
		gbc_btnAceptar.gridy = 8;
		getContentPane().add(btnAceptar, gbc_btnAceptar);
		
		
		//----------------------------------- Botón para cancelar -------------------------------
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		GridBagConstraints gbc_btnCancelar = new GridBagConstraints();
		gbc_btnCancelar.anchor = GridBagConstraints.EAST;
		gbc_btnCancelar.gridwidth = 3;
		gbc_btnCancelar.insets = new Insets(0, 0, 5, 5);
		gbc_btnCancelar.gridx = 7;
		gbc_btnCancelar.gridy = 8;
		getContentPane().add(btnCancelar, gbc_btnCancelar);
		
	}
	private void limpiarFormulario() {
		nombreEvento.setText("");
		siglaEvento.setText("");
		descripcionEvento.setText("");

	}

	public void actualizarCategorias() {
		    // Obtenemos la nueva lista de categorías del sistema
		    List<String> categoriasDisponibles = sistemaEventos.listarCategorias();
		    
		    // Creamos un nuevo DefaultListModel con los datos actualizados
		    DefaultListModel<String> modelo = new DefaultListModel<>();
		    for (String categoria : categoriasDisponibles) {
		        modelo.addElement(categoria);
		    }
		    
		    // Asignamos el nuevo modelo a la JList
		    listaCategorias.setModel(modelo);
		}
}
