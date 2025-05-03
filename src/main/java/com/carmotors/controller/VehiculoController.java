package com.carmotors.controller;

import com.carmotors.model.Vehiculo;
import com.carmotors.modelDAO.ClienteDAO;
import com.carmotors.modelDAO.VehiculoDAO;
import com.carmotors.view.PanelVehiculo;

import javax.swing.JOptionPane;

public class VehiculoController {
    private PanelVehiculo vista;
    private VehiculoDAO vehiculoDAO;
    private ClienteDAO clienteDAO; // Para validaciones

    public VehiculoController(PanelVehiculo vista, VehiculoDAO vehiculoDAO, ClienteDAO clienteDAO) {

    }

    private void guardarVehiculo() {
    }
}