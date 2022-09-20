import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel{
    private final int WIDTH = 600;
    private final int HEIGHT = 600;
    private final int UNIT_SIZE=30;

    private Timer timer;
    private int x = 0;
    private int y = 0;
    private int[] xSnake = new int[WIDTH];
    private int[] ySnake = new int[HEIGHT];
    private int bodyParts = 2;
    String direction = "right";

    GamePanel() {
        timer = new Timer(100, e->{
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
        //Snake
        for(int i=0;i<bodyParts;i++) {
            g.drawRect(xSnake[i], ySnake[i], UNIT_SIZE, UNIT_SIZE);
        }
        g.dispose();
    }

    public void update(){
        //important part
        for(int i=bodyParts;i>0;i--){
            xSnake[i]=xSnake[i-1];
            ySnake[i]=ySnake[i-1];
        }
        //direction moving
        switch (direction){
            case "left" -> x-=UNIT_SIZE;
            case "up" -> y-=UNIT_SIZE;
            case "right" -> x+=UNIT_SIZE;
            case "down" -> y+=UNIT_SIZE;
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
