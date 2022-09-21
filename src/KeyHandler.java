import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    GamePanel gp;

    KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println(e.getKeyCode());
        switch (e.getKeyCode()) {
            case 37 -> {
                if (!gp.getDirection().equals("right") &&
                        gp.getxSnake()[0] - gp.getUNIT_SIZE() != gp.getxSnake()[1] &&
                        gp.getySnake()[0] != gp.getySnake()[1]) {
                    gp.setDirection("left");
                }
            }
            case 38 -> {
                if (!gp.getDirection().equals("down") &&
                        gp.getxSnake()[0] != gp.getxSnake()[1] &&
                        gp.getySnake()[0] - gp.getUNIT_SIZE() != gp.getySnake()[1]) {
                    gp.setDirection("up");
                }
            }
            case 39 -> {
                if (!gp.getDirection().equals("left") &&
                        gp.getxSnake()[0] + gp.getUNIT_SIZE() != gp.getxSnake()[1] &&
                        gp.getySnake()[0] != gp.getySnake()[1]) {
                    gp.setDirection("right");
                }
            }
            case 40 -> {
                if (!gp.getDirection().equals("up") &&
                        gp.getxSnake()[0] != gp.getxSnake()[1] &&
                        gp.getySnake()[0] + gp.getUNIT_SIZE() != gp.getySnake()[1]) {
                    gp.setDirection("down");
                }
            }
            case 82 -> gp.startGame();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
