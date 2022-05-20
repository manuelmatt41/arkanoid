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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EtchedBorder;

public class CreationLevelPanel extends JPanel {
    public CreationLevelPanel(ArkanoidFrame ventanaPrincipal) {
        setLayout(null);
        this.ventanaPrincipal = ventanaPrincipal;
        iniciarComponentes();

        addKeyListener(keyHandler);
        setFocusable(true);
        setBackground(ArkanoidFrame.BACKGROUND_COLOR);
        setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, Color.black, Color.black));
    }

    private void iniciarComponentes() {
        int x = 0, y = 0;
        espaciosVacios = new JLabel[56];
        for (int i = 0; i < 56; i++) {
            JLabel lb = new JLabel();
            lb.setSize(100, 32);
            lb.setLocation(x, y);
            lb.setOpaque(true);
            lb.setBorder(
                    BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, Color.DARK_GRAY,
                            Color.darkGray));
            lb.setBackground(new Color(147, 176, 171));
            lb.addMouseListener(mouseHandler);
            lb.addKeyListener(keyHandler);
            add(lb);
            espaciosVacios[i] = lb;

            if ((i + 1) % 7 == 0) {
                x = 0;
                y += 32;
            } else {
                x += 100;
            }
        }

        btBrickAzul = new JButton("Brick azul");
        btBrickAzul.setActionCommand("0");
        btBrickAzul.setSize(btBrickAzul.getPreferredSize());
        btBrickAzul.setLocation(10, 450);
        btBrickAzul.addActionListener(actionHandler);
        btBrickAzul.addKeyListener(keyHandler);
        add(btBrickAzul);

        btBrickRojo = new JButton("Brick Rojo");
        btBrickRojo.setActionCommand("1");
        btBrickRojo.setSize(btBrickRojo.getPreferredSize());
        btBrickRojo.setLocation(110, 450);
        btBrickRojo.addActionListener(actionHandler);
        btBrickRojo.addKeyListener(keyHandler);
        add(btBrickRojo);

        btBrickGris = new JButton("Brick gris");
        btBrickGris.setActionCommand("2");
        btBrickGris.setSize(btBrickGris.getPreferredSize());
        btBrickGris.setLocation(210, 450);
        btBrickGris.addActionListener(actionHandler);
        btBrickGris.addKeyListener(keyHandler);
        add(btBrickGris);

        btBorrar = new JButton("Borrar");
        btBorrar.setSize(btBorrar.getPreferredSize());
        btBorrar.setLocation(310, 450);
        btBorrar.addActionListener(actionHandler);
        btBorrar.addKeyListener(keyHandler);
        add(btBorrar);

        btGuardar = new JButton("Guardar");
        btGuardar.setSize(btGuardar.getPreferredSize());
        btGuardar.setLocation(395, 450);
        btGuardar.addActionListener((e) -> {
            pedirNombreArchivo();
        });
        btGuardar.addKeyListener(keyHandler);
        add(btGuardar);

        ladrilloSeleccionado = new JLabel();
        ladrilloSeleccionado.setLocation(500, 450);
        ladrilloSeleccionado.addKeyListener(keyHandler);
        add(ladrilloSeleccionado);

        lbInformacion = new JLabel("Brick seleccioando:");
        lbInformacion.setSize(lbInformacion.getPreferredSize());
        lbInformacion.setLocation(500, 435);
        lbInformacion.addKeyListener(keyHandler);
        add(lbInformacion);
    }

    public void pedirNombreArchivo() {
        int casillasPintadas = espaciosVacios.length;

        for (int i = 0; i < espaciosVacios.length; i++) {
            if (espaciosVacios[i].getIcon() == null) {
                casillasPintadas--;
            } else {
                casillasPintadas++;
            }
        }

        if (casillasPintadas > 0) {
            String nombre = "";
            while (nombre.length() > 8 || nombre.equals("")) {
                nombre = JOptionPane.showInputDialog(null, "Introduce un nombre para el nivel");
                if (nombre != null) {
                    if (!nombre.equals("")) {
                        if (nombre.length() <= 8) {
                            guardarNivel(nombre.trim().toLowerCase());
                        } else {
                            JOptionPane.showMessageDialog(null, "Nombre demasiado largo");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "No se ha introducido ningun nombre");
                    }
                } else {
                    return;
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "No se ha puesto ningun brick");
        }
    }

    public void guardarNivel(String nombreArchivo) {
        File file = new File(
                System.getProperty("user.home") + "\\AppData\\Roaming\\arkanoid\\niveles\\" + nombreArchivo + ".txt");
        file.canExecute();
        int res = JOptionPane.YES_OPTION;

        if (file.exists()) {
            res = JOptionPane.showConfirmDialog(null, "El archivo ya existe, quiere sobreescribirlo?", "Aviso",
                    JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
        }

        if (res == JOptionPane.YES_OPTION) {
            try (PrintWriter p = new PrintWriter(new FileWriter(file))) {
                for (int i = 0; i < espaciosVacios.length; i++) {
                    if (espaciosVacios[i].getIcon() != null) {
                        if (espaciosVacios[i].getIcon().toString().contains("Brick1")) {
                            p.print(1);
                        }

                        if (espaciosVacios[i].getIcon().toString().contains("Brick2")) {
                            p.print(2);
                        }

                        if (espaciosVacios[i].getIcon().toString().contains("Brick3")) {
                            p.print(3);
                        }
                    } else {
                        p.print(0);
                    }

                    if ((i + 1) % 7 == 0) {
                        p.println();
                    }
                }
            } catch (IOException e) {
                System.err.println("No se ha podido crear el archivo");
            }
        }

    }

    private class ActionHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == btBrickAzul || e.getSource() == btBrickRojo || e.getSource() == btBrickGris) {
                ladrilloSeleccionado.setIcon(new ImageIcon(imagenes[Integer.parseInt(e.getActionCommand())]));
                ladrilloSeleccionado.setSize(ladrilloSeleccionado.getPreferredSize());
            }

            if (e.getSource() == btBorrar) {
                ladrilloSeleccionado.setIcon(null);
            }
        }

    }

    private class MouseHandler extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            for (int i = 0; i < espaciosVacios.length; i++) {
                if (e.getSource() == espaciosVacios[i]) {
                    if (ladrilloSeleccionado.getIcon() != null) {
                        espaciosVacios[i].setIcon(ladrilloSeleccionado.getIcon());
                        espaciosVacios[i].setBackground(new Color(147, 176, 171));
                        espaciosVacios[i].setBorder(null);
                    } else {
                        espaciosVacios[i].setIcon(null);
                        espaciosVacios[i].setBackground(new Color(147, 176, 171));
                        espaciosVacios[i].setBorder(
                                BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, Color.black, Color.black));
                    }
                }
            }
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            if (SwingUtilities.isLeftMouseButton(e)) {
                for (int i = 0; i < espaciosVacios.length; i++) {
                    if (e.getSource() == espaciosVacios[i]) {
                        if (ladrilloSeleccionado.getIcon() != null) {
                            espaciosVacios[i].setIcon(ladrilloSeleccionado.getIcon());
                            espaciosVacios[i].setBackground(new Color(147, 176, 171));
                            espaciosVacios[i].setBorder(null);

                        } else {
                            espaciosVacios[i].setIcon(null);
                            espaciosVacios[i].setBackground(new Color(147, 176, 171));
                            espaciosVacios[i].setBorder(
                                    BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, Color.DARK_GRAY,
                                            Color.darkGray));
                        }
                    }
                }
            }
            for (int i = 0; i < espaciosVacios.length; i++) {
                if (e.getSource() == espaciosVacios[i]) {
                    if (espaciosVacios[i].getIcon() == null) {
                        espaciosVacios[i].setBackground(Color.gray);
                    }
                }
            }

        }

        @Override
        public void mouseExited(MouseEvent e) {
            for (int i = 0; i < espaciosVacios.length; i++) {
                if (e.getSource() == espaciosVacios[i]) {
                    if (espaciosVacios[i].getIcon() == null) {
                        espaciosVacios[i].setBackground(new Color(147, 176, 171));
                    }
                }
            }
        }
    }

    private class KeyHandler extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if ((e.isControlDown() && e.getKeyCode() == KeyEvent.VK_P)) {
                pedirNombreArchivo();
            }

            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                ventanaPrincipal.creationLevelPanel.setVisible(false);
                ventanaPrincipal.getContentPane().removeAll();
                ventanaPrincipal.removeKeyListener(keyHandler);

                ventanaPrincipal.creationLevelPanel = null;
                ventanaPrincipal.mainMenuPanel = new MainMenuPanel(ventanaPrincipal);
                ventanaPrincipal.add(ventanaPrincipal.mainMenuPanel, BorderLayout.CENTER);
            }
        }
    }

    JButton btBrickAzul;
    JButton btBrickRojo;
    JButton btBrickGris;
    JButton btBorrar;
    JButton btGuardar;
    JLabel ladrilloSeleccionado;
    JLabel lbInformacion;
    ActionHandler actionHandler = new ActionHandler();
    MouseHandler mouseHandler = new MouseHandler();
    KeyHandler keyHandler = new KeyHandler();
    URL[] imagenes = { CreationLevelPanel.class.getResource("resource\\img\\Brick1.png"),
            CreationLevelPanel.class.getResource("resource\\img\\Brick2.png"),
            CreationLevelPanel.class.getResource("resource\\img\\Brick3.png") };
    JLabel[] espaciosVacios;
    ArkanoidFrame ventanaPrincipal;
}
