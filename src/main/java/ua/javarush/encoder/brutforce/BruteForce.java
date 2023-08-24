package ua.javarush.encoder.brutforce;

import ua.javarush.encoder.crypto.CaesarCipher;

import java.util.*;

public class BruteForce {
    private CaesarCipher caesarCipher;
    private final Map<Character, Double> DICT = new HashMap<>();

    {
        DICT.put('a', 8.167);
        DICT.put('b', 1.492);
        DICT.put('c', 2.782);
        DICT.put('d', 4.253);
        DICT.put('e', 12.702);
        DICT.put('f', 2.228);
        DICT.put('g', 2.015);
        DICT.put('h', 6.094);
        DICT.put('i', 6.966);
        DICT.put('j', 0.253);
        DICT.put('k', 1.772);
        DICT.put('l', 4.025);
        DICT.put('m', 2.406);
        DICT.put('n', 6.749);
        DICT.put('o', 7.507);
        DICT.put('p', 1.929);
        DICT.put('q', 0.095);
        DICT.put('r', 5.987);
        DICT.put('s', 6.327);
        DICT.put('t', 9.056);
        DICT.put('u', 2.758);
        DICT.put('v', 0.978);
        DICT.put('w', 2.360);
        DICT.put('x', 0.250);
        DICT.put('y', 1.974);
        DICT.put('z', 0.074);
    }

    public BruteForce(CaesarCipher caesarCipher) {
        this.caesarCipher = caesarCipher;
    }

    public int getBruteForce(List<String> inputLines) {
        int key = -1;
        double keyMinDiff = Integer.MAX_VALUE;
        for (int i = 0; i < DICT.size(); i++) { //by all key
            Map<Character, Integer> charsCount = new HashMap<>();
            int count =0;
            for (String line : inputLines) {
                String decodedLine = caesarCipher.decoder(line, i).toLowerCase();
                for (char item : decodedLine.toCharArray()) {
                    if (DICT.containsKey(item)) {
                        count++;
                        if (charsCount.containsKey(item)) {
                            charsCount.put(item, charsCount.get(item) + 1);
                        }else {
                            charsCount.put(item, 1);
                        }
                    }
                }
            }
            double localDiff = 0;
            for (Map.Entry<Character, Integer> entry : charsCount.entrySet()) {
                double diffChar = Math.abs(DICT.get(entry.getKey()) - ((entry.getValue() * 1.0) / count));
                localDiff += diffChar;
            }
            localDiff = localDiff/charsCount.size();
            if (localDiff > 0 && keyMinDiff > localDiff) {
                keyMinDiff = localDiff;
                key = i;
            }
        }
        System.out.println("Brute force key:" + key);
        return key;
    }
}
