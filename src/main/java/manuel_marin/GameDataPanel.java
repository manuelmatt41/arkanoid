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

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

/**
 * Clase se contiene los datos de la partida.
 */
public class GameDataPanel extends JPanel {
    /**
     * Inicializa las propiedades de los parametros.
     */
    public GameDataPanel() {
        setLayout(new FlowLayout(FlowLayout.CENTER, 40, 0));
        puntuacion = 0;
        vidas = 3;

        lblPuntuacion = new JLabel("Puntuacion: " + puntuacion);
        lblPuntuacion.setFont(new Font("sans serif", Font.BOLD, 16));
        lblPuntuacion.setSize(lblPuntuacion.getPreferredSize());
        add(lblPuntuacion);

        lblVidas = new JLabel("Vidas: " + vidas);
        lblVidas.setFont(new Font("sans serif", Font.BOLD, 16));
        lblVidas.setSize(lblVidas.getPreferredSize());
        add(lblVidas);

        setBackground(ArkanoidFrame.BACKGROUND_COLOR);
        setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, Color.black, Color.black));
        setFocusable(false);
    }

    /**
     * Actuliza las Jlabel.
     */
    public void updateLabels() {
        lblPuntuacion.setText("Puntuacion: " + puntuacion);
        lblPuntuacion.setSize(lblPuntuacion.getPreferredSize());
        lblVidas.setText("Vidas: " + vidas);
        lblVidas.setSize(lblVidas.getPreferredSize());
    }

    /**
     * Muestra la puntuacion de la partida.
     */
    JLabel lblPuntuacion;
    /**
     * Muestra las vidad de la partida.
     */
    JLabel lblVidas;
    /**
     * valor de la puntuacion de la partida.
     */
    int puntuacion;
    /**
     * Valor de las vidad de la partida.
     */
    int vidas;
}
