package manuel_marin;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;

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
        bricks = CargaDeNiveles.getNivel(new File("src\\main\\java\\manuel_marin\\niveles\\nivel1.txt"));
        hitBoxBricksEsquinas = new JLabel[bricks.length][4];
        hitBoxBricksLaterales = new JLabel[bricks.length][4];

        for (int i = 0; i < bricks.length; i++) {
            if (bricks[i] != null) {
                bricks[i].setSize(bricks[i].getPreferredSize());
                bricks[i].setLocation(x, y);
                bricks[i].addKeyListener(movimientoPlataforma);
                hitBoxBricksEsquinas[i] = getPosicionesHitBoxEsquinas(bricks[i]);
                hitBoxBricksLaterales[i] = getPosicionesHitBoxLaterales(bricks[i], hitBoxBricksEsquinas[i]);
                // lbl.setVisible(false);
                add(bricks[i]);
                bricks[i] = bricks[i];

                for (int j = 0; j < hitBoxBricksEsquinas[i].length; j++) {
                    add(hitBoxBricksEsquinas[i][j]);
                }

                for (int j = 0; j < hitBoxBricksLaterales[i].length; j++) {
                    add(hitBoxBricksLaterales[i][j]);
                }
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

        hitBoxPlataformaEsquinas = getPosicionesHitBoxEsquinas(plataforma);

        for (int i = 0; i < hitBoxPlataformaEsquinas.length; i++) {
            add(hitBoxPlataformaEsquinas[i]);
        }

        hitBoxPlataformaLateral = getPosicionesHitBoxLaterales(plataforma, hitBoxPlataformaEsquinas);

        for (int i = 0; i < hitBoxPlataformaLateral.length; i++) {
            add(hitBoxPlataformaLateral[i]);
        }

        pelota = new JLabel(new ImageIcon(PanelJuego.class.getResource("\\img\\Pelota.png")));
        pelota.setSize(20, 20);
        pelota.setLocation(300, 412);
        add(pelota);

        movimientosPelota = new MovimientosPelota();

        fpsPelota = new Timer(17, movimientosPelota);
        addKeyListener(movimientoPlataforma);
    }

    private JLabel[] getPosicionesHitBoxEsquinas(JLabel brick) {
        JLabel[] hitboxs = new JLabel[4];
        Point[] posicionesHitBoxs = new Point[4];

        posicionesHitBoxs[0] = new Point(brick.getLocation());

        posicionesHitBoxs[1] = new Point(
                brick.getLocation().x + brick.getBounds().width - (brick.getBounds().width / 20),
                brick.getLocation().y);

        posicionesHitBoxs[2] = new Point(brick.getLocation().x,
                brick.getLocation().y + brick.getBounds().height - (brick.getBounds().height / 6));

        posicionesHitBoxs[3] = new Point(
                brick.getLocation().x + brick.getBounds().width - (brick.getBounds().width / 20),
                brick.getLocation().y + brick.getBounds().height - (brick.getBounds().height / 6));

        for (int i = 0; i < hitboxs.length; i++) {
            JLabel hitbox = new JLabel();
            hitbox.setSize(5, 5);
            hitbox.setLocation(posicionesHitBoxs[i]);
            hitboxs[i] = hitbox;
        }

        return hitboxs;
    }

    private JLabel[] getPosicionesHitBoxLaterales(JLabel brick, JLabel[] hitBoxEsquinas) {
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
                hitboxLateral.setSize(90, 5);
            } else {
                hitboxLateral.setSize(5, 12);
            }
            hitboxLateral.setLocation(posicionesLateralesHitBoxs[i]);
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

                    for (int i = 0; i < hitBoxPlataformaEsquinas.length; i++) {
                        hitBoxPlataformaEsquinas[i].setLocation(hitBoxPlataformaEsquinas[i].getLocation().x - 15,
                                hitBoxPlataformaEsquinas[i].getLocation().y);
                        hitBoxPlataformaLateral[i].setLocation(hitBoxPlataformaLateral[i].getLocation().x - 15,
                                hitBoxPlataformaLateral[i].getLocation().y);
                    }
                }
                plataforma.setLocation(nuevoPunto);

            }

            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                if (plataforma.getLocation().x <= 600) {
                    nuevoPunto.x += 15;

                    for (int i = 0; i < hitBoxPlataformaEsquinas.length; i++) {
                        hitBoxPlataformaEsquinas[i].setLocation(hitBoxPlataformaEsquinas[i].getLocation().x + 15,
                                hitBoxPlataformaEsquinas[i].getLocation().y);
                        hitBoxPlataformaLateral[i].setLocation(hitBoxPlataformaLateral[i].getLocation().x + 15,
                                hitBoxPlataformaLateral[i].getLocation().y);
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
        public MovimientosPelota() {
            velocidad = 4;
            posicionX = pelota.getLocation().x;
            posicionY = pelota.getLocation().y;
            direccionX = false;
            direccionY = true;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            for (int i = 0; i < bricks.length; i++) {
                if (bricks[i] != null) {
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
                                        if (bricks[i].getIcon().toString().contains("Brick1")) {
                                            for (int k = 0; k < hitBoxBricksEsquinas[i].length; k++) {
                                                hitBoxBricksEsquinas[i][k].setVisible(false);
                                                hitBoxBricksLaterales[i][k].setVisible(false);
                                            }
                                        }
                                    } else {
                                        if (hitBoxBricksLaterales[i][j].isVisible()) {
                                            if (hitBoxBricksLaterales[i][j].getBounds()
                                                    .intersects(pelota.getBounds())) {
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
                                                if (bricks[i].getIcon().toString().contains("Brick1")) {
                                                    for (int k = 0; k < hitBoxBricksEsquinas[i].length; k++) {
                                                        hitBoxBricksEsquinas[i][k].setVisible(false);
                                                        hitBoxBricksLaterales[i][k].setVisible(false);
                                                    }
                                                }
                                            }
                                        }

                                    }
                                }
                            }
                            if (durezaLadrillo(bricks[i])) {
                                bricks[i].setVisible(false);
                            }
                            ventanaPrincipal.panelPuntuacion.puntuacion += 100;
                            ventanaPrincipal.panelPuntuacion.updateLabels();
                        }

                    }
                }
            }

            if (plataforma.getBounds().intersects(pelota.getBounds())) {
                for (int j = 0; j < 2; j++) {
                    if (hitBoxPlataformaEsquinas[j].getBounds().intersects(pelota.getBounds())) {
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
                    } else {
                        if (hitBoxPlataformaLateral[0].getBounds().intersects(pelota.getBounds())) {
                            direccionY = false;
                        }
                    }
                }

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

            if (pelota.getLocation().y >= 440) {
                ventanaPrincipal.panelPuntuacion.vidas--;
                ventanaPrincipal.panelPuntuacion.updateLabels();

                if (ventanaPrincipal.panelPuntuacion.vidas > 0) {
                    pelota.setLocation(300, 412);
                    plataforma.setLocation(250, 430);

                    JLabel[] reinicioHitBox = getPosicionesHitBoxEsquinas(plataforma);

                    for (int i = 0; i < hitBoxPlataformaEsquinas.length; i++) {
                        hitBoxPlataformaEsquinas[i].setLocation(reinicioHitBox[i].getLocation());
                    }

                    reinicioHitBox = getPosicionesHitBoxLaterales(plataforma, hitBoxPlataformaEsquinas);
                    for (int i = 0; i < hitBoxPlataformaLateral.length; i++) {
                        hitBoxPlataformaLateral[i].setLocation(reinicioHitBox[i].getLocation());
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
            direccionY = true;
        }

        private boolean durezaLadrillo(JLabel brick) {
            if (brick.getIcon().toString().contains("Brick2.png")) {
                brick.setIcon(new ImageIcon(PanelJuego.class.getResource("\\img\\Brick1.png")));
                return false;
            } else {
                return true;
            }
        }

        int velocidad;
        int posicionX;
        int posicionY;
        boolean direccionX;
        boolean direccionY;
    }

    JLabel[] bricks;
    JLabel[][] hitBoxBricksEsquinas;
    JLabel[][] hitBoxBricksLaterales;
    JLabel[] hitBoxPlataformaEsquinas;
    JLabel[] hitBoxPlataformaLateral;
    JLabel plataforma;
    JLabel pelota;
    Timer fpsPelota;
    VentanaPrincipal ventanaPrincipal;
    MovimientosPelota movimientosPelota;
    MovimientoPlataforma movimientoPlataforma = new MovimientoPlataforma();
}
