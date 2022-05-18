package Exceptions;

public class AlreadyExistsException extends Exception{

    public final String message;

    public AlreadyExistsException(String message){this.message = message;}

    @Override
    public String getMessage(){return this.message;}

}
