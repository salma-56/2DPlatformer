package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu {

    /**
     * Class creates a menu panel
     */
    private JButton startButton;
    private JPanel menuPanel;
    private JButton optionsButton;
    private JButton quitButton;

    public Menu(Game game) {

        //Code for when you press the start button:
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.startGame();
            }
        });


        //Code for when you press the options button:

        optionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.openOptionsPanel();
            }
        });


        //Code for when you press the quit button:

        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.exitGame();
            }
        });
    }


    public JPanel getMenuPanel() {
        return menuPanel;
    }
}
