package com.carmotors.model.enums;

public enum EstadoServicio {
    PENDIENTE("Pendiente"),
    EN_PROCESO("En Proceso"),
    COMPLETADO("Completado"),
    CANCELADO("Cancelado");

    private final String descripcion;

    EstadoServicio(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    // Método para convertir de String a Enum
    public static EstadoServicio fromString(String text) {
        for (EstadoServicio estado : EstadoServicio.values()) {
            if (estado.descripcion.equalsIgnoreCase(text)) {
                return estado;
            }
        }
        throw new IllegalArgumentException("Estado no válido: " + text);
    }

    @Override
    public String toString() {
        return "EstadoServicio{" +
                "descripcion='" + descripcion + '\'' +
                '}';
    }
}