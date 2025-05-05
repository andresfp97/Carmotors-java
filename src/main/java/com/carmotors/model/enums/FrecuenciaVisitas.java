package com.carmotors.model.enums;

public enum FrecuenciaVisitas {
    DIARIA("Diaria "),
    SEMANAL("Semanal"),
    MENSUAL("Mensual"),
    TRIMESTRAL("Trimestral"),
    QUINCENAL("Quincenal"),
    ANUAL("Anual");

    private final String descripcion;

    FrecuenciaVisitas(String descripcion) {
            this.descripcion = descripcion;
        }

    public String getDescripcion() {
        return descripcion;
    }

    public static FrecuenciaVisitas fromString(String text) {
        if (text == null) {
            return null;
        }

        for (FrecuenciaVisitas estado : FrecuenciaVisitas.values()) {
            if (estado.descripcion.equalsIgnoreCase(text.trim())) {
                return estado;
            }
        }
        throw new IllegalArgumentException("Estado no válido: " + text);
    }
}