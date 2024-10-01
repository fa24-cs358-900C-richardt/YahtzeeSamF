/**
 * yahtzee game for 1-6 players with command line interface
 * 
 * @author Sam Farris
 * @date 9/30/2024
 */

import java.lang.NumberFormatException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Yahtzee {
    private static final int MAX_PLAYERS = 6;
    private static final int NUM_OF_ROLLS = 3;
    private static final int TOP_TOTAL_BONUS_REQUIRED = 63;
    private static final int TOP_TOTAL_BONUS_POINTS = 35;
    private static final String[] TOP_CATEGORIES = {
        "Ones", "Twos", "Threes", "Fours", "Fives", "Sixes"
    };
    private static final String[] BOTTOM_CATEGORIES = {
        "Three of a Kind", "Four of a Kind", "Full House",
        "Small Straight", "Large Straight", "Yahtzee", "Chance"
    };
    private static final String[] CATEGORIES = new String[TOP_CATEGORIES.length + BOTTOM_CATEGORIES.length];
    private static Random random = new Random();
    private static Scanner scanner = new Scanner(System.in);
    public static final int MAX_DICE = 6;
    public static final int NUM_OF_DICE = 5;
    public static final int NUM_OF_TOP_CATEGORIES = TOP_CATEGORIES.length;
    public static final int NUM_OF_BOTTOM_CATEGORIES = BOTTOM_CATEGORIES.length;
    public static final int NUM_OF_CATEGORIES = CATEGORIES.length;
    /**
     * start the yahtzee game, give the turns to each player, and display final scores
     * 
     * @param args
     */
    public static void main(String args[]) {
        for (int i = 0; i < Yahtzee.CATEGORIES.length; i++) {
            if (i + 1 <= Yahtzee.NUM_OF_TOP_CATEGORIES) Yahtzee.CATEGORIES[i] = Yahtzee.TOP_CATEGORIES[i];
            else Yahtzee.CATEGORIES[i] = Yahtzee.BOTTOM_CATEGORIES[i - Yahtzee.NUM_OF_TOP_CATEGORIES];
        }
        int numOfPlayers = Yahtzee.getNumOfPlayers();
        Player[] players = new Player[numOfPlayers];
        for (int i = 0; i < numOfPlayers; i++) {
            System.out.print("Enter name for player #" + (i + 1) + ": ");
            players[i] = new Player(scanner.nextLine());
        }
        for (int i = 0; i < Yahtzee.NUM_OF_CATEGORIES; i++) {
            for (int j = 0; j < numOfPlayers; j++) {
                Yahtzee.playTurn(players[j]);
            }
        }
        Yahtzee.displayFinalScores(players);
    }
    /**
     * prompt for number of players that are going to play,
     * and handle error input accordingly
     * 
     * @return the number of players playing yahtzee
     */
    private static int getNumOfPlayers() {
        while (true) {
            System.out.print("Enter number of players for Yahtzee (1-" + Yahtzee.MAX_PLAYERS + "): ");
            try {
                int num = Integer.parseInt(scanner.nextLine());
                if (num >= 1 && num <= Yahtzee.MAX_PLAYERS) return num;
            } catch (NumberFormatException e) {
            }
            System.out.print("Invalid input, ");
        }
    }
    /**
     * prompt the player with their rolls and actions they can take after each roll,
     * after rolls ask for category to use, and handle error input accordingly 
     * 
     * @param player the player to give their turn to
     */
    private static void playTurn(Player player) {
        System.out.println("\n" + player.name + "'s turn");
        int[] dice = new int[Yahtzee.NUM_OF_DICE];
        boolean[] keep = new boolean[Yahtzee.NUM_OF_DICE];
        for (int i = 0; i < Yahtzee.NUM_OF_ROLLS; i++) {
            Yahtzee.rollDice(dice, keep);
            System.out.println("Roll #" + (i + 1) + ": " + UtilArray.toString(dice));
            if (i < Yahtzee.NUM_OF_ROLLS - 1) Yahtzee.keepDice(keep, i + 1);
        }
        Yahtzee.chooseCategory(player, dice);
    }
    /**
     * roll the dices that aren't being kept
     * 
     * @param dice the dice to roll
     * @param keep the dice to not roll based on index
     */
    private static void rollDice(int[] dice, boolean[] keep) {
        for (int i = 0; i < Yahtzee.NUM_OF_DICE; i++) {
            if (!keep[i]) dice[i] = random.nextInt(Yahtzee.MAX_DICE) + 1;
        }
    }
    /**
     * prompt the player for the dice to keep then stop keeping if need be,
     * and handle error input accordingly
     * 
     * @param keep the dice to not roll based on index
     * @param rolls the roll number the player is currently on
     */
    private static void keepDice(boolean[] keep, int rolls) {
        boolean keepFlag = true;
        outer: while (true) {
            System.out.print("Enter indexes (1-" + Yahtzee.NUM_OF_DICE + " comma separated) of dice to" + (keepFlag
                ? " keep or press Enter to reroll all: "
                : " not keep anymore or press Enter to keep index" + (keep.length == 1 ? " " : "es ") + UtilArray.toString(keep) + ": "));
            String input = scanner.nextLine();
            String[] indexes;
            if (input.isEmpty()) {
                if (!keepFlag) break;
                keepFlag = false;
                indexes = new String[Yahtzee.NUM_OF_DICE];
                for (int i = 0; i < indexes.length; i++) {
                    indexes[i] = Integer.toString(i + 1);
                }
            } else indexes = input.split(",");
            for (int i = 0; i < indexes.length; i++) {
                try {
                    int index = Integer.parseInt(indexes[i].trim());
                    if (index >= 1 && index <= Yahtzee.NUM_OF_DICE) {
                        keep[index - 1] = keepFlag;
                        if (i != indexes.length - 1 || rolls == 1 || !keepFlag) continue;
                        keepFlag = false;
                        continue outer;
                    }
                } catch (NumberFormatException e) {
                }
                for (int j = 0; j < indexes.length; j++) {
                    keep[j] = false;
                }
                System.out.print("Invalid input (reset previously kept), ");
                keepFlag = true;
                continue outer;
            }
            break;
        }
    }
    /**
     * prompt the player for the available categories they can use,
     * calculate score based on dice param, and update player's scores,
     * and handle error input accordingly
     * 
     * @param player the player to choose category and update scores for
     * @param dice the dice the player rolled previously
     */
    private static void chooseCategory(Player player, int[] dice) {
        for (int i = 0; i < Yahtzee.NUM_OF_CATEGORIES; i++) {
            if (player.usedCategories[i]) continue;
            System.out.println((i + 1) + ". " + CATEGORIES[i]);
        }
        while (true) {
            System.out.print("Enter category number to use: ");
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                if (choice >= 1 && choice <= Yahtzee.NUM_OF_CATEGORIES && !player.usedCategories[--choice]) {
                    int score = DiceScore.calculate(choice, dice);
                    if (choice + 1 <= Yahtzee.NUM_OF_TOP_CATEGORIES) player.topScores[choice] = score;
                    else player.bottomScores[choice - Yahtzee.NUM_OF_TOP_CATEGORIES] = score;
                    player.usedCategories[choice] = true;
                    System.out.println("You scored " + score + " points in " + Yahtzee.CATEGORIES[choice]);
                    return;
                }
            } catch (NumberFormatException e) {
            }
            System.out.print("Invalid input, ");
        }
    }
    /**
     * display final scores in command line for the players param,
     * and display winner or winners based on the final scores
     * 
     * @param players the players to display final scores for
     */
    private static void displayFinalScores(Player[] players) {
        System.out.println("\nResults:");
        int max = -1;
        ArrayList<String> winners = new ArrayList<String>();
        for (int i = 0; i < players.length; i++) {
            Player player = players[i];
            int topTotal = UtilArray.sum(player.topScores);
            int bottomTotal = UtilArray.sum(player.bottomScores);
            int total = topTotal + bottomTotal;
            if (topTotal >= Yahtzee.TOP_TOTAL_BONUS_REQUIRED) total += Yahtzee.TOP_TOTAL_BONUS_POINTS;
            if (total >= max) {
                if (total > max) {
                    max = total;
                    winners.clear();
                }
                winners.add(player.name);
            }
            System.out.println(player.name + ": "
                + topTotal + " top + "
                + (topTotal >= Yahtzee.TOP_TOTAL_BONUS_REQUIRED
                    ? (Yahtzee.TOP_TOTAL_BONUS_POINTS + " bonus + ")
                    : "")
                + bottomTotal + " bottom = " + total);
        }
        System.out.println("\nThe winner" + (winners.size() == 1 ? " is: " : "s are: ") + String.join(", ", winners));
    }
}