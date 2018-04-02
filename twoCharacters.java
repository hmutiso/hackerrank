/*
 * Solution to the HackerRank problem: Two Characters.
 * https://www.hackerrank.com/challenges/two-characters/problem
 *
 * Problem:
 *
 * In this challenge, you will be given a string. You must remove characters
 * until the string is made up of any two alternating characters. When you
 * choose a character to remove, all instances of that character must be
 * removed. Your goal is to create the longest string possible that contains
 * just two alternating letters.
 *
 * As an example, consider the string "abaacdabd". If you delete the character
 * 'a', you will be left with the string "bcdbd". Now, removing the character
 * 'c' leaves you with a valid string "bdbd" having a length of 4. Removing
 * either 'b' or 'd' at any point would not result in a valid string.
 * 
 * Given a string "s", convert it to the longest possible string "t" made up
 * only of altrenating characters. Print the length of string "t" on a new
 * line. If no string "t" can be formed, print 0 instead.
 */

import java.util.Scanner;
import java.util.Set;
import java.util.HashSet;
import java.lang.Math;

public class Solution {

    /*
     * Given a String 's' and two chars 'c1' and 'c2', return the length of the
     * substring formed by alternating occurrences of 'c1' and 'c2'. Do this
     * without altering the string in any way. If such a string cannot be
     * constructed, return 0.
     *
     * Initially, I used a stack as follows: push instances of 'c1' and 'c2' onto
     * a stack. If the current char in the string 's' is the same as the char at
     * the top of the stack, then a string of alternating chars is not possible,
     * so return 0. This approach has O(n) time complexity and O(n) space complexity.
     *
     * Using two indices to track the latest occurrences of 'c1' and c2' is preferable
     * as it has O(n) time complexity and O(1) space complexity.
     */
    static int getLengthAlternatingChars(String s, char c1, char c2) {
        int c1PrevIndex = -1, c2PrevIndex = -1, strLength = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == c1) {
                if (c1PrevIndex > c2PrevIndex) {
                    // Two consecutive occurrences of 'c1'
                    return 0;
                }
                c1PrevIndex = i;
                strLength++;
            } else if (s.charAt(i) == c2) {
                if (c2PrevIndex > c1PrevIndex) {
                    // Two consecutive occurrences of 'c2'
                    return 0;
                }
                c2PrevIndex = i;
                strLength++;
            }
        }
        return strLength;
    }
    
    /*
     * Time complexity: O(n^2): must check every unique pair of chars.
     * Space complexity: O(n^2): the HashSet 'processedCharPairs' stores
     * O(n^2) strings.
     */
    static int getMaxLengthAlternatingCharString(String s) {
        char c1, c2;
        int maxStrLen = 0;
        Set<String> processedCharPairs = new HashSet<String>();
        for (int i = 0; i < s.length(); i++) {
            c1 = s.charAt(i);
            for (int j = i+1; j < s.length(); j++) {
                c2 = s.charAt(j);
                if (processedCharPairs.contains("" + c1 + c2)) {
                    continue;
                }
                maxStrLen = Math.max(maxStrLen, getLengthAlternatingChars(s, c1, c2));
                // Save char pairs <c1,c2> and <c2,c1> to avoid repeated computations.
                processedCharPairs.add("" + c1 + c2);
                processedCharPairs.add("" + c2 + c1);
            }
        } 
        return maxStrLen;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int l = in.nextInt();
        String str = in.next();
        System.out.println(getMaxLengthAlternatingCharString(str));
        in.close();
    }
}
