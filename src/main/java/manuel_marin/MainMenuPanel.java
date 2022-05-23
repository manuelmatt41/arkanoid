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
import javax.swing.ImageIcon;
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
        levelSelectionText.setSize(levelSelectionText.getPreferredSize());
        levelSelectionText.setLocation(265, 55);
        levelSelectionText.addMouseListener(mainMenuMouseEventManager);
        add(levelSelectionText);

        selectionLevelBackground = new JLabel(new ImageIcon(MainMenuPanel.class.getResource("resource\\img\\menu.png")));
        selectionLevelBackground.setSize(selectionLevelBackground.getPreferredSize());
        selectionLevelBackground.setLocation(250, 20);
        selectionLevelBackground.addMouseListener(mainMenuMouseEventManager);
        add(selectionLevelBackground);

        levelCreationText = new JLabel("Crear nivel");
        levelCreationText.setFont(setFontSize(font, 17));
        levelCreationText.setForeground(Color.white);
        levelCreationText.setSize(levelCreationText.getPreferredSize());
        levelCreationText.setLocation(285, 175);
        levelCreationText.addMouseListener(mainMenuMouseEventManager);
        add(levelCreationText);

        levelCreationBackground = new JLabel(new ImageIcon(MainMenuPanel.class.getResource("resource\\img\\menu.png")));
        levelCreationBackground.setSize(levelCreationBackground.getPreferredSize());
        levelCreationBackground.setLocation(250, 140);
        levelCreationBackground.addMouseListener(mainMenuMouseEventManager);
        add(levelCreationBackground);

        skinPersonalizationText = new JLabel("Personalizar skins");
        skinPersonalizationText.setFont(setFontSize(font, 17));
        skinPersonalizationText.setForeground(Color.white);
        skinPersonalizationText.setSize(skinPersonalizationText.getPreferredSize());
        skinPersonalizationText.setLocation(257, 290);
        skinPersonalizationText.addMouseListener(mainMenuMouseEventManager);
        add(skinPersonalizationText);

        skinPersonalizationBackground = new JLabel(new ImageIcon(MainMenuPanel.class.getResource("resource\\img\\menu.png")));
        skinPersonalizationBackground.setSize(skinPersonalizationBackground.getPreferredSize());
        skinPersonalizationBackground.setLocation(250, 260);
        skinPersonalizationBackground.addMouseListener(mainMenuMouseEventManager);
        add(skinPersonalizationBackground);

        musicText = new JLabel("Musica " + (musica ? "on" : "off"));
        musicText.setSize(musicText.getPreferredSize());
        musicText.setLocation(20, 450);
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
            if (e.getSource() == levelSelectionText || e.getSource() == selectionLevelBackground) {
                arkanoidFrame.mainMenuPanel.setVisible(false);
                arkanoidFrame.getContentPane().removeAll();

                arkanoidFrame.levelSelectionPanel = new LevelSelectionPanel(arkanoidFrame);
                arkanoidFrame.mainMenuPanel = null;

                arkanoidFrame.add(arkanoidFrame.levelSelectionPanel, BorderLayout.CENTER);
                arkanoidFrame.addKeyListener(arkanoidFrame.levelSelectionPanel.levelCreationKeyEventManager);
            }

            if (e.getSource() == levelCreationText || e.getSource() == levelCreationBackground) {
                arkanoidFrame.mainMenuPanel.setVisible(false);
                arkanoidFrame.getContentPane().removeAll();

                arkanoidFrame.creationLevelPanel = new CreationLevelPanel(arkanoidFrame);
                arkanoidFrame.mainMenuPanel = null;
                
                arkanoidFrame.add(arkanoidFrame.creationLevelPanel, BorderLayout.CENTER);
                arkanoidFrame.addKeyListener(arkanoidFrame.creationLevelPanel.keyHandler);
            }

            if (e.getSource() == skinPersonalizationText || e.getSource() == skinPersonalizationBackground) {
                arkanoidFrame.mainMenuPanel.setVisible(false);
                arkanoidFrame.getContentPane().removeAll();
                
                arkanoidFrame.skinPersonalizatonPanel = new SkinPersonalizatonPanel(arkanoidFrame);
                arkanoidFrame.mainMenuPanel = null;

                arkanoidFrame.add(arkanoidFrame.skinPersonalizatonPanel, BorderLayout.CENTER);
                arkanoidFrame.addKeyListener(arkanoidFrame.skinPersonalizatonPanel.keyHandler);
            }

            if (e.getSource() == musicText) {
                musica = !musica;
                musicText.setText("Musica " + (musica ? "on" : "off"));
                musicText.setSize(musicText.getPreferredSize());

                if (musica) {
                    arkanoidFrame.soundsEffect.loop();
                } else {
                    arkanoidFrame.soundsEffect.clip.stop();
                }
            }
        }

    }

    JLabel levelSelectionText;
    JLabel selectionLevelBackground;
    JLabel levelCreationText;
    JLabel levelCreationBackground;
    JLabel skinPersonalizationText;
    JLabel skinPersonalizationBackground;
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
