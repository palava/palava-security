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

import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.annotation.RequiresRoles;
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
 * A filter which runs on commands annotated with {@link RequiresRoles}
 * and throws an {@link AuthorizationException} if the current user does not have
 * the required role.
 *
 * @author Willi Schoenborn
 */
@Singleton
public final class RoleFilter implements IpcCallFilter {

    private static final Logger LOG = LoggerFactory.getLogger(RoleFilter.class);
    
    private final Provider<Subject> provider;
    
    @Inject
    public RoleFilter(Provider<Subject> provider) {
        this.provider = Preconditions.checkNotNull(provider, "Provider");
    }

    @Override
    public Map<String, Object> filter(IpcCall call, IpcCommand command, IpcCallFilterChain chain)
        throws IpcCommandExecutionException {
        
        final RequiresRoles requiresRoles = command.getClass().getAnnotation(RequiresRoles.class);
        final String role = requiresRoles.value();
        
        LOG.trace("{} requires {}", command, role);
        final Subject currentUser = provider.get();
        
        currentUser.checkRole(role);
        
        LOG.trace("{} is permitted to execute", currentUser, command);
        
        return chain.filter(call, command);
    }

}
