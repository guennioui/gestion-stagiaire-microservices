package ma.emsi.stagemicroservice.exceptions;

public class StageAlreadyExistingException extends Exception{
    public StageAlreadyExistingException(String message) {
        super(message);
    }
}
