package com.carmotors.view;

import com.carmotors.model.enums.EstadoLote;
import com.carmotors.model.Lote;
import com.carmotors.model.Proveedor;
import com.carmotors.model.Repuesto;
import com.carmotors.model.enums.TipoRepuesto;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;

import org.jdatepicker.impl.UtilDateModel;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Properties;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PanelRepuesto extends JPanel {
    private JTextField txtNombre, txtMarca, txtModelo, txtIdRepuesto, txtIdProveedor,
            txtCatidadInicial, txtCatidadDisponible, txtPrecio;
    private JButton btnGuardar, btnGuardarLote;
    private JComboBox<String> txtEstado, txtTipo;
    private JDatePickerImpl datePickerVidaUtil, datePickerFechaIngreso;

    public PanelRepuesto() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(240, 240, 240));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        initComponents();
    }

    private void initComponents() {
        // ===== PANEL SUPERIOR =====
        JPanel panelSuperior = new JPanel();
        panelSuperior.setLayout(new BoxLayout(panelSuperior, BoxLayout.Y_AXIS));
        panelSuperior.setBackground(new Color(255, 255, 255));
        panelSuperior.setBorder(createStyledBorder("Registrar Repuesto"));

        // Formulario superior
        JPanel formSuperior = new JPanel(new GridBagLayout());
        formSuperior.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Componentes del formulario
        addFormRow(formSuperior, gbc, 0, "Nombre:", txtNombre = createStyledTextField(15));
        addFormRow(formSuperior, gbc, 1, "Tipo:",
                txtTipo = createStyledComboBox(new String[] { "Mecanico", "Electrico", "Carroceria", "Consumo" }));
        addFormRow(formSuperior, gbc, 2, "Marca:", txtMarca = createStyledTextField(15));
        addFormRow(formSuperior, gbc, 3, "Modelo Compatible:", txtModelo = createStyledTextField(15));
        addFormRow(formSuperior, gbc, 4, "Precio:", txtPrecio = createStyledTextField(15));

        // Selector de fechas para Vida Útil Estimada
        UtilDateModel model = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Hoy");
        p.put("text.month", "Mes");
        p.put("text.year", "Año");

        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        datePickerVidaUtil = new JDatePickerImpl(datePanel, new DateLabelFormatter());
        JPanel datePanelWrapper = new JPanel(new BorderLayout());
        datePanelWrapper.setBackground(Color.WHITE);
        datePanelWrapper.add(datePickerVidaUtil, BorderLayout.WEST);
        addFormRow(formSuperior, gbc, 5, "Vida Útil Estimada:", datePanelWrapper);

        panelSuperior.add(formSuperior);

        // Botón Guardar dentro del panel superior
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.WHITE);
        btnGuardar = createStyledButton("Guardar Repuesto", new Color(76, 175, 80));
        buttonPanel.add(btnGuardar);

        panelSuperior.add(Box.createRigidArea(new Dimension(0, 10)));
        panelSuperior.add(buttonPanel);

        add(panelSuperior);

        // ===== PANEL INFERIOR =====
        JPanel panelInferior = new JPanel();
        panelInferior.setLayout(new BoxLayout(panelInferior, BoxLayout.Y_AXIS));
        panelInferior.setBackground(new Color(255, 255, 255));
        panelInferior.setBorder(createStyledBorder("Registro de Lote"));

        // Formulario inferior
        JPanel formInferior = new JPanel(new GridBagLayout());
        formInferior.setBackground(Color.WHITE);

        addFormRow(formInferior, gbc, 0, "ID Repuesto:", txtIdRepuesto = createStyledTextField(15));
        addFormRow(formInferior, gbc, 1, "ID Proveedor:", txtIdProveedor = createStyledTextField(15));

        UtilDateModel model2 = new UtilDateModel();
        Properties p2 = new Properties();
        p2.put("text.today", "Hoy");
        p2.put("text.month", "Mes");
        p2.put("text.year", "Año");

        JDatePanelImpl datePanel2 = new JDatePanelImpl(model2, p2);
        datePickerFechaIngreso = new JDatePickerImpl(datePanel2, new DateLabelFormatter());
        JPanel datePanelWrapper2 = new JPanel(new BorderLayout());
        datePanelWrapper2.setBackground(Color.WHITE);
        datePanelWrapper2.add(datePickerFechaIngreso, BorderLayout.WEST);

        addFormRow(formInferior, gbc, 2, "Fecha Ingreso:", datePanelWrapper2);
        addFormRow(formInferior, gbc, 3, "Cantidad Inicial:", txtCatidadInicial = createStyledTextField(15));
        addFormRow(formInferior, gbc, 4, "Cantidad Disponible:", txtCatidadDisponible = createStyledTextField(15));
        addFormRow(formInferior, gbc, 5, "Estado:",
                txtEstado = createStyledComboBox(new String[] { "Disponible", "Reservado", "Fuera de servicio" }));

        panelInferior.add(formInferior);

        // Botón Guardar Lote
        JPanel buttonPanelLote = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanelLote.setBackground(Color.WHITE);
        btnGuardarLote = createStyledButton("Guardar Lote", new Color(33, 150, 243));
        buttonPanelLote.add(btnGuardarLote);

        panelInferior.add(Box.createRigidArea(new Dimension(0, 10)));
        panelInferior.add(buttonPanelLote);

        add(Box.createRigidArea(new Dimension(0, 15)));
        add(panelInferior);
    }

    // Métodos auxiliares para creación de componentes
    private JTextField createStyledTextField(int columns) {
        JTextField field = new JTextField(columns);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180)),
                BorderFactory.createEmptyBorder(5, 8, 5, 8)));
        return field;
    }

    private JComboBox<String> createStyledComboBox(String[] items) {
        JComboBox<String> combo = new JComboBox<>(items);
        combo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        combo.setBackground(Color.WHITE);
        combo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180)),
                BorderFactory.createEmptyBorder(2, 8, 2, 8)));
        return combo;
    }

    private JFormattedTextField createDateField() {
        JFormattedTextField field = new JFormattedTextField(new SimpleDateFormat("dd/MM/yyyy"));
        field.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        field.setColumns(10);
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
        TitledBorder border = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180)),
                title,
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 13),
                new Color(70, 70, 70));
        return border;
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

    // Clase para formatear la fecha del DatePicker
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

    // Métodos para obtener datos del formulario
    public Repuesto getDatosFormulario() {
        Repuesto r = new Repuesto();
        r.setNombre(txtNombre.getText());

        String tipoStr = (String) txtTipo.getSelectedItem();
        TipoRepuesto tipoRepuesto = TipoRepuesto.valueOf(tipoStr.toUpperCase().replace(" ", "_"));
        r.setTipo(tipoRepuesto);

        r.setMarca(txtMarca.getText());
        r.setModeloCompatible(txtModelo.getText());

        try {
            r.setPrecio(Integer.parseInt(txtPrecio.getText()));
        } catch (NumberFormatException e) {
            r.setPrecio(0); // Valor por defecto si no se puede parsear
        }

        if (datePickerVidaUtil.getModel().isSelected()) {
            LocalDate localDate = LocalDate.of(
                    datePickerVidaUtil.getModel().getYear(),
                    datePickerVidaUtil.getModel().getMonth() + 1,
                    datePickerVidaUtil.getModel().getDay());
            r.setVidaUtilEstimada(java.sql.Date.valueOf(localDate));
        } else {
            r.setVidaUtilEstimada(null);
        }

        return r;
    }

    public Lote getDatosFormularioLote() {
        Lote r = new Lote();
        Repuesto repuesto = new Repuesto();
        Proveedor proveedor = new Proveedor();

        repuesto.setId(Integer.valueOf(txtIdRepuesto.getText()));
        proveedor.setId(Integer.valueOf(txtIdProveedor.getText()));
        r.setIdrepuesto(repuesto);
        r.setIdproveedor(proveedor);

        if (datePickerFechaIngreso.getModel().isSelected()) {
            Calendar cal = Calendar.getInstance();
            cal.set(datePickerFechaIngreso.getModel().getYear(),
                    datePickerFechaIngreso.getModel().getMonth(),
                    datePickerFechaIngreso.getModel().getDay());
            r.setFechaIngreso(cal.getTime());
        } else {
            r.setFechaIngreso(null);
        }

        r.setCantidadInicial(Integer.valueOf(txtCatidadInicial.getText()));
        r.setCantidadDisponible(Integer.valueOf(txtCatidadDisponible.getText()));

        String estadoStr = (String) txtEstado.getSelectedItem();
        EstadoLote estado = EstadoLote.valueOf(estadoStr.toUpperCase().replace(" ", "_"));
        r.setEstado(estado);

        return r;
    }

    public void limpiarFormulario() {
        // Limpiar formulario superior
        txtNombre.setText("");
        txtTipo.setSelectedIndex(0);
        txtMarca.setText("");
        txtModelo.setText("");
        txtPrecio.setText("");

        // Limpiar el datePicker correctamente
        datePickerVidaUtil.getModel().setValue(null);
        datePickerVidaUtil.getModel().setSelected(false);
        datePickerVidaUtil.getJFormattedTextField().setText("");

        datePickerFechaIngreso.getModel().setValue(null);
        datePickerFechaIngreso.getModel().setSelected(false);
        datePickerFechaIngreso.getJFormattedTextField().setText("");

        // Limpiar formulario inferior
        txtIdRepuesto.setText("");
        txtIdProveedor.setText("");
        txtCatidadInicial.setText("");
        txtCatidadDisponible.setText("");
        txtEstado.setSelectedIndex(0);
    }

    public void setGuardarListener(java.awt.event.ActionListener listener) {
        btnGuardar.addActionListener(listener);
    }

    public void setGuardarListenerLote(java.awt.event.ActionListener listener) {
        btnGuardarLote.addActionListener(listener);
    }

    public void setDatosFormulario(Repuesto repuesto) {
        txtNombre.setText(repuesto.getNombre());
        txtTipo.setSelectedItem(repuesto.getTipo().toString().toLowerCase());
        txtMarca.setText(repuesto.getMarca());
        txtModelo.setText(repuesto.getModeloCompatible());
        txtPrecio.setText(String.valueOf(repuesto.getPrecio()));

        // Manejo seguro de la fecha
        if (repuesto.getVidaUtilEstimada() != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(repuesto.getVidaUtilEstimada());
            datePickerVidaUtil.getModel().setDate(
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH));
            datePickerVidaUtil.getModel().setSelected(true);
        } else {
            datePickerVidaUtil.getModel().setValue(null);
        }
    }

    public void setDatosFormularioLote(Lote lote) {
        txtIdRepuesto.setText(String.valueOf(lote.getIdrepuesto()));
        txtIdProveedor.setText(String.valueOf(lote.getIdproveedor()));

        if (lote.getFechaIngreso() != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(lote.getFechaIngreso());
            datePickerFechaIngreso.getModel().setDate(
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH));
            datePickerFechaIngreso.getModel().setSelected(true);
        } else {
            datePickerFechaIngreso.getModel().setValue(null);
        }

        txtCatidadInicial.setText(String.valueOf(lote.getCantidadInicial()));
        txtCatidadDisponible.setText(String.valueOf(lote.getCantidadDisponible()));
        txtEstado.setSelectedItem(lote.getEstado().toString().toLowerCase().replace("_", " "));
    }

}