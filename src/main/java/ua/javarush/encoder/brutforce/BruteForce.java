package ua.javarush.encoder.brutforce;

import ua.javarush.encoder.crypto.CaesarCipher;

import java.util.*;

public class BruteForce {
    private final CaesarCipher caesarCipher;
    private Map<Character, Double> dictionary;

    public BruteForce(CaesarCipher caesarCipher, Map<Character, Double> dictionary) {
        this.caesarCipher = caesarCipher;
        this.dictionary = dictionary;
    }

    public int getBruteForce(List<String> inputLines) {
        int key = -1;
        double keyMinDiff = Integer.MAX_VALUE;
        for (int i = 0; i < dictionary.size(); i++) { //by all key
            Map<Character, Integer> charsCount = new HashMap<>();
            int count = 0;
            for (String line : inputLines) {
                String decodedLine = caesarCipher.decoder(line, i).toLowerCase();
                for (char item : decodedLine.toCharArray()) {
                    if (dictionary.containsKey(item)) {
                        count++;
                        if (charsCount.containsKey(item)) {
                            charsCount.put(item, charsCount.get(item) + 1);
                        } else {
                            charsCount.put(item, 1);
                        }
                    }
                }
            }
            double localDiff = 0;
            for (Map.Entry<Character, Integer> entry : charsCount.entrySet()) {
                double diffChar = Math.abs(dictionary.get(entry.getKey()) - ((entry.getValue() * 1.0) / count));
                localDiff += diffChar;
            }
            localDiff = localDiff / charsCount.size();
            if (localDiff > 0 && keyMinDiff > localDiff) {
                keyMinDiff = localDiff;
                key = i;
            }
        }
        return key;
    }
}
