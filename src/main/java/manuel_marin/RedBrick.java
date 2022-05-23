package manuel_marin;

import java.awt.Point;

public class RedBrick extends Brick {
    public RedBrick() {
        this(new Point());
    }

    public RedBrick(Point location) {
        super(ResourcesImg.RED_BRICK, location);
    }
    
}
