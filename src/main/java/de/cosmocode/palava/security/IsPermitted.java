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

import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

import de.cosmocode.palava.ipc.IpcArguments;
import de.cosmocode.palava.ipc.IpcCall;
import de.cosmocode.palava.ipc.IpcCommand;
import de.cosmocode.palava.ipc.IpcCommandExecutionException;
import de.cosmocode.palava.ipc.IpcCommand.Description;
import de.cosmocode.palava.ipc.IpcCommand.Param;
import de.cosmocode.palava.ipc.IpcCommand.Return;

/**
 * See below.
 *
 * @author Willi Schoenborn
 */
@Description("Checks whether the current user has a specific permission.")
@Param(name = IsPermitted.PERMISSION, description = "The permission to check against")
@Return(name = IsPermitted.IS_PERMITTED, description = "A boolean, true if the user is permitted, false otherwise")
@Singleton
public final class IsPermitted implements IpcCommand {

    public static final String PERMISSION = "permission";
    public static final String IS_PERMITTED = "isPermitted";
    
    private static final Logger LOG = LoggerFactory.getLogger(IsPermitted.class);
    
    private final Provider<Subject> provider;
    
    @Inject
    public IsPermitted(Provider<Subject> provider) {
        this.provider = Preconditions.checkNotNull(provider, "Provider");
    }
    
    @Override
    public void execute(IpcCall call, Map<String, Object> result) throws IpcCommandExecutionException {
        final IpcArguments arguments = call.getArguments();
        final String permission = arguments.getString(PERMISSION);
        
        final Subject currentUser = provider.get();
        
        LOG.trace("Checking user against {}", permission);
        final boolean isPermitted = currentUser.isPermitted(permission);
        LOG.trace("{} has {}: {}", new Object[] {
            currentUser, permission, Boolean.valueOf(isPermitted)
        });
        
        result.put(IS_PERMITTED, isPermitted);
    }

}