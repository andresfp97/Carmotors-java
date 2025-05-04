package com.carmotors.view;

import com.carmotors.model.Lote;
import com.carmotors.model.DetalleTrabajoRepuesto;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class PanelDetalleTrabajoRepuesto extends JPanel {
    private JLabel lblTrabajoId;
    private JComboBox<Lote> cbLotes;
    private JTextField txtCantidad;
    private JLabel lblStock;
    private JButton btnGuardar;
    private JButton btnEliminar;
    private JTable tblDetalles;

    private JTextField txtLote;
    private JButton btnBuscarLote;

    public PanelDetalleTrabajoRepuesto() {
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel superior (formulario)
        JPanel panelFormulario = new JPanel();
        panelFormulario.setLayout(new BoxLayout(panelFormulario, BoxLayout.Y_AXIS));
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Registro de Repuestos"));

        // Panel de información del trabajo
        JPanel panelTrabajo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lblTrabajoId = new JLabel("N/A");
        panelTrabajo.add(new JLabel("Trabajo ID:"));
        panelTrabajo.add(lblTrabajoId);

        // Panel de selección de lote
        JPanel panelLote = new JPanel(new GridLayout(2, 2, 5, 5));
        cbLotes = new JComboBox<>();
        cbLotes.setPreferredSize(new Dimension(200, 25));
        txtCantidad = new JTextField();
        lblStock = new JLabel("Stock disponible: -");


        txtLote = new JTextField(15);
        btnBuscarLote = new JButton("Buscar Lote");

        panelLote.add(new JLabel("Lote:"));
        panelLote.add(cbLotes);

        panelLote.add(btnBuscarLote);
        panelLote.add(new JLabel("Cantidad:"));
        panelLote.add(txtCantidad);
        panelLote.add(new JLabel("Stock:"));
        panelLote.add(lblStock);

        // Botón para guardar
        btnGuardar = new JButton("Registrar Repuesto");
        btnGuardar.setBackground(new Color(0, 102, 204));
        btnGuardar.setForeground(Color.WHITE);

        panelFormulario.add(panelTrabajo);
        panelFormulario.add(panelLote);
        panelFormulario.add(btnGuardar);

        // Panel central (tabla de detalles)
        tblDetalles = new JTable();
        JScrollPane scrollPane = new JScrollPane(tblDetalles);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Repuestos utilizados"));

        // Panel inferior (botones)
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnEliminar = new JButton("Eliminar Selección");
        btnEliminar.setBackground(new Color(204, 0, 0));
        btnEliminar.setForeground(Color.WHITE);
        panelBotones.add(btnEliminar);

        // Agregar componentes al panel principal
        add(panelFormulario, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
    }
    public void setBuscarLoteListener(ActionListener listener) {
        if (btnBuscarLote != null && listener != null) {
            // Eliminar listeners anteriores para evitar duplicados
            for (ActionListener al : btnBuscarLote.getActionListeners()) {
                btnBuscarLote.removeActionListener(al);
            }

            btnBuscarLote.addActionListener(listener);

            // También responder a Enter en el campo de texto
            txtLote.addActionListener(listener);
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
            DefaultComboBoxModel<Lote> model = new DefaultComboBoxModel<>();
            if (lotes != null) {
                for (Lote lote : lotes) {
                    model.addElement(lote);
                }
            }
            cbLotes.setModel(model);

            // Actualizar stock del primer lote si existe
            if (model.getSize() > 0) {
                Lote primerLote = model.getElementAt(0);
                setStockDisponible(primerLote.getCantidadDisponible());
            } else {
                setStockDisponible(0);
            }
        }
    }

    public void setGuardarListener(ActionListener listener) {
        if (btnGuardar != null && listener != null) {
            btnGuardar.addActionListener(listener);
        }
    }

    public void setEliminarListener(ActionListener listener) {
        if (btnEliminar != null && listener != null) {
            btnEliminar.addActionListener(listener);
        }
    }

    public void mostrarInfoTrabajo(String idTrabajo) {
        if (lblTrabajoId != null) {
            lblTrabajoId.setText(idTrabajo != null ? idTrabajo : "N/A");
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
        return cbLotes != null ? (Lote) cbLotes.getSelectedItem() : null;
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
        setStockDisponible(0);
    }

    // En el controlador:
    /**
     * Establece el listener para el botón de búsqueda de lote
     * @param listener El ActionListener que manejará el evento de búsqueda
     */

}