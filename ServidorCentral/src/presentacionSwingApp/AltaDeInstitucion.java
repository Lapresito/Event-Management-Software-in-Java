package presentacionSwingApp;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import logica.IInstitucionesController;

public class AltaDeInstitucion extends JInternalFrame{
	
	private IInstitucionesController IInController;
	
	private static final long serialVersionUID = 1L;
	private JTextField textFieldNombre;
	private JTextField textFieldDescripcion;
	private JTextField textFieldSitioWeb;
	private JLabel lblIngreseNombre;
	private JLabel lblIngreseDescripcion;
	private JLabel lblIngreseSitioWeb;
	private JButton btnAceptar;
	private JButton btnCancelar;
	

	
	public boolean validarCampos(String nombreInstitucion, String descripcion, String sitioWeb) {

		if (nombreInstitucion == null || nombreInstitucion.isBlank()) {
			JOptionPane.showMessageDialog(null, "Ingrese el nombre de la Institucion.", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		if (descripcion == null || descripcion.isBlank()) {
			JOptionPane.showMessageDialog(null, "Ingrese la descripcion.", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		if (sitioWeb == null || sitioWeb.isBlank()) {
			JOptionPane.showMessageDialog(null, "Ingrese el sitio web.", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}


		return true;
	}
	
	
	public AltaDeInstitucion(IInstitucionesController IIn) {
		
		IInController = IIn;
		
		setTitle("Alta de Institucion");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setResizable(true);
		setIconifiable(true);
		setMaximizable(true);
		setClosable(true);
		setBounds(100, 100, 455, 276);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{41, 65, 140, 140, 58, 0};
		gridBagLayout.rowHeights = new int[]{-25, 35, 35, 35, 40, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		
		lblIngreseNombre = new JLabel("Nombre:");
		GridBagConstraints gbc_lblIngreseNombre = new GridBagConstraints();
		gbc_lblIngreseNombre.insets = new Insets(0, 0, 5, 5);
		gbc_lblIngreseNombre.anchor = GridBagConstraints.EAST;
		gbc_lblIngreseNombre.gridx = 1;
		gbc_lblIngreseNombre.gridy = 1;
		getContentPane().add(lblIngreseNombre, gbc_lblIngreseNombre);
		
		textFieldNombre = new JTextField();
		textFieldNombre.setColumns(10);
		GridBagConstraints gbc_textFieldNombre = new GridBagConstraints();
		gbc_textFieldNombre.gridwidth = 2;
		gbc_textFieldNombre.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldNombre.fill = GridBagConstraints.BOTH;
		gbc_textFieldNombre.gridx = 2;
		gbc_textFieldNombre.gridy = 1;
		getContentPane().add(textFieldNombre, gbc_textFieldNombre);
		
		lblIngreseDescripcion = new JLabel("Descripcion:");
		lblIngreseNombre.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_lblIngreseDescripcion = new GridBagConstraints();
		gbc_lblIngreseDescripcion.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblIngreseDescripcion.insets = new Insets(0, 0, 5, 5);
		gbc_lblIngreseDescripcion.gridx = 1;
		gbc_lblIngreseDescripcion.gridy = 2;
		getContentPane().add(lblIngreseDescripcion, gbc_lblIngreseDescripcion);
		
		textFieldDescripcion = new JTextField();
		textFieldDescripcion.setColumns(10);
		GridBagConstraints gbc_textFieldDescripcion = new GridBagConstraints();
		gbc_textFieldDescripcion.gridwidth = 2;
		gbc_textFieldDescripcion.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldDescripcion.fill = GridBagConstraints.BOTH;
		gbc_textFieldDescripcion.gridx = 2;
		gbc_textFieldDescripcion.gridy = 2;
		getContentPane().add(textFieldDescripcion, gbc_textFieldDescripcion);
		
		lblIngreseSitioWeb = new JLabel("Sitio Web:");
		GridBagConstraints gbc_lblIngreseSitioWeb = new GridBagConstraints();
		gbc_lblIngreseSitioWeb.insets = new Insets(0, 0, 5, 5);
		gbc_lblIngreseSitioWeb.anchor = GridBagConstraints.EAST;
		gbc_lblIngreseSitioWeb.gridx = 1;
		gbc_lblIngreseSitioWeb.gridy = 3;
		getContentPane().add(lblIngreseSitioWeb, gbc_lblIngreseSitioWeb);
		
		textFieldSitioWeb = new JTextField();
		GridBagConstraints gbc_textFieldSitioWeb = new GridBagConstraints();
		gbc_textFieldSitioWeb.gridwidth = 2;
		gbc_textFieldSitioWeb.fill = GridBagConstraints.BOTH;
		gbc_textFieldSitioWeb.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldSitioWeb.gridx = 2;
		gbc_textFieldSitioWeb.gridy = 3;
		getContentPane().add(textFieldSitioWeb, gbc_textFieldSitioWeb);
		textFieldSitioWeb.setColumns(10);
		
		btnAceptar = new JButton("Aceptar");
		GridBagConstraints gbc_btnAceptar = new GridBagConstraints();
		gbc_btnAceptar.insets = new Insets(0, 0, 0, 5);
		gbc_btnAceptar.gridx = 2;
		gbc_btnAceptar.gridy = 4;
		getContentPane().add(btnAceptar, gbc_btnAceptar);
		
		
		
		btnAceptar.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				String nombre = textFieldNombre.getText();
				String descripcion = textFieldDescripcion.getText();
				String sitioWeb = textFieldSitioWeb.getText();
				if (!validarCampos(nombre, descripcion, sitioWeb)) {
					return;
				}
				try {
					IInController.altaInstitucion(nombre, descripcion, sitioWeb,"");
					JOptionPane.showMessageDialog(null, "Institucion agregada con éxito.");
				} catch (IOException err) {
					JOptionPane.showMessageDialog(AltaDeInstitucion.this, err.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
				}
				limpiarFormulario();

				

			}

		});
		
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
		gbc_btnCancelar.gridy = 4;
		getContentPane().add(btnCancelar, gbc_btnCancelar);

	}

	private void limpiarFormulario() {
        textFieldNombre.setText("");
        textFieldDescripcion.setText("");
        textFieldSitioWeb.setText("");
	}

}
