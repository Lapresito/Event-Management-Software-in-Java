package presentacionSwingApp;

import java.awt.EventQueue;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;

import javax.swing.JInternalFrame;
import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JFormattedTextField;
import java.awt.GridBagConstraints;
import javax.swing.JComboBox;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JTextField;

import logica.Fabrica;
import logica.IEventosController;
import logica.DTEdicion;
import logica.DTRegistro;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;

public class AceptarRechazarEdiciones extends JInternalFrame {
	private IEventosController ctrlEvento = Fabrica.getInstance().getIControladorEventos();
	private static final long serialVersionUID = 1L;
	
	private Principal principal;
	private JTextField textFieldOrganizador;
	
	
	private JComboBox<String> comboBoxEventos;
	private JComboBox<String> comboBoxEdicionesDeEvento;
	private JTextField textField_Estado;
	private JComboBox<String> comboBoxTiposDeRegistro;
	JButton btnCancelar = new JButton("Cancelar");
	private JTextField textFechaIni;
	private JTextField textFechaFin;

	  public AceptarRechazarEdiciones(Principal pr) {
	    principal = pr;
	    setTitle("Aceptar y/o rechazar Edición de Evento");
	    setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	    setClosable(true);
	    setResizable(true);
	    setIconifiable(true);
	    setMaximizable(true);
	    setBounds(100, 100, 450, 300);
	    GridBagLayout gridBagLayout = new GridBagLayout();
	    gridBagLayout.columnWidths = new int[] { 0, 0, 92, 95, 0, 0, 0 };
	    gridBagLayout.rowHeights = new int[] { 15, 15, 20, 10, 0, 0, 0, 28, 31, 0 };
	    gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, Double.MIN_VALUE };
	    gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0,
	        Double.MIN_VALUE };
	    getContentPane().setLayout(gridBagLayout);

	    // --------------------- LISTA EVENTOS ----------------------------------------
	    JLabel lblSelecEvento = new JLabel("Seleccione Evento:");
	    GridBagConstraints gbc_lblSelecEvento = new GridBagConstraints();
	    gbc_lblSelecEvento.insets = new Insets(0, 0, 5, 5);
	    gbc_lblSelecEvento.anchor = GridBagConstraints.EAST;
	    gbc_lblSelecEvento.gridx = 1;
	    gbc_lblSelecEvento.gridy = 1;
	    getContentPane().add(lblSelecEvento, gbc_lblSelecEvento);

	    comboBoxEventos = new JComboBox<String>();
	    GridBagConstraints gbc_comboBoxEventos = new GridBagConstraints();
	    gbc_comboBoxEventos.gridwidth = 3;
	    gbc_comboBoxEventos.insets = new Insets(0, 0, 5, 5);
	    gbc_comboBoxEventos.fill = GridBagConstraints.BOTH;
	    gbc_comboBoxEventos.gridx = 2;
	    gbc_comboBoxEventos.gridy = 1;
	    getContentPane().add(comboBoxEventos, gbc_comboBoxEventos);

	    // --------------------- LISTA EDICIONES DE EVENTO ----------------------------------------
	    JLabel lblSelecEdicion = new JLabel("Seleccione Edición:");
	    GridBagConstraints gbc_lblSelecEdicion = new GridBagConstraints();
	    gbc_lblSelecEdicion.fill = GridBagConstraints.VERTICAL;
	    gbc_lblSelecEdicion.anchor = GridBagConstraints.EAST;
	    gbc_lblSelecEdicion.insets = new Insets(0, 0, 5, 5);
	    gbc_lblSelecEdicion.gridx = 1;
	    gbc_lblSelecEdicion.gridy = 2;
	    getContentPane().add(lblSelecEdicion, gbc_lblSelecEdicion);

	    comboBoxEdicionesDeEvento = new JComboBox<String>();
	    GridBagConstraints gbc_comboBoxEdicionesDeEvento = new GridBagConstraints();
	    gbc_comboBoxEdicionesDeEvento.gridwidth = 3;
	    gbc_comboBoxEdicionesDeEvento.insets = new Insets(0, 0, 5, 5);
	    gbc_comboBoxEdicionesDeEvento.fill = GridBagConstraints.BOTH;
	    gbc_comboBoxEdicionesDeEvento.gridx = 2;
	    gbc_comboBoxEdicionesDeEvento.gridy = 2;
	    getContentPane().add(comboBoxEdicionesDeEvento, gbc_comboBoxEdicionesDeEvento);

	    // --------------------- FECHA DE ALTA EVENTO ----------------------------------------
	    JLabel lblFechaIni = new JLabel("Fecha de inicio:");
	    GridBagConstraints gbc_lblFechaIni = new GridBagConstraints();
	    gbc_lblFechaIni.insets = new Insets(0, 0, 5, 5);
	    gbc_lblFechaIni.anchor = GridBagConstraints.SOUTHEAST;
	    gbc_lblFechaIni.gridx = 1;
	    gbc_lblFechaIni.gridy = 3;
	    getContentPane().add(lblFechaIni, gbc_lblFechaIni);

	    textFechaIni = new JTextField();
	    textFechaIni.setText("");
	    textFechaIni.setEditable(false);
	    textFechaIni.setColumns(10);
	    GridBagConstraints gbc_textFechaIni = new GridBagConstraints();
	    gbc_textFechaIni.anchor = GridBagConstraints.SOUTH;
	    gbc_textFechaIni.insets = new Insets(0, 0, 5, 5);
	    gbc_textFechaIni.fill = GridBagConstraints.HORIZONTAL;
	    gbc_textFechaIni.gridx = 2;
	    gbc_textFechaIni.gridy = 3;
	    getContentPane().add(textFechaIni, gbc_textFechaIni);

	    JLabel lblFechaFin = new JLabel("Fecha de Fin:");
	    GridBagConstraints gbc_lblFechaFin = new GridBagConstraints();
	    gbc_lblFechaFin.anchor = GridBagConstraints.SOUTHEAST;
	    gbc_lblFechaFin.insets = new Insets(0, 0, 5, 5);
	    gbc_lblFechaFin.gridx = 3;
	    gbc_lblFechaFin.gridy = 3;
	    getContentPane().add(lblFechaFin, gbc_lblFechaFin);

	    textFechaFin = new JTextField();
	    textFechaFin.setText("");
	    textFechaFin.setEditable(false);
	    textFechaFin.setColumns(10);
	    GridBagConstraints gbc_textFechaFin = new GridBagConstraints();
	    gbc_textFechaFin.anchor = GridBagConstraints.SOUTH;
	    gbc_textFechaFin.insets = new Insets(0, 0, 5, 5);
	    gbc_textFechaFin.fill = GridBagConstraints.HORIZONTAL;
	    gbc_textFechaFin.gridx = 4;
	    gbc_textFechaFin.gridy = 3;
	    getContentPane().add(textFechaFin, gbc_textFechaFin);

	    // --------------------- ORGANIZADOR ----------------------------------------
	    JLabel lblOrganizador = new JLabel("Organizador:");
	    GridBagConstraints gbc_lblOrganizador = new GridBagConstraints();
	    gbc_lblOrganizador.insets = new Insets(0, 0, 5, 5);
	    gbc_lblOrganizador.anchor = GridBagConstraints.EAST;
	    gbc_lblOrganizador.gridx = 1;
	    gbc_lblOrganizador.gridy = 4;
	    getContentPane().add(lblOrganizador, gbc_lblOrganizador);

	    textFieldOrganizador = new JTextField();
	    textFieldOrganizador.setEditable(false);
	    GridBagConstraints gbc_textFieldOrganizador = new GridBagConstraints();
	    gbc_textFieldOrganizador.insets = new Insets(0, 0, 5, 5);
	    gbc_textFieldOrganizador.fill = GridBagConstraints.HORIZONTAL;
	    gbc_textFieldOrganizador.gridx = 2;
	    gbc_textFieldOrganizador.gridy = 4;
	    getContentPane().add(textFieldOrganizador, gbc_textFieldOrganizador);
	    textFieldOrganizador.setColumns(10);
	    
	    // --------------------- Estado ----------------------------------------
	    JLabel lblEstado = new JLabel("Estado:");
	    GridBagConstraints gbc_lblEstado = new GridBagConstraints();
	    gbc_lblEstado.insets = new Insets(0, 0, 5, 5);
	    gbc_lblEstado.anchor = GridBagConstraints.EAST;
	    gbc_lblEstado.gridx = 3;
	    gbc_lblEstado.gridy = 4;
	    getContentPane().add(lblEstado, gbc_lblEstado);
	    
	    textField_Estado = new JTextField();
	    textField_Estado.setEditable(false);
	    GridBagConstraints gbc_textField_Estado = new GridBagConstraints();
	    gbc_textField_Estado.insets = new Insets(0, 0, 5, 5);
	    gbc_textField_Estado.fill = GridBagConstraints.HORIZONTAL;
	    gbc_textField_Estado.gridx = 4;
	    gbc_textField_Estado.gridy = 4;
	    getContentPane().add(textField_Estado, gbc_textField_Estado);
	    textField_Estado.setColumns(10);

	    // --------------------- TIPO DE REGISTRO ----------------------------------------
	    JLabel lblTiposDeRegistro = new JLabel("Tipos de Registro:");
	    GridBagConstraints gbc_lblTiposDeRegistro = new GridBagConstraints();
	    gbc_lblTiposDeRegistro.insets = new Insets(0, 0, 5, 5);
	    gbc_lblTiposDeRegistro.anchor = GridBagConstraints.EAST;
	    gbc_lblTiposDeRegistro.gridx = 1;
	    gbc_lblTiposDeRegistro.gridy = 5;
	    getContentPane().add(lblTiposDeRegistro, gbc_lblTiposDeRegistro);

	    comboBoxTiposDeRegistro = new JComboBox<String>();
	    comboBoxTiposDeRegistro.addItemListener(new ItemListener() {
	      @Override
	      public void itemStateChanged(ItemEvent e) {
	        if (e.getStateChange() == ItemEvent.SELECTED) {
	          String tiposeleccionado = (String) comboBoxTiposDeRegistro.getSelectedItem();
	          String edicionseleccionada = (String) comboBoxEdicionesDeEvento.getSelectedItem();
	          if (tiposeleccionado != null
	              && tiposeleccionado != "La edicion no contiene tipos de registros") {
	            principal.mostrarDatosTipoRegistro(edicionseleccionada, tiposeleccionado);
	          }
	        }
	      }
	    });
	    comboBoxTiposDeRegistro.setSelectedIndex(-1);

	    GridBagConstraints gbc_comboBoxTiposDeRegistro = new GridBagConstraints();
	    gbc_comboBoxTiposDeRegistro.gridwidth = 3;
	    gbc_comboBoxTiposDeRegistro.insets = new Insets(0, 0, 5, 5);
	    gbc_comboBoxTiposDeRegistro.fill = GridBagConstraints.HORIZONTAL;
	    gbc_comboBoxTiposDeRegistro.gridx = 2;
	    gbc_comboBoxTiposDeRegistro.gridy = 5;
	    getContentPane().add(comboBoxTiposDeRegistro, gbc_comboBoxTiposDeRegistro);
	    
	    
	    // --------------------- BTN ACEPTAR ----------------------------------------
	    JButton btnAceptar = new JButton("Aceptar");
	    btnAceptar.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		try {
	    			String ed = (String) comboBoxEdicionesDeEvento.getSelectedItem();
	    			if (ed == null || ed == "Seleccione una evento" || ed == "El evento no contiene ediciones ingresadas.") {
	    				throw new IOException("edición no válida"); 
	    			}
	    			
	    			ctrlEvento.aceptarEdicion(ed);	  
	    			JOptionPane.showMessageDialog(null, "Edición aceptada exitosamente!!.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
	    			recargarEventos();
	    			
	    		}catch(IOException ex){
	    		      JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	    		}
	    		
	    	}
	    });
	    GridBagConstraints gbc_btnAceptar = new GridBagConstraints();
	    gbc_btnAceptar.fill = GridBagConstraints.HORIZONTAL;
	    gbc_btnAceptar.insets = new Insets(0, 0, 5, 5);
	    gbc_btnAceptar.gridx = 2;
	    gbc_btnAceptar.gridy = 7;
	    getContentPane().add(btnAceptar, gbc_btnAceptar);
	    
	    
	 // --------------------- BTN RECHAZAR ----------------------------------------
	    JButton btnRechazar = new JButton("Rechazar");
	    btnRechazar.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		try {
	    			String ed = (String) comboBoxEdicionesDeEvento.getSelectedItem();
	    			if (ed == null || ed == "Seleccione una evento" || ed == "El evento no contiene ediciones ingresadas.") {
	    				throw new IOException("edición no válida"); 
	    			}
	    			
	    			ctrlEvento.rechazarEdicion(ed);	    
	    			JOptionPane.showMessageDialog(null, "Edición rechazada exitosamente!!.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
	    			recargarEventos();
	    		}catch(IOException ex){
	    		      JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	    		}
	    		
	    	}
	    });
	    
	    GridBagConstraints gbc_btnRechazar = new GridBagConstraints();
	    gbc_btnRechazar.fill = GridBagConstraints.HORIZONTAL;
	    gbc_btnRechazar.insets = new Insets(0, 0, 5, 5);
	    gbc_btnRechazar.gridx = 3;
	    gbc_btnRechazar.gridy = 7;
	    getContentPane().add(btnRechazar, gbc_btnRechazar);
	    
	    
	    // --------------------- BTN CANCELAR ----------------------------------------
	    GridBagConstraints gbc_btnCancelar = new GridBagConstraints();
	    gbc_btnCancelar.anchor = GridBagConstraints.EAST;
	    gbc_btnCancelar.insets = new Insets(0, 0, 5, 5);
	    gbc_btnCancelar.gridx = 4;
	    gbc_btnCancelar.gridy = 7;
	    getContentPane().add(btnCancelar, gbc_btnCancelar);

	    
	    
	    // limpiar formulario()
	    activarEventos(); // listeners
	    recargarEventos();

	  }

	  // --------------------- FUNCIONES AUXILIARES ----------------------------------------

	  private void limpiarFormulario() {
	    comboBoxTiposDeRegistro.removeAllItems();
	    textFieldOrganizador.setText("");
	    textFechaIni.setText("");
	    textFechaFin.setText("");
	    textField_Estado.setText("");
	  }

	  private void activarEventos() {
	    btnCancelar.addActionListener(e -> {
	      limpiarFormulario();
	      setVisible(false);
	    });
	    comboBoxEventos.addActionListener(e -> {
	      recargarEdiciones();

	    });

	    // Recarga eventos
	    comboBoxEventos.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
	      public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent e) {
	        /* recargarEventos(); */ } // Son nombres que vienen de la interfaz

	      public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent e) {
	      }

	      public void popupMenuCanceled(javax.swing.event.PopupMenuEvent e) {
	      }
	    });
	    // Recarga de ediciones
	    comboBoxEdicionesDeEvento.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
	      public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent e) {
	        recargarEdiciones();
	      }

	      public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent e) {
	      }

	      public void popupMenuCanceled(javax.swing.event.PopupMenuEvent e) {
	      }
	    });

	    // Cuando cambia la edición, cargo el detalle (organizador, tipos, registros, patrocinios)
	    comboBoxEdicionesDeEvento.addActionListener(e -> cargarDetalleEdicion());
	  }

	  public void recargarEventos() {
	    comboBoxEventos.removeAllItems();
	    List<String> ListaEventos = new ArrayList<>();
	    ListaEventos.add("Sin selección");
	    List<String> eventos = ctrlEvento.listarEventos();
	    Collections.sort(eventos);
	    ListaEventos.addAll(eventos);
	    for (String ev : ListaEventos) {
	      comboBoxEventos.addItem(ev);
	    }
	    comboBoxEventos.setSelectedItem("Sin selección");
	  }

	  private void recargarEdiciones() {
	    Object ev = comboBoxEventos.getSelectedItem();
	    comboBoxEdicionesDeEvento.removeAllItems();
	    if (ev == "Sin selección" || ev == null) {
	      limpiarFormulario();
	      comboBoxEdicionesDeEvento.addItem("Seleccione una evento");
	      comboBoxEdicionesDeEvento.setSelectedItem("Seleccione una evento");
	    } else {
	      comboBoxEdicionesDeEvento.removeAllItems();
	      limpiarFormulario();

	      List<String> eds = new ArrayList<>();
	      eds.addAll(ctrlEvento.listarEdicionesEventoIngresadas(ev.toString()));
	      Collections.sort(eds);
	      if (eds.isEmpty()) {
	        comboBoxEdicionesDeEvento.addItem("El evento no contiene ediciones ingresadas.");
	        comboBoxEdicionesDeEvento.setSelectedItem("El evento no contiene ediciones ingresadas.");
	      } else {
	        comboBoxEdicionesDeEvento.setModel(new DefaultComboBoxModel<>(eds.toArray(new String[0])));
	        cargarDetalleEdicion();
	      }

	    }

	  }

	  private void cargarDetalleEdicion() {
	    Object ev = comboBoxEventos.getSelectedItem();
	    Object ed = comboBoxEdicionesDeEvento.getSelectedItem();

	    limpiarFormulario();

	    if (ev == null || ev == "Sin selección")
	      return;
	    if (ed == null || ed == "Seleccione una evento" || ed == "El evento no contiene ediciones ingresadas.")
	      return;

	    try {

	      DTEdicion edicion = ctrlEvento.infoEdicion(ed.toString());

	      textFechaIni.setText(edicion.getFechaIni().toString());
	      textFechaFin.setText(edicion.getFechaFin().toString());

	      if (edicion != null) {
	    	  textFieldOrganizador.setText(edicion.getOrganizador());
	    	  textField_Estado.setText(edicion.getEstado().toString());
	      }

	      List<String> tipos = ctrlEvento.listarTiposDeRegistroDeEdicion(ed.toString());
	      Collections.sort(tipos);
	      if (tipos.isEmpty()) {
	        comboBoxTiposDeRegistro.addItem("La edicion no contiene tipos de registros");
	      } else {
	        comboBoxTiposDeRegistro.setModel(new DefaultComboBoxModel<>(tipos.toArray(new String[0])));
	      }


	    }
	    catch (Exception ex) {
	      JOptionPane.showMessageDialog(this, "Error al cargar datos: " + ex.getMessage(), "Error",
	          JOptionPane.ERROR_MESSAGE);
	    }
	  }

	  public void mostrarEdicion(DTEdicion ed) {

	    comboBoxEventos.setSelectedItem(ed.getNombreEvento());

	    comboBoxEdicionesDeEvento.setSelectedItem(ed.getNombreEdicion());
	    activarEventos();
	    cargarDetalleEdicion();
	    try {
	      recargarEventos();
	      recargarEdiciones();
	    }
	    catch (Exception ex) {
	      // Posible excepcion
	    }

	    comboBoxEventos.setSelectedItem(ed.getNombreEvento());

	    comboBoxEdicionesDeEvento.setSelectedItem(ed.getNombreEdicion());

	    textFieldOrganizador.setText("");

	    try {
	      String org = ctrlEvento.obtenerOrganizadorDeEdicion(ed.getNombreEdicion());
	      if (org != null)
	        textFieldOrganizador.setText(org);

	      List<String> tipos = ctrlEvento.listarTiposDeRegistroDeEdicion(ed.getNombreEdicion());
	      Collections.sort(tipos);
	      if (tipos.isEmpty()) {
	        comboBoxTiposDeRegistro.addItem("La edicion no contiene tipos de registros");
	      } else {
	        comboBoxTiposDeRegistro.setModel(new DefaultComboBoxModel<>(tipos.toArray(new String[0])));
	      }

	    }
	    catch (Exception ex) {
	      JOptionPane.showMessageDialog(this, "Error al cargar datos: " + ex.getMessage(), "Error",
	          JOptionPane.ERROR_MESSAGE);
	    }

	  }


}
