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
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EtchedBorder;

public class SkinPersonalizatonPanel extends JPanel {
    public SkinPersonalizatonPanel() {
        setLayout(null);

        iniciarComponentes();
        addKeyListener(keyHandler);
    }

    public void platformPaint() {
        borrarPixeles();
        int x = 50, y = 200;
        JLabel pixel;
        abrirImagen();
        for (int i = 0; i < 32; i++) {
            for (int j = 0; j < 100; j++) {
                pixel = new JLabel();
                pixel.setSize(6, 6);
                pixel.setLocation(x, y);
                pixel.setOpaque(true);
                pixel.setBackground(new Color(imagen.getRGB(j, i)));
                pixel.addMouseListener(mouseHandler);
                pixel.addKeyListener(keyHandler);
                add(pixel, BorderLayout.CENTER);
                pixeles[i][j] = pixel;
                x += 6;
            }
            x = 50;
            y += 6;
        }
        repaint();
    }

    private void borrarPixeles() {
        removeAll();
        iniciarComponentes();
    }

    private void iniciarComponentes() {
        platform = new JButton("Customizar plataforma");
        platform.setSize(platform.getPreferredSize());
        platform.setLocation(20, 20);
        platform.addKeyListener(keyHandler);
        platform.addActionListener((e) -> {
            platformPaint();
        });
        ;
        add(platform);

        seleccionColor = new JButton("Seleccion de color");
        seleccionColor.setSize(seleccionColor.getPreferredSize());
        seleccionColor.setLocation(200, 20);
        seleccionColor.addActionListener((e) -> {
            pincel = JColorChooser.showDialog(this, "Seleccione un color", Color.black);
        });
        seleccionColor.addKeyListener(keyHandler);
        add(seleccionColor);
    }

    public void abrirImagen() {
        File f = new File(System.getProperty("user.home") + "\\AppData\\Roaming\\arkanoid\\img\\Plataforma.png");
        if (f.exists()) {
            try {
                imagen = ImageIO.read(f);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                imagen = ImageIO
                        .read(new File(SkinPersonalizatonPanel.class.getResource("img\\Plataforma.png").toURI()));
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        }
    }

    public void cambiarImagen() {
        for (int i = 0; i < imagen.getHeight(); i++) {
            for (int j = 0; j < imagen.getWidth(); j++) {
                imagen.setRGB(j, i, pixeles[i][j].getBackground().getRGB());
            }
        }

        try {
            ImageIO.write(imagen, "png",
                    new File(System.getProperty("user.home") + "\\AppData\\Roaming\\arkanoid\\img\\Plataforma.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class MouseHandler extends MouseAdapter {
        @Override
        public void mouseEntered(MouseEvent e) {
            JLabel pixel = (JLabel) e.getSource();
            pixel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED, Color.gray, Color.gray));

            if (SwingUtilities.isLeftMouseButton(e)) {
                pixel.setBackground(pincel);
            }
        }

        @Override
        public void mouseExited(MouseEvent e) {
            JLabel pixel = (JLabel) e.getSource();

            pixel.setBorder(null);

        }

        @Override
        public void mousePressed(MouseEvent e) {
            JLabel pixel = (JLabel) e.getSource();
            if (SwingUtilities.isLeftMouseButton(e)) {
                pixel.setBackground(pincel);
            }
        }

    }

    private class KeyHandler extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_P) {
                cambiarImagen();
            }
        }
    }

    JButton platform;
    JButton seleccionColor;
    JLabel[][] pixeles = new JLabel[32][100];
    MouseHandler mouseHandler = new MouseHandler();
    KeyHandler keyHandler = new KeyHandler();
    Color pincel = Color.black;
    BufferedImage imagen;
}
