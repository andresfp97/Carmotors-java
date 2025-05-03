/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.carmotors.run;


import com.carmotors.model.Lote;
import com.carmotors.modelDAO.ClienteDAO;
import com.carmotors.modelDAO.LoteDAO;
import com.carmotors.modelDAO.ProveedorDAO;
import com.carmotors.modelDAO.RepuestoDAO;
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
            ClienteDAO cdao = new ClienteDAO();
            LoteDAO ldao = new LoteDAO();

            VentanaPrincipal vista = new VentanaPrincipal(dao, pdao, cdao, ldao);
            vista.setVisible(true);
        });
  
    }
}
