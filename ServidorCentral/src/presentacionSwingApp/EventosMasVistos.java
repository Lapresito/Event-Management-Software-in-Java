package presentacionSwingApp;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import logica.DTEvento;
import logica.IEventosController;

public class EventosMasVistos extends JInternalFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable tablaTop;
    private DefaultTableModel modeloTabla;
    private IEventosController controlador;

    public EventosMasVistos(IEventosController controlador) {
        super("Top 5 Eventos más visitados", true, true, true, true);
        this.controlador = controlador;

        setSize(600, 350);
        getContentPane().setLayout(new GridBagLayout());

        // --- Título ---
        GridBagConstraints gbcTitulo = new GridBagConstraints();
        gbcTitulo.gridx = 0;
        gbcTitulo.gridy = 0;
        gbcTitulo.insets = new Insets(10, 10, 10, 10);
        gbcTitulo.anchor = GridBagConstraints.CENTER;

        JLabel titulo = new JLabel("Top 5 Eventos Más Visitados");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        getContentPane().add(titulo, gbcTitulo);

        // --- Tabla ---
        String[] columnas = {"Puesto", "Nombre del Evento", "Visitas"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaTop = new JTable(modeloTabla);
        tablaTop.setFillsViewportHeight(true);
        tablaTop.setRowHeight(25);
        tablaTop.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tablaTop.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        // Renderer para resaltar el top 1
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {

                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                if (row == 0) { // Top 1
                    c.setFont(c.getFont().deriveFont(Font.BOLD));
                    c.setBackground(new Color(255, 223, 128)); // dorado claro
                } else {
                    c.setFont(c.getFont().deriveFont(Font.PLAIN));
                    c.setBackground(Color.WHITE);
                }

                if (isSelected) {
                    c.setBackground(new Color(180, 200, 255));
                }

                return c;
            }
        };

        // Aplicar renderer a todas las columnas
        for (int i = 0; i < tablaTop.getColumnCount(); i++) {
            tablaTop.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }

        JScrollPane scrollPane = new JScrollPane(tablaTop);
        scrollPane.setPreferredSize(new Dimension(550, 180));

        GridBagConstraints gbcTabla = new GridBagConstraints();
        gbcTabla.gridx = 0;
        gbcTabla.gridy = 1;
        gbcTabla.fill = GridBagConstraints.BOTH;
        gbcTabla.weightx = 1;
        gbcTabla.weighty = 1;
        gbcTabla.insets = new Insets(10, 20, 10, 20);
        getContentPane().add(scrollPane, gbcTabla);

        // --- Botón Actualizar ---
        JButton btnActualizar = new JButton("Actualizar");
        btnActualizar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnActualizar.setBackground(new Color(60, 120, 200));
        btnActualizar.setForeground(Color.WHITE);
        btnActualizar.setFocusPainted(false);

        btnActualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                recargarEventos();
            }
        });

        GridBagConstraints gbcBoton = new GridBagConstraints();
        gbcBoton.gridx = 0;
        gbcBoton.gridy = 2;
        gbcBoton.insets = new Insets(5, 0, 15, 0);
        gbcBoton.anchor = GridBagConstraints.CENTER;
        getContentPane().add(btnActualizar, gbcBoton);

        recargarEventos();
    }

    /** Recarga los eventos desde el controlador y actualiza la tabla */
    private void recargarEventos() {
        try {
            modeloTabla.setRowCount(0);
            List<DTEvento> topEventos = controlador.conseguirEventosMasVisitados();
            int puesto = 1;
            for (DTEvento e : topEventos) {
                modeloTabla.addRow(new Object[]{
                        puesto,
                        e.getNombre(),
                        e.getVisitas()
                });
                puesto++;
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al actualizar los eventos:\n" + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
