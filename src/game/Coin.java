package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

/**
 * @author Salma, Jama, salma.jama.2@city.ac.uk
 * This generates the coins appearances, sounds and also places them into levels
 */

public class Coin extends StaticBody {

    /**
     * coinImage - the appearance of the coin
     */
    final static BodyImage coinImage = new BodyImage("data/Coin.png", 1.5f);

    /**
     * coinShape - the shape of the coin
     */
    final static Shape coinShape = new CircleShape(0.75f);

    /**
     * coinSound - the sound the coin makes when destroyed
     */
    final static SoundClip coinSound;

    static {
        try {
            coinSound = new SoundClip("data/coinsound.wav");
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }

//Constructor to create a coin, attach a shape and set an initial position
    public Coin(World world, float x, float y) {
        super(world, coinShape);
        this.addImage(coinImage);
        this.setPosition(new Vec2(x, y));
    }

    /**
     * This method creates additional coins you want to place into the level.
     * <p></p>
     * It creates the additional coins based on the position pf the first coin created
     *
     * @param world - This it the level which the coins are placed on
     * @param x - The x coordinate of the coin
     * @param y - The y coordinate of the coin
     * @param noOfCoin - The number of coins placed in to the level
     * @param spacing - The size of the horizontal spaces between each coin
     */
    //Method to loop coins on a platform
    public void coinLoop(World world, float x, float y, int noOfCoin, int spacing) {
        for (int i = 0; i < noOfCoin; i++) {
            x = x + spacing;
            new Coin(world, x, y);   //Calls the first constructor to create the coins
        }


    }


    /**
     * Sound plays when coin is destroyed
     * <p></p>
     * Additional code to play a coin sound before calling upon a method to destroy the coin
     * Used when player or their projectiles collide with a coin
     */
    @Override
    public void destroy() {
        coinSound.play();
        super.destroy();
    }

}
