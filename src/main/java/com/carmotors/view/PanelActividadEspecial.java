package com.carmotors.view;


import com.carmotors.model.ActividadEspecial;
import com.carmotors.model.enums.TipoActividadEspecial;
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

public class PanelActividadEspecial extends JPanel {
    private JComboBox<String> txtTipo;
    private JTextField txtDescripcion;
    private JDatePickerImpl datePickerFechaInicio, datePickerFechaFin;
    private JButton btnGuardarActividad;


    public PanelActividadEspecial() {
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
        panelSuperior.setBorder(createStyledBorder("Registrar Actividad Especial"));

        // Formulario superior
        JPanel formSuperior = new JPanel(new GridBagLayout());
        formSuperior.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Componentes del formulario

        addFormRow(formSuperior, gbc, 0, "Tipo Actividad:", txtTipo = createStyledComboBox(new String[]{"Campaña", "Inspeccion"}));
        addFormRow(formSuperior, gbc, 1, "Descripcion:", txtDescripcion = createStyledTextField(15));


        // Selector de fechas para Fecha inicio
        UtilDateModel model = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Hoy");
        p.put("text.month", "Mes");
        p.put("text.year", "Año");

        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        datePickerFechaInicio = new JDatePickerImpl(datePanel, new DateLabelFormatter());
        JPanel datePanelWrapper = new JPanel(new BorderLayout());
        datePanelWrapper.setBackground(Color.WHITE);
        datePanelWrapper.add(datePickerFechaInicio, BorderLayout.WEST);
        addFormRow(formSuperior, gbc, 2, "Fecha Inicio:", datePanelWrapper);

        UtilDateModel model2 = new UtilDateModel();
        Properties p2 = new Properties();
        p2.put("text.today", "Hoy");
        p2.put("text.month", "Mes");
        p2.put("text.year", "Año");

        JDatePanelImpl datePanel2 = new JDatePanelImpl(model2, p2);
        datePickerFechaFin = new JDatePickerImpl(datePanel2, new DateLabelFormatter());
        JPanel datePanelWrapper2 = new JPanel(new BorderLayout());
        datePanelWrapper2.setBackground(Color.WHITE);
        datePanelWrapper2.add(datePickerFechaFin, BorderLayout.WEST);
        addFormRow(formSuperior, gbc, 3, "Fecha Fin:", datePanelWrapper2);

        panelSuperior.add(formSuperior);

        // Botón Guardar dentro del panel superior
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.WHITE);
        btnGuardarActividad = createStyledButton("Crear Actividad:", new Color(76, 175, 80));
        buttonPanel.add(btnGuardarActividad);

        panelSuperior.add(Box.createRigidArea(new Dimension(0, 10)));
        panelSuperior.add(buttonPanel);

        add(panelSuperior);
    }


    // Métodos auxiliares para creación de componentes
    private JTextField createStyledTextField(int columns) {
        JTextField field = new JTextField(columns);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180)),
                BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        return field;
    }

    private JComboBox<String> createStyledComboBox(String[] items) {
        JComboBox<String> combo = new JComboBox<>(items);
        combo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        combo.setBackground(Color.WHITE);
        combo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180)),
                BorderFactory.createEmptyBorder(2, 8, 2, 8)
        ));
        return combo;
    }

    private JFormattedTextField createDateField() {
        JFormattedTextField field = new JFormattedTextField(new SimpleDateFormat("dd/MM/yyyy"));
        field.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        field.setColumns(10);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180)),
                BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
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
                new Color(70, 70, 70)
        );
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

    public ActividadEspecial getDatosFormulario() {
        ActividadEspecial r = new ActividadEspecial();

        String tipoStr = (String) txtTipo.getSelectedItem();
        TipoActividadEspecial tipoActividadEspecial = TipoActividadEspecial.valueOf(tipoStr.toUpperCase().replace(" ", "_"));
        r.setTipoActividad(tipoActividadEspecial);

        r.setDescripcion(txtDescripcion.getText());

        if (datePickerFechaInicio.getModel().isSelected()) {
            LocalDate localDateFechaInicio = LocalDate.of(
                    datePickerFechaInicio.getModel().getYear(),
                    datePickerFechaInicio.getModel().getMonth() + 1,
                    datePickerFechaInicio.getModel().getDay()
            );
            r.setFechaInicio(java.sql.Date.valueOf(localDateFechaInicio));
        } else {
            r.setFechaInicio(null);
        }

        if (datePickerFechaFin.getModel().isSelected()) {
            LocalDate localDateFechaFin = LocalDate.of(
                    datePickerFechaFin.getModel().getYear(),
                    datePickerFechaFin.getModel().getMonth() + 1,
                    datePickerFechaFin.getModel().getDay()
            );
            r.setFechaFin(java.sql.Date.valueOf(localDateFechaFin));
        } else {
            r.setFechaFin(null);
        }

        return r;
    }

    public void limpiarFormulario() {

        txtTipo.setSelectedIndex(0);
        txtDescripcion.setText("");

        datePickerFechaInicio.getModel().setValue(null);
        datePickerFechaInicio.getModel().setSelected(false);
        datePickerFechaInicio.getJFormattedTextField().setText("");

        datePickerFechaFin.getModel().setValue(null);
        datePickerFechaFin.getModel().setSelected(false);
        datePickerFechaFin.getJFormattedTextField().setText("");
    }
    public void setGuardarListener(java.awt.event.ActionListener listener) {

        if (btnGuardarActividad == null) {
            throw new IllegalStateException("El botón btnGuardarActividad no ha sido inicializado. ¿Se llamó a initComponents()?");
        }
        btnGuardarActividad.addActionListener(listener);
    }

    public void setDatosFormulario(ActividadEspecial actividadEspecial) {

        txtTipo.setSelectedItem(actividadEspecial.getTipoActividad().toString().toLowerCase());
        txtDescripcion.setText(actividadEspecial.getDescripcion());

        // Manejo seguro de la fecha
        if (actividadEspecial.getFechaInicio() != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(actividadEspecial.getFechaInicio());
            datePickerFechaInicio.getModel().setDate(
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)
            );
            datePickerFechaInicio.getModel().setSelected(true);
        } else {
            datePickerFechaInicio.getModel().setValue(null);
        }

        if (actividadEspecial.getFechaFin() != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(actividadEspecial.getFechaInicio());
            datePickerFechaFin.getModel().setDate(
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)
            );
            datePickerFechaFin.getModel().setSelected(true);
        } else {
            datePickerFechaFin.getModel().setValue(null);
        }
    }
}