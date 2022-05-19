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

public class MainMenuPanel extends JPanel {
    public MainMenuPanel(ArkanoidFrame ventanaPrincipal) {
        setLayout(null);
        this.arkanoidFrame = ventanaPrincipal;
        iniciarFont();
        iniciarComponentes();
        setBackground(new Color(147, 176, 171));
        setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, Color.black, Color.black));
    }

    private void iniciarComponentes() {
        lbTextoSeleccionarNivel = new JLabel("Seleccionar nivel");
        lbTextoSeleccionarNivel.setFont(setFontSize(font, 17));
        lbTextoSeleccionarNivel.setForeground(Color.white);
        lbTextoSeleccionarNivel.setSize(lbTextoSeleccionarNivel.getPreferredSize());
        lbTextoSeleccionarNivel.setLocation(265, 55);
        lbTextoSeleccionarNivel.addMouseListener(mouseHandler);
        add(lbTextoSeleccionarNivel);

        seleccionarNivel = new JLabel(new ImageIcon(MainMenuPanel.class.getResource("resource\\img\\menu.png")));
        seleccionarNivel.setSize(seleccionarNivel.getPreferredSize());
        seleccionarNivel.setLocation(250, 20);
        seleccionarNivel.addMouseListener(mouseHandler);
        add(seleccionarNivel);

        lbCrearNivel = new JLabel("Crear nivel");
        lbCrearNivel.setFont(setFontSize(font, 17));
        lbCrearNivel.setForeground(Color.white);
        lbCrearNivel.setSize(lbCrearNivel.getPreferredSize());
        lbCrearNivel.setLocation(285, 175);
        lbCrearNivel.addMouseListener(mouseHandler);
        add(lbCrearNivel);

        crearNivel = new JLabel(new ImageIcon(MainMenuPanel.class.getResource("resource\\img\\menu.png")));
        crearNivel.setSize(crearNivel.getPreferredSize());
        crearNivel.setLocation(250, 140);
        crearNivel.addMouseListener(mouseHandler);
        add(crearNivel);

        lbPersonalizarSkins = new JLabel("Personalizar skins");
        lbPersonalizarSkins.setFont(setFontSize(font, 17));
        lbPersonalizarSkins.setForeground(Color.white);
        lbPersonalizarSkins.setSize(lbPersonalizarSkins.getPreferredSize());
        lbPersonalizarSkins.setLocation(257, 290);
        lbPersonalizarSkins.addMouseListener(mouseHandler);
        add(lbPersonalizarSkins);

        personalizarSkins = new JLabel(new ImageIcon(MainMenuPanel.class.getResource("resource\\img\\menu.png")));
        personalizarSkins.setSize(personalizarSkins.getPreferredSize());
        personalizarSkins.setLocation(250, 260);
        personalizarSkins.addMouseListener(mouseHandler);
        add(personalizarSkins);

        lbMusica = new JLabel("Musica " + (musica ? "on" : "off"));
        lbMusica.setSize(lbMusica.getPreferredSize());
        lbMusica.setLocation(20, 450);
        lbMusica.addMouseListener(mouseHandler);
        add(lbMusica);
    }

    private Font setFontSize(Font font, int size) {
        Map<TextAttribute, Object> map = new Hashtable<TextAttribute, Object>();
        map.put(TextAttribute.SIZE, size);

        return font.deriveFont(map);
    }

    private void iniciarFont() {
        try {
            font = Font.createFont(Font.TRUETYPE_FONT,
                    new File(MainMenuPanel.class.getResource("resource\\StreamerDemo-eZ4Dg.otf").toURI()));
        } catch (FontFormatException | IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private class MouseHandler extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getSource() == lbTextoSeleccionarNivel || e.getSource() == seleccionarNivel) {
                arkanoidFrame.mainMenuPanel.setVisible(false);
                arkanoidFrame.getContentPane().removeAll();

                arkanoidFrame.levelSelectionPanel = new LevelSelectionPanel(arkanoidFrame);
                arkanoidFrame.mainMenuPanel = null;

                arkanoidFrame.add(arkanoidFrame.levelSelectionPanel, BorderLayout.CENTER);
            }

            if (e.getSource() == lbCrearNivel || e.getSource() == crearNivel) {
                arkanoidFrame.mainMenuPanel.setVisible(false);
                arkanoidFrame.getContentPane().removeAll();

                arkanoidFrame.creationLevelPanel = new CreationLevelPanel(arkanoidFrame);
                arkanoidFrame.mainMenuPanel = null;
                
                arkanoidFrame.add(arkanoidFrame.creationLevelPanel, BorderLayout.CENTER);
            }

            if (e.getSource() == lbPersonalizarSkins || e.getSource() == personalizarSkins) {
                arkanoidFrame.skinPersonalizatonPanel = new SkinPersonalizatonPanel(arkanoidFrame);
                arkanoidFrame.add(arkanoidFrame.skinPersonalizatonPanel, BorderLayout.CENTER);

                arkanoidFrame.mainMenuPanel.setVisible(false);
                arkanoidFrame.mainMenuPanel = null;
            }

            if (e.getSource() == lbMusica) {
                musica = !musica;
                lbMusica.setText("Musica " + (musica ? "on" : "off"));
                lbMusica.setSize(lbMusica.getPreferredSize());

                if (musica) {
                    arkanoidFrame.soundsEffect.loop();
                } else {
                    arkanoidFrame.soundsEffect.clip.stop();
                }
            }
        }

    }

    JLabel lbTextoSeleccionarNivel;
    JLabel seleccionarNivel;
    JLabel lbCrearNivel;
    JLabel crearNivel;
    JLabel lbPersonalizarSkins;
    JLabel personalizarSkins;
    JLabel lbMusica;
    boolean musica = true;
    ArkanoidFrame arkanoidFrame;
    MouseHandler mouseHandler = new MouseHandler();
    Font font;
}
