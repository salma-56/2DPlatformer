package game;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameOver {
    /**
     * Class to generate game over screen. Game can be reset or quit
     */
    private JLabel gameOverSign;
    private JButton startButton;
    private JButton quit;

    public JPanel getGameOverPanel() {
        return gameOverPanel;
    }

    private JPanel gameOverPanel;

    public GameOver(Game game){

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.newGame();
            }
        });
        quit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.exitGame();
            }
        });
    }
}
