package manuel_marin;

import java.awt.Point;

/**
 * Clase que hereda de Brick para formar un brck gris
 */
public class GrayBrick extends Brick {
    public GrayBrick() {
        this(new Point());
    }

    /**
     * Inicializa las propiedades de los parametros
     * @param location
     */
    public GrayBrick(Point location) {
        super(ResourcesImg.GRAY_BRICK, location);
    }
    
}
