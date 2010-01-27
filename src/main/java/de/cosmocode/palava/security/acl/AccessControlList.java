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

package de.cosmocode.palava.security.acl;

import de.cosmocode.palava.security.Permission;
import de.cosmocode.palava.security.PermissionDeniedException;
import de.cosmocode.palava.security.Role;
import de.cosmocode.palava.security.Subject;

/**
 * An {@link AccessControlList} manages permissions
 * and their relations to specific {@link Role}s.
 *
 * @author Willi Schoenborn
 */
public interface AccessControlList {

    /**
     * Grants a {@link Permission} to a given {@link Role}.
     * 
     * @param role the role
     * @param permission the permission being granted
     * @return true if the permission was not yet granted to the
     *         given role
     * @throws NullPointerException if role or permission is null
     */
    boolean grant(Role role, Permission permission);
    
    /**
     * Revokes a {@link Permission} for a given {@link Role}.
     * 
     * @param role the role
     * @param permission the permission being revoked
     * @return true if the permission was granted before
     * @throws NullPointerException if role or permission is null
     */
    boolean revoke(Role role, Permission permission);
    
    /**
     * Checks whether the given {@link Role} has the permission.
     * 
     * @param role the role
     * @param permission the permission
     * @throws PermissionDeniedException if permission was denied
     */
    void check(Role role, Permission permission) throws PermissionDeniedException;
    
    /**
     * Checks whether the given {@link Subject} has the permission.
     * 
     * @param subject the subject
     * @param permission the permission
     * @throws PermissionDeniedException if permission was denied
     */
    void check(Subject subject, Permission permission) throws PermissionDeniedException;
    
}
