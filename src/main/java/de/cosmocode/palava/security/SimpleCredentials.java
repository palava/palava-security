package de.cosmocode.palava.security;

import com.google.common.base.Preconditions;

/**
 * A basic implementation of the {@link Credentials} interface
 * using a username-and-password-based approach.
 *
 * @author Willi Schoenborn
 */
public final class SimpleCredentials implements Credentials {

    private String username;
    private String password;
    
    public SimpleCredentials(String username, String password) {
        this.username = Preconditions.checkNotNull(username, "Username");
        this.password = Preconditions.checkNotNull(password, "Password"); 
    }
    
    public String getUsername() {
        return username;
    }
    
    public String getPassword() {
        return password;
    }
    
    @Override
    public void clear() {
        this.username = null;
        this.password = null;
    }

}
