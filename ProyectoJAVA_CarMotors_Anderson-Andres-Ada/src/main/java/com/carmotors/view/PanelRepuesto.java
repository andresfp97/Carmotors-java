package com.carmotors.view;

import com.carmotors.model.Repuesto;
import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PanelRepuesto extends JPanel {
    private JTextField txtNombre, txtTipo, txtMarca, txtModelo;
    private JFormattedTextField txtFecha;
    private JButton btnGuardar, btnLimpiar;

    public PanelRepuesto() {
        setLayout(new GridLayout(6, 2, 10, 10));
        setBackground(new Color(40, 35, 35));
        initComponents();
    }

    private void initComponents() {
        txtNombre = new JTextField();
        txtTipo = new JTextField();
        txtMarca = new JTextField();
        txtModelo = new JTextField();
        txtFecha = new JFormattedTextField(new SimpleDateFormat("dd/MM/yyyy"));
        btnGuardar = new JButton("Guardar");
        btnLimpiar = new JButton("Limpiar");

        JLabel[] labels = {
                new JLabel("Nombre:"),
                new JLabel("Tipo:"),
                new JLabel("Marca:"),
                new JLabel("Modelo Compatible:"),
                new JLabel("Vida Útil Estimada:")
        };

        for (JLabel label : labels) {
            label.setForeground(Color.WHITE);
        }

        add(labels[0]);
        add(txtNombre);
        add(labels[1]);
        add(txtTipo);
        add(labels[2]);
        add(txtMarca);
        add(labels[3]);
        add(txtModelo);
        add(labels[4]);
        add(txtFecha);
        add(btnGuardar);
        add(btnLimpiar);

        btnLimpiar.addActionListener(e -> limpiarFormulario());
    }

    public Repuesto getDatosFormulario() {
        Repuesto r = new Repuesto();
        r.setNombre(txtNombre.getText());
        r.setTipo(txtTipo.getText());
        r.setMarca(txtMarca.getText());
        r.setModeloCompatible(txtModelo.getText());
        r.setVidaUtilEstimada((Date) txtFecha.getValue());
        return r;
    }

    public void limpiarFormulario() {
        txtNombre.setText("");
        txtTipo.setText("");
        txtMarca.setText("");
        txtModelo.setText("");
        txtFecha.setValue(null);
    }

    public void setGuardarListener(java.awt.event.ActionListener listener) {
        btnGuardar.addActionListener(listener);
    }

    public void setDatosFormulario(Repuesto repuesto) {
        txtNombre.setText(repuesto.getNombre());
        txtTipo.setText(repuesto.getTipo());
        txtMarca.setText(repuesto.getMarca());
        txtModelo.setText(repuesto.getModeloCompatible());
        txtFecha.setValue(repuesto.getVidaUtilEstimada());
    }
}