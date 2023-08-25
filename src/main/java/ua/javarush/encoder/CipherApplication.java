package ua.javarush.encoder;

import ua.javarush.encoder.crypto.CaesarCipher;
import ua.javarush.encoder.utility.FileService;
import ua.javarush.encoder.alphabet.Alphabets;
import ua.javarush.encoder.runner.Runner;

public class CipherApplication {
    public static void main(String[] args) {
        FileService fileService = new FileService();
        CaesarCipher caesarCipher = new CaesarCipher(Alphabets.getEn());
        Runner runner = new Runner(caesarCipher, fileService);
        runner.run(args);
    }
}
