package tiberiu.assignment2.exceptions;

public class InvalidRegisterInputException extends Exception {

    private final String reason;

    public InvalidRegisterInputException(String reason) {
        this.reason = reason;
    }

    @Override
    public String getMessage() {
        return reason;
    }
}
