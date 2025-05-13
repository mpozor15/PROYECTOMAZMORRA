package com.ignaciomanuel.mazmorra.logica;

import com.ignaciomanuel.mazmorra.logica.actores.Protagonista;

public class EstadoInicial {
    public MapaJuego mapa;
    public Protagonista protagonista;

    public EstadoInicial(MapaJuego mapa, Protagonista protagonista) {
        this.mapa = mapa;
        this.protagonista = protagonista;
    }

    public MapaJuego getMapa() {
        return mapa;
    }

    public Protagonista getProtagonista() {
        return protagonista;
    }
}
