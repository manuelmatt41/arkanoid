package manuel_marin;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

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

        addKeyListener(movimientoPlataforma);
    }

    private class MovimientoPlataforma extends KeyAdapter {
        public MovimientoPlataforma(PanelJuego panelJuego) {
            this.panelJuego = panelJuego;
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

            if (lblPelotas.isEmpty()) {
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    lblPelotas.add(lblCircle);
                    //movimientosPelota = new MovimientosPelota(panelJuego);
                    fpsPelota = new Timer(17, movimientosPelota);
                    fpsPelota.start();
                }
            }
        }

        PanelJuego panelJuego;
    }

    // Clase encargada de actulizar las fisicas
    // TODO Optimizar codigo y hacerlo más claro.aa
    // TODO Forma de hacerlo podria ser pedir el parametro de la pelota y tener varios timer para cada pelota y asi poder eliminarlos sin tener problemas y cada clase tendria sus parametros separados y la carga seria mucho mas rapida.
    private class MovimientosPelota implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            
        }
        
    }

    String[] pathImg = { "\\img\\Brick1.png" };
    JLabel[] lblBricks;
    JLabel lblPlataforma;
    JLabel lblCircle;
    Timer fpsPelota;
    Timer fpsMejoras;
    VentanaPrincipal ventanaPrincipal;
    ArrayList<Integer> posicionesMejorasEliminadas = new ArrayList<>();
    MovimientosPelota movimientosPelota;
    ArrayList<JLabel> lblPelotas = new ArrayList<>();
    ArrayList<Mejora> mejoras = new ArrayList<>();
    MovimientoPlataforma movimientoPlataforma = new MovimientoPlataforma(this);
}
