/*-
 * =====LICENSE-START=====
 * Java 11 Application
 * ------
 * Copyright (C) 2020 - 2022 Organization Name
 * ------
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * =====LICENSE-END=====
 */
package manuel_marin;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * Clase donde se muestra el juego.
 */
public class GamePanel extends JPanel {
    /**
     * inicializa las propiedades de los parametros.
     * 
     * @param ventanaPrincipal El JFrame donde se carga la clase.
     */
    public GamePanel(ArkanoidFrame ventanaPrincipal, String nivel) {
        setLayout(null);
        this.ventanaPrincipal = ventanaPrincipal;
        this.nivel = nivel;
        iniciarComponentes();

        setBackground(new Color(147, 176, 171));
        setFocusable(true);
    }

    /**
     * Inicio los componentes del panel.
     */
    private void iniciarComponentes() {
        // Crea los ladrillos juntos a sus hitboxs
        int x = 0, y = 0;
        bricks = LevelLoader.levelLoad(new File(nivel));
        hitBoxBricksEsquinas = new JLabel[bricks.length][4];
        hitBoxBricksLaterales = new JLabel[bricks.length][4];

        for (int i = 0; i < bricks.length; i++) {
            if (bricks[i] != null) {
                bricks[i].setSize(bricks[i].getPreferredSize());
                bricks[i].setLocation(x, y);
                bricks[i].addKeyListener(movimientoPlataforma);
                hitBoxBricksEsquinas[i] = getPosicionesHitBoxEsquinas(bricks[i]);
                hitBoxBricksLaterales[i] = getPosicionesHitBoxLaterales(bricks[i], hitBoxBricksEsquinas[i]);
                add(bricks[i]);
                bricks[i] = bricks[i];

                for (int j = 0; j < hitBoxBricksEsquinas[i].length; j++) {
                    add(hitBoxBricksEsquinas[i][j]);
                }

                for (int j = 0; j < hitBoxBricksLaterales[i].length; j++) {
                    add(hitBoxBricksLaterales[i][j]);
                }
            }

            if ((i + 1) % 7 == 0) {
                x = 0;
                y += 32;
            } else {
                x += 100;
            }
        }

        // Crea la plataforma con sus hitboxs
        plataforma = new JLabel(new ImageIcon(GamePanel.class.getResource("\\img\\Plataforma.png")));
        plataforma.setSize(plataforma.getPreferredSize());
        plataforma.setLocation(250, 430);
        plataforma.addKeyListener(movimientoPlataforma);
        add(plataforma);

        hitBoxPlataformaEsquinas = getPosicionesHitBoxEsquinas(plataforma);

        for (int i = 0; i < hitBoxPlataformaEsquinas.length; i++) {
            add(hitBoxPlataformaEsquinas[i]);
        }

        hitBoxPlataformaLateral = getPosicionesHitBoxLaterales(plataforma, hitBoxPlataformaEsquinas);

        for (int i = 0; i < hitBoxPlataformaLateral.length; i++) {
            add(hitBoxPlataformaLateral[i]);
        }

        // Crea la pelota
        pelota = new JLabel(new ImageIcon(GamePanel.class.getResource("\\img\\Pelota.png")));
        pelota.setSize(20, 20);
        pelota.setLocation(300, 412);
        add(pelota);

        // Crea un timer que se encarga de los movimientos
        movimientosPelota = new MovimientosPelota();

        fpsPelota = new Timer(17, movimientosPelota);
        addKeyListener(movimientoPlataforma);

        fpsPlataforma = new Timer(17, movimientoPlataforma);
    }

    /**
     * Crea y devuelve un array de JLabel que representan las hitbox de las esquinas
     * de los ladrillos. De manera que 0.Arriba izquierda, 1.Arriba derecha, 2.Abajo
     * izquierda y 3.Abajo derecha.
     * 
     * @param brick JLabel que representa el brick para calcular las posiciones
     *              correspondientes para las hitbox de las esquinas.
     * @return Devuelve un array de JLabel.
     */
    private JLabel[] getPosicionesHitBoxEsquinas(JLabel brick) {
        JLabel[] hitboxs = new JLabel[4];
        Point[] posicionesHitBoxs = new Point[4];

        posicionesHitBoxs[0] = new Point(brick.getLocation());

        posicionesHitBoxs[1] = new Point(
                brick.getLocation().x + brick.getBounds().width - (brick.getBounds().width / 20),
                brick.getLocation().y);

        posicionesHitBoxs[2] = new Point(brick.getLocation().x,
                brick.getLocation().y + brick.getBounds().height - (brick.getBounds().height / 6));

        posicionesHitBoxs[3] = new Point(
                brick.getLocation().x + brick.getBounds().width - (brick.getBounds().width / 20),
                brick.getLocation().y + brick.getBounds().height - (brick.getBounds().height / 6));

        for (int i = 0; i < hitboxs.length; i++) {
            JLabel hitbox = new JLabel();
            hitbox.setSize(5, 5);
            hitbox.setLocation(posicionesHitBoxs[i]);
            hitboxs[i] = hitbox;
        }

        return hitboxs;
    }

    /**
     * Crea y devuelve un array de JLabel que representan las hitbox de laterales
     * de los ladrillos respectos a las posiciones de las hitbox de las esquinas. De
     * manera que 0.Arriba, 1.Izquierda, 2.Derecha y 3.Abajo.
     * 
     * @param brick          JLabel que representa el brick para calcular las
     *                       posiciones
     *                       correspondientes para las hitbox de las esquinas.
     * @param hitBoxEsquinas Array de hitbox de esquinas para calcular las
     *                       posiciones respecto a las esquinas
     * @return Devuelve un array de JLabel.
     */
    private JLabel[] getPosicionesHitBoxLaterales(JLabel brick, JLabel[] hitBoxEsquinas) {
        JLabel[] hitboxs = new JLabel[4];
        Point[] posicionesLateralesHitBoxs = new Point[4];

        posicionesLateralesHitBoxs[0] = new Point(
                hitBoxEsquinas[0].getLocation().x + hitBoxEsquinas[0].getBounds().width,
                hitBoxEsquinas[0].getLocation().y);

        posicionesLateralesHitBoxs[1] = new Point(
                hitBoxEsquinas[0].getLocation().x,
                hitBoxEsquinas[0].getLocation().y + hitBoxEsquinas[0].getBounds().height);

        posicionesLateralesHitBoxs[2] = new Point(
                hitBoxEsquinas[1].getLocation().x,
                hitBoxEsquinas[1].getLocation().y + hitBoxEsquinas[1].getBounds().height);

        posicionesLateralesHitBoxs[3] = new Point(
                hitBoxEsquinas[2].getLocation().x + hitBoxEsquinas[2].getBounds().width,
                hitBoxEsquinas[2].getLocation().y);

        for (int i = 0; i < hitboxs.length; i++) {
            JLabel hitboxLateral = new JLabel();
            if (i == 0 || i == 3) {
                hitboxLateral.setSize(90, 5);
            } else {
                hitboxLateral.setSize(5, 12);
            }
            hitboxLateral.setLocation(posicionesLateralesHitBoxs[i]);
            hitboxs[i] = hitboxLateral;
        }

        return hitboxs;
    }

    /**
     * Clase encargada del movimiento de la plataforma.
     */
    private class MovimientoPlataforma extends KeyAdapter implements ActionListener {
        /**
         * KeyListener que si se pulsa las flechas laterales mueve la plataforma a su
         * respectivo lado.
         * Si se pulsa la flecha de arriba mueve la pelota, ya que inicia el Timer que
         * se encarga de moverla.
         */
        @Override
        public void keyPressed(KeyEvent e) {

            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                direccion = false;
                fpsPlataforma.start();
            }

            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                direccion = true;
                fpsPlataforma.start();
            }

            if (e.getKeyCode() == KeyEvent.VK_UP) {
                if (!fpsPelota.isRunning()) {
                    fpsPelota.start();
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            fpsPlataforma.stop();
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Point nuevoPunto = plataforma.getLocation();

            if (direccion) {
                if (plataforma.getLocation().x <= 600) {
                    nuevoPunto.x += 8;

                    for (int i = 0; i < hitBoxPlataformaEsquinas.length; i++) {
                        hitBoxPlataformaEsquinas[i].setLocation(hitBoxPlataformaEsquinas[i].getLocation().x + 8,
                                hitBoxPlataformaEsquinas[i].getLocation().y);
                        hitBoxPlataformaLateral[i].setLocation(hitBoxPlataformaLateral[i].getLocation().x + 8,
                                hitBoxPlataformaLateral[i].getLocation().y);
                    }
                }
                plataforma.setLocation(nuevoPunto);
            } else {
                if (plataforma.getLocation().x >= 0) {
                    nuevoPunto.x -= 8;

                    for (int i = 0; i < hitBoxPlataformaEsquinas.length; i++) {
                        hitBoxPlataformaEsquinas[i].setLocation(hitBoxPlataformaEsquinas[i].getLocation().x - 8,
                                hitBoxPlataformaEsquinas[i].getLocation().y);
                        hitBoxPlataformaLateral[i].setLocation(hitBoxPlataformaLateral[i].getLocation().x - 8,
                                hitBoxPlataformaLateral[i].getLocation().y);
                    }
                }
                plataforma.setLocation(nuevoPunto);
            }
        }

        boolean direccion;
    }

    /**
     * Clase encargada de actulizar las fisicas.
     */
    private class MovimientosPelota implements ActionListener {
        /**
         * Inicializa las propiedades de los parametros.
         */
        public MovimientosPelota() {
            velocidadJuego = velocidadNormal;
            posicionX = pelota.getLocation().x;
            posicionY = pelota.getLocation().y;
            direccionX = false;
            direccionY = true;
            ladrillos = 0;

            for (int i = 0; i < bricks.length; i++) {
                if (bricks[i] != null) {
                    ladrillos++;
                }
            }
        }

        /**
         * Calcula las fisicas de la pelota y su respuesta a la plataforma y los
         * ladrillos que toca.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            // Detecta que ladrillo ha sido tocado.
            for (int i = 0; i < bricks.length; i++) {
                if (bricks[i] != null) {
                    if (bricks[i].isVisible()) {
                        if (bricks[i].getBounds().intersects(pelota.getBounds())) {
                            // Detecta que tipo de hitbox a tocado.
                            for (int j = 0; j < hitBoxBricksEsquinas[i].length; j++) {
                                if (hitBoxBricksEsquinas[i][j].isVisible()) {
                                    if (hitBoxBricksEsquinas[i][j].getBounds().intersects(pelota.getBounds())) {
                                        if (j == 0) {
                                            if (direccionX) {
                                                direccionX = false;
                                            }

                                            if (direccionY) {
                                                direccionY = false;
                                            }
                                        }

                                        if (j == 1) {
                                            if (!direccionX) {
                                                direccionX = true;
                                            }

                                            if (direccionY) {
                                                direccionY = false;
                                            }
                                        }

                                        if (j == 2) {
                                            if (direccionX) {
                                                direccionX = false;
                                            }

                                            if (!direccionY) {
                                                direccionY = true;
                                            }
                                        }

                                        if (j == 3) {
                                            if (!direccionX) {
                                                direccionX = true;
                                            }

                                            if (!direccionY) {
                                                direccionY = true;
                                            }
                                        }

                                        velocidadJuego = velocidadAcelerada;
                                        if (bricks[i].getIcon().toString().contains("Brick1")) {
                                            for (int k = 0; k < hitBoxBricksEsquinas[i].length; k++) {
                                                hitBoxBricksEsquinas[i][k].setVisible(false);
                                                hitBoxBricksLaterales[i][k].setVisible(false);
                                            }
                                        }
                                    } else {
                                        if (hitBoxBricksLaterales[i][j].isVisible()) {
                                            if (hitBoxBricksLaterales[i][j].getBounds()
                                                    .intersects(pelota.getBounds())) {
                                                if (j == 0) {
                                                    direccionY = false;
                                                }

                                                if (j == 1) {
                                                    direccionX = false;
                                                }

                                                if (j == 2) {
                                                    direccionX = true;
                                                }

                                                if (j == 3) {
                                                    direccionY = true;
                                                }

                                                velocidadJuego = velocidadNormal;
                                                if (bricks[i].getIcon().toString().contains("Brick1")) {
                                                    for (int k = 0; k < hitBoxBricksEsquinas[i].length; k++) {
                                                        hitBoxBricksEsquinas[i][k].setVisible(false);
                                                        hitBoxBricksLaterales[i][k].setVisible(false);
                                                    }
                                                }
                                            }
                                        }

                                    }
                                }
                            }
                            if (romperLadrillo(bricks[i])) {
                                bricks[i].setVisible(false);
                                ladrillos--;

                            }
                            ventanaPrincipal.gameDataPanel.puntuacion += 100;
                            ventanaPrincipal.gameDataPanel.updateLabels();
                        }

                    }
                }
            }

            if (ladrillos == mapaVacio) {

                ventanaPrincipal.levelSelectionPanel.setVisible(true);

                ventanaPrincipal.gamePanel.setVisible(false);
                ventanaPrincipal.gameDataPanel.setVisible(false);
                ventanaPrincipal.remove(ventanaPrincipal.gameDataPanel);
                ventanaPrincipal.remove(ventanaPrincipal.gamePanel);
            }

            // Detecta si la plataforma ha tocado la pelota.
            if (plataforma.getBounds().intersects(pelota.getBounds())) {
                for (int j = 0; j < 2; j++) {
                    if (hitBoxPlataformaEsquinas[j].getBounds().intersects(pelota.getBounds())) {
                        if (j == 0) {
                            if (direccionX) {
                                direccionX = false;
                            }
                            direccionY = false;
                            velocidadJuego = 6;
                        }

                        if (j == 1) {
                            if (!direccionX) {
                                direccionX = true;
                            }
                            direccionY = false;

                            velocidadJuego = velocidadAcelerada;
                        }
                    } else {
                        if (hitBoxPlataformaLateral[0].getBounds().intersects(pelota.getBounds())) {
                            direccionY = false;
                            velocidadJuego = velocidadNormal;
                        }
                    }
                }

            }

            // Detecta los bordes del panel
            if (pelota.getLocation().x <= 0) {
                direccionX = true;
            }

            if (pelota.getLocation().x >= 680) {
                direccionX = false;
            }

            if (pelota.getLocation().y <= 0) {
                direccionY = true;
            }

            // Si cae para reiniciar la partida hasta que se quede sin vidas y se vuelve a
            // empezar.
            if (pelota.getLocation().y >= 440) {
                ventanaPrincipal.gameDataPanel.vidas--;
                ventanaPrincipal.gameDataPanel.updateLabels();

                if (ventanaPrincipal.gameDataPanel.vidas > 0) {
                    pelota.setLocation(300, 412);
                    plataforma.setLocation(250, 430);

                    JLabel[] reinicioHitBox = getPosicionesHitBoxEsquinas(plataforma);

                    for (int i = 0; i < hitBoxPlataformaEsquinas.length; i++) {
                        hitBoxPlataformaEsquinas[i].setLocation(reinicioHitBox[i].getLocation());
                    }

                    reinicioHitBox = getPosicionesHitBoxLaterales(plataforma, hitBoxPlataformaEsquinas);
                    for (int i = 0; i < hitBoxPlataformaLateral.length; i++) {
                        hitBoxPlataformaLateral[i].setLocation(reinicioHitBox[i].getLocation());
                    }

                    actualizarParametros();
                    fpsPelota.stop();
                    return;
                } else {
                    ventanaPrincipal.dispose();
                    new ArkanoidFrame();
                    fpsPelota.stop();
                    return;
                }
            }

            // Mueve en x e y dependiendo la dirreccion que lleve true es positivo y false
            // negativo.
            if (direccionX) {
                posicionX += velocidadJuego;
            } else {
                posicionX -= velocidadJuego;
            }

            if (direccionY) {
                posicionY += velocidadJuego;
            } else {
                posicionY -= velocidadJuego;
            }

            pelota.setLocation(posicionX, posicionY);
        }

        /**
         * Vuelve los parametros a los datos por defecto al empezar otra ronda.
         */
        private void actualizarParametros() {
            posicionX = pelota.getLocation().x;
            posicionY = pelota.getLocation().y;
            direccionX = false;
            direccionY = true;
        }

        /**
         * Comprueba que si rompe un ladrillo no tenga mas capas, devuelve true si es la
         * ultima capa y false si hay mas.
         * 
         * @param brick Ladrillo que se comprueba que tiene mas capas.
         * @return Devuelve truee si no hay mas capas y false si hay mas.
         */
        private boolean romperLadrillo(JLabel brick) {
            if (brick.getIcon().toString().contains("Brick2.png")) {
                brick.setIcon(new ImageIcon(GamePanel.class.getResource("\\img\\Brick1.png")));
                return false;
            } else {
                return true;
            }
        }

        /**
         * Valor de la velocidad.
         */
        int velocidadJuego;
        int velocidadNormal = 4;
        /**
         * Valor de la posicion x de la pelota.
         */
        int posicionX;
        /**
         * Valor de la posicion y de la pelota.
         */
        int posicionY;
        /**
         * Valor de la direccion que lleva la pelota true positivo y false neativo.
         */
        boolean direccionX;
        /**
         * Valor de la direccion que lleva la pelota true positivo y false neativo.
         */
        boolean direccionY;
        int ladrillos;
        int velocidadAcelerada = 6;
        int mapaVacio = 0;
    }

    /**
     * Array de ladrillos del mapa.
     */
    JLabel[] bricks;
    /**
     * Array bidimensional para las hitboxs de las esquinas de todos los ladrillos.
     */
    JLabel[][] hitBoxBricksEsquinas;
    /**
     * Array bidimensional para las hitboxs de los laterales de todos los ladrillos.
     */
    JLabel[][] hitBoxBricksLaterales;
    /**
     * Plataforma del juego.
     */
    JLabel plataforma;
    /**
     * Array para las hitboxs de las esquinas de la plataforma.
     */
    JLabel[] hitBoxPlataformaEsquinas;
    /**
     * Array para las hitboxs de los laterales de la plataforma.
     */
    JLabel[] hitBoxPlataformaLateral;
    /**
     * Pelota deel juego.
     */
    JLabel pelota;
    /**
     * Valor del JFrame donde esta el juego.
     */
    ArkanoidFrame ventanaPrincipal;
    /**
     * Clase encargada del movimiento de la pelota
     */
    MovimientosPelota movimientosPelota;
    /**
     * Clase encargada de iniciar el movimiento de la pelota cada cierto timpo.
     */
    Timer fpsPelota;
    /**
     * Clas encargada del movimiento de la plataforma.
     */
    MovimientoPlataforma movimientoPlataforma = new MovimientoPlataforma();
    Timer fpsPlataforma;
    String nivel;
}