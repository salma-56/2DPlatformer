package game;

import city.cs.engine.CollisionEvent;
import city.cs.engine.CollisionListener;
import org.jbox2d.common.Vec2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public class RightCollision implements CollisionListener, ActionListener {
    /**
     * Class determines how a right flame, projectile interacts with enemies, platforms and coins
     */
    private final Flame rightFlame;
    private Flame rightCollisionFlame;
    private GameLevel level;
    private static Boolean collisionOccurred;
    private final Keyboard key;
private final Player player;
    public RightCollision(Flame rightFlame, GameLevel level, Keyboard key, Player player) {
        this.rightFlame = rightFlame;
        this.level = level;
        this.key = key;
        this.player = player;
        collisionOccurred = false;

    }

    @Override
    public void collide(CollisionEvent e) {

        //When flame collides with enemy:
        if (e.getOtherBody() instanceof Villain) {

            this.rightDestroy();


            if (((Villain) e.getOtherBody()).getPurple()) {
                e.getOtherBody().destroy();
            }

            //If enemy is an owlet, then increase their hit count.
            //If the hit count is 2/more, then destroy the owlet.

            if (((Villain) e.getOtherBody()).getOwlet()) {
                ((Villain) e.getOtherBody()).setHitCount();  //Increase the hit count

                if (((Villain) e.getOtherBody()).getHitCount() > 2) {
                    e.getOtherBody().destroy();
                }

            }
        }

//When flame collides with platforms:
       else if (e.getOtherBody() instanceof Platform) {

            this.rightDestroy();
        }


       //When flame collides with coins:
       else if (e.getOtherBody() instanceof Coin) {
            this.rightDestroy();
            e.getOtherBody().destroy();
            player.collectCoin();
        }

    }

    public void rightDestroy() {
        //Set variable true after a collision
        collisionOccurred= true;

        //Destroying initial flame:
        Vec2 endRight = new Vec2(rightFlame.getPosition()); //Store the initial flame's position
        rightFlame.destroy();

        //Create a new flame, this will have a new image attached:

        rightCollisionFlame = new Flame(level);
        rightCollisionFlame.setPosition(endRight); //Both the old and new flame have the same position


        //Start a timer - this will destroy the new flame produced (rightCollisionFlame):
        Timer destruct2 = new Timer(400, this);
        destruct2.start();
        destruct2.setRepeats(false);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
            rightCollisionFlame.destroy();
            key.resetCount();
        }

        public static Boolean getCollisionOccurred() {
            return collisionOccurred;
        }
    }





