package game;

import org.jbox2d.common.Vec2;

import java.io.*;

public class GameSaverLoader {

    private static Player player;

    private static GameLevel level;


    public GameSaverLoader (Player player) {
        GameSaverLoader.player = player;
    }

    /**
     * Saves the state of this player
     * <p></p>
     * Writes this player's health,coins and positions on to file
     * @param fileName - Saves level which this player is on
     * @throws IOException
     */
    public static void save(String fileName)
            throws IOException {
        FileWriter writer = null;

        //Writing player's attributes on to a file. Overrides enabled.
        try {
            writer = new FileWriter(fileName);
            
            writer.write(fileName + "," + player.getHealth() + "," + Player.getCoin() + "," + player.getPosition().x + "," + player.getPosition().y);
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    //Save: String levelName,  Int player Health,  Int player Coin, Int  player x position , Int player y position


    /**
     * Loads the most recent save file
     * <p></p>
     * Initialises a new level where this player's attributes are already set, using data from the latest save file
     * @param fileName - Writes different fileName for each level
     * @param game - Initialises new level
     * @return level - returns new Level back to game
     */
    public static GameLevel load(String fileName, Game game) {

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            System.out.println("Reading " + fileName + " ...");
            String line = br.readLine();

            String[] tokens = line.split(",");

            //Storing player's attributes from save file into variables
            String file = tokens[0];
            int playerHealth = Integer.parseInt(tokens[1]);
            int playerCoin = Integer.parseInt(tokens[2]);
            float playerXPos = Float.parseFloat(tokens[3]);
            float playerYPos = Float.parseFloat(tokens[4]);


            //Testing if values are correct:
            System.out.println("File  " + file + "  playerHealth: " + playerHealth + "   Coin: " + playerCoin + "  xPos:  " + playerXPos + "  yPos:  " + playerYPos);

            //Creating level using player and level data from the same file
            switch (file) {
                case "Level1" -> {
                     level = new Level1(game);
                    Player.setHealth(playerHealth);
                    Player.setCoin(playerCoin);
                    player = level.getPlayer();
                    level.getPlayer().setPosition(new Vec2(playerXPos, playerYPos));
                    return level;

                }
                case "Level2" -> {
                     level = new Level2(game);
                    Player.setHealth(playerHealth);
                    Player.setCoin(playerCoin);
                    player = level.getPlayer();
                    level.getPlayer().setPosition(new Vec2(playerXPos, playerYPos));
                    return level;
                }

                case "Level3" -> {
                    level = new Level3(game);
                    Player.setHealth(playerHealth);
                    Player.setCoin(playerCoin);
                    player = level.getPlayer();
                    level.getPlayer().setPosition(new Vec2(playerXPos, playerYPos));
                    return level;
                }

            }



        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }



        return null;
    }





}




