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

import de.cosmocode.palava.security.acl.AccessControlList;
import de.cosmocode.palava.security.acl.DefaultAccessControlList;

/**
 * Static factory for security related objects like
 * {@link Role}, {@link Group}, {@link Subject} and more.
 *
 * @author Willi Schoenborn
 */
public final class Security {

    private Security() {
        
    }
    
    /**
     * Creates a new {@link AccessControlList}.
     * 
     * @return a new {@link AccessControlList}
     */
    public static AccessControlList newAccessControlList() {
        return new DefaultAccessControlList();
    }
    
    /**
     * Creates new simple {@link Credentials} for
     * username/password login.
     * 
     * @param username the username
     * @param password the password
     * @return new {@link SimpleCredentials} populated with the given username and password
     */
    public static SimpleCredentials newCredentials(String username, String password) {
        return new SimpleCredentials(username, password);
    }
    
}
