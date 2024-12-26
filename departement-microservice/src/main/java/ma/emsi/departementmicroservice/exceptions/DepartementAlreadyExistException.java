package ma.emsi.departementmicroservice.exceptions;

public class DepartementAlreadyExistException extends Exception{
    public DepartementAlreadyExistException(String message) {
        super(message);
    }
}
