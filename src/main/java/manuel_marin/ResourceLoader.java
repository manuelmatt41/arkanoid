/*-
 * =====LICENSE-START=====
 * Java 11 Application
 * ------
 * Copyright (C) 2020 - 2022 Organization Name
 * ------
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * =====LICENSE-END=====
 */
package manuel_marin;

import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.ImageIcon;

public class ResourceLoader {
    /**
     * Lee un archivo de texto con una determinada estructura para crear el diseño
     * de nivel del juego. De forma que sea un 8x7 de numeros donde el 0 es "aire",
     * 1 es un ladrillo azul y 2 es un ladrillo rojo.
     * 
     * @param nivel Archivo que contiene el diseño de nivel.
     * @return Devuelve un array de Jlabel que representan los ladrillos que hay en
     *         el mapa.
     */
    public static Brick[] levelLoad(File nivel) {
        Brick[] mapa = new Brick[56];
        int contador = 0;
        int x = 0, y = 0;
        try (Scanner sc = new Scanner(nivel)) {
            while (sc.hasNext()) {
                String fila = sc.nextLine();

                if (fila.length() == 7) {

                    for (int i = 0; i < fila.length(); i++) {
                        if (fila.charAt(i) == '0') {
                            mapa[contador] = null;
                        }

                        if (fila.charAt(i) == '1') {
                            mapa[contador] = new BlueBrick(new Point(x, y));
                        }

                        if (fila.charAt(i) == '2') {
                            mapa[contador] = new RedBrick(new Point(x, y));
                        }

                        if (fila.charAt(i) == '3') {
                            mapa[contador] = new GrayBrick(new Point(x, y));
                        }

                        contador++;

                        if ((i + 1) % 7 == 0) {
                            x = 0;
                            y += 32;
                        } else {
                            x += 100;
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("No se ha podido acceder al archivo");
        }

        return mapa;
    }

    /**
     * Devuelve la imagen correpondiente al tipo de Imagen que se quiere
     * @param resourceType Tipo de objeto que se requiere su imagen
     * @return Devuelve la imagen correspndiente, si no se encuentra o no tiene devuelve la imagen por defecto del objeto
     */
    public static ImageIcon skinLoader(ResourcesImg resourceType) {
        if (resourceType.userFile != null) {
            if (resourceType.userFile.exists()) {
                return new ImageIcon(resourceType.userFile.getAbsolutePath());
            }
        }

        return skinLoaderResource(resourceType);

    }

    /**
     * Devuelve la imagen por defecto del tipo de objeto que contiene la imagen
     * @param resourceType Tipo de objeto que contiene la imagen
     * @return Devuelve la imagen por defecto del tipo de objeto
     */
    private static ImageIcon skinLoaderResource(ResourcesImg resourceType) {
        return new ImageIcon(resourceType.resourcePath);
    }
}
