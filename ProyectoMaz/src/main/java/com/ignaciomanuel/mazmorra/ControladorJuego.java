package com.ignaciomanuel.mazmorra;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

public class ControladorJuego {

    @FXML
    private Canvas canvas;

    @FXML
    private Label lblSalud;

    @FXML
    private Label lblTurno;

    @FXML
    private Label lblEnemigos;

    @FXML
    private VBox panelEnemigos;

    @FXML
    private TextArea areaEventos;

    // Getters públicos para que Principal acceda a los elementos si los necesita
    public Canvas getCanvas() {
        return canvas;
    }

    public Label getLblSalud() {
        return lblSalud;
    }

    public Label getLblTurno() {
        return lblTurno;
    }

    public Label getLblEnemigos() {
        return lblEnemigos;
    }

    public VBox getPanelEnemigos() {
        return panelEnemigos;
    }

    public TextArea getAreaEventos() {
        return areaEventos;
    }
}
