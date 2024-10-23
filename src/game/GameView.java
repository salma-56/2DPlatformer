package game;

import city.cs.engine.BodyImage;
import city.cs.engine.UserView;
import city.cs.engine.World;
import org.jbox2d.common.Vec2;


import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;

import static java.awt.Font.BOLD;

/***
 * Shows the view of the world, including the background and foreground
 */
public class GameView extends UserView {


//Fields for colours, background images, fonts and foreground images
    private final Image background, background2, background3;
    private final Image gifBackground;
    private final Image coinIcon;

    private GameLevel level;
    private final Font font;
    private final Color colour1;

    private Image displayHealth;
    private final Color colour2;
    private final Color colour3;

    private Player player;

    /**
     *
     * @param level - Use to pick colours/backgrounds depending on the level chosen
     * @param width - Width of the view
     * @param height - Height of the view
     * @param player - Shows health and coin of this player
     */
    public GameView(GameLevel level, int width, int height, Player player) {
        super(level, width, height);

        font = new Font("SANS_SERIF", BOLD, 20);
        colour1 = new Color(157, 237, 255);
        colour2 = new Color(22,37,136);
        colour3 = new Color(18,70,21);

        this.player = player;

        this.level = level;

        //Level 1 backgrounds:
        gifBackground = new ImageIcon("data/Star.gif").getImage();
        background = new ImageIcon("data/Sea.png").getImage();

        //Level 2 backgrounds:

        background2 = new ImageIcon("data/SnowBackground.png").getImage();

        //Level 3 background:
        background3 = new ImageIcon("data/FairyBackground.png").getImage();


        coinIcon = new ImageIcon("data/CoinIcon.png").getImage();

    }

    /**
     * Changes background
     * <p>* </p>
     * Changes background depending on the level the player is at
     * @param g
     */
    @Override
    protected void paintBackground(Graphics2D g) {

        if (level instanceof Level1) {

            g.drawImage(this.gifBackground, 0, 0, this);
            g.drawImage(this.background, 0, 0, this);

        }

        if (level instanceof Level2) {


            g.drawImage(background2, 0, 0, 700, 500, this);

        }

        if(level instanceof Level3) {
            g.drawImage(background3, 0, 0,700,500,  this);
        }

    }

    /**
     * Changes foreground depending on the level this player is on
     *<p></p>
     * Changes colours of fonts, depending on level, displays the health and number of coins this player has
     * @param g
     */
    @Override
    protected void paintForeground(Graphics2D g) {
         if (level instanceof Level2) {
            g.setColor(colour2);
         }

         if(level instanceof Level1) {
             g.setColor(colour1);
         }

         if(level instanceof Level3) {
             g.setColor(colour3);
         }

            g.drawImage(this.setHealthDisplay(), 0, 0, this);
            g.drawImage(this.coinIcon, 0, 0, this);
            g.setFont(font);
            g.drawString(" " + Player.getCoin(), 33, 59);

        }

    /**
     * Chooses an image depending on current health of this player
     * <p></p>
     * Used to make the foreground display the corresponding health of this player
     * @return Image - Returns the corresponding foreground to the number of lives the player has
     */
    //This method changes the foreground based on how many lives you have:
    public Image setHealthDisplay() {
        if (player.getHealth() == 1) {
            displayHealth = new ImageIcon("data/Health1.gif").getImage();
            return displayHealth;
        } else if (player.getHealth() == 2) {
            displayHealth = new ImageIcon("data/Health2.png").getImage();
            return displayHealth;
        } else if (player.getHealth() == 3) {
            displayHealth = new ImageIcon("data/Health3.png").getImage();
            return displayHealth;
        }
        return null;
    }


    /**
     * Updates the level field
     * <p></p>
     * Used so level is the same as the level the player is on. Allowing correct foreground and background to be displayed
     * @param world - The current level, this player is on
     */
    @Override
    public void setWorld(World world) {
        super.setWorld(world);  //Need to use super., as we want the original method to keep running so we can continue to pass the world into a new level after a switch
        this.level = ((GameLevel) world);  //On top of that we now have an additional code, to change the level this class refers to, it will now refer to the one from the Game class
    }


public GameLevel getLevel() {
    return this.level;
}

}








