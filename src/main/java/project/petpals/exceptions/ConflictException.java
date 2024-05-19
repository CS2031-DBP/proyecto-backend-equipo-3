package project.petpals.exceptions;

public class ConflictException extends RuntimeException{
    public ConflictException(String message){
        super(message);
    }
}
