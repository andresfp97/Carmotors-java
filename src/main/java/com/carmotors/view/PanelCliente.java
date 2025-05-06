package com.carmotors.view;

import com.carmotors.controller.ClienteController;
import com.carmotors.model.Cliente;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Objects;

public class PanelCliente extends JPanel {
    private JTextField txtNombre, txtIdentificacion, txtTelefono, txtCorreoElectronico, txtBuscarId;
    private JButton btnGuardar, btnBuscar, btnEliminar, btnLimpiar;
    private JTable tablaClientes;
    private DefaultTableModel modeloTablaClientes;
    private ClienteController clienteController;

    public PanelCliente() {
        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 240));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        initComponents();
    }

    public void setControlador(ClienteController clienteController) {
        this.clienteController = Objects.requireNonNull(clienteController, "El controlador no puede ser nulo");
        initListeners();
    }

    private void initListeners() {
        btnBuscar.addActionListener(e -> {
            try {
                String idTexto = txtBuscarId.getText().trim();
                if (idTexto.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Por favor ingrese un ID", "Campo vacío", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                clienteController.buscarPorId(idTexto);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "El ID debe ser un número", "Error de formato", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnEliminar.addActionListener(e -> {
            try {
                String idTexto = txtBuscarId.getText().trim(); // Corrección: Usar txtBuscarId
                if (idTexto.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Por favor ingrese un ID", "Campo vacío", JOptionPane.WARNING_MESSAGE); // Mensaje corregido
                    return;
                }
                clienteController.eliminarCliente(idTexto);
                limpiarFormulario();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "El ID debe ser un número", "Error de formato", JOptionPane.ERROR_MESSAGE); // Mensaje corregido
            }
        });

        btnLimpiar.addActionListener(e -> {
            txtBuscarId.setText("");
            clienteController.limpiarFormulario();
            clienteController.cargarClientes();
        });

        btnGuardar.addActionListener(e -> {
            clienteController.guardarCliente();
        });
        tablaClientes.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = tablaClientes.getSelectedRow();
                if (row >= 0) {
                    int id = (int) modeloTablaClientes.getValueAt(row, 0);
                    clienteController.buscarPorId(String.valueOf(id));
                }
            }
        });
    }

    private void initComponents() {
        JTabbedPane tabbedPane = new JTabbedPane();
        JPanel tabFormulario = new JPanel();
        tabFormulario.setLayout(new BorderLayout());
        tabFormulario.setBackground(new Color(240, 240, 240));

        JPanel panelFormulario = new JPanel();
        panelFormulario.setLayout(new BoxLayout(panelFormulario, BoxLayout.Y_AXIS));
        panelFormulario.setBackground(Color.WHITE);
        panelFormulario.setBorder(createStyledBorder("Registrar Cliente"));

        JPanel formContent = new JPanel(new GridBagLayout());
        formContent.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        addFormRow(formContent, gbc, 0, "Nombre:", txtNombre = createStyledTextField(15));
        addFormRow(formContent, gbc, 1, "Identificación:", txtIdentificacion = createStyledTextField(15));
        addFormRow(formContent, gbc, 2, "Teléfono:", txtTelefono = createStyledTextField(15));
        addFormRow(formContent, gbc, 3, "Correo Electrónico:", txtCorreoElectronico = createStyledTextField(15));

        panelFormulario.add(formContent);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.WHITE);
        btnGuardar = createStyledButton("Guardar Cliente", new Color(76, 175, 80));
        buttonPanel.add(btnGuardar);
        panelFormulario.add(Box.createRigidArea(new Dimension(0, 10)));
        panelFormulario.add(buttonPanel);

        tabFormulario.add(panelFormulario, BorderLayout.NORTH);

        JPanel panelTabla = new JPanel(new BorderLayout());
        panelTabla.setBorder(createStyledBorder("Registros de Clientes"));
        panelTabla.setBackground(Color.WHITE);

        JPanel buscarPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buscarPanel.setBackground(Color.WHITE);
        buscarPanel.add(new JLabel("Buscar por ID:"));
        txtBuscarId = createStyledTextField(10);  // Usar el nuevo campo txtBuscarId
        buscarPanel.add(txtBuscarId);
        btnBuscar = createStyledButton("Buscar", new Color(33, 150, 243));
        btnEliminar = createStyledButton("Eliminar", new Color(244, 67, 54));
        btnLimpiar = createStyledButton("Limpiar búsqueda", new Color(255, 193, 7));
        buscarPanel.add(btnBuscar);
        buscarPanel.add(btnEliminar);
        buscarPanel.add(btnLimpiar);

        panelTabla.add(buscarPanel, BorderLayout.NORTH);

        modeloTablaClientes = new DefaultTableModel(
                new Object[]{"ID", "Nombre", "Identificación", "Teléfono", "Email"}, 0);
        tablaClientes = new JTable(modeloTablaClientes);
        panelTabla.add(new JScrollPane(tablaClientes), BorderLayout.CENTER);

        tabFormulario.add(panelTabla, BorderLayout.CENTER);

        tabbedPane.addTab("Formulario Cliente", tabFormulario);
        add(tabbedPane, BorderLayout.CENTER);
    }

    private JTextField createStyledTextField(int columns) {
        JTextField field = new JTextField(columns);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180)),
                BorderFactory.createEmptyBorder(5, 8, 5, 8)));
        return field;
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return button;
    }

    private TitledBorder createStyledBorder(String title) {
        return BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180)),
                title,
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 13),
                new Color(70, 70, 70));
    }

    private void addFormRow(JPanel panel, GridBagConstraints gbc, int row, String labelText, JComponent field) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0;
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        panel.add(label, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        panel.add(field, gbc);
    }

    public void limpiarFormulario() {
        txtNombre.setText("");
        txtIdentificacion.setText("");
        txtTelefono.setText("");
        txtCorreoElectronico.setText("");
    }

    public Cliente getDatosFormulario() {
        Cliente cliente = new Cliente();
        cliente.setNombre(txtNombre.getText());
        cliente.setIdentificacion(txtIdentificacion.getText());
        cliente.setTelefono(txtTelefono.getText());
        cliente.setCorreoElectronico(txtCorreoElectronico.getText());
        return cliente;
    }

    public void setDatosFormulario(Cliente cliente) {
        txtNombre.setText(cliente.getNombre());
        txtIdentificacion.setText(cliente.getIdentificacion());
        txtTelefono.setText(cliente.getTelefono());
        txtCorreoElectronico.setText(cliente.getCorreoElectronico());
    }

    public void setClientes(List<Cliente> clientes) {
        modeloTablaClientes.setRowCount(0);
        for (Cliente c : clientes) {
            modeloTablaClientes.addRow(new Object[]{
                    c.getId(),
                    c.getNombre(),
                    c.getIdentificacion(),
                    c.getTelefono(),
                    c.getCorreoElectronico()
            });
        }
    }

    public DefaultTableModel getModeloTablaClientes() {
        return modeloTablaClientes;
    }

    public JTextField getTxtNombre() {
        return txtNombre;
    }

    public JTextField getTxtIdentificacion() {
        return txtIdentificacion;
    }

    public JTextField getTxtTelefono() {
        return txtTelefono;
    }

    public JTextField getTxtCorreoElectronico() {
        return txtCorreoElectronico;
    }

    public JButton getBtnGuardar() {
        return btnGuardar;
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public JButton getBtnEliminar() {
        return btnEliminar;
    }

    public JButton getBtnLimpiar() {
        return btnLimpiar;
    }

    public JTable getTablaClientes() {
        return tablaClientes;
    }

    public JTextField getTxtBuscarId() {
        return txtBuscarId;
    }
}

