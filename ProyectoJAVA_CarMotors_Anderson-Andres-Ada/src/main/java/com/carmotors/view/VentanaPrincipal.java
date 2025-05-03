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
import com.carmotors.controller.RepuestoController;
import com.carmotors.controller.ProveedorController;
import com.carmotors.modelDAO.ProveedorDAO;
import com.carmotors.modelDAO.RepuestoDAO;

public class VentanaPrincipal extends JFrame {
    private JPanel panelDerecho;
    private CardLayout cardLayout;
    private JPanel panelIzquierdo;
    private PanelRepuesto panelRepuesto;
    private PanelProveedor panelProveedor;
    private final int MIN_MENU_WIDTH = 250;

    public VentanaPrincipal(RepuestoDAO repuestoDAO, ProveedorDAO proveedorDAO) {
        setTitle("Sistema de Gestión de Repuestos y Proveedores");
        setSize(1200, 700);
        setMinimumSize(new Dimension(800, 500));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        initComponents();
        initControllers(repuestoDAO, proveedorDAO);
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

        panelDerecho.add(panelRepuesto, "Repuestos");
        panelDerecho.add(panelProveedor, "Proveedores");

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelIzquierdo, panelDerecho);
        splitPane.setDividerSize(5);
        splitPane.setDividerLocation(MIN_MENU_WIDTH);
        add(splitPane);

        crearBotonesMenu();
    }

    private void initControllers(RepuestoDAO repuestoDAO, ProveedorDAO proveedorDAO) {
        new RepuestoController(panelRepuesto, repuestoDAO);
        new ProveedorController(panelProveedor, proveedorDAO);
    }

    private void crearBotonesMenu() {
        JButton btnRepuestos = new JButton("Repuestos");
        JButton btnProveedores = new JButton("Proveedores");

        btnRepuestos.setForeground(Color.WHITE);
        btnProveedores.setForeground(Color.WHITE);

        btnRepuestos.setBackground(new Color(25, 20, 20));
        btnProveedores.setBackground(new Color(25, 20, 20));

        btnRepuestos.setBorderPainted(false);
        btnProveedores.setBorderPainted(false);

        btnRepuestos.addActionListener(e -> cardLayout.show(panelDerecho, "Repuestos"));
        btnProveedores.addActionListener(e -> cardLayout.show(panelDerecho, "Proveedores"));

        panelIzquierdo.add(btnRepuestos);
        panelIzquierdo.add(Box.createRigidArea(new Dimension(0, 10)));
        panelIzquierdo.add(btnProveedores);
        panelIzquierdo.add(Box.createRigidArea(new Dimension(0, 10)));
    }
}