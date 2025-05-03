/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.carmotors.run;

import com.carmotors.controller.ProveedorController;
import com.carmotors.controller.RepuestoController;
import com.carmotors.modelDAO.ProveedorDAO;
import com.carmotors.modelDAO.RepuestoDAO;
import com.carmotors.view.PanelProveedor;
import com.carmotors.view.PanelRepuesto;
import com.carmotors.view.VentanaPrincipal;
import javax.swing.SwingUtilities;

/**
 *
 * @author ANDRES
 */
public class ProyectoJAVA_CarMotors_AndersonAndresAda {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            RepuestoDAO dao = new RepuestoDAO();
            ProveedorDAO pdao = new ProveedorDAO();
            
            VentanaPrincipal vista = new VentanaPrincipal(dao, pdao);
            vista.setVisible(true);
        });
  
    }
}
