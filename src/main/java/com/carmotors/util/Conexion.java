package com.carmotors.util;

/**
 *
 * @author ANDRES
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.sql.*;

public class Conexion {

    private Connection con = null;
    private static Conexion db;
    private Statement statement;

    private String url = "jdbc:mysql://localhost:3306/";
    private String dbName = "carmotors";
    private String driver = "com.mysql.cj.jdbc.Driver";  // ✅ Usa el driver moderno
    private String userName = "root";
    private String password = "";

    public Conexion() {
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url + dbName, userName, password);
            System.out.println("✅ Conexión exitosa.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Conexion getConexion() {
        if (db == null) {
            db = new Conexion();
        }
        return db;
    }

    public Connection getConnection() {
        return this.con;
    }

    public void cerrarConexion() {
        try {
            if (con != null && !con.isClosed()) {
                con.close();
                System.out.println("🔒 Conexión cerrada.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
