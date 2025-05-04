package com.carmotors.view;

import com.carmotors.model.Servicio;
import com.carmotors.model.enums.EstadoServicio;
import com.carmotors.model.enums.TipoMantenimiento;

import javax.swing.*;
import java.awt.*;

public class PanelServicio extends JPanel {
    private JTextField txtNombre, txtDescripcion, txtCosto, txtTiempo;
    private JComboBox<String> cbTipoMantenimiento, cbEstado;
    private JButton btnGuardar, btnLimpiar;

    public PanelServicio() {
        setLayout(new GridLayout(7, 2, 10, 10)); // Aumentado a 7 filas
        initComponents();
    }

    private void initComponents() {
        txtDescripcion = new JTextField();
        txtCosto = new JTextField();
        txtTiempo = new JTextField();

        // ComboBox para TipoMantenimiento
        cbTipoMantenimiento = new JComboBox<>();
        for (TipoMantenimiento tipo : TipoMantenimiento.values()) {
            cbTipoMantenimiento.addItem(tipo.name());
        }

        // ComboBox para EstadoServicio
        cbEstado = new JComboBox<>();
        for (EstadoServicio estado : EstadoServicio.values()) {
            cbEstado.addItem(estado.name());
        }

        btnGuardar = new JButton("Guardar");
        btnLimpiar = new JButton("Limpiar");

        add(new JLabel("Tipo Mantenimiento:"));
        add(cbTipoMantenimiento);
        add(new JLabel("Descripción:"));
        add(txtDescripcion);
        add(new JLabel("Costo:"));
        add(txtCosto);
        add(new JLabel("Estado:"));
        add(cbEstado);
        add(new JLabel("Tiempo estimado (min):"));
        add(txtTiempo);
        add(btnGuardar);
        add(btnLimpiar);
    }

    public Servicio getDatosFormulario() {
        Servicio servicio = new Servicio();
        servicio.setTipoMantenimiento(TipoMantenimiento.valueOf(cbTipoMantenimiento.getSelectedItem().toString()));
        servicio.setDescripcion(txtDescripcion.getText());
        servicio.setCostoManoObra(Double.parseDouble(txtCosto.getText()));
        servicio.setEstadoServicio(EstadoServicio.valueOf(cbEstado.getSelectedItem().toString()));
        servicio.setTiempoEstimadoMinutos(Integer.parseInt(txtTiempo.getText()));

        return servicio;
    }

    public void setGuardarListener(java.awt.event.ActionListener listener) {
        btnGuardar.addActionListener(listener);
    }

    public void limpiarFormulario() {
        cbTipoMantenimiento.setSelectedIndex(0);
        txtDescripcion.setText("");
        txtCosto.setText("");
        cbEstado.setSelectedIndex(0);
        txtTiempo.setText("");
    }
}