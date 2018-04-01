/*
 * Solution to the HackerRank problem: Super Reduced String
 * https://www.hackerrank.com/challenges/reduced-string/problem
 *
 * Problem:
 * 
 * Steve has a string 's', consisting of n lowercase English alphabetic letters.
 * In one operation, he can delete any pair of adjacent letters with the same 
 * value. For example, string "aabcc" would become either "aab" or "bcc" after
 * 1 operation.
 *
 * Steve wants to reduce 's' as much as possible. To do this, he will repeat
 * the above operation as many times as it can be performed. Help Steve out
 * by finding and printing 's' in its non-reducible form!
 * 
 * Note: If the final string is empty, print "Empty String".
 *
 * Sample Input: aaabccddd
 * Sample Output: abd
 *
 * Explanation:
 *
 * Steve can perform the following sequence of operations to get the final
 * string:
 *
 * aaabccddd → abccddd
 * abccddd → abddd
 * abddd → abd
 *
 * Thus, we print abd.
 */

import java.util.Scanner;
import java.util.Stack;

public class Solution {
    
    /*
     * Runtime complexity: O(n) - each char is pushed onto the stack at most
     * once; and once on the stack, each char is popped exactly once.
     *
     * Space complexity: O(n) - the stack and char array each take up O(n) space.
     */
    static String superReducedString(String s) {
        if (s == null) {
            throw new IllegalArgumentException("Null string is invalid input");
        }
        Stack<Character> charStack = new Stack<Character>();
        for (int i = 0; i < s.length(); i++) {
            if (charStack.empty() || s.charAt(i) != charStack.peek()) {
                charStack.push(s.charAt(i));
            } else {
                charStack.pop();
            }
        }
        if (charStack.empty()) {
            return "Empty String";
        } else {
            char[] chars = new char[charStack.size()];
            for (int j = chars.length-1; j >= 0; j--) {
                chars[j] = charStack.pop();
            }
            return new String(chars);
        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String s = in.next();
        String result = superReducedString(s);
        System.out.println(result);
        in.close();
    }
}
