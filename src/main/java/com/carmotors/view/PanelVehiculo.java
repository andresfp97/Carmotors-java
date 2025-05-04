package com.carmotors.view;

import com.carmotors.model.Cliente;
import com.carmotors.model.Vehiculo;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class PanelVehiculo extends JPanel {
    private JTextField txtMarca, txtModelo, txtPlaca, txtTipoVehiculo;
    private JComboBox<ClienteCombo> cbClientes;
    private JButton btnGuardar, btnLimpiar, btnBuscarCliente;

    public PanelVehiculo() {
        setLayout(new GridLayout(8, 2, 10, 10));
        setBackground(new Color(40, 35, 35));
        initComponents(); // Solo un método de inicialización
        configurarComboClientes();
    }

    private void initComponents() {
        cbClientes = new JComboBox<>();
        txtMarca = new JTextField();
        txtModelo = new JTextField();
        txtPlaca = new JTextField();
        txtTipoVehiculo = new JTextField();
        btnGuardar = new JButton("Guardar");
        btnLimpiar = new JButton("Limpiar");
        btnBuscarCliente = new JButton("Buscar Cliente");

        // Configurar combo box
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

        JLabel[] labels = {
                new JLabel("Cliente:"),
                new JLabel("Marca:"),
                new JLabel("Modelo:"),
                new JLabel("Placa:"),
                new JLabel("Tipo de Vehículo:")
        };

        // Configurar colores de las etiquetas
        for (JLabel label : labels) {
            label.setForeground(Color.WHITE);
        }

        // Agregar componentes al panel
        add(labels[0]); add(cbClientes);
        add(new JLabel()); add(btnBuscarCliente); // Espacio y botón buscar
        add(labels[1]); add(txtMarca);
        add(labels[2]); add(txtModelo);
        add(labels[3]); add(txtPlaca);
        add(labels[4]); add(txtTipoVehiculo);
        add(btnGuardar); add(btnLimpiar);

        // Acción para limpiar formulario
        btnLimpiar.addActionListener(e -> limpiarFormulario());
    }
    private void configurarComboClientes() {
        // Configurar renderizador personalizado
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

        // Añadir listener para debug
        cbClientes.addActionListener(e -> {
            ClienteCombo seleccionado = (ClienteCombo) cbClientes.getSelectedItem();
            System.out.println("Cliente seleccionado: " + (seleccionado != null ? seleccionado.getId() : "null"));
        });
    }

    // Clase interna para manejar el combo de clientes
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

        // Obtener ID del cliente seleccionado
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

    public void cargarClientes(java.util.List<Cliente> clientes) {
        DefaultComboBoxModel<ClienteCombo> model = new DefaultComboBoxModel<>();

        // Opción por defecto
        model.addElement(new ClienteCombo(null, "-- Seleccione un cliente --"));

        if (clientes != null && !clientes.isEmpty()) {
            for (Cliente cliente : clientes) {
                String displayText = String.format("%s (%s)", cliente.getNombre(), cliente.getIdentificacion());
                model.addElement(new ClienteCombo(cliente.getId(), displayText));
            }
        } else {
            System.out.println("[WARNING] Lista de clientes vacía o nula");
        }

        // Actualización segura en el hilo de eventos
        SwingUtilities.invokeLater(() -> {
            cbClientes.setModel(model);
            System.out.println("[DEBUG] ComboBox actualizado con " + model.getSize() + " elementos");

            // Seleccionar el primer elemento si existe
            if (model.getSize() > 1) {
                cbClientes.setSelectedIndex(1); // Saltar la opción por defecto
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