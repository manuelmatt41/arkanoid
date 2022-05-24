package manuel_marin;

import java.io.File;
import java.net.URL;

/**
 * Clase que contiene las imagenes de distintos objetos de la aplicacion
 */
public enum ResourcesImg {
    PLATFORM(ResourceLoader.class.getResource("resource\\img\\Plataforma.png"),
            new File(System.getProperty("user.home") + "\\AppData\\Roaming\\arkanoid\\img\\Plataforma.png")),
    BALL(ResourceLoader.class.getResource("resource\\img\\Pelota.png"),
            new File(System.getProperty("user.home") + "\\AppData\\Roaming\\arkanoid\\img\\Pelota.png")),
    BLUE_BRICK(ResourceLoader.class.getResource("resource\\img\\Brick1.png"), null),
    RED_BRICK(ResourceLoader.class.getResource("resource\\img\\Brick2.png"), null),
    GRAY_BRICK(ResourceLoader.class.getResource("resource\\img\\Brick3.png"), null);

    /**
     * Inicializa las propiedades de los parametros
     * @param resourcePath
     * @param userFile
     */
    private ResourcesImg(URL resourcePath, File userFile) {
        this.resourcePath = resourcePath;
        this.userFile = userFile;
    }

    /**
     * Path de la imagen por defecto
     */
    URL resourcePath;
    /**
     * Path de la imagen del usuario
     */
    File userFile;
}
