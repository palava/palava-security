package de.cosmocode.palava.security;

/**
 * A {@link Role} is a
 * <strong>job function or title which defines an authority level</strong>.
 * 
 * @author Willi Schoenborn
 * @see <a href="http://en.wikipedia.org/wiki/Role-based_access_control">Wikipedia</a>
 */
public interface Role {

    /**
     * Provides the name of this role.
     * 
     * @return this Role's name
     */
    String getName();
    
}
