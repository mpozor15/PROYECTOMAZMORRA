package com.ignaciomanuel.mazmorra;

import java.util.ArrayList;
import java.util.List;

import com.ignaciomanuel.mazmorra.logica.CargadorMapa;
import com.ignaciomanuel.mazmorra.logica.EstadoInicial;
import com.ignaciomanuel.mazmorra.logica.MapaJuego;
import com.ignaciomanuel.mazmorra.logica.actores.Actor;
import com.ignaciomanuel.mazmorra.logica.actores.Enemigo;
import com.ignaciomanuel.mazmorra.logica.actores.Protagonista;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


public class Principal extends Application {

    private static Principal instancia;
    public static final int TAMANO_CELDA = 32;

    private MapaJuego mapaJuego;
    private List<Actor> actores = new ArrayList<>();
    private Protagonista protagonista;
    private ControladorJuego controlador;
    private int turnoCount = 0;

    public static Principal getInstancia() { return instancia; }
    public ControladorJuego getControlador() { return controlador; }
    public static List<Actor> getActores() { return getInstancia().actores; }

    public static void registrarEvento(String msg) {
        getInstancia().controlador.getAreaEventos().appendText(msg + "\n");
    }
    public static void eliminarActor(Actor a) {
        getInstancia().actores.remove(a);
    }
    public static void gameOver() {
        registrarEvento("☠️ ¡Has muerto! Fin del juego.");
        Platform.runLater(Platform::exit);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        instancia = this;

        // 1) Ventana inicial para nombre y stats
        FXMLLoader inicioLoader = new FXMLLoader(getClass().getResource("/ventanaInicio.fxml"));
        Parent inicioRoot = inicioLoader.load();
        Stage initStage = new Stage();
        initStage.setScene(new Scene(inicioRoot));
        initStage.setTitle("Crear personaje");
        initStage.showAndWait();

        // 2) Carga de la vista principal
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistaJuego.fxml"));
        Parent root = loader.load();
        controlador = loader.getController();

        Scene scene = new Scene(root);
        primaryStage.setTitle("Mazmorra");
        primaryStage.setScene(scene);

        // 3) Manejo de teclas y turnos
        scene.setOnKeyPressed(event -> {
            boolean moved = false;
            switch (event.getCode()) {
                case W:
                    protagonista.mover(0, -1);
                    moved = true;
                    break;
                case S:
                    protagonista.mover(0, 1);
                    moved = true;
                    break;
                case A:
                    protagonista.mover(-1, 0);
                    moved = true;
                    break;
                case D:
                    protagonista.mover(1, 0);
                    moved = true;
                    break;
                default:
                    // otras teclas no hacen nada
                    break;
            }
            if (moved) {
                redibujar();
                turnoCount++;

                // Turno de enemigos
                for (Actor a : new ArrayList<>(actores)) {
                    if (a instanceof Enemigo) {
                        ((Enemigo)a).moverInteligente(protagonista);
                        redibujar();
                    }
                }
                // Actualiza panel derecho (incluye nombre, salud, turno, enemigos)
                controlador.actualizarPanelDerecho(protagonista, actores, turnoCount);
            }
        });

        primaryStage.show();
        controlador.getCanvas().requestFocus();

        // 4) Cargar mapa e imágenes
        RecursosGraficos.cargarImagenes();
        EstadoInicial estado = CargadorMapa.cargarMapa();
        if (estado == null) {
            registrarEvento("❌ No se pudo cargar el mapa.");
            return;
        }
        mapaJuego    = estado.getMapa();
        protagonista = estado.getProtagonista();

        // Aplica stats desde el formulario
        protagonista.setSalud(ControladorInicio.getSalud());
        protagonista.setFuerza(ControladorInicio.getFuerza());
        protagonista.setDefensa(ControladorInicio.getDefensa());
        protagonista.setVelocidad(ControladorInicio.getVelocidad());

        actores.add(protagonista);
        actores.addAll(mapaJuego.getActoresOrdenadosPorVelocidad());

        // Ajusta el canvas al tamaño del mapa
        controlador.getCanvas().setWidth(mapaJuego.getAncho() * TAMANO_CELDA);
        controlador.getCanvas().setHeight(mapaJuego.getAlto()  * TAMANO_CELDA);

        // Primer dibujado y sincroniza panel derecho
        redibujar();
        controlador.actualizarPanelDerecho(protagonista, actores, turnoCount);
    }

    private void redibujar() {
        mapaJuego.dibujar(controlador.getCanvas().getGraphicsContext2D());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
