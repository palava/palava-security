package de.cosmocode.palava.security;

import com.google.common.collect.ImmutableSet;

/**
 * A {@link Subject} is
 * <strong>a person or automated agent</strong>.
 * 
 * A Subject is related to a set of {@link Role}s.
 *
 * @author Willi Schoenborn
 * @see <a href="http://en.wikipedia.org/wiki/Role-based_access_control">Wikipedia</a>
 */
public interface Subject {

    /**
     * Provide all {@link Role}s associated with this
     * {@link Subject}.
     * 
     * @return a set of roles
     */
    ImmutableSet<Role> getRoles();
    
}
