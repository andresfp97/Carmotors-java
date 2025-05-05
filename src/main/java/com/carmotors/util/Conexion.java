package com.carmotors.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class Conexion {
    private static Conexion instance;
    private HikariDataSource dataSource;

    public Conexion() {

        try {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl("jdbc:mysql://localhost:3306/carmotors");
            config.setUsername("root");
            config.setPassword("root"); // Revisar
            config.setMaximumPoolSize(10);
            config.setMinimumIdle(5);
            config.setConnectionTimeout(60000);
            config.addDataSourceProperty("cachePrepStmts", "true");
            config.addDataSourceProperty("prepStmtCacheSize", "250");
            config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

            dataSource = new HikariDataSource(config);
            System.out.println("✅ Pool de conexiones HikariCP inicializado correctamente");
        } catch (Exception e) {
            System.err.println("❌ Error al inicializar pool de conexiones");
            e.printStackTrace();
            throw new RuntimeException("Error al inicializar pool de conexiones", e);
        }
    }

    public static synchronized Conexion getConexion() {
        if (instance == null) {
            instance = new Conexion();
        }
        return instance;
    }

    public Connection getConnection() {
        try {
            Connection conn = dataSource.getConnection();
            System.out.println("🔹 Conexión obtenida del pool");
            return conn;
        } catch (SQLException e) {
            System.err.println("❌ Error al obtener conexión del pool");
            e.printStackTrace();
            throw new RuntimeException("Error al obtener conexión", e);
        }
    }
}