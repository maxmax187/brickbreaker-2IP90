import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;



//!!!!!!!!!!!!!!
// to remember: objects origin coordinates are top-left

// TODO LIST
// ability to restart game after win/loss
// create main menu
// System.exit(0); // exits the application

// add comments everywhere/ follow coding standard

/**
 *  Main gameplay loop, including starting a game, keeping track of ball, paddle & bricks and 
 *  game completion.
 */
public class GamePlay extends JPanel implements ActionListener {
    private boolean playing = false;
    private boolean finished = false;
    private boolean oneTickDelay = false;
    private MapGen brickMap;

    // borders, later defined to be the same as window borders defined in Main.java
    private int bottomBorder;   
    private int rightBorder;

    private int ballX; // Ball X-coordinate 100
    private int ballY; // Ball Y-coordinate 450
    private int ballXSpeed = 0; // Ball X-speed
    private int ballYSpeed = -4; // Ball Y-speed
    private int ballSize = 16; // diameter of the ball          15!!!!
    private int ballRadius = (int) Math.floor(ballSize / 2); 

    private int paddleX; // Paddle X-coordinate
    private int paddleY;
    private int paddleWidth = 60; // Paddle width
    private int paddleHeight = 8;

    private int brickX; // Brick matrix generation left most starting X-coordinate
    private int brickY; // Brick matrix generation upper most starting Y-coordinate
    private int brickWidth = 60;
    private int brickHeight = 20;
    private int brickGapSize = 10;  // gap in between the bricks both vertically and horizontally

    private int brickRowAmt = 4;
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

        // make sure the bricks are centered
        int totalBrickWidth = brickColAmt * brickWidth + (brickColAmt - 1) * brickGapSize;
        brickX = (int) Math.floor((width - 10 - totalBrickWidth) / 2);
        brickY = brickX;
        
        paddleX = (int) Math.floor(width / 2 - (paddleWidth / 2));
        paddleY = (int) Math.floor(height * 0.90 - paddleHeight);

        ballX = (int) Math.round(paddleX + (paddleWidth / 2) - ballRadius);
        ballY = (int) Math.round(paddleY - (1.2 * ballSize)); 

        // Initialize event loop.
        Timer timer = new Timer(5, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (finished) {
            // Clear the panel
            g.setColor(Color.BLACK); // Set the color to clear the screen
            g.fillRect(0, 0, getWidth(), getHeight());

            // Display text for game over or win
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 30));

            String text = totalBricks <= 0 ? "You won!" : "Game Over!"; // check win condition
            int textWidth = g.getFontMetrics().stringWidth(text);
            g.drawString(text, (getWidth() - textWidth) / 2, getHeight() / 2);

            // String enterString = "press ENTER to try again!";
            // textWidth = g.getFontMetrics().stringWidth(enterString);
            // int textHeight = (int) Math.floor(getHeight() * 0.6);
            // g.drawString(enterString, (getWidth() - textWidth) / 2, textHeight);
        }

        if (!playing) {
            return;
        }

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
        // to prevent ball not correctly reacting to a collision with 2 blocks at once
        if (oneTickDelay) {
            oneTickDelay = false;  
            return;
        }

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
            double paddleCenter = paddleWidth / 2 + paddleX;
            int ballCenter = ballX + ballRadius;

            double distance = ballCenter - paddleCenter;
            ballXSpeed = (int) Math.round(distance / (paddleWidth / Math.abs(ballYSpeed)));
            oneTickDelay = true;
        }

        // Ball-brick collisions
        outerloop:
        for (int i = 0; i < brickMap.brickMap.length; i++) {
            for (int j = 0; j < brickMap.brickMap[i].length; j++) {  
                int brickObjX = brickX + (brickWidth + brickGapSize) * i;
                int brickObjY = brickY + (brickHeight + brickGapSize) * j;

                Rectangle brickObj = new Rectangle(brickObjX, brickObjY, brickWidth, brickHeight);

                if (brickMap.brickMap[i][j] && ballObj.intersects(brickObj)) {
                    // Get the center of the ball for collision direction analysis
                    int ballCenterX = ballX + ballRadius;
                    int ballCenterY = ballY + ballRadius;
                    
                    // Calculate the distance between ball and brick edges
                    int dx = Math.min(Math.abs(ballCenterX - brickObjX), 
                        Math.abs(ballCenterX - (brickObjX + brickObj.width)));
                    int dy = Math.min(Math.abs(ballCenterY - brickObjY), 
                            Math.abs(ballCenterY - (brickObjY + brickObj.height)));

                    // Check collision direction based on the distances
                    if (dx == dy) { // if the ball collides with the corner of a block
                        ballXSpeed = -ballXSpeed;
                        ballYSpeed = -ballYSpeed;
                    } else if (dx < dy) {   // horizontal collision
                        ballXSpeed = -ballXSpeed;
                    } else {    // vertical collision
                        ballYSpeed = -ballYSpeed;
                    }
                    brickMap.setBricksValue(false, i, j);
                    totalBricks--;
                    oneTickDelay = true;
                    break outerloop; // part of preventing multiple block collision in 1 tick
                }
            }
        }

        // Win condition
        if (totalBricks <= 0) {
            playing = false;
            finished = true;
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
            finished = true;
        }

        repaint();
    }

    /**
     *  move paddle to the left if it is not already touching the window border.
     */
    public void moveLeft() {
        if (paddleX > 0) {
            paddleX -= (int) Math.floor(paddleWidth / 3);
        }
    }

    /**
     *  move paddle to the right if it is not already touching the window border.
     */
    public void moveRight() {
        if (paddleX < rightBorder - paddleWidth) {
            paddleX += (int) Math.floor(paddleWidth / 3); 
        }
    }

    // TODO fix game over replay & stuff
    public void startGame() {
        System.out.println("enter");
        if (playing) {
            return;
        }
        playing = true;
        finished = false;
    }
}
