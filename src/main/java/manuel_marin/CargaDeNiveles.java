package manuel_marin;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class CargaDeNiveles {
    /**
     * Lee un archivo de texto con una determinada estructura para crear el diseño
     * de nivel del juego. De forma que sea un 8x7 de numeros donde el 0 es "aire",
     * 1 es un ladrillo azul y 2 es un ladrillo rojo.
     * 
     * @param nivel Archivo que contiene el diseño de nivel.
     * @return Devuelve un array de Jlabel que representan los ladrillos que hay en
     *         el mapa.
     */
    public static JLabel[] cargarNivel(File nivel) {
        JLabel[] mapa = new JLabel[56];
        int contador = 0;
        try (Scanner sc = new Scanner(nivel)) {
            while (sc.hasNext()) {
                String fila = sc.nextLine();

                if (fila.length() == 7) {
                    for (int i = 0; i < fila.length(); i++) {
                        if (fila.charAt(i) == '0') {
                            mapa[contador] = null;
                        }

                        if (fila.charAt(i) == '1') {
                            JLabel brickAzul = new JLabel(
                                    new ImageIcon(CargaDeNiveles.class.getResource("\\img\\Brick1.png")));
                            mapa[contador] = brickAzul;
                        }

                        if (fila.charAt(i) == '2') {
                            JLabel brickRojo = new JLabel(
                                    new ImageIcon(CargaDeNiveles.class.getResource("\\img\\Brick2.png")));
                            mapa[contador] = brickRojo;
                        }

                        contador++;
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("No se ha podido acceder al archivo");
        }

        return mapa;
    }
}
