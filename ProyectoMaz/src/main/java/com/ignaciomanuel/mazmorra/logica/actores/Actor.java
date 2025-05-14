package com.ignaciomanuel.mazmorra.logica.actores;

import com.ignaciomanuel.mazmorra.logica.Celda;

public abstract class Actor {
    protected Celda celda;

    public Actor(Celda celda) {
        this.celda = celda;
        this.celda.setActor(this);
    }

    public abstract int getVelocidad();

    public abstract String getNombre();

    public Celda getCelda() {
        return celda;
    }

    public void mover(int dx, int dy) {
        int nuevoX = celda.getX() + dx;
        int nuevoY = celda.getY() + dy;
        Celda destino = celda.getMapa().getCelda(nuevoX, nuevoY);

        if (destino != null && destino.getTipo().esTransitable() && destino.getActor() == null) {
            celda.setActor(null);
            destino.setActor(this);
            this.celda = destino;
        }
    }
}

