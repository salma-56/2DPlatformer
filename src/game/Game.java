package game;

import city.cs.engine.*;


import javax.swing.*;

import java.awt.*;
import java.io.IOException;


/**
 * Your main game entry point
 *
 * @author Salma, Jama, salma.jama.2@city.ac.uk
 */
public class Game {

    /**
     * The current level the player is on
     */
    private GameLevel level;

    /**
     * The view of the level
     */
    private final GameView view;

    /**
     * Allows player to move, shoot projectiles, save, load, and open a menu, using a keyboard
     */
    private final Keyboard keyMovement;

    //Menu Items:
    private final JFrame frame;
    private final Menu menu;
    private final Setting optionsControl;
    private final GameOver gameOver;

    private double audio;

    /**
     * Used so the view can track the player's movements
     */
    private Camera camera;

    private GameSaverLoader state;


    /**
     * Initialise a new Game.
     */
    public Game() {


        //1. make an empty game world

        level = new Level1(this);


        //3. make a view to look into the game world

        view = new GameView(level, 700, 500, level.getPlayer());


//Movement using keys
        keyMovement = new Keyboard(level.getPlayer(), level, this);
        view.addKeyListener(keyMovement);


//Imported GiveFocus class -> Followed instructions from L4
        GiveFocus focus = new GiveFocus();
        view.addMouseListener(focus);

//Moving Camera with player

        camera = new Camera(view, level.getPlayer());
        level.addStepListener(camera);

        //optional: draw a 1-metre grid over the view
//        view.setGridResolution(1);


        //4. create a Java window (frame) and add the game view to it
//        final JFrame frame = new JFrame("Game");
        frame = new JFrame("Game");
        frame.add(view);

        //Adding menu
        menu = new Menu(this);
        optionsControl = new Setting(this);
        gameOver = new GameOver(this);

        // enable the frame to quit the application
        // when the x button is pressed
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationByPlatform(true);
        // don't let the frame be resized
        frame.setResizable(false);
        // size the frame to fit the world view
        frame.pack();
        // finally, make the frame visible
        frame.setVisible(true);


        // start our game world simulation!
        level.start();
        view.requestFocus();


//        GameSaverLoader state = new GameSaverLoader(level.getPlayer());


    }


    /**
     * Used to save the state of the player
     * <p></p>
     * Used when player presses 's' on keyboard. This calls an additional method under GameLoader which writes to a file
     *
     * @see GameSaverLoader
     */

    //Method used to save the game -> Press S
    public void saveFromGame() {
         state = new GameSaverLoader(level.getPlayer());

        try {

            GameSaverLoader.save(level.getLevelName());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Loads the most recent save file
     * <p></p>
     * Used when player presses 'l' key. Calls the method to load the game, under GameSaverLoader. This returns a new level, with the player's attributes under the most recent save file,
     * then the view,keyboard and camera are all updated to the latest level
     */

    //method to load the game: -> Press L
    public void loadFromGame() {
        level.stop();

        this.level = GameSaverLoader.load(level.getLevelName(), this);

        //Update the view/keyboard/camera for the newly created level
        view.setWorld(level);

        camera = new Camera(view, level.getPlayer());
        level.addStepListener(camera);

        keyMovement.updatePlayer(level.getPlayer());

        keyMovement.setLevel(level);


        view.requestFocus();

        level.start();


    }

    /**
     * Method to transition to next level
     * <p></p>
     * Upon completing the level's requirements, this method is called so next level is created
     * The camera/view/keyboard are updated to the latest level created
     */
    public void goToNextLevel() {

        //Level 1 to level 2
        if (level instanceof Level1) {

            level.stop();
            Player.resetPlayerItems();


            level.getSound().stop();
            level = new Level2(this);


            view.setWorld(level);
            camera = new Camera(view, level.getPlayer());
            level.addStepListener(camera);

            keyMovement.updatePlayer(level.getPlayer());

            keyMovement.setLevel(level);

saveFromGame();
            level.start();
        } else if (level instanceof Level2) {

            level.stop();
            level.getSound().stop();

            Player.resetPlayerItems();


            level = new Level3(this);
            keyMovement.resetCount();
            keyMovement.resetItems();


            view.setWorld(level);
            camera = new Camera(view, level.getPlayer());
            level.addStepListener(camera);

            keyMovement.updatePlayer(level.getPlayer());

            keyMovement.setLevel(level);

            saveFromGame();

            level.start();
        }

        //When level3 is complete, a game over screen appears instead
        else if (level instanceof Level3) {

            level.getSound().stop();

            level.stop();
            frame.remove(view);
            frame.add(gameOver.getGameOverPanel(), BorderLayout.CENTER);
            frame.repaint();
            frame.pack();
        }
    }


    //Menu features

    /**
     * Increases and decreases the background audio of the level
     * <p></p>
     * Used when the volume buttons under settings are pressed
     *
     * @param audioLevel - Used to set and change the audio value
     */
    public void setAudio(double audioLevel) {
        this.audio = audioLevel;
        level.getSound().setVolume(audioLevel);
    }


    //Resets entire game

    /**
     * Resets the game
     * <p></p>
     * Used when the 'reset' game button is pressed
     */
    public void newGame() {
        level.stop();
        Player.resetPlayerItems();
        frame.dispose();
        new Game();
    }


    /**
     * Will resume the current level
     * <p></p>
     * Used when 'start' button is pressed. The menu is removed and the view of the level is added instead
     */

    //Press start button to close the menu
    public void startGame() {
//        frame.remove(menu.menuPanel);
        frame.remove(menu.getMenuPanel()); //Remove the menu

        frame.add(view);
        frame.pack(); //Entire level can be viewed

        level.start(); //Resume the state of the level again
    }


    /**
     * Used to open the menu
     * <p></p>
     * The view of the world is removed, and the menu panel is inserted instead
     */
    public void openMenu() {

        level.stop();  //Pause the state of the level
        frame.remove(view);
        frame.setLayout(new BorderLayout());
//        frame.add(menu.menuPanel, BorderLayout.CENTER);
        frame.add(menu.getMenuPanel(), BorderLayout.CENTER);  //Place your panel for menu, on top of the frame

        frame.pack();


    }

    /**
     * Exits the game when 'exit' button pressed
     */
    //Exits the application
    public void exitGame() {
        level.getSound().stop(); //Stop the level's sound from playing
        level.stop();
        frame.dispose();
    }

    /**
     * Method called when player restarts the level
     * <p></p>
     *
     * @param level - Used to set the view and keyMovement to current level
     */
    //Method to update view/keyboard/camera as player restarts level:
    public void updateLevel(GameLevel level) {
        keyMovement.resetCount();
        keyMovement.resetItems();

        view.setWorld(level);
        camera = new Camera(view, level.getPlayer());
        level.addStepListener(camera);

        keyMovement.updatePlayer(level.getPlayer());
        keyMovement.setLevel(level);

        frame.setLayout(new BorderLayout());
        frame.add(view);
    }

    /**
     * Restarts the current level
     * <p></p>
     * Used when player presses 'reset level' button, a new level is created, the settings panel is removed and the view of the latest level is inserted
     */
    public void restartLevel() {

        //Stop the level and remove the panels
        level.stop();
        frame.getContentPane().removeAll();
        frame.revalidate();
        frame.repaint();


        //Create a new level object- object will be the same class as the previous level
        if (level instanceof Level1) {
            this.level = new Level1(this);  //Create new level1 object, to 'reset' the game
            this.updateLevel(level);


        } else if (level instanceof Level2) {
            level = new Level2(this); //Create new level2 object, to 'reset' the game
            this.updateLevel(level);

        } else if (level instanceof Level3) {
            level = new Level3(this);
            this.updateLevel(level);
        }

        //Once you have your new level, make sure the frame shows it and start the level
        frame.add(view);
        frame.repaint();

        frame.pack();
        Player.resetPlayerItems();

        level.start();
        view.requestFocus();
    }

    /**
     * Opens up a sub-menu which shows all options
     * <p></p>
     */

    //Creating sub-menu for options:
    public void openOptionsPanel() {
//        frame.remove(menu.getMenuPanel()menuPanel);
        frame.remove(menu.getMenuPanel()); //Remove the menu panel

        frame.repaint();
        frame.add(optionsControl.optionsControlPanel);
        frame.pack();
    }


    /**
     * Opens up the settings panel to view additional options
     * <p></p>
     * Due to the card layout of the options panel, gamePlay and other panels are also removed when this method is called.
     * Player must press the 'options' and then the 'settings' button to call this method
     */
    public void openSettings() {

        //Removing a panel from the parent container, panelHolder


        optionsControl.getPanelHolder().remove(optionsControl.getGamePlayPanel());

        optionsControl.getPanelHolder().remove(optionsControl.getOtherPanel());

        optionsControl.getPanelHolder().add(optionsControl.getSettingPanel());


        frame.repaint();
        frame.pack();
    }

    /**
     * Opens up the gamePlay panel
     * <p></p>
     * This method is called when 'gamePlay' button is pressed. Once called, the other panels are also removed.
     */
    public void openGamePlay() {
        optionsControl.getPanelHolder().removeAll();  //Remove both panels underneath


        optionsControl.getPanelHolder().add(optionsControl.getGamePlayPanel());

        frame.repaint();
        frame.pack();
    }


    /**
     * Opens the 'other' panel, under the sub-mennu
     * <p></p>
     * Used when the 'other' button is pressed under the 'options' sub-menu
     */
//Opens up other panel, under the sub-menu
    public void openOther() {
        optionsControl.getPanelHolder().remove(optionsControl.getGamePlayPanel());

        optionsControl.getPanelHolder().remove(optionsControl.getSettingPanel());


        optionsControl.getPanelHolder().add(optionsControl.getOtherPanel());


        frame.repaint();
        frame.pack();
    }


    /**
     * Player can go back to the menu from the options, sub-menu.
     * <p></p>
     * All panels under the 'options' panel are removed, and the menu panel is reinserted.
     */
    //Create a back button:
    public void goBack() {
        frame.getContentPane().removeAll();  //Use to remove all components
        frame.repaint();
        frame.add(menu.getMenuPanel(), BorderLayout.CENTER);  //Place your panel for menu, on top of the frame

        frame.pack();

    }


    /**
     * Run the game.
     */
    public static void main(String[] args) {

        new Game();

    }
}
