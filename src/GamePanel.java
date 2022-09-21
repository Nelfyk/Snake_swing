import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class GamePanel extends JPanel {
    private final int SCREEN_WIDTH = 600;
    private final int SCREEN_HEIGHT = 600;
    private final int UNIT_SIZE = 15;
    private final int DELAY = 100;

    private Timer timer;
    private Random random;

    private int[] xSnake = new int[SCREEN_WIDTH];
    private int[] ySnake = new int[SCREEN_HEIGHT];
    private int bodyParts;
    private int xApple;
    private int yApple;
    private int score;
    private String direction;

    private int xBonusApple;
    private int yBonusApple;
    private int bonusCounter;
    private int bonusLifeCounter;
    private int bonusCost = 3;

    private final int COLOR_MAX_LIMIT = 60;
    private final int COLOR_MIN_LIMIT = 0;
    private int red;
    private int green;
    private int blue;
    private int colorCounter;
    private int tailColor;

    GamePanel() {
        random = new Random();
        timer = new Timer(DELAY, e -> {
            update();
            repaint();
        });
        this.addKeyListener(new KeyHandler(this));
        this.setFocusable(true);
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        startGame();
        this.setBackground(new Color(red, green, blue));

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (timer.isRunning()) {
            // Grid
//            for (int i = UNIT_SIZE; i < SCREEN_WIDTH; i += UNIT_SIZE) {
//                g.drawLine(i, 0, i, SCREEN_HEIGHT);
//                g.drawLine(0, i, SCREEN_WIDTH, i);
//            }
            // Snake
            g.setColor(new Color(0, 255, 0));
            g.fillRect(xSnake[0], ySnake[0], UNIT_SIZE, UNIT_SIZE);
            for (int i = 1; i < bodyParts; i++) {
                if (200-(i*2)>20){
                    tailColor = 200-(i*2);
                }
                g.setColor(new Color(0, tailColor, 0));
                g.fillRect(xSnake[i], ySnake[i], UNIT_SIZE, UNIT_SIZE);
            }

            // Apple
            g.setColor(new Color(255, 0, 0));
            g.fillOval(xApple, yApple, UNIT_SIZE, UNIT_SIZE);

            // BonusApple
            if (xBonusApple >= 0) {
                g.setColor(new Color(255, 255, random.nextInt(255)));
                g.fillOval(xBonusApple, yBonusApple, UNIT_SIZE, UNIT_SIZE);
            }

            // Score
            g.setColor(new Color(235, 85, 52));
            g.setFont(new Font("MV Boli", Font.BOLD, SCREEN_WIDTH / 30)); // 20
            g.drawString("score: " + score, UNIT_SIZE, UNIT_SIZE * 2);

            // Background
            if (colorCounter < 2) {
                colorCounter++;
            } else {
                colorCounter = 0;
                this.setBackground(new Color(red, green, blue));
                if (green == blue && blue == COLOR_MIN_LIMIT && red < COLOR_MAX_LIMIT) {
                    red++;
                } else {
                    if (red == COLOR_MAX_LIMIT && green < COLOR_MAX_LIMIT && blue == COLOR_MIN_LIMIT) {
                        green++;
                    } else if (red != COLOR_MIN_LIMIT && blue == COLOR_MIN_LIMIT) {
                        red--;
                    } else if (green == COLOR_MAX_LIMIT && blue < COLOR_MAX_LIMIT) {
                        blue++;
                    } else if (green != COLOR_MIN_LIMIT && red == COLOR_MIN_LIMIT) {
                        green--;
                    } else if (blue == COLOR_MAX_LIMIT && red < COLOR_MAX_LIMIT) {
                        red++;
                    } else if (blue != COLOR_MIN_LIMIT) {
                        blue--;
                    }
                }
            }


        } else {
            this.setBackground(new Color(0, 0, 0));
            // Text
            g.setColor(new Color(255, 0, 0));
            g.setFont(new Font("MV Boli", Font.BOLD, SCREEN_WIDTH / 12)); // 50
            g.drawString("GAME OVER", SCREEN_WIDTH / 2 - (SCREEN_WIDTH / 12 * 3), SCREEN_HEIGHT / 2 - SCREEN_HEIGHT / 6);
            g.setFont(new Font("MV Boli", Font.BOLD, SCREEN_WIDTH / 24)); // 25
            g.drawString("press \"R\" to restart", SCREEN_WIDTH / 2 - (SCREEN_WIDTH / 24 * 5), SCREEN_HEIGHT / 2 + (SCREEN_WIDTH / 24) * 2);
            // Score
            g.setColor(new Color(255, 0, 0));
            g.setFont(new Font("MV Boli", Font.PLAIN, SCREEN_WIDTH / 20)); // 30
            g.drawString("Final score: " + score, SCREEN_WIDTH / 2 - (SCREEN_WIDTH / 20 * 4), SCREEN_HEIGHT / 2);
        }
//        g.dispose();
    }

    public void update() {
        // Updating the coordinates of snake body parts
        for (int i = bodyParts; i > 0; i--) {
            xSnake[i] = xSnake[i - 1];
            ySnake[i] = ySnake[i - 1];
        }
        // Direction moving
        switch (direction) {
            case "left" -> xSnake[0] -= UNIT_SIZE;
            case "up" -> ySnake[0] -= UNIT_SIZE;
            case "right" -> xSnake[0] += UNIT_SIZE;
            case "down" -> ySnake[0] += UNIT_SIZE;
        }
        // --- COLLISION ---
        // Wall
        if (xSnake[0] > SCREEN_WIDTH - UNIT_SIZE || xSnake[0] < 0
                || ySnake[0] > SCREEN_HEIGHT - UNIT_SIZE
                || ySnake[0] < 0) {
            endGame();
        }
        // Body parts
        for (int i = 1; i < bodyParts; i++) {
            if (xSnake[0] == xSnake[i] && ySnake[0] == ySnake[i]) {
                endGame();
            }
        }
        // Eat Apple
        if (xSnake[0] == xApple && ySnake[0] == yApple) {
            bodyParts++;
            score++;
            spawnApple();
            timer.setDelay(timer.getDelay() - 2);
        }
        // Eat Bonus Apple
        if (xSnake[0] == xBonusApple && ySnake[0] == yBonusApple) {
            bodyParts += bonusCost;
            // fixes drawing bug for [0,0] coordinates
            for (int i = bodyParts - 1; i > bodyParts - bonusCost; i--) {
                xSnake[i] = -200;
            }
            score += bonusCost;
            destroyBonusApple();
            timer.setDelay(timer.getDelay() - 10);
//            timer.setDelay(timer.getDelay() - 2);
        }
        // Bonus Apple
        if (bonusCounter < 100) {
            bonusCounter++;
        } else if (bonusLifeCounter == 0) {
            spawnBonusApple();
            bonusLifeCounter = 100;
        } else {
            bonusLifeCounter--;
            if (bonusLifeCounter == 0) {
                destroyBonusApple();
            }
        }

    }

    public void startGame() {
        red = green = blue = COLOR_MIN_LIMIT;
        colorCounter = 0;
        bonusCounter = 0;
        direction = "right";
        xSnake[0] = UNIT_SIZE * 2;
        ySnake[0] = random.nextInt(SCREEN_WIDTH / UNIT_SIZE) * UNIT_SIZE;
        score = 0;
        bodyParts = 2;
        spawnApple();
        destroyBonusApple();
        timer.setDelay(DELAY);
        timer.start();
    }

    public void spawnApple() {
        boolean spawn = false;
        while (!spawn) {
            xApple = random.nextInt(SCREEN_WIDTH / UNIT_SIZE) * UNIT_SIZE;
            yApple = random.nextInt(SCREEN_HEIGHT / UNIT_SIZE) * UNIT_SIZE;
            if (xApple != xSnake[0] && yApple != ySnake[0])
                spawn = true;
        }
    }

    public void spawnBonusApple() {
        boolean spawn = false;
        while (!spawn) {
            xBonusApple = random.nextInt(SCREEN_WIDTH / UNIT_SIZE) * UNIT_SIZE;
            yBonusApple = random.nextInt(SCREEN_HEIGHT / UNIT_SIZE) * UNIT_SIZE;
            if (xBonusApple != xSnake[0] && yBonusApple != ySnake[0] &&
                    xBonusApple != xApple && yBonusApple != yApple)
                spawn = true;
        }
    }

    public void destroyBonusApple() {
        xBonusApple = -1;
        yBonusApple = -1;
        bonusCounter = 0;
        bonusLifeCounter = 0;
    }

    public void endGame() {
        timer.stop();
        repaint();
    }

    /*---------------------------------
         G E T T E R / S E T T E R
    ---------------------------------*/

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getDirection() {
        return direction;
    }

    public int[] getxSnake() {
        return xSnake;
    }

    public int[] getySnake() {
        return ySnake;
    }

    public int getUNIT_SIZE() {
        return UNIT_SIZE;
    }
}

