package ua.javarush.encoder.utility;

import ua.javarush.encoder.crypto.CaesarCipher;
import ua.javarush.encoder.exception.WrongCommandRuntimeException;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Runner {

    private final CaesarCipher caesarCipher;

    private final FileService fileService;

    public Runner(CaesarCipher caesarCipher, FileService fileService) {
        this.caesarCipher = caesarCipher;
        this.fileService = fileService;
    }

    public void run(String[] args) {
        ConsoleProvider consoleProvider = new ConsoleProvider();
        Command command;
        Path sourceFilePath;
        int key = 0;

        if (args.length > 0) {
            command = Command.valueOf(args[0]);
            sourceFilePath = Path.of(args[1]);
            if (args.length > 2) {
                key = Integer.parseInt(args[2]);
            }
        } else {
            consoleProvider.print("Select one of the commands and press enter");
            consoleProvider.print("[e]ncrypt | [d]ecrypt | [b]ryte force");
            command = commandNormalize(consoleProvider.read());
            consoleProvider.print("Input source file path.");
            sourceFilePath = Path.of(consoleProvider.read());
            if (command == Command.ENCRYPT || command == Command.DECRYPT) {
                consoleProvider.print("Input key.");
                key = consoleProvider.readInt();
            }
        }

        ArrayList<String> outText = new ArrayList<>();
        switch (command) {
            case ENCRYPT -> getEncrypt(sourceFilePath, key, outText);
            case DECRYPT -> getDecrypt(sourceFilePath, key, outText);
            case BRUTE_FORCE -> getBrutForce(sourceFilePath, outText);
        }

        Path destinationFilePath = getDestinationFilePath(sourceFilePath, command);
        fileService.write(destinationFilePath, outText);

        consoleProvider.print("Command " + command + " completed.");

    }

    private void getEncrypt(Path sourceFilePath, int key, ArrayList<String> outText) {
        List<String> inputLines = fileService.read(sourceFilePath);
        for (String line : inputLines) {
            outText.add(caesarCipher.encoder(line, key));
        }
    }

    private void getDecrypt(Path sourceFilePath, int key, ArrayList<String> outText) {
        List<String> inputLines = fileService.read(sourceFilePath);
        for (String line : inputLines) {
            outText.add(caesarCipher.decoder(line, key));
        }
    }

    private void getBrutForce(Path sourceFilePath, ArrayList<String> outText) {
        throw new RuntimeException("BRUTE_FORCE in construction...");
        /*List<String> inputLines = fileService.read(sourceFilePath);
        BruteForce bruteForce = new BruteForce(caesarCipher);
        int key = bruteForce.getBruteForce(inputLines);
        for (String line : inputLines) {
            outText.add(caesarCipher.decoder(line, key));
        }*/
    }

    private Command commandNormalize(String command) {
        if (command == null) {
            throw new WrongCommandRuntimeException();
        }
        if (command.equalsIgnoreCase(Command.ENCRYPT.toString()) || command.equalsIgnoreCase("e")) {
            return Command.ENCRYPT;
        } else if (command.equalsIgnoreCase(Command.DECRYPT.toString()) || command.equalsIgnoreCase("d")) {
            return Command.DECRYPT;
        } else if (command.equalsIgnoreCase(Command.BRUTE_FORCE.toString()) || command.equalsIgnoreCase("b")) {
            return Command.BRUTE_FORCE;
        } else {
            throw new WrongCommandRuntimeException();
        }
    }

    private Path getDestinationFilePath(Path sourceFileName, Command command) {
        Path sourceDirPath = sourceFileName.getParent();
        String sourceShortFileName = sourceFileName.getFileName().toString();
        String destinationFileName = sourceShortFileName.substring(0, sourceShortFileName.lastIndexOf('.')) +
                "[" + command + "]" +
                sourceShortFileName.substring(sourceShortFileName.lastIndexOf('.'));
        return sourceDirPath.resolve(Path.of(destinationFileName));
    }
}
