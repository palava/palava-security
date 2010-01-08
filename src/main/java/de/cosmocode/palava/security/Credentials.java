package de.cosmocode.palava.security;

/**
 * {@link Credentials} usually represent username and password.
 * This interface makes no assumption how concrete sub classes
 * interpret this term.
 *
 * @author Willi Schoenborn
 */
public interface Credentials {

    /**
     * Clears internal saved state.
     */
    void clear();
    
}
