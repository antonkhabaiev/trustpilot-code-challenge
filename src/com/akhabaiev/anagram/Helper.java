package com.akhabaiev.anagram;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * Created by ukhaban on 9/13/15.
 */
public class Helper {
    /**
     * Checks if needle is a subset of haystack.
     * @param needle
     * @param haystack
     * @return
     */
    public static boolean isSubset(char[] needle, char[] haystack) {
        if (needle.length > haystack.length) {
            return false;
        }

        int[] charCount = new int[256]; //char count for extended ASCII

        for(char h: haystack) {
            charCount[h]++;
        }

        for(char n: needle) {
            charCount[n]--;
            if(charCount[n] < 0)
                return false;
        }

        return true;
    }

    public static int getCurrentPhraseLength(int wordNumber, String[] words) {
        int length = 0;
        for(int i=0; i< wordNumber; i++) {
            length+=words[i].length();
            length++; //add space
        }
        return length;
    }

    /**
     * Sets all positions to 0.
     * @param array
     */
    public static void nullifyArray(int[] array) {
        Arrays.fill(array, 0);
    }

    /**
     * Sets character counts for anagram alphabet in the input array
     * @param a
     */
    public static void setAlphabet(int[] a, char[] anagramAlphabet) {
        for(char pos: anagramAlphabet) {
            a[pos] ++;
        }
    }

    /**
     * Check if we can add input word to phrase.
     * @param array
     * @param word
     * @return
     */
    public static boolean canAddWord(int[] array, String word) {
        for(char letter: word.toCharArray()) {
            array[letter]--;
            if(array[letter] < 0)
                return false;
        }
        return true;
    }

    public static String getMD5(String input) throws NoSuchAlgorithmException
    {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(input.getBytes());

        byte byteData[] = md.digest();

        //convert the byte to hex format method 1
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < byteData.length; i++)
        {
            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100,
                    16).substring(1));
        }
        return sb.toString();
    }
}
