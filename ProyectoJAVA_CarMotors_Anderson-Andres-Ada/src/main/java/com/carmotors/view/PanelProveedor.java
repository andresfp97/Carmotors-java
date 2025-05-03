package com.carmotors.view;

import com.carmotors.model.Proveedor;
import javax.swing.*;
import java.awt.*;

public class PanelProveedor extends JPanel {
    private JTextField txtNombre, txtNit, txtContacto;
    private JSpinner spnFrecuencia;
    private JButton btnGuardar, btnLimpiar;

    public PanelProveedor() {
        setLayout(new GridLayout(5, 2, 10, 10));
        setBackground(new Color(40, 35, 35));
        initComponents();
    }

    private void initComponents() {
        txtNombre = new JTextField();
        txtNit = new JTextField();
        txtContacto = new JTextField();
        spnFrecuencia = new JSpinner(new SpinnerNumberModel(1, 1, 30, 1));
        btnGuardar = new JButton("Guardar");
        btnLimpiar = new JButton("Limpiar");

        JLabel[] labels = {
                new JLabel("Nombre:"),
                new JLabel("NIT:"),
                new JLabel("Contacto:"),
                new JLabel("Frecuencia de visitas (días):")
        };

        for (JLabel label : labels) {
            label.setForeground(Color.WHITE);
        }

        add(labels[0]); add(txtNombre);
        add(labels[1]); add(txtNit);
        add(labels[2]); add(txtContacto);
        add(labels[3]); add(spnFrecuencia);
        add(btnGuardar); add(btnLimpiar);

        btnLimpiar.addActionListener(e -> limpiarFormulario());
    }

    public Proveedor getDatosFormulario() {
        Proveedor p = new Proveedor();
        p.setNombre(txtNombre.getText());
        p.setNit(txtNit.getText());
        p.setContacto(txtContacto.getText());
        p.setFrecuenciaVisitas((Integer) spnFrecuencia.getValue());
        return p;
    }

    public void limpiarFormulario() {
        txtNombre.setText("");
        txtNit.setText("");
        txtContacto.setText("");
        spnFrecuencia.setValue(1);
    }

    public void setGuardarListener(java.awt.event.ActionListener listener) {
        btnGuardar.addActionListener(listener);
    }

    public void setDatosFormulario(Proveedor proveedor) {
        txtNombre.setText(proveedor.getNombre());
        txtNit.setText(proveedor.getNit());
        txtContacto.setText(proveedor.getContacto());
        spnFrecuencia.setValue(proveedor.getFrecuenciaVisitas());
    }
}
