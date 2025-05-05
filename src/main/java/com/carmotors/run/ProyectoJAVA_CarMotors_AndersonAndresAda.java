package com.carmotors.run;


import com.carmotors.model.Cliente;
import com.carmotors.model.Proveedor;
import com.carmotors.modelDAO.*;
import com.carmotors.view.VentanaPrincipal;
import javax.swing.SwingUtilities;
import java.util.List;

/**
 *
 * @author ANDRES
 */
public class ProyectoJAVA_CarMotors_AndersonAndresAda {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Inicializar DAOs
            RepuestoDAO repuestoDAO = new RepuestoDAO();
            ProveedorDAO proveedorDAO = new ProveedorDAO();
            ClienteDAO clienteDAO = new ClienteDAO();
            ActividadEspecialDAO aedao = new ActividadEspecialDAO();
            ClienteActividadDAO cadao = new ClienteActividadDAO();

            LoteDAO loteDAO = new LoteDAO();
            VehiculoDAO vehiculoDAO = new VehiculoDAO();


            ServicioDAO servicioDAO = new ServicioDAO();
            TrabajoDAO trabajoDAO = new TrabajoDAO();


            DetalleTrabajoRepuestoDAO detalleTrabajoDAO = new DetalleTrabajoRepuestoDAO();
            EvaluacionProveedorDAO epdao = new EvaluacionProveedorDAO();

            // Debug inicial
            System.out.println("=== DEBUG INICIAL ===");
            System.out.println("Probando conexión y obtención de clientes...");

            List<Cliente> testClientes = clienteDAO.obtenerTodos();
            System.out.println("Número de clientes obtenidos: " + testClientes.size());
            testClientes.forEach(c -> System.out.println("Cliente: " + c.getId() + " - " + c.getNombre()));

            // Crear ventana principal primero
            VentanaPrincipal vista = new VentanaPrincipal(
                    repuestoDAO,
                    proveedorDAO,
                    clienteDAO,
                    loteDAO,
                    vehiculoDAO,
                    null,
                    servicioDAO,
                    trabajoDAO,
                    detalleTrabajoDAO,
                    aedao,
                    cadao,
                    epdao
                    // Temporalmente null, lo seteamos después
            );

            // Crear callback para actualización que usa la vista ya creada
            Runnable actualizarCallback = () -> {
                System.out.println("🔁 Ejecutando callback de actualización...");
                SwingUtilities.invokeLater(() -> {
                    List<Cliente> clientesActualizados = clienteDAO.obtenerTodos();
                    System.out.println("📋 Clientes obtenidos para actualización: " + clientesActualizados.size());
                    clientesActualizados.forEach(c -> System.out.println("   - " + c.getNombre()));

                    // Actualizar la vista con los nuevos clientes
                    vista.actualizarListaClientes(clientesActualizados);
                });
            };

            Runnable actualizarProveedores = () -> {
                SwingUtilities.invokeLater(() -> {
                    List<Proveedor> proveedores = proveedorDAO.obtenerTodos();
                    vista.actualizarListaProveedores(proveedores);
                });
            };
            vista.setActualizarCallback1(actualizarProveedores);
            actualizarProveedores.run();

            // Asignar el callback a la ventana y controladores
            vista.setActualizarCallback(actualizarCallback);
            vista.setVisible(true);
        });
    }
}