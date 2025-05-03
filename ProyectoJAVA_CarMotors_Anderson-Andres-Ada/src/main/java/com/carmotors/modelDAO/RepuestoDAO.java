/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.carmotors.modelDAO;
import com.carmotors.model.Repuesto;
import com.carmotors.util.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;
/**
 *
 * @author ANDRES
 */
public class RepuestoDAO implements CrudDAO<Repuesto> {
    private Connection con;

    public RepuestoDAO() {
        con = Conexion.getConexion().getConnection(); 
    }

    @Override
    public void agregar(Repuesto repuesto) {
        String sql = "insert into repuesto (nombre_repuesto, tipo_repuesto, marca, modelo_compatible, vida_util_estimada) values (?,?,?,?,?)";

        try(PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, repuesto.getNombre());
            pstmt.setString(2, repuesto.getTipo());
            pstmt.setString(3, repuesto.getMarca());
            pstmt.setString(4, repuesto.getModeloCompatible());

            if(repuesto.getVidaUtilEstimada() != null) {
                pstmt.setDate(5, new Date(repuesto.getVidaUtilEstimada().getTime()));
            } else {
                pstmt.setNull(5, java.sql.Types.DATE);
            }

            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error al agregar el repuesto: " + repuesto + "\n" + e.getMessage());
        }
    }


}