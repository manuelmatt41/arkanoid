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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

/**
 * Clase que hereda de JPanel para gestionar el menu de seleccion de niveles.
 */
public class LevelSelectionPanel extends JPanel {
    /**
     * Inicializa las propiedades de los parametros, las caracteristicas del panel.
     * @param ventanaPrincipal Valor del JFrame principal.
     */
    public LevelSelectionPanel(ArkanoidFrame ventanaPrincipal) {
        setLayout(null);

        this.arkanoidFrame = ventanaPrincipal;
        getLevelResource();
        getLevelUser();
        startComponent();

        setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, Color.black, Color.black));
        setBackground(arkanoidFrame.BACKGROUND_COLOR);
    }

    /**
     * Inicia los componentes del JPanel.
     */
    private void startComponent() {
        int x = 25, y = 25;
        for (int i = 0; i < levelsFiles.size(); i++) {
            JLabel b = new JLabel(
                    levelsFiles.get(i).getName().substring(0, levelsFiles.get(i).getName().lastIndexOf(".txt")));
            b.setFont(new Font("Consolas", Font.BOLD, 16));
            b.setForeground(Color.white);
            b.setSize(100, 60);
            b.setLocation(x + 5, y + 10);
            b.setOpaque(true);
            b.setBackground(arkanoidFrame.OPTION_COLOR_UNSELECTED);
            b.setBorder(BorderFactory.createEtchedBorder(Color.gray, Color.gray));
            b.addKeyListener(levelCreationKeyEventManager);
            b.addMouseListener(levelCreationMouseEventManager);
            add(b);

            if ((i + 1) % 6 == 0) {
                x = 25;
                y += 60;
            } else {
                x += 110;
            }
        }
    }

    /**
     * Lee los archivos de los niveles por defecto del juego.
     */
    private void getLevelResource() {
        boolean error = false;
        for (int i = 0; i < ResourceLevels.values().length; i++) {
            try {
                levelsFiles.add(new File(ResourceLevels.values()[i].resourcePath.toURI()));
            } catch (URISyntaxException e) {
                error = true;
            }
        }

        if (error) {
            JOptionPane.showMessageDialog(null, "No se han cargado los niveles por defecto");
        }
    }

    /**
     * Lee los archivos de los niveles creados por el usuario.
     */
    private void getLevelUser() {
        File levelsUser = new File(System.getProperty("user.home") + "\\AppData\\Roaming\\arkanoid\\niveles");

        for (int i = 0; i < levelsUser.listFiles().length; i++) {
            levelsFiles.add(levelsUser.listFiles()[i]);
        }

    }

    /**
     * Clase que maneja los eventos de raton de {@link #LevelSelectionPanel()}
     */
    private class LevelCreationMouseEventManager extends MouseAdapter {

        /**
         * Inicializa el panel del juego.
         */
        @Override
        public void mouseClicked(MouseEvent e) {

            for (int i = 0; i < levelsFiles.size(); i++) {
                if (levelsFiles.get(i) != null) {
                    if (levelsFiles.get(i).getName().contains(((JLabel) e.getSource()).getText())) {
                        arkanoidFrame.levelSelectionPanel.setVisible(false);
                        arkanoidFrame.getContentPane().removeAll();
                        arkanoidFrame.removeKeyListener(levelCreationKeyEventManager);

                        arkanoidFrame.gameDataPanel = new GameDataPanel(arkanoidFrame);
                        arkanoidFrame.gamePanel = new GamePanel(arkanoidFrame, levelsFiles.get(i));
                        arkanoidFrame.levelSelectionPanel = null;

                        arkanoidFrame.add(arkanoidFrame.gameDataPanel, BorderLayout.NORTH);
                        arkanoidFrame.add(arkanoidFrame.gamePanel, BorderLayout.CENTER);
                        arkanoidFrame.addKeyListener(arkanoidFrame.gamePanel.platformMovement);
                    }
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

    /**
     * Clase que maneja los eventos de teclado de {@link #LevelSelectionPanel()}.
     */
    private class LevelCreationKeyEventManager extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                arkanoidFrame.levelSelectionPanel.setVisible(false);
                arkanoidFrame.getContentPane().removeAll();
                arkanoidFrame.removeKeyListener(levelCreationKeyEventManager);

                arkanoidFrame.levelSelectionPanel = null;
                arkanoidFrame.mainMenuPanel = new MainMenuPanel(arkanoidFrame);
                arkanoidFrame.add(arkanoidFrame.mainMenuPanel, BorderLayout.CENTER);
            }
        }
    }

    /**
     * Niveles para iniciar el menu.
     */
    ArrayList<File> levelsFiles = new ArrayList<>();
    ArkanoidFrame arkanoidFrame;
    LevelCreationMouseEventManager levelCreationMouseEventManager = new LevelCreationMouseEventManager();
    LevelCreationKeyEventManager levelCreationKeyEventManager = new LevelCreationKeyEventManager();
}
