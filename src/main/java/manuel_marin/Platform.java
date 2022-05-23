package manuel_marin;

import java.awt.Point;

import javax.swing.JLabel;

public class Platform extends JLabel {
    public Platform(Point location) {
        super();

        cornerHitbox = new JLabel[2];

        this.setIcon(ResourceLoader.skinLoader(ResourcesImg.PLATFORM));
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
            l.setLocation(calculateCornersHitboxs(i));
            cornerHitbox[i] = l;
        }
    }

    boolean direction;
    JLabel[] cornerHitbox;
}
