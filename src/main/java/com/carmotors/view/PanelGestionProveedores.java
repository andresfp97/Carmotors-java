package com.carmotors.view;

import com.carmotors.model.EvaluacionProveedor;
import com.carmotors.model.Proveedor;
import com.carmotors.modelDAO.EvaluacionProveedorDAO;
import com.carmotors.modelDAO.LoteDAO;
import com.carmotors.modelDAO.ProveedorDAO;
import com.carmotors.modelDAO.RepuestoDAO;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.List;

import com.carmotors.model.enums.FrecuenciaVisitas;

public class PanelGestionProveedores extends JPanel {

    private JTextField txtNombre, txtNit, txtContacto, txtBuscarProveedor, txtpuntualidad, txtCalidadProducto, txtCosto,
            txtObservaciones, txtBuscarEvaluacion, txtFrecuencia;
    private JButton btnGuardar, btnBuscarProveedor,btnEliminarProveedor, btnLimpiarBusquedaProveedor,  btnGuardarEvaluacion, btnBuscarEvaluacion,
            btnEliminarEvaluacion, btnLimpiarBusquedaEvaluacion;
    private JTable tablaProveedor, tablaEvaluacion ;
    private DefaultTableModel modeloTablaProveedor, modeloTablaEvaluaciones;
    private JDatePickerImpl datePickerFechaEvaluacion;
    private JComboBox<PanelGestionProveedores.ProveedorCombo> cbProveedores;
    private ProveedorDAO proveedorDAO = new ProveedorDAO();
    private EvaluacionProveedorDAO evaluacionProveedorDAO = new EvaluacionProveedorDAO();
    

    public PanelGestionProveedores() {
        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 240));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        initComponents();
        initListeners();
        cargarTablaProveedores();
        cargarTablaEvaluaciones();
    }

    private void cargarTablaProveedores() {
        modeloTablaProveedor.setRowCount(0);
        for (Proveedor p : proveedorDAO.obtenerTodos()) {
            modeloTablaProveedor.addRow(new Object[]{p.getId(), p.getNombre(), p.getNit(), p.getContacto(), p.getFrecuenciaVisitas()});
        }
    }

    public void cargarTablaEvaluaciones() {
        DefaultTableModel model = (DefaultTableModel) tablaEvaluacion.getModel();
        model.setRowCount(0); // Limpiar tabla

        List<EvaluacionProveedor> evaluaciones = evaluacionProveedorDAO.obtenerTodos();

        if (evaluaciones.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "No hay evaluaciones registradas",
                    "Información",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        for (EvaluacionProveedor ev : evaluaciones) {
            model.addRow(new Object[]{
                    ev.getIdEvaluacion(),
                    ev.getProveedor() != null ? ev.getProveedor().getNombre() : "N/A",
                    ev.getFechaEvaluacion() != null ? sdf.format(ev.getFechaEvaluacion()) : "N/A",
                    ev.getPuntualidad() != null ? ev.getPuntualidad() : 0,
                    ev.getCalidadProductos() != null ? ev.getCalidadProductos() : 0,
                    ev.getCosto() != null ? ev.getCosto() : 0,
                    ev.getObservaciones() != null ? ev.getObservaciones() : ""
            });
        }
        tablaEvaluacion.getColumnModel().getColumn(0).setPreferredWidth(80);  // ID
        tablaEvaluacion.getColumnModel().getColumn(1).setPreferredWidth(150); // Proveedor
        tablaEvaluacion.getColumnModel().getColumn(2).setPreferredWidth(100); // Fecha
        tablaEvaluacion.getColumnModel().getColumn(3).setPreferredWidth(80);  // Puntualidad
        tablaEvaluacion.getColumnModel().getColumn(4).setPreferredWidth(80);  // Calidad
        tablaEvaluacion.getColumnModel().getColumn(5).setPreferredWidth(80);  // Costo
        tablaEvaluacion.getColumnModel().getColumn(6).setPreferredWidth(200); // Observaciones
    }

    private String formatDate(Date date) {
        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            return sdf.format(date);
        }
        return "";
    }

    private void initListeners() {

        // Listener para buscar proveedor
        btnBuscarProveedor.addActionListener(e -> {
            String textoBusqueda = txtBuscarProveedor.getText().trim();
            if (!textoBusqueda.isEmpty()) {
                // Asumo que tienes un método buscarPorNombre o similar en ProveedorDAO
                Integer id = Integer.parseInt(textoBusqueda);
                java.util.List<Proveedor> resultados = proveedorDAO.buscarPorId(id);
                modeloTablaProveedor.setRowCount(0);
                for (Proveedor p : resultados) {
                    modeloTablaProveedor.addRow(new Object[]{p.getId(), p.getNombre(), p.getNit(), p.getContacto(), p.getFrecuenciaVisitas()});
                }
                if (resultados.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "No se encontraron proveedores con ese nombre", "No encontrado", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                cargarTablaProveedores(); // Mostrar todos si el campo de búsqueda está vacío
            }
        });

        // Listener para eliminar proveedor
        btnEliminarProveedor.addActionListener(e -> {
            String idTexto = txtBuscarProveedor.getText().trim();
            if (!idTexto.isEmpty()) {
                try {
                    Integer id = Integer.parseInt(idTexto);
                    int confirmacion = JOptionPane.showConfirmDialog(
                            this,
                            "¿Está seguro que desea eliminar este proveedor?",
                            "Confirmar eliminación",
                            JOptionPane.YES_NO_OPTION
                    );
                    if (confirmacion == JOptionPane.YES_OPTION) {
                        String resultadoEliminacion = proveedorDAO.eliminarPorId(id); // Asegúrate de tener este método en ProveedorDAO
                        JOptionPane.showMessageDialog(this, resultadoEliminacion, "Resultado de eliminación", JOptionPane.INFORMATION_MESSAGE);
                        cargarTablaProveedores();
                        txtBuscarProveedor.setText("");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Ingrese un ID válido", "Error de formato", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Ingrese el ID del proveedor a eliminar", "Campo vacío", JOptionPane.WARNING_MESSAGE);
            }
        });

        btnLimpiarBusquedaProveedor.addActionListener(e -> {
            txtBuscarProveedor.setText("");
            cargarTablaProveedores();
        });

        // Listener para buscar evaluación (asumo búsqueda por ID de evaluación)
        btnBuscarEvaluacion.addActionListener(e -> {
            String idTexto = txtBuscarEvaluacion.getText().trim();
            if (!idTexto.isEmpty()) {
                try {
                    Integer id = Integer.parseInt(idTexto);
                    EvaluacionProveedor evaluacion = evaluacionProveedorDAO.buscarPorId(id); // Asegúrate de tener este método en EvaluacionProveedorDAO
                    modeloTablaEvaluaciones.setRowCount(0);
                    if (evaluacion != null) {
                        modeloTablaEvaluaciones.addRow(new Object[]{
                                evaluacion.getIdEvaluacion(),
                                evaluacion.getProveedor().getId(),
                                evaluacion.getFechaEvaluacion(),
                                evaluacion.getPuntualidad(),
                                evaluacion.getCalidadProductos(),
                                evaluacion.getCosto(),
                                evaluacion.getObservaciones()
                        });
                    } else {
                        JOptionPane.showMessageDialog(this, "No se encontró una evaluación con ese ID", "No encontrado", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Ingrese un ID válido", "Error de formato", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                cargarTablaEvaluaciones(); // Mostrar todas las evaluaciones si el campo de búsqueda está vacío
            }
        });

        // Listener para eliminar evaluación
        btnEliminarEvaluacion.addActionListener(e -> {
            String idTexto = txtBuscarEvaluacion.getText().trim();
            if (!idTexto.isEmpty()) {
                try {
                    Integer id = Integer.parseInt(idTexto);
                    int confirmacion = JOptionPane.showConfirmDialog(
                            this,
                            "¿Está seguro que desea eliminar esta evaluación?",
                            "Confirmar eliminación",
                            JOptionPane.YES_NO_OPTION
                    );
                    if (confirmacion == JOptionPane.YES_OPTION) {
                        String resultadoEliminacion = evaluacionProveedorDAO.eliminarPorId(id); // Asegúrate de tener este método en EvaluacionProveedorDAO
                        JOptionPane.showMessageDialog(this, resultadoEliminacion, "Resultado de eliminación", JOptionPane.INFORMATION_MESSAGE);
                        cargarTablaEvaluaciones();
                        txtBuscarEvaluacion.setText("");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Ingrese un ID válido", "Error de formato", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Ingrese el ID de la evaluación a eliminar", "Campo vacío", JOptionPane.WARNING_MESSAGE);
            }
        });

        btnLimpiarBusquedaEvaluacion.addActionListener(e -> {
            txtBuscarEvaluacion.setText("");
            cargarTablaEvaluaciones();
        });
    }

    private void initComponents() {
        JTabbedPane tabbedPane = new JTabbedPane();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // ======================= TAB 1: REGISTRAR PROVEEDOR =======================
        JPanel tabProveedor = new JPanel();
        tabProveedor.setLayout(new BorderLayout());
        tabProveedor.setBackground(new Color(240, 240, 240));

        JPanel panelSuperior = new JPanel();
        panelSuperior.setLayout(new BoxLayout(panelSuperior, BoxLayout.Y_AXIS));
        panelSuperior.setBackground(Color.WHITE);
        panelSuperior.setBorder(createStyledBorder("Registrar Proveedor"));

        JPanel formSuperior = new JPanel(new GridBagLayout());
        formSuperior.setBackground(Color.WHITE);

        addFormRow(formSuperior, gbc, 0, "Nombre:", txtNombre = createStyledTextField(15));
        addFormRow(formSuperior, gbc, 1, "NIT:", txtNit = createStyledTextField(15));
        addFormRow(formSuperior, gbc, 2, "Contacto:", txtContacto= createStyledTextField(15));
        addFormRow(formSuperior, gbc, 3, "Frecuencia visitas:", txtFrecuencia = createStyledTextField(15));

        panelSuperior.add(formSuperior);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.WHITE);
        btnGuardar = createStyledButton("Guardar Proveedor", new Color(76, 175, 80));
        buttonPanel.add(btnGuardar);
        panelSuperior.add(Box.createRigidArea(new Dimension(0, 10)));
        panelSuperior.add(buttonPanel);

        tabProveedor.add(panelSuperior, BorderLayout.NORTH);

        // Sección tabla proveedores
        JPanel paneltablaProveedor = new JPanel(new BorderLayout());
        paneltablaProveedor.setBorder(createStyledBorder("Registros de Proveedores"));
        paneltablaProveedor.setBackground(Color.WHITE);

        JPanel buscarPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buscarPanel.setBackground(Color.WHITE);
        buscarPanel.add(new JLabel("Buscar por ID:"));
        txtBuscarProveedor = createStyledTextField(10);
        buscarPanel.add(txtBuscarProveedor);
        btnBuscarProveedor = createStyledButton("Buscar", new Color(33, 150, 243));
        btnEliminarProveedor = createStyledButton("Eliminar", new Color(244, 67, 54));
        btnLimpiarBusquedaProveedor = createStyledButton("Limpiar busqueda", new Color(54, 243, 101));
        buscarPanel.add(btnBuscarProveedor);
        buscarPanel.add(btnEliminarProveedor);
        buscarPanel.add(btnLimpiarBusquedaProveedor);

        paneltablaProveedor.add(buscarPanel, BorderLayout.NORTH);

        modeloTablaProveedor = new DefaultTableModel(new Object[]{"ID", "Nombre", "NIT", "Contacto", "Frecuencia"}, 0);
        tablaProveedor = new JTable(modeloTablaProveedor);
        paneltablaProveedor.add(new JScrollPane(tablaProveedor), BorderLayout.CENTER);

        tabProveedor.add(paneltablaProveedor, BorderLayout.CENTER);

        // ======================= TAB 2: REGISTRO DE EVALUACION =======================
        JPanel tabEvaluacion = new JPanel();
        tabEvaluacion.setLayout(new BorderLayout());
        tabEvaluacion.setBackground(new Color(240, 240, 240));

        JPanel panelInferior = new JPanel();
        panelInferior.setLayout(new BoxLayout(panelInferior, BoxLayout.Y_AXIS));
        panelInferior.setBackground(Color.WHITE);
        panelInferior.setBorder(createStyledBorder("Registro de Evaluacion"));

        JPanel formInferior = new JPanel(new GridBagLayout());
        formInferior.setBackground(Color.WHITE);

        // Inicializar cbProveedores y llenarlo con datos
        List<Proveedor> proveedores = proveedorDAO.obtenerTodos();
        DefaultComboBoxModel<ProveedorCombo> modelCbProveedores = new DefaultComboBoxModel<>();
        modelCbProveedores.addElement(null); // Para permitir no seleccionar nada inicialmente
        for (Proveedor p : proveedores) {
            modelCbProveedores.addElement(new ProveedorCombo(p.getId(), p.getNombre()));
        }
        cbProveedores = new JComboBox<>(modelCbProveedores);
        cbProveedores.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        cbProveedores.setBackground(Color.WHITE);
        cbProveedores.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180)),
                BorderFactory.createEmptyBorder(2, 8, 2, 8)
        ));
        addFormRow(formInferior, gbc, 0, "ID Proveedor:", cbProveedores);

        UtilDateModel model2 = new UtilDateModel();
        Properties p2 = new Properties();
        p2.put("text.today", "Hoy");
        p2.put("text.month", "Mes");
        p2.put("text.year", "Año");
        JDatePanelImpl datePanel2 = new JDatePanelImpl(model2, p2);
        datePickerFechaEvaluacion = new JDatePickerImpl(datePanel2, new DateLabelFormatter());
        JPanel datePanelWrapper2 = new JPanel(new BorderLayout());
        datePanelWrapper2.setBackground(Color.WHITE);
        datePanelWrapper2.add(datePickerFechaEvaluacion, BorderLayout.WEST);
        addFormRow(formInferior, gbc, 1, "Fecha Evaluacion:", datePanelWrapper2);
        addFormRow(formInferior, gbc, 2, "Puntualidad:", txtpuntualidad = createStyledTextField(15));
        addFormRow(formInferior, gbc, 3, "Calidad Producto:", txtCalidadProducto = createStyledTextField(15));
        addFormRow(formInferior, gbc, 4, "Costo:", txtCosto = createStyledTextField(15));
        addFormRow(formInferior, gbc, 5, "observaciones:", txtObservaciones = createStyledTextField(15));

        panelInferior.add(formInferior);

        JPanel buttonPanelProveedor = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanelProveedor.setBackground(Color.WHITE);
        btnGuardarEvaluacion = createStyledButton("Guardar Lote", new Color(76, 175, 80));
        buttonPanelProveedor.add(btnGuardarEvaluacion);
        panelInferior.add(Box.createRigidArea(new Dimension(0, 10)));
        panelInferior.add(buttonPanelProveedor);

        tabEvaluacion.add(panelInferior, BorderLayout.NORTH);

        JPanel panelTablaEvaluacion = new JPanel(new BorderLayout());
        panelTablaEvaluacion.setBorder(createStyledBorder("Registros de Evaluaciones"));
        panelTablaEvaluacion.setBackground(Color.WHITE);

        JPanel buscarPanelEvaluacion = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buscarPanelEvaluacion.setBackground(Color.WHITE);
        buscarPanelEvaluacion.add(new JLabel("Buscar por ID:"));
        txtBuscarEvaluacion = createStyledTextField(10);
        buscarPanelEvaluacion.add(txtBuscarEvaluacion);
        btnBuscarEvaluacion = createStyledButton("Buscar", new Color(33, 150, 243));
        btnEliminarEvaluacion = createStyledButton("Eliminar", new Color(244, 67, 54));
        btnLimpiarBusquedaEvaluacion = createStyledButton("Limpiar busqueda", new Color(54, 243, 101));
        buscarPanelEvaluacion.add(btnBuscarEvaluacion);
        buscarPanelEvaluacion.add(btnEliminarEvaluacion);
        buscarPanelEvaluacion.add(btnLimpiarBusquedaEvaluacion);
        panelTablaEvaluacion.add(buscarPanelEvaluacion, BorderLayout.NORTH);

        modeloTablaEvaluaciones = new DefaultTableModel(new Object[]{"ID Evaluación", "Proveedor", "Fecha", "Puntualidad", "Calidad", "Costo", "Observaciones"}, 0);
        tablaEvaluacion = new JTable(modeloTablaEvaluaciones);
        panelTablaEvaluacion.add(new JScrollPane(tablaEvaluacion), BorderLayout.CENTER);

        tabEvaluacion.add(panelTablaEvaluacion, BorderLayout.CENTER);

        // Añadir pestañas
        tabbedPane.addTab("Formulario Proveedor", tabProveedor);
        tabbedPane.addTab("Formulario Evaluacion", tabEvaluacion);

        add(tabbedPane, BorderLayout.CENTER);
    }
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

    // Métodos para obtener datos del formulario
    public Proveedor getDatosFormularioProveedor() {
        Proveedor proveedor = new Proveedor();
        proveedor.setNombre(txtNombre.getText());
        proveedor.setNit(txtNit.getText());
        proveedor.setContacto(txtContacto.getText());
        try {
            proveedor.setFrecuenciaVisitas(FrecuenciaVisitas.fromString(txtFrecuencia.getText()));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor ingrese una frecuencia de visitas válida", "Error", JOptionPane.ERROR_MESSAGE);
            throw e;
        }
        return proveedor;
    }

    public EvaluacionProveedor getDatosFormularioEvaluacion() {
        EvaluacionProveedor evaluacion = new EvaluacionProveedor();

        // Obtener el proveedor seleccionado del JComboBox
        ProveedorCombo proveedorCombo = (ProveedorCombo) cbProveedores.getSelectedItem();
        if (proveedorCombo != null && proveedorCombo.getId() != null) {
            Proveedor proveedor = new Proveedor();
            proveedor.setId(proveedorCombo.getId());
            evaluacion.setProveedor(proveedor);
        } else {
            JOptionPane.showMessageDialog(this, "Por favor seleccione un proveedor", "Error", JOptionPane.ERROR_MESSAGE);
            return null; // O lanza una excepción, dependiendo de tu lógica
        }

        if (datePickerFechaEvaluacion.getModel().isSelected()) {
            Calendar cal = Calendar.getInstance();
            cal.set(datePickerFechaEvaluacion.getModel().getYear(),
                    datePickerFechaEvaluacion.getModel().getMonth(),
                    datePickerFechaEvaluacion.getModel().getDay());
            evaluacion.setFechaEvaluacion(cal.getTime());
        } else {
            evaluacion.setFechaEvaluacion(null);
        }

        try {
            evaluacion.setPuntualidad(Integer.parseInt(txtpuntualidad.getText()));
            evaluacion.calidad_producto(Integer.parseInt(txtCalidadProducto.getText()));
            evaluacion.setCosto(Integer.parseInt(txtCosto.getText()));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor ingrese valores numéricos válidos para la evaluación", "Error", JOptionPane.ERROR_MESSAGE);
            throw e;
        }

        evaluacion.setObservaciones(txtObservaciones.getText());

        return evaluacion;
    }

    public void limpiarFormulario() {
        // Limpiar formulario proveedor (superior)
        txtNombre.setText("");
        txtNit.setText("");
        txtContacto.setText("");
        txtFrecuencia.setText("");

        // Limpiar formulario evaluación (inferior)
        cbProveedores.setSelectedIndex(-1); // Desseleccionar el proveedor
        datePickerFechaEvaluacion.getModel().setValue(null);
        datePickerFechaEvaluacion.getModel().setSelected(false);
        datePickerFechaEvaluacion.getJFormattedTextField().setText("");
        txtpuntualidad.setText("");
        txtCalidadProducto.setText("");
        txtCosto.setText("");
        txtObservaciones.setText("");
    }

    public void setDatosFormularioProveedor(Proveedor proveedor) {
        txtNombre.setText(proveedor.getNombre());
        txtNit.setText(proveedor.getNit());
        txtContacto.setText(proveedor.getContacto());
        txtFrecuencia.setText(String.valueOf(proveedor.getFrecuenciaVisitas()));
    }

    public void setDatosFormularioEvaluacion(EvaluacionProveedor evaluacionProveedor) {
        if (evaluacionProveedor.getProveedor() != null) {
            // Buscar el proveedor en el JComboBox y seleccionarlo
            for (int i = 0; i < cbProveedores.getItemCount(); i++) {
                if (cbProveedores.getItemAt(i).getId().equals(evaluacionProveedor.getProveedor().getId())) {
                    cbProveedores.setSelectedIndex(i);
                    break;
                }
            }
        } else {
            cbProveedores.setSelectedIndex(-1);
        }

        if (evaluacionProveedor.getFechaEvaluacion() != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(evaluacionProveedor.getFechaEvaluacion());
            datePickerFechaEvaluacion.getModel().setDate(
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)
            );
            datePickerFechaEvaluacion.getModel().setSelected(true);
        } else {
            datePickerFechaEvaluacion.getModel().setValue(null);
        }

        txtpuntualidad.setText(String.valueOf(evaluacionProveedor.getPuntualidad()));
        txtCalidadProducto.setText(String.valueOf(evaluacionProveedor.getCalidadProductos()));
        txtCosto.setText(String.valueOf(evaluacionProveedor.getCosto()));
        txtObservaciones.setText(evaluacionProveedor.getObservaciones());
    }

    public void setGuardarListener(java.awt.event.ActionListener listener) {
        btnGuardar.addActionListener(listener);
    }
    public void setGuardarListenerEvaluacion(java.awt.event.ActionListener listener) {
        btnGuardarEvaluacion.addActionListener(listener);
    }
}
