package Exceptions;

public class InvalidRegisterInputException extends Exception{

    private final String reason;

    public InvalidRegisterInputException(String reason) {
        this.reason = reason;
    }

    @Override
    public String getMessage() {return reason;}
}
