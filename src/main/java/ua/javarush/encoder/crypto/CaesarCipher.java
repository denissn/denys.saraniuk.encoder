package ua.javarush.encoder.crypto;

import java.util.ArrayList;

public class CaesarCipher {

    private final ArrayList<Character> symbols;

    public CaesarCipher(ArrayList<Character> symbols) {
        this.symbols = symbols;
    }

    public String encoder(String text, int key) {
        return symbolsShift(text, key);
    }

    public String decoder(String text, int key) {
        key = symbols.size() - (key % symbols.size());
        return symbolsShift(text, key);
    }

    private String symbolsShift(String text, int key) {
        StringBuilder sb = new StringBuilder();
        for (char item : text.toCharArray()) {
            if (symbols.contains(item)) {
                sb.append(symbols.get((symbols.indexOf(item) + key) % symbols.size()));
            } else {
                sb.append(item);
            }
        }
        return sb.toString();
    }
}
