import javax.swing.*;

/**
 * Initialization of the application.
 */
public class Main {
    private static int WINDOW_WIDTH = 420;
    private static int WINDOW_HEIGHT = 600;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Brick Breaker Pong 2IP90");

        GamePlay gamePlay = new GamePlay(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.add(gamePlay);

        UserInput userInput = new UserInput(gamePlay);
        frame.addKeyListener(userInput);

        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
