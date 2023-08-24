package ua.javarush.encoder.brutforce;

import ua.javarush.encoder.crypto.CaesarCipher;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BrutForce {
    private CaesarCipher caesarCipher;
    private final Map<Character, Double> EN_DICT = new HashMap<>();
    {
        EN_DICT.put('a',2.0);
        EN_DICT.put('A',2.0);
    }

    public BrutForce(CaesarCipher caesarCipher) {
        this.caesarCipher = caesarCipher;
    }

    public int getBrutForсe(List<String> inputLines){
        int key = -1;


        return key;
    }
}
