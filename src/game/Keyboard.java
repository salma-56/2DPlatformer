
package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;

/**
 * Allows the player to move, open the menu, save/load, choose and create projectiles using keys
 */
public class Keyboard implements KeyListener, ActionListener {


    private Player player;
    private GameLevel level;

    private static int count;
    private static Boolean leftFlameProduced = false; //Both FlameProduced variables are used so the correct code can execute once the timer has completed
    private static Boolean rightFlameProduced = false;
    private static Flame leftFlame, rightFlame;

    private Water water;
    private static WaterCollision waterCollision;
    private static Boolean waterProduced = false;

    private static Boolean chooseWater = false;
    private static Boolean chooseFire = false;

    //Menu items
    private static Game game;


    public Keyboard(Player player, GameLevel level, Game game) {
        this.player = player;
        this.level = level;
        this.game = game;
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    /**
     * Opens menu, saves and load, chooses projectiles and allows player to move
     * <p></p>
     * Corresponding images also created when this player moves
     * @param e the event to be processed
     */
    @Override
    public void keyPressed(KeyEvent e) {

        //Press S to save the game:
        if (e.getKeyCode() == KeyEvent.VK_S) {
            game.saveFromGame();
        }

        //Press L to load the game:
        if (e.getKeyCode() == KeyEvent.VK_L) {
            game.loadFromGame();
        }


        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            game.openMenu();
        }

// Switch-case to pick a power up:
        //Press 1 to choose flame power up and
        switch (e.getKeyCode()) {
            case (KeyEvent.VK_1) -> {
                chooseFire = true;
                chooseWater = false;
            }
            case (KeyEvent.VK_2) -> {
                chooseFire = false;
                chooseWater = true;
            }

        }

        if (e.getKeyCode() == KeyEvent.VK_0) {
            System.out.println(count);
        }

        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            player.startWalking(5f);

            player.changeImage("right");


        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            player.startWalking(-5f);
            player.changeImage("left");


        } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            player.jump(13f);
        }


//Press Q for an attack:
        else if (e.getKeyCode() == KeyEvent.VK_Q) {

            //Create flames if you chose this item:
            if (chooseFire) {
                if (count == 0) {  //Only produce a flame if the count is 0
                    player.stopWalking();


//If the player is facing right, produce a right flame
                    if (player.getImage("right") == player.getPlayerPosition()) {

                        rightFlameProduced = true;
                        player.changeImage("rightAttack");

                        rightFlame = new Flame(level, player.getPosition().x + 2, player.getPosition().y, "right");


                        RightCollision rightCollision = new RightCollision(rightFlame, level, this, player);
                        rightFlame.addCollisionListener(rightCollision);

//Start a timer to check if the flame has been destroyed
                        Timer destroyFlame = new Timer(1500, this);
                        destroyFlame.start();
                        destroyFlame.setRepeats(false);
                    }

                    //Create a left flame if the player is facing left
                    else if (player.getImage("left") == player.getPlayerPosition()) {


                        leftFlameProduced = true;
                        player.changeImage("leftAttack");

                        leftFlame = new Flame(level, player.getPosition().x - 2, player.getPosition().y);


                        LeftCollision leftCollision = new LeftCollision(leftFlame, level, this, player);
                        leftFlame.addCollisionListener(leftCollision);


                        Timer destroyFlame = new Timer(1600, this);
                        destroyFlame.start();
                        destroyFlame.setRepeats(false);
                    }
                    count++;  //After a flame has been produced, increase the count, so no more is created
                }
            }

            //Create water if you are on a correct level, and you picked this item:
            else if (level.waterAttack() && chooseWater) {

                if (count == 0) {

                    player.stopWalking();
                    waterProduced = true;

                    //Player facing right, then shoot right
                    if (player.getImage("right") == player.getPlayerPosition()) {
                        player.changeImage("rightAttack");
                        water = new Water(level, player.getPosition().x + 2, player.getPosition().y);
                    }

                    //Player facing left, so shoot left
                    else {
                        player.changeImage("leftAttack");
                        water = new Water(level, player.getPosition().x - 2, player.getPosition().y, "left");
                    }


                    //If water collides with any items:
                    waterCollision = new WaterCollision(water, this, player);
                    water.addCollisionListener(waterCollision);


                    //After shooting, increase the count
                    count++;

                    //Start a timer
                    Timer destroyWater = new Timer(1600, this);
                    destroyWater.start();
                    destroyWater.setRepeats(false);
                }

            }
        }

    }

    /**
     * Chooses correct image and stops this player from walking
     * <p></p>
     * Image chosen depends on the player's previous actions
     * @param e the event to be processed
     */
    @Override
    public void keyReleased(KeyEvent e) {


        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            player.stopWalking();
            player.changeImage("idleRight");

        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            player.stopWalking();
            player.changeImage("idleLeft");

        } else if (e.getKeyCode() == KeyEvent.VK_Q) {

            player.stopWalking();
            //If player was facing right, place the right idle image
            if (player.getImage("right") == player.getPlayerPosition()) {
                player.changeImage("idleRight");

//If player was facing left, place the left idle image
            } else {
                player.changeImage("idleLeft");
            }


        }


    }

    /**
     * Resets the count variable
     * <p></p>
     * Used so player can create more projectiles
     */
    public void resetCount() {
        count = 0;
    } //Reset the count so a new projectile can be made

    /**
     * Self-destructs a projectile after a certain time
     * <p></p>
     * Used so if projectile does not hit anything, the projectile will be destroyed and another can be created
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        //Reset the count after destroying a projectile, so a new one can be produced



        //Follow this path if a left flame was produced
        if (leftFlameProduced) {

            //If the left flame did not collide with anything, destroy it
            if (!LeftCollision.getCollisionOccurred()) {
                leftFlame.destroy();
                resetCount();
                leftFlameProduced = false;
            }
        }

        //Follow this path if a right flame was produced
        else if (rightFlameProduced) {

            //If the right flame did not collide with anything, destroy it
            if (!RightCollision.getCollisionOccurred()) {
                rightFlame.destroy();
                resetCount();
                rightFlameProduced = false;
            }
        }
        else if (waterProduced) {
            if (!WaterCollision.getCollisionOccurred()) {
                water.destroy();
                resetCount();
                waterProduced = false;  //Must reset the static variable so more projectiles can be made
            }
        }

    }

    public void updatePlayer(Player player) {
        this.player = player;
    }


    /**
     * Updates the level field to the level thi player is on
     * @param level - will use this to set the field to the latest level
     */
    public void setLevel(GameLevel level) {
        this.level = level;
    }


    /**
     * Resets which projectile was produced
     * <p></p>
     * Used so if player moves to next level or resets level, it ensures a new projectile can be created
     */
    public void resetItems() {
        leftFlameProduced = false;
        rightFlameProduced = false;
        waterProduced = false;
    }
}











