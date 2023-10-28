import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;


/**
 * 
 */
public class MainMenu extends JPanel {
    private JButton playButton;

    public MainMenu() {
        setLayout(new BorderLayout());

        // Create a panel to center the play button
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        // Create the "Play" button
        playButton = new JButton("Play");
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO
                // Add action to start the game when the "Play" button is clicked
                // You can switch to the game screen here
            }
        });


        // Add the play button to the button panel
        buttonPanel.add(playButton);

        // Add the button panel to the center of the main menu panel
        add(buttonPanel, BorderLayout.CENTER);
    }
}
