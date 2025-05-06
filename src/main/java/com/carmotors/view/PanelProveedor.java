package com.carmotors.view;

import com.carmotors.model.Proveedor;
import com.carmotors.model.enums.FrecuenciaVisitas;

import javax.swing.*;
import java.awt.*;


public class PanelProveedor extends JPanel {
    private JTextField txtNombre, txtNit, txtContacto;
    private JComboBox<FrecuenciaVisitas> cmbFrecuencia;
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
        
        // Cambiamos el JSpinner por un JComboBox con las opciones del enum
        cmbFrecuencia = new JComboBox<>(FrecuenciaVisitas.values());
        cmbFrecuencia.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, 
                    boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof FrecuenciaVisitas) {
                    setText(((FrecuenciaVisitas) value).getDescripcion());
                }
                return this;
            }
        });
        
        btnGuardar = new JButton("Guardar");
        btnLimpiar = new JButton("Limpiar");

        JLabel[] labels = {
            new JLabel("Nombre:"),
            new JLabel("NIT:"),
            new JLabel("Contacto:"),
            new JLabel("Frecuencia de visitas:")
        };

        for (JLabel label : labels) {
            label.setForeground(Color.WHITE);
        }

        add(labels[0]); add(txtNombre);
        add(labels[1]); add(txtNit);
        add(labels[2]); add(txtContacto);
        add(labels[3]); add(cmbFrecuencia);
        add(btnGuardar); add(btnLimpiar);

        btnLimpiar.addActionListener(e -> limpiarFormulario());
    }

    public Proveedor getDatosFormulario() {
        Proveedor p = new Proveedor();
        p.setNombre(txtNombre.getText().trim());   
        p.setNit(txtNit.getText().trim());
        p.setContacto(txtContacto.getText().trim());
        p.setFrecuenciaVisitas((FrecuenciaVisitas) cmbFrecuencia.getSelectedItem());
        return p;
    }

    public void limpiarFormulario() {
        txtNombre.setText("");
        txtNit.setText("");
        txtContacto.setText("");
        cmbFrecuencia.setSelectedIndex(0);
    }

    public void setGuardarListener(java.awt.event.ActionListener listener) {
        btnGuardar.addActionListener(listener);
    }

    public void setDatosFormulario(Proveedor proveedor) {
        txtNombre.setText(proveedor.getNombre());
        txtNit.setText(proveedor.getNit());
        txtContacto.setText(proveedor.getContacto());
        cmbFrecuencia.setSelectedItem(proveedor.getFrecuenciaVisitas());
    }
}