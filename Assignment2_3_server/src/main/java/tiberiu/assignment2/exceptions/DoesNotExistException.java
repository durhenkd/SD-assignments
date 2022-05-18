package tiberiu.assignment2.exceptions;

public class DoesNotExistException extends Exception {

    private final String message;

    public DoesNotExistException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
