package fr.epita.trackmoviesback.exception;

public class UtilisateurNonLoggeException extends RuntimeException{
    public UtilisateurNonLoggeException() {
        super();
    }

    public UtilisateurNonLoggeException(String message) {
        super(message);
    }
}
