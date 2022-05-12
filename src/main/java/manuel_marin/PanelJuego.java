package manuel_marin;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class PanelJuego extends JPanel {
    public PanelJuego(VentanaPrincipal ventanaPrincipal) {
        setLayout(null);
        this.ventanaPrincipal = ventanaPrincipal;
        iniciarComponentes();

        setFocusable(true);
    }

    private void iniciarComponentes() {
        int x = 0, y = 0;
        bricks = new JLabel[42];
        hitBoxBricksEsquinas = new JLabel[bricks.length][4];
        hitBoxBricksLaterales = new JLabel[bricks.length][4];

        for (int i = 0; i < bricks.length; i++) {
            JLabel lbl = new JLabel(new ImageIcon(PanelJuego.class.getResource(pathImg[0])));
            lbl.setSize(lbl.getPreferredSize());
            lbl.setLocation(x, y);
            lbl.addKeyListener(movimientoPlataforma);
            hitBoxBricksEsquinas[i] = posicionHitBoxEsquinas(lbl);
            hitBoxBricksLaterales[i] = posicionHitBoxLaterales(lbl, hitBoxBricksEsquinas[i]);
            // lbl.setVisible(false);
            add(lbl);
            bricks[i] = lbl;

            for (int j = 0; j < hitBoxBricksEsquinas[i].length; j++) {
                add(hitBoxBricksEsquinas[i][j]);
            }

            for (int j = 0; j < hitBoxBricksLaterales[i].length; j++) {
                add(hitBoxBricksLaterales[i][j]);
            }

            if ((i + 1) % 7 == 0) {
                x = 0;
                y += 32;
            } else {
                x += 100;
            }
        }

        plataforma = new JLabel(new ImageIcon(PanelJuego.class.getResource("\\img\\Plataforma.png")));
        plataforma.setSize(plataforma.getPreferredSize());
        plataforma.setLocation(250, 430);
        plataforma.addKeyListener(movimientoPlataforma);
        add(plataforma);

        hitBoxPlataforma = posicionHitBoxEsquinas(plataforma);
        for (int i = 0; i < hitBoxPlataforma.length; i++) {
            add(hitBoxPlataforma[i]);
        }

        pelota = new JLabel(new ImageIcon(PanelJuego.class.getResource("\\img\\Pelota.png")));
        pelota.setSize(20, 20);
        pelota.setLocation(300, 412);
        add(pelota);

        movimientosPelota = new MovimientosPelota(pelota);

        fpsPelota = new Timer(17, movimientosPelota);
        addKeyListener(movimientoPlataforma);
    }

    private JLabel[] posicionHitBoxEsquinas(JLabel brick) {
        JLabel[] hitboxs = new JLabel[4];
        Point[] posicionesHitBoxs = new Point[4];

        posicionesHitBoxs[0] = new Point(brick.getLocation());

        posicionesHitBoxs[1] = new Point(
                brick.getLocation().x + brick.getBounds().width - (brick.getBounds().width / 10),
                brick.getLocation().y);

        posicionesHitBoxs[2] = new Point(brick.getLocation().x,
                brick.getLocation().y + brick.getBounds().height - (brick.getBounds().height / 4));

        posicionesHitBoxs[3] = new Point(
                brick.getLocation().x + brick.getBounds().width - (brick.getBounds().width / 10),
                brick.getLocation().y + brick.getBounds().height - (brick.getBounds().height / 4));

        for (int i = 0; i < hitboxs.length; i++) {
            JLabel hitbox = new JLabel();
            hitbox.setSize(10, 8);
            hitbox.setLocation(posicionesHitBoxs[i]);
            hitbox.setOpaque(true);
            hitbox.setBackground(Color.green);
            hitboxs[i] = hitbox;
        }

        return hitboxs;
    }

    private JLabel[] posicionHitBoxLaterales(JLabel brick, JLabel[] hitBoxEsquinas) {
        JLabel[] hitboxs = new JLabel[4];
        Point[] posicionesLateralesHitBoxs = new Point[4];

        posicionesLateralesHitBoxs[0] = new Point(
                hitBoxEsquinas[0].getLocation().x + hitBoxEsquinas[0].getBounds().width,
                hitBoxEsquinas[0].getLocation().y);

        posicionesLateralesHitBoxs[1] = new Point(
                hitBoxEsquinas[0].getLocation().x,
                hitBoxEsquinas[0].getLocation().y + hitBoxEsquinas[0].getBounds().height);

        posicionesLateralesHitBoxs[2] = new Point(
                hitBoxEsquinas[1].getLocation().x,
                hitBoxEsquinas[1].getLocation().y + hitBoxEsquinas[1].getBounds().height);

        posicionesLateralesHitBoxs[3] = new Point(
                hitBoxEsquinas[2].getLocation().x + hitBoxEsquinas[2].getBounds().width,
                hitBoxEsquinas[2].getLocation().y);

        for (int i = 0; i < hitboxs.length; i++) {
            JLabel hitboxLateral = new JLabel();
            if (i == 0 || i == 3) {
                hitboxLateral.setSize(80, 8);
            } else {
                hitboxLateral.setSize(10, 16);
            }
            hitboxLateral.setLocation(posicionesLateralesHitBoxs[i]);
            hitboxLateral.setOpaque(true);
            hitboxLateral.setBackground(Color.red);
            hitboxs[i] = hitboxLateral;
        }

        return hitboxs;
    }

    // Clase encargada del movimiento de la plataforma
    private class MovimientoPlataforma extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            Point nuevoPunto = plataforma.getLocation();

            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                if (plataforma.getLocation().x >= 0) {
                    nuevoPunto.x -= 15;

                    for (int i = 0; i < hitBoxPlataforma.length; i++) {
                        hitBoxPlataforma[i].setLocation(hitBoxPlataforma[i].getLocation().x - 15,
                                hitBoxPlataforma[i].getLocation().y);
                    }
                }
                plataforma.setLocation(nuevoPunto);

            }

            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                if (plataforma.getLocation().x <= 600) {
                    nuevoPunto.x += 15;

                    for (int i = 0; i < hitBoxPlataforma.length; i++) {
                        hitBoxPlataforma[i].setLocation(hitBoxPlataforma[i].getLocation().x + 15,
                                hitBoxPlataforma[i].getLocation().y);
                    }
                }
                plataforma.setLocation(nuevoPunto);
            }

            if (e.getKeyCode() == KeyEvent.VK_UP) {
                if (!fpsPelota.isRunning()) {
                    fpsPelota.start();
                }
            }
        }
    }

    // Clase encargada de actulizar las fisicas.
    private class MovimientosPelota implements ActionListener {
        public MovimientosPelota(JLabel pelota) {
            this.pelota = pelota;
            velocidad = 4;
            posicionX = pelota.getLocation().x;
            posicionY = pelota.getLocation().y;
            direccionX = false;
            direccionY = false;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            for (int i = 0; i < bricks.length; i++) {
                if (bricks[i].isVisible()) {
                    if (bricks[i].getBounds().intersects(pelota.getBounds())) {
                        for (int j = 0; j < hitBoxBricksEsquinas[i].length; j++) {
                            if (hitBoxBricksEsquinas[i][j].isVisible()) {
                                if (hitBoxBricksEsquinas[i][j].getBounds().intersects(pelota.getBounds())) {
                                    if (j == 0) {
                                        if (direccionX) {
                                            direccionX = false;
                                        }

                                        if (direccionY) {
                                            direccionY = false;
                                        }
                                    }

                                    if (j == 1) {
                                        if (!direccionX) {
                                            direccionX = true;
                                        }

                                        if (direccionY) {
                                            direccionY = false;
                                        }
                                    }

                                    if (j == 2) {
                                        if (direccionX) {
                                            direccionX = false;
                                        }

                                        if (!direccionY) {
                                            direccionY = true;
                                        }
                                    }

                                    if (j == 3) {
                                        if (!direccionX) {
                                            direccionX = true;
                                        }

                                        if (!direccionY) {
                                            direccionY = true;
                                        }
                                    }

                                    velocidad = 6;
                                } else {
                                    if (hitBoxBricksLaterales[i][j].isVisible()) {
                                        if (hitBoxBricksLaterales[i][j].getBounds().intersects(pelota.getBounds())) {
                                            if (j == 0) {
                                                direccionY = false;
                                            }

                                            if (j == 1) {
                                                direccionX = false;
                                            }

                                            if (j == 2) {
                                                direccionX = true;
                                            }

                                            if (j == 3) {
                                                direccionY = true;
                                            }

                                            velocidad = 4;
                                        }
                                    }
                                }

                                hitBoxBricksLaterales[i][j].setVisible(false);
                                hitBoxBricksEsquinas[i][j].setVisible(false);
                            }
                        }

                        bricks[i].setVisible(false);
                        ventanaPrincipal.panelPuntuacion.puntuacion += 100;
                        ventanaPrincipal.panelPuntuacion.updateLabels();
                    }
                }
            }

            if (plataforma.getBounds().intersects(pelota.getBounds())) {
                for (int j = 0; j < hitBoxPlataforma.length; j++) {
                    if (hitBoxPlataforma[j].getBounds().intersects(pelota.getBounds())) {
                        if (j == 0) {
                            if (direccionX) {
                                direccionX = false;
                            }
                            direccionY = false;
                            velocidad = 6;
                        }

                        if (j == 1) {
                            if (!direccionX) {
                                direccionX = true;
                            }
                            direccionY = false;

                            velocidad = 6;
                        }
                    }
                }

                direccionY = false;

            }

            if (pelota.getLocation().x <= 0) {
                direccionX = true;
            }

            if (pelota.getLocation().x >= 680) {
                direccionX = false;
            }

            if (pelota.getLocation().y <= 0) {
                direccionY = true;
            }

            if (pelota.getLocation().y >= 480) {
                ventanaPrincipal.panelPuntuacion.vidas--;
                ventanaPrincipal.panelPuntuacion.updateLabels();

                if (ventanaPrincipal.panelPuntuacion.vidas > 0) {
                    pelota.setLocation(300, 412);
                    plataforma.setLocation(250, 430);

                    JLabel[] reinicioHitBox = posicionHitBoxEsquinas(plataforma);

                    for (int i = 0; i < hitBoxPlataforma.length; i++) {
                        hitBoxPlataforma[i].setLocation(reinicioHitBox[i].getLocation());
                    }

                    actualizarParametros();
                    fpsPelota.stop();
                    return;
                } else {
                    ventanaPrincipal.dispose();
                    new VentanaPrincipal();
                    fpsPelota.stop();
                    return;
                }
            }

            if (direccionX) {
                posicionX += velocidad;
            } else {
                posicionX -= velocidad;
            }

            if (direccionY) {
                posicionY += velocidad;
            } else {
                posicionY -= velocidad;
            }

            pelota.setLocation(posicionX, posicionY);
        }

        private void actualizarParametros() {
            posicionX = pelota.getLocation().x;
            posicionY = pelota.getLocation().y;
            direccionX = false;
            direccionY = false;
        }

        int velocidad;
        int posicionX;
        int posicionY;
        boolean direccionX;
        boolean direccionY;
        JLabel pelota;
    }

    String[] pathImg = { "\\img\\Brick1.png" };
    JLabel[] bricks;
    JLabel[][] hitBoxBricksEsquinas;
    JLabel[][] hitBoxBricksLaterales;
    JLabel[] hitBoxPlataforma;
    JLabel plataforma;
    JLabel pelota;
    Timer fpsPelota;
    Timer fpsMejoras;
    VentanaPrincipal ventanaPrincipal;
    MovimientosPelota movimientosPelota;
    MovimientoPlataforma movimientoPlataforma = new MovimientoPlataforma();
}
