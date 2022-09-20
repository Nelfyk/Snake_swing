import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    GamePanel gp;
    KeyHandler(GamePanel gp){
        this.gp=gp;
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println(e.getKeyCode());
        switch(e.getKeyCode()){
            case 37 -> {
                if(!gp.direction.equals("right")) {
                    gp.direction = "left";
                }
            }
            case 38 -> {
                if(!gp.direction.equals("down")) {
                    gp.direction = "up";
                }
            }
            case 39 -> {
                if(!gp.direction.equals("left")) {
                    gp.direction = "right";
                }
            }
            case 40 -> {
                if(!gp.direction.equals("up")) {
                    gp.direction = "down";
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
