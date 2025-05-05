package com.carmotors.view;

import com.carmotors.model.Servicio;
import com.carmotors.model.enums.EstadoServicio;
import com.carmotors.model.enums.TipoMantenimiento;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PanelServicio extends JPanel {
    private JTextField txtNombre, txtDescripcion, txtCosto, txtTiempo;
    private JComboBox<String> cbTipoMantenimiento, cbEstado;
    private JComboBox<Servicio> listServicios;
    private JButton btnGuardar, btnLimpiar;

    public PanelServicio() {
        setLayout(new GridLayout(8, 2, 10, 10)); 
        initComponents();
    }

    private void initComponents() {
        txtNombre = new JTextField();
        txtDescripcion = new JTextField();
        txtCosto = new JTextField();
        txtTiempo = new JTextField();
        listServicios = new JComboBox<>();

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

        add(new JLabel("Nombre:"));
        add(txtNombre);
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
        add(new JLabel("Servicios existentes:"));
        add(listServicios);
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
        txtNombre.setText("");
        txtDescripcion.setText("");
        txtCosto.setText("");
        cbEstado.setSelectedIndex(0);
        txtTiempo.setText("");
    }

    public void inicializarComponente() {
        listServicios.setSelectedIndex(-1);
        limpiarFormulario();
    }

    public void mostrarServicios(List<Servicio> list) {
        DefaultComboBoxModel<Servicio> model = new DefaultComboBoxModel<>();
        for (Servicio servicio : list) {
            model.addElement(servicio);
        }
        listServicios.setModel(model);
        listServicios.setSelectedIndex(-1);
    }
}