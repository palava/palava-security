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
@Description("Checks whether the current user is authenticated.")
@Return(
    name = IsAuthenticated.IS_AUTHENTICATED,
    description = "A boolean, true if the user is authenticated, false otherwise"
)
@Singleton
public final class IsAuthenticated implements IpcCommand {

    public static final String IS_AUTHENTICATED = "isAuthenticated";
    
    private static final Logger LOG = LoggerFactory.getLogger(IsAuthenticated.class);

    private final Provider<Subject> provider;
    
    @Inject
    public IsAuthenticated(Provider<Subject> provider) {
        this.provider = Preconditions.checkNotNull(provider, "Provider");
    }
    
    @Override
    public void execute(IpcCall call, Map<String, Object> result) throws IpcCommandExecutionException {
        final Subject currentUser = provider.get();
        
        LOG.trace("Check user is authenticated");
        final boolean isAuthenticated = currentUser.isAuthenticated();
        
        LOG.trace("{} is authenticated: {}", currentUser, isAuthenticated);
        result.put(IS_AUTHENTICATED, isAuthenticated);
    }

}
