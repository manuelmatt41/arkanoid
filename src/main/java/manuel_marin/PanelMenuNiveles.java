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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JPanel;

public class PanelMenuNiveles extends JPanel {
    public PanelMenuNiveles(VentanaPrincipal ventanaPrincipal) {
        setLayout(null);

        this.ventanaPrincipal = ventanaPrincipal;
        iniciarCantidadNiveles();
        int x = 30, y = 30;
        for (int i = 0; i < niveles.length; i++) {
            JButton b = new JButton("Nivel " + (i + 1));
            b.setActionCommand(niveles[i].getAbsolutePath());
            b.setSize(b.getPreferredSize());
            b.setLocation(x, y);
            b.addActionListener(actionManagement);
            b.addKeyListener(keyHandler);
            add(b);

            if ((i + 1) % 6 == 0) {
                x = 30;
                y += 30;
            } else {
                x += 75;
            }
        }

    }

    private void iniciarCantidadNiveles() {
        File carpetaNiveles = new File(PanelMenuNiveles.class.getResource("niveles").getPath());

        niveles = carpetaNiveles.listFiles();
    }

    private class ActionManagement implements ActionListener {
        public ActionManagement(PanelMenuNiveles panelMenuJuego) {
            this.panelMenuJuego = panelMenuJuego;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            ventanaPrincipal.panelPuntuacion = new PanelDatosPartida();
            ventanaPrincipal.add(ventanaPrincipal.panelPuntuacion, BorderLayout.NORTH);

            ventanaPrincipal.panelJuego = new PanelJuego(ventanaPrincipal, e.getActionCommand());
            ventanaPrincipal.add(ventanaPrincipal.panelJuego, BorderLayout.CENTER);
            
            panelMenuJuego.setVisible(false);
        }

        PanelMenuNiveles panelMenuJuego;
    }

    private class KeyHandler extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                ventanaPrincipal.panelMenuPrincipal = new PanelMenuPrincipal(ventanaPrincipal);
                ventanaPrincipal.add(ventanaPrincipal.panelMenuPrincipal, BorderLayout.CENTER);

                ventanaPrincipal.panelMenuJuego.setVisible(false);
                ventanaPrincipal.remove(ventanaPrincipal.panelMenuJuego);
            }
        }
    }

    File[] niveles;
    VentanaPrincipal ventanaPrincipal;
    ActionManagement actionManagement = new ActionManagement(this);
    KeyHandler keyHandler = new KeyHandler();
}
