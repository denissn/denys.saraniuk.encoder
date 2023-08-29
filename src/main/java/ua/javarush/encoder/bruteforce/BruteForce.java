package ua.javarush.encoder.bruteforce;

import ua.javarush.encoder.crypto.CaesarCipher;
import ua.javarush.encoder.utility.LocaleAlphabet;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class BruteForce {
    private final CaesarCipher caesarCipher;
    private final Map<Character, Double> dictionary;

    public BruteForce(CaesarCipher caesarCipher, LocaleAlphabet localeAlphabet) {
        this.caesarCipher = caesarCipher;
        this.dictionary = Dictionaries.getDictionary(localeAlphabet);
    }

    public int getBruteForce(List<String> inputLines) {
        int key = -1;
        double minDiff = Integer.MAX_VALUE;
        for (int i = 0; i < dictionary.size(); i++) { //by all key
            Map<Character, Integer> charsByKey = getCharsMapByKey(inputLines, i);
            double localDiff = getDiffForChar(charsByKey);
            if (localDiff >= 0 && minDiff > localDiff) {
                minDiff = localDiff;
                key = i;
            }
        }
        return key;
    }

    private double getDiffForChar(Map<Character, Integer> charsByKey) {
        char charWithMaxFrequency = 0;
        Map<Character, Double> tempDictionary = new HashMap<>(dictionary);
        tempDictionary.keySet().retainAll(charsByKey.keySet());
        charsByKey.keySet().retainAll(tempDictionary.keySet());
        int charsAmount = charsByKey.values().stream().mapToInt(Integer::intValue).sum();
        double localDiff = -1;
        if (!tempDictionary.isEmpty()) {
            charWithMaxFrequency = Collections.max(tempDictionary.entrySet(), Map.Entry.comparingByValue()).getKey();
            localDiff = Math.abs(tempDictionary.get(charWithMaxFrequency) - ((charsByKey.get(charWithMaxFrequency) * 1.0) / charsAmount));
        }
        return localDiff;
    }

    private Map<Character, Integer> getCharsMapByKey(List<String> inputLines, int i) {
        Map<Character, Integer> charsByKey = new HashMap<>();
        for (String line : inputLines) {
            String decodedLine = caesarCipher.decoder(line, i).toLowerCase();
            for (char item : decodedLine.toCharArray()) {
                charsByKey.merge(item, 1, Integer::sum);
            }
        }
        return charsByKey;
    }
}
