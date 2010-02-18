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

import java.util.Map;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.annotation.RequiresGuest;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

import de.cosmocode.palava.ipc.IpcCall;
import de.cosmocode.palava.ipc.IpcCallFilter;
import de.cosmocode.palava.ipc.IpcCallFilterChain;
import de.cosmocode.palava.ipc.IpcCommand;
import de.cosmocode.palava.ipc.IpcCommandExecutionException;

/**
 * A filter which runs on commands annotated with {@link RequiresGuest}
 * and throws an {@link AuthenticationException} if the current user is
 * either remembered or authenticated.
 *
 * @author Willi Schoenborn
 */
@Singleton
public final class GuestFilter implements IpcCallFilter {

    private static final Logger LOG = LoggerFactory.getLogger(GuestFilter.class);
    
    private final Provider<Subject> provider;
    
    @Inject
    public GuestFilter(Provider<Subject> provider) {
        this.provider = Preconditions.checkNotNull(provider, "Provider");
    }

    @Override
    public Map<String, Object> filter(IpcCall call, IpcCommand command, IpcCallFilterChain chain)
        throws IpcCommandExecutionException {

        LOG.trace("{} requires neither being authenticated nor remembered", command);
        final Subject currentUser = provider.get();
        
        if (currentUser.isAuthenticated()) {
            throw new AuthenticationException(String.format("%s requires not to be authenticated", command));
        } else if (currentUser.isRemembered()) {
            throw new AuthenticationException(String.format("%s requires not to be remembered", command));
        } else {
            LOG.trace("{} is a guest and therefore permitted to execute {}", currentUser, command);
            return chain.filter(call, command);
        }
    }

}
