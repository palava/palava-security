/**
 * palava - a java-php-bridge
 * Copyright (C) 2007-2010  CosmoCode GmbH
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package de.cosmocode.palava.security;

import java.util.Set;

import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.SessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Named;

/**
 * Allows creating and configuring a {@link SecurityManager} instance via guice.
 *
 * @author Willi Schoenborn
 */
final class SecurityManagerProvider implements Provider<SecurityManager> {

    private static final Logger LOG = LoggerFactory.getLogger(SecurityManagerProvider.class);

    private final DefaultSecurityManager manager;

    @Inject
    public SecurityManagerProvider(@Named("shiro.ini") String ini, Set<Realm> realms) {
        Preconditions.checkNotNull(ini, "Ini");
        Preconditions.checkNotNull(realms, "Realms");
        LOG.debug("Providing {}", SecurityManager.class.getName());
        this.manager = new DefaultSecurityManager(realms);
    }
    
    /**
     * Allows to inject another implementation for the {@link SessionManager}
     * interface forthe underlying security manager.
     * 
     * @param sessionManager the session manager to use
     * @throws NullPointerException if sessionManager is null
     */
    @Inject(optional = true)
    void setSessionManager(SessionManager sessionManager) {
        Preconditions.checkNotNull(sessionManager, "SessionManager");
        LOG.debug("Assigning {} to {}", sessionManager, manager);
        manager.setSessionManager(sessionManager);
    }
    
    @Override
    public SecurityManager get() {
        return manager;
    }
    
}
