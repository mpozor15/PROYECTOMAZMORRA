package com.ignaciomanuel.mazmorra.logica.actores;

import com.ignaciomanuel.mazmorra.Principal;
import com.ignaciomanuel.mazmorra.logica.Celda;

public class Enemigo extends Actor {
    private int salud;
    private int fuerza;
    private int defensa;
    private int velocidad;
    private int percepcion;

    public Enemigo(Celda celda, int salud, int fuerza, int defensa, int velocidad, int percepcion) {
        super(celda);
        this.salud      = salud;
        this.fuerza     = fuerza;
        this.defensa    = defensa;
        this.velocidad  = velocidad;
        this.percepcion = percepcion;
    }

    // Getters y setters...
    public int getSalud()      { return salud; }
    public void setSalud(int s){ this.salud = s; }
    public int getFuerza()     { return fuerza; }
    public void setFuerza(int f){ this.fuerza = f; }
    public int getDefensa()    { return defensa; }
    public void setDefensa(int d){ this.defensa = d; }
    public int getVelocidad()  { return velocidad; }
    public void setVelocidad(int v){ this.velocidad = v; }
    public int getPercepcion() { return percepcion; }
    public void setPercepcion(int p){ this.percepcion = p; }

    // Recibe daño
    public void recibirDano(int dano) {
        this.salud -= dano;
        Principal.registrarEvento("💢 Enemigo recibió " + dano + " de daño. Salud restante: " + salud);
    }

    // Ataca al protagonista
    public void atacar(Protagonista p) {
        int reduccion = p.getDefensa() / 2;
        int dano = this.fuerza - reduccion;
        if (dano < 1) dano = 1;

        p.recibirDano(dano);
        Principal.registrarEvento("⚔️ Enemigo ataca y causa " + dano + " de daño.");

        if (p.getSalud() <= 0) {
            Principal.gameOver();
        }
    }

    /** 
     * Movimiento con combate: 
     * si la celda está libre, se mueve; 
     * si hay un protagonista, ataca.
     */
    @Override
    public void mover(int dx, int dy) {
        int nx = celda.getX() + dx;
        int ny = celda.getY() + dy;
        Celda destino = celda.getMapa().getCelda(nx, ny);
        if (destino != null && destino.getTipo().esTransitable()) {
            Actor ocupante = destino.getActor();
            if (ocupante == null) {
                // moverse
                celda.setActor(null);
                destino.setActor(this);
                this.celda = destino;
            } else if (ocupante instanceof Protagonista) {
                // atacar al héroe
                atacar((Protagonista) ocupante);
            }
        }
    }

    /**
     * Estrategia simple: se acerca un paso al protagonista
     * o ataca si queda adyacente.
     */
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
