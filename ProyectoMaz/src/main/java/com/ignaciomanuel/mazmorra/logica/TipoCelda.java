package com.ignaciomanuel.mazmorra.logica;

public enum TipoCelda {
    PARED,
    SUELO,
    SALIDA;

    public boolean esTransitable() {
        return this == SUELO || this == SALIDA;
    }

    public boolean esMeta() {
        return this == SALIDA;
    }
}
