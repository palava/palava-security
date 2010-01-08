package de.cosmocode.palava.security.acl;

import com.google.common.base.Preconditions;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import de.cosmocode.palava.security.Permission;
import de.cosmocode.palava.security.Role;
import de.cosmocode.palava.security.Subject;

/**
 * Default implementation of the {@link AccessControlList} interface.
 *
 * @author Willi Schoenborn
 */
public class DefaultAccessControlList implements AccessControlList {
    
    private static final String ERROR_FORMAT = "Permission %s not allowed for role %s";
    private static final String ERROR_FORMAT_SUBJECT = "Permission %s not allowed for subject %s";

    private final Multimap<Role, Permission> grants = HashMultimap.create();
    
    @Override
    public boolean grant(Role role, Permission permission) {
        Preconditions.checkNotNull(role, "Role");
        Preconditions.checkNotNull(permission, "Permission");
        return grants.put(role, permission);
    }
    
    @Override
    public boolean revoke(Role role, Permission permission) {
        Preconditions.checkNotNull(role, "Role");
        Preconditions.checkNotNull(permission, "Permission");
        return grants.remove(role, permission);
    }
    
    @Override
    public void check(Role role, Permission permission) {
        Preconditions.checkNotNull(role, "Role");
        Preconditions.checkNotNull(permission, "Permission");
        if (grants.containsEntry(role, permission)) {
            return;
        } else {
            throw new SecurityException(String.format(ERROR_FORMAT, permission, role));
        }
    }
    
    @Override
    public void check(Subject subject, Permission permission) {
        Preconditions.checkNotNull(subject, "Subject");
        Preconditions.checkNotNull(permission, "Permission");
        for (Role role : subject.getRoles()) {
            if (grants.containsEntry(role, permission)) return;
        }
        throw new SecurityException(String.format(ERROR_FORMAT_SUBJECT, permission, subject));
    }

}
