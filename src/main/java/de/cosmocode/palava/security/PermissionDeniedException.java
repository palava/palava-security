package de.cosmocode.palava.security;

/**
 * Indicates that a required permission was denied.
 *
 * @author Willi Schoenborn
 */
public final class PermissionDeniedException extends SecurityException {

    private static final long serialVersionUID = -7075664618315680981L;

    public PermissionDeniedException(String message) {
        super(message);
    }
    
    public PermissionDeniedException(Throwable throwable) {
        super(throwable);
    }
    
    public PermissionDeniedException(Permission permission) {
        super("Permission denied: " + permission);
    }
    
    public PermissionDeniedException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
