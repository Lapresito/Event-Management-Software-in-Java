package presentacionSwingApp;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import logica.DTAsistente;
import logica.DTAsistente;
import logica.IEventosController;
import logica.IUsuariosController;

public class RegistroAEdicionDeEvento extends JInternalFrame {

  private IEventosController IEvController;
  private IUsuariosController IUsController;

  private JComboBox<String> comboBoxEvento;
  private JComboBox<String> comboBoxEdicion;
  private JComboBox<String> comboBoxAsistente;
  private JComboBox<String> comboBoxTiposDeRegistro;

  private static final long serialVersionUID = 1L;

  public boolean validarCampos(String nombreEvento, String nombreEdicion, String tipoRegistro,
      String asistente) {

    if (nombreEvento == null || nombreEvento.isBlank()) {
      JOptionPane.showMessageDialog(null, "Ingrese el nombre del Evento.", "Error",
          JOptionPane.ERROR_MESSAGE);
      return false;
    }

    if (nombreEdicion == null || nombreEdicion.isBlank()) {
      JOptionPane.showMessageDialog(null, "Seleccione una Edición.", "Error",
          JOptionPane.ERROR_MESSAGE);
      return false;
    }

    if (tipoRegistro == null || tipoRegistro.isBlank()) {
      JOptionPane.showMessageDialog(null, "Seleccione un Tipo de Registro.", "Error",
          JOptionPane.ERROR_MESSAGE);
      return false;
    }

    if (asistente == null || asistente.isBlank()) {
      JOptionPane.showMessageDialog(null, "Seleccione un Asistente.", "Error",
          JOptionPane.ERROR_MESSAGE);
      return false;
    }

    return true;
  }

  public RegistroAEdicionDeEvento(IEventosController IEv, IUsuariosController IUs) {

    IEvController = IEv;
    IUsController = IUs;
    setTitle("Registro a Edicion de Evento");
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

    // --------------------- LISTA EVENTO ----------------------------------------

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

    // load editions

    comboBoxEvento.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        String eventoSeleccionado = (String) comboBoxEvento.getSelectedItem();
        if (eventoSeleccionado != null) {
          comboBoxEdicion.removeAllItems();
          List<String> ediciones = IEvController.listarEdicionesEvento(eventoSeleccionado);
          for (String nombreEdicion : ediciones) {
            comboBoxEdicion.addItem(nombreEdicion);
          }
          comboBoxEdicion.setSelectedIndex(-1);
        }
      }
    });
    // --------------------- LISTA EDICION ----------------------------------------

    JLabel lblVerEdicion = new JLabel("Seleccione Edicion:");
    GridBagConstraints gbc_lblVerEdicion = new GridBagConstraints();
    gbc_lblVerEdicion.anchor = GridBagConstraints.EAST;
    gbc_lblVerEdicion.insets = new Insets(0, 0, 5, 5);
    gbc_lblVerEdicion.gridx = 0;
    gbc_lblVerEdicion.gridy = 2;
    getContentPane().add(lblVerEdicion, gbc_lblVerEdicion);

    comboBoxEdicion = new JComboBox<>();
    GridBagConstraints gbc_comboBoxEdicion = new GridBagConstraints();
    gbc_comboBoxEdicion.gridwidth = 2;
    gbc_comboBoxEdicion.insets = new Insets(0, 0, 5, 5);
    gbc_comboBoxEdicion.fill = GridBagConstraints.HORIZONTAL;
    gbc_comboBoxEdicion.gridx = 1;
    gbc_comboBoxEdicion.gridy = 2;
    getContentPane().add(comboBoxEdicion, gbc_comboBoxEdicion);
    comboBoxEvento.setSelectedIndex(-1);

    // --------------- Load TiposDeRegistro for selected Edition --------------- //

    comboBoxEdicion.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        String edicionSeleccionada = (String) comboBoxEdicion.getSelectedItem();
        cargarTPs(edicionSeleccionada);
      }
    });

    // --------------------- LISTA TIPO REGISTRO
    // ----------------------------------------

    JLabel lblVerTRegistro = new JLabel("Seleccione Tipo de Registro:");
    GridBagConstraints gbc_lblVerTRegistro = new GridBagConstraints();
    gbc_lblVerTRegistro.anchor = GridBagConstraints.EAST;
    gbc_lblVerTRegistro.insets = new Insets(0, 0, 5, 5);
    gbc_lblVerTRegistro.gridx = 0;
    gbc_lblVerTRegistro.gridy = 3;
    getContentPane().add(lblVerTRegistro, gbc_lblVerTRegistro);

    comboBoxTiposDeRegistro = new JComboBox<>();
    GridBagConstraints gbc_comboBoxTRegistro = new GridBagConstraints();
    gbc_comboBoxTRegistro.gridwidth = 2;
    gbc_comboBoxTRegistro.insets = new Insets(0, 0, 5, 5);
    gbc_comboBoxTRegistro.fill = GridBagConstraints.HORIZONTAL;
    gbc_comboBoxTRegistro.gridx = 1;
    gbc_comboBoxTRegistro.gridy = 3;
    getContentPane().add(comboBoxTiposDeRegistro, gbc_comboBoxTRegistro);

    // --------------------- LISTA ASISTENTES
    // ----------------------------------------

    JLabel lblVerAsistente = new JLabel("Seleccione Asistente:");
    GridBagConstraints gbc_lblVerAsistente = new GridBagConstraints();
    gbc_lblVerAsistente.anchor = GridBagConstraints.EAST;
    gbc_lblVerAsistente.insets = new Insets(0, 0, 5, 5);
    gbc_lblVerAsistente.gridx = 0;
    gbc_lblVerAsistente.gridy = 4;
    getContentPane().add(lblVerAsistente, gbc_lblVerAsistente);

    comboBoxAsistente = new JComboBox<>();
    GridBagConstraints gbc_comboBoxAsistente = new GridBagConstraints();
    gbc_comboBoxAsistente.gridwidth = 2;
    gbc_comboBoxAsistente.insets = new Insets(0, 0, 5, 5);
    gbc_comboBoxAsistente.fill = GridBagConstraints.HORIZONTAL;
    gbc_comboBoxAsistente.gridx = 1;
    gbc_comboBoxAsistente.gridy = 4;
    getContentPane().add(comboBoxAsistente, gbc_comboBoxAsistente);

    // -----------------= botones =--------------

    // ----- Boton Registrar> -----------
    JButton btnRegistrar = new JButton("Registrar");
    GridBagConstraints gbc_btnRegistrar = new GridBagConstraints();
    gbc_btnRegistrar.fill = GridBagConstraints.HORIZONTAL;
    gbc_btnRegistrar.insets = new Insets(0, 0, 5, 5);
    gbc_btnRegistrar.gridx = 1;
    gbc_btnRegistrar.gridy = 5;
    getContentPane().add(btnRegistrar, gbc_btnRegistrar);

    // ---- logica boton
    btnRegistrar.addActionListener(new ActionListener() {

      public void actionPerformed(ActionEvent e) {

        String nombreEvento = (String) comboBoxTiposDeRegistro.getSelectedItem();
        String nombreEdicion = (String) comboBoxEdicion.getSelectedItem();
        String tipoRegistro = (String) comboBoxTiposDeRegistro.getSelectedItem();
        String asistente = (String) comboBoxAsistente.getSelectedItem();

        try {
          if (!validarCampos(nombreEvento, nombreEdicion, tipoRegistro, asistente)) {
            return;
          }

          IUs.altaRegistro(asistente, nombreEdicion, tipoRegistro, null);
          JOptionPane.showMessageDialog(null, "Registro agregado con éxito.");

        }
        catch (IOException err) {
          JOptionPane.showMessageDialog(RegistroAEdicionDeEvento.this, err.getMessage(), "Error",
              JOptionPane.ERROR_MESSAGE);
        }

      }

    });

    JButton btnCancelar = new JButton("Cancelar");
    btnCancelar.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        setVisible(false);
      }
    });
    GridBagConstraints gbc_btnCancelar = new GridBagConstraints();
    gbc_btnCancelar.fill = GridBagConstraints.HORIZONTAL;
    gbc_btnCancelar.insets = new Insets(0, 0, 5, 5);
    gbc_btnCancelar.gridx = 2;
    gbc_btnCancelar.gridy = 5;
    getContentPane().add(btnCancelar, gbc_btnCancelar);

  }

  private void cargarTPs(String nombreEdicion) {
    Vector<String> tiposDeRegistro = new Vector<>(
        IEvController.listarTiposDeRegistroDeEdicion(nombreEdicion));
    DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(tiposDeRegistro);
    comboBoxTiposDeRegistro.setModel(model);
    SwingUtilities.invokeLater(() -> comboBoxTiposDeRegistro.setSelectedIndex(-1));
  }

  // --------------- Load data --------------- //

  public void cargarEventosYAsistentes() {
    Vector<String> eventos = new Vector<>(IEvController.listarEventos());
    DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(eventos);
    comboBoxEvento.setModel(model);
    SwingUtilities.invokeLater(() -> comboBoxEvento.setSelectedIndex(-1));
    Vector<DTAsistente> asistentes = new Vector<>(IUsController.listarAsistentes());
    
    Vector<String> asistentesS = new Vector<>();
    asistentes.forEach(as ->{
    	asistentesS.add(as.getNickname());
    });
    DefaultComboBoxModel<String> modelAsist = new DefaultComboBoxModel<>(asistentesS);
    comboBoxAsistente.setModel(modelAsist);
    SwingUtilities.invokeLater(() -> comboBoxAsistente.setSelectedIndex(-1));
  }

}
