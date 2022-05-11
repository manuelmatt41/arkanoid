package manuel_marin;

import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Mejora extends JLabel {
    public Mejora(URL url, int probabilidad) {
        super(new ImageIcon(url));
        setProbabilidad(probabilidad);
    }

    public void setProbabilidad(int probabilidad) {
        this.probabilidad = probabilidad;
    }

    public int getProbabilidad() {
        return probabilidad;
    }

    private int probabilidad;
}
