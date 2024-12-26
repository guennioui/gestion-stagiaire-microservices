package ma.emsi.departementmicroservice.handlers;

import ma.emsi.departementmicroservice.controllers.DepartementController;
import ma.emsi.departementmicroservice.errors.ErrorResponse;
import ma.emsi.departementmicroservice.exceptions.DepartementAlreadyExistException;
import ma.emsi.departementmicroservice.exceptions.DepartementNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice(assignableTypes = DepartementController.class)
public class DepartementExceptionHandler {
    @ExceptionHandler(value = DepartementNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody ErrorResponse handlingDepartementNotFoundException(DepartementNotFoundException ex){
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }

    @ExceptionHandler(value = DepartementAlreadyExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public @ResponseBody ErrorResponse handlingDepartementAlreadyExistingException(DepartementAlreadyExistException ex){
        return new ErrorResponse(HttpStatus.CONFLICT.value(), ex.getMessage());
    }
}
