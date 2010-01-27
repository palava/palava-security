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

import com.google.common.base.Preconditions;

/**
 * A basic implementation of the {@link Credentials} interface
 * using a username-and-password-based approach.
 *
 * @author Willi Schoenborn
 */
public final class SimpleCredentials implements Credentials {

    private String username;
    private String password;
    
    public SimpleCredentials(String username, String password) {
        this.username = Preconditions.checkNotNull(username, "Username");
        this.password = Preconditions.checkNotNull(password, "Password"); 
    }
    
    public String getUsername() {
        return username;
    }
    
    public String getPassword() {
        return password;
    }
    
    @Override
    public void clear() {
        this.username = null;
        this.password = null;
    }

}
