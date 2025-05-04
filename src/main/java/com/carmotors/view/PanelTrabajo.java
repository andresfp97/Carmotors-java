package com.carmotors.view;

import com.carmotors.model.Servicio;
import com.carmotors.model.Vehiculo;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class PanelTrabajo extends JPanel {
    private JComboBox<Vehiculo> cbVehiculos;
    private JComboBox<Servicio> cbServicios;
    private JTextField txtTecnico;
    private JButton btnCrear;
    private JFormattedTextField txtFechaRecepcion;
    private JFormattedTextField txtFechaEntrega;

    public PanelTrabajo() {
        setLayout(new GridLayout(6, 2, 10, 10)); // Aumentamos a 6 filas
        initComponents();
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
    }

    private void initComponents() {
        // Configuración de ComboBox con renderers personalizados
        cbVehiculos = new JComboBox<>();
        cbVehiculos.setRenderer(new VehiculoRenderer());

        cbServicios = new JComboBox<>();
        cbServicios.setRenderer(new ServicioRenderer());

        txtTecnico = new JTextField();

        // Configuración de campos de fecha
        txtFechaRecepcion = new JFormattedTextField(new java.text.SimpleDateFormat("dd/MM/yyyy"));
        txtFechaRecepcion.setValue(new java.util.Date());

        txtFechaEntrega = new JFormattedTextField(new java.text.SimpleDateFormat("dd/MM/yyyy"));

        btnCrear = new JButton("Crear Trabajo");
        btnCrear.setBackground(new Color(70, 130, 180));
        btnCrear.setForeground(Color.WHITE);

        // Añadir componentes al panel
        add(new JLabel("Vehículo:"));
        add(cbVehiculos);
        add(new JLabel("Servicio:"));
        add(cbServicios);
        add(new JLabel("Técnico:"));
        add(txtTecnico);
        add(new JLabel("Fecha Recepción:"));
        add(txtFechaRecepcion);
        add(new JLabel("Fecha Entrega (opcional):"));
        add(txtFechaEntrega);
        add(new JLabel());
        add(btnCrear);
    }

    public void limpiarFormulario() {
        // Limpiar selección de ComboBoxes
        cbVehiculos.setSelectedIndex(-1);  // Deseleccionar cualquier ítem
        cbServicios.setSelectedIndex(-1); // Deseleccionar cualquier ítem

        // Limpiar campo de texto del técnico
        txtTecnico.setText("");

        // Restablecer fechas
        txtFechaRecepcion.setValue(new java.util.Date()); // Fecha actual
        txtFechaEntrega.setValue(null); // Limpiar fecha de entrega

        // Opcional: Mostrar mensaje de confirmación
        JOptionPane.showMessageDialog(this,
                "Formulario limpiado correctamente",
                "Información",
                JOptionPane.INFORMATION_MESSAGE);
    }
    // Método para obtener fecha entrega seguro
    public LocalDate getFechaEntrega() {
        try {
            return ((java.util.Date)txtFechaEntrega.getValue()).toInstant()
                    .atZone(java.time.ZoneId.systemDefault())
                    .toLocalDate();
        } catch (Exception e) {
            return null; // Retorna null si no hay fecha seleccionada
        }
    }

    // Renderer personalizado para Vehículos
    private class VehiculoRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                      boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (value instanceof Vehiculo) {
                Vehiculo v = (Vehiculo) value;
                setText(String.format("%s - %s %s", v.getPlaca(), v.getMarca(), v.getModelo()));
            }
            return this;
        }
    }

    // Renderer personalizado para Servicios
    private class ServicioRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                      boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (value instanceof Servicio) {
                Servicio s = (Servicio) value;
                setText(String.format("%s - %s (%.2f)", s.getIdServicio(),
                        s.getDescripcion(), s.getCostoManoObra()));
            }
            return this;
        }
    }

    // Métodos existentes mejorados
    public void cargarVehiculos(java.util.List<Vehiculo> vehiculos) {
        SwingUtilities.invokeLater(() -> {
            DefaultComboBoxModel<Vehiculo> model = new DefaultComboBoxModel<>();
            if (vehiculos != null) {
                vehiculos.forEach(model::addElement);
            }
            cbVehiculos.setModel(model);
        });
    }

    public void cargarServicios(java.util.List<Servicio> servicios) {
        SwingUtilities.invokeLater(() -> {
            DefaultComboBoxModel<Servicio> model = new DefaultComboBoxModel<>();
            if (servicios != null) {
                servicios.forEach(model::addElement);
            }
            cbServicios.setModel(model);
        });
    }

    // Nuevos métodos para obtener fechas
    public java.time.LocalDate getFechaRecepcion() {
        return ((java.util.Date)txtFechaRecepcion.getValue()).toInstant()
                .atZone(java.time.ZoneId.systemDefault())
                .toLocalDate();
    }



    // Métodos existentes
    public void setCrearTrabajoListener(java.awt.event.ActionListener listener) {
        btnCrear.addActionListener(listener);
    }

    public Vehiculo getVehiculoSeleccionado() {
        return (Vehiculo) cbVehiculos.getSelectedItem();
    }

    public Servicio getServicioSeleccionado() {
        return (Servicio) cbServicios.getSelectedItem();
    }

    public String getTecnico() {
        return txtTecnico.getText();
    }
}