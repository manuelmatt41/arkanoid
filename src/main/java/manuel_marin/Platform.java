package manuel_marin;

import java.awt.Color;
import java.awt.Point;
import java.io.File;

import javax.swing.JLabel;

public class Platform extends JLabel {
    public Platform(Point location) {
        super();

        cornerHitbox = new JLabel[2];

        this.setIcon(ResourceLoader.skinLoader(skinFile, ResourceLoader.PLATFORM));
        this.setSize(this.getPreferredSize());
        this.setLocation(location);
        setCornerHitbox();
    }

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

    public void setCornerHitbox() {
        for (int i = 0; i < cornerHitbox.length; i++) {
            JLabel l = new JLabel();
            l.setSize(5, 5);
            l.setOpaque(true);
            l.setBackground(Color.green);
            l.setLocation(calculateCornersHitboxs(i));
            cornerHitbox[i] = l;
        }
    }

    File skinFile = new File(System.getProperty("user.home") + "\\AppData\\Roaming\\arkanoid\\img\\Plataforma.png");
    boolean direction;
    JLabel[] cornerHitbox;
}
