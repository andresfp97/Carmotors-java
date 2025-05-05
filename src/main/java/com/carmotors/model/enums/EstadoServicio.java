package com.carmotors.model.enums;

public enum EstadoServicio {
    PENDIENTE("PENDIENTE"), // Cambiado para coincidir exactamente con la base de datos
    EN_PROCESO("EN_PROCESO"),
    COMPLETADO("COMPLETADO"),
    CANCELADO("CANCELADO");

    private final String descripcion;

    EstadoServicio(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public static EstadoServicio fromString(String text) {
        if (text == null) {
            return null;
        }
        
        for (EstadoServicio estado : EstadoServicio.values()) {
            if (estado.descripcion.equalsIgnoreCase(text.trim())) {
                return estado;
            }
        }
        throw new IllegalArgumentException("Estado no válido: " + text);
    }
}