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

import java.io.Serializable;
import java.util.Map;
import java.util.UUID;

import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.AbstractSessionManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;

import de.cosmocode.palava.ipc.IpcSessionProvider;
import de.cosmocode.patterns.Adapter;

/**
 * Adapts the {@link IpcSessionProvider} to the {@link SessionManager} interface.
 *
 * @author Willi Schoenborn
 */
@Adapter(SessionManager.class)
final class IpcSessionProviderAdapter extends AbstractSessionManager implements SessionManager {

    private static final Logger LOG = LoggerFactory.getLogger(IpcSessionProviderAdapter.class);

    private final IpcSessionProvider provider;
    
    @Inject
    public IpcSessionProviderAdapter(IpcSessionProvider provider) {
        this.provider = Preconditions.checkNotNull(provider, "Provider");
    }

    @Override
    @SuppressWarnings("unchecked")
    protected Session createSession(Map initData) throws AuthorizationException {
        return new IpcSessionAdapter(provider.getSession(UUID.randomUUID().toString(), null));
    }

    @Override
    protected Session doGetSession(Serializable sessionId) throws InvalidSessionException {
        // TODO fail if there is no session with that id
        Preconditions.checkNotNull(sessionId, "SessionId");
        return new IpcSessionAdapter(provider.getSession(sessionId.toString(), null));
    }
    
}
