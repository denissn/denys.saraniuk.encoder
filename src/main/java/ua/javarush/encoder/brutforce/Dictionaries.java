package ua.javarush.encoder.brutforce;

import java.util.HashMap;
import java.util.Map;

public final class Dictionaries {

    private Dictionaries() {
    }

    private static final Map<Character, Double> EN = fillEn();
    private static final Map<Character, Double> UA = fillUa();

    public static Map<Character, Double> getDictionaryEn(){
        return EN;
    }

    public static Map<Character, Double> getDictionaryUa(){
        return UA;
    }

    private static Map<Character, Double> fillEn() {
        Map<Character, Double> en = new HashMap<>(26);
        en.put('a', 8.167);
        en.put('b', 1.492);
        en.put('c', 2.782);
        en.put('d', 4.253);
        en.put('e', 12.702);
        en.put('f', 2.228);
        en.put('g', 2.015);
        en.put('h', 6.094);
        en.put('i', 6.966);
        en.put('j', 0.253);
        en.put('k', 1.772);
        en.put('l', 4.025);
        en.put('m', 2.406);
        en.put('n', 6.749);
        en.put('o', 7.507);
        en.put('p', 1.929);
        en.put('q', 0.095);
        en.put('r', 5.987);
        en.put('s', 6.327);
        en.put('t', 9.056);
        en.put('u', 2.758);
        en.put('v', 0.978);
        en.put('w', 2.360);
        en.put('x', 0.250);
        en.put('y', 1.974);
        en.put('z', 0.074);
        return en;
    }

    private static Map<Character, Double> fillUa() {
        Map<Character, Double> ua = new HashMap<>(32);

        return ua;
    }
}
