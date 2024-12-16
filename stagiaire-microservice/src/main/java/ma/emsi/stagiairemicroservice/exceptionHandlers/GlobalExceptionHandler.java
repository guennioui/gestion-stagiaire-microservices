package ma.emsi.stagiairemicroservice.exceptionHandlers;

import ma.emsi.stagiairemicroservice.controllers.StagiaireController;
import ma.emsi.stagiairemicroservice.errors.ErrorResponse;
import ma.emsi.stagiairemicroservice.exceptions.StagiaireAlreadyExistException;
import ma.emsi.stagiairemicroservice.exceptions.StagiaireNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice(assignableTypes = StagiaireController.class)
// we can create a dedicated exception handler class for each controller by using @ControllerAdvice with a specific controller class target.
// like StagiaireExceptionHandler which has @ControllerAdvice(assignableTypes = StagiaireController.class) annotation
public class GlobalExceptionHandler {

    @ExceptionHandler(value = StagiaireAlreadyExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public @ResponseBody ErrorResponse HandleStagiaireAlreadyExistException(StagiaireAlreadyExistException ex){
        return new ErrorResponse(HttpStatus.CONFLICT.value(), ex.getMessage());
    }

    @ExceptionHandler(value = StagiaireNotFoundException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public @ResponseBody ErrorResponse HandleStagiaireNotFoundException(StagiaireNotFoundException ex){
        return new ErrorResponse(HttpStatus.CONFLICT.value(), ex.getMessage());
    }
}
