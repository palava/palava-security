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

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
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
import de.cosmocode.palava.ipc.IpcCommand.Description;
import de.cosmocode.palava.ipc.IpcCommand.Param;
import de.cosmocode.palava.ipc.IpcCommand.Params;
import de.cosmocode.palava.ipc.IpcCommand.Throw;
import de.cosmocode.palava.ipc.IpcCommand.Throws;
import de.cosmocode.palava.ipc.IpcCommandExecutionException;

/**
 * See below.
 *
 * @author Willi Schoenborn
 */
@Description("Authenticates the current user.")
@Params({
    @Param(name = Login.USERNAME, description = "The user's username"),
    @Param(name = Login.PASSWORD, description = "The user's secret password"),
    @Param(
        name = Login.REMEMBER_ME,
        description = "A boolean which enables the rememberMe-feature",
        optional = true,
        defaultValue = "false"
    )
})
@Throws({
    @Throw(name = IllegalStateException.class, description = "If user is already authenticated"),
    @Throw(name = UnknownAccountException.class, description = "if username is unknown"),
    @Throw(name = IncorrectCredentialsException.class, description = "If password didn't match"),
    @Throw(name = LockedAccountException.class, description = "If account is locked, no access possible"),
    @Throw(name = AuthenticationException.class, description = "Unexpected error during authentication")
})
@Singleton
public final class Login implements IpcCommand {

    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String REMEMBER_ME = "rememberMe";

    private static final Logger LOG = LoggerFactory.getLogger(Login.class);

    private final Provider<Subject> provider;

    @Inject
    public Login(Provider<Subject> provider) {
        this.provider = Preconditions.checkNotNull(provider, "Provider");
    }

    @Override
    public void execute(IpcCall call, Map<String, Object> result) throws IpcCommandExecutionException {
        final IpcArguments arguments = call.getArguments();
        final String username = arguments.getString(USERNAME);
        final char[] password = arguments.getString(PASSWORD).toCharArray();
        final boolean rememberMe = arguments.getBoolean(REMEMBER_ME, false);

        final Subject currentUser = provider.get();

        Preconditions.checkState(!currentUser.isAuthenticated(), "%s is already authenticated", currentUser);

        final UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        token.setRememberMe(rememberMe);

        LOG.debug("Attempt to login {}", currentUser);

        try {
            currentUser.login(token);
        } catch (UnknownAccountException e) {
            throw new IpcCommandExecutionException(e);
        } catch (IncorrectCredentialsException e) {
            throw new IpcCommandExecutionException(e);
        } catch (LockedAccountException e) {
            throw new IpcCommandExecutionException(e);
        } catch (AuthenticationException e) {
            throw new IpcCommandExecutionException(e);
        }

        LOG.info("Successfully logged in as {}", currentUser.getPrincipal());
    }

}
