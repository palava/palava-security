package de.cosmocode.palava.security;

import javax.security.auth.login.LoginException;

import org.jdom.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.cosmocode.palava.AbstractService;
import de.cosmocode.palava.ComponentManager;
import de.cosmocode.palava.Server;
import de.cosmocode.palava.ServiceInitializationException;

public class DummySecurityService extends AbstractService implements SecurityService {

    private static final Logger log = LoggerFactory.getLogger(DummySecurityService.class);

    @Override
    public void check(Subject subject, Permission permission) throws PermissionDeniedException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Subject login(Credentials credentials) throws LoginException {
        // TODO Auto-generated method stub
        return null;
    }

    
}
