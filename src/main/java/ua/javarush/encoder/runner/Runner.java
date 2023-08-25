package ua.javarush.encoder.runner;

import ua.javarush.encoder.brutforce.BruteForce;
import ua.javarush.encoder.brutforce.Dictionaries;
import ua.javarush.encoder.crypto.CaesarCipher;
import ua.javarush.encoder.exception.WrongCommandRuntimeException;
import ua.javarush.encoder.utility.Command;
import ua.javarush.encoder.utility.ConsoleProvider;
import ua.javarush.encoder.utility.FileService;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Runner {

    private final CaesarCipher caesarCipher;
    private final FileService fileService;
    private final ConsoleProvider consoleProvider = new ConsoleProvider();

    public Runner(CaesarCipher caesarCipher, FileService fileService) {
        this.caesarCipher = caesarCipher;
        this.fileService = fileService;
    }

    public void run(String[] args) {
        Command command;
        Path sourceFilePath;
        int key;

        if (args.length > 0) {
            command = getCommandFromParams(args[0]);
            sourceFilePath = getPathFromPrams(args[1]);
            key = getKeyFromPrams(args[2], command);
        } else {
            command = getCommandFromConsole();
            sourceFilePath = getPathFromConsole();
            key = getKeyFromConsole(command);
        }

        ArrayList<String> outText = new ArrayList<>();
        List<String> inputLines = fileService.read(sourceFilePath);

        switch (command) {
            case ENCRYPT -> getEncrypt(inputLines, key, outText);
            case DECRYPT -> getDecrypt(inputLines, key, outText);
            case BRUTE_FORCE -> getBrutForce(inputLines, outText);
        }

        Path destinationFilePath = getDestinationFilePath(sourceFilePath, command, key);

        fileService.write(destinationFilePath, outText);
        consoleProvider.print("Command " + command + " completed.");
    }

    private Command getCommandFromParams(String argument) {
        return normalizeCommand(argument);
    }

    private Path getPathFromPrams(String argument) {
        return Path.of(argument);
    }

    private int getKeyFromPrams(String argument, Command command) {
        if (argument != null && (command == Command.ENCRYPT || command == Command.DECRYPT)) {
            return Integer.parseInt(argument);
        }
        return -1;
    }

    private Command getCommandFromConsole() {
        consoleProvider.print("Select one of the commands and press enter:");
        consoleProvider.print("[E]NCRYPT | [D]ECRYPT | [B]RUTE_FORCE");
        return normalizeCommand(consoleProvider.read());
    }

    private Path getPathFromConsole() {
        consoleProvider.print("Input source file path:");
        return Path.of(consoleProvider.read());
    }

    private int getKeyFromConsole(Command command) {
        if (command == Command.ENCRYPT || command == Command.DECRYPT) {
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

    private void getBrutForce(List<String> inputLines, ArrayList<String> outText) {
        BruteForce bruteForce = new BruteForce(caesarCipher, Dictionaries.getDictionaryEn());
        int key = bruteForce.getBruteForce(inputLines);
        for (String line : inputLines) {
            outText.add(caesarCipher.decoder(line, key));
        }
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
