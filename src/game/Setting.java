package game;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Class is used to create a settings sub-menu, when "options" button is clicked under the menu
 */
public class Setting {

    public JPanel optionsControlPanel;
    private JPanel optionsMenu;  //This is where all the buttons on top are held
    private JButton gamePlay;
    private JButton settingsButton;
    private JButton other;
    private JPanel panelHolder;
    private JPanel gamePlayPanel;
    private JPanel settingPanel;
    private JPanel otherPanel;
    private JButton audioButton;
    private JButton retartLevelButton;
    private JButton restartGameButton;
    private JButton backOther;
    private JButton backGamePlay;
    private JButton backSetting;
    private JButton audioDown;
    private JTextArea instructions;
    private double audio = 0.5;

    public Setting(Game game) {


//Code for pressing the settings button:
        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.openSettings();

            }
        });

//Code for pressing the GamePlay button:

        gamePlay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.openGamePlay();
            }
        });


        //Code for pressing back buttons:

        backGamePlay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.goBack();
            }
        });
        backSetting.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.goBack();
            }
        });


        backOther.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//game.goBack();
            }
        });
        restartGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.newGame();
            }
        });
        retartLevelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.restartLevel();
            }
        });
        audioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if ((0 < audio) && (audio < 1)) {
                    audio = audio + 0.1;
                    game.setAudio(audio);
                }
            }
        });
        audioDown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if ((0.2 < audio) && (audio < 1)) {
                    audio = audio - 0.1;
                    game.setAudio(audio);
                }
            }
        });
        backOther.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.goBack();
            }
        });
        other.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.openOther();
            }
        });
    }


    public JPanel getPanelHolder() {
        return panelHolder;
    }

    public JPanel getGamePlayPanel() {
        return gamePlayPanel;
    }

    public JPanel getSettingPanel() {
        return settingPanel;
    }

    public JPanel getOtherPanel() {
        return otherPanel;
    }
}
