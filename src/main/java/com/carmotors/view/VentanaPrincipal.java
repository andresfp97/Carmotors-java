package com.carmotors.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Objects;

import com.carmotors.controller.*;
import com.carmotors.model.Cliente;
import com.carmotors.model.Proveedor;
import com.carmotors.modelDAO.*;

public class VentanaPrincipal extends JFrame {
    private JPanel panelDerecho;
    private CardLayout cardLayout;
    private JPanel panelIzquierdo;
    private PanelRepuesto panelRepuesto;
    private PanelProveedor panelProveedor;
    private PanelCliente panelCliente;
    private PanelVehiculo panelVehiculo;
    private PanelServicio panelServicio;
    private PanelTrabajo panelTrabajo;
    private PanelDetalleTrabajoRepuesto panelDetalleTrabajo;
    private Runnable actualizarCallback;
    private ClienteController clienteController;
    private ProveedorController proveedorController;
    private PanelActividadEspecial panelActividadEspecial;
    private PanelFactura panelFactura;
    private PanelEvaluacionProveedor panelEvaluacionProveedor;
    private PanelGestionProveedores panelGestionProveedores;

    private final int MIN_MENU_WIDTH = 220; // Ancho ligeramente menor para un aspecto más compacto

    public VentanaPrincipal(RepuestoDAO repuestoDAO, ProveedorDAO proveedorDAO,
                            ClienteDAO clienteDAO, LoteDAO loteDAO,
                            VehiculoDAO vehiculoDAO, Runnable actualizarCallback,
                            ServicioDAO servicioDAO, TrabajoDAO trabajoDAO,
                            DetalleTrabajoRepuestoDAO detalleTrabajoDAO,
                            ActividadEspecialDAO actividadEspecialDAO, ClienteActividadDAO clienteActividadDAO,
                            EvaluacionProveedorDAO evaluacionProveedorDAO, FacturaDAO facturaDA) {
        setTitle("Sistema de Gestión Carmotors");
        setSize(1200, 700);
        setMinimumSize(new Dimension(900, 600)); // Aumentar mínimo para mejor visualización
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(238, 238, 238)); // Fondo general claro

        initComponents();
        initControllers(repuestoDAO, proveedorDAO, clienteDAO, loteDAO,
                vehiculoDAO, actualizarCallback, servicioDAO,
                trabajoDAO, detalleTrabajoDAO,
                actividadEspecialDAO, clienteActividadDAO,
                evaluacionProveedorDAO, facturaDA);
    }

    private void initComponents() {
        panelIzquierdo = new JPanel();
        panelIzquierdo.setLayout(new BoxLayout(panelIzquierdo, BoxLayout.Y_AXIS));
        panelIzquierdo.setBackground(new Color(54, 57, 63)); // Gris oscuro azulado (Discord)
        panelIzquierdo.setBorder(new EmptyBorder(10, 10, 10, 10)); // Margen para el menú

        cardLayout = new CardLayout();
        panelDerecho = new JPanel(cardLayout);
        panelDerecho.setBackground(new Color(245, 245, 245)); // Gris claro para el contenido
        panelDerecho.setBorder(new EmptyBorder(10, 10, 10, 10)); // Margen para el contenido

        // Inicialización de todos los paneles
        panelRepuesto = new PanelRepuesto();
        panelProveedor = new PanelProveedor();
        panelCliente = new PanelCliente();
        panelVehiculo = new PanelVehiculo();
        panelServicio = new PanelServicio();
        panelTrabajo = new PanelTrabajo();
        panelDetalleTrabajo = new PanelDetalleTrabajoRepuesto();
        panelActividadEspecial = new PanelActividadEspecial();
        panelEvaluacionProveedor = new PanelEvaluacionProveedor();
        panelGestionProveedores = new PanelGestionProveedores();
        panelFactura = new PanelFactura();

        // Agregar paneles al cardLayout
        panelDerecho.add(panelRepuesto, "Repuestos");
        panelDerecho.add(panelGestionProveedores, "GestionProveedores");
        panelDerecho.add(panelCliente, "Clientes");
        panelDerecho.add(panelVehiculo, "Vehiculo");
        panelDerecho.add(panelServicio, "Servicios");
        panelDerecho.add(panelTrabajo, "Trabajos");
        panelDerecho.add(panelDetalleTrabajo, "DetallesTrabajo");
        panelDerecho.add(panelActividadEspecial, "Actividades Especiales");
        panelDerecho.add(panelEvaluacionProveedor, "Evaluacion Proveedor");
        panelDerecho.add(panelFactura, "Factura");

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelIzquierdo, panelDerecho);
        splitPane.setDividerSize(8);
        splitPane.setDividerLocation(MIN_MENU_WIDTH);
        splitPane.setResizeWeight(0.0); // Evitar que el menú crezca demasiado
        add(splitPane);

        crearBotonesMenu();
    }

    private void initControllers(RepuestoDAO repuestoDao, ProveedorDAO proveedorDao,
                                 ClienteDAO clienteDao, LoteDAO loteDao,
                                 VehiculoDAO vehiculoDAO, Runnable actualizarCallback,
                                 ServicioDAO servicioDAO, TrabajoDAO trabajoDAO,
                                 DetalleTrabajoRepuestoDAO detalleTrabajoDAO,
                                 ActividadEspecialDAO actividadEspecialDAO, ClienteActividadDAO clienteActividadDAO,
                                 EvaluacionProveedorDAO evaluacionProveedorDAO, FacturaDAO facturaDAO) {

        new RepuestoController(panelRepuesto, repuestoDao);
        new ProveedorController(panelGestionProveedores, proveedorDao);
        this.clienteController = new ClienteController(panelCliente, clienteDao, actualizarCallback);
        new LoteController(panelRepuesto, loteDao);
        new VehiculoController(panelVehiculo, vehiculoDAO, clienteDao);
        new ServicioController(panelServicio, servicioDAO);
        new TrabajoController(panelTrabajo, trabajoDAO, vehiculoDAO, servicioDAO);
        new DetalleTrabajoRepuestoController(panelDetalleTrabajo, detalleTrabajoDAO, loteDao, trabajoDAO);
        new ActividadEspecialController(panelActividadEspecial, actividadEspecialDAO);
        new EvaluacionProveedorController(panelGestionProveedores, evaluacionProveedorDAO, proveedorDao);

        FacturaController facturaController = new FacturaController(
                panelFactura,
                facturaDAO,
                clienteDao,
                trabajoDAO,
                vehiculoDAO);
        panelFactura.setFacturaController(facturaController);
    }

    private void crearBotonesMenu() {
        crearBotonMenu("Gestión de Inventario", "Repuestos");
        crearBotonMenu("Gestión de Proveedores", "GestionProveedores");
        crearBotonMenu("Clientes", "Clientes");
        crearBotonMenu("Gestión Vehículo", "Vehiculo");
        crearBotonMenu("Servicios", "Servicios");
        crearBotonMenu("Trabajos", "Trabajos");
        crearBotonMenu("Factura", "Factura");
        crearBotonMenu("Detalles Trabajo", "DetallesTrabajo");
        crearBotonMenu("Actividades Especiales", "Actividades Especiales");
        // crearBotonMenu("Cliente Actividad", "Cliente Actividad"); // Comentado o eliminado si no hay panel
        crearBotonMenu("Evaluación Proveedor", "Evaluacion Proveedor");

        // Añadir un espaciador vertical para empujar los botones hacia arriba
        panelIzquierdo.add(Box.createVerticalGlue());
    }

    private void crearBotonMenu(String texto, String comando) {
        JButton boton = new JButton(texto);
        boton.setForeground(Color.WHITE);
        boton.setBackground(new Color(70, 71, 74)); // Gris más claro para los botones
        boton.setBorderPainted(false);
        boton.setFocusPainted(false);
        boton.setAlignmentX(Component.CENTER_ALIGNMENT);
        boton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40)); // Altura fija para los botones
        boton.setMinimumSize(new Dimension(MIN_MENU_WIDTH - 20, 40)); // Ancho mínimo
        boton.setPreferredSize(new Dimension(MIN_MENU_WIDTH - 20, 40));
        boton.setBorder(new EmptyBorder(5, 15, 5, 15)); // Margen interno
        boton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(new Color(114, 137, 218)); // Azul al pasar el ratón (Discord blue)
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(new Color(70, 71, 74));
            }
        });
        boton.addActionListener(e -> cardLayout.show(panelDerecho, comando));
        panelIzquierdo.add(boton);
        panelIzquierdo.add(Box.createRigidArea(new Dimension(0, 5))); // Espacio entre botones
    }

    public void actualizarListaClientes(java.util.List<Cliente> clientes) {
        if (panelVehiculo != null) {
            panelVehiculo.cargarClientes(clientes);
        }
    }

    public void actualizarListaProveedores(java.util.List<Proveedor> proveedores) {
        if (panelEvaluacionProveedor != null) {
            panelEvaluacionProveedor.cargarProveedor(proveedores);
        }
    }

    public void setActualizarCallback1(Runnable callback) {
        this.actualizarCallback = callback;
        if (proveedorController != null) {
            actualizarControladorCliente(callback);
        }
    }

    public void setActualizarCallback(Runnable callback) {
        this.actualizarCallback = callback;
        if (clienteController != null) {
            actualizarControladorCliente(callback);
        }
    }

    private void actualizarControladorCliente(Runnable callback) {
        Objects.requireNonNull(callback, "El callback no puede ser nulo");
        if (clienteController != null) {
            clienteController.setActualizarCallback(callback);
        }
    }
}