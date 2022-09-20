import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class GamePanel extends JPanel{
    private final int SCREEN_WIDTH = 600;
    private final int SCREEN_HEIGHT = 600;
    private final int UNIT_SIZE=30;
    private final int DELAY = 100;

    private Timer timer;
    private Random random;
    private int[] xSnake = new int[SCREEN_WIDTH];
    private int[] ySnake = new int[SCREEN_HEIGHT];
    private int bodyParts = 2;
    private int xApple;
    private int yApple;

    String direction = "right";

    GamePanel() {
        random = new Random();
        timer = new Timer(DELAY, e->{
            update();
            repaint();
        });

        this.addKeyListener(new KeyHandler(this));
        this.setFocusable(true);
//        this.setSize(SCREEN_WIDTH,SCREEN_HEIGHT);
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setBackground(Color.black);
        spawnApple();
        timer.start();

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
//        // grid
//            for (int i = UNIT_SIZE; i < SCREEN_WIDTH; i += UNIT_SIZE) {
//                g.drawLine(i, 0, i, SCREEN_HEIGHT);
//                g.drawLine(0, i, SCREEN_WIDTH, i);
//            }
        //Snake
        g.setColor(Color.GREEN);
        for(int i=0;i<bodyParts;i++) {
            g.fillRect(xSnake[i], ySnake[i], UNIT_SIZE, UNIT_SIZE);
        }
        //Apple
        g.setColor(Color.RED);
        g.fillOval(xApple,yApple,UNIT_SIZE,UNIT_SIZE);
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
            case "left" -> xSnake[0]-=UNIT_SIZE;
            case "up" -> ySnake[0]-=UNIT_SIZE;
            case "right" -> xSnake[0]+=UNIT_SIZE;
            case "down" -> ySnake[0]+=UNIT_SIZE;
        }
        // COLLISION
        if(xSnake[0]>SCREEN_WIDTH-UNIT_SIZE || xSnake[0]<0 || ySnake[0]>SCREEN_HEIGHT-UNIT_SIZE || ySnake[0]<0){
            endGame();
        }
        if(xSnake[0]==xApple && ySnake[0]==yApple){
            bodyParts++;
            spawnApple();
            timer.setDelay(timer.getDelay()-5);
        }
    }
    public void startGame(){
        xSnake[0]=30;
        ySnake[0]=30;
        spawnApple();

    }
    public void spawnApple(){
        xApple=random.nextInt(SCREEN_WIDTH/UNIT_SIZE)*UNIT_SIZE;
        yApple=random.nextInt(SCREEN_HEIGHT/UNIT_SIZE)*UNIT_SIZE;
    }
    public void endGame(){
        System.exit(1);
    }
}

/*
apple
tail
*/
