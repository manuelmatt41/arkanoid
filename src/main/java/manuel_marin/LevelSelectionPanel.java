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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

public class LevelSelectionPanel extends JPanel {
    public LevelSelectionPanel(ArkanoidFrame ventanaPrincipal) {
        setLayout(null);

        this.arkanoidFrame = ventanaPrincipal;
        iniciarCantidadNiveles();
        int x = 25, y = 25;
        for (int i = 0; i < niveles.size(); i++) {
            JLabel b = new JLabel(
                    niveles.get(i).getName().substring(0, niveles.get(i).getName().lastIndexOf(".txt")));
            b.setSize(b.getPreferredSize());
            b.setLocation(x + 5, y + 10);
            b.addKeyListener(keyHandler);
            b.addMouseListener(mouseHandler);
            add(b);

            JLabel lb = new JLabel(new ImageIcon(LevelSelectionPanel.class.getResource("resource\\img\\menupequeño.png")));
            lb.setSize(lb.getPreferredSize());
            lb.setLocation(x, y);
            add(lb);

            if ((i + 1) % 6 == 0) {
                x = 25;
                y += 60;
            } else {
                x += 110;
            }
        }

        setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, Color.black, Color.black));
    }

    private void iniciarCantidadNiveles() {
        File carpetaNiveles = null;
        try {
            carpetaNiveles = new File(LevelSelectionPanel.class.getResource("resource\\niveles").toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < carpetaNiveles.listFiles().length; i++) {
            niveles.add(carpetaNiveles.listFiles()[i]);
        }

        carpetaNiveles = new File(System.getProperty("user.home") + "\\AppData\\Roaming\\arkanoid\\niveles");

        for (int i = 0; i < carpetaNiveles.listFiles().length; i++) {
            niveles.add(carpetaNiveles.listFiles()[i]);
        }
    }

    private class mouseHandler extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent e) {
            
            for (int i = 0; i < niveles.size(); i++) {
                if (niveles.get(i) != null) {
                    
                    if (niveles.get(i).getName().contains(((JLabel)e.getSource()).getText())) {
                        arkanoidFrame.levelSelectionPanel.setVisible(false);
                        arkanoidFrame.getContentPane().removeAll();
                        arkanoidFrame.removeKeyListener(keyHandler);

                        arkanoidFrame.gameDataPanel = new GameDataPanel();
                        arkanoidFrame.gamePanel = new GamePanel(arkanoidFrame, niveles.get(i).getAbsolutePath());
                        arkanoidFrame.levelSelectionPanel = null;
                        
                        arkanoidFrame.add(arkanoidFrame.gameDataPanel, BorderLayout.NORTH);
                        arkanoidFrame.add(arkanoidFrame.gamePanel, BorderLayout.CENTER);
                        arkanoidFrame.addKeyListener(arkanoidFrame.gamePanel.movimientoPlataforma);
                    }
                }
            }

        }


    }

    private class KeyHandler extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                arkanoidFrame.levelSelectionPanel.setVisible(false);
                arkanoidFrame.getContentPane().removeAll();
                arkanoidFrame.removeKeyListener(keyHandler);
                
                arkanoidFrame.mainMenuPanel = new MainMenuPanel(arkanoidFrame);
                arkanoidFrame.add(arkanoidFrame.mainMenuPanel, BorderLayout.CENTER);
            }
        }
    }

    ArrayList<File> niveles = new ArrayList<>();
    ArkanoidFrame arkanoidFrame;
    mouseHandler mouseHandler = new mouseHandler();
    KeyHandler keyHandler = new KeyHandler();
}
