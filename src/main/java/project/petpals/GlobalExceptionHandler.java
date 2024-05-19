package project.petpals;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import project.petpals.exceptions.ConflictException;
import project.petpals.exceptions.NotFoundException;

import java.nio.file.AccessDeniedException;


@RestController
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public String handleNotfound(NotFoundException ex1){return ex1.getMessage();}

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(ConflictException.class)
    public String handleConfict(ConflictException ex2){return ex2.getMessage();}

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public String handleAccessDenied(AccessDeniedException ex3){return ex3.getMessage();}
}
