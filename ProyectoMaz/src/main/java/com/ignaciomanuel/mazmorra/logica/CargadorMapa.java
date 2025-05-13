package com.ignaciomanuel.mazmorra.logica;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.ignaciomanuel.mazmorra.ControladorInicio;
import com.ignaciomanuel.mazmorra.logica.actores.Enemigo;
import com.ignaciomanuel.mazmorra.logica.actores.Protagonista;

public class CargadorMapa {

    public static EstadoInicial cargarMapa() {
        try {
            var lector = new BufferedReader(new InputStreamReader(
                CargadorMapa.class.getResourceAsStream("/map.txt")
            ));
            StringBuilder sb = new StringBuilder();
            String linea;
            while ((linea = lector.readLine()) != null) {
                sb.append(linea).append("\n");
            }
            lector.close();

            String[] lineas = sb.toString().split("\n");
            int ancho = lineas[0].length();
            int alto  = lineas.length;

            MapaJuego mapa = new MapaJuego(ancho, alto);
            Protagonista protagonista = null;

            for (int y = 0; y < alto; y++) {
                linea = lineas[y];
                for (int x = 0; x < ancho; x++) {
                    char ch = linea.charAt(x);
                    Celda celda;

                    switch (ch) {
                        case '#':
                            celda = new Celda(mapa, x, y, TipoCelda.PARED);
                            break;
                        case 'P':
                            celda = new Celda(mapa, x, y, TipoCelda.SUELO);
                            protagonista = new Protagonista(
                                celda,
                                ControladorInicio.getSalud(),
                                ControladorInicio.getFuerza(),
                                ControladorInicio.getDefensa(),
                                ControladorInicio.getVelocidad()
                            );
                            break;
                        case 'E':
                            celda = new Celda(mapa, x, y, TipoCelda.SUELO);
                            new Enemigo(celda, 30, 10, 5, 2, 3);
                            break;
                        case 'M':
                            celda = new Celda(mapa, x, y, TipoCelda.SALIDA);
                            break;
                        default:
                            celda = new Celda(mapa, x, y, TipoCelda.SUELO);
                            break;
                    }

                    mapa.setCelda(x, y, celda);
                }
            }

            if (protagonista == null) {
                throw new IllegalStateException("No se encontró ningún protagonista 'P' en el mapa.");
            }

            return new EstadoInicial(mapa, protagonista);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
