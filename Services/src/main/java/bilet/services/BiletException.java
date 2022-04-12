package bilet.services;

public class BiletException extends Exception{
    public BiletException() {}

    public BiletException(String message) {
        super(message);
    }

    public BiletException(String message, Throwable cause) {
        super(message, cause);}
}
