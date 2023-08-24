package ua.javarush.encoder.utility;

import ua.javarush.encoder.exceptions.FileExistRuntimeException;
import ua.javarush.encoder.exceptions.FileNotFoundRuntimeException;
import ua.javarush.encoder.exceptions.FileWriteRuntimeException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileService {

    public List<String> read(Path filePath) {
        List<String> lines;
        try {
            lines = Files.readAllLines(filePath);
        } catch (IOException e) {
            throw new FileNotFoundRuntimeException();
        }
        return lines;
    }

    public void write(Path filePath, List<String> lines) {
        if (Files.notExists(filePath)) {
            try {
                Files.write(filePath, lines);
            } catch (IOException e) {
                throw new FileWriteRuntimeException();
            }
        } else {
            throw new FileExistRuntimeException();
        }
    }
}
