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
import java.util.ArrayList;

public class PanelFactura extends JPanel {
    private JComboBox<Trabajo> cbTrabajos;
    private JButton btnGenerarFactura;
    private JTextArea txtDetalleFactura;
    private FacturaController facturaController;
    private TrabajoDAO trabajoDAO;
    private ActionListener generarFacturaListener;
    private JTable tablaFacturas;  // Agregamos la tabla para mostrar las facturas
    private DefaultTableModel modeloTablaFacturas; //y su modelo

    public PanelFactura() {
        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 240));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        initComponents();
    }

    private void initComponents() {
        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelSuperior.setBackground(Color.WHITE);
        panelSuperior.setBorder(createStyledBorder("Seleccionar Trabajo"));

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

        btnGenerarFactura = createStyledButton("Generar Factura", new Color(70, 130, 180));

        panelSuperior.add(lblTrabajo);
        panelSuperior.add(cbTrabajos);
        panelSuperior.add(btnGenerarFactura);

        txtDetalleFactura = new JTextArea();
        txtDetalleFactura.setEditable(false);
        txtDetalleFactura.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPaneDetalle = new JScrollPane(txtDetalleFactura);
        scrollPaneDetalle.setBorder(createStyledBorder("Detalle de Factura"));

        // Crear tabla para mostrar facturas
        JPanel panelTablaFacturas = new JPanel(new BorderLayout());
        panelTablaFacturas.setBorder(createStyledBorder("Facturas Generadas"));
        panelTablaFacturas.setBackground(Color.WHITE);

        modeloTablaFacturas = new DefaultTableModel(
                new Object[]{"ID Factura", "Número Factura", "Fecha Emisión", "Total"}, 0);
        tablaFacturas = new JTable(modeloTablaFacturas);
        JScrollPane scrollPaneTabla = new JScrollPane(tablaFacturas);
        panelTablaFacturas.add(scrollPaneTabla, BorderLayout.CENTER);

        // Crear un panel para organizar los componentes verticalmente
        JPanel panelContenedor = new JPanel();
        panelContenedor.setLayout(new BoxLayout(panelContenedor, BoxLayout.Y_AXIS));
        panelContenedor.add(panelSuperior);       // Agregar el panel superior primero
        panelContenedor.add(scrollPaneDetalle);  // Luego el detalle
        panelContenedor.add(panelTablaFacturas); // Y finalmente la tabla

        add(panelContenedor, BorderLayout.CENTER); // Agregar el panel contenedor al centro del PanelFactura
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
        modeloTablaFacturas.setRowCount(0); // Limpiar la tabla de facturas
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

        // Agregar la factura a la tabla
        modeloTablaFacturas.addRow(new Object[]{
                factura.getIdFactura(),
                factura.getNumeroFactura(),
                factura.getFechaEmision(),
                factura.getTotal()
        });
    }

    public void setFacturaController(FacturaController facturaController) {
        this.facturaController = facturaController;

        if (this.facturaController != null) {
            SwingUtilities.invokeLater(() -> {
                this.facturaController.cargarTrabajosFacturables();
            });
        }

        if (btnGenerarFactura != null && this.facturaController != null) {
            btnGenerarFactura.addActionListener(e -> {
                facturaController.generarFacturaDesdeVista();
            });
        }
    }

    public void setGenerarFacturaListener(ActionListener listener) {
        this.generarFacturaListener = listener;
        if (btnGenerarFactura != null && listener != null) {
            btnGenerarFactura.addActionListener(listener);
        }
    }

    public void setTrabajoDAO(TrabajoDAO trabajoDAO) {
        this.trabajoDAO = trabajoDAO;
    }

    // Métodos auxiliares para estilos (los mismos que en PanelCliente)
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

    private void addFormRow(JPanel panel, GridBagConstraints gbc, int row, String labelText, JComponent field) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0;
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        panel.add(label, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        panel.add(field, gbc);
    }

    public void setFacturas(List<Factura> facturas) {
        // Limpiar la tabla antes de agregar nuevas facturas
        modeloTablaFacturas.setRowCount(0);
        for (Factura factura : facturas) {
            modeloTablaFacturas.addRow(new Object[]{
                    factura.getIdFactura(),
                    factura.getNumeroFactura(),
                    factura.getFechaEmision(),
                    factura.getTotal()
            });
        }
    }
}

