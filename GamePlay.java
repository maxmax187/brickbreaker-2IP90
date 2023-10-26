import java.awt.*;
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
// fix collisions now that there are multiple rows of bricks
// split creation of the map from the gameplay??

// add comments everywhere/ follow coding standard

public class GamePlay extends JPanel implements ActionListener, KeyListener {
    public boolean playing = false;

    private int bottomBorder = 500;
    private int rightBorder = 400;

    private int ballX = 1; // Ball X-coordinate 100
    private int ballY = 1; // Ball Y-coordinate 450
    private int ballXSpeed = -2; // Ball X-speed
    private int ballYSpeed = -2; // Ball Y-speed
    private int ballSize = 10; // diameter of the ball

    private int paddleX = 100; // Paddle X-coordinate
    private int paddleY = 480;
    private int paddleWidth = 60; // Paddle width
    private int paddleHeight = 10;

    private int brickX = 50; // Brick X-coordinate
    private int brickY = 50; // Brick Y-coordinate
    private int brickWidth = 60;
    private int brickHeight = 20;
    private int brickGapSize = 10;  // gap in between the bricks both vertically and horizontally
    private boolean[][] bricks; // boolean matrix to keep track of broken vs unbroken bricks

    private int brickRowAmt = 2;
    private int brickColAmt = 4;

    public GamePlay() {
        bricks = new boolean[brickColAmt][brickRowAmt]; // Initialize the brick array
        for (int i = 0; i < bricks.length; i++) {
            for (int j = 0; j < bricks[i].length; j++) {
                bricks[i][j] = true; // All bricks are initially present
            }
        }

        Timer timer = new Timer(5, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw the ball
        g.setColor(Color.RED);
        g.fillOval(ballX, ballY, ballSize, ballSize);

        // Draw the paddle
        g.setColor(Color.BLUE);
        g.fillRect(paddleX, paddleY, paddleWidth, paddleHeight);

        // Draw the bricks
        g.setColor(Color.GREEN);
        for (int i = 0; i < bricks.length; i++) {
            for (int j = 0; j < bricks[i].length; j++) {
                if (bricks[i][j]) {
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
        }


        // Ball-paddle collision
        // TODO re-examine, compare to brick collision check
        if (ballY + ballSize >= paddleY - paddleHeight && ballX >= paddleX && ballX <= paddleX + paddleWidth) {
            ballYSpeed = -ballYSpeed;
        }

        // Ball-brick collisions
        // TODO re-make using rectangle.intersects()
        for (int i = 0; i < bricks.length; i++) {
            for (int j = 0; j < bricks[i].length; j++) {
                int brickXMin = brickX + (brickWidth + brickGapSize) * i;
                int brickXMax = brickX + (brickWidth + brickGapSize) * (i + 1);
                boolean touchingBrickX = (ballX + ballSize >= brickXMin) && (ballX <= brickXMax);

                int brickYMin = brickY + (brickHeight + brickGapSize) * j;
                int brickYMax = brickY + (brickHeight + brickGapSize) * (j + 1);
                boolean touchingBrickY = (ballY + ballSize >= brickYMin) && (ballY <= brickYMax);

                // TODO what if ball hits the side of a brick
                if (bricks[i][j] && touchingBrickY && touchingBrickX) {
                    bricks[i][j] = false;
                    ballYSpeed = -ballYSpeed;
                }
            }

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
            // Game over
            // TODO add game over menu
            System.exit(0);
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
        if (key == KeyEvent.VK_RIGHT && paddleX < rightBorder - paddleWidth) { // TODO properly align with window border
            paddleX += 20;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }


}
