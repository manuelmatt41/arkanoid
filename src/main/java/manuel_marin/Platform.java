package manuel_marin;

import java.awt.Point;

import javax.swing.JLabel;

/**
 * Clase que hereda de JLabel para formar la plataforma del juego
 */
public class Platform extends JLabel {
    /**
     * Incializa las propiedades de los parametros
     * @param location Punto donde se encuentra la plataforma
     */
    public Platform(Point location) {
        super();

        cornerHitbox = new JLabel[2];

        this.setIcon(ResourceLoader.skinLoader(ResourcesImg.PLATFORM));
        this.setSize(this.getPreferredSize());
        this.setLocation(location);
        setCornerHitbox();
    }

    /**
     * Devuelve la posicion de la hitbox superior de la plataforma
     * @param hitbox Hitbox que se va a calcular la hitbox
     * @return Devuelve la posiconde la hitbox
     */
    private Point calculateCornersHitboxs(int hitbox) {
        if (hitbox == 0) {
            return new Point(this.getLocation());
        }

        if (hitbox == 1) {
            return new Point(this.getLocation().x + this.getBounds().width - (this.getBounds().width / 20),
                    this.getLocation().y);
        }

        return new Point();
    }

    /**
     * Establece las hitbox de las esquinas superiores de la plataforma
     */
    public void setCornerHitbox() {
        for (int i = 0; i < cornerHitbox.length; i++) {
            JLabel l = new JLabel();
            l.setSize(5, 5);
            l.setLocation(calculateCornersHitboxs(i));
            cornerHitbox[i] = l;
        }
    }

    /**
     * Direccion de movimiento de la plataforma
     */
    boolean direction;
    /**
     * Lista de las hitbox de las esquinas superiores de la plataforma
     */
    JLabel[] cornerHitbox;
}
