package de.school.game.util.rendering;

public class FileOpeningException extends RuntimeException{
    public FileOpeningException(String message) {
        super(message);
    }
    public FileOpeningException() {
        super("Error at loading Image");
    }
}
