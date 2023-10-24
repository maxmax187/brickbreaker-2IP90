import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GamePlay extends JPanel implements ActionListener, KeyListener {
    private int bottomBorder = 500;
    private int rightBorder = 400;

    private int ballX = 100; // Ball X-coordinate
    private int ballY = 450; // Ball Y-coordinate
    private int ballXSpeed = -2; // Ball X-speed
    private int ballYSpeed = -2; // Ball Y-speed
    private int ballSize = 20;

    private int paddleX = 100; // Paddle X-coordinate
    private int paddleY = 480;
    private int paddleWidth = 60; // Paddle width
    private int paddleHeight = 10;

    private int brickX = 50; // Brick X-coordinate
    private int brickY = 50; // Brick Y-coordinate
    private int brickWidth = 60;
    private int brickHeight = 20;
    private int brickGapSize = 10;
    private boolean[][] bricks; // Array to keep track of bricks

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
                    g.fillRect(brickX + i * (brickWidth + brickGapSize), brickY + j * (brickHeight + brickGapSize), brickWidth, brickHeight);
                }
            }
            //TODO brickY increase
            // brickY -= 20;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ballX += ballXSpeed;
        ballY += ballYSpeed;

        // Ball-paddle collision
        if (ballY + ballSize / 2 > paddleY - paddleHeight && ballX >= paddleX && ballX <= paddleX + paddleWidth) {
            ballYSpeed = -ballYSpeed;
        }

        // Ball-brick collisions
        for (int i = 0; i < bricks.length; i++) {
            for (int j = 0; j < bricks[i].length; j++) {
                if (bricks[i][j] && ballY <= brickY + 20 && ballX >= brickX + i * 70 && ballX <= brickX + i * 70 + 60) {
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
