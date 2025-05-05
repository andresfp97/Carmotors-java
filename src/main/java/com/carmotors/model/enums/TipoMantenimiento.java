package com.carmotors.model.enums;

public enum TipoMantenimiento {
    PREVENTIVO("Preventivo"),  // Changed to match database case
    CORRECTIVO("Correctivo");  // Changed to match database case

    private final String descripcion;

    TipoMantenimiento(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public static TipoMantenimiento fromString(String text) {
        for (TipoMantenimiento tipo : TipoMantenimiento.values()) {
            if (tipo.descripcion.equalsIgnoreCase(text)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Tipo de mantenimiento no válido: " + text);
    }
}