package presentacionSwingApp;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;
import logica.IEventosController;

public class AltaCategoria extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JTextField textFieldNombre;
	
	private IEventosController IEvController;
	
	public boolean validarCampos(String nombreCategoria) {

		if (nombreCategoria == null || nombreCategoria.isBlank()) {
			JOptionPane.showMessageDialog(null, "Ingrese el nombre de la Institucion.", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		return true;
	}

	/**
	 * Create the frame.
	 */
	public AltaCategoria(IEventosController IEv) {
		
		IEvController = IEv;
		
		setTitle("Nombre de Categoria");
		setResizable(true);
		setIconifiable(true);
		setMaximizable(true);
		setClosable(true);
		setBounds(100, 100, 450, 300);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{51, 100, 98, 35, 0};
		gridBagLayout.rowHeights = new int[]{20, 0, 0, 30, 30, 30, 30, 35, 59, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		
		
		// ---------------------  CAMPO NOMBRE ----------------------------------------
		JLabel lblInfoNombre = new JLabel("Nombre Categoria:");
		GridBagConstraints gbc_lblInfoNombre = new GridBagConstraints();
		gbc_lblInfoNombre.insets = new Insets(0, 0, 5, 5);
		gbc_lblInfoNombre.anchor = GridBagConstraints.EAST;
		gbc_lblInfoNombre.gridx = 0;
		gbc_lblInfoNombre.gridy = 1;
		getContentPane().add(lblInfoNombre, gbc_lblInfoNombre);
		
		textFieldNombre = new JTextField();
		textFieldNombre.setColumns(10);
		GridBagConstraints gbc_textFieldNombre = new GridBagConstraints();
		gbc_textFieldNombre.gridwidth = 2;
		gbc_textFieldNombre.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldNombre.fill = GridBagConstraints.BOTH;
		gbc_textFieldNombre.gridx = 2;
		gbc_textFieldNombre.gridy = 1;
		getContentPane().add(textFieldNombre, gbc_textFieldNombre);

		
		
		// -----------------= botones =--------------
		
		JButton btnAceptar = new JButton("Aceptar");
		GridBagConstraints gbc_btnAceptar = new GridBagConstraints();
		gbc_btnAceptar.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnAceptar.insets = new Insets(0, 0, 5, 5);
		gbc_btnAceptar.gridx = 1;
		gbc_btnAceptar.gridy = 2;
		getContentPane().add(btnAceptar, gbc_btnAceptar);
		
		btnAceptar.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				String nombre = textFieldNombre.getText();
				if (!validarCampos(nombre)) {
					return;
				}
				IEvController.altaCategoria(nombre);
				setVisible(false);
				limpiarFormulario();

				JOptionPane.showMessageDialog(null, "Categoria agregada con éxito.");

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
		gbc_btnCancelar.gridy = 2;
		getContentPane().add(btnCancelar, gbc_btnCancelar);

	}
	private void limpiarFormulario() {
        textFieldNombre.setText("");

	}

}