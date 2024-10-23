package game;

import city.cs.engine.DynamicBody;
import city.cs.engine.Shape;
import org.jbox2d.common.Vec2;

/** This is the base class for the projectiles,
 the constructor provides all basic functionalities of the projectiles- their positions, speeds and anti-gravity
 */
public class Attack extends DynamicBody {


    /**
     *
     * @param level - The level this projectile is created in
     * @param x - The initial, x position of the projectile
     * @param y - The initial, y position of the projec
     * @param shape - The shape of the projectile
     */
    //Right attack:
    public Attack(GameLevel level, float x, float y, Shape shape) {
        super(level, shape);
        this.setPosition(new Vec2(x, y));
        this.setGravityScale(0);
        this.setLinearVelocity(new Vec2(3, 0));

    }

    /**
     * Creates a left-facing projectile
     * @param level - The level the projectile will be created in
     * @param shape - The shape of the projectile
     * @param x - Initial x position of the projectile
     * @param y - Initial y position of the projectile
     */
    //Left attack: This allows for projectiles with a left-facing image and shape to be created
    public Attack(GameLevel level, Shape shape, float x, float y) {
        super(level, shape);
        this.setPosition(new Vec2(x, y));
        this.setGravityScale(0);
        this.setLinearVelocity(new Vec2(-3, 0));
    }

    /**
     * Creates a ghostly fixture
     * <p></p>
     * Used when a ghostly fixture of a flame is created
     * @param level - The level the projectile will be created in
     */
    //For ghostly fixture projectiles:
    public Attack(GameLevel level) {
        super(level);
        this.setGravityScale(0);
    }
}
