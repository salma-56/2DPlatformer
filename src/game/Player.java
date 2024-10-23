package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;


public class Player extends Walker {

    /**
     * Class for player's images, shapes and current number of lives or coins gained
     */
//Player images and shapes
    private static final Shape playerShape = new PolygonShape(-0.62f, 1.98f, 0.51f, 1.88f, 1.18f, 0.75f, 1.27f, -1.28f, 0.62f, -1.98f, -0.92f, -1.98f, -1.29f, -1.07f, -1.12f, 1.62f);

    private static final BodyImage idleRight = new BodyImage("data/PinkGifRight.gif", 4f);
    private static final BodyImage idleLeft = new BodyImage("data/PinkGifLeft.gif", 4f);

    private static final BodyImage walkRight = new BodyImage("data/WalkingRight.gif", 4f);
    private static final BodyImage walkLeft = new BodyImage("data/WalkingLeft.gif", 4f);
    private static final BodyImage attackRight = new BodyImage("data/PinkThrow.gif", 4f);
    private static final BodyImage attackLeft = new BodyImage("data/PinkLeftThrow.gif", 4f);

    private static BodyImage currentFacingImage; //Used to store players direction

    //Player features - health and coins collected
    private static int health = 3;

    private static int coin = 0;

    private static Game game;

    /**
     * Creates player, and initial image displayed on player is right-facing
     */
    public Player(GameLevel level, Game game) {
        super(level, playerShape);
        this.addImage(idleRight);
        SolidFixture fixture = new SolidFixture(this, playerShape);
        fixture.setFriction(2);
        currentFacingImage = idleRight; //When player first uses an attack , a right facing projectile is produced
        this.game = game;

    }

    /**
     * Changes the image of the player depending on their movements
     * <p></p>
     * Used so if player moves, a corresponding image is placed on player - depending on how the player moved
     * @param image - String is used to choose the corresponding image
     */
    public void changeImage(String image) {
        switch (image) {
            //Choose an idle image
            case "idleRight" -> {
                this.removeAllImages();
                this.addImage(idleRight);
                this.playerFacing(idleRight);  //Use this method to store the player's image, this ensures flames are in the correct direction
            }
            case "idleLeft" -> {
                this.removeAllImages();
                this.addImage(idleLeft);
                this.playerFacing(idleLeft);
            }

            //Images for when player is walking
            case "right" -> {
                this.removeAllImages();
                this.addImage(walkRight);
            }

            case "left" -> {
                this.removeAllImages();
                this.addImage(walkLeft);
            }

            //Images for when player attacks
            case "rightAttack" -> {
                this.removeAllImages();
                this.addImage(attackRight);
            }

            case "leftAttack" -> {
                this.removeAllImages();
                this.addImage(attackLeft);
            }

        }

    }

    //Using image to store the player's position e.g. if player is facing right/left:

    public void playerFacing(BodyImage image) {
        currentFacingImage = image;
    }

    public BodyImage getPlayerPosition() {
        return currentFacingImage;  //Using a getter, so keyboard class can access the direction the player faces
    }


    public BodyImage getImage(String choose) {
        switch (choose) {
            case "right" -> {
                return idleRight;
            }
            case "left" -> {
                return idleLeft;
            }
        }
        return null;
    }

    /**
     * Reduces health of player + resets the level if the player loses all lives
     */
    public void reduceHealth() {
        --health;


        //Call the reset button when you lose all lives:
        if (health == 0) {
            game.restartLevel();

        }
    }

    public int getHealth() {
        return health;
    }


    public static void collectCoin() {
        coin = coin + 1;
    }

    public static int getCoin() {
        return coin;
    }

    /**
     * Reset the fields, health and coins if called
     */
    public static void resetPlayerItems() {
        coin = 0;
        health = 3;
    }

    //Setter for when loading a new game:
    public static void setHealth(int health) {
        Player.health = health;
    }

    public static void setCoin(int coin) {
        Player.coin = coin;
    }

}


