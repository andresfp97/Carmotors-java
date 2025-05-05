package com.carmotors.view;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;
import com.carmotors.controller.*;
import com.carmotors.model.Cliente;
import com.carmotors.model.DetalleTrabajoRepuesto;
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
    private PanelClienteActividad panelClienteActividad;
    private PanelFactura panelFactura;
    private PanelEvaluacionProveedor panelEvaluacionProveedor;
    private final int MIN_MENU_WIDTH = 250;

    public VentanaPrincipal(RepuestoDAO repuestoDAO, ProveedorDAO proveedorDAO,
            ClienteDAO clienteDAO, LoteDAO loteDAO,
            VehiculoDAO vehiculoDAO, Runnable actualizarCallback,
            ServicioDAO servicioDAO, TrabajoDAO trabajoDAO,
            DetalleTrabajoRepuestoDAO detalleTrabajoDAO,
            ActividadEspecialDAO actividadEspecialDAO, ClienteActividadDAO clienteActividadDAO,
            EvaluacionProveedorDAO evaluacionProveedorDAO, FacturaDAO facturaDAO,
            DetalleFacturaDAO detalleFacturaDAO) {

        setTitle("Sistema de Gestión de Repuestos y Proveedores");
        setSize(1200, 700);
        setMinimumSize(new Dimension(800, 500));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        initComponents();

        initControllers(repuestoDAO, proveedorDAO, clienteDAO, loteDAO,
                vehiculoDAO, actualizarCallback, servicioDAO,
                trabajoDAO, detalleTrabajoDAO,
                actividadEspecialDAO, clienteActividadDAO,
                evaluacionProveedorDAO, facturaDAO, detalleFacturaDAO);
    }

    private void initComponents() {
        panelIzquierdo = new JPanel();
        panelIzquierdo.setLayout(new BoxLayout(panelIzquierdo, BoxLayout.Y_AXIS));
        panelIzquierdo.setBackground(new Color(25, 20, 20));

        cardLayout = new CardLayout();
        panelDerecho = new JPanel(cardLayout);
        panelDerecho.setBackground(new Color(40, 35, 35));

        // Inicialización de todos los paneles
        panelRepuesto = new PanelRepuesto();
        panelProveedor = new PanelProveedor();
        panelCliente = new PanelCliente();
        panelVehiculo = new PanelVehiculo();
        panelServicio = new PanelServicio();
        panelTrabajo = new PanelTrabajo();
        panelDetalleTrabajo = new PanelDetalleTrabajoRepuesto();
        panelActividadEspecial = new PanelActividadEspecial();
        panelClienteActividad = new PanelClienteActividad();
        panelEvaluacionProveedor = new PanelEvaluacionProveedor();
        panelFactura = new PanelFactura(); // Initialize panelFactura first

        // Agregar paneles al cardLayout
        panelDerecho.add(panelRepuesto, "Repuestos");
        panelDerecho.add(panelProveedor, "Proveedores");
        panelDerecho.add(panelCliente, "Clientes");
        panelDerecho.add(panelVehiculo, "Vehiculo");
        panelDerecho.add(panelServicio, "Servicios");
        panelDerecho.add(panelTrabajo, "Trabajos");
        panelDerecho.add(panelDetalleTrabajo, "DetallesTrabajo");
        panelDerecho.add(panelActividadEspecial, "Actividades Especiales");
        panelDerecho.add(panelClienteActividad, "Cliente Actividad");
        panelDerecho.add(panelEvaluacionProveedor, "Evaluacion Proveedor");
        panelDerecho.add(panelFactura, "Factura");

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelIzquierdo, panelDerecho);
        splitPane.setDividerSize(5);
        splitPane.setDividerLocation(MIN_MENU_WIDTH);
        add(splitPane);

        crearBotonesMenu();
    }

    private void initControllers(RepuestoDAO repuestoDao, ProveedorDAO proveedorDao,
            ClienteDAO clienteDao, LoteDAO loteDao,
            VehiculoDAO vehiculoDAO, Runnable actualizarCallback,
            ServicioDAO servicioDAO, TrabajoDAO trabajoDAO,
            DetalleTrabajoRepuestoDAO detalleTrabajoDAO,
            ActividadEspecialDAO actividadEspecialDAO, ClienteActividadDAO clienteActividadDAO,
            EvaluacionProveedorDAO evaluacionProveedorDAO, FacturaDAO facturaDAO,
            DetalleFacturaDAO detalleFacturaDAO) {

        new RepuestoController(panelRepuesto, repuestoDao);
        new ProveedorController(panelProveedor, proveedorDao);
        this.clienteController = new ClienteController(panelCliente, clienteDao, actualizarCallback);
        new LoteController(panelRepuesto, loteDao);
        new VehiculoController(panelVehiculo, vehiculoDAO, clienteDao);
        new ServicioController(panelServicio, servicioDAO);
        new TrabajoController(panelTrabajo, trabajoDAO, vehiculoDAO, servicioDAO);
        new DetalleTrabajoRepuestoController(panelDetalleTrabajo, detalleTrabajoDAO, loteDao, trabajoDAO);
        new ActividadEspecialController(panelActividadEspecial, actividadEspecialDAO);
        new ClienteActividadController(panelClienteActividad, clienteActividadDAO);
        new EvaluacionProveedorController(panelEvaluacionProveedor, evaluacionProveedorDAO, proveedorDao);

        // Initialize FacturaController and set the listener for "Generar Factura"
        // Inicializar FacturaController
        FacturaController facturaController = new FacturaController(
                panelFactura,
                facturaDAO,
                detalleFacturaDAO, // Usar el parámetro recibido
                clienteDao, // Usar clienteDao (el parámetro recibido)
                trabajoDAO,
                vehiculoDAO);

        // Configurar el panel
        panelFactura.setFacturaController(facturaController);
        panelFactura.setTrabajoDAO(trabajoDAO);

        // Cargar trabajos facturables
        facturaController.cargarTrabajosFacturables();

        // Configurar listener (opcional, puede hacerse en el constructor del
        // controlador)

        panelFactura.setGenerarFacturaListener(e -> facturaController.generarFacturaDesdeVista());
    }

    private void crearBotonesMenu() {
        JButton btnRepuestos = crearBotonMenu("Gestión de Inventario", "Repuestos");
        JButton btnProveedores = crearBotonMenu("Proveedores y Compras", "Proveedores");
        JButton btnClientes = crearBotonMenu("Clientes y Facturación", "Clientes");
        JButton btnVehiculo = crearBotonMenu("Gestión Vehículo", "Vehiculo");
        JButton btnServicios = crearBotonMenu("Servicios", "Servicios");
        JButton btnTrabajos = crearBotonMenu("Trabajos", "Trabajos");
        JButton btnEvaluacion = crearBotonMenu("Evaluacion Proveedor", "Evaluacion Proveedor");
        JButton btnFactura = crearBotonMenu("Factura", "Factura");

        JButton btnDetallesTrabajo = crearBotonMenu("Detalles Trabajo", "DetallesTrabajo");
        JButton btnActividades = new JButton("Actividades Especiales");
        JButton btnClienteActividad = new JButton("Cliente Actividad");

        btnRepuestos.setForeground(Color.WHITE);
        btnProveedores.setForeground(Color.WHITE);
        btnClientes.setForeground(Color.WHITE);
        btnActividades.setForeground(Color.WHITE);
        btnClienteActividad.setBackground(Color.WHITE);
        btnEvaluacion.setBackground(Color.WHITE);
        btnFactura.setBackground(Color.WHITE);

        btnRepuestos.setBackground(new Color(25, 20, 20));
        btnProveedores.setBackground(new Color(25, 20, 20));
        btnClientes.setBackground(new Color(25, 20, 20));
        btnActividades.setBackground(new Color(25, 20, 20));
        btnClienteActividad.setBackground(new Color(25, 20, 20));
        btnEvaluacion.setBackground(new Color(25, 20, 20));
        btnFactura.setBackground(new Color(25, 20, 20));

        btnRepuestos.setBorderPainted(false);
        btnProveedores.setBorderPainted(false);
        btnClientes.setBorderPainted(false);
        btnActividades.setBorderPainted(false);
        btnClienteActividad.setBorderPainted(false);
        btnEvaluacion.setBorderPainted(false);
        btnFactura.setBorderPainted(false);

        btnRepuestos.addActionListener(e -> cardLayout.show(panelDerecho, "Repuestos"));
        btnProveedores.addActionListener(e -> cardLayout.show(panelDerecho, "Proveedores"));
        btnClientes.addActionListener(e -> cardLayout.show(panelDerecho, "Clientes"));
        btnActividades.addActionListener(e -> cardLayout.show(panelDerecho, "Actividades Especiales"));
        btnClienteActividad.addActionListener(e -> cardLayout.show(panelDerecho, "Cliente Actividad"));
        btnEvaluacion.addActionListener(e -> cardLayout.show(panelDerecho, "Evaluacion Proveedor"));
        btnFactura.addActionListener(e -> cardLayout.show(panelDerecho, "Factura"));

        panelIzquierdo.add(btnRepuestos);
        panelIzquierdo.add(Box.createRigidArea(new Dimension(0, 10)));
        panelIzquierdo.add(btnProveedores);
        panelIzquierdo.add(Box.createRigidArea(new Dimension(0, 10)));
        panelIzquierdo.add(btnClientes);
        panelIzquierdo.add(Box.createRigidArea(new Dimension(0, 10)));
        panelIzquierdo.add(btnVehiculo);
        panelIzquierdo.add(Box.createRigidArea(new Dimension(0, 10)));
        panelIzquierdo.add(btnServicios);
        panelIzquierdo.add(Box.createRigidArea(new Dimension(0, 10)));
        panelIzquierdo.add(btnTrabajos);
        panelIzquierdo.add(Box.createRigidArea(new Dimension(0, 10)));
        panelIzquierdo.add(btnDetallesTrabajo);
        panelIzquierdo.add(btnActividades);
        panelIzquierdo.add(Box.createRigidArea(new Dimension(0, 10)));
        panelIzquierdo.add(btnClienteActividad);
        panelIzquierdo.add(Box.createRigidArea(new Dimension(0, 10)));
        panelIzquierdo.add(btnEvaluacion);
        panelIzquierdo.add(Box.createRigidArea(new Dimension(0, 10)));
        panelIzquierdo.add(btnFactura);
        // Remove duplicate button
        // panelIzquierdo.add(btnFactura);
        panelIzquierdo.add(Box.createRigidArea(new Dimension(0, 10)));
    }

    private JButton crearBotonMenu(String texto, String comando) {
        JButton boton = new JButton(texto);
        boton.setForeground(Color.WHITE);
        boton.setBackground(new Color(25, 20, 20));
        boton.setBorderPainted(false);
        boton.setAlignmentX(Component.CENTER_ALIGNMENT);
        boton.setMaximumSize(new Dimension(Integer.MAX_VALUE, boton.getMinimumSize().height));
        boton.addActionListener(e -> cardLayout.show(panelDerecho, comando));
        return boton;
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