package game;

import city.cs.engine.*;

public class Patroller implements StepListener {

    /**
     * Class creates patroller
     * <p></p>
     * Used to describe the state of the enemy
     */
    private final Villain villain;
    private final Platform platform;
    private final float platformPos;
    private float villainPos;
    private final float platformRBoundary;  //Right boundary for cloud platform
    private final float platformLBoundary; // Left boundary for cloud platform

    private Boolean hit;
    private final Player player;
    private State state;
    private State newState;
    private Float gap;

//Creates the range the patroller has to travel
    public Patroller(Villain villain, Platform platform, int range, Player player) {
        this.villain = villain;
        this.platform = platform;
        platformPos = platform.getPosition().x;
        villainPos = villain.getPosition().x;
        platformRBoundary = platformPos + range;
        platformLBoundary = platformPos - range;
        this.hit = false;
        this.player = player;

        this.state = State.doNothing;
    }

    /**
     * Changes state of enemy depending on enemy type and number of hits
     * <p></p>
     * Used so enemies behave as patrollers or chasers
     * @param stepEvent
     */
    @Override
    public void preStep(StepEvent stepEvent) {
        villainPos = villain.getPosition().x;

        gap = player.getPosition().x - villain.getPosition().x;


        newState = State.dot;


        if (villain.getHitCount() >= 2 && gap != 0 && villain.getOwlet()) {

            if (villain.getPosition().x < player.getPosition().x) {
                newState = State.chaserRight;  //Chase the player right, if the enemy is behind the player
            } else if ((villain.getPosition().x > player.getPosition().x)) {
                newState = State.chaserLeft;  //Chase player left if enemy is in front of player
            }


        }

        if (villain.getHitCount() < 2 || villain.getPurple()) {


            //If the villain goes beyond the right boundary, it should turn left
            if (villainPos > platformRBoundary) {
                newState = State.patrollerLeft;
            } else if (villainPos < platformLBoundary) {  //If villain goes beyond the left boundary, turn right

                newState = State.patrollerRight;  //Call the state to move enemy right
            }


        }


        if (newState != this.state) {
            this.state = newState;
            this.defineState();
        }


    }

    @Override
    public void postStep(StepEvent stepEvent) {


    }


    //Determines if enemy patrols the region or chases you
    private static enum State {

        patrollerRight,
        patrollerLeft,

        chaserLeft,
        chaserRight,
        doNothing,

        dot;

        private State() {

        }
    }


    private void defineState() {
        switch (this.state) {

            case patrollerLeft:

                villain.startWalking(-1f);

                if (villain.getPurple()) {
                    villain.getImage("purpleLeft");
                } else if (villain.getOwlet()) {   //Change to left
                    villain.getImage("owletLeft");
                }
                // }

                break;
            case patrollerRight:

                villain.startWalking(1f);
                if (villain.getPurple()) {
                    villain.getImage("purpleRight");
                } else if (villain.getOwlet()) {   //Change to left
                    villain.getImage("owletRight");
                }


                break;


            case chaserRight:
                villain.startWalking(2);
                villain.getImage("owletRight");

                break;

            case chaserLeft:

                villain.startWalking(-2);
                villain.getImage("owletLeft");
                break;


            case doNothing: {
                villain.stopWalking();

            }


        }
    }


}

