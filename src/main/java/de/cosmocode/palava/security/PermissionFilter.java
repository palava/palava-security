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
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import com.google.inject.Provider;

import de.cosmocode.palava.ipc.IpcCall;
import de.cosmocode.palava.ipc.IpcCallFilter;
import de.cosmocode.palava.ipc.IpcCallFilterChain;
import de.cosmocode.palava.ipc.IpcCommand;
import de.cosmocode.palava.ipc.IpcCommandExecutionException;

/**
 * A filter which runs on commands annotated with {@link RequiresPermissions}
 * and throws an {@link AuthorizationException} if the current user does not have
 * the required permission.
 *
 * @author Willi Schoenborn
 */
final class PermissionFilter implements IpcCallFilter {

    private static final Logger LOG = LoggerFactory.getLogger(PermissionFilter.class);
    
    private final Provider<Subject> provider;
    
    @Inject
    public PermissionFilter(Provider<Subject> provider) {
        this.provider = Preconditions.checkNotNull(provider, "Provider");
    }
    
    @Override
    public Map<String, Object> filter(IpcCall call, IpcCommand command, IpcCallFilterChain chain)
        throws IpcCommandExecutionException {

        final RequiresPermissions requiresPermissions = command.getClass().getAnnotation(RequiresPermissions.class);
        final String permission = requiresPermissions.value();
        
        LOG.trace("{} requires {}", command, permission);
        final Subject currentUser = provider.get();
        
        currentUser.checkPermission(permission);
        
        LOG.trace("{} has permission {} and is therefore permitted to execute {}", new Object[] {
            currentUser, permission, command
        });
        
        return chain.filter(call, command);
    }

}
