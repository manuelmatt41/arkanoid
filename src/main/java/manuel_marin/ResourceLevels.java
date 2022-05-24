package manuel_marin;

import java.net.URL;

/**
 * Enumeracion de niveles por defecto
 */
public enum ResourceLevels {
    NIVEL_1(ResourceLevels.class.getResource("resource/niveles/1.txt")),
    NIVEL_2(ResourceLevels.class.getResource("resource/niveles/2.txt")),
    NIVEL_3(ResourceLevels.class.getResource("resource/niveles/3.txt"));

    /**
     * Inicializa las propiedades de los parametros
     * @param resourcePath Path del archivo que guarda el nivel
     */
    private ResourceLevels(URL resourcePath) {
        this.resourcePath = resourcePath;
    }

    /**
     * Path del archivo que guarda el nivel
     */
    URL resourcePath;
}
