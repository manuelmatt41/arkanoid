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
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 * Clase que hereda de JFrame para gestionar una ventana.
 */
public class ArkanoidFrame extends JFrame {
    /**
     * Inicializa las propiedades de los parametros.
     */
    public ArkanoidFrame() {
        super("Araknoid");
        setLayout(new BorderLayout());

        mainMenuPanel = new MainMenuPanel(this);
        add(mainMenuPanel, BorderLayout.CENTER);

        soundsEffect.loop();

        setFocusable(true);
        setUndecorated(true);
        setVisible(true);
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
                new ImageIcon(ArkanoidFrame.class.getResource("resource\\img\\cursor.png")).getImage(),
                new Point(), "myCursor"));
    }

    /**
     * Panel donde se encuentra el juego.
     */
    GamePanel gamePanel;
    /**
     * Panel donde se encuentra los datos de la partida.
     */
    GameDataPanel gameDataPanel;
    LevelSelectionPanel levelSelectionPanel;
    CreationLevelPanel creationLevelPanel;
    MainMenuPanel mainMenuPanel;
    SkinPersonalizatonPanel skinPersonalizatonPanel;
    SoundsEffect soundsEffect = new SoundsEffect("background.wav");
}
