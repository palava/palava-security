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

import de.cosmocode.palava.ipc.IpcCall;
import de.cosmocode.palava.ipc.IpcCommand;
import de.cosmocode.palava.ipc.IpcCommandExecutionException;
import de.cosmocode.palava.ipc.IpcCommand.Description;
import de.cosmocode.palava.ipc.IpcCommand.Return;

/**
 * See below.
 *
 * @author Willi Schoenborn
 */
@Description("Checks whether the current user is remembered.")
@Return(
    name = IsRemembered.IS_REMEMBERED,
    description = "A boolean, true if the current user is remembered, false otherwise"
)
@Singleton
public final class IsRemembered implements IpcCommand {

    public static final String IS_REMEMBERED = "isRemembered";
    
    private static final Logger LOG = LoggerFactory.getLogger(IsRemembered.class);

    private final Provider<Subject> provider;
    
    @Inject
    public IsRemembered(Provider<Subject> provider) {
        this.provider = Preconditions.checkNotNull(provider, "Provider");
    }

    @Override
    public void execute(IpcCall call, Map<String, Object> result) throws IpcCommandExecutionException {
        final Subject currentUser = provider.get();
        
        LOG.trace("Check user is remembered");
        final boolean isRemembered = currentUser.isRemembered();
        
        LOG.trace("{} is remembered: {}", currentUser, isRemembered);
        result.put(IS_REMEMBERED, isRemembered);
    }

}