package manuel_marin;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class VentanaPrincipal extends JFrame {
    public VentanaPrincipal() {
        super("Araknoid");
        setLayout(new BorderLayout());

        panelPuntuacion = new PanelDatosPartida();
        add(panelPuntuacion, BorderLayout.NORTH);

        panelJuego = new PanelJuego(this);
        add(panelJuego, BorderLayout.CENTER);

        setUndecorated(true);
        setVisible(true);
        setSize(600, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }

    PanelJuego panelJuego;
    PanelDatosPartida panelPuntuacion;
}
