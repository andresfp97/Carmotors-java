package com.carmotors.view;

import com.carmotors.model.Cliente;

import javax.swing.*;
import java.awt.*;

public class PanelCliente  extends JPanel{
    private JTextField txtNombre, txtIdentificacion, txtTelefono, txtCorreoElectronico;
    private JButton btnGuardar, btnLimpiar;

    public PanelCliente() {
        setLayout(new GridLayout(5, 2, 10, 10));
        setBackground(new Color(40, 35, 35));
        initComponents();
    }
    private void initComponents() {
        txtNombre = new JTextField();
        txtIdentificacion = new JTextField();
        txtTelefono = new JTextField();
        txtCorreoElectronico = new JTextField();
        btnGuardar = new JButton("Guardar");
        btnLimpiar = new JButton("Limpiar");

        JLabel[] labels = {
                new JLabel("Nombre:"),
                new JLabel("Identificacion:"),
                new JLabel("Telefono(celular):"),
                new JLabel("Email:")
        };

        for (JLabel label : labels) {
            label.setForeground(Color.WHITE);
        }

        add(labels[0]); add(txtNombre);
        add(labels[1]); add(txtIdentificacion);
        add(labels[2]); add(txtTelefono);
        add(labels[3]); add(txtCorreoElectronico);
        add(btnGuardar); add(btnLimpiar);

        btnLimpiar.addActionListener(e -> limpiarFormulario());
    }


    public void limpiarFormulario() {
        txtNombre.setText("");
        txtIdentificacion.setText("");
        txtTelefono.setText("");
        txtCorreoElectronico.setText("");
    }

    public Cliente getDatosFormulario() {
        Cliente p = new Cliente();
        p.setNombre(txtNombre.getText());
        p.setIdentificacion(txtIdentificacion.getText());
        p.setTelefono(txtTelefono.getText());
        p.setCorreoElectronico(txtCorreoElectronico.getText());
        return p;
    }

    public void setGuardarListener(java.awt.event.ActionListener listener) {
        btnGuardar.addActionListener(listener);
    }

    public void setDatosFormulario(Cliente cliente) {
        txtNombre.setText(cliente.getNombre());
        txtIdentificacion.setText(cliente.getIdentificacion());
        txtTelefono.setText(cliente.getTelefono());
        txtCorreoElectronico.setText(cliente.getCorreoElectronico());
    }
}
