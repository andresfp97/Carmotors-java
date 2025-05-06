package com.carmotors.view;

import com.carmotors.model.Cliente;
import com.carmotors.model.Vehiculo;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Objects;

public class PanelVehiculo extends JPanel {
    private JTextField txtMarca, txtModelo, txtPlaca, txtTipoVehiculo;
    private JComboBox<ClienteCombo> cbClientes;
    private JButton btnGuardar, btnLimpiar, btnBuscarCliente;

    public PanelVehiculo() {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 245)); // Fondo gris claro
        setBorder(new EmptyBorder(15, 15, 15, 15)); // Márgenes alrededor del panel
        initComponents();
    }

    private void initComponents() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(220, 220, 220)),
                new EmptyBorder(15, 15, 15, 15)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10); // Espacio entre componentes
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0; // Hacer que los campos de texto se expandan

        JLabel lblCliente = createStyledLabel("Cliente:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(lblCliente, gbc);

        JPanel clientePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        clientePanel.setBackground(Color.WHITE);
        cbClientes = createStyledComboBox();
        clientePanel.add(cbClientes);
        btnBuscarCliente = createStyledButton("Buscar", new Color(63, 81, 181)); // Azul indigo
        clientePanel.add(btnBuscarCliente);
        gbc.gridx = 1;
        gbc.gridy = 0;
        formPanel.add(clientePanel, gbc);

        JLabel lblMarca = createStyledLabel("Marca:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(lblMarca, gbc);
        txtMarca = createStyledTextField();
        gbc.gridx = 1;
        gbc.gridy = 1;
        formPanel.add(txtMarca, gbc);

        JLabel lblModelo = createStyledLabel("Modelo:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(lblModelo, gbc);
        txtModelo = createStyledTextField();
        gbc.gridx = 1;
        gbc.gridy = 2;
        formPanel.add(txtModelo, gbc);

        JLabel lblPlaca = createStyledLabel("Placa:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(lblPlaca, gbc);
        txtPlaca = createStyledTextField();
        gbc.gridx = 1;
        gbc.gridy = 3;
        formPanel.add(txtPlaca, gbc);

        JLabel lblTipoVehiculo = createStyledLabel("Tipo de Vehículo:");
        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(lblTipoVehiculo, gbc);
        txtTipoVehiculo = createStyledTextField();
        gbc.gridx = 1;
        gbc.gridy = 4;
        formPanel.add(txtTipoVehiculo, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.WHITE);
        btnGuardar = createStyledButton("Guardar", new Color(76, 175, 80)); // Verde
        buttonPanel.add(btnGuardar);
        btnLimpiar = createStyledButton("Limpiar", new Color(255, 152, 0)); // Naranja
        buttonPanel.add(btnLimpiar);

        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        btnLimpiar.addActionListener(e -> limpiarFormulario());

        // Configurar renderizador personalizado para el ComboBox
        cbClientes.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof ClienteCombo) {
                    ClienteCombo cliente = (ClienteCombo) value;
                    setText(cliente.getDisplayText());
                }
                return this;
            }
        });
    }

    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        label.setForeground(new Color(50, 50, 50));
        return label;
    }

    private JTextField createStyledTextField() {
        JTextField textField = new JTextField();
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textField.setBorder(new CompoundBorder(
                new LineBorder(new Color(200, 200, 200)),
                new EmptyBorder(8, 10, 8, 10)
        ));
        return textField;
    }

    private JComboBox<ClienteCombo> createStyledComboBox() {
        JComboBox<ClienteCombo> comboBox = new JComboBox<>();
        comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        comboBox.setBorder(new CompoundBorder(
                new LineBorder(new Color(200, 200, 200)),
                new EmptyBorder(8, 10, 8, 10)
        ));
        return comboBox;
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(new EmptyBorder(10, 20, 10, 20));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return button;
    }

    private void configurarComboClientes() {
        cbClientes.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value,
                                                          int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof ClienteCombo) {
                    ClienteCombo cc = (ClienteCombo) value;
                    setText(cc.toString());
                }
                return this;
            }
        });

        cbClientes.addActionListener(e -> {
            ClienteCombo seleccionado = (ClienteCombo) cbClientes.getSelectedItem();
            System.out.println("Cliente seleccionado: " + (seleccionado != null ? seleccionado.getId() : "null"));
        });
    }

    private static class ClienteCombo {
        private final Integer id;
        private final String displayText;

        public ClienteCombo(Integer id, String displayText) {
            this.id = id;
            this.displayText = displayText;
        }

        public Integer getId() {
            return id;
        }

        public String getDisplayText() {
            return displayText;
        }

        @Override
        public String toString() {
            return displayText;
        }
    }

    public void limpiarFormulario() {
        cbClientes.setSelectedIndex(-1);
        txtMarca.setText("");
        txtModelo.setText("");
        txtPlaca.setText("");
        txtTipoVehiculo.setText("");
    }

    public Vehiculo getDatosFormulario() {
        Vehiculo vehiculo = new Vehiculo();
        if (cbClientes.getSelectedItem() instanceof ClienteCombo) {
            ClienteCombo cliente = (ClienteCombo) cbClientes.getSelectedItem();
            vehiculo.setId(cliente.getId());
        }
        vehiculo.setMarca(txtMarca.getText());
        vehiculo.setModelo(txtModelo.getText());
        vehiculo.setPlaca(txtPlaca.getText());
        vehiculo.setTipoVehiculo(txtTipoVehiculo.getText());
        return vehiculo;
    }

    public void setGuardarListener(ActionListener listener) {
        btnGuardar.addActionListener(listener);
    }

    public void setBuscarClienteListener(ActionListener listener) {
        btnBuscarCliente.addActionListener(listener);
    }

    public void setDatosFormulario(Vehiculo vehiculo) {
        // Necesitarías implementar lógica para seleccionar el cliente en el combo
        txtMarca.setText(vehiculo.getMarca());
        txtModelo.setText(vehiculo.getModelo());
        txtPlaca.setText(vehiculo.getPlaca());
        txtTipoVehiculo.setText(vehiculo.getTipoVehiculo());
    }

    public void cargarClientes(List<Cliente> clientes) {
        DefaultComboBoxModel<ClienteCombo> model = new DefaultComboBoxModel<>();
        model.addElement(new ClienteCombo(null, "-- Seleccione un cliente --"));
        if (clientes != null && !clientes.isEmpty()) {
            for (Cliente cliente : clientes) {
                String displayText = String.format("%s (%s)", cliente.getNombre(), cliente.getIdentificacion());
                model.addElement(new ClienteCombo(cliente.getId(), displayText));
            }
        } else {
            System.out.println("[WARNING] Lista de clientes vacía o nula");
        }
        SwingUtilities.invokeLater(() -> {
            cbClientes.setModel(model);
            System.out.println("[DEBUG] ComboBox actualizado con " + model.getSize() + " elementos");
            if (model.getSize() > 1) {
                cbClientes.setSelectedIndex(1);
            }
            cbClientes.revalidate();
            cbClientes.repaint();
        });
    }

    public Integer getClienteSeleccionadoId() {
        if (cbClientes.getSelectedItem() instanceof ClienteCombo) {
            return ((ClienteCombo) cbClientes.getSelectedItem()).getId();
        }
        return null;
    }
}