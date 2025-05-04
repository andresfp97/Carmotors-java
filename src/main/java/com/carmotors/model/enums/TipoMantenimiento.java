package com.carmotors.model.enums;

public enum TipoMantenimiento {
    PREVENTIVO("preventivo"),
    CORRECTIVO("correctivo");

    private final String descripcion;

    TipoMantenimiento(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    // Método para convertir de String a Enum
    public static TipoMantenimiento fromString(String text) {
        for (TipoMantenimiento estado : TipoMantenimiento.values()) {
            if (estado.descripcion.equalsIgnoreCase(text)) {
                return estado;
            }
        }
        throw new IllegalArgumentException("Estado no válido: " + text);
    }
}