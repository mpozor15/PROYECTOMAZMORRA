package com.ignaciomanuel.mazmorra;

import java.util.List;

import com.ignaciomanuel.*;.mazmorra.logica.actores.Actor;
import com.ignaciomanuel.*;.mazmorra.logica.actores.Enemigo;
import com.ignaciomanuel.*;.mazmorra.logica.actores.Protagonista;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ControladorJuego {

    @FXML private Canvas canvas;
    @FXML private Label lblTituloEstado;
    @FXML private ProgressBar pbSalud;
    @FXML private Label lblTurnoDetail;
    @FXML private VBox vboxEnemigos;
    @FXML private TextArea areaEventos;

    public Canvas getCanvas() { return canvas; }
    public TextArea getAreaEventos() { return areaEventos; }

    /**
     * Refresca el panel derecho.
     */
    public void actualizarPanelDerecho(Protagonista p, List<Actor> actores, int turnoCount) {
        // Título con nombre
        lblTituloEstado.setText(ControladorInicio.getNombre());

        // Salud
        double pct = (double)p.getSalud() / p.getMaxSalud();
        pbSalud.setProgress(pct < 0 ? 0 : pct);

        // Turno
        lblTurnoDetail.setText(String.valueOf(turnoCount));

        // Enemigos
        vboxEnemigos.getChildren().clear();
        int idx = 1;
        for (Actor a : actores) {
            if (a instanceof Enemigo) {
                Enemigo e = (Enemigo)a;
                double ep = (double)e.getSalud() / e.getMaxSalud();
                ProgressBar bar = new ProgressBar(ep < 0 ? 0 : ep);
                bar.setPrefWidth(120);
                Label lab = new Label("Enemigo #" + (idx++));
                HBox row = new HBox(6, lab, bar);
                vboxEnemigos.getChildren().add(row);
            }
        }
    }
}
