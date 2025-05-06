package com.carmotors.view;

import com.carmotors.model.Lote;
import com.carmotors.model.Trabajo;
import com.carmotors.model.DetalleTrabajoRepuesto;
import com.carmotors.model.Repuesto; // Importar la clase Repuesto
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects; // Importar Objects para el método equals y hashCode


public class PanelDetalleTrabajoRepuesto extends JPanel {
    private JComboBox<TrabajoCombo> cbTrabajos;
    private JComboBox<LoteCombo> cbLotes;
    private JTextField txtCantidad;
    private JLabel lblStock;
    private JButton btnGuardar;
    private JButton btnEliminar;
    private JTable tblDetalles;

    private JTextField txtLote;
    private JButton btnBuscarLote;
    private List<Lote> listaLotes; // Mantener una lista de lotes

    public PanelDetalleTrabajoRepuesto() {
        setLayout(new BorderLayout());
        initComponents();
        this.listaLotes = new ArrayList<>(); // Inicializar la lista de lotes
    }

    private void initComponents() {
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel superior (formulario)
        JPanel panelFormulario = new JPanel();
        panelFormulario.setLayout(new BoxLayout(panelFormulario, BoxLayout.Y_AXIS));
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Registro de Repuestos"));

        // Panel de selección de trabajo
        JPanel panelTrabajo = new JPanel(new BorderLayout(10, 0));
        panelTrabajo.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        JLabel lblTrabajo = new JLabel("Seleccionar Trabajo:");
        lblTrabajo.setPreferredSize(new Dimension(150, 25));
        cbTrabajos = new JComboBox<>();
        configurarComboTrabajos();
        panelTrabajo.add(lblTrabajo, BorderLayout.WEST);
        panelTrabajo.add(cbTrabajos, BorderLayout.CENTER);

        // Panel de selección de lote
        JPanel panelLote = new JPanel(new BorderLayout(10, 0));
        panelLote.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        JLabel lblLote = new JLabel("Lote:");
        lblLote.setPreferredSize(new Dimension(150, 25));
        cbLotes = new JComboBox<>();
        configurarComboLotes();
        panelLote.add(lblLote, BorderLayout.WEST);
        panelLote.add(cbLotes, BorderLayout.CENTER);

        // Panel de cantidad
        JPanel panelCantidad = new JPanel(new BorderLayout(10, 0));
        panelCantidad.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        JLabel lblCantidad = new JLabel("Cantidad:");
        lblCantidad.setPreferredSize(new Dimension(150, 25));
        txtCantidad = new JTextField();
        panelCantidad.add(lblCantidad, BorderLayout.WEST);
        panelCantidad.add(txtCantidad, BorderLayout.CENTER);

        // Panel de stock
        JPanel panelStock = new JPanel(new BorderLayout(10, 0));
        panelStock.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        JLabel lblStockLabel = new JLabel("Stock:");
        lblStockLabel.setPreferredSize(new Dimension(150, 25));
        lblStock = new JLabel("Stock disponible: 0");
        panelStock.add(lblStockLabel, BorderLayout.WEST);
        panelStock.add(lblStock, BorderLayout.CENTER);

        // Botón para guardar
        JPanel panelBotonGuardar = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBotonGuardar.setBorder(BorderFactory.createEmptyBorder(10, 5, 5, 5));
        btnGuardar = new JButton("Registrar Repuesto");
        btnGuardar.setBackground(new Color(0, 102, 204));
        btnGuardar.setForeground(Color.WHITE);
        panelBotonGuardar.add(btnGuardar);

        // Agregar todos los paneles al formulario
        panelFormulario.add(panelTrabajo);
        panelFormulario.add(panelLote);
        panelFormulario.add(panelCantidad);
        panelFormulario.add(panelStock);
        panelFormulario.add(panelBotonGuardar);

        // Panel central (tabla de detalles)
        JPanel panelTabla = new JPanel(new BorderLayout());
        panelTabla.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        tblDetalles = new JTable();
        JScrollPane scrollPane = new JScrollPane(tblDetalles);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Repuestos utilizados"));
        panelTabla.add(scrollPane, BorderLayout.CENTER);

        // Panel inferior (botones)
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnEliminar = new JButton("Eliminar Selección");
        btnEliminar.setBackground(new Color(204, 0, 0));
        btnEliminar.setForeground(Color.WHITE);
        panelBotones.add(btnEliminar);

        // Agregar componentes al panel principal
        add(panelFormulario, BorderLayout.NORTH);
        add(panelTabla, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
        
        // Agregar panel de busqueda de Lote.
        JPanel panelBusquedaLote = new JPanel(new BorderLayout(10,0));
        panelBusquedaLote.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        JLabel lblBuscarLote = new JLabel("Buscar Lote:");
        lblBuscarLote.setPreferredSize(new Dimension(150,25));
        txtLote = new JTextField();
        btnBuscarLote = new JButton("Buscar");
        panelBusquedaLote.add(lblBuscarLote, BorderLayout.WEST);
        panelBusquedaLote.add(txtLote, BorderLayout.CENTER);
        panelBusquedaLote.add(btnBuscarLote, BorderLayout.EAST);
        panelFormulario.add(panelBusquedaLote, 2); // Insertar en la posición 2
    }

    private void configurarComboTrabajos() {
        cbTrabajos.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value,
                                                          int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof TrabajoCombo) {
                    TrabajoCombo tc = (TrabajoCombo) value;
                    setText(tc.toString());
                }
                return this;
            }
        });

        // Añadir listener para debug
        cbTrabajos.addActionListener(e -> {
            TrabajoCombo seleccionado = (TrabajoCombo) cbTrabajos.getSelectedItem();
            System.out.println("Trabajo seleccionado: " + (seleccionado != null ? seleccionado.getId() : "null"));
        });
    }

    private void configurarComboLotes() {
        cbLotes.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value,
                                                          int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof LoteCombo) {
                    LoteCombo lc = (LoteCombo) value;
                    setText(lc.toString());
                }
                return this;
            }
        });

        // Añadir listener para debug
        cbLotes.addActionListener(e -> {
            LoteCombo seleccionado = (LoteCombo) cbLotes.getSelectedItem();
            if (seleccionado != null) {
                System.out.println("Lote seleccionado: " + seleccionado.getId() + ", Stock: " + seleccionado.getStock());
                setStockDisponible(seleccionado.getStock());
            }
        });
    }

    // Clase interna para manejar el combo de trabajos
    public static class TrabajoCombo {
        private final Integer id;
        private final String displayText;
        private final Trabajo trabajo;

        public TrabajoCombo(Trabajo trabajo) {
            this.trabajo = trabajo;
            this.id = trabajo.getIdTrabajo();
            this.displayText = "Trabajo #" + trabajo.getIdTrabajo() + " - " +
                              (trabajo.getVehiculo() != null ? trabajo.getVehiculo().getPlaca() : "Sin vehículo");
        }

        public Integer getId() {
            return id;
        }

        public Trabajo getTrabajo() {
            return trabajo;
        }

        @Override
        public String toString() {
            return displayText;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            TrabajoCombo that = (TrabajoCombo) obj;
            return Objects.equals(id, that.id);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }
    }

    // Clase interna para manejar el combo de lotes
    public static class LoteCombo {
        private final Integer id;
        private final String displayText;
        private final Lote lote;
        private final Integer stock;

        public LoteCombo(Lote lote) {
            this.lote = lote;
            this.id = lote.getId();
            this.stock = lote.getCantidadDisponible();
            String repuestoInfo = lote.getIdrepuesto() != null ?
                    "Repuesto: " + lote.getIdrepuesto().getNombre()  : "Sin repuesto";
            this.displayText = "Lote #" + lote.getId() + " - " + repuestoInfo + " (Disp: " + lote.getCantidadDisponible() + ")";
        }

        public Integer getId() {
            return id;
        }

        public Integer getStock() {
            return stock;
        }

        public Lote getLote() {
            return lote;
        }

        @Override
        public String toString() {
            return displayText;
        }
        
         @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            LoteCombo that = (LoteCombo) obj;
            return Objects.equals(id, that.id);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }
    }

    public void setTrabajoSelectionListener(ActionListener listener) {
        if (cbTrabajos != null && listener != null) {
            cbTrabajos.addActionListener(listener);
        }
    }

    public void cargarTrabajos(List<Trabajo> trabajos) {
       if (cbTrabajos != null) {
            DefaultComboBoxModel<TrabajoCombo> model = new DefaultComboBoxModel<>();

            // Opción por defecto
            model.addElement(new TrabajoCombo(new Trabajo() {
                {
                    setIdTrabajo(-1);
                }

                @Override
                public String toString() {
                    return "-- Seleccione un trabajo --";
                }
            }));

            if (trabajos != null) {
                for (Trabajo trabajo : trabajos) {
                    model.addElement(new TrabajoCombo(trabajo));
                }
            }

            // Actualización segura en el hilo de eventos
            SwingUtilities.invokeLater(() -> {
                cbTrabajos.setModel(model);
                System.out.println("[DEBUG] ComboBox de trabajos actualizado con " + model.getSize() + " elementos");

                // Seleccionar el primer elemento si existe
                if (model.getSize() > 1) {
                    cbTrabajos.setSelectedIndex(0); // Seleccionar la opción por defecto
                }

                cbTrabajos.revalidate();
                cbTrabajos.repaint();
            });
        }
    }

    public Trabajo getTrabajoSeleccionado() {
        if (cbTrabajos != null && cbTrabajos.getSelectedItem() instanceof TrabajoCombo) {
            TrabajoCombo seleccionado = (TrabajoCombo) cbTrabajos.getSelectedItem();
             if (seleccionado.getId() != -1) { // No es la opción por defecto
                return seleccionado.getTrabajo();
            }
        }
        return null;
    }
    
    public void setBuscarLoteListener(ActionListener listener) {
        if (btnBuscarLote != null && listener != null) {
            // Eliminar listeners anteriores para evitar duplicados
            for (ActionListener al : btnBuscarLote.getActionListeners()) {
                btnBuscarLote.removeActionListener(al);
            }

            btnBuscarLote.addActionListener(listener);
        }
    }

    /**
     * Obtiene el número/código de lote ingresado
     * @return El texto ingresado en el campo de lote
     */
    public String getNumeroLote() {
        return txtLote != null ? txtLote.getText().trim() : "";
    }

    // Métodos para interactuar con el controlador
    public void setLoteSelectionListener(ActionListener listener) {
        if (cbLotes != null && listener != null) {
            cbLotes.addActionListener(listener);
        }
    }

    public void cargarLotes(List<Lote> lotes) {
        if (cbLotes != null) {
            DefaultComboBoxModel<LoteCombo> model = new DefaultComboBoxModel<>();
            
             // Opción por defecto
            model.addElement(new LoteCombo(new Lote() {
                {
                    setId(-1);
                    setCantidadDisponible(0);
                }
                
                @Override
                public String toString() {
                    return "-- Seleccione un lote --";
                }
            }));

            if (lotes != null) {
                for (Lote lote : lotes) {
                    model.addElement(new LoteCombo(lote));
                }
            }
            
             // Actualización segura en el hilo de eventos
            SwingUtilities.invokeLater(() -> {
                cbLotes.setModel(model);
                System.out.println("[DEBUG] ComboBox de lotes actualizado con " + model.getSize() + " elementos");
                
                 // Seleccionar el primer elemento si existe
                if (model.getSize() > 1) {
                    cbLotes.setSelectedIndex(0); // Seleccionar la opción por defecto
                }
                
                cbLotes.revalidate();
                cbLotes.repaint();
            });
        }
    }
    
     public void actualizarListaLotes(List<Lote> lotes) {
        this.listaLotes = lotes;
    }

    public void setEliminarListener(ActionListener listener) {
        if (btnEliminar != null && listener != null) {
            btnEliminar.addActionListener(listener);
        }
    }

    public void setStockDisponible(int stock) {
        if (lblStock != null) {
            lblStock.setText("Stock disponible: " + stock);
        }
    }

    public Integer getCantidad() {
        try {
            return txtCantidad != null ? Integer.parseInt(txtCantidad.getText()) : null;
        } catch (NumberFormatException e) {
            return null;
        }
    }

   public Lote getLoteSeleccionado() {
        if (cbLotes != null && cbLotes.getSelectedItem() instanceof LoteCombo) {
            LoteCombo seleccionado = (LoteCombo) cbLotes.getSelectedItem();
             if (seleccionado.getId() != -1) { // No es la opción por defecto
                 return seleccionado.getLote();
             }
        }
        return null;
    }

    public void mostrarDetalles(List<DetalleTrabajoRepuesto> detalles) {
        if (tblDetalles != null) {
            DetalleTableModel model = new DetalleTableModel(detalles);
            tblDetalles.setModel(model);
        }
    }

    public DetalleTrabajoRepuesto getDetalleSeleccionado() {
        if (tblDetalles != null && tblDetalles.getSelectedRow() >= 0) {
            DetalleTableModel model = (DetalleTableModel) tblDetalles.getModel();
            return model.getDetalleAt(tblDetalles.getSelectedRow());
        }
        return null;
    }

    public void limpiarFormulario() {
        if (txtCantidad != null) txtCantidad.setText("");
        if (txtLote != null) txtLote.setText("");
        setStockDisponible(0);
        if (cbLotes != null) {
             DefaultComboBoxModel<LoteCombo> model = (DefaultComboBoxModel<LoteCombo>) cbLotes.getModel();
             if(model.getSize() > 0){
                 cbLotes.setSelectedIndex(0);
             }
        }
    }

    public void setGuardarListener(ActionListener listener) {
        btnGuardar.addActionListener(e -> {
            btnGuardar.setEnabled(false); // Deshabilitar botón durante la operación
            try {
                listener.actionPerformed(e);
            } finally {
                btnGuardar.setEnabled(true); // Rehabilitar después
            }
        });
    }

    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void mostrarExito(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }

    public boolean validarFormulario() {
        if (getTrabajoSeleccionado() == null) {
            mostrarError("Seleccione un trabajo");
            return false;
        }
        if (getLoteSeleccionado() == null) {
            mostrarError("Seleccione un lote");
            return false;
        }
        if (getCantidad() == null || getCantidad() <= 0) {
            mostrarError("Ingrese una cantidad válida");
            return false;
        }
        return true;
    }

    // Modelo de tabla para mostrar los detalles del trabajo-repuesto
   private static class DetalleTableModel extends AbstractTableModel {
        private final List<DetalleTrabajoRepuesto> detalles;
        private final String[] columnNames = {"ID Detalle", "Trabajo", "Lote", "Repuesto", "Cantidad Usada"};

        public DetalleTableModel(List<DetalleTrabajoRepuesto> detalles) {
            this.detalles = detalles != null ? detalles : new ArrayList<>(); // Evitar nulos
        }

        @Override
        public int getRowCount() {
            return detalles.size();
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public String getColumnName(int column) {
            return columnNames[column];
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            if (rowIndex < 0 || rowIndex >= detalles.size()) {
                return null; // Manejar índice fuera de rango
            }
            DetalleTrabajoRepuesto detalle = detalles.get(rowIndex);
            switch (columnIndex) {
                case 0: return detalle.getIdDetalle();
                case 1: return detalle.getTrabajo() != null ? detalle.getTrabajo().getIdTrabajo() : "N/A";
                case 2: return detalle.getLote() != null ? detalle.getLote().getId() : "N/A";
                case 3:
                    if (detalle.getLote() != null && detalle.getLote().getIdrepuesto() != null) {
                        return detalle.getLote().getIdrepuesto().getNombre();
                    } else {
                        return "N/A";
                    }
                case 4: return detalle.getCantidadUsada();
                default: return null;
            }
        }

        public DetalleTrabajoRepuesto getDetalleAt(int row) {
            if (row < 0 || row >= detalles.size()) {
                return null; // Manejar índice fuera de rango
            }
            return detalles.get(row);
        }
    }
    
    public void cargarLotesFiltrados(List<Lote> lotesFiltrados) {
        if (cbLotes != null) {
            DefaultComboBoxModel<LoteCombo> model = new DefaultComboBoxModel<>();
            
             // Opción por defecto
            model.addElement(new LoteCombo(new Lote() {
                {
                    setId(-1);
                    setCantidadDisponible(0);
                }
                
                @Override
                public String toString() {
                    return "-- Seleccione un lote --";
                }
            }));

            if (lotesFiltrados != null) {
                for (Lote lote : lotesFiltrados) {
                    model.addElement(new LoteCombo(lote));
                }
            }
            
             // Actualización segura en el hilo de eventos
            SwingUtilities.invokeLater(() -> {
                cbLotes.setModel(model);
                System.out.println("[DEBUG] ComboBox de lotes actualizado con " + model.getSize() + " elementos");
                
                 // Seleccionar el primer elemento si existe
                if (model.getSize() > 1) {
                    cbLotes.setSelectedIndex(0); // Seleccionar la opción por defecto
                }
                
                cbLotes.revalidate();
                cbLotes.repaint();
            });
        }
    }
}

