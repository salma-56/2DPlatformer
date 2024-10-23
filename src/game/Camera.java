package game;

import city.cs.engine.StepEvent;
import city.cs.engine.StepListener;
import org.jbox2d.common.Vec2;



public class Camera implements StepListener {

    /**
     * This class is used to adjust the view, so it centres on the player's new positions
     *
     * Fields:
     * view is used to set the centre, to the player's new position
     * player used to access the current Vec2 coordinates of the player
     */

    /**
     * The view of the world
     */
    private final GameView view;

    /**
     * The player - the view will track this player's movements
     */
    private final Player player;


    public Camera(GameView view, Player player) {
        this.view = view;
        this.player = player;
    }

    @Override
    public void preStep(StepEvent stepEvent) {
    }

    /**
     * This tracks the player's movement
     * This method allows the view to always track the player's positions including when new levels are introduced/levels have been reset.
     * @param stepEvent
     */
    @Override
    public void postStep(StepEvent stepEvent) {
        Vec2 playerPosition = player.getPosition();
Vec2 hideGround = new Vec2(playerPosition.x, playerPosition.y+8.4f);
        view.setCentre(hideGround);

        /* The method involves variables, hideGround which adjusts the y-value of the player's positions
        ,so the bottom of the platforms are not visible
         */
    }

    }




