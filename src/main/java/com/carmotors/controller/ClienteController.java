package com.carmotors.controller;

import com.carmotors.model.Cliente;
import com.carmotors.modelDAO.ClienteDAO;
import com.carmotors.view.PanelCliente;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClienteController {
    private static final Logger LOGGER = Logger.getLogger(ClienteController.class.getName());

    private final PanelCliente vista;
    private final ClienteDAO clienteDAO;
    private Runnable actualizarCallback;

    public ClienteController(PanelCliente vista, ClienteDAO clienteDAO, Runnable actualizarCallback) {
        this.vista = Objects.requireNonNull(vista, "La vista no puede ser nula");
        this.clienteDAO = Objects.requireNonNull(clienteDAO, "El DAO no puede ser nulo");
        this.actualizarCallback = actualizarCallback;

        this.vista.setControlador(this);
        cargarClientes();
        LOGGER.log(Level.CONFIG, "Controlador de cliente inicializado correctamente");
    }

    public void guardarCliente() {
        try {
            Cliente cliente = vista.getDatosFormulario();
            LOGGER.log(Level.INFO, "Intentando guardar cliente: {0}", cliente.getNombre());

            if (!validarCliente(cliente)) {
                return;
            }

            boolean guardadoExitoso = clienteDAO.agregar(cliente);

            if (guardadoExitoso) {
                LOGGER.log(Level.INFO, "Cliente guardado exitosamente: {0}", cliente.getNombre());
                mostrarMensajeExito();
                vista.limpiarFormulario();
                cargarClientes();
                ejecutarCallbackActualizacion();
            } else {
                LOGGER.log(Level.WARNING, "No se pudo guardar el cliente");
                mostrarMensajeError("No se pudo guardar el cliente");
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al guardar cliente", e);
            mostrarMensajeError("Error al guardar: " + e.getMessage());
        }
    }

    private boolean validarCliente(Cliente cliente) {
        if (cliente.getNombre() == null || cliente.getNombre().trim().isEmpty()) {
            mostrarMensajeError("El nombre del cliente es requerido");
            return false;
        }
        if (cliente.getIdentificacion() == null || cliente.getIdentificacion().trim().isEmpty()) {
            mostrarMensajeError("La identificación es requerida");
            return false;
        }
        return true;
    }

    private void ejecutarCallbackActualizacion() {
        if (actualizarCallback != null) {
            try {
                LOGGER.log(Level.FINE, "Ejecutando callback de actualización");
                actualizarCallback.run();
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Error al ejecutar callback de actualización", e);
            }
        } else {
            LOGGER.log(Level.WARNING, "No hay callback de actualización configurado");
        }
    }

    private void mostrarMensajeExito() {
        SwingUtilities.invokeLater(() ->
                JOptionPane.showMessageDialog(
                        vista,
                        "Cliente guardado correctamente",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE
                )
        );
    }

    private void mostrarMensajeError(String mensaje) {
        SwingUtilities.invokeLater(() ->
                JOptionPane.showMessageDialog(
                        vista,
                        mensaje,
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                )
        );
    }

    public void cargarCliente(Cliente cliente) {
        SwingUtilities.invokeLater(() -> {
            if (cliente != null) {
                vista.setDatosFormulario(cliente);
            }
        });
    }

    public void limpiarFormulario() {
        SwingUtilities.invokeLater(vista::limpiarFormulario);
    }

    public Runnable getActualizarCallback() {
        return this.actualizarCallback;
    }

    public void setActualizarCallback(Runnable callback) {
        Objects.requireNonNull(callback, "El callback no puede ser nulo");
        this.actualizarCallback = callback;
    }

    public void eliminarCliente(String idTexto) {
        try {
            int id = Integer.parseInt(idTexto);
            String resultado = clienteDAO.eliminarPorId(id);  // Usar el método renombrado
            mostrarMensajeResultado(resultado);
            cargarClientes();
            ejecutarCallbackActualizacion();
            vista.limpiarFormulario();
        } catch (NumberFormatException e) {
            mostrarMensajeError("El ID debe ser un número válido.");
        }
    }
    
    public void buscarPorId(String idTexto) {
        try {
            int id = Integer.parseInt(idTexto);
            Cliente cliente = clienteDAO.obtenerPorId(id);
            if (cliente != null) {
                vista.setDatosFormulario(cliente);
            } else {
                mostrarMensajeError("No se encontró cliente con ID: " + id);
            }
            cargarClientes();
        } catch (NumberFormatException e) {
            mostrarMensajeError("El ID debe ser un número válido.");
        }
    }

    public void cargarClientes() {
        List<Cliente> clientes = clienteDAO.obtenerTodos();
        SwingUtilities.invokeLater(() -> {
            vista.setClientes(clientes);
        });
    }

    private void mostrarMensajeResultado(String mensaje) {
        SwingUtilities.invokeLater(() ->
                JOptionPane.showMessageDialog(
                        vista,
                        mensaje,
                        "Resultado",
                        JOptionPane.INFORMATION_MESSAGE
                )
        );
    }
}

