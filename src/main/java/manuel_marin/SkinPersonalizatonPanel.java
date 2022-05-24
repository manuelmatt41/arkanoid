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

/**
 * Clase que hereda de JPanel para gestionar la personalizacion de skins.
 */
public class SkinPersonalizatonPanel extends JPanel {
    /**
     * Inicializa las propiedades de los parametros
     * 
     * @param arkanoidFrame
     */
    public SkinPersonalizatonPanel(ArkanoidFrame arkanoidFrame) {
        setLayout(null);
        this.arkanoidFrame = arkanoidFrame;
        iniciarComponentes();
        addKeyListener(keyHandler);
        setBackground(arkanoidFrame.BACKGROUND_COLOR);
        setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, Color.black, Color.black));
    }

    /**
     * Inicia los componentes del JPanel
     */
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
            if (pincel != null) {
                if (pincel.getRGB() == OPACO) {
                    pincel = new Color(OPACO + 1);
                }
            } else {
                pincel = new Color(OPACO + 1);
            }
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

    /**
     * Pinta una serie de JLabel que representa cada pixel que copia a la imagen de
     * la plataforma para poder pintar sobre ella
     */
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
                if (pixel.getBackground().getRGB() != OPACO) {
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

    /**
     * Pinta una serie de JLabel que representa cada pixel que copia a la imagen de
     * la pelota para poder pintar sobre ella
     */
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

                if (pixel.getBackground().getRGB() != OPACO) {
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

    /**
     * Borra todas los componentes y vuelve inciar los componentes iniciales
     */
    private void borrarPixeles() {
        removeAll();
        iniciarComponentes();
    }

    /**
     * Abre la imagen para personalizar la del usuario, sino abre la que viene por
     * defecto.
     */
    public void abrirImagen() {
        File f = new File(System.getProperty("user.home") + "/AppData/Roaming/arkanoid/img/" + archivo);
        if (f.exists()) {
            try {
                imagen = ImageIO.read(f);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                imagen = ImageIO
                        .read(new File(SkinPersonalizatonPanel.class.getResource("resource/img/" + archivo).toURI()));
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Pinta la imagen con los nuevos colores
     */
    private void cambiarImagen() {
        for (int i = 0; i < imagen.getHeight(); i++) {
            for (int j = 0; j < imagen.getWidth(); j++) {
                if (pixeles[i][j] != null) {
                    imagen.setRGB(j, i, pixeles[i][j].getBackground().getRGB());
                }
            }
        }
    }

    /**
     * Esatblece un color transparente y lo guarda
     */
    private void guardarTransparencia() {
        ImageFilter filter = new RGBImageFilter() {
            @Override
            public int filterRGB(int x, int y, int rgb) {
                if ((rgb | 0xFF000000) == OPACO) {
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

    /**
     * Guarda la imagen personalizada en el AppData y si no existe la carpeta la
     * crea
     */
    public void guardarImagen() {
        cambiarImagen();
        guardarTransparencia();
        File f = new File(System.getProperty("user.home") + "/AppData/Roaming/arkanoid/img");
        try {
            if (f.exists()) {
                if (ImageIO.write(imagen, "png",
                        new File(System.getProperty("user.home") + "/AppData/Roaming/arkanoid/img/" + archivo))) {
                    JOptionPane.showMessageDialog(this, "Se ha guardado correctamente");
                }
            } else {
                if (f.mkdirs()) {
                    f = new File(f.getAbsolutePath() + "/" + archivo);
                    if (f.createNewFile()) {
                        if (ImageIO.write(imagen, "png",
                                new File(System.getProperty("user.home") + "/AppData/Roaming/arkanoid/img/"
                                        + archivo))) {
                            JOptionPane.showMessageDialog(this, "Se ha guardado correctamente");
                        }
                    }
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "No se ha guardado correctamente");
        }
        guardarSkin.setVisible(false);
    }

    /**
     * Clase que gestiona los eventos de raton de {@link #SkinPersonalizatonPanel()}
     */
    private class MouseHandler extends MouseAdapter {
        /**
         * Comprueba que se mantiene el click izquierdo para pintar el color
         * seleccionado cuando se entre a cada JLabel que repensenta los pixeles.
         * Tambien crea un borde para ver el JLabel seleccionado
         */
        @Override
        public void mouseEntered(MouseEvent e) {
            JLabel pixel = (JLabel) e.getSource();
            pixel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED, Color.gray, Color.gray));

            if (SwingUtilities.isLeftMouseButton(e)) {
                pixel.setBackground(pincel);
            }
        }

        /**
         * Elimina el borde de las JLabel al sacar el raton de encima
         */
        @Override
        public void mouseExited(MouseEvent e) {
            JLabel pixel = (JLabel) e.getSource();

            pixel.setBorder(null);

        }

        /**
         * Pinta el JLabel seleccionado con el color selecciona
         */
        @Override
        public void mousePressed(MouseEvent e) {
            JLabel pixel = (JLabel) e.getSource();
            if (SwingUtilities.isLeftMouseButton(e)) {
                pixel.setBackground(pincel);
            }
        }

    }

    /**
     * Clase que gestiona los eventos de teclado de {@link #SkinPersonalizatonPanel()}
     */
    private class KeyHandler extends KeyAdapter {
        /**
         * Atajos de teclado
         */
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

    /**
     * Clase que gestiona las acciones de los botones
     */
    private class ActionHandler implements ActionListener {

        /**
         * Guarda la imagen si la imagen ha sido abierta
         */
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
    /**
     * Lista de JLabel que representar la cantidad de pixeles de las imagenes
     */
    JLabel[][] pixeles;
    MouseHandler mouseHandler = new MouseHandler();
    KeyHandler keyHandler = new KeyHandler();
    /**
     * Color seleccionado para pintar en las imagenes
     */
    Color pincel = Color.black;
    BufferedImage imagen;
    ActionHandler actionHandler = new ActionHandler();
    /**
     * Nombre del archivo de la imagen
     */
    String archivo;
    /**
     * Numero hexadecimal que reprensenta el RGB transparente de la imagen.
     */
    final int OPACO = 0xFF000000;
    ArkanoidFrame arkanoidFrame;
}
