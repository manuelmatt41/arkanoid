package manuel_marin;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Clase se contiene los datos de la partida.
 */
public class PanelDatosPartida extends JPanel {
    /**
     * Inicializa las propiedades de los parametros.
     */
    public PanelDatosPartida() {
        setLayout(new FlowLayout(FlowLayout.CENTER, 40, 0));
        puntuacion = 0;
        vidas = 3;

        lblPuntuacion = new JLabel("Puntuacion: " + puntuacion);
        lblPuntuacion.setFont(new Font("sans serif", Font.BOLD, 16));
        lblPuntuacion.setSize(lblPuntuacion.getPreferredSize());
        add(lblPuntuacion);

        lblVidas = new JLabel("Vidas: " + vidas);
        lblVidas.setFont(new Font("sans serif", Font.BOLD, 16));
        lblVidas.setSize(lblVidas.getPreferredSize());
        add(lblVidas);

        setBackground(new Color(147, 176, 171));
    }

    /**
     * Actuliza las Jlabel.
     */
    public void updateLabels() {
        lblPuntuacion.setText("Puntuacion: " + puntuacion);
        lblPuntuacion.setSize(lblPuntuacion.getPreferredSize());
        lblVidas.setText("Vidas: " + vidas);
        lblVidas.setSize(lblVidas.getPreferredSize());
    }

    /**
     * Muestra la puntuacion de la partida.
     */
    JLabel lblPuntuacion;
    /**
     * Muestra las vidad de la partida.
     */
    JLabel lblVidas;
    /**
     * valor de la puntuacion de la partida.
     */
    int puntuacion;
    /**
     * Valor de las vidad de la partida.
     */
    int vidas;
}
