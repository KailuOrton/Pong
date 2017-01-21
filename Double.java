/**
 * Created by Kailu on 1/16/17.
 */
import javax.swing.*;
import java.awt.*;
import java .awt.event.*;

/**
 * Created by Kailu on 12/12/16.
 */

final public class Double extends Thread implements ActionListener, Runnable {
    DrawPanel drawPanel;
    JFrame old;
    // Thread thread1 = new Thread(new Tester());
    //JFrame newer;

    public JButton sb; // button for starting the game
    public JButton scores; //button that registers the high scores in the game

    private int oneX = 7; // X coordinate of the ball that constantly updates
    private int oneY = 7; // Y coordinate of the ball that constantly updates
    private int direction1 = 125; // starting coordinates for the rectangle1
    private int direction2 = 125; //starting coordinates for the recatngle2
    private int points = 0; //keeps track of the number of times the player successfully bounces the ball off the base
    private double incrementer = 1; // controls the speed of the ball and increases it periodically after a certain number of points.

    boolean up = false;
    boolean down = true;
    boolean left = false;
    boolean right = true;

    public static void main(String[] args) {
        Tester tester = new Tester();
        //tester.initScreen();
        new Double().go();
    }

    public void initScreen() {
        old = new JFrame("Bounce");
        old.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        sb = new JButton("Start New Game");
        scores = new JButton("High Scores");

        JPanel panel = new JPanel(new FlowLayout());
        panel.add(sb);
        panel.add(scores);

        sb.addActionListener(this);
        scores.addActionListener(this);

        sb.setSize(10,20);
        scores.setSize(10,20);

        old.add(panel);
        old.setVisible(true);
        old.setResizable(false);
        old.setSize(300, 300);
        old.setLocation(375, 55);
    }
    private void go() {
        old = new JFrame("BOUNCE");
        old.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        drawPanel = new DrawPanel();

        old.getContentPane().add(BorderLayout.CENTER, drawPanel);
        old.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                arrow(e);
            }
        });
        old.setVisible(true);
        old.setResizable(false);
        old.setSize(300, 300);
        old.setLocation(375, 55);
        moveIt();
    }

    class DrawPanel extends JPanel {
        public void paintComponent(Graphics g) {
            g.setColor(Color.BLUE);
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
            g.setColor(Color.RED);
            g.fillRect(3, 3, this.getWidth()-6, this.getHeight()-6);
            g.setColor(Color.WHITE);
            g.fillRect(6, 6, this.getWidth()-12, this.getHeight()-12);
            g.setColor(Color.BLACK);
            g.fillOval(oneX, oneY, 10, 10);
            g.setColor(Color.BLACK);
            g.fillRect(direction1,265,50,7);
            g.fillRect(direction2,7,50,7);
        }
    }
    private void arrow(KeyEvent event){
        if (event.getKeyCode() == KeyEvent.VK_LEFT) {
            direction1 -= 7;
        }
        if(event.getKeyCode() == KeyEvent.VK_RIGHT){
            direction1 += 7;
        }
        if(event.getKeyCode() == KeyEvent.VK_A){
            direction2 -= 7;
        }
        if(event.getKeyCode() == KeyEvent.VK_D){
            direction2 += 7;
        }
    }

    private void moveIt() {
        while(true){
            if(points == 5){
                incrementer = 1.2;
            }
            if(oneX >= 283){
                right = false;
                left = true;
            }
            if(oneX <= 7){
                right = true;
                left = false;
            }
            if(oneY == 255 && oneX >= direction1 && oneX <= (direction1 + 50)){
                up = true;
                down = false;
                points++;
            }
            if(oneY > 276 || oneY < 7){
                JOptionPane.showMessageDialog(null,"Your Final Score was: " + points);
                System.exit(0);
            }
            if(oneY == 7 && oneX >= direction2 && oneX <= (direction2 + 50)){
                up = false;
                down = true;
            }
            if(up){
                oneY -= incrementer;
            }
            if(down){
                oneY += incrementer;
            }
            if(left){
                oneX -= incrementer;
            }
            if(right){
                oneX += incrementer;
            }
            if(points == 10){
                incrementer = 1.4;
            }
            if(points == 15){
                incrementer = 2;
            }
            try{
                Thread.sleep(10);
            } catch (Exception exc){}
            old.repaint();
        }
    }
    public void actionPerformed(ActionEvent e) {
        JButton result = (JButton) e.getSource();
        if(result == sb){
            old.dispose();
            // thread1.start();
        }
    }
    public void run() {
        go();
    }

}
