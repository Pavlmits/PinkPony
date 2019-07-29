package exception;

public class UnknownParameterException extends Exception {

    public UnknownParameterException(final String errorMessage){
        super(errorMessage);
    }
}
