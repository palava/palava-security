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
import de.cosmocode.palava.ipc.IpcCommandExecutionException;
import de.cosmocode.palava.ipc.IpcCommand.Description;
import de.cosmocode.palava.ipc.IpcCommand.Param;
import de.cosmocode.palava.ipc.IpcCommand.Params;
import de.cosmocode.palava.ipc.IpcCommand.Throw;
import de.cosmocode.palava.ipc.IpcCommand.Throws;

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
