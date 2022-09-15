import javax.swing.*;

public class Frame extends JFrame {
    final int WIDTH = 600;
    final int HEIGHT = 600;

    Frame(){
        this.add(new GamePanel());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(WIDTH,HEIGHT);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
