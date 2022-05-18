package tiberiu.assignment2.exceptions;

public class AlreadyExistsException extends Exception {

    public final String message;

    public AlreadyExistsException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

}
