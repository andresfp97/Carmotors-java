package com.carmotors.view;

import com.carmotors.model.EvaluacionProveedor;
import com.carmotors.model.Proveedor;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

public class PanelEvaluacionProveedor extends JPanel {
    private JDatePickerImpl datePickerFechaEvaluacion;
    private JTextField txtpuntualidad, txtCalidadProducto, txtCosto, txtObservaciones;
    private JComboBox<ProveedorCombo> cbProveedores;
    private JButton btnGuardarEvaluacion;

    public PanelEvaluacionProveedor() {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 245)); // Fondo gris claro
        setBorder(new EmptyBorder(15, 15, 15, 15)); // Márgenes alrededor del panel
        initComponents();
        configurarComboproveedores();
    }

    public JComboBox<ProveedorCombo> getCbProveedores() {
        return cbProveedores;
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
        gbc.anchor = GridBagConstraints.WEST;

        JLabel lblProveedor = createStyledLabel("Proveedor:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(lblProveedor, gbc);
        cbProveedores = createStyledComboBox();
        gbc.gridx = 1;
        gbc.gridy = 0;
        formPanel.add(cbProveedores, gbc);

        JLabel lblFechaEvaluacion = createStyledLabel("Fecha de Evaluación:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(lblFechaEvaluacion, gbc);
        UtilDateModel model = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Hoy");
        p.put("text.month", "Mes");
        p.put("text.year", "Año");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        datePickerFechaEvaluacion = new JDatePickerImpl(datePanel, new DateLabelFormatter());
        JPanel datePanelWrapper = new JPanel(new BorderLayout());
        datePanelWrapper.setBackground(Color.WHITE);
        datePanelWrapper.add(datePickerFechaEvaluacion, BorderLayout.WEST);
        datePickerFechaEvaluacion.getJFormattedTextField().setBorder(createTextFieldBorder());
        gbc.gridx = 1;
        gbc.gridy = 1;
        formPanel.add(datePanelWrapper, gbc);

        JLabel lblPuntualidad = createStyledLabel("Puntualidad:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(lblPuntualidad, gbc);
        txtpuntualidad = createStyledTextField();
        gbc.gridx = 1;
        gbc.gridy = 2;
        formPanel.add(txtpuntualidad, gbc);

        JLabel lblCalidadProducto = createStyledLabel("Calidad del Producto:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(lblCalidadProducto, gbc);
        txtCalidadProducto = createStyledTextField();
        gbc.gridx = 1;
        gbc.gridy = 3;
        formPanel.add(txtCalidadProducto, gbc);

        JLabel lblCosto = createStyledLabel("Porcentaje de Costo:");
        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(lblCosto, gbc);
        txtCosto = createStyledTextField();
        gbc.gridx = 1;
        gbc.gridy = 4;
        formPanel.add(txtCosto, gbc);

        JLabel lblObservaciones = createStyledLabel("Observaciones:");
        gbc.gridx = 0;
        gbc.gridy = 5;
        formPanel.add(lblObservaciones, gbc);
        txtObservaciones = createStyledTextField();
        gbc.gridx = 1;
        gbc.gridy = 5;
        formPanel.add(txtObservaciones, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.WHITE);
        btnGuardarEvaluacion = createStyledButton("Guardar Evaluación", new Color(76, 175, 80)); // Verde
        buttonPanel.add(btnGuardarEvaluacion);

        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
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
        textField.setBorder(createTextFieldBorder());
        return textField;
    }

    private CompoundBorder createTextFieldBorder() {
        return new CompoundBorder(
                new LineBorder(new Color(200, 200, 200)),
                new EmptyBorder(8, 10, 8, 10)
        );
    }

    private JComboBox<ProveedorCombo> createStyledComboBox() {
        JComboBox<ProveedorCombo> comboBox = new JComboBox<>();
        comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        comboBox.setBorder(createTextFieldBorder());
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

    private void configurarComboproveedores() {
        cbProveedores.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value,
                                                          int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof ProveedorCombo) {
                    ProveedorCombo cc = (ProveedorCombo) value;
                    setText(cc.toString());
                }
                return this;
            }
        });

        cbProveedores.addActionListener(e -> {
            ProveedorCombo seleccionado = (ProveedorCombo) cbProveedores.getSelectedItem();
            System.out.println("Proveedor seleccionado: " + (seleccionado != null ? seleccionado.getId() : "null"));
        });
    }

    private class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {
        private final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");

        @Override
        public Object stringToValue(String text) throws ParseException {
            if (text == null || text.trim().isEmpty()) {
                return null;
            }
            try {
                return dateFormatter.parse(text);
            } catch (ParseException e) {
                return null;
            }
        }

        @Override
        public String valueToString(Object value) {
            try {
                if (value == null) {
                    return "";
                }

                if (value instanceof Date) {
                    return dateFormatter.format((Date) value);
                } else if (value instanceof Calendar) {
                    return dateFormatter.format(((Calendar) value).getTime());
                }
                return "";
            } catch (Exception e) {
                return "";
            }
        }
    }

    // Clase interna para manejar el combo de Proveedores
    private static class ProveedorCombo {
        private final Integer id;
        private final String displayText;

        public ProveedorCombo(Integer id, String displayText) {
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
        cbProveedores.setSelectedIndex(-1);
        datePickerFechaEvaluacion.getModel().setValue(null);
        datePickerFechaEvaluacion.getModel().setSelected(false);
        datePickerFechaEvaluacion.getJFormattedTextField().setText("");
        txtpuntualidad.setText("");
        txtCalidadProducto.setText("");
        txtCosto.setText("");
        txtObservaciones.setText("");
    }

    public EvaluacionProveedor getDatosFormulario() {
        EvaluacionProveedor evaluacionProveedor = new EvaluacionProveedor();

        // Obtener proveedor seleccionado
        ProveedorCombo proveedorCombo = (ProveedorCombo) cbProveedores.getSelectedItem();
        if (proveedorCombo != null && proveedorCombo.getId() != null) {
            Proveedor proveedor = new Proveedor();
            proveedor.setId(proveedorCombo.getId());
            evaluacionProveedor.setProveedor(proveedor);
        }

        // Fecha
        if (datePickerFechaEvaluacion.getModel().isSelected()) {
            Date fecha = new Date(
                    datePickerFechaEvaluacion.getModel().getYear() - 1900,
                    datePickerFechaEvaluacion.getModel().getMonth(),
                    datePickerFechaEvaluacion.getModel().getDay()
            );
            evaluacionProveedor.setFechaEvaluacion(fecha);
        }

        try {
            evaluacionProveedor.setPuntualidad(parseInt(txtpuntualidad.getText()));
            evaluacionProveedor.setCalidadProductos(parseInt(txtCalidadProducto.getText()));
            evaluacionProveedor.setCosto(parseInt(txtCosto.getText()));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Los campos de puntuación deben ser números");
        }

        evaluacionProveedor.setObservaciones(txtObservaciones.getText());

        return evaluacionProveedor;
    }

    private int parseInt(String text) {
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public void setGuardarListener(ActionListener listener) {
        btnGuardarEvaluacion.addActionListener(listener);
    }

    public void cargarProveedor(java.util.List<Proveedor> proveedores) {
        DefaultComboBoxModel<ProveedorCombo> model = new DefaultComboBoxModel<>();

        // Opción por defecto
        model.addElement(new ProveedorCombo(null, "-- Seleccione un proveedor --"));

        if (proveedores != null && !proveedores.isEmpty()) {
            for (Proveedor proveedor : proveedores) {
                String displayText = String.format("%s (%s)", proveedor.getNombre(), proveedor.getId());
                model.addElement(new ProveedorCombo(proveedor.getId(), displayText));
            }
        } else {
            System.out.println("[WARNING] Lista de proveedores vacía o nula");
        }

        // Actualización segura en el hilo de eventos
        SwingUtilities.invokeLater(() -> {
            cbProveedores.setModel(model);
            System.out.println("[DEBUG] ComboBox actualizado con " + model.getSize() + " elementos");

            // Seleccionar el primer elemento si existe
            if (model.getSize() > 1) {
                cbProveedores.setSelectedIndex(1); // Saltar la opción por defecto
            }

            cbProveedores.revalidate();
            cbProveedores.repaint();
        });
    }

    public void setDatosFormulario(EvaluacionProveedor evaluacionProveedor) {
        // Buscar y seleccionar el proveedor en el JComboBox
        if (evaluacionProveedor.getProveedor() != null) {
            DefaultComboBoxModel<ProveedorCombo> model = (DefaultComboBoxModel<ProveedorCombo>) cbProveedores.getModel();
            for (int i = 0; i < model.getSize(); i++) {
                ProveedorCombo combo = model.getElementAt(i);
                if (combo.getId() != null && combo.getId().equals(evaluacionProveedor.getProveedor().getId())) {
                    cbProveedores.setSelectedIndex(i);
                    break;
                }
            }
        }

        // Establecer la fecha en el JDatePicker
        if (evaluacionProveedor.getFechaEvaluacion() != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(evaluacionProveedor.getFechaEvaluacion());
            datePickerFechaEvaluacion.getModel().setDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
            datePickerFechaEvaluacion.getModel().setSelected(true);
        } else {
            datePickerFechaEvaluacion.getModel().setValue(null);
            datePickerFechaEvaluacion.getModel().setSelected(false);
            datePickerFechaEvaluacion.getJFormattedTextField().setText("");
        }

        txtpuntualidad.setText(String.valueOf(evaluacionProveedor.getPuntualidad()));
        txtCalidadProducto.setText(String.valueOf(evaluacionProveedor.getCalidadProductos()));
        txtCosto.setText(String.valueOf(evaluacionProveedor.getCosto()));
        txtObservaciones.setText(evaluacionProveedor.getObservaciones());
    }
}