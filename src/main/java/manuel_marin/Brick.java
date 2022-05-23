package manuel_marin;

import java.awt.Point;

import javax.swing.JLabel;

public class Brick extends JLabel {

    public Brick(ResourcesImg brickType, Point location) {
        super(ResourceLoader.skinLoader(brickType));
        cornerHitbox = new JLabel[4];
        sideHitbox = new JLabel[4];
        setSize(getPreferredSize());
        setLocation(location);
    }

    @Override
    public void setLocation(Point p) {
        super.setLocation(p);
        setCornerHitbox();
        setSideHitBox();
    }

    private Point calculateCornersHitboxs(int hitbox) {
        if (hitbox == 0) {
            return new Point(this.getLocation());
        }

        if (hitbox == 1) {
            return new Point(this.getLocation().x + this.getBounds().width - (this.getBounds().width / 20),
                    this.getLocation().y);
        }

        if (hitbox == 2) {
            return new Point(this.getLocation().x,
                    this.getLocation().y + this.getBounds().height - (this.getBounds().height / 6));
        }

        if (hitbox == 3) {
            return new Point(this.getLocation().x + this.getBounds().width - (this.getBounds().width / 20),
                    this.getLocation().y + this.getBounds().height - (this.getBounds().height / 6));
        }

        return new Point();
    }

    private Point calculateSidesHitboxs(int hitbox) {
        if (hitbox == 0 || hitbox == 1) {
            return new Point(this.getLocation());
        }

        if (hitbox == 2) {
            return new Point(this.getLocation().x + this.getBounds().width - (this.getBounds().width / 20),
                    this.getLocation().y);
        }

        if (hitbox == 3) {
            return new Point(this.getLocation().x,
                    this.getLocation().y + this.getBounds().height - (this.getBounds().height / 6));
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

    public JLabel[] getCornerHitbox() {
        return cornerHitbox;
    }

    public void setSideHitBox() {
        for (int i = 0; i < sideHitbox.length; i++) {
            JLabel l = new JLabel();

            if (i == 0 || i == 3) {
                l.setSize(100, 5);
            } else {
                l.setSize(5, 32);
            }

            l.setLocation(calculateSidesHitboxs(i));
            sideHitbox[i] = l;
        }
    }

    public JLabel[] getSideHitBox() {
        return sideHitbox;
    }


    JLabel[] cornerHitbox;
    JLabel[] sideHitbox;
}
