package manuel_marin;

import java.awt.Point;
import java.io.File;

import javax.swing.JLabel;

public class Platform extends JLabel {
    public Platform(Point location) {
        super();
        this.setIcon(ResourceLoader.skinLoader(skinFile, ResourceLoader.PLATFORM));
        this.setSize(this.getPreferredSize());
        this.setLocation(location);
    }

    File skinFile = new File(System.getProperty("user.home") + "\\AppData\\Roaming\\arkanoid\\img\\Plataforma.png");
    boolean direction;
}
