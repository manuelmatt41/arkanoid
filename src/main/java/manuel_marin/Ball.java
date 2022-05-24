package manuel_marin;

import java.awt.Point;

import javax.swing.JLabel;

/**
 * Clase que hereda de JLabel para crear una pelota en el juego.
 */
public class Ball extends JLabel {
    /**
     * Inicializa las propiedades de los parametros
     * @param location Punto donde se encuentra en la JPanel
     */
    public Ball(Point location) {
        super();
        setIcon(ResourceLoader.skinLoader(ResourcesImg.BALL));
        this.setSize(15, 15);
        this.setLocation(location);
        directionX = true;
        directionY = true;
    }

    /**
     * Direccion X de la pelota
     */
    boolean directionX;
    /**
     * Direccion Y de la pelota
     */
    boolean directionY;
    /**
     * Valor de la velocidad normal de la pelota
     */
    final int NORMAL_SPEED = 4;
    /**
     * Valor de la velocidad acelerada de la pelota
     */
    final int ACELERATE_SPEED = 6;
}
