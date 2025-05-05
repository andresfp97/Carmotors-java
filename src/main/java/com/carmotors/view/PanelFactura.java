package com.carmotors.view;

import com.carmotors.controller.FacturaController;
import com.carmotors.model.Factura;
import com.carmotors.model.Trabajo;
import com.carmotors.modelDAO.FacturaDAO;
import com.carmotors.modelDAO.TrabajoDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;

public class PanelFactura extends JPanel {
    private JComboBox<Trabajo> cbTrabajos;
    private JButton btnGenerarFactura;
    private JTextArea txtDetalleFactura;
    private FacturaController facturaController;
    private TrabajoDAO trabajoDAO;
    private ActionListener generarFacturaListener;

    public PanelFactura() {
        setLayout(new BorderLayout(10, 10));
        initComponents();
    }

    private void initComponents() {
        // Panel superior con combobox y botón
        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));

        cbTrabajos = new JComboBox<>();
        cbTrabajos.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value,
                    int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Trabajo) {
                    Trabajo t = (Trabajo) value;
                    setText(String.format("Trabajo #%d - %s", t.getId(), t.getServicio().getDescripcion()));
                }
                return this;
            }
        });

        btnGenerarFactura = new JButton("Generar Factura");
        btnGenerarFactura.setBackground(new Color(70, 130, 180));
        btnGenerarFactura.setForeground(Color.WHITE);

        panelSuperior.add(new JLabel("Trabajo:"));
        panelSuperior.add(cbTrabajos);
        panelSuperior.add(btnGenerarFactura);

        // Área de texto para mostrar la factura
        txtDetalleFactura = new JTextArea();
        txtDetalleFactura.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(txtDetalleFactura);

        add(panelSuperior, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }


    public Trabajo getTrabajoSeleccionado() {
        return (Trabajo) cbTrabajos.getSelectedItem();
    }

    public void cargarTrabajos(Object trabajos) {
        DefaultComboBoxModel<Trabajo> model = new DefaultComboBoxModel<>();
        model.addAll((List<Trabajo>) trabajos);
        cbTrabajos.setModel(model);
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
                "FACTURA #%s\n\n" +
                        "Cliente: %s\n" +
                        "Fecha: %s\n" +
                        "CUFE: %s\n\n" +
                        "Subtotal: $%.2f\n" +
                        "IVA (19%%): $%.2f\n" +
                        "TOTAL: $%.2f",
                factura.getNumeroFactura(),
                factura.getIdCliente().getNombre(),
                factura.getFechaEmision(),
                factura.getCUFE(),
                factura.getSubtotal(),
                factura.getImpuestos(),
                factura.getTotal());
        txtDetalleFactura.setText(detalle);
    }

    public void setFacturaController(FacturaController facturaController) {
        this.facturaController = facturaController;
        // Configurar el listener para el botón de generar factura
        if (btnGenerarFactura != null) {
            btnGenerarFactura.addActionListener(e -> {
                if (facturaController != null) { 
                    facturaController.setGenerarFacturaListener(this.generarFacturaListener);
                }
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
}