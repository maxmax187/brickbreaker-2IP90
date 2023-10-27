import javax.swing.*;

public class Main {
    private static int WINDOW_WIDTH = 400;
    private static int WINDOW_HEIGHT = 600;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Brick Breaker Pong 2IP90");

        MainMenu menu = new MainMenu();
        frame.add(menu);

        // GamePlay gamePlay = new GamePlay(WINDOW_WIDTH, WINDOW_HEIGHT);
        // frame.add(gamePlay);
        // frame.addKeyListener(gamePlay);

        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}
