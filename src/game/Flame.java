package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;


public class Flame extends Attack {


    //Images and shapes for right/left flames:
    private final static BodyImage rightFlame = new BodyImage("data/RightFlame.gif", 1.5f);

    private final static Shape rightFlameShape = new PolygonShape(1.482f, 0.585f, 1.774f, 0.136f, 1.692f, -0.463f, 1.22f, -0.732f, -1.272f, -0.515f, -1.849f, -0.156f, -0.891f, 0.473f, 0.816f, 0.585f
    );

    private static final BodyImage leftFlame = new BodyImage("data/LeftFlame.gif", 1.5f);

    private final static Shape leftFlameShape = new PolygonShape(-1.445f, 0.488f, -1.699f, 0.069f, -1.677f, -0.38f, -1.392f, -0.665f, 0.269f, -0.65f, 1.804f, -0.201f, 1.542f, 0.061f, 0.621f, 0.451f);


    //Images and shapes for the flames which are produced after a collision:
    private static final Shape rightCollision = new PolygonShape(1.711f, 0.658f, 1.902f, 0.368f, 1.894f, -0.48f, 1.589f, -0.663f, 1.016f, -0.785f, -1.833f, -0.021f, -1.764f, 0.322f, 1.199f, 0.765f
    );
    private static final BodyImage rightCollisionImage = new BodyImage("data/RightFlameCollision.gif", 1.5f);

    private static final Shape leftCollision = new PolygonShape(-1.757f, 0.613f, -1.886f, 0.391f, -1.909f, -0.235f, -1.749f, -0.632f, -1.046f, -0.755f, 1.864f, -0.044f, 1.81f, 0.353f, -1.13f, 0.742f
    );
    private static final BodyImage leftCollisionImage = new BodyImage("data/LeftFlameCollision.gif", 1.5f);


    //Constructor for right flame

    public Flame(GameLevel level, float x, float y, String flamePosition) {
        super(level, x, y, rightFlameShape);
        this.addImage(rightFlame);
        this.setLinearVelocity(new Vec2(3, 0));

    }

    //Constructor for left flame
    public Flame(GameLevel level, float x, float y) {
        super(level,leftFlameShape, x, y );
        this.addImage(leftFlame);

    }

    /**
     * Calls for the creation of a ghostly fixture, which has a right flame image and shape associated with it
     * <p></p>
     * This is used when the right flame,projectile has collided with an item, a new ghostly fixture appears to show the aftermath of the collision
     *
     * @param level - The projectile is created on the specified level
     */


    //Constructor for right flame which is produced after a collision
    public Flame(GameLevel level) {
        super(level);
        new GhostlyFixture(this, rightCollision);
        this.addImage(rightCollisionImage);
    }

    /**
     * A left facing ghostly projectile is created
     * <p></p>
     * This is used when a left, flame projectile has collided with an item. A new ghostly fixture appears to show the aftermath of the collision
     *
     * @param level - The level the projectile will be created in
     * @param left - To signify this has a left facing shape and image attached to the ghostly fixture
     */

    //Constructor for left flame which is produced after a collision
    public Flame(GameLevel level, String left) {
        super(level);
        new GhostlyFixture(this, leftCollision);
        this.addImage(leftCollisionImage);
    }


}





