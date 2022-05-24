package manuel_marin;

import java.awt.Point;

/**
 * Clase que hereda de Brick para formar un brick rojo
 */
public class RedBrick extends Brick {
    public RedBrick() {
        this(new Point());
    }

    /**
     * Inicializa las propiedades de los parametros
     * @param location Punto donde se encuentra el brick
     */
    public RedBrick(Point location) {
        super(ResourcesImg.RED_BRICK, location);
    }
    
}
