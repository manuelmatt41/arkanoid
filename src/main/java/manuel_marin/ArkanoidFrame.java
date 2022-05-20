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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 * Clase que hereda de JFrame para gestionar la ventana del juego.
 */
public class ArkanoidFrame extends JFrame {
    /**
     * Inicializa las propiedades de los parametros, las caracteristicas de la ventana y la musica de fondo.
     */
    public ArkanoidFrame() {
        super("Araknoid");
        setLayout(new BorderLayout());

        //Contenido de la ventana
        mainMenuPanel = new MainMenuPanel(this);
        add(mainMenuPanel, BorderLayout.CENTER);

        //Musica de fondo
        soundsEffect.loop();

        //Caracteristicas de la ventana
        setFocusable(true);
        setUndecorated(true);
        setVisible(true);
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
                new ImageIcon(ArkanoidFrame.class.getResource("resource\\img\\cursor.png")).getImage(),
                new Point(), "myCursor"));
    }

    GamePanel gamePanel;
    GameDataPanel gameDataPanel;
    LevelSelectionPanel levelSelectionPanel;
    CreationLevelPanel creationLevelPanel;
    MainMenuPanel mainMenuPanel;
    SkinPersonalizatonPanel skinPersonalizatonPanel;
    SoundsEffect soundsEffect = new SoundsEffect("background.wav");
    public static final Color BACKGROUND_COLOR = new Color(147, 176, 171);
}
