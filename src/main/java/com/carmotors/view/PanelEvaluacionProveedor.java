package com.carmotors.view;


import com.carmotors.model.EvaluacionProveedor;
import com.carmotors.model.Proveedor;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

public class PanelEvaluacionProveedor extends JPanel{
    private JDatePickerImpl datePickerFechaEvaluacion;
    private JTextField txtpuntualidad, txtCalidadProducto, txtCosto, txtObservaciones;
    private JComboBox<PanelEvaluacionProveedor.ProveedorCombo> cbProveedores;
    private JButton btnGuardarEvaluacion;

    public PanelEvaluacionProveedor() {
        setLayout(new GridLayout(8, 2, 10, 10));
        setBackground(new Color(40, 35, 35));
        initComponents(); // Solo un método de inicialización
        configurarComboproveedores();
    }

    public JComboBox<ProveedorCombo> getCbProveedores() {
        return cbProveedores;
    }

    private void initComponents() {

        cbProveedores = new JComboBox<>();

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

        txtpuntualidad    = new JTextField();
        txtCalidadProducto = new JTextField();
        txtCosto = new JTextField();
        txtObservaciones = new JTextField();
        btnGuardarEvaluacion = new JButton("Guardar evaluacion");

        // Configurar combo box
        cbProveedores.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof PanelEvaluacionProveedor.ProveedorCombo) {
                    PanelEvaluacionProveedor.ProveedorCombo proveedor = (PanelEvaluacionProveedor.ProveedorCombo) value;
                    setText(proveedor.getDisplayText());
                }
                return this;
            }
        });

        JLabel[] labels = {
                new JLabel("ID del Proveedor:"),
                new JLabel("Fecha de evaluacion:"),
                new JLabel("Puntualidad:"),
                new JLabel("Calidad del Producto:"),
                new JLabel("Porcentaje de Costo:"),
                new JLabel("Observaciones:")
        };

        // Configurar colores de las etiquetas
        for (JLabel label : labels) {
            label.setForeground(Color.WHITE);
        }

        // Agregar componentes al panel
        add(labels[0]); add(cbProveedores);
        add(labels[1]); add(datePanelWrapper);
        add(labels[2]); add(txtpuntualidad);
        add(labels[3]); add(txtCalidadProducto);
        add(labels[4]); add(txtCosto);
        add(labels[5]); add(txtObservaciones);
        add(btnGuardarEvaluacion);
    }
    private void configurarComboproveedores() {
        // Configurar renderizador personalizado
        cbProveedores.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value,
                                                          int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof PanelEvaluacionProveedor.ProveedorCombo) {
                    PanelEvaluacionProveedor.ProveedorCombo cc = (PanelEvaluacionProveedor.ProveedorCombo) value;
                    setText(cc.toString());
                }
                return this;
            }
        });

        // Añadir listener para debug
        cbProveedores.addActionListener(e -> {
            PanelEvaluacionProveedor.ProveedorCombo seleccionado = (PanelEvaluacionProveedor.ProveedorCombo) cbProveedores.getSelectedItem();
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
            evaluacionProveedor.setPuntualidad(Integer.parseInt(txtpuntualidad.getText()));
            evaluacionProveedor.setCalidadProductos(Integer.parseInt(txtCalidadProducto.getText()));
            evaluacionProveedor.setCosto(Integer.parseInt(txtCosto.getText()));
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
        DefaultComboBoxModel<PanelEvaluacionProveedor.ProveedorCombo> model = new DefaultComboBoxModel<>();

        // Opción por defecto
        model.addElement(new PanelEvaluacionProveedor.ProveedorCombo(null, "-- Seleccione un proveedor --"));

        if (proveedores != null && !proveedores.isEmpty()) {
            for (Proveedor proveedor : proveedores) {
                String displayText = String.format("%s (%s)", proveedor.getNombre(), proveedor.getId());
                model.addElement(new PanelEvaluacionProveedor.ProveedorCombo(proveedor.getId(), displayText));
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
        cbProveedores.setSelectedItem(evaluacionProveedor.getProveedor().getId());
        txtpuntualidad.setText(String.valueOf(evaluacionProveedor.getPuntualidad()));
        txtCalidadProducto.setText(String.valueOf(evaluacionProveedor.getCalidadProductos()));
        txtCosto.setText(String.valueOf(evaluacionProveedor.getCalidadProductos() ));
        txtObservaciones.setText(evaluacionProveedor.getObservaciones());
    }

}
