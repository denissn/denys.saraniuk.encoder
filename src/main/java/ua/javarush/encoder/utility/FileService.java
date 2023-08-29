package ua.javarush.encoder.utility;

import ua.javarush.encoder.exception.*;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.List;

public class FileService {

    public List<String> read(Path filePath) {
        List<String> lines;
        try {
            lines = Files.readAllLines(filePath);
        } catch (NoSuchFileException e) {
            throw new FileNotFoundRuntimeException(e);
        } catch (IOException e) {
            throw new FileReadRuntimeException(e);
        }
        return lines;
    }

    public void write(Path filePath, List<String> lines) {
        try {
            Files.write(filePath, lines);
        } catch (FileAlreadyExistsException e) {
            throw new FileAlreadyExistsRuntimeException(e);
        } catch (IOException e) {
            throw new FileWriteRuntimeException(e);
        }

    }
}
