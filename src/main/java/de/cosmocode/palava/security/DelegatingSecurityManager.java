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

import java.util.Set;

import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;

/**
 * Implementation of the {@link SecurityManager} implementation which
 * delegates to service instances bound via guice.
 *
 * @author Willi Schoenborn
 */
final class DelegatingSecurityManager extends DefaultSecurityManager implements SecurityManager {

    @Inject
    public DelegatingSecurityManager(Set<Realm> realms) {
        Preconditions.checkNotNull(realms, "Realms");
        setRealms(realms);
    }
    
    
}
