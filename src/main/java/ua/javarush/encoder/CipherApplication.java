package ua.javarush.encoder;

import ua.javarush.encoder.crypto.CaesarCipher;
import ua.javarush.encoder.utility.ConsoleProvider;
import ua.javarush.encoder.utility.FileService;
import ua.javarush.encoder.runner.Runner;
import ua.javarush.encoder.utility.LocaleAlphabet;

public class CipherApplication {
    public static void main(String[] args) {
        FileService fileService = new FileService();
        ConsoleProvider consoleProvider = new ConsoleProvider();
        LocaleAlphabet localeAlphabet = LocaleAlphabet.UA;
        CaesarCipher caesarCipher = new CaesarCipher(localeAlphabet);
        Runner runner = new Runner(caesarCipher, fileService, consoleProvider, localeAlphabet);
        runner.run(args);
    }
}
