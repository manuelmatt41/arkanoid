package manuel_marin;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JPanel;

public class PanelMenuNiveles extends JPanel {
    public PanelMenuNiveles(VentanaPrincipal ventanaPrincipal) {
        setLayout(null);

        this.ventanaPrincipal = ventanaPrincipal;
        iniciarCantidadNiveles();
        int x = 30, y = 30;
        for (int i = 0; i < niveles.length; i++) {
            JButton b = new JButton("Nivel " + (i + 1));
            b.setActionCommand(niveles[i].getAbsolutePath());
            b.setSize(b.getPreferredSize());
            b.setLocation(x, y);
            b.addActionListener(actionManagement);
            b.addKeyListener(keyHandler);
            add(b);

            if ((i + 1) % 6 == 0) {
                x = 30;
                y += 30;
            } else {
                x += 75;
            }
        }

    }

    private void iniciarCantidadNiveles() {
        File carpetaNiveles = new File("src\\main\\java\\manuel_marin\\niveles");

        niveles = carpetaNiveles.listFiles();
    }

    private class ActionManagement implements ActionListener {
        public ActionManagement(PanelMenuNiveles panelMenuJuego) {
            this.panelMenuJuego = panelMenuJuego;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            ventanaPrincipal.panelPuntuacion = new PanelDatosPartida();
            ventanaPrincipal.add(ventanaPrincipal.panelPuntuacion, BorderLayout.NORTH);

            ventanaPrincipal.panelJuego = new PanelJuego(ventanaPrincipal, e.getActionCommand());
            ventanaPrincipal.add(ventanaPrincipal.panelJuego, BorderLayout.CENTER);
            
            panelMenuJuego.setVisible(false);
        }

        PanelMenuNiveles panelMenuJuego;
    }

    private class KeyHandler extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                ventanaPrincipal.panelMenuPrincipal = new PanelMenuPrincipal(ventanaPrincipal);
                ventanaPrincipal.add(ventanaPrincipal.panelMenuPrincipal, BorderLayout.CENTER);

                ventanaPrincipal.panelMenuJuego.setVisible(false);
                ventanaPrincipal.remove(ventanaPrincipal.panelMenuJuego);
            }
        }
    }

    File[] niveles;
    VentanaPrincipal ventanaPrincipal;
    ActionManagement actionManagement = new ActionManagement(this);
    KeyHandler keyHandler = new KeyHandler();
}
