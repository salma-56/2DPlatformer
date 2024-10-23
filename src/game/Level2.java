package game;

import city.cs.engine.DebugViewer;
import city.cs.engine.SoundClip;
import org.jbox2d.common.Vec2;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.*;
import java.io.IOException;

/**
 * This class creates the level 2 platforms/coins/villains and allows level 2 files to be created
 */
public class Level2 extends GameLevel {

    /**
     * The background audio for level 2
     */
    private static SoundClip level2Sound;

    static {
        try {
            level2Sound = new SoundClip("data/wintersound.wav");
            level2Sound.loop();
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Creates all coins, platforms and enemies
     * @param game
     */
    public Level2(Game game) {
        super(game);

        getPlayer().setPosition(new Vec2(40,-1));
        Platform.setColour(72,156,229);

//        new DebugViewer(this, 500, 500);

        //Create ordinary platforms:

        Platform ground = new Platform(this, 14, 1.5f);
        ground.setPosition(new Vec2(0, -5));

        Platform platform1 = new Platform(this, "", "");
        platform1.setPosition(new Vec2(-10, 3));

        Platform platform2 = new Platform(this, "", "");
        platform2.setPosition(new Vec2(12, 5));

        Platform platform3 = new Platform(this, "", "");
        platform3.setPosition(new Vec2(0, 12));

        //Final Platform:
        Platform platform5 = new Platform(this, "", "");
        platform5.setPosition(new Vec2(38, 22));
        platform5.setIsFinalPlatform(true);


        //Create disappearing platforms:


        Platform platform4Left = new Platform(this, 1f, 2);
        platform4Left.setAngleDegrees(90);
        platform4Left.setPosition(new Vec2(13, 18));
        platform4Left.setFillColor(new Color(72,156,229));


        Platform platform4 = new Platform(this, 1f, 2f, 90, 17, 18);

        Platform platform4Right = new Platform(this, 1f, 2);
        platform4Right.setAngleDegrees(90);
        platform4Right.setPosition(new Vec2(21, 18));
        platform4Right.setFillColor(new Color(72,156,229));


        //Place coins:
        Coin coinGround = new Coin(this, -5, 15);
        coinGround.coinLoop(this, -5, 15, 5, 2);

        Coin coinPlatform4 = new Coin(this, 12, 20);
        coinGround.coinLoop(this, 12, 20, 5, 2);

        //Place player:
        getPlayer().setPosition(new Vec2(0, 0));


        //Create enemies:

        //Destroy the second (shared) enemy
        getVillain2().destroy();


        getVillain1().setPosition(new Vec2(-10, 0));


        Villain owlet = new Villain(this, "owlet");
        owlet.setPosition(new Vec2(-10, 6));

        Villain owlet2 = new Villain(this, "owlet");
        owlet2.setPosition(new Vec2(13, 9));


        //Patroller
        Patroller villain1 = new Patroller(getVillain1(), ground, 10, getPlayer());
        this.addStepListener(villain1);

        Patroller owletPatroller = new Patroller(owlet, platform1, 4, getPlayer());
        this.addStepListener(owletPatroller);

        Patroller owlet2Patroller = new Patroller(owlet2, platform2, 4, getPlayer());
        this.addStepListener(owlet2Patroller);


    }

    /**
     * Shows whether the level has completed
     * @return Returns true if coins collected are greater than 10
     */
    @Override
    public boolean isComplete() {
        if(Player.getCoin() >= 10) {
            return true;
        }
        return false;
    }


    /**
     * Enables water projectiles to be created under this level
     * @return true - so water projectiles can be created
     */
    @Override
    public boolean waterAttack() {
        return true;
    }

    /**
     * Creates a save file called "Level2"
     * <p></p>
     * Used so player can save and load a new Level2 game
     * @return
     */
    @Override
    public String getLevelName() {
        return "Level2";
    }

    /**
     * Returns background sound for this level
     * <p></p>
     * Used to adjust volume of audio/stop audio from playing
     * @return background sound
     */
    @Override
    public SoundClip getSound() {
        return level2Sound;
    }




}
