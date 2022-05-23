package manuel_marin;

import java.net.URL;

public enum ResourceLevels {
    NIVEL_1(ResourceLevels.class.getResource("resource/niveles/1.txt")),
    NIVEL_2(ResourceLevels.class.getResource("resource/niveles/2.txt")),
    NIVEL_3(ResourceLevels.class.getResource("resource/niveles/3.txt"));

    private ResourceLevels(URL resourcePath) {
this.resourcePath = resourcePath;
    }

    URL resourcePath;
}
