package manuel_marin;

import java.awt.Point;

public class BlueBrick extends Brick {

    public BlueBrick() {
        this(new Point());
    }

    public BlueBrick(Point location) {
        super(ResourcesImg.BLUE_BRICK, location);
    }
    
}
