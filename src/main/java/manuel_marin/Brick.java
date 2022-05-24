package manuel_marin;

import java.awt.Point;

import javax.swing.JLabel;

/**
 * Clase que hereda de JLabel para formar bricks en el juego.
 */
public class Brick extends JLabel {

    /**
     * Inicializa las propiedades de los parametros
     * @param brickType Tipo de imagen que lleva el brick
     * @param location Punto donde se encuentra el brick en el JPanel
     */
    public Brick(ResourcesImg brickType, Point location) {
        super(ResourceLoader.skinLoader(brickType));
        cornerHitbox = new JLabel[4];
        sideHitbox = new JLabel[4];
        setSize(getPreferredSize());
        setLocation(location);
    }

    /**
     * Realiza la funcion de la clase heredada y llama los setter de esta clase.
     */
    @Override
    public void setLocation(Point p) {
        super.setLocation(p);
        setCornerHitbox();
        setSideHitBox();
    }

    /**
     * Devuelve la posicion de las hitboxs de las esquinas del brick
     * @param hitbox La hitbox que se va a calcular la posicion.
     * @return Devuelve la posicion de las hitbox de las esquinas del brick
     */
    private Point calculateCornersHitboxs(int hitbox) {
        if (hitbox == 0) {
            return new Point(this.getLocation());
        }

        if (hitbox == 1) {
            return new Point(this.getLocation().x + this.getBounds().width - (this.getBounds().width / 20),
                    this.getLocation().y);
        }

        if (hitbox == 2) {
            return new Point(this.getLocation().x,
                    this.getLocation().y + this.getBounds().height - (this.getBounds().height / 6));
        }

        if (hitbox == 3) {
            return new Point(this.getLocation().x + this.getBounds().width - (this.getBounds().width / 20),
                    this.getLocation().y + this.getBounds().height - (this.getBounds().height / 6));
        }

        return new Point();
    }

    /**
     * Devuelve la posicion de las hitboxs laterales del brick
     * @param hitbox La hitbox que va a calcular la posicion
     * @return Devuelve la posicion de la hitbox del brick
     */
    private Point calculateSidesHitboxs(int hitbox) {
        if (hitbox == 0 || hitbox == 1) {
            return new Point(this.getLocation());
        }

        if (hitbox == 2) {
            return new Point(this.getLocation().x + this.getBounds().width - (this.getBounds().width / 20),
                    this.getLocation().y);
        }

        if (hitbox == 3) {
            return new Point(this.getLocation().x,
                    this.getLocation().y + this.getBounds().height - (this.getBounds().height / 6));
        }

        return new Point();
    }

    /**
     * Establece el valor de cornerHitbox
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
     * Devuelve el valor cornerHitbox
     * @return Devuelve el valor cornerHitbox
     */
    public JLabel[] getCornerHitbox() {
        return cornerHitbox;
    }

    /**
     * Establece el valor sideHitbox
     */
    public void setSideHitBox() {
        for (int i = 0; i < sideHitbox.length; i++) {
            JLabel l = new JLabel();

            if (i == 0 || i == 3) {
                l.setSize(100, 5);
            } else {
                l.setSize(5, 32);
            }

            l.setLocation(calculateSidesHitboxs(i));
            sideHitbox[i] = l;
        }
    }

    /**
     * Devuelve el valor sideHitbox
     * @return Devuelve el valor sideHitbox
     */
    public JLabel[] getSideHitBox() {
        return sideHitbox;
    }

    /**
     * Lista de las hitbox de las esquinas del brick
     */
    JLabel[] cornerHitbox;
    /**
     * Lista de las hitbox de los laterales del brick
     */
    JLabel[] sideHitbox;
}
