package manuel_marin;

import java.awt.BorderLayout;

import javax.swing.JFrame;

/**
 * Clase que hereda de JFrame para gestionar una ventana.
 */
public class VentanaPrincipal extends JFrame {
    /**
     * Inicializa las propiedades de los parametros.
     */
    public VentanaPrincipal() {
        super("Araknoid");
        setLayout(new BorderLayout());

        panelMenuPrincipal = new PanelMenuPrincipal(this);
        add(panelMenuPrincipal, BorderLayout.CENTER);

        setUndecorated(true);
        setVisible(true);
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    /**
     * Panel donde se encuentra el juego.
     */
    PanelJuego panelJuego;
    /**
     * Panel donde se encuentra los datos de la partida.
     */
    PanelDatosPartida panelPuntuacion;
    PanelMenuNiveles panelMenuJuego;
    PanelCreacionNiveles panelCreacionNiveles;
    PanelMenuPrincipal panelMenuPrincipal;
}
