package manuel_marin;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JPanel;

public class PanelMenuJuego extends JPanel {
    public PanelMenuJuego(VentanaPrincipal ventanaPrincipal) {
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
        public ActionManagement(PanelMenuJuego panelMenuJuego) {
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

        PanelMenuJuego panelMenuJuego;
    }

    File[] niveles;
    VentanaPrincipal ventanaPrincipal;
    ActionManagement actionManagement = new ActionManagement(this);
}
