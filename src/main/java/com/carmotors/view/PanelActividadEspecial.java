package com.carmotors.view;


import com.carmotors.controller.ActividadEspecialController;
import com.carmotors.model.ActividadEspecial;
import com.carmotors.model.ClienteActividad;
import com.carmotors.model.enums.ResultadoActividad;
import com.carmotors.model.enums.TipoActividadEspecial;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;

import org.jdatepicker.impl.UtilDateModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Properties;


import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PanelActividadEspecial extends JPanel {

    private JTable table;
    private DefaultTableModel tableModel;
    private JComboBox<String> txtTipo;
    private JComboBox<ResultadoActividad> cbResultado;
    private JTextField txtDescripcion, txtIdCliente, txtIdActividad, txtBuscarActividad;
    private JDatePickerImpl datePickerFechaInicio, datePickerFechaFin;
    private JTabbedPane tabbedPane;
    private ActividadEspecialController actividadEspecialController;
    private JButton btnGuardarActividad, btnGuardarClienteActividad, btnBuscarActividad, btnEliminarActividad;
    public PanelActividadEspecial() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(240, 240, 240));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        initComponents();
    }

    public void inicializarTabla() {
        tableModel = new DefaultTableModel(
                new Object[]{"ID", "Tipo Actividad", "Descripción", "Fecha Inicio", "Fecha Fin"},
                0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer que la tabla no sea editable
            }
        };

        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Configurar scroll pane
        JScrollPane scrollPane = new JScrollPane(table);
        this.add(scrollPane);
    }


    private void initComponents() {


        tableModel = new DefaultTableModel(
                new Object[][]{},
                new String[]{"ID", "Tipo", "Descripción", "Fecha Inicio", "Fecha Fin"}
        );
        // Crear la tabla con el modelo
        table = new JTable(tableModel);


        // Configuración principal
        setLayout(new BorderLayout(5, 5));
        setBackground(new Color(240, 240, 240));

        // Panel principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(240, 240, 240));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        // ===== 1. FORMULARIO DE ACTIVIDAD =====
        JPanel activityPanel = new JPanel();
        activityPanel.setLayout(new BoxLayout(activityPanel, BoxLayout.Y_AXIS));
        activityPanel.setBackground(Color.WHITE);
        activityPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));

        // Formulario compacto
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        formPanel.setBackground(Color.WHITE);
        formPanel.setMaximumSize(new Dimension(400, 120));

        addCompactFormRow(formPanel, "Tipo Actividad:", txtTipo = createCompactComboBox(new String[]{"Campaña", "Inspeccion"}));
        addCompactFormRow(formPanel, "Descripción:", txtDescripcion = createCompactTextField(15));
        addCompactFormRow(formPanel, "Fecha Inicio:", createCompactDatePanel(datePickerFechaInicio = createDatePicker()));
        addCompactFormRow(formPanel, "Fecha Fin:", createCompactDatePanel(datePickerFechaFin = createDatePicker()));

        activityPanel.add(formPanel);
        activityPanel.add(Box.createRigidArea(new Dimension(0, 8)));

        btnGuardarActividad = createCompactButton("Guardar Actividad", new Color(76, 175, 80));
        btnGuardarActividad.setAlignmentX(Component.CENTER_ALIGNMENT);
        activityPanel.add(btnGuardarActividad);

        // ===== 2. PANEL DE BÚSQUEDA (ALTURA REDUCIDA) =====
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.Y_AXIS));
        searchPanel.setBackground(Color.WHITE);
        searchPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));
        searchPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80)); // Altura reducida

        // Barra de búsqueda compacta
        JPanel searchBarPanel = new JPanel(new BorderLayout(5, 5));
        searchBarPanel.setBackground(Color.WHITE);
         txtBuscarActividad = createCompactTextField(15); // Campo más pequeño
        btnBuscarActividad = createCompactButton("Buscar", new Color(33, 150, 243));
        btnBuscarActividad.setPreferredSize(new Dimension(80, 25)); // Botón más compacto

        searchBarPanel.add(txtBuscarActividad, BorderLayout.CENTER);
        searchBarPanel.add(btnBuscarActividad, BorderLayout.EAST);
        searchPanel.add(searchBarPanel);
        searchPanel.add(Box.createRigidArea(new Dimension(0, 8)));

        // Solo un botón Eliminar (se quitó el Buscar duplicado)
        btnEliminarActividad = createCompactButton("Eliminar", new Color(244, 67, 54));
        btnEliminarActividad.setAlignmentX(Component.CENTER_ALIGNMENT);
        searchPanel.add(btnEliminarActividad);

        // ===== 3. PANEL DE PESTAÑAS (CON MAYOR ALTURA) =====
        tabbedPane = new JTabbedPane();
        tabbedPane.setBackground(Color.WHITE);
        tabbedPane.setPreferredSize(new Dimension(600, 250)); // Aumentada la altura




        // Pestaña 1: Asociar Cliente
        JPanel associationPanel = new JPanel();
        associationPanel.setLayout(new BoxLayout(associationPanel, BoxLayout.Y_AXIS));
        associationPanel.setBackground(Color.WHITE);
        associationPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        JPanel associationForm = new JPanel(new GridLayout(3, 2, 5, 5));
        associationForm.setBackground(Color.WHITE);
        associationForm.setMaximumSize(new Dimension(300, 90));

        addCompactFormRow(associationForm, "ID Cliente:", txtIdCliente = createCompactTextField(10));
        addCompactFormRow(associationForm, "ID Actividad:", txtIdActividad = createCompactTextField(10));
        addCompactFormRow(associationForm, "Resultado:", cbResultado = createCompactResultadoComboBox());

        associationPanel.add(associationForm);
        associationPanel.add(Box.createRigidArea(new Dimension(0, 8)));

        btnGuardarClienteActividad = createCompactButton("Guardar Asociación", new Color(255, 152, 0));
        btnGuardarClienteActividad.setAlignmentX(Component.CENTER_ALIGNMENT);
        associationPanel.add(btnGuardarClienteActividad);

        tabbedPane.addTab("Asociar Cliente", associationPanel);

        // Pestaña 2: Ver Actividades
        table = new JTable(new Object[][]{}, new String[]{"ID", "Tipo", "Descripción", "Fecha Inicio", "Fecha Fin"});
        table.setRowHeight(20);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        JScrollPane tableScroll = new JScrollPane(table);
        tabbedPane.addTab("Ver Actividades", tableScroll);

        // Organización final
        mainPanel.add(activityPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        mainPanel.add(searchPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        mainPanel.add(tabbedPane);

        add(mainPanel, BorderLayout.CENTER);
    }

    // ===== MÉTODOS AUXILIARES COMPACTOS =====
    private void addCompactFormRow(JPanel panel, String labelText, JComponent component) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        panel.add(label);
        panel.add(component);
    }

    private JTextField createCompactTextField(int columns) {
        JTextField textField = new JTextField(columns);
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        textField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
        textField.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180)));
        return textField;
    }

    private JComboBox<String> createCompactComboBox(String[] items) {
        JComboBox<String> comboBox = new JComboBox<>(items);
        comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        comboBox.setBackground(Color.WHITE);
        comboBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
        return comboBox;
    }

    private JComboBox<ResultadoActividad> createCompactResultadoComboBox() {
        JComboBox<ResultadoActividad> comboBox = new JComboBox<>(ResultadoActividad.values());
        comboBox.setRenderer(new DefaultListCellRenderer() {
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
        comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        comboBox.setBackground(Color.WHITE);
        comboBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
        return comboBox;
    }

    private JButton createCompactButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(4, 12, 4, 12));
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
        return button;
    }

    private JPanel createCompactDatePanel(JDatePickerImpl datePicker) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.add(datePicker, BorderLayout.WEST);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
        return panel;
    }

    // ===== MÉTODOS AUXILIARES (MANTENIENDO LA LÓGICA ORIGINAL) =====


    private JComboBox<String> createComboBox(String[] items) {
        JComboBox<String> comboBox = new JComboBox<>(items);
        comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        comboBox.setBackground(Color.WHITE);
        return comboBox;
    }

    private JDatePickerImpl createDatePicker() {
        UtilDateModel model = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Hoy");
        p.put("text.month", "Mes");
        p.put("text.year", "Año");

        return new JDatePickerImpl(new JDatePanelImpl(model, p), new DateLabelFormatter());
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
    public ActividadEspecial getDatosFormularioActividad() {
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
    public ClienteActividad getDatosFormularioClienteActividad() {
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
    public void limpiarFormularioActividad() {

        txtTipo.setSelectedIndex(0);
        txtDescripcion.setText("");

        datePickerFechaInicio.getModel().setValue(null);
        datePickerFechaInicio.getModel().setSelected(false);
        datePickerFechaInicio.getJFormattedTextField().setText("");

        datePickerFechaFin.getModel().setValue(null);
        datePickerFechaFin.getModel().setSelected(false);
        datePickerFechaFin.getJFormattedTextField().setText("");
    }
    public void limpiarFormularioClienteActividad() {
        txtIdCliente.setText("");
        txtIdActividad.setText("");
        cbResultado.setSelectedIndex(0);
    }
    public void setGuardarListenerActividad(java.awt.event.ActionListener listener) {

        if (btnGuardarActividad == null) {
            throw new IllegalStateException("El botón btnGuardarActividad no ha sido inicializado. ¿Se llamó a initComponents()?");
        }
        btnGuardarActividad.addActionListener(listener);
    }
    public void setGuardarListenerClienteActividad(java.awt.event.ActionListener listener) {
        btnGuardarClienteActividad.addActionListener(listener);
    }
    public void setDatosFormularioActividad(ActividadEspecial actividadEspecial) {

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
    public void setDatosFormularioClienteActividad(ClienteActividad clienteActividad) {
        if (clienteActividad != null) {
            txtIdCliente.setText(clienteActividad.getIdCliente() != null ?
                    clienteActividad.getIdCliente().toString() : "");
            txtIdActividad.setText(clienteActividad.getIdActividad() != null ?
                    clienteActividad.getIdActividad().toString() : "");
            cbResultado.setSelectedItem(clienteActividad.getResultadoActividad());
        }
    }

    // Métodos para obtener datos
    public String getCriterioBusqueda() {
        return txtBuscarActividad.getText();
    }

    public JTable getTable() {
        return table; // Asegúrate de que 'table' sea accesible
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    // Métodos para configurar listeners
    public void setBuscarListener(ActionListener listener) {
        btnBuscarActividad.addActionListener(listener);
    }

    public void setEliminarListener(ActionListener listener) {
        btnEliminarActividad.addActionListener(listener);
    }


}