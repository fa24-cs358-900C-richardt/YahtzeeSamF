/**
 * player object for yahtzee game to store players data
 * 
 * @author Sam Farris
 * @date 9/30/2024
 */

public class Player {
    String name;
    int[] topScores = new int[Yahtzee.NUM_OF_TOP_CATEGORIES];
    int[] bottomScores = new int[Yahtzee.NUM_OF_BOTTOM_CATEGORIES];
    boolean[] usedCategories = new boolean[Yahtzee.NUM_OF_CATEGORIES];
    /**
     * set initial data for player
     * 
     * @param name the name for the player
     */
    Player(String name) {
        this.name = name;
    }
}