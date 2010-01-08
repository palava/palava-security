package de.cosmocode.palava.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableSet;

public class User implements Subject {

    private static final Logger log = LoggerFactory.getLogger(User.class);

    @Override
    public ImmutableSet<Role> getRoles() {
        return ImmutableSet.of();
    }
    
}
