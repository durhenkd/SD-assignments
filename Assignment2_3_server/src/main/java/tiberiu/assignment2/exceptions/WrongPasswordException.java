package tiberiu.assignment2.exceptions;

public class WrongPasswordException extends Exception {

    @Override
    public String getMessage() {
        return "Wrong Password!";
    }
}
