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

import com.google.common.base.Preconditions;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import de.cosmocode.palava.security.Permission;
import de.cosmocode.palava.security.PermissionDeniedException;
import de.cosmocode.palava.security.Role;
import de.cosmocode.palava.security.Subject;

/**
 * Default implementation of the {@link AccessControlList} interface.
 *
 * @author Willi Schoenborn
 */
public class DefaultAccessControlList implements AccessControlList {
    
    private final Multimap<Role, Permission> grants = HashMultimap.create();
    
    @Override
    public boolean grant(Role role, Permission permission) {
        Preconditions.checkNotNull(role, "Role");
        Preconditions.checkNotNull(permission, "Permission");
        return grants.put(role, permission);
    }
    
    @Override
    public boolean revoke(Role role, Permission permission) {
        Preconditions.checkNotNull(role, "Role");
        Preconditions.checkNotNull(permission, "Permission");
        return grants.remove(role, permission);
    }
    
    @Override
    public void check(Role role, Permission permission) {
        Preconditions.checkNotNull(role, "Role");
        Preconditions.checkNotNull(permission, "Permission");
        if (grants.containsEntry(role, permission)) {
            return;
        } else if (role.hasParent()) {
            check(role.getParent(), permission);
        } else {
            throw new PermissionDeniedException(permission);
        }
    }
    
    @Override
    public void check(Subject subject, Permission permission) {
        Preconditions.checkNotNull(subject, "Subject");
        Preconditions.checkNotNull(permission, "Permission");
        for (Role role : subject.getRoles()) {
            if (grants.containsEntry(role, permission)) return;
        }
        throw new PermissionDeniedException(permission);
    }

}
