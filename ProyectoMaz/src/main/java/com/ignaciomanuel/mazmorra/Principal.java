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

    public static Principal getInstancia() {
        return instancia;
    }

    public ControladorJuego getControlador() {
    return controlador;
    }

    public static MapaJuego getMapa() {
        return getInstancia().mapaJuego;
    }

    public static List<Actor> getActores() {
        return getInstancia().actores;
    }

    public static void registrarEvento(String mensaje) {
        getInstancia().controlador.getAreaEventos().appendText(mensaje + "\n");
    }

    public static void eliminarActor(Actor actor) {
        getInstancia().actores.remove(actor);
    }

    public static void gameOver() {
    registrarEvento("☠️ ¡Has muerto! Fin del juego.");
    // Mostrar alerta y salir de la aplicación
    Platform.runLater(() -> {
        Alert alerta = new Alert(AlertType.INFORMATION);
        alerta.setTitle("Game Over");
        alerta.setHeaderText(null);
        alerta.setContentText("Has muerto. El juego ha terminado.");
        alerta.showAndWait();
        Platform.exit();
    });
}

    @Override
    public void start(Stage primaryStage) throws Exception {
        instancia = this;

        // 1) Ventana de creación de personaje
        FXMLLoader inicioLoader = new FXMLLoader(Principal.class.getResource("/ventanaInicio.fxml"));
        Parent inicioRoot = inicioLoader.load();
        Stage ventanaInicio = new Stage();
        ventanaInicio.setTitle("Crear personaje");
        ventanaInicio.setScene(new Scene(inicioRoot));
        ventanaInicio.showAndWait();

        // 2) Cargar interfaz principal
        FXMLLoader loader = new FXMLLoader(Principal.class.getResource("/vistaJuego.fxml"));
        Parent root = loader.load();
        controlador = loader.getController();

        Scene scene = new Scene(root);
        primaryStage.setTitle("Mazmorra");
        primaryStage.setScene(scene);

        // 3) Manejo de teclas con switch clásico
        scene.setOnKeyPressed(event -> {
            boolean moved = false;
            switch (event.getCode()) {
                case W:
                case UP:
                    protagonista.mover(0, -1);
                    moved = true;
                    break;
                case S:
                case DOWN:
                    protagonista.mover(0, 1);
                    moved = true;
                    break;
                case A:
                case LEFT:
                    protagonista.mover(-1, 0);
                    moved = true;
                    break;
                case D:
                case RIGHT:
                    protagonista.mover(1, 0);
                    moved = true;
                    break;
                default:
                    // otras teclas no hacen nada
                    break;
            }

            if (moved) {
                // 3a) Redibujar
                redibujar();

                // 3b) Incrementar y mostrar turno
                turnoCount++;
                controlador.getLblTurno().setText(String.valueOf(turnoCount));

                // 3c) Turno de enemigos (instanceof + cast clásico)
                for (Actor a : new ArrayList<>(actores)) {
                    if (a instanceof Enemigo) {
                        Enemigo enemigo = (Enemigo) a;
                        enemigo.moverInteligente(protagonista);
                        redibujar();
                    }
                }

                // 3d) Actualizar número de enemigos vivos
                long vivos = 0;
                for (Actor a : actores) {
                    if (a instanceof Enemigo) vivos++;
                }
                controlador.getLblEnemigos().setText(String.valueOf(vivos));
            }
        });

        primaryStage.show();

        // 4) Dar foco al canvas para capturar teclas
        controlador.getCanvas().requestFocus();

        // 5) Cargar imágenes y mapa
        RecursosGraficos.cargarImagenes();
        EstadoInicial estado = CargadorMapa.cargarMapa();
        if (estado == null) {
            registrarEvento("❌ No se pudo cargar el mapa.");
            return;
        }
        mapaJuego = estado.getMapa();
        protagonista = estado.getProtagonista();

        // 6) Aplicar stats desde formulario
        protagonista.setSalud(ControladorInicio.getSalud());
        protagonista.setFuerza(ControladorInicio.getFuerza());
        protagonista.setDefensa(ControladorInicio.getDefensa());
        protagonista.setVelocidad(ControladorInicio.getVelocidad());

        actores.add(protagonista);
        actores.addAll(mapaJuego.getActoresOrdenadosPorVelocidad());

        // 7) Ajustar canvas al tamaño del mapa
        int anchoPx = mapaJuego.getAncho() * TAMANO_CELDA;
        int altoPx  = mapaJuego.getAlto()  * TAMANO_CELDA;
        controlador.getCanvas().setWidth(anchoPx);
        controlador.getCanvas().setHeight(altoPx);

        // 8) Inicializar etiquetas de turno y enemigos
        controlador.getLblTurno().setText("0");
        long countE = 0;
        for (Actor a : actores) if (a instanceof Enemigo) countE++;
        controlador.getLblEnemigos().setText(String.valueOf(countE));

        registrarEvento("✅ Juego iniciado correctamente.");
        redibujar();
    }

    private void redibujar() {
        mapaJuego.dibujar(controlador.getCanvas().getGraphicsContext2D());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
