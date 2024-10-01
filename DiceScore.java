/**
 * compute player's dice score for yahtzee game
 * 
 * @author Sam Farris
 * @date 9/30/2024
 */

public class DiceScore {
    private static final int ONES = 1;
    private static final int TWOS = 2;
    private static final int THREES = 3;
    private static final int FOURS = 4;
    private static final int FIVES = 5;
    private static final int SIXES = 6;
    private static final int THREE_OF_A_KIND = 7;
    private static final int FOUR_OF_A_KIND = 8;
    private static final int FULL_HOUSE = 9;
    private static final int SMALL_STRAIGHT = 10;
    private static final int LARGE_STRAIGHT = 11;
    private static final int YAHTZEE = 12;
    private static final int CHANCE = 13;
    private static final int FULL_HOUSE_POINTS = 25;
    private static final int SMALL_STRAIGHT_POINTS = 30;
    private static final int LARGE_STRAIGHT_POINTS = 40;
    private static final int YAHTZEE_POINTS = 50;
    /**
     * calculate the total dice score for the category and dice params
     * 
     * @param category the category to calculate
     * @param dice the dice to calculate
     * @return the total dice score for the category and dice params
     */
    public static int calculate(int category, int[] dice) {
        category++;
        switch (category) {
            case DiceScore.ONES:
            case DiceScore.TWOS:
            case DiceScore.THREES:
            case DiceScore.FOURS:
            case DiceScore.FIVES:
            case DiceScore.SIXES:
                return DiceScore.sum(dice, category);
            case DiceScore.THREE_OF_A_KIND:
            case DiceScore.FOUR_OF_A_KIND:
                return DiceScore.hasNOfAKind(dice, category - 4) ? UtilArray.sum(dice) : 0;
            case DiceScore.FULL_HOUSE:
                return (DiceScore.hasNOfAKind(dice, 3) && DiceScore.hasNOfAKind(dice, 2)) ? DiceScore.FULL_HOUSE_POINTS : 0;
            case DiceScore.SMALL_STRAIGHT:
                return DiceScore.hasStraight(dice, 4) ? DiceScore.SMALL_STRAIGHT_POINTS : 0;
            case DiceScore.LARGE_STRAIGHT:
                return DiceScore.hasStraight(dice, 5) ? DiceScore.LARGE_STRAIGHT_POINTS : 0;
            case DiceScore.YAHTZEE:
                return DiceScore.hasNOfAKind(dice, 5) ? DiceScore.YAHTZEE_POINTS : 0;
            case DiceScore.CHANCE:
                return UtilArray.sum(dice);
            default:
                return 0;
        }
    }
    /**
     * sum that includes the number param for the dice param
     * 
     * @param dice the dice to sum that includes the number param
     * @param num the number to include in the sum
     * @return the total sum that includes the number param
     */
    private static int sum(int[] dice, int num) {
        int sum = 0;
        for (int i = 0; i < dice.length; i++) {
            if (num == dice[i]) sum += num;
        }
        return sum;
    }
    /**
     * check if there is repeating numbers n param amount of times
     * 
     * @param dice the dice to check if there is repeating numbers
     * @param n the number of times to check for repeating numbers
     * @return if there is repeating numbers n param amount of times
     */
    private static boolean hasNOfAKind(int[] dice, int n) {
        int counts[] = new int[Yahtzee.MAX_DICE];
        for (int i = 0; i < counts.length; i++) {
            counts[i] = 0;
        }
        for (int i = 0; i < dice.length; i++) {
            counts[dice[i] - 1]++;
        }
        for (int i = 0; i < counts.length; i++) {
            if (counts[i] >= n) return true;
        }
        return false;
    }
    /**
     * check if there is an increment length param amount of times
     * 
     * @param dice the dice to check if there it increments
     * @param length the length of increments
     * @return if there is an increment length param amount of times
     */
    private static boolean hasStraight(int[] dice, int length) {
        int temp;
        for (int i = 0; i < dice.length; i++) {
            for (int j = i + 1; j < dice.length; j++) {
                if (dice[i] > dice[j]) {
                    temp = dice[i];
                    dice[i] = dice[j];
                    dice[j] = temp;
                }
            }
        }
        int count = 1;
        for (int i = 1; i < Yahtzee.NUM_OF_DICE; i++) {
            if (dice[i] == dice[i - 1] + 1 && ++count == length) return true;
            else if (dice[i] > dice[i - 1] + 1) count = 1;
        }
        return false;
    }
}