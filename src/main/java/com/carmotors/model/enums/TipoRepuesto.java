package com.carmotors.model.enums;

public enum TipoRepuesto {
    Mecanico("Mecánico"),
    Electrico("Eléctrico"),
    Carroceria("Carrocería"),
    Consumo("Consumo");

    private final String descripcion;

    TipoRepuesto(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return this.name();
    }

    public static TipoRepuesto fromString(String text) {
        for (TipoRepuesto tipo : TipoRepuesto.values()) {
            if (tipo.descripcion.equalsIgnoreCase(text)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Tipo de repuesto no válido: " + text);
    }
}
