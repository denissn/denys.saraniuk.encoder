package ua.javarush.encoder;

import ua.javarush.encoder.crypto.CaesarCipher;
import ua.javarush.encoder.utility.FileService;
import ua.javarush.encoder.alphabet.Alphabet;
import ua.javarush.encoder.utility.Runner;

public class Cipher {
    public static void main(String[] args) {
        FileService fileService = new FileService();
        CaesarCipher caesarCipher = new CaesarCipher(Alphabet.EN);
        Runner runner = new Runner(caesarCipher, fileService);
        runner.run(args);
    }
}
