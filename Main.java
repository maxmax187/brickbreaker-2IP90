import javax.swing.*;

/**
 * Initialization of the application.
 */
public class Main {
    private static int WINDOW_WIDTH = 400;
    private static int WINDOW_HEIGHT = 600;
    public String gameState = "Main Menu"; //TODO !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

    public static void main(String[] args) {
        JFrame frame = new JFrame("Brick Breaker Pong 2IP90");

        // MainMenu menu = new MainMenu();
        // frame.add(menu);

        GamePlay gamePlay = new GamePlay(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.add(gamePlay);

        UserInput userInput = new UserInput();
        frame.addKeyListener(userInput);
        userInput.initialize(gamePlay);

        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}
