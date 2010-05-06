/**
 * Copyright 2010 CosmoCode GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
