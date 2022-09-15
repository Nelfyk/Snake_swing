import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel{
    Timer timer;
    int x = 0;

    GamePanel() {
        timer = new Timer(1, e->{
            repaint();
            System.out.println(x);
            if(x<600-30)
                x++;
            else {
                timer.stop();
                System.exit(0);
            }
        });
        this.setSize(600,600);
        this.setBackground(Color.black);
        timer.start();

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawRect(x, 20, 30, 30);
        g.dispose();
    }
}
