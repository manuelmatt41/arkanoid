package manuel_marin;

import java.awt.Point;
import java.io.File;

import javax.swing.JLabel;

public class Ball extends JLabel {
    public Ball(Point location) {
        super();
        setIcon(ResourceLoader.skinLoader(skinFile, ResourceLoader.BALL));
        this.setSize(this.getPreferredSize());
        this.setLocation(location);
        directionX = true;
        directionY = true;
    }

    File skinFile = new File(System.getProperty("user.home") + "\\AppData\\Roaming\\arkanoid\\img\\Pelota.png");
    boolean directionX;
    boolean directionY;
    final int NORMAL_SPEED = 4;
    final int ACELERATE_SPEED = 6;
}
