package fr.epita.trackmoviesback.exception;

public class MauvaisParamException extends RuntimeException{
    public MauvaisParamException() {
        super();
    }

    public MauvaisParamException(String message) {
        super(message);
    }
}
