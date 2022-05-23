package manuel_marin;

import java.awt.Point;

public class GrayBrick extends Brick {
    public GrayBrick() {
        this(new Point());
    }

    public GrayBrick(Point location) {
        super(ResourcesImg.GRAY_BRICK, location);
    }
    
}
