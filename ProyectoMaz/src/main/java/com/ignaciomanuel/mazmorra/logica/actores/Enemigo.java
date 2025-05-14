package com.ignaciomanuel.mazmorra.logica.actores;

import com.ignaciomanuel.mazmorra.Principal;
import com.ignaciomanuel.mazmorra.logica.Celda;

public class Enemigo extends Actor {
    private final int maxSalud;
    private int salud;
    private int fuerza;
    private int defensa;
    private int velocidad;
    private int percepcion;

    public Enemigo(Celda celda, int salud, int fuerza, int defensa, int velocidad, int percepcion) {
        super(celda);
        this.maxSalud   = salud;
        this.salud      = salud;
        this.fuerza     = fuerza;
        this.defensa    = defensa;
        this.velocidad  = velocidad;
        this.percepcion = percepcion;
    }

    // Getters
    public int getSalud()      { return salud; }
    public int getFuerza()     { return fuerza; }
    public int getDefensa()    { return defensa; }
    public int getVelocidad()  { return velocidad; }
    public int getPercepcion() { return percepcion; }
    public int getMaxSalud()   { return maxSalud; }

    // Setters
    public void setSalud(int s)     { this.salud = s; }
    public void setFuerza(int f)    { this.fuerza = f; }
    public void setDefensa(int d)   { this.defensa = d; }
    public void setVelocidad(int v) { this.velocidad = v; }
    public void setPercepcion(int p){ this.percepcion = p; }

    /** Recibe daño y registra evento */
    public void recibirdaño(int daño) {
        this.salud -= daño;
        Principal.registrarEvento("💢 Enemigo recibió " + daño + " de daño. Salud restante: " + salud);
    }

    /** Ataca al protagonista */
    public void atacar(Protagonista p) {
        int reduccion = p.getDefensa() / 2;
        int daño = this.fuerza - reduccion;
        if (daño < 1) daño = 1;

        p.recibirdaño(daño);
        Principal.registrarEvento("⚔️ Enemigo ataca por " + daño + " de daño.");
        if (p.getSalud() <= 0) {
            Principal.gameOver();
        }
    }

    /** Movimiento con combate */
    @Override
    public void mover(int dx, int dy) {
        int nx = celda.getX() + dx;
        int ny = celda.getY() + dy;
        Celda destino = celda.getMapa().getCelda(nx, ny);
        if (destino == null) return;

        if (destino.getTipo().esTransitable()) {
            Actor occ = destino.getActor();
            if (occ == null) {
                celda.setActor(null);
                destino.setActor(this);
                this.celda = destino;
            } else if (occ instanceof Protagonista) {
                atacar((Protagonista) occ);
            }
        }
    }

    /** Estrategia simple: acercarse/atacar */
    public void moverInteligente(Protagonista p) {
        int dx = Integer.compare(p.getCelda().getX(), celda.getX());
        int dy = Integer.compare(p.getCelda().getY(), celda.getY());
        mover(dx, dy);
    }

    @Override
    public String getNombre() {
        return "Enemigo";
    }
}