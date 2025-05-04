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
        this.vista = vista;
        this.vehiculoDAO = vehiculoDAO;
        this.clienteDAO = clienteDAO;
        this.vista.setGuardarListener(e -> guardarVehiculo());

        cargarClientesEnVista();
    }

    private void guardarVehiculo() {
        try {
            Vehiculo vehiculo = vista.getDatosFormulario();

            // Validar selección de cliente
            Integer idCliente = vista.getClienteSeleccionadoId();
            if (idCliente == null) {
                JOptionPane.showMessageDialog(vista, "Debe seleccionar un cliente válido",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validar que el cliente exista (usando el ID obtenido del combo)
            if (clienteDAO.obtenerPorId(idCliente) == null) {
                JOptionPane.showMessageDialog(vista, "El cliente seleccionado no existe",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Asignar el ID del cliente al vehículo
            vehiculo.setId(idCliente);

            // Validar placa única
            if (vehiculoDAO.existePlaca(vehiculo.getPlaca())) {
                JOptionPane.showMessageDialog(vista, "La placa ya está registrada",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean guardadoExitoso = vehiculoDAO.agregar(vehiculo);

            if (guardadoExitoso) {
                JOptionPane.showMessageDialog(vista, "Vehículo guardado correctamente",
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
                vista.limpiarFormulario();
            } else {
                JOptionPane.showMessageDialog(vista, "No se pudo guardar el vehículo",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista, "Error al guardar: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public void cargarVehiculo(Vehiculo vehiculo) {
        if (vehiculo != null) {
            vista.setDatosFormulario(vehiculo);
        }
    }

    public void limpiarFormulario() {
        vista.limpiarFormulario();
    }

    // Método para cargar clientes en un combobox (si tu vista lo tiene)
    public void cargarClientesEnVista() {
        vista.cargarClientes(clienteDAO.obtenerTodos());
    }
}