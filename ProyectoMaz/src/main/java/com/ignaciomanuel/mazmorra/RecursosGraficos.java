package com.ignaciomanuel.mazmorra;

import javafx.scene.image.Image;

public class RecursosGraficos {
    public static Image imagenPared;
    public static Image imagenSuelo;
    public static Image imagenProtagonista;
    public static Image imagenEnemigo;
    public static Image imagenMeta;

    public static void cargarImagenes() {
        imagenPared = new Image("/pared.png");
        imagenSuelo = new Image("/suelo.png");
        imagenProtagonista = new Image("/protagonista.png");
        imagenEnemigo = new Image("/enemigo.png");
        imagenMeta = new Image("/meta.png");
    }
}