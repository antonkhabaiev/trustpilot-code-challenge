package com.akhabaiev;

import com.akhabaiev.anagram.Dictionary;
import com.akhabaiev.anagram.Helper;
import com.akhabaiev.anagram.RandomPhraseBuilder;
import com.akhabaiev.anagram.Solver;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Main {

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
	    String anagram = "poultry outwits ants";
        String correctHash = "4624d200580677270a54ccff86b9610e";

        System.out.println("testing...");
        RandomPhraseBuilder randomPhraseBuilder = new RandomPhraseBuilder("wordlist");
        for(int i=0; i< 10; i++) {
            System.out.println("running tests no: "+ i);
            String randomPhrase = randomPhraseBuilder.buildRandomPhrase(3, 4);
            System.out.println("random phrase[after build]: " + randomPhrase);
            String md5 = Helper.getMD5(randomPhrase);
            System.out.println("md5 of random phrase: " + md5);
            String randomAnagram = randomPhraseBuilder.buildAnagramPhraseRecursive(randomPhrase);
            System.out.println("anagram of random phrase: " + randomAnagram);

            long start = System.currentTimeMillis();
            Map<Integer, List<String>> restrictedDictionary = Dictionary.createRestrictedDictionaryFromPath("wordlist", randomAnagram);
            Solver solver = new Solver(restrictedDictionary, randomAnagram);
            String result = solver.findSecretPhraseRecursive(md5);
            System.out.println("Result: [" + result + "]");
            long end = System.currentTimeMillis();
            long tDelta = end - start;
            double elapsedSeconds = tDelta / 1000.0;
            System.out.println("Elapsed seconds: " + elapsedSeconds);
        }
    }
}
