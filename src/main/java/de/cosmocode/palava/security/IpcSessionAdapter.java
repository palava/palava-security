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
import java.util.Collection;
import java.util.Date;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;
import com.google.inject.Inject;

import de.cosmocode.palava.ipc.IpcSession;

/**
 * Implementation of the {@link Session} interface which delegates to
 * an underlying {@link IpcSession}.
 *
 * @author Willi Schoenborn
 */
final class IpcSessionAdapter implements Session {

    private final IpcSession session;
    
    @Inject
    public IpcSessionAdapter(IpcSession session) {
        this.session = Preconditions.checkNotNull(session, "Session");
    }
    
    @Override
    public Serializable getId() {
        return session.getSessionId();
    }
    
    @Override
    public Object getAttribute(Object key) throws InvalidSessionException {
        return session.get(key);
    }
    
    @Override
    public Collection<Object> getAttributeKeys() throws InvalidSessionException {
        final Collection<Object> attributeKeys = Sets.newHashSet();
        for (Entry<Object, Object> entry : session) {
            attributeKeys.add(entry.getKey());
        }
        return attributeKeys;
    }
    
    @Override
    public String getHost() {
        return null;
    }
    
    @Override
    public Date getLastAccessTime() {
        return session.lastAccessTime();
    }
    
    @Override
    public Date getStartTimestamp() {
        return session.startedAt();
    }
    
    @Override
    public Object removeAttribute(Object key) throws InvalidSessionException {
        return session.remove(key);
    }
    
    @Override
    public void setAttribute(Object key, Object value) throws InvalidSessionException {
        session.set(key, value);
    }
    
    @Override
    public void touch() throws InvalidSessionException {
        session.touch();
    }
    
    @Override
    public void stop() throws InvalidSessionException {
        session.destroy();
    }
    
    @Override
    public long getTimeout() throws InvalidSessionException {
        return session.getTimeout(TimeUnit.MILLISECONDS);
    }
    
    @Override
    public void setTimeout(long maxIdleTimeInMillis) throws InvalidSessionException {
        session.setTimeout(maxIdleTimeInMillis, TimeUnit.MILLISECONDS);
    }

}
