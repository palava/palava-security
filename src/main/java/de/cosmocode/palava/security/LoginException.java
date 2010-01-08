package de.cosmocode.palava.security;

/**
 * Indicates a failure while logging in.
 *
 * @author Willi Schoenborn
 */
public final class LoginException extends Exception {

    private static final long serialVersionUID = -4806127184216914657L;

    public LoginException(String message) {
        super(message);
    }
    
    public LoginException(Throwable throwable) {
        super(throwable);
    }
    
    public LoginException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
