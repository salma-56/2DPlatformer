package game;

import city.cs.engine.DebugViewer;
import city.cs.engine.SoundClip;
import org.jbox2d.common.Vec2;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.*;
import java.io.IOException;

/**
 * The Level 1 for this game
 */
public class Level1 extends GameLevel {

    /**
     * The player which is in this level
     */
    private final Player player;

    /**
     * The audio for this level
      */
    static SoundClip level1Audio;

    static {
        try {
            level1Audio = new SoundClip("data/dreams.wav");
            level1Audio.loop();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Constructor creates platforms, coins and enemies
     * @param game - Used to create world
     */
    public Level1(Game game) {
        super(game);

        Platform.setColour(226,87,243);


//        new DebugViewer(this, 500, 500);

        //Creating ordinary platforms:

        Platform ground = new Platform(this, 7, 1.5f);
        ground.setPosition(new Vec2(-1, -46));

        Platform cloud = new Platform(this, "cloud");
        cloud.setPosition(new Vec2(14, -41));

        Platform cloud1 = new Platform(this, "cloud");
        cloud1.setPosition(new Vec2(0, -34));

        Platform cloud2 = new Platform(this, "cloud");
        cloud2.setPosition(new Vec2(9, -26));

        Platform cloud3 = new Platform(this, "cloud");
        cloud3.setPosition(new Vec2(-4, -19));

        Platform cloud4 = new Platform(this, "cloud");
        cloud4.setPosition(new Vec2(40, -1));
        cloud4.setIsFinalPlatform(true);

        //Platforms which disappear after player stands on it:

        Platform platform1Left = new Platform(this, 1f, 2f);
        platform1Left.setAngleDegrees(90);
        platform1Left.setPosition(new Vec2(12, -11));

        Platform platform1 = new Platform(this, 1f, 2f, 90, 8, -11);

        Platform platform1Right = new Platform(this, 1, 2);
        platform1Right.setAngleDegrees(90);
        platform1Right.setPosition(new Vec2(4, -11));


        Platform platform2Right = new Platform(this, 1, 2);
        platform2Right.setAngleDegrees(90);
        platform2Right.setPosition(new Vec2(19, -6));

        Platform platform2 = new Platform(this, 1, 2, 90, 23, -6);

        Platform platform2Left = new Platform(this, 1, 2);
        platform2Left.setAngleDegrees(90);
        platform2Left.setPosition(new Vec2(27, -6));


        //Place coins:
        Coin coinCloud1 = new Coin(this, -4, -31.5f);
        coinCloud1.coinLoop(this, -4, -31.5f, 4, 2);

        Coin coinCloud = new Coin(this, 10, -38.5f);
        coinCloud.coinLoop(this, 10, -38.5f, 4, 2);

        //Place players + enemies (patrollers)

        player = getPlayer();
        player.setPosition(new Vec2(0, -45));

        Villain villain1 = getVillain1();
        villain1.setPosition(new Vec2(-4, -18));
        Patroller patroller = new Patroller(villain1, cloud3, 4, player);
        this.addStepListener(patroller);


        Villain villain2 = getVillain2();
        villain2.setPosition(new Vec2(9, -24));
        Patroller patroller2 = new Patroller(villain2, cloud2, 4, player);
        this.addStepListener(patroller2);


    }

    /**
     * Determines whether this player complete goals of level
     * <p></p>
     * To accomplish level, this player must collect 10 or more coins
     * @return Boolean - If complete, will generate next level
     */
    @Override
    public boolean isComplete() {

        if (player.getCoin() >= 10) {
            return true;
        }
        return false;
    }

    /**
     * Disables players from creating water projectiles in level 1
     * @return false
     */
    @Override
    public boolean waterAttack() {
        return false;
    }

    //Used to create correct file after saving the game
    @Override
    public String getLevelName() {
        return "Level1";
    }

    //Gets audio so audio can be disabled from Game class
    @Override
    public SoundClip getSound() {
        return level1Audio;
    }



}

