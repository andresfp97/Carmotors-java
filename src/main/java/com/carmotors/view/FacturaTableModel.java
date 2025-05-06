package com.carmotors.view;

import com.carmotors.model.Factura;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class FacturaTableModel extends AbstractTableModel {
    private final List<Factura> facturas;
    private final String[] columnNames = {"ID", "Cliente", "Vehículo", "Total", "Fecha"};

    public FacturaTableModel(List<Factura> facturas) {
        this.facturas = facturas != null ? facturas : new ArrayList<>();
    }

    @Override
    public int getRowCount() {
        return facturas.size();
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
        Factura factura = facturas.get(rowIndex);
        switch (columnIndex) {
            case 0: return factura.getIdFactura();
            case 1: return factura.getIdCliente();
            case 2: return factura.getIdTrabajo();
            case 3: return factura.getTotal();
            case 4: return factura.getFechaEmision();
            default: return null;
        }
    }

    public Factura getFacturaAt(int row) {
        return facturas.get(row);
    }
}