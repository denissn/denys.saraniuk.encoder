package ua.javarush.encoder;

import ua.javarush.encoder.crypto.CaesarCipher;
import ua.javarush.encoder.utility.FileService;
import ua.javarush.encoder.runner.Runner;
import ua.javarush.encoder.utility.LocaleAlphabet;

public class CipherApplication {
    public static void main(String[] args) {
        FileService fileService = new FileService();
        LocaleAlphabet localeAlphabet = LocaleAlphabet.EN;

        CaesarCipher caesarCipher = new CaesarCipher(localeAlphabet);

        Runner runner = new Runner(caesarCipher, fileService, localeAlphabet);
        runner.run(args);
    }
}
