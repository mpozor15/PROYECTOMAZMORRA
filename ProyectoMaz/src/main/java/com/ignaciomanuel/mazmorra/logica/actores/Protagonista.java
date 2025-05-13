package com.ignaciomanuel.mazmorra.logica.actores;

import com.ignaciomanuel.mazmorra.Principal;
import com.ignaciomanuel.mazmorra.logica.Celda;
import com.ignaciomanuel.mazmorra.logica.TipoCelda;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Protagonista extends Actor {
    private int salud;
    private int fuerza;
    private int defensa;
    private int velocidad;

    public Protagonista(Celda celda, int salud, int fuerza, int defensa, int velocidad) {
        super(celda);
        this.salud     = salud;
        this.fuerza    = fuerza;
        this.defensa   = defensa;
        this.velocidad = velocidad;
    }

    // Getters
    public int getSalud()     { return salud; }
    public int getFuerza()    { return fuerza; }
    public int getDefensa()   { return defensa; }
    public int getVelocidad() { return velocidad; }

    // Setters
    public void setSalud(int salud)         { this.salud = salud; }
    public void setFuerza(int fuerza)       { this.fuerza = fuerza; }
    public void setDefensa(int defensa)     { this.defensa = defensa; }
    public void setVelocidad(int velocidad) { this.velocidad = velocidad; }

    // Recibir daño y actualizar UI
    public void recibirDano(int dano) {
        this.salud -= dano;
        Principal.registrarEvento("💢 Protagonista recibió " + dano + " de daño. Salud restante: " + salud);
        Principal.getInstancia()
                 .getControlador()
                 .getLblSalud()
                 .setText(String.valueOf(this.salud));
    }

    // Atacar a un enemigo
    public void atacar(Enemigo enemigo) {
        int reduccion = enemigo.getDefensa() / 2;
        int dano = this.fuerza - reduccion;
        if (dano < 1) dano = 1;

        enemigo.recibirDano(dano);
        Principal.registrarEvento("🗡️ Atacas al enemigo por " + dano + " puntos de daño.");
        if (enemigo.getSalud() <= 0) {
            Principal.registrarEvento("🏆 ¡Enemigo derrotado!");
            Celda celdaEnemigo = enemigo.getCelda();
            celdaEnemigo.setActor(null);
            Principal.eliminarActor(enemigo);
        }
    }

    /**
     * Movimiento con combate y victoria.
     * Si pisa la salida tras eliminar enemigos, muestra alerta de victoria.
     */
    @Override
    public void mover(int dx, int dy) {
        int nx = celda.getX() + dx;
        int ny = celda.getY() + dy;
        Celda destino = celda.getMapa().getCelda(nx, ny);
        if (destino == null) return;

        // Meta (salida)
        if (destino.getTipo() == TipoCelda.SALIDA) {
                Principal.registrarEvento("🏁 ¡Has alcanzado la salida! Victoria.");
                Platform.runLater(() -> {
                    Alert alerta = new Alert(AlertType.INFORMATION);
                    alerta.setTitle("¡Victoria!");
                    alerta.setHeaderText(null);
                    alerta.setContentText("🎉 Enhorabuena, has ganado la partida.");
                    alerta.showAndWait();
                    Platform.exit();
                });
            return;
        }

        // Suelo transitable o combate
        if (destino.getTipo().esTransitable()) {
            Actor ocupante = destino.getActor();
            if (ocupante == null) {
                celda.setActor(null);
                destino.setActor(this);
                this.celda = destino;
            } else if (ocupante instanceof Enemigo) {
                atacar((Enemigo) ocupante);
            }
        }
    }

    @Override
    public String getNombre() {
        return "Protagonista";
    }
}
