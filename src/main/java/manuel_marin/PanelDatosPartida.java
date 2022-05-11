package manuel_marin;

import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class PanelDatosPartida extends JPanel {
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
    }

    public void updateLabels() {
        lblPuntuacion.setText("Puntuacion: " + puntuacion);
        lblPuntuacion.setSize(lblPuntuacion.getPreferredSize());
        lblVidas.setText("Vidas: " + vidas);
        lblVidas.setSize(lblVidas.getPreferredSize());
    }

    JLabel lblPuntuacion;
    JLabel lblVidas;
    int puntuacion;
    int vidas;
}
