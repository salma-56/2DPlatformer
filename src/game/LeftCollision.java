package game;

import city.cs.engine.CollisionEvent;
import city.cs.engine.CollisionListener;
import city.cs.engine.World;
import org.jbox2d.common.Vec2;

import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Contains methods for what projectiles do upon contact with platforms, coins and enemies
 */
public class LeftCollision implements CollisionListener, ActionListener {


    /**
     * The left flame that will be destroyed
     */
    private final Flame leftFlame;

    /**
     * The ghostly fixture of the left flame - will be created upon a collision
     */
    private static Flame leftCollisionFlame;
    private final GameLevel level;


    /**
     * collisionOccured used to enable/disable the creation of more projectiles depending on its value
     */
    private static Boolean collisionOccurred;
    private  final Keyboard key;
    private final Player player;


    public LeftCollision(Flame leftFlame, GameLevel level, Keyboard key, Player player) {
        this.leftFlame = leftFlame;
        this.level = level;
        this.key = key;
        this.player = player;
         collisionOccurred = false; //Boolean to check if the flame has collided or not


    }

    /**
     *Destroys flame projectile and/or enemies depending on collisions
     * @param e
     */
    @Override
    public void collide(CollisionEvent e) {
        if (e.getOtherBody() instanceof Villain) {

            this.destroyFlame();

            if (((Villain) e.getOtherBody()).getPurple()) {
                e.getOtherBody().destroy();
            }

            //If enemy is an owlet, then increase their hit count.
            //If the hit count is 2/more, then destroy the owlet.

            if (((Villain) e.getOtherBody()).getOwlet()) {
                ((Villain) e.getOtherBody()).setHitCount();  //Increase the hit count

                //Only destroy the owlet if it was hit more than 2 times
                if (((Villain) e.getOtherBody()).getHitCount() > 2) {
                    e.getOtherBody().destroy();
                }




            } else if (e.getOtherBody() instanceof Platform) {
                this.destroyFlame();
            } else if (e.getOtherBody() instanceof Coin) {
                this.destroyFlame();
                e.getOtherBody().destroy();
                player.collectCoin();

            }
        }


    }


    /**
     * Destroys the flame and creates a ghostly fixture to show aftermath of collisions
     * <p></p>
     * Also generates a timer, so the ghostly fixture eventually self-destructs
     */
    public void destroyFlame() {
        //Set variable to true
        collisionOccurred = true;

        //Destroy the initial flame:
        Vec2 endLeft = new Vec2(leftFlame.getPosition()); //Store the intial flame's position
        leftFlame.destroy();

        //Create a new flame which has new image and shape:
        leftCollisionFlame = new Flame(level, "left"); //Create a new flame, which has a new image attached

        leftCollisionFlame.setPosition(endLeft); //Position of new flame is same as the old flame

        //Create a timer: This will destroy the newly made flame (leftCollisionFlame)
        Timer leftDestruct = new Timer(400, this);
        leftDestruct.start();
        leftDestruct.setRepeats(false);
    }


    /**
     * Destroys the flame and resets the count variable (under Keyboard)
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        leftCollisionFlame.destroy();   //Destroy the new flame produced
        key.resetCount();  //Reset the count so more flames can be produced
    }


    /**
     * Used so Keyboard class is aware if the projectile collided with any items
     * <p></p>
     * Allows Keyboard to generate more projectiles, as long as condition is true
     * @return
     */
    public static Boolean getCollisionOccurred() {
        return collisionOccurred;
    }

}


