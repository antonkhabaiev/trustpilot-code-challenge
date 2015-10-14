package com.akhabaiev.anagram;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by Anton Khabaiev on 9/14/15.
 */
public class Solver {
    Map<Integer, List<String>> words;
    String[] anagramParts;
    char[] anagramAlphabet;

    public Solver(Map<Integer, List<String>> words, String anagramPhrase) {
        this.words = words;
        anagramParts = anagramPhrase.split(" ");
        anagramAlphabet = anagramPhrase.replace("\\s","").toCharArray();
    }

    /**
     * Find correct phrase for input anagram that consists of 3 words.
    */

    public String findSecretPhraseRecursive(String correctHash) throws NoSuchAlgorithmException {
        if(anagramParts.length == 1 && anagramParts[0].equals(""))
            return "";

        StringBuilder phraseBuilder = new StringBuilder();
        int[] charCounter = new int[256];
        Helper.setAlphabet(charCounter, this.anagramAlphabet);
        return recurse(0, charCounter, phraseBuilder, correctHash);
    }

    private String recurse(int wordNumber, int[] charCounter, StringBuilder phraseBuilder, String correctHash) throws NoSuchAlgorithmException {
        if(wordNumber == anagramParts.length) {
            String match = phraseBuilder.toString();
            String md5 = Helper.getMD5(match);
            if(md5.equals(correctHash))
                return match;
            else
                return "";
        }

        for(String word: words.get(anagramParts[wordNumber].length())) {
            int[] charCounterCopy = Arrays.copyOf(charCounter, charCounter.length);
            phraseBuilder.setLength(Helper.getCurrentPhraseLength(wordNumber, anagramParts));

            if(Helper.canAddWord(charCounterCopy, word)) {
                phraseBuilder.append(word);
                if(wordNumber < anagramParts.length - 1)
                    phraseBuilder.append(" ");
                String match = recurse(wordNumber+1, charCounterCopy, phraseBuilder, correctHash);
                if(!match.equals(""))
                    return match;
            }
        }

        return "";
    }


}
