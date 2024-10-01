/**
 * utility for yahtzee game to work with different arrays 
 * 
 * @author Sam Farris
 * @date 9/30/2024
 */

public class UtilArray {
    /**
     * calculate the total of arr param
     * 
     * @param arr the arr to sum
     * @return the total for the arr param
     */
    public static int sum(int[] arr) {
        int sum = 0;
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];
        }
        return sum;
    }
    /**
     * convert the arr param to a string
     * 
     * @param arr the arr to convert to string
     * @return the string for the arr param
     */
    public static String toString(int[] arr) {
        String str = "";
        for (int i = 0; i < arr.length; i++) {
            str += arr[i];
            if (i != arr.length - 1) str += ", ";
        }
        return str;
    }
    /**
     * convert the arr param to a string
     * 
     * @param arr the arr to convert to string
     * @return the string for the arr param
     */
    public static String toString(boolean[] arr) {
        String str = "";
        for (int i = 0; i < arr.length; i++) {
            if (!arr[i]) continue;
            str += i + 1 + ", ";
        }
        return str.substring(0, str.length() - 2);
    }
}