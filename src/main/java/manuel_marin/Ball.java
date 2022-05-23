package manuel_marin;

import java.awt.Point;

import javax.swing.JLabel;

public class Ball extends JLabel {
    public Ball(Point location) {
        super();
        setIcon(ResourceLoader.skinLoader(ResourcesImg.BALL));
        this.setSize(this.getPreferredSize());
        this.setLocation(location);
        directionX = true;
        directionY = true;
    }

    boolean directionX;
    boolean directionY;
    final int NORMAL_SPEED = 4;
    final int ACELERATE_SPEED = 6;
}
