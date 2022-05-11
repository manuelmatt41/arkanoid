package manuel_marin;

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
        for (int i = 0; i < 36; i++) {
            JLabel lbl = new JLabel(new ImageIcon(PanelJuego.class.getResource(pathImg[0])));
            lbl.setSize(lbl.getPreferredSize());
            lbl.setLocation(x, y);
            lbl.addKeyListener(movimientoPlataforma);
            add(lbl);
            lblBricks[i] = lbl;
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
                        lblBricks[i].setVisible(false);

                        direccionX = ladoGolpeHorizontal(lblBricks[i]);
                        direccionY = ladoGolpeVertical(lblBricks[i]);
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
        
        private boolean ladoGolpeHorizontal(JLabel brick) {
            int derecha = brick.getBounds().width + brick.getLocation().x;
            int izquierda = brick.getLocation().x;
            int medio = brick.getBounds().width / 2 + brick.getLocation().x;
            boolean cambioDireccion = direccionX;
            if (pelota.getLocation().x <= derecha && pelota.getLocation().x >= medio) {
                cambioDireccion = true;
            }

            if (pelota.getLocation().x >= izquierda && pelota.getLocation().x <= medio) {
                cambioDireccion = false;
            }

            return cambioDireccion;
        }

        private boolean ladoGolpeVertical(JLabel brick) {
            int abajo = brick.getBounds().height + brick.getLocation().y;
            int arriba = brick.getLocation().y;
            int medio = brick.getBounds().height / 2 + brick.getLocation().y;
            boolean cambioDireccion = direccionX;
            if (pelota.getLocation().x <= abajo && pelota.getLocation().x >= medio) {
                cambioDireccion = true;
            }

            if (pelota.getLocation().x >= arriba && pelota.getLocation().x <= medio) {
                cambioDireccion = false;
            }

            return cambioDireccion;
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
    JLabel lblPlataforma;
    JLabel lblCircle;
    Timer fpsPelota;
    Timer fpsMejoras;
    VentanaPrincipal ventanaPrincipal;
    MovimientosPelota movimientosPelota;
    MovimientoPlataforma movimientoPlataforma = new MovimientoPlataforma(this);
}
