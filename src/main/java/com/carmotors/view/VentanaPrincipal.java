/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.carmotors.view;

/**
 *
 * @author ANDRES
 */


import javax.swing.*;
import java.awt.*;

import com.carmotors.controller.ClienteController;
import com.carmotors.controller.LoteController;
import com.carmotors.controller.RepuestoController;
import com.carmotors.controller.ProveedorController;
import com.carmotors.modelDAO.ClienteDAO;
import com.carmotors.modelDAO.LoteDAO;
import com.carmotors.modelDAO.ProveedorDAO;
import com.carmotors.modelDAO.RepuestoDAO;

public class VentanaPrincipal extends JFrame {
    private JPanel panelDerecho;
    private CardLayout cardLayout;
    private JPanel panelIzquierdo;
    private PanelRepuesto panelRepuesto;
    private PanelProveedor panelProveedor;
    private PanelCliente panelCliente;
    private final int MIN_MENU_WIDTH = 250;

    public VentanaPrincipal(RepuestoDAO repuestoDAO, ProveedorDAO proveedorDAO, ClienteDAO clienteDAO, LoteDAO loteDAO) {
        setTitle("Sistema de Gestión de Repuestos y Proveedores");
        setSize(1200, 700);
        setMinimumSize(new Dimension(800, 500));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        initComponents();
        initControllers(repuestoDAO, proveedorDAO, clienteDAO, loteDAO);
    }

    private void initComponents() {
        panelIzquierdo = new JPanel();
        panelIzquierdo.setLayout(new BoxLayout(panelIzquierdo, BoxLayout.Y_AXIS));
        panelIzquierdo.setBackground(new Color(25, 20, 20));

        cardLayout = new CardLayout();
        panelDerecho = new JPanel(cardLayout);
        panelDerecho.setBackground(new Color(40, 35, 35));

        panelRepuesto = new PanelRepuesto();
        panelProveedor = new PanelProveedor();
        panelCliente = new PanelCliente();

        panelDerecho.add(panelRepuesto, "Repuestos");
        panelDerecho.add(panelProveedor, "Proveedores");
        panelDerecho.add(panelCliente, "Clientes");

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelIzquierdo, panelDerecho);
        splitPane.setDividerSize(5);
        splitPane.setDividerLocation(MIN_MENU_WIDTH);
        add(splitPane);

        crearBotonesMenu();
    }

    private void initControllers(RepuestoDAO repuestoDao, ProveedorDAO proveedorDao, ClienteDAO clienteDao, LoteDAO loteDao) {
        new RepuestoController(panelRepuesto, repuestoDao);
        new ProveedorController(panelProveedor, proveedorDao);
        new ClienteController(panelCliente, clienteDao );
        new LoteController(panelRepuesto, loteDao);
    }

    private void crearBotonesMenu() {
        JButton btnRepuestos = new JButton("Gestion de Inventario");
        JButton btnProveedores = new JButton("Proveedores y Compras");
        JButton btnClientes = new JButton("Clientes y Facturacion");

        btnRepuestos.setForeground(Color.WHITE);
        btnProveedores.setForeground(Color.WHITE);
        btnClientes.setForeground(Color.WHITE);

        btnRepuestos.setBackground(new Color(25, 20, 20));
        btnProveedores.setBackground(new Color(25, 20, 20));
        btnClientes.setBackground(new Color(25, 20, 20));

        btnRepuestos.setBorderPainted(false);
        btnProveedores.setBorderPainted(false);
        btnClientes.setBorderPainted(false);

        btnRepuestos.addActionListener(e -> cardLayout.show(panelDerecho, "Repuestos"));
        btnProveedores.addActionListener(e -> cardLayout.show(panelDerecho, "Proveedores"));
        btnClientes.addActionListener(e -> cardLayout.show(panelDerecho, "Clientes"));

        panelIzquierdo.add(btnRepuestos);
        panelIzquierdo.add(Box.createRigidArea(new Dimension(0, 10)));
        panelIzquierdo.add(btnProveedores);
        panelIzquierdo.add(Box.createRigidArea(new Dimension(0, 10)));
        panelIzquierdo.add(btnClientes);
        panelIzquierdo.add(Box.createRigidArea(new Dimension(0, 10)));
    }
}