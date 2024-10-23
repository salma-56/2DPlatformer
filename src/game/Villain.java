package game;

import city.cs.engine.*;

public class Villain extends Walker {

    /**
     * Class for how a villain is created with the images/hit counts associated with each villain
     */

    private static final Shape villainShape = new PolygonShape(0.42f, 1.94f, -0.47f, 1.9f, -1.2f, 1.01f, -1.29f, -1.09f, -1.08f, -1.95f, 0.9f, -1.98f, 1.25f, -1.34f, 1.33f, 0.94f
    );

    //Purple, patroller villain
    private static final BodyImage villainWalkingLeft = new BodyImage("data/EnemyWalkingLeft.gif", 4f);
    private static final BodyImage villainWalkingRight = new BodyImage("data/EnemyWalkingRight.gif", 4f);

    //Owlet villain
    private static final BodyImage owletWalkingRight = new BodyImage("data/OwletWalkingRight.gif", 4f);
    private static final BodyImage owletWalkingLeft = new BodyImage("data/OwletWalkingLeft.gif", 4f);

    //Used so other classes know if the enemy is a patroller or chaser
    private Boolean isPurple = false;
    private Boolean isOwlet = false;

    private int hitCount;

    /**
     * Creates a purple, patroller villain. With associated image
     * @param world - Used to place enemy in correct world
     */
    public Villain(World world) {
        super(world, villainShape);
        this.addImage(villainWalkingRight);
        this.startWalking(1);
        isPurple = true;
    }

    /**
     * Creates an owlet enemy, with an associated image added
     * @param level - Used so enemy is created in the correct level
     * @param owlet - Used so owlet enemy is created
     */

    //Owlet enemy
    public Villain(GameLevel level, String owlet) {
        super(level, villainShape);
        this.addImage(owletWalkingRight);
        this.startWalking(1);
        isOwlet = true;
        hitCount= 0;

    }

    /**
     * Changes image of both owlet and patroller enemy
     * <p></p>
     * Used so when enemy moves under Patroller class, correct image is placed on top
     * @param image - Image is placed depending on text used
     */
    //Method to change enemy's image
    public void getImage(String image) {
        switch (image) {
            //1 refers to villain 1 (purple villains)
            case ("purpleRight") -> {
                this.removeAllImages();
                this.addImage(villainWalkingRight);
            }
            case ("purpleLeft") -> {
                this.removeAllImages();
                this.addImage(villainWalkingLeft);
            }
            case ("owletRight") -> {
                this.removeAllImages();
                this.addImage(owletWalkingRight);
            }
            case ("owletLeft") -> {
                this.removeAllImages();
                this.addImage(owletWalkingLeft);
            }

        }

    }

    /**
     * Retrieves hit count of the enemy to adjust it
     * <p></p>
     * Used under projectile collisions class, so when player attacks enemy, the hit count can be altered
     * @return int - the number of hits
     */
    public int getHitCount() {
        return hitCount;
    }

    /**
     * Sets the hit count of the owlet villains
     * <p></p>
     * Used so when owlet is hit with projectile, its hit count can be updated
     */
    public void setHitCount() {
       this.hitCount = hitCount + 1;
    }


    /**
     * Shows Patroller class if enemy is a purple, patroller or not
     * @return true- shows enemy is a purple patroller
     */
        public Boolean getPurple() {
            return isPurple;
        }

    /**
     * Used to show Patroller class if an enemy is an owlet
     * @return true - if enemy is an owlet
     */
    public Boolean getOwlet() {
            return isOwlet;
        }
    }

