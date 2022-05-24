package manuel_marin;

import java.awt.Point;

/**
 * Clase que hereda de Brick para formar un Brick azul
 */
public class BlueBrick extends Brick {

    public BlueBrick() {
        this(new Point());
    }

    /**
     * Inicializa las propiedades de los parametros
     * @param location
     */
    public BlueBrick(Point location) {
        super(ResourcesImg.BLUE_BRICK, location);
    }
    
}
