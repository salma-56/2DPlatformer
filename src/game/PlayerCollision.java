package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlayerCollision implements CollisionListener, ActionListener {

    private final Player player;
    private static Boolean allowVillainCollision;

//private final World world;


    private GameLevel level;
    private Game game;


    public PlayerCollision(Player player, GameLevel level, Game game) {
        this.player = player;
        allowVillainCollision = true;
        this.level = level;
        this.game = game;
    }

    @Override
    public void collide(CollisionEvent e) {

//Code only executes if the allowVillainCollision is set to true
        if ((e.getOtherBody() instanceof Villain && allowVillainCollision)) {

            player.reduceHealth();

            //Introduce a delay once the player has collided with villain:

            allowVillainCollision = false;    //When collision occurs, set variable to false
            Timer delay = new Timer(300, this);  //Start a timer, once time elapses variable is set to true
            delay.start();
            delay.setRepeats(false);

        }
        //Player picks up coins:
        if (e.getOtherBody() instanceof Coin) {
            e.getOtherBody().destroy();
            player.collectCoin();
        }

//        if (e.getOtherBody() instanceof Coin && level.isComplete()) {
//            e.getOtherBody().destroy();
//            player.collectCoin();
//            game.goToNextLevel();
//
//        }


        //If the player collides with the horizontal platforms only:
         if (e.getOtherBody() instanceof Platform && ((Platform) e.getOtherBody()).getPlatformCollision()) {


            //Storing the positions/width/height of the platform before its destroyed
            Vec2 platformPosition = e.getOtherBody().getPosition();
            float width = ((Platform) e.getOtherBody()).getW();
            float height = ((Platform) e.getOtherBody()).getH();

            //Destroy the platform, the player is currently standing on
            e.getOtherBody().destroy();

            //Create a ghostly fixture in the destroyed platform's place, this causes the player to fall through it
            Platform ghostPlatform = new Platform(level);
            ghostPlatform.setPosition(platformPosition);

            new GhostlyFixture(ghostPlatform, new BoxShape(width, height));

//Start a timer, when time completes, produce a new platform
            Timer platformTimer = new Timer(1100, (ActionListener) e.getOtherBody());
            platformTimer.start();
            platformTimer.setRepeats(false);


        }

         //Go to next level if you reach the final platform and have completed all level requirements
        if ((e.getOtherBody() instanceof  Platform) &&  (((Platform) e.getOtherBody()).getIsFinalPlatform()) && (level.isComplete()) ) {
            game.goToNextLevel();
        }




    }

    @Override
    public void actionPerformed(ActionEvent e) {
        allowVillainCollision = true;
    }


}

