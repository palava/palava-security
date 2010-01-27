/**
 * palava - a java-php-bridge
 * Copyright (C) 2007  CosmoCode GmbH
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

import javax.security.auth.login.LoginException;

import de.cosmocode.palava.core.framework.Service;

/**
 * A {@link Service} used to handle authentication and
 * authorization based on interfaces.
 * 
 * <p>
 *   Consider specifing an application specific interface on
 *   top of this to restrict the return value to your
 *   application specific type.
 * </p>
 *
 * @author Willi Schoenborn
 */
public interface SecurityService extends Service {

    /**
     * Attempts to login using provided credentials.
     * 
     * @param credentials the credentials
     * @return a {@link Subject} representing a user
     * @throws LoginException if login failed
     */
    Subject login(Credentials credentials) throws LoginException;
    
    /**
     * Checks whether the given {@link Subject} has the permission.
     * 
     * @param subject the subject
     * @param permission the permission
     * @throws PermissionDeniedException if permission was denied
     */
    void check(Subject subject, Permission permission) throws PermissionDeniedException;
    
}
