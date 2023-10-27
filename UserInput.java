import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Handle user input during different stages of the game state.
 */
public class UserInput implements KeyListener {
    private GamePlay gamePlay;

    public void initialize(GamePlay instance) {
        gamePlay = instance;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_ENTER) {
            gamePlay.startGame();
        }

        if (key == KeyEvent.VK_LEFT) {
            gamePlay.moveLeft();
        }

        if (key == KeyEvent.VK_RIGHT) { 
            gamePlay.moveRight();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

}
