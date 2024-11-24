package ma.emsi.encadrantmicroservice.exceptionHandler;

import ma.emsi.encadrantmicroservice.controllers.EncadrantController;
import ma.emsi.encadrantmicroservice.errors.ErrorResponse;
import ma.emsi.encadrantmicroservice.exceptions.EncadrantAlreadyExistException;
import ma.emsi.encadrantmicroservice.exceptions.EncadrantNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
@ControllerAdvice(assignableTypes = EncadrantController.class)
public class EncadrantExceptionHandler {
    @ExceptionHandler(value = EncadrantNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody ErrorResponse handlingStageNotFoundException(EncadrantNotFoundException ex){
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }

    @ExceptionHandler(value = EncadrantAlreadyExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public @ResponseBody ErrorResponse handlingStageAlreadyExistingException(EncadrantAlreadyExistException ex){
        return new ErrorResponse(HttpStatus.CONFLICT.value(), ex.getMessage());
    }
}
