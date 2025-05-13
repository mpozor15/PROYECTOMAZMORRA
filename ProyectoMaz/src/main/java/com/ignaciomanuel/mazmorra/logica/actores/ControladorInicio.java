package com.ignaciomanuel.mazmorra.logica.actores;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ControladorInicio {

    @FXML private TextField txtSalud;
    @FXML private TextField txtFuerza;
    @FXML private TextField txtDefensa;
    @FXML private TextField txtVelocidad;

    private static int salud = 100;
    private static int fuerza = 20;
    private static int defensa = 10;
    private static int velocidad = 3;

    @FXML
    private void handleIniciarJuego() {
        try {
            salud = Integer.parseInt(txtSalud.getText());
            fuerza = Integer.parseInt(txtFuerza.getText());
            defensa = Integer.parseInt(txtDefensa.getText());
            velocidad = Integer.parseInt(txtVelocidad.getText());

            Stage stage = (Stage) txtSalud.getScene().getWindow();
            stage.close(); // cerrar ventana
        } catch (NumberFormatException e) {
            System.err.println("⚠️ Entrada inválida: " + e.getMessage());
        }
    }

    public static int getSalud() { return salud; }
    public static int getFuerza() { return fuerza; }
    public static int getDefensa() { return defensa; }
    public static int getVelocidad() { return velocidad; }
}
