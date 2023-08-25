package ua.javarush.encoder.exception;

public class WrongCommandRuntimeException extends RuntimeException {
    public WrongCommandRuntimeException(String message) {
        super(message);
    }
}
