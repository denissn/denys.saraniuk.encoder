package ua.javarush.encoder.bruteforce;

import ua.javarush.encoder.utility.LocaleAlphabet;

import java.util.HashMap;
import java.util.Map;

public final class Dictionaries {

    private Dictionaries() {
    }

    private static final Map<Character, Double> EN = setEn();
    private static final Map<Character, Double> UA = setUa();

    public static Map<Character, Double> getDictionary(LocaleAlphabet localeAlphabet) {
        Map<Character, Double> dictionary;
        switch (localeAlphabet) {
            case UA, UA_WITH_SYMBOLS -> dictionary = UA;
            default -> dictionary = EN;
        }
        return dictionary;
    }

    private static Map<Character, Double> setEn() {
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

    private static Map<Character, Double> setUa() {
        Map<Character, Double> ua = new HashMap<>(33);
        ua.put('а', 0.064);
        ua.put('б', 0.013);
        ua.put('в', 0.046);
        ua.put('г', 0.013);
        ua.put('ґ', 0.0001);
        ua.put('д', 0.027);
        ua.put('е', 0.042);
        ua.put('є', 0.005);
        ua.put('ж', 0.007);
        ua.put('з', 0.020);
        ua.put('и', 0.055);
        ua.put('і', 0.044);
        ua.put('ї', 0.010);
        ua.put('й', 0.009);
        ua.put('к', 0.033);
        ua.put('л', 0.027);
        ua.put('м', 0.029);
        ua.put('н', 0.068);
        ua.put('о', 0.086);
        ua.put('п', 0.025);
        ua.put('р', 0.043);
        ua.put('с', 0.037);
        ua.put('т', 0.045);
        ua.put('у', 0.027);
        ua.put('ф', 0.003);
        ua.put('х', 0.011);
        ua.put('ц', 0.010);
        ua.put('ч', 0.011);
        ua.put('ш', 0.005);
        ua.put('щ', 0.004);
        ua.put('ь', 0.016);
        ua.put('ю', 0.008);
        ua.put('я', 0.019);
        return ua;
    }
}
