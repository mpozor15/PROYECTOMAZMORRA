package com.ignaciomanuel.mazmorra.logica;

import com.ignaciomanuel.mazmorra.Principal;
import com.ignaciomanuel.mazmorra.RecursosGraficos;
import com.ignaciomanuel.mazmorra.logica.actores.Actor;
import com.ignaciomanuel.mazmorra.logica.actores.Enemigo;

import javafx.scene.canvas.GraphicsContext;

public class Celda {
    private final MapaJuego mapa;
    private final int x;
    private final int y;
    private TipoCelda tipo;
    private Actor actor;

    public Celda(MapaJuego mapa, int x, int y, TipoCelda tipo) {
        this.mapa = mapa;
        this.x = x;
        this.y = y;
        this.tipo = tipo;
    }

    public void dibujar(GraphicsContext gc) {
        try {
            switch (tipo) {
                case PARED:
                    gc.drawImage(RecursosGraficos.imagenPared, x * Principal.TAMANO_CELDA, y * Principal.TAMANO_CELDA, Principal.TAMANO_CELDA, Principal.TAMANO_CELDA);
                    break;
                case SUELO:
                    gc.drawImage(RecursosGraficos.imagenSuelo, x * Principal.TAMANO_CELDA, y * Principal.TAMANO_CELDA, Principal.TAMANO_CELDA, Principal.TAMANO_CELDA);
                    break;
                case SALIDA:
                    // ¿Quedan enemigos?
                    boolean quedan = Principal.getActores().stream()
                     .anyMatch(a -> a instanceof Enemigo);
                    if (quedan) {
                    // si aún hay enemigos, dibuja suelo
                        gc.drawImage(RecursosGraficos.imagenSuelo,
                        x * Principal.TAMANO_CELDA,
                        y * Principal.TAMANO_CELDA,
                        Principal.TAMANO_CELDA,
                        Principal.TAMANO_CELDA);
                    } else {
                    // si no hay enemigos, dibuja la meta
                        gc.drawImage(RecursosGraficos.imagenMeta,
                        x * Principal.TAMANO_CELDA,
                        y * Principal.TAMANO_CELDA,
                         Principal.TAMANO_CELDA,
                        Principal.TAMANO_CELDA);
                    }
                break;

            }

            if (actor != null) {
                if ("Protagonista".equals(actor.getNombre())) {
                    gc.drawImage(RecursosGraficos.imagenProtagonista, x * Principal.TAMANO_CELDA, y * Principal.TAMANO_CELDA, Principal.TAMANO_CELDA, Principal.TAMANO_CELDA);
                } else if ("Enemigo".equals(actor.getNombre())) {
                    gc.drawImage(RecursosGraficos.imagenEnemigo, x * Principal.TAMANO_CELDA, y * Principal.TAMANO_CELDA, Principal.TAMANO_CELDA, Principal.TAMANO_CELDA);
                }
            }
        } catch (Exception e) {
            System.err.println("Error al dibujar celda en (" + x + "," + y + "): " + e.getMessage());
        }
        
    }

    public void setTipo(TipoCelda nuevoTipo) {
        this.tipo = nuevoTipo;
    }

    public TipoCelda getTipo() {
        return tipo;
    }

    public Actor getActor() {
        return actor;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public MapaJuego getMapa() {
        return mapa;
    }
}
