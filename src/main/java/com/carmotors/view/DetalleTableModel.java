package com.carmotors.view;

import com.carmotors.model.DetalleTrabajoRepuesto;
import javax.swing.table.AbstractTableModel;
import java.util.List;

public class DetalleTableModel extends AbstractTableModel {
    private final List<DetalleTrabajoRepuesto> detalles;
    private final String[] columnNames = {"ID", "Repuesto", "Lote", "Cantidad"};

    public DetalleTableModel(List<DetalleTrabajoRepuesto> detalles) {
        this.detalles = detalles;
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
        DetalleTrabajoRepuesto detalle = detalles.get(rowIndex);
        switch (columnIndex) {
            case 0: return detalle.getIdDetalle();
            case 1: return detalle.getLote().getIdrepuesto();
            case 2: return detalle.getLote().getId();
            case 3: return detalle.getCantidadUsada();
            default: return null;
        }
    }

    public DetalleTrabajoRepuesto getDetalleAt(int row) {
        return detalles.get(row);
    }
}