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
        lblBricks = new JLabel[36];
        hitBoxBricks = new JLabel[lblBricks.length][4];

        for (int i = 0; i < lblBricks.length; i++) {
            JLabel lbl = new JLabel(new ImageIcon(PanelJuego.class.getResource(pathImg[0])));
            lbl.setSize(lbl.getPreferredSize());
            lbl.setLocation(x, y);
            lbl.addKeyListener(movimientoPlataforma);
            hitBoxBricks[i] = posicionHitBox(lbl);
            add(lbl);
            lblBricks[i] = lbl;

            for (int j = 0; j < hitBoxBricks[i].length; j++) {
                add(hitBoxBricks[i][j]);
            }
            
            if ((i + 1) % 6 == 0) {
                x = 0;
                y += 32;
            } else {
                x += 100;
            }
        }

        lblPlataforma = new JLabel(new ImageIcon(PanelJuego.class.getResource("\\img\\Plataforma.png")));
        lblPlataforma.setSize(lblPlataforma.getPreferredSize());
        lblPlataforma.setLocation(250, 530);
        lblPlataforma.addKeyListener(movimientoPlataforma);
        add(lblPlataforma);

        lblCircle = new JLabel(new ImageIcon(PanelJuego.class.getResource("\\img\\Pelota.png")));
        lblCircle.setSize(20, 20);
        lblCircle.setLocation(300, 512);
        add(lblCircle);

        movimientosPelota = new MovimientosPelota(lblCircle);

        fpsPelota = new Timer(17, movimientosPelota);
        fpsPelota.start();
        addKeyListener(movimientoPlataforma);
    }

    private JLabel[] posicionHitBox(JLabel brick) {
        JLabel[] hitboxs = new JLabel[4];
        Point[] posicionesHitBoxs = new Point[4];

        posicionesHitBoxs[0] = new Point(brick.getLocation());
        posicionesHitBoxs[1] = new Point(
                brick.getLocation().x + brick.getBounds().width - (brick.getBounds().width / 10), brick.getLocation().y);
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

    private class MovimientoPlataforma extends KeyAdapter {
        public MovimientoPlataforma(PanelJuego panelJuego) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            Point nuevoPunto = lblPlataforma.getLocation();

            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                if (lblPlataforma.getLocation().x >= 0) {
                    nuevoPunto.x -= 15;
                }
                lblPlataforma.setLocation(nuevoPunto);
            }

            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                if (lblPlataforma.getLocation().x <= 500) {
                    nuevoPunto.x += 15;
                }
                lblPlataforma.setLocation(nuevoPunto);
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
            for (int i = 0; i < lblBricks.length; i++) {
                if (lblBricks[i].isVisible()) {
                    if (lblBricks[i].getBounds().intersects(pelota.getBounds())) {
                        for (int j = 0; j < hitBoxBricks[i].length; j++) {
                            if (hitBoxBricks[i][j].isVisible()) {
                                if (hitBoxBricks[i][j].getBounds().intersects(pelota.getBounds())) {
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
                                }

                                hitBoxBricks[i][j].setVisible(false);
                            }
                        }

                        lblBricks[i].setVisible(false);
                    }
                }
            }

            if (pelota.getLocation().x <= 0) {
                direccionX = true;
            }

            if (pelota.getLocation().x >= 580) {
                direccionX = false;
            }

            if (pelota.getLocation().y <= 0) {
                direccionY = true;
            }

            if (pelota.getLocation().y >= 580) {
                direccionY = false;
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

        int velocidad;
        int posicionX;
        int posicionY;
        boolean direccionX;
        boolean direccionY;
        JLabel pelota;
    }

    String[] pathImg = { "\\img\\Brick1.png" };
    JLabel[] lblBricks;
    JLabel[][] hitBoxBricks;
    JLabel lblPlataforma;
    JLabel lblCircle;
    Timer fpsPelota;
    Timer fpsMejoras;
    VentanaPrincipal ventanaPrincipal;
    MovimientosPelota movimientosPelota;
    MovimientoPlataforma movimientoPlataforma = new MovimientoPlataforma(this);
}
