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

import javax.swing.JButton;
import javax.swing.JPanel;

public class MainMenuPanel extends JPanel {
    public MainMenuPanel(ArkanoidFrame ventanaPrincipal) {
        setLayout(null);
        this.arkanoidFrame = ventanaPrincipal;

        btSeleccionarNivel = new JButton("Selecionar nivel");
        btSeleccionarNivel.setSize(btSeleccionarNivel.getPreferredSize());
        btSeleccionarNivel.setLocation(0, 0);
        btSeleccionarNivel.addActionListener(actionHandler);
        add(btSeleccionarNivel);

        btCrearNivel = new JButton("Crear nivel");
        btCrearNivel.setSize(btCrearNivel.getPreferredSize());
        btCrearNivel.setLocation(150, 0);
        btCrearNivel.addActionListener(actionHandler);
        add(btCrearNivel);

        btPersonalizarSkins = new JButton("Personalizar Skins");
        btPersonalizarSkins.setSize(btPersonalizarSkins.getPreferredSize());
        btPersonalizarSkins.setLocation(250, 0);
        btPersonalizarSkins.addActionListener(actionHandler);
        add(btPersonalizarSkins);
    }

    private class ActionHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == btSeleccionarNivel) {
                arkanoidFrame.levelSelectionPanel = new LevelSelectionPanel(arkanoidFrame);
                arkanoidFrame.add(arkanoidFrame.levelSelectionPanel, BorderLayout.CENTER);

                arkanoidFrame.mainMenuPanel.setVisible(false);
                arkanoidFrame.remove(arkanoidFrame.mainMenuPanel);
            }

            if (e.getSource() == btCrearNivel) {
                arkanoidFrame.creationLevelPanel = new CreationLevelPanel(arkanoidFrame);
                arkanoidFrame.add(arkanoidFrame.creationLevelPanel, BorderLayout.CENTER);

                arkanoidFrame.mainMenuPanel.setVisible(false);
                arkanoidFrame.remove(arkanoidFrame.mainMenuPanel);
            }

            if (e.getSource() == btPersonalizarSkins) {
                arkanoidFrame.skinPersonalizatonPanel = new SkinPersonalizatonPanel(arkanoidFrame);
                arkanoidFrame.add(arkanoidFrame.skinPersonalizatonPanel, BorderLayout.CENTER);

                arkanoidFrame.mainMenuPanel.setVisible(false);
                arkanoidFrame.remove(arkanoidFrame.mainMenuPanel);
            }
        }

    }

    JButton btSeleccionarNivel;
    JButton btCrearNivel;
    JButton btPersonalizarSkins;
    ArkanoidFrame arkanoidFrame;
    ActionHandler actionHandler = new ActionHandler();
}
