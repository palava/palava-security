package de.cosmocode.palava.security;

/**
 * A {@link Permission} is
 * <strong>an approval of a mode of access to a resource</strong>.
 *
 * A {@link Permission} contains a target and an optinal action.
 *
 * @author Willi Schoenborn
 * @see <a href="http://en.wikipedia.org/wiki/Role-based_access_control">Wikipedia</a>
 */
public interface Permission {

    /**
     * Provide the target property's value.
     * 
     * @return the target of this {@link Permission}
     */
    String getTarget();
    
    /**
     * Provide the action property's value.
     * 
     * @return the action of this {@link Permission}
     */
    String getAction();
    
}
