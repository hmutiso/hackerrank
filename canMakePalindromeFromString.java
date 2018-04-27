import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;

public class Solution {
    /*
     * Problem:
     *
     * Dothraki are planning an attack to usurp King Robert's throne. King Robert
     * learns of this conspiracy from Raven and plans to lock the single door
     * through which the enemy can enter his kingdom.
     * But, to lock the door he needs a key that is an anagram of a palindrome. He
     * starts to go through his box of strings, checking to see if they can be
     * rearranged into a palindrome. Complete the GameOfThrones function below to
     * determine whether a given string can be rearranged into a palindrome.
     *
     * [Note] I've renamed the gameOfThrones() function to:
     * canReshuffleStringToMakePalindrome(). This function returns "YES" if the
     * input string's chars can be reshuffled into a string that is a palindrome,
     * and "NO" otherwise.
     *
     * Runtime complexity: O(n), where n is the length of string s.
     * Space complexity: O(n) : charCntMap potentially stores all chars of the
     * input string 's'.
     */
    static String canReshuffleStringToMakePalindrome(String s) {
        boolean stringHasOddLength = (s.length() % 2 == 1);
        int charsWithOddCnt = 0, charCnt = 0;
        Map<Character, Integer> charCntMap = new HashMap<Character, Integer>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (charCntMap.containsKey(c))
                charCnt = charCntMap.get(c) + 1;
            else
                charCnt = 1;
            charCntMap.put(c, charCnt);
            if (charCnt % 2 == 1)
                charsWithOddCnt++;
            else
                charsWithOddCnt--;
        }
        if (stringHasOddLength)
            // At most one char can have an odd count
            return (charsWithOddCnt <= 1) ? "YES" : "NO";
        else
            // No char can have an odd count
            return (charsWithOddCnt == 0) ? "YES" : "NO";
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String s = in.next();
        String result = canReshuffleStringToMakePalindrome(s);
        System.out.println(result);
    }
}
