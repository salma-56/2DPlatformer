package game;

import city.cs.engine.SoundClip;
import city.cs.engine.World;

import java.awt.*;

public abstract class GameLevel extends World {

    /**
     * player - player is shared in all 3 level
     */
    private static Player player;

    /**
     * villain1- a purple,patroller is placed in all 3 levels
     */
    private final Villain villain1;

    /**
     * villain2 - a second, purple, patroller is placed in all 3 levels
     */
    private final Villain villain2;

    /**
     * Initialises player and 2 villains. player can collide with specific items
     */
    public GameLevel(Game game) {

        // Main character:
        player = new Player(this, game);

        // Collisions for the player
        PlayerCollision playerCollision = new PlayerCollision(player, this, game);

        player.addCollisionListener(playerCollision);


        //Villain: Purple
        villain1 = new Villain(this);
        villain2 = new Villain(this);


    }

    //Method use to
    public abstract boolean isComplete();

    public abstract boolean waterAttack();

    /**
     * Gets the Player
     *
     * @return player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Gets this first enemy
     *
     * @return villain1- Will return the first villain
     */
    public Villain getVillain1() {
        return villain1;
    }

    /**
     * Gets this second enemy
     *
     * @return villain2 - Will return villain2
     */
    public Villain getVillain2() {
        return villain2;
    }


    //Gets all level names - this is put into a save file
    public abstract String getLevelName();

//Gets the background sound for all levels - so the volume can be adjusted/ audio can be stopped
    public abstract SoundClip getSound();

}