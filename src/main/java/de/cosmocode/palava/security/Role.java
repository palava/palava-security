package de.cosmocode.palava.security;

import java.util.Iterator;

/**
 * A {@link Role} is a
 * <strong>job function or title which defines an authority level</strong>.
 * 
 * @author Willi Schoenborn
 * @see <a href="http://en.wikipedia.org/wiki/Role-based_access_control">Wikipedia</a>
 */
public interface Role extends Iterable<Role> {

    /**
     * Provides the name of this role.
     * 
     * @return this Role's name
     */
    String getName();

    /**
     * Provides the parent of this role.
     * 
     * @return the parent role or null if there is none
     */
    Role getParent();
    
    /**
     * Checks for the existence of a parent role.
     * 
     * @return true if there is a parent role
     */
    boolean hasParent();
    
    /**
     * Adds a {@link Role} to this role.
     * 
     * @param role the Role
     * @return true if the role was not already included
     * @throws IllegalArgumentException if role is this or an ancestor
     */
    boolean addMember(Role role);
    
    /**
     * Removes a {@link Role} from this role.
     * 
     * @param role the Role
     * @return true if the was present before
     */
    boolean removeMember(Role role);
    
    /**
     * Checks whether a {@link Role} is included in this role.
     * 
     * @param role the role
     * @return true if the given {@link Role} is a member of this role
     */
    boolean isMember(Role role);
    
    /**
     * Provides an iterable view on all members in this role.
     * Returns an empty iterator if this role has no contained roles.
     * 
     * @return an {@link Iterator} over all contained roles
     */
    @Override
    Iterator<Role> iterator();
    
    
    
}
