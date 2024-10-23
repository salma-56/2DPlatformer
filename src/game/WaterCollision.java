package game;

import city.cs.engine.CollisionEvent;
import city.cs.engine.CollisionListener;

public class WaterCollision implements CollisionListener {
    /**
     * Class to show how water collides with platforms/coins/villains
     */
    private Water water;
    private static Boolean collisionOccurred;
    private Keyboard key;

    private Player player;

    public WaterCollision(Water water, Keyboard key, Player player) {
        this.water = water;
        this.key = key;
        this.player = player;
        collisionOccurred = false; //Used to check if more water projectiles can be generated
    }

    /**
     * Destroys projectile if in contact with platforms/coins/enemies
     * <p></p>
     * If colliding with a purple patroller, enemy is are destroyed. Only destroys owlet if owlet was hit twice
     * @param e
     */
    @Override
    public void collide(CollisionEvent e) {

//Destroys projectile if in contact with platforms

        if (e.getOtherBody() instanceof Platform) {
            destroy();
            collisionOccurred = true;

        }



 //Destroys projectiles and enemy if villain is hit

        if (e.getOtherBody() instanceof Villain) {

            //If the enemy is purple, destroy it on contact
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

            destroy();  //This destructs the water and allows you to create more
            collisionOccurred = true;


        }


        if (e.getOtherBody() instanceof Coin) {
            e.getOtherBody().destroy();
            player.collectCoin();
            destroy();
            collisionOccurred = true;


        }


    }

    /**
     * Destroy the projectile upon contact. Reset the count under Keyboard class
     * <p></p>
     * ResetCount used so Keyboard class will be allowed to generate more projectiles
     */
    public void destroy() {
        water.destroy();
        key.resetCount();
    }

    /**
     * Getter for if a collision has occured or not
     * Used so Keyboard class will be allowed to generate more projectiles or not
     * @return true if collision has occured. False if no collisions
     */
    public static Boolean getCollisionOccurred() {
        return collisionOccurred;
    }

}
