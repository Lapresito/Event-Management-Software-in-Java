package presentacionSwingApp;

import java.awt.EventQueue;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;
import java.util.Map;
import java.util.HashMap;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import logica.IUsuariosController;
import logica.DTAsistente;
import logica.DTAsistente;
import logica.DTRegistro;
import logica.Fabrica;

public class ConsultaDeRegistro extends JInternalFrame {

  private static final long serialVersionUID = 1L;
  private JTextField textFieldNombreEdicion;
  private JTextField textFieldNombreEvento;
  private JTextField textFieldFechaAlta;
  private JTextField textFieldCosto;
  private Map<String, String> resYedis = new HashMap<>();
  private String key;
  private boolean actualizandoRegistro = false;

  JComboBox<String> comboBoxRegistro;
  JComboBox<String> comboBoxAsistente;

  private IUsuariosController IUsController;

  public boolean validarCampos(String usuario, String registro) {
    if (usuario == null || usuario.isBlank()) {
      JOptionPane.showMessageDialog(null, "Seleccione un usuario.", "Error",
          JOptionPane.ERROR_MESSAGE);
      return false;
    }
    if (registro == null || registro.isBlank()) {
      JOptionPane.showMessageDialog(null, "Seleccione un registro.", "Error",
          JOptionPane.ERROR_MESSAGE);
      return false;
    }
    return true;
  }

  public ConsultaDeRegistro(IUsuariosController IUs) {

    IUsController = IUs;

    setTitle("Consulta de Registro");
    setResizable(true);
    setIconifiable(true);
    setMaximizable(true);
    setClosable(true);
    setBounds(100, 100, 450, 300);

    GridBagLayout gridBagLayout = new GridBagLayout();
    gridBagLayout.columnWidths = new int[] { 51, 100, 98, 35, 0 };
    gridBagLayout.rowHeights = new int[] { 20, 0, 0, 30, 30, 30, 30, 35, 59, 0 };
    gridBagLayout.columnWeights = new double[] { 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE };
    gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0,
        Double.MIN_VALUE };
    getContentPane().setLayout(gridBagLayout);

    // --------------------- Lista Asistentes ----------------------------------------
    JLabel lblSelecUsuario = new JLabel("Seleccione Usuario:");
    GridBagConstraints gbc_lblSelecUsuario = new GridBagConstraints();
    gbc_lblSelecUsuario.anchor = GridBagConstraints.EAST;
    gbc_lblSelecUsuario.insets = new Insets(0, 0, 5, 5);
    gbc_lblSelecUsuario.gridx = 0;
    gbc_lblSelecUsuario.gridy = 1;
    getContentPane().add(lblSelecUsuario, gbc_lblSelecUsuario);

    comboBoxAsistente = new JComboBox<>();
    GridBagConstraints gbc_comboBoxAsistente = new GridBagConstraints();
    gbc_comboBoxAsistente.gridwidth = 2;
    gbc_comboBoxAsistente.insets = new Insets(0, 0, 5, 5);
    gbc_comboBoxAsistente.fill = GridBagConstraints.HORIZONTAL;
    gbc_comboBoxAsistente.gridx = 1;
    gbc_comboBoxAsistente.gridy = 1;
    getContentPane().add(comboBoxAsistente, gbc_comboBoxAsistente);

    // Listener del comboBoxAsistente
    comboBoxAsistente.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        String asistenteSeleccionado = (String) comboBoxAsistente.getSelectedItem();
        if (asistenteSeleccionado != null) {
          actualizandoRegistro = true; // bloqueo
          comboBoxRegistro.removeAllItems();
          resYedis = IUsController.listarRegistrosYEdicionAsistente(asistenteSeleccionado);
          for (String valor : resYedis.values()) {
            comboBoxRegistro.addItem(valor);
          }
          comboBoxRegistro.setSelectedIndex(-1);
          actualizandoRegistro = false; // desbloqueo
        }
      }
    });

    // --------------------- Lista Registros ----------------------------------------
    JLabel lblSelecRegistro = new JLabel("Seleccione Registro de Usuario:");
    GridBagConstraints gbc_lblSelecRegistro = new GridBagConstraints();
    gbc_lblSelecRegistro.anchor = GridBagConstraints.EAST;
    gbc_lblSelecRegistro.insets = new Insets(0, 0, 5, 5);
    gbc_lblSelecRegistro.gridx = 0;
    gbc_lblSelecRegistro.gridy = 2;
    getContentPane().add(lblSelecRegistro, gbc_lblSelecRegistro);

    comboBoxRegistro = new JComboBox<>();
    GridBagConstraints gbc_comboBoxRegistro = new GridBagConstraints();
    gbc_comboBoxRegistro.gridwidth = 2;
    gbc_comboBoxRegistro.insets = new Insets(0, 0, 5, 5);
    gbc_comboBoxRegistro.fill = GridBagConstraints.HORIZONTAL;
    gbc_comboBoxRegistro.gridx = 1;
    gbc_comboBoxRegistro.gridy = 2;
    getContentPane().add(comboBoxRegistro, gbc_comboBoxRegistro);

    comboBoxRegistro.addActionListener(e -> {
      if (actualizandoRegistro)
        return; // ignorar eventos mientras se actualiza
      String valorSeleccionado = (String) comboBoxRegistro.getSelectedItem();
      if (valorSeleccionado != null) {
        key = null;
        for (Map.Entry<String, String> entry : resYedis.entrySet()) {
          if (entry.getValue().equals(valorSeleccionado)) {
            key = entry.getKey();
          }
        }
        if (key != null) {
          mostrarRegistro((String) comboBoxAsistente.getSelectedItem(), key);
        }
      }
    });

    // --------------------- Campos de Info ----------------------------------------
    JLabel lblInfoNombreEvento = new JLabel("Nombre Evento:");
    GridBagConstraints gbc_lblInfoNombreEvento = new GridBagConstraints();
    gbc_lblInfoNombreEvento.insets = new Insets(0, 0, 5, 5);
    gbc_lblInfoNombreEvento.anchor = GridBagConstraints.EAST;
    gbc_lblInfoNombreEvento.gridx = 0;
    gbc_lblInfoNombreEvento.gridy = 3;
    getContentPane().add(lblInfoNombreEvento, gbc_lblInfoNombreEvento);

    textFieldNombreEvento = new JTextField();
    textFieldNombreEvento.setEditable(false);
    textFieldNombreEvento.setColumns(10);
    GridBagConstraints gbc_textFieldNombreEvento = new GridBagConstraints();
    gbc_textFieldNombreEvento.gridwidth = 2;
    gbc_textFieldNombreEvento.insets = new Insets(0, 0, 5, 5);
    gbc_textFieldNombreEvento.fill = GridBagConstraints.BOTH;
    gbc_textFieldNombreEvento.gridx = 1;
    gbc_textFieldNombreEvento.gridy = 3;
    getContentPane().add(textFieldNombreEvento, gbc_textFieldNombreEvento);

    JLabel lblInfoNombreEdicion = new JLabel("Nombre Edicion:");
    GridBagConstraints gbc_lblInfoNombreEdicion = new GridBagConstraints();
    gbc_lblInfoNombreEdicion.insets = new Insets(0, 0, 5, 5);
    gbc_lblInfoNombreEdicion.anchor = GridBagConstraints.EAST;
    gbc_lblInfoNombreEdicion.gridx = 0;
    gbc_lblInfoNombreEdicion.gridy = 4;
    getContentPane().add(lblInfoNombreEdicion, gbc_lblInfoNombreEdicion);

    textFieldNombreEdicion = new JTextField();
    textFieldNombreEdicion.setEditable(false);
    textFieldNombreEdicion.setColumns(10);
    GridBagConstraints gbc_textFieldNombreEdicion = new GridBagConstraints();
    gbc_textFieldNombreEdicion.gridwidth = 2;
    gbc_textFieldNombreEdicion.insets = new Insets(0, 0, 5, 5);
    gbc_textFieldNombreEdicion.fill = GridBagConstraints.BOTH;
    gbc_textFieldNombreEdicion.gridx = 1;
    gbc_textFieldNombreEdicion.gridy = 4;
    getContentPane().add(textFieldNombreEdicion, gbc_textFieldNombreEdicion);

    JLabel lblInfoFechaAlta = new JLabel("Fecha Alta:");
    GridBagConstraints gbc_lblInfoFechaAlta = new GridBagConstraints();
    gbc_lblInfoFechaAlta.insets = new Insets(0, 0, 5, 5);
    gbc_lblInfoFechaAlta.anchor = GridBagConstraints.EAST;
    gbc_lblInfoFechaAlta.gridx = 0;
    gbc_lblInfoFechaAlta.gridy = 5;
    getContentPane().add(lblInfoFechaAlta, gbc_lblInfoFechaAlta);

    textFieldFechaAlta = new JTextField();
    textFieldFechaAlta.setEditable(false);
    textFieldFechaAlta.setColumns(10);
    GridBagConstraints gbc_textFieldFechaAlta = new GridBagConstraints();
    gbc_textFieldFechaAlta.gridwidth = 2;
    gbc_textFieldFechaAlta.insets = new Insets(0, 0, 5, 5);
    gbc_textFieldFechaAlta.fill = GridBagConstraints.BOTH;
    gbc_textFieldFechaAlta.gridx = 1;
    gbc_textFieldFechaAlta.gridy = 5;
    getContentPane().add(textFieldFechaAlta, gbc_textFieldFechaAlta);

    JLabel lblInfoCosto = new JLabel("Costo:");
    GridBagConstraints gbc_lblInfoCosto = new GridBagConstraints();
    gbc_lblInfoCosto.insets = new Insets(0, 0, 5, 5);
    gbc_lblInfoCosto.anchor = GridBagConstraints.EAST;
    gbc_lblInfoCosto.gridx = 0;
    gbc_lblInfoCosto.gridy = 6;
    getContentPane().add(lblInfoCosto, gbc_lblInfoCosto);

    textFieldCosto = new JTextField();
    textFieldCosto.setEditable(false);
    textFieldCosto.setColumns(10);
    GridBagConstraints gbc_textFieldCosto = new GridBagConstraints();
    gbc_textFieldCosto.gridwidth = 2;
    gbc_textFieldCosto.insets = new Insets(0, 0, 5, 5);
    gbc_textFieldCosto.fill = GridBagConstraints.BOTH;
    gbc_textFieldCosto.gridx = 1;
    gbc_textFieldCosto.gridy = 6;
    getContentPane().add(textFieldCosto, gbc_textFieldCosto);

    // --------------------- Botón Cerrar ----------------------------------------
    JButton btnCancelar = new JButton("Cerrar");
    btnCancelar.addActionListener(e -> setVisible(false));
    GridBagConstraints gbc_btnCancelar = new GridBagConstraints();
    gbc_btnCancelar.fill = GridBagConstraints.HORIZONTAL;
    gbc_btnCancelar.insets = new Insets(0, 0, 5, 5);
    gbc_btnCancelar.gridx = 1;
    gbc_btnCancelar.gridy = 7;
    getContentPane().add(btnCancelar, gbc_btnCancelar);

    btnCancelar.addActionListener(e -> {
      // Ocultar ventana
      setVisible(false);
      resetear();
    });

  }

  private void resetear() {
    // Variables internas
    key = null;
    resYedis.clear();
    actualizandoRegistro = false;

    // Campos de texto
    textFieldNombreEdicion.setText("");
    textFieldNombreEvento.setText("");
    textFieldFechaAlta.setText("");
    textFieldCosto.setText("");

    // ComboBoxes
    comboBoxRegistro.removeAllItems();
    comboBoxRegistro.setEnabled(true);

    comboBoxAsistente.removeAllItems();
    comboBoxAsistente.setEnabled(true);
    List<DTAsistente> asistentes = IUsController.listarAsistentes();
    for (DTAsistente a : asistentes) {
      comboBoxAsistente.addItem(a.getNickname());
    }
    comboBoxAsistente.setSelectedIndex(-1);
  }

  public void cargarAsistentes() {
    Vector<DTAsistente> asistentes = new Vector<>(IUsController.listarAsistentes());
    Vector<String> asistentesS = new Vector<>();
    asistentes.forEach(as ->{
    	asistentesS.add(as.getNickname());
    });
    
    DefaultComboBoxModel<String> modelAsist = new DefaultComboBoxModel<>(asistentesS);
    comboBoxAsistente.setModel(modelAsist);
    SwingUtilities.invokeLater(() -> comboBoxAsistente.setSelectedIndex(-1));
  }

  private void cargarRegistros(String asistente) {
    Vector<String> registros = new Vector<>(IUsController.listarRegistrosAsistente(asistente));
    DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(registros);
    comboBoxRegistro.setModel(model);
    SwingUtilities.invokeLater(() -> comboBoxRegistro.setSelectedIndex(-1));
  }

  public void mostrarRegistro(String nickAsist, String nombreEdicion) {

    textFieldNombreEvento.setText("");
    textFieldNombreEdicion.setText("");
    textFieldFechaAlta.setText("");
    textFieldCosto.setText("");

    comboBoxAsistente.setSelectedItem(nickAsist);

    comboBoxRegistro.setSelectedItem(nombreEdicion);

    DTRegistro info = IUsController.infoRegistroDeAsistente(nickAsist, nombreEdicion);
    textFieldNombreEvento.setText(info.getNombreEvento());
    textFieldNombreEdicion.setText(info.getNombreEdicion());
    textFieldFechaAlta.setText(info.getFechaAlta().toString());
    textFieldCosto.setText(String.valueOf(info.getCosto()));

  }

}
