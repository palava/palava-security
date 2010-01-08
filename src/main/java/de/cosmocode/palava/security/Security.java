package de.cosmocode.palava.security;

import de.cosmocode.palava.security.acl.AccessControlList;
import de.cosmocode.palava.security.acl.DefaultAccessControlList;

/**
 * Static factory for security related objects like
 * {@link Role}, {@link Group}, {@link Subject} and more.
 *
 * @author Willi Schoenborn
 */
public final class Security {

    private Security() {
        
    }
    
    /**
     * Creates a new {@link AccessControlList}.
     * 
     * @return a new {@link AccessControlList}
     */
    public static AccessControlList newAccessControlList() {
        return new DefaultAccessControlList();
    }
    
}
