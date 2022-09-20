import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel{
    private final int WIDTH = 600;
    private final int HEIGHT = 600;
    private final Timer timer;
    private int x = 0;
    private int y = 0;
    private final int step = 1;
    String direction = "right";

    GamePanel() {
        timer = new Timer(1, e->{
            update();
            repaint();
        });

        this.addKeyListener(new KeyHandler(this));
        this.setFocusable(true);
        this.setSize(WIDTH,HEIGHT);
        this.setBackground(Color.black);
        timer.start();

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawRect(x, y, 30, 30);
        g.dispose();
    }

    public void update(){
        switch (direction){
            case "left" -> x-=step;
            case "up" -> y-=step;
            case "right" -> x+=step;
            case "down" -> y+=step;
        }
        // COLLISION
        if(x+50>WIDTH || x<0 || y+50>HEIGHT || y<0){
            endGame();
        }
    }
    public void endGame(){
        System.exit(1);
    }
}

/*
apple
tail
*/
