package com.ignaciomanuel.mazmorra.logica;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.ignaciomanuel.mazmorra.logica.actores.Actor;

public class MapaJuego {
    private final int ancho;
    private final int alto;
    private final Celda[][] celdas;
    private Actor protagonista;

    public MapaJuego(int ancho, int alto) {
        this.ancho = ancho;
        this.alto = alto;
        this.celdas = new Celda[ancho][alto];
    }

    public void setCelda(int x, int y, Celda celda) {
        celdas[x][y] = celda;
    }

    public Celda getCelda(int x, int y) {
        if (x >= 0 && y >= 0 && x < ancho && y < alto) {
            return celdas[x][y];
        }
        return null;
    }

    public Celda buscarCeldaProtagonista() {
        for (int y = 0; y < alto; y++) {
            for (int x = 0; x < ancho; x++) {
                Celda c = getCelda(x, y);
                if (c != null && c.getActor() != null && "Protagonista".equals(c.getActor().getNombre())) {
                    return c;
                }
            }
        }
        return null;
    }

    public void setProtagonista(Actor protagonista) {
        this.protagonista = protagonista;
    }

    public int getAncho() {
        return ancho;
    }

    public int getAlto() {
        return alto;
    }

    public List<Actor> getActoresOrdenadosPorVelocidad() {
        List<Actor> lista = new ArrayList<>();
        for (int y = 0; y < alto; y++) {
            for (int x = 0; x < ancho; x++) {
                Celda c = getCelda(x, y);
                if (c != null && c.getActor() != null) {
                    lista.add(c.getActor());
                }
            }
        }
        lista.sort(Comparator.comparingInt(Actor::getVelocidad).reversed());
        return lista;
    }

    public Celda buscarPrimeraCeldaLibre() {
        for (int y = 0; y < alto; y++) {
            for (int x = 0; x < ancho; x++) {
                Celda c = getCelda(x, y);
                if (c != null && c.getTipo().esTransitable() && c.getActor() == null) {
                    return c;
                }
            }
        }
        return null;
    }

    public void dibujar(javafx.scene.canvas.GraphicsContext gc) {
        for (int y = 0; y < alto; y++) {
            for (int x = 0; x < ancho; x++) {
                Celda c = getCelda(x, y);
                if (c != null) {
                    c.dibujar(gc);
                }
            }
        }
    }
}
