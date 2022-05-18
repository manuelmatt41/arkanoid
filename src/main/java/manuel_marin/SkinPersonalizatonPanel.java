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
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.awt.image.RGBImageFilter;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EtchedBorder;

public class SkinPersonalizatonPanel extends JPanel {
    public SkinPersonalizatonPanel(ArkanoidFrame arkanoidFrame) {
        setLayout(null);
        this.arkanoidFrame = arkanoidFrame;
        iniciarComponentes();
        addKeyListener(keyHandler);
    }

    public void platformPaint() {
        borrarPixeles();
        guardarSkin.setVisible(true);
        int x = 50, y = 200;
        JLabel pixel;
        archivo = "Plataforma.png";
        abrirImagen();
        pixeles = new JLabel[32][100];
        for (int i = 0; i < 32; i++) {
            for (int j = 0; j < 100; j++) {
                pixel = new JLabel();
                pixel.setSize(6, 6);
                pixel.setLocation(x, y);
                pixel.setOpaque(true);
                pixel.setBackground(new Color(imagen.getRGB(j, i)));
                pixel.addMouseListener(mouseHandler);
                pixel.addKeyListener(keyHandler);
                if (pixel.getBackground().getRGB() != transparencia) {
                    add(pixel, BorderLayout.CENTER);
                    pixeles[i][j] = pixel;
                }
                x += 6;
            }
            x = 50;
            y += 6;
        }
        repaint();
    }

    public void ballPaint() {
        borrarPixeles();
        guardarSkin.setVisible(true);
        int x = 200, y = 150;
        JLabel pixel;
        archivo = "Pelota.png";
        abrirImagen();
        pixeles = new JLabel[20][20];
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                pixel = new JLabel();
                pixel.setSize(10, 10);
                pixel.setLocation(x, y);
                pixel.setOpaque(true);
                pixel.setBackground(new Color(imagen.getRGB(j, i)));
                pixel.addMouseListener(mouseHandler);
                pixel.addKeyListener(keyHandler);

                if (pixel.getBackground().getRGB() != transparencia) {
                    add(pixel, BorderLayout.CENTER);
                    pixeles[i][j] = pixel;
                }
                x += 10;
            }
            x = 200;
            y += 10;
        }

        repaint();
    }

    private void borrarPixeles() {
        removeAll();
        iniciarComponentes();
    }

    private void iniciarComponentes() {
        platformSkin = new JButton("Customizar plataforma");
        platformSkin.setSize(platformSkin.getPreferredSize());
        platformSkin.setLocation(20, 20);
        platformSkin.addKeyListener(keyHandler);
        platformSkin.addActionListener((e) -> {
            platformPaint();
        });
        add(platformSkin);

        ballSkin = new JButton("Customizar pelota");
        ballSkin.setSize(ballSkin.getPreferredSize());
        ballSkin.setLocation(200, 20);
        ballSkin.addKeyListener(keyHandler);
        ballSkin.addActionListener((e) -> {
            ballPaint();
        });
        add(ballSkin);

        seleccionColor = new JButton("Seleccion de color");
        seleccionColor.setSize(seleccionColor.getPreferredSize());
        seleccionColor.setLocation(350, 20);
        seleccionColor.addActionListener((e) -> {
            pincel = JColorChooser.showDialog(this, "Seleccione un color", Color.black);
        });
        seleccionColor.addKeyListener(keyHandler);
        add(seleccionColor);

        guardarSkin = new JButton("Guardar");
        guardarSkin.setSize(guardarSkin.getPreferredSize());
        guardarSkin.setLocation(500, 20);
        guardarSkin.addActionListener(actionHandler);
        guardarSkin.setVisible(false);
        add(guardarSkin);
    }

    public void abrirImagen() {
        File f = new File(System.getProperty("user.home") + "\\AppData\\Roaming\\arkanoid\\img\\" + archivo);
        if (f.exists()) {
            try {
                imagen = ImageIO.read(f);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                imagen = ImageIO
                        .read(new File(SkinPersonalizatonPanel.class.getResource("img\\" + archivo).toURI()));
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        }
    }

    private void cambiarImagen() {
        for (int i = 0; i < imagen.getHeight(); i++) {
            for (int j = 0; j < imagen.getWidth(); j++) {
                if (pixeles[i][j] != null) {
                    imagen.setRGB(j, i, pixeles[i][j].getBackground().getRGB());
                }
            }
        }
    }

    private void guardarTransparencia() {
        ImageFilter filter = new RGBImageFilter() {
            @Override
            public int filterRGB(int x, int y, int rgb) {
                if ((rgb | 0xFF000000) == transparencia) {
                    // Mark the alpha bits as zero - transparent
                    return 0x00FFFFFF & rgb;
                } else {
                    // nothing to do
                    return rgb;
                }
            }

        };
        ImageProducer ip = new FilteredImageSource(imagen.getSource(), filter);
        Image img = Toolkit.getDefaultToolkit().createImage(ip);
        imagen = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2dImg = imagen.createGraphics();
        g2dImg.drawImage(img, 0, 0, null);
        g2dImg.dispose();
    }

    public void guardarImagen() {
        cambiarImagen();
        guardarTransparencia();
        try {
            if (ImageIO.write(imagen, "png",
                    new File(System.getProperty("user.home") + "\\AppData\\Roaming\\arkanoid\\img\\" + archivo))) {
                JOptionPane.showMessageDialog(this, "Se ha guardado correctamente");
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "No se ha guardado correctamente");
        }
        guardarSkin.setVisible(false);
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
                guardarImagen();
            }

            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                arkanoidFrame.mainMenuPanel = new MainMenuPanel(arkanoidFrame);
                arkanoidFrame.add(arkanoidFrame.mainMenuPanel, BorderLayout.CENTER);

                arkanoidFrame.skinPersonalizatonPanel.setVisible(false);
                arkanoidFrame.remove(arkanoidFrame.skinPersonalizatonPanel);
            }
        }
    }

    private class ActionHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (imagen != null) {
                guardarImagen();
            } else {
                JOptionPane.showMessageDialog(null, "No se ha seleccionado ninguna imagen");
            }
        }

    }

    JButton platformSkin;
    JButton ballSkin;
    JButton seleccionColor;
    JButton guardarSkin;
    JLabel[][] pixeles;
    MouseHandler mouseHandler = new MouseHandler();
    KeyHandler keyHandler = new KeyHandler();
    Color pincel = Color.black;
    BufferedImage imagen;
    ActionHandler actionHandler = new ActionHandler();
    String archivo;
    int transparencia = 0xFF000000;
    ArkanoidFrame arkanoidFrame;
}
