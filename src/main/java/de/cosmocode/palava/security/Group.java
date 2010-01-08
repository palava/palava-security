package de.cosmocode.palava.security;

import java.util.Iterator;

/**
 * A {@link Group} is a basically a {@link Role}
 * which is able to contain multiple {@link Role}s.
 * 
 * A group must at least contain one role.
 *
 * @author Willi Schoenborn
 */
public interface Group extends Role, Iterable<Role> {

    /**
     * Adds a {@link Role} to this group.
     * 
     * @param role the Role
     * @return true if the role was not already included
     */
    boolean addMember(Role role);
    
    /**
     * Removes a {@link Role} from this group.
     * 
     * @param role the Role
     * @return true if the was present before
     */
    boolean removeMember(Role role);
    
    /**
     * Checks whether a {@link Role} is included in this group.
     * 
     * @param role the role
     * @return true if the given {@link Role} is a member of this group
     */
    boolean isMember(Role role);
    
    /**
     * Provides an iterable view on all members in this group.
     * 
     * @return an {@link Iterator} over all contained roles
     */
    @Override
    Iterator<Role> iterator();
    
}
