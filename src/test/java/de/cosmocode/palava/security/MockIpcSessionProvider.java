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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.internal.Maps;

import de.cosmocode.palava.ipc.AbstractIpcSession;
import de.cosmocode.palava.ipc.IpcSession;
import de.cosmocode.palava.ipc.IpcSessionProvider;

final class MockIpcSessionProvider implements IpcSessionProvider {

    private static final Logger LOG = LoggerFactory.getLogger(MockIpcSessionProvider.class);

    private final Map<String, IpcSession> cache = Maps.newHashMap();
    
    @Override
    public IpcSession getSession(final String sessionId, String identifier) {
        final IpcSession cached = cache.get(sessionId);
        if (cached == null) {
            LOG.trace("Creating new session with id {}", sessionId);
            final IpcSession session = new AbstractIpcSession() {
                
                private final Map<Object, Object> context = Maps.newHashMap();
                
                @Override
                public String getSessionId() {
                    return sessionId;
                }
                
                @Override
                protected Map<Object, Object> context() {
                    return context;
                }
                
            };
            cache.put(sessionId, session);
            return session;
        } else {
            LOG.trace("Found cached session with id {}", sessionId);
            return cached;
        }
    }

}
