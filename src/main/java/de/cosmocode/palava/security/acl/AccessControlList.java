package de.cosmocode.palava.security.acl;

import de.cosmocode.palava.security.Permission;
import de.cosmocode.palava.security.Role;
import de.cosmocode.palava.security.Subject;

/**
 * An {@link AccessControlList} manages permissions
 * and their relations to specific {@link Role}s.
 *
 * @author Willi Schoenborn
 */
public interface AccessControlList {

    /**
     * Grants a {@link Permission} to a given {@link Role}.
     * 
     * @param role the role
     * @param permission the permission being granted
     * @return true if the permission was not yet granted to the
     *         given role
     * @throws NullPointerException if role or permission is null
     */
    boolean grant(Role role, Permission permission);
    
    /**
     * Revokes a {@link Permission} for a given {@link Role}.
     * 
     * @param role the role
     * @param permission the permission being revoked
     * @return true if the permission was granted before
     * @throws NullPointerException if role or permission is null
     */
    boolean revoke(Role role, Permission permission);
    
    /**
     * Checks whether the given {@link Role} has the permission.
     * 
     * @param role the role
     * @param permission the permission
     * @throws SecurityException if authorization failed
     */
    void check(Role role, Permission permission);
    
    /**
     * Checks whether the given {@link Subject} has the permission.
     * 
     * @param subject the subject
     * @param permission the permission
     * @throws SecurityException if authorization failed 
     */
    void check(Subject subject, Permission permission);
    
}
