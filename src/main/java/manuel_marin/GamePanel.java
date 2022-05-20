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

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.EtchedBorder;

/**
 * Clase donde se muestra el juego.
 */
public class GamePanel extends JPanel {
    /**
     * inicializa las propiedades de los parametros.
     * 
     * @param arkanoidFrame El JFrame donde se carga la clase.
     */
    public GamePanel(ArkanoidFrame arkanoidFrame, File levelFile) {
        setLayout(null);
        this.arkanoidFrame = arkanoidFrame;
        this.levelFile = levelFile;

        startComponents();
        startTimers();

        setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, Color.black, Color.black));
        setBackground(ArkanoidFrame.BACKGROUND_COLOR);
    }

    private void startComponents() {
        bricks = ResourceLoader.levelLoad(levelFile);

        for (int i = 0; i < bricks.length; i++) {
            if (bricks[i] != null) {
                add(bricks[i]);
                for (int j = 0; j < bricks[i].getCornerHitbox().length; j++) {
                    add(bricks[i].getCornerHitbox()[j]);
                    add(bricks[i].getSideHitBox()[j]);
                }
            }
        }

        platform = new Platform(PLATFORM_SPAWN);
        add(platform);

        ball = new Ball(BALL_SPAWN);
        add(ball);
    }

    private void startTimers() {
        platformFPS = new Timer(17, platformMovement);
        ballFPS = new Timer(17, ballMovement);
    }

    private class PlatformMovement extends KeyAdapter implements ActionListener {
        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                platform.direction = false;
                platformFPS.start();
            }

            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                platform.direction = true;
                platformFPS.start();
            }

            if (e.getKeyCode() == KeyEvent.VK_UP) {
                ballMovement.parameterUpdate();
                ballFPS.start();
            }

        }

        @Override
        public void keyReleased(KeyEvent e) {
            platformFPS.stop();
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int x = platform.getLocation().x;
            int y = platform.getLocation().y;

            if (platform.direction) {
                x += PLATFORM_SPEED;
            } else {
                x -= PLATFORM_SPEED;
            }

            platform.setLocation(x, y);
        }

    }

    private class BallMovement implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            ballHitBrick();

            if (ballX <= LEFT_MARGIN_LIMIT) {
                ball.directionX = true;
            }

            if (ballX >= RIGHT_MARGIN_LIMIT) {
                ball.directionX = false;
            }

            if (ballY <= UP_MARGIN_LIMIT) {
                ball.directionY = true;
            }

            if (ballY >= DOWN_MARGIN_LIMIT) {
                ball.directionY = false;
            }

            if (ball.directionX) {
                ballX += actualSpeed;
            } else {
                ballX -= actualSpeed;
            }

            if (ball.directionY) {
                ballY += actualSpeed;
            } else {
                ballY -= actualSpeed;
            }

            ball.setLocation(ballX, ballY);
        }

        public void ballHitBrick() {
            new Thread(() -> {
                for (int i = 0; i < bricks.length; i++) {
                    if (bricks[i] != null) {
                        if (bricks[i].isVisible()) {
                            if (bricks[i].getBounds().intersects(ball.getBounds())) {
                                bricks[i].setVisible(false);
                            }
                        }
                    }
                }
            }) {}.start();;
        }

        public void parameterUpdate() {
            ballX = ball.getLocation().x;
            ballY = ball.getLocation().y;
            actualSpeed = ball.NORMAL_SPEED;
        }

        int ballX;
        int ballY;
        int actualSpeed;
    }

    File levelFile;
    ArkanoidFrame arkanoidFrame;
    Brick[] bricks;
    Platform platform;
    Ball ball;
    Timer platformFPS;
    Timer ballFPS;
    PlatformMovement platformMovement = new PlatformMovement();
    BallMovement ballMovement = new BallMovement();
    final Point PLATFORM_SPAWN = new Point(300, 440);
    final Point BALL_SPAWN = new Point(340, 420);
    final int PLATFORM_SPEED = 10;
    final int LEFT_MARGIN_LIMIT = 0;
    final int UP_MARGIN_LIMIT = 0;
    final int RIGHT_MARGIN_LIMIT = 680;
    final int DOWN_MARGIN_LIMIT = 460;
}
