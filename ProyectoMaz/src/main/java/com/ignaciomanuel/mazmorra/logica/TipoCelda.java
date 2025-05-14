package com.ignaciomanuel.mazmorra.logica;

public enum TipoCelda {
    PARED,
    SUELO,
    SALIDA,
    TRAMPA;

    public boolean esTransitable() {
        return this == SUELO || this == SALIDA || this == TRAMPA;
    }

    public boolean esMeta() {
        return this == SALIDA;
    }
}
