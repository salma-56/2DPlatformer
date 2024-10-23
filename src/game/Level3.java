package game;

import city.cs.engine.DebugViewer;
import city.cs.engine.SoundClip;
import org.jbox2d.common.Vec2;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.*;
import java.io.IOException;

public class Level3 extends GameLevel {

    /**
     * Class which creates level3 projectiles, coins and enemies.
     */

    /**
     * level3Audio is the background track of the level
     */
    private static SoundClip level3Audio;

    //Plays the sound
    static {
        try {
            level3Audio = new SoundClip("data/level3audio.wav");
            level3Audio.loop();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Creates platforms, villains and coins for level 3
     * @param game - Used to create the new world
     */
    public Level3(Game game) {


        super(game);

//        new DebugViewer(this, 500, 500);

        // Create platforms

        Platform ground = new Platform(this, "", 1);
        ground.setPosition(new Vec2(0, -5));

        Platform platform1 = new Platform(this, "", 1);
        platform1.setPosition(new Vec2(27, -5));


        Platform platform2 = new Platform(this, "", "", "");
        platform2.setPosition(new Vec2(40, 3));

        Platform platform3 = new Platform(this, "", 1);
        platform3.setPosition(new Vec2(25, 10));

        Platform platform4 = new Platform(this, "", 1);
        platform4.setPosition(new Vec2(5, 14));

        Platform platform5 = new Platform(this, "", 1);
        platform5.setPosition(new Vec2(-20, 14));

        Platform platform6 = new Platform(this, "", "", "");
        platform6.setPosition(new Vec2(-35, 20));


        Platform platform7 = new Platform(this, "", 1);
        platform7.setPosition(new Vec2(-20, 29));

//Final platform
        Platform platform8 = new Platform(this, "", 1);
        platform8.setPosition(new Vec2(5, 29));
        platform8.setIsFinalPlatform(true);

        //Place player
        getPlayer().setPosition(new Vec2(0, -5));

        //Create enemies
        getVillain1().setPosition(new Vec2(27, -3));
        Patroller patroller1 = new Patroller(getVillain1(), platform1, 5, getPlayer());
        this.addStepListener(patroller1);


        getVillain2().setPosition(new Vec2(25, 12));
        Patroller patroller2 = new Patroller(getVillain2(), platform3, 5, getPlayer());
        this.addStepListener(patroller2);


        Villain owlet1 = new Villain(this, "owlet");
        owlet1.setPosition(new Vec2(5, 15));
        Patroller patroller3 = new Patroller(owlet1, platform4, 5, getPlayer());
        this.addStepListener(patroller3);

        Villain owlet2 = new Villain(this, "owlet");
        owlet2.setPosition(new Vec2(-20, 15));
        Patroller patroller4 = new Patroller(owlet2, platform5, 5, getPlayer());
        this.addStepListener(patroller4);

        Villain villain3 = new Villain(this);
        villain3.setPosition(new Vec2(-20, 30));
        Patroller patroller5 = new Patroller(villain3, platform7, 5, getPlayer());
        this.addStepListener(patroller5);


        //Create coins
        Coin coin2 = new Coin(this, 37, 5);
        coin2.coinLoop(this, 37, 5, 3, 2);

        Coin coin6 = new Coin(this, -38, 22);
        coin6.coinLoop(this, -38, 22, 3, 2);


    }

    /**
     * Shows whether the level has been completed or not
     * <p></p>
     * To complete level 3, 6 coins must be picked up
     * @return Returns a boolean depending on if level goals were accomplished
     */
    @Override
    public boolean isComplete() {

        if (Player.getCoin() >= 10) {  //6 coins picked up to complete the level
            return true;
        }
        return false;
    }

    /**
     *
     * @return true- so water projectile can be created under level 3
     */
    @Override
    public boolean waterAttack() {
        return true;
    }


    @Override
    public String getLevelName() {
        return "Level3";
    }

    @Override
    public SoundClip getSound() {
        return level3Audio;
    }

}
