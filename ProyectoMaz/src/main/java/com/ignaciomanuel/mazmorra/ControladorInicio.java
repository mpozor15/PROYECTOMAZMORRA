package com.ignaciomanuel.mazmorra.logica.actores;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ControladorInicio {

    @FXML private TextField txtNombre;
    @FXML private TextField txtSalud;
    @FXML private TextField txtFuerza;
    @FXML private TextField txtDefensa;
    @FXML private TextField txtVelocidad;

    private static String nombre  = "Protagonista";
    private static int    salud   = 100;
    private static int    fuerza  = 20;
    private static int    defensa = 10;
    private static int    velocidad = 3;

    /** Handler para el botón “Iniciar juego” */
    @FXML
    private void handleIniciarJuego() {
        String n = txtNombre.getText().trim();
        if (!n.isEmpty()) {
            nombre = n;
        }
        try {
            salud     = Integer.parseInt(txtSalud.getText().trim());
            fuerza    = Integer.parseInt(txtFuerza.getText().trim());
            defensa   = Integer.parseInt(txtDefensa.getText().trim());
            velocidad = Integer.parseInt(txtVelocidad.getText().trim());
        } catch (NumberFormatException e) {
            System.err.println("⚠️ Entrada inválida: " + e.getMessage());
            // podrías mostrar una alerta de error aquí
        }
        // cerrar ventana
        Stage s = (Stage) txtNombre.getScene().getWindow();
        s.close();
    }

    // getters estáticos para Principal y ControladorJuego
    public static String getNombre()   { return nombre; }
    public static int    getSalud()    { return salud; }
    public static int    getFuerza()   { return fuerza; }
    public static int    getDefensa()  { return defensa; }
    public static int    getVelocidad(){ return velocidad; }
}
