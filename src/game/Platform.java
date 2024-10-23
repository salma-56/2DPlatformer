package game;

import city.cs.engine.*;
import city.cs.engine.Shape;
import city.cs.engine.StaticBody;
import org.jbox2d.common.Vec2;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Platform extends StaticBody implements ActionListener {

    /**
     * Used to create all platforms types of the game
     */
    private static final Shape cloudPlatformShape = new PolygonShape(-5.1f, 1.31f, 5.76f, 1.35f, 6.0f, 1.16f, 5.97f, 0.19f, 4.64f, -1.14f, 3.53f, -1.52f, -4.04f, -1.45f, -5.93f, -0.19f);

    private static final BodyImage pinkCloud = new BodyImage("data/Cloud.png", 3f);
    private Boolean platformCollision = false;
    private static World world;

    private float w, h, x, y;
    private static int r,g,b;



    //Level 2 platforms shapes and images
    private static final Shape winterPlatformShape = new PolygonShape(1.27f, 1.68f, 6.66f, 1.29f, 5.82f, -0.41f, 1.24f, -1.99f, -1.93f, -1.78f, -5.55f, -0.51f, -6.61f, 1.07f
    );

    private static final BodyImage winterImage = new BodyImage("data/WinterPlatform.png", 4);


    private static final BodyImage smallIvyPlatform = new BodyImage("data/IvyPlatform.png", 3.5f);

  private static final BodyImage ivyPlatform = new BodyImage("data/IvyPlatform.png", 7.5f);
    private static final Shape smallIvyPlatformShape = new PolygonShape(-4.14f, 0.33f, 3.65f, 0.47f, 4.06f, -0.3f, 3.32f, -1.04f, -0.07f, -1.73f, -3.36f, -0.82f);


    private static final Shape ivyPlatformShape = new PolygonShape(-8.99f,0.7f, 8.7f,0.52f, 8.73f,-0.95f, 7.08f,-2.27f, -0.11f,-3.77f, -6.86f,-1.94f, -8.81f,-0.84f
    );

    private Boolean isFinalPlatform = false;

    public static void setColour(int r, int g, int b) {
        Platform.b = b;
        Platform.g = g;
        Platform.r = r;
    }



    //Constructor for Level1 platform
    public Platform(World world, String platform) {
        super(world, cloudPlatformShape);
        this.addImage(pinkCloud);
    }

    //Constructor for Level2 platform
    public Platform(GameLevel level, String p, String platform) {
        super(level, winterPlatformShape);
        this.addImage(winterImage);
    }


    //Constructor for Level3 platform
    public Platform(GameLevel level, String platform, int no) {
        super(level, ivyPlatformShape);
        this.addImage(ivyPlatform);
//        this.setAlwaysOutline(true);

    }

    public Platform(GameLevel level, String ivy , String size, String type) {
        super(level, smallIvyPlatformShape);
        this.addImage(smallIvyPlatform);
//        this.setAlwaysOutline(true);

    }

    //Vertical Platforms
    public Platform(World world, float w, float h) {
        super(world, new BoxShape(w, h));
        this.setFillColor(new Color(226, 87, 243));


    }

    //Horizontal Platform which the Player can collide with
    public Platform(World world, float w, float h, int degrees, float x, float y) {

        super(world, new BoxShape(w, h));
        this.setFillColor(new Color(r,g,b));
//        this.setAlwaysOutline(true);
        this.setAngleDegrees(90);
        platformCollision = true; //This variable must be true to enable collisions

        //Storing variables so another platform with same attributes can be created later
        this.w = w;
        this.h = h;
        this.x = x;
        this.y = y;


        Platform.world = world;


        this.setPosition(new Vec2(x, y));


    }

    /**
     * Creates a ghostly fixture platform
     * @param world - Where the platform must be placed
     */
    //Constructor used to make ghostly fixtures - platforms player can fall through
    public Platform(World world) {
        super(world);
        this.setAngleDegrees(90);
        this.setFillColor(new Color(r, g, b, 50));

    }




    //Variable used to detect player collisions with horizontal platforms
    public Boolean getPlatformCollision() {
        return platformCollision;
    }


    //Retrieving width and height so a GhostlyFixture can be made after a collision
    public float getW() {
        return w;
    }

    public float getH() {
        return h;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        //Creating a replacement horizontal platform using stored variables
        new Platform(world, w, h, 90, x, y);
    }




    //Method to state final platform has been reached
    public void setIsFinalPlatform(Boolean isFinalPlatform) {
        this.isFinalPlatform = isFinalPlatform;
    }

    public Boolean getIsFinalPlatform() {
        return this.isFinalPlatform;
    }
}






