package de.cosmocode.palava.security;

import javax.security.auth.login.LoginException;

import de.cosmocode.palava.Service;

/**
 * A {@link Service} used to handle authentication and
 * authorization based on interfaces.
 * 
 * <p>
 *   Consider specifing an application specific interface on
 *   top of this to restrict the return value to your
 *   application specific type.
 * </p>
 *
 * @author Willi Schoenborn
 */
public interface SecurityService extends Service {

    /**
     * Attempts to login using provided credentials.
     * 
     * @param credentials the credentials
     * @return a {@link Subject} representing a user
     * @throws LoginException if login failed
     */
    Subject login(Credentials credentials) throws LoginException;
    
    /**
     * Checks whether the given {@link Subject} has the permission.
     * 
     * @param subject the subject
     * @param permission the permission
     * @throws PermissionDeniedException if permission was denied
     */
    void check(Subject subject, Permission permission) throws PermissionDeniedException;
    
}
