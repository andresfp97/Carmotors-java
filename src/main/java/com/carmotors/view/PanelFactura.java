package com.carmotors.view;

import com.carmotors.controller.FacturaController;
import com.carmotors.model.Factura;
import com.carmotors.model.Trabajo;
import com.carmotors.modelDAO.TrabajoDAO;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class PanelFactura extends JPanel {
    private JComboBox<Trabajo> cbTrabajos;
    private JButton btnGenerar, btnBuscar, btnEliminar, btnLimpiar;
    private JTextArea txtDetalleFactura;
    private FacturaController facturaController;
    private TrabajoDAO trabajoDAO;
    private ActionListener generarFacturaListener;
    private JTable tablaFacturas;
    private DefaultTableModel modeloTablaFacturas;

    public PanelFactura() {
        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 240));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        initComponents();
    }

    private void initComponents() {
        JTabbedPane tabbedPane = new JTabbedPane();

        // ======================= TAB 1: FORMULARIO FACTURA =======================
        JPanel tabFormulario = new JPanel();
        tabFormulario.setLayout(new BorderLayout());
        tabFormulario.setBackground(new Color(240, 240, 240));

        // Panel del formulario
        JPanel panelFormulario = new JPanel();
        panelFormulario.setLayout(new BoxLayout(panelFormulario, BoxLayout.Y_AXIS));
        panelFormulario.setBackground(Color.WHITE);
        panelFormulario.setBorder(createStyledBorder("Generar Factura"));

        JPanel formContent = new JPanel(new GridBagLayout());
        formContent.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblTrabajo = new JLabel("Trabajo:");
        lblTrabajo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        cbTrabajos = new JComboBox<>();
        cbTrabajos.setPreferredSize(new Dimension(300, 30));
        cbTrabajos.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        cbTrabajos.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value,
                                                          int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Trabajo) {
                    Trabajo t = (Trabajo) value;
                    setText(String.format("Trabajo #%d - %s (%s)",
                            t.getIdTrabajo(),
                            t.getServicio().getDescripcion(),
                            t.getVehiculo().getPlaca()));
                }
                return this;
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        formContent.add(lblTrabajo, gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        formContent.add(cbTrabajos, gbc);

        panelFormulario.add(formContent);

        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.WHITE);
        btnGenerar = createStyledButton("Generar Factura", new Color(76, 175, 80));
        buttonPanel.add(btnGenerar);
        panelFormulario.add(Box.createRigidArea(new Dimension(0, 10)));
        panelFormulario.add(buttonPanel);

        tabFormulario.add(panelFormulario, BorderLayout.NORTH);

        // ======================= TAB 2: LISTADO DE FACTURAS =======================
        JPanel panelTabla = new JPanel(new BorderLayout());
        panelTabla.setBorder(createStyledBorder("Registros de Facturas"));
        panelTabla.setBackground(Color.WHITE);

        // Panel de búsqueda
        JPanel buscarPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buscarPanel.setBackground(Color.WHITE);
        buscarPanel.add(new JLabel("Buscar por Número de Factura:"));
        JTextField txtBuscar = createStyledTextField(10);
        buscarPanel.add(txtBuscar);
        btnBuscar = createStyledButton("Buscar", new Color(33, 150, 243));
        btnEliminar = createStyledButton("Eliminar", new Color(244, 67, 54));
        btnLimpiar = createStyledButton("Limpiar búsqueda", new Color(255, 193, 7));
        buscarPanel.add(btnBuscar);
        buscarPanel.add(btnEliminar);
        buscarPanel.add(btnLimpiar);

        panelTabla.add(buscarPanel, BorderLayout.NORTH);

        // Tabla de facturas
        modeloTablaFacturas = new DefaultTableModel(
                new Object[]{"Número", "Fecha", "Cliente", "Subtotal", "Impuestos", "Total", "CUFE"}, 0);
        tablaFacturas = new JTable(modeloTablaFacturas);
        panelTabla.add(new JScrollPane(tablaFacturas), BorderLayout.CENTER);

        tabFormulario.add(panelTabla, BorderLayout.CENTER);

        // Área de texto para mostrar la factura detallada (inicialmente oculta en la primera pestaña)
        txtDetalleFactura = new JTextArea();
        txtDetalleFactura.setEditable(false);
        txtDetalleFactura.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPaneDetalle = new JScrollPane(txtDetalleFactura);
        scrollPaneDetalle.setBorder(createStyledBorder("Detalle de Factura"));
        tabFormulario.add(scrollPaneDetalle, BorderLayout.SOUTH); // Añadido al sur de la primera pestaña

        // Añadir pestañas
        tabbedPane.addTab("Generar Factura", tabFormulario);
        tabbedPane.addTab("Listado de Facturas", panelTabla); // Reutilizamos el panelTabla aquí
        add(tabbedPane, BorderLayout.CENTER);
    }

    public Trabajo getTrabajoSeleccionado() {
        return (Trabajo) cbTrabajos.getSelectedItem();
    }

    public void cargarTrabajos(List<Trabajo> trabajos) {
        DefaultComboBoxModel<Trabajo> model = new DefaultComboBoxModel<>();
        if (trabajos != null) {
            for (Trabajo trabajo : trabajos) {
                model.addElement(trabajo);
            }
        }
        cbTrabajos.setModel(model);
        cbTrabajos.setSelectedIndex(-1);
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void limpiarFormulario() {
        cbTrabajos.setSelectedIndex(-1);
        txtDetalleFactura.setText("");
    }

    public void mostrarFacturaGenerada(Factura factura) {
        String detalle = String.format(
                "========================================\n" +
                        "      TALLER AUTOMOTRIZ MOTORES & RUEDAS\n" +
                        "           NIT: 900.123.456-7\n" +
                        "   Dirección: Calle 123 #45-67, Bogotá\n" +
                        "        Teléfono: +57 1 2345678\n" +
                        "========================================\n" +
                        "\n" +
                        "           FACTURA ELECTRÓNICA\n" +
                        "Número: %s\n" +
                        "Fecha: %s\n" +
                        "CUFE: %s\n" +
                        "\n" +
                        "Cliente: %s\n" +
                        "Identificación: %s\n" +
                        "\n" +
                        "========================================\n" +
                        "           DETALLE DE LA FACTURA\n" +
                        "========================================\n" +
                        "Subtotal: $%.2f\n" +
                        "Impuestos: $%.2f\n" +
                        "========================================\n" +
                        "Total: $%.2f\n" +
                        "========================================\n" +
                        "\n" +
                        "Gracias por su preferencia!\n" +
                        "\n" +
                        "TALLER AUTOMOTRIZ MOTORES & RUEDAS\n" +
                        "========================================\n",
                factura.getNumeroFactura(),
                factura.getFechaEmision(),
                factura.getCUFE(),
                factura.getIdCliente().getNombre(),
                factura.getIdCliente().getIdentificacion(),
                factura.getSubtotal(),
                factura.getImpuestos(),
                factura.getTotal());

        txtDetalleFactura.setText(detalle);
    }

    public void setFacturaController(FacturaController facturaController) {
        this.facturaController = facturaController;

        if (this.facturaController != null) {
            SwingUtilities.invokeLater(() -> {
                this.facturaController.cargarTrabajosFacturables();
            });
        }

        if (btnGenerar != null && this.facturaController != null) {
            btnGenerar.addActionListener(e -> {
                facturaController.generarFacturaDesdeVista();
            });
        }
    }

    public void setGenerarFacturaListener(ActionListener listener) {
        this.generarFacturaListener = listener;
        if (btnGenerar != null && listener != null) {
            btnGenerar.addActionListener(listener);
        }
    }

    public void setTrabajoDAO(TrabajoDAO trabajoDAO) {
        this.trabajoDAO = trabajoDAO;
    }

    // Métodos auxiliares para estilos
    private JTextField createStyledTextField(int columns) {
        JTextField field = new JTextField(columns);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180)),
                BorderFactory.createEmptyBorder(5, 8, 5, 8)));
        return field;
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return button;
    }

    private TitledBorder createStyledBorder(String title) {
        return BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180)),
                title,
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 13),
                new Color(70, 70, 70));
    }
}