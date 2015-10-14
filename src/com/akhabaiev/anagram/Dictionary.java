package com.akhabaiev.anagram;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

/**
 * Created by Anton Khabaiev on 9/14/15.
 */
public class Dictionary {
    public static Map<Integer, List<String>> createRestrictedDictionaryFromList(List<String> grossary, String anagramString) throws IOException {
        Map<Integer, List<String>> words = createRestrictedDictionary(grossary.stream(), anagramString);
        return words;
    }

    public static Map<Integer, List<String>> createRestrictedDictionaryFromPath(String path, String anagramString) throws IOException {
        Stream<String> linesStream = Files.lines(Paths.get(path));
        Map<Integer, List<String>> words = createRestrictedDictionary(linesStream, anagramString);
        linesStream.close();
        return words;
    }

    private static Map<Integer, List<String>> createRestrictedDictionary(Stream<String> stream, String anagramString) {
        Map<Integer, List<String>> words = new HashMap<>();
        Set<Integer> allowedLength = new HashSet<>();
        String[] anagramParts = anagramString.split(" ");
        for(String part : anagramParts)
            allowedLength.add(part.length());
        char[] anagramAlphabet = anagramString.replace("\\s","").toCharArray();

        stream.forEach(word -> {
            int key = word.length();
            if (allowedLength.contains(key)
                    && Helper.isSubset(word.toCharArray(), anagramAlphabet)) {
                List<String> wordList;
                if (words.containsKey(key))
                    wordList = words.get(key);
                else {
                    wordList = new ArrayList<String>();
                    words.put(key, wordList);
                }
                wordList.add(word);
            }
        });

        return words;
    }

    public static List<String> createDictionary(String path) throws IOException {
        List<String> dictionary = new ArrayList<>();
        Stream<String> linesStream = Files.lines(Paths.get(path));
        linesStream.forEach(dictionary::add);
        linesStream.close();

        return dictionary;
    }
}
