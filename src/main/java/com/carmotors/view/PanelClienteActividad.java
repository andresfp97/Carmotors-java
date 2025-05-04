package com.carmotors.view;

import com.carmotors.model.ClienteActividad;
import com.carmotors.model.enums.ResultadoActividad;

import javax.swing.*;
import java.awt.*;

public class PanelClienteActividad extends JPanel {
    private JTextField txtIdCliente;
    private JTextField txtIdActividad;
    private JComboBox<ResultadoActividad> cbResultado;
    private JButton btnGuardar, btnLimpiar;

    public PanelClienteActividad() {
        setLayout(new GridLayout(4, 2, 10, 10));
        setBackground(new Color(40, 35, 35));
        initComponents();
    }

    private void initComponents() {
        // Configurar el layout y fondo primero
        setLayout(new GridLayout(4, 2, 10, 10));
        setBackground(new Color(40, 35, 35));

        // Inicializar todos los componentes
        txtIdCliente = new JTextField();
        txtIdActividad = new JTextField();
        cbResultado = new JComboBox<>(ResultadoActividad.values());
        btnGuardar = new JButton("Guardar");
        btnLimpiar = new JButton("Limpiar");

        // Configurar el renderizador del ComboBox
        cbResultado.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value != null) {
                    setText(value.toString().charAt(0) + value.toString().substring(1).toLowerCase());
                }
                return this;
            }
        });

        // Crear etiquetas
        JLabel lblIdCliente = new JLabel("ID Cliente:");
        JLabel lblIdActividad = new JLabel("ID Actividad:");
        JLabel lblResultado = new JLabel("Resultado:");
        JLabel lblEspacio = new JLabel(""); // Etiqueta vacía para completar el grid

        // Configurar colores de las etiquetas
        lblIdCliente.setForeground(Color.WHITE);
        lblIdActividad.setForeground(Color.WHITE);
        lblResultado.setForeground(Color.WHITE);

        // Agregar componentes al panel en el orden correcto
        add(lblIdCliente);
        add(txtIdCliente);
        add(lblIdActividad);
        add(txtIdActividad);
        add(lblResultado);
        add(cbResultado);
        add(btnGuardar);
        add(btnLimpiar);

        // Acción para el botón limpiar
        btnLimpiar.addActionListener(e -> limpiarFormulario());
    }


    public void limpiarFormulario() {
        txtIdCliente.setText("");
        txtIdActividad.setText("");
        cbResultado.setSelectedIndex(0);
    }

    public ClienteActividad getDatosFormulario() {
        ClienteActividad ca = new ClienteActividad();

        try {
            ca.setIdCliente(Integer.parseInt(txtIdCliente.getText()));
            ca.setIdActividad(Integer.parseInt(txtIdActividad.getText()));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Los IDs deben ser números válidos",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        ca.setResultadoActividad((ResultadoActividad) cbResultado.getSelectedItem());
        return ca;
    }

    public void setGuardarListener(java.awt.event.ActionListener listener) {
        btnGuardar.addActionListener(listener);
    }

    public void setDatosFormulario(ClienteActividad clienteActividad) {
        if (clienteActividad != null) {
            txtIdCliente.setText(clienteActividad.getIdCliente() != null ?
                    clienteActividad.getIdCliente().toString() : "");
            txtIdActividad.setText(clienteActividad.getIdActividad() != null ?
                    clienteActividad.getIdActividad().toString() : "");
            cbResultado.setSelectedItem(clienteActividad.getResultadoActividad());
        }
    }
}
