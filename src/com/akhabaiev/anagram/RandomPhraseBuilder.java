package com.akhabaiev.anagram;

import com.sun.deploy.util.StringUtils;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by Anton Khabaiev on 9/14/15.
 */
public class RandomPhraseBuilder {
    List<String> grossary;
    Map<Integer, List<String>> dictionary;

    public RandomPhraseBuilder(String path) throws IOException {
        grossary = Dictionary.createDictionary(path);
    }

    public String buildRandomPhrase(int minWordCount, int maxWordCount) {
        StringBuilder randomPhrase = new StringBuilder();
        int wordCount = getRandom(minWordCount, maxWordCount); // word count between 0 and maxWordCount
        System.out.println("word count: "+ wordCount);

        for(int i=0; i< wordCount; i++) {
            int randPos = getRandom(0, grossary.size());
            randomPhrase.append(grossary.get(randPos));
            if(i != wordCount-1)
                randomPhrase.append(" ");
        }

        return randomPhrase.toString();
    }

    private int getRandom(int start, int end) {
        return (int)(Math.random()*(end-start)) + start;
    }

    public String buildAnagramPhraseRecursive(String phrase) throws IOException {
        if(phrase.length() < 1)
            return "";
        dictionary = Dictionary.createRestrictedDictionaryFromList(grossary, phrase);
        StringBuilder phraseBuilder = new StringBuilder();
        int[] charCounter = new int[256];
        Helper.setAlphabet(charCounter, phrase.toCharArray());
        return recurse(0, charCounter, phraseBuilder, phrase.split(" "));
    }

    private String recurse(int wordNumber, int[] charCounter, StringBuilder phraseBuilder, String[] anagramParts) {
        if(wordNumber == anagramParts.length) {
            String match = phraseBuilder.toString();
            if(match.equals(String.join(" ", anagramParts)))
                return "";
            return phraseBuilder.toString();
        }

        for(String word: dictionary.get(anagramParts[wordNumber].length())) {
            int[] charCounterCopy = Arrays.copyOf(charCounter, charCounter.length);
            phraseBuilder.setLength(Helper.getCurrentPhraseLength(wordNumber, anagramParts));

            if(Helper.canAddWord(charCounterCopy, word)) {
                phraseBuilder.append(word);
                if(wordNumber < anagramParts.length - 1)
                    phraseBuilder.append(" ");
                String match = recurse(wordNumber+1, charCounterCopy, phraseBuilder, anagramParts);
                if(!match.equals(""))
                    return match;
            }
        }

        return "";
    }
}
