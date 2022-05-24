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
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Hashtable;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

/**
 * Clase que hereda de JPanel para gestionar el menu principal del juego.
 */
public class MainMenuPanel extends JPanel {

    /**
     * Inicializa las propiedades de los parametros y las caracteristicas del panel
     * @param arkanoidFrame
     */
    public MainMenuPanel(ArkanoidFrame arkanoidFrame) {
        setLayout(null);
        this.arkanoidFrame = arkanoidFrame;
        musica = true;
        createCustomFont();
        startComponents();
        setBackground(new Color(147, 176, 171));
        setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, Color.black, Color.black));
    }

    /**
     * Crea los componentes de la clase.
     */
    private void startComponents() {
        levelSelectionText = new JLabel("Seleccionar nivel");
        levelSelectionText.setFont(setFontSize(font, 17));
        levelSelectionText.setForeground(Color.white);
        levelSelectionText.setSize(200, 75);
        levelSelectionText.setLocation(265, 50);
        levelSelectionText.setOpaque(true);
        levelSelectionText.setBackground(arkanoidFrame.OPTION_COLOR_UNSELECTED);
        levelSelectionText.setBorder(BorderFactory.createEtchedBorder(Color.gray, Color.gray));
        levelSelectionText.addMouseListener(mainMenuMouseEventManager);
        add(levelSelectionText);

        levelCreationText = new JLabel("Crear nivel");
        levelCreationText.setFont(setFontSize(font, 17));
        levelCreationText.setForeground(Color.white);
        levelCreationText.setSize(200, 75);
        levelCreationText.setLocation(265, 150);
        levelCreationText.setOpaque(true);
        levelCreationText.setBackground(arkanoidFrame.OPTION_COLOR_UNSELECTED);
        levelCreationText.setBorder(BorderFactory.createEtchedBorder(Color.gray, Color.gray));
        levelCreationText.addMouseListener(mainMenuMouseEventManager);
        add(levelCreationText);

        skinPersonalizationText = new JLabel("Personalizar skins");
        skinPersonalizationText.setFont(setFontSize(font, 17));
        skinPersonalizationText.setForeground(Color.white);
        skinPersonalizationText.setSize(200, 75);
        skinPersonalizationText.setLocation(265, 250);
        skinPersonalizationText.setOpaque(true);
        skinPersonalizationText.setBackground(arkanoidFrame.OPTION_COLOR_UNSELECTED);
        skinPersonalizationText.setBorder(BorderFactory.createEtchedBorder(Color.gray, Color.gray));
        skinPersonalizationText.addMouseListener(mainMenuMouseEventManager);
        add(skinPersonalizationText);

        programExitText = new  JLabel("salir");
        programExitText.setFont(setFontSize(font, 17));
        programExitText.setForeground(Color.white);
        programExitText.setSize(200, 75);
        programExitText.setLocation(265, 350);
        programExitText.setOpaque(true);
        programExitText.setBackground(arkanoidFrame.OPTION_COLOR_UNSELECTED);
        programExitText.setBorder(BorderFactory.createEtchedBorder(Color.gray, Color.gray));
        programExitText.addMouseListener(mainMenuMouseEventManager);
        add(programExitText);

        musicText = new JLabel("Musica " + (musica ? "on" : "off"));
        musicText.setFont(setFontSize(font, 12));
        musicText.setForeground(Color.white);
        musicText.setSize(80, 80);
        musicText.setLocation(20, 350);
        musicText.setOpaque(true);
        musicText.setBackground(arkanoidFrame.OPTION_COLOR_UNSELECTED);
        musicText.setBorder(BorderFactory.createEtchedBorder(Color.gray, Color.gray));
        musicText.addMouseListener(mainMenuMouseEventManager);
        add(musicText);
    }

    /**
     * Crea una Font personalizada y si no es capaz de hacerlo devuelve una Font por defecto.
     */
    public void createCustomFont() {
        try {
            font = Font.createFont(Font.TRUETYPE_FONT,
                    new File(MainMenuPanel.class.getResource("resource\\StreamerDemo-eZ4Dg.otf").toURI()));
        } catch (FontFormatException | IOException | URISyntaxException e) {
            font = new Font("Arial", Font.PLAIN, 16);
        }
    }

    /**
     * Reescala el tamaño de la Font que se le pasa como parametro y la devuelve reescalada.
     * @param font Fuente que se va a reescalar.
     * @param size Tamaño que va a tener la Font.
     * @return Devuelve la Font reescalada.
     */
    public Font setFontSize(Font font, int size) {
        Map<TextAttribute, Object> map = new Hashtable<TextAttribute, Object>();
        map.put(TextAttribute.SIZE, size);

        return font.deriveFont(map);
    }

    /**
     * Clase encargada de manejar los eventos de ratón de {@link #MainMenuPanel()}.
     */
    private class MainMenuMouseEventManager extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            //Eliminan el panel actual de la ventana y crean el respectivo panel que se va.
            if (e.getSource() == levelSelectionText) {
                arkanoidFrame.mainMenuPanel.setVisible(false);
                arkanoidFrame.getContentPane().removeAll();

                arkanoidFrame.levelSelectionPanel = new LevelSelectionPanel(arkanoidFrame);
                arkanoidFrame.mainMenuPanel = null;

                arkanoidFrame.add(arkanoidFrame.levelSelectionPanel, BorderLayout.CENTER);
                arkanoidFrame.addKeyListener(arkanoidFrame.levelSelectionPanel.levelCreationKeyEventManager);
            }

            if (e.getSource() == levelCreationText) {
                arkanoidFrame.mainMenuPanel.setVisible(false);
                arkanoidFrame.getContentPane().removeAll();

                arkanoidFrame.creationLevelPanel = new CreationLevelPanel(arkanoidFrame);
                arkanoidFrame.mainMenuPanel = null;
                
                arkanoidFrame.add(arkanoidFrame.creationLevelPanel, BorderLayout.CENTER);
                arkanoidFrame.addKeyListener(arkanoidFrame.creationLevelPanel.keyHandler);
            }

            if (e.getSource() == skinPersonalizationText) {
                arkanoidFrame.mainMenuPanel.setVisible(false);
                arkanoidFrame.getContentPane().removeAll();
                
                arkanoidFrame.skinPersonalizatonPanel = new SkinPersonalizatonPanel(arkanoidFrame);
                arkanoidFrame.mainMenuPanel = null;

                arkanoidFrame.add(arkanoidFrame.skinPersonalizatonPanel, BorderLayout.CENTER);
                arkanoidFrame.addKeyListener(arkanoidFrame.skinPersonalizatonPanel.keyHandler);
            }

            if (e.getSource() == programExitText) {
                System.exit(0);
            }

            if (e.getSource() == musicText) {
                musica = !musica;
                musicText.setText("Musica " + (musica ? "on" : "off"));
                musicText.setSize(80, 80);

                if (musica) {
                    arkanoidFrame.soundsEffect.loop();
                } else {
                    arkanoidFrame.soundsEffect.clip.stop();
                }
            }
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            JLabel l = (JLabel) e.getSource();

            l.setBackground(arkanoidFrame.OPTION_COLOR_SELECTED);
        }

        @Override
        public void mouseExited(MouseEvent e) {
            JLabel l = (JLabel) e.getSource();

            l.setBackground(arkanoidFrame.OPTION_COLOR_UNSELECTED);
        }

    }

    JLabel levelSelectionText;
    JLabel levelCreationText;
    JLabel skinPersonalizationText;
    JLabel programExitText;
    JLabel musicText;
    ArkanoidFrame arkanoidFrame;
    MainMenuMouseEventManager mainMenuMouseEventManager = new MainMenuMouseEventManager();
    /**
     * Fuente personalizada que se utiliza en el juego.
     */
    Font font;
    /**
     * Valor que si es true la musica esta encendida y false esta apagada.
     */
    boolean musica;
}
