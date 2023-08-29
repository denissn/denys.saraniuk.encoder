package ua.javarush.encoder.runner;

import ua.javarush.encoder.bruteforce.BruteForce;
import ua.javarush.encoder.crypto.CaesarCipher;
import ua.javarush.encoder.exception.WrongCommandRuntimeException;
import ua.javarush.encoder.utility.Command;
import ua.javarush.encoder.utility.ConsoleProvider;
import ua.javarush.encoder.utility.FileService;
import ua.javarush.encoder.utility.LocaleAlphabet;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Runner {

    private final FileService fileService;
    private final CaesarCipher caesarCipher;
    private final LocaleAlphabet localeAlphabet;
    private final ConsoleProvider consoleProvider;

    public Runner(CaesarCipher caesarCipher, FileService fileService, ConsoleProvider consoleProvider, LocaleAlphabet localeAlphabet) {
        this.caesarCipher = caesarCipher;
        this.fileService = fileService;
        this.localeAlphabet = localeAlphabet;
        this.consoleProvider = consoleProvider;
    }

    public void run(String[] args) {
        Command command = getCommand(args);
        Path sourceFilePath = getPath(args);
        int key = getKey(args, command);

        ArrayList<String> outText = new ArrayList<>();
        List<String> inputLines = fileService.read(sourceFilePath);

        switch (command) {
            case ENCRYPT -> getEncrypt(inputLines, key, outText);
            case DECRYPT -> getDecrypt(inputLines, key, outText);
            case BRUTE_FORCE -> key = getBrutForce(inputLines, outText);
        }

        Path destinationFilePath = getDestinationFilePath(sourceFilePath, command, key);

        fileService.write(destinationFilePath, outText);
        consoleProvider.print("Command:" + command + " alphabet:" + localeAlphabet + " key:" + key + " completed.");
    }

    private Command getCommand(String[] args) {
        if (args.length > 0) {
            return normalizeCommand(args[0]);
        }
        consoleProvider.print("Select one of the commands and press enter:");
        consoleProvider.print("[E]NCRYPT | [D]ECRYPT | [B]RUTE_FORCE");
        return normalizeCommand(consoleProvider.read());
    }

    private Path getPath(String[] args) {
        if (args.length > 0) {
            return Path.of(args[1]);
        }
        consoleProvider.print("Input source file path:");
        return Path.of(consoleProvider.read());
    }

    private int getKey(String[] args, Command command) {
        if (args.length > 0) {
            if (args.length > 2 && (command == Command.ENCRYPT || command == Command.DECRYPT)) {
                return Integer.parseInt(args[2]);
            }
        } else if (command == Command.ENCRYPT || command == Command.DECRYPT) {
            consoleProvider.print("Input key:");
            return Integer.parseInt(consoleProvider.read());
        }
        return -1;
    }

    private void getEncrypt(List<String> inputLines, int key, ArrayList<String> outText) {
        for (String line : inputLines) {
            outText.add(caesarCipher.encoder(line, key));
        }
    }

    private void getDecrypt(List<String> inputLines, int key, ArrayList<String> outText) {
        for (String line : inputLines) {
            outText.add(caesarCipher.decoder(line, key));
        }
    }

    private int getBrutForce(List<String> inputLines, ArrayList<String> outText) {
        BruteForce bruteForce = new BruteForce(caesarCipher, localeAlphabet);
        int key = bruteForce.getBruteForce(inputLines);
        for (String line : inputLines) {
            outText.add(caesarCipher.decoder(line, key));
        }
        return key;
    }

    private Command normalizeCommand(String consoleInputLine) {
        if (consoleInputLine != null) {
            if (isCorrectCommand(consoleInputLine, Command.ENCRYPT)) {
                return Command.ENCRYPT;
            }
            if (isCorrectCommand(consoleInputLine, Command.DECRYPT)) {
                return Command.DECRYPT;
            }
            if (isCorrectCommand(consoleInputLine, Command.BRUTE_FORCE)) {
                return Command.BRUTE_FORCE;
            }
        }
        throw new WrongCommandRuntimeException("Invalid command entered. Check inserted command.");
    }

    private boolean isCorrectCommand(String consoleInputLine, Command command) {
        String firstCommandLetter = command.toString().substring(0, 1);
        return consoleInputLine.equalsIgnoreCase(command.toString()) || consoleInputLine.equalsIgnoreCase(firstCommandLetter);
    }

    private Path getDestinationFilePath(Path sourceFileName, Command command, int key) {
        Path sourceDirPath = sourceFileName.getParent();
        String sourceShortFileName = sourceFileName.getFileName().toString();
        String additive = command.toString();
        if (command == Command.BRUTE_FORCE) {
            additive = String.valueOf(key);
        }
        String destinationFileName = sourceShortFileName.substring(0, sourceShortFileName.lastIndexOf('.')) +
                "[" + additive + "]" +
                sourceShortFileName.substring(sourceShortFileName.lastIndexOf('.'));
        return sourceDirPath.resolve(Path.of(destinationFileName));
    }
}
