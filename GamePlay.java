import java.awt.*;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;



//!!!!!!!!!!!!!!
// to remember: objects origin coordinates are top-left

// TODO LIST
// create win condition
// create game over screen
// create win screen
// create main menu
// properly align the collisions on the right hand border

// add comments everywhere/ follow coding standard

/**
 *  Main gameplay loop, including starting a game, keeping track of ball, paddle & bricks and 
 *  game completion.
 */
public class GamePlay extends JPanel implements ActionListener, KeyListener {
    private boolean playing = false;
    private MapGen brickMap;

    // borders, later defined to be the same as window borders defined in Main.java
    private int bottomBorder;   
    private int rightBorder;

    private int ballX = 1; // Ball X-coordinate 100
    private int ballY = 1; // Ball Y-coordinate 450
    private int ballXSpeed = -1; // Ball X-speed
    private int ballYSpeed = -1; // Ball Y-speed
    private int ballSize = 30; // diameter of the ball

    private int paddleX = 100; // Paddle X-coordinate
    private int paddleY = 480;
    private int paddleWidth = 60; // Paddle width
    private int paddleHeight = 10;

    private int brickX = 50; // Brick X-coordinate
    private int brickY = 50; // Brick Y-coordinate
    private int brickWidth = 60;
    private int brickHeight = 20;
    private int brickGapSize = 10;  // gap in between the bricks both vertically and horizontally
    // private boolean[][] brickMap; // boolean matrix to keep track of broken vs unbroken bricks

    private int brickRowAmt = 2;
    private int brickColAmt = 4;
    private int totalBricks;

    /**
     *  Start of the main event loop of the game.
     * @param width width of the window specified in Main.java.
     * @param height height of the window specified in Main.java.
     */
    public GamePlay(int width, int height) {
        bottomBorder = (int) Math.floor(height * 0.95);
        rightBorder = width;

        brickMap = new MapGen(brickRowAmt, brickColAmt); // Initialize the brick matrix
        totalBricks = brickRowAmt * brickColAmt;

        Timer timer = new Timer(5, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        if (!playing) {
            return;
        }
        super.paintComponent(g);

        // Draw the ball
        g.setColor(Color.RED);
        g.fillOval(ballX, ballY, ballSize, ballSize);

        // Draw the paddle
        g.setColor(Color.BLUE);
        g.fillRect(paddleX, paddleY, paddleWidth, paddleHeight);

        // Draw the bricks
        g.setColor(Color.GREEN);
        for (int i = 0; i < brickMap.brickMap.length; i++) {
            for (int j = 0; j < brickMap.brickMap[i].length; j++) {
                if (brickMap.brickMap[i][j]) {
                    int x = brickX + i * (brickWidth + brickGapSize);
                    int y = brickY + j * (brickHeight + brickGapSize);
                    g.fillRect(x, y, brickWidth, brickHeight);
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (playing) {
            ballX += ballXSpeed;
            ballY += ballYSpeed;    
        } else { 
            return;
        }


        // Ball-paddle collision
        Rectangle ballObj = new Rectangle(ballX, ballY, ballSize, ballSize);
        Rectangle paddleObj = new Rectangle(paddleX, paddleY, paddleWidth, paddleHeight);
        if (ballObj.intersects(paddleObj)) {
            ballYSpeed = -ballYSpeed;
        }

        // Ball-brick collisions
        for (int i = 0; i < brickMap.brickMap.length; i++) {
            for (int j = 0; j < brickMap.brickMap[i].length; j++) {    
                int brickObjX = brickX + (brickWidth + brickGapSize) * i;
                int brickObjY = brickY + (brickHeight + brickGapSize) * j;

                Rectangle brickObj = new Rectangle(brickObjX, brickObjY, brickWidth, brickHeight);

                if (brickMap.brickMap[i][j] && ballObj.intersects(brickObj)) {
                    brickMap.setBricksValue(false, i, j);
                    totalBricks--;
                    ballYSpeed = -ballYSpeed;  
                }
            }
        }

        // Win condition
        if (totalBricks <= 0) {
            playing = false;
            // TODO: add win screen
        }
        
        // Border collisions
        if (ballX <= 0 || ballX + ballSize >= rightBorder) {
            ballXSpeed = -ballXSpeed;
        }

        // top of screen
        if (ballY <= 0) {
            ballYSpeed = -ballYSpeed;
        }

        if (ballY >= bottomBorder) {
            playing = false;
            System.out.println(ballX);
            removeAll();
            // Game over
            // TODO add game over menu
            // System.exit(0); // exits the application
        }

        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_ENTER) {
            playing = !playing;
        }

        if (key == KeyEvent.VK_LEFT && paddleX > 0) {
            paddleX -= 20;
        }
        if (key == KeyEvent.VK_RIGHT && paddleX < rightBorder - paddleWidth) { 
            paddleX += 20; // TODO properly align with window border
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }


}
