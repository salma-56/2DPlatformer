package game;

import city.cs.engine.BodyImage;
import city.cs.engine.PolygonShape;
import city.cs.engine.Shape;
import city.cs.engine.World;
import org.jbox2d.common.Vec2;

public class Water extends Attack{
    /**
     * This class generates water projectiles
     */

    //Shape and Image for Right waves
   private static final Shape water = new PolygonShape(1.177f,0.556f, 1.412f,0.014f, 1.295f,-0.362f, 0.971f,-0.662f, 0.118f,-0.692f, -1.377f,-0.015f, -0.129f,0.626f);
private static final BodyImage RightWaterImage = new BodyImage("data/Water.gif", 1.5f);

//Shape and Image for Left waves
private static final BodyImage LeftWaterImage = new BodyImage("data/LeftWater.gif", 1.5f);
private static final Shape waterLeft = new PolygonShape(-1.406f,-0.121f, -1.4f,0.291f, -0.741f,0.726f, 0.306f,0.615f, 1.377f,0.003f, 0.465f,-0.609f, -0.388f,-0.668f
);

    /**
     * Creates right-facing water projectiles
     * @param level - The level projectile is created in
     * @param x - The initial x position of the projectile
     * @param y - The initial y position of the projectile
     */
    public Water(GameLevel level, float x, float y) {
        super(level,x,y, water);
        this.addImage(RightWaterImage);
        this.isAlwaysOutline();


    }

    /**
     * Creates left-facing water projectiles
     * @param level - The level the projectile is created in
     * @param x - The initial x position of projectile
     * @param y - The initial y position of the projectile
     * @param left - To show this is a left-facing projectile
     */
    //Left attacks
    public Water(GameLevel level, float x, float y, String left) {
        super(level,waterLeft, x, y);
        this.addImage(LeftWaterImage);
        this.isAlwaysOutline();

    }
}
