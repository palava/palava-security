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

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresGuest;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.authz.annotation.RequiresUser;

import de.cosmocode.palava.ipc.Commands;
import de.cosmocode.palava.ipc.FilterModule;

/**
 * Binds all security filters to commands annotated with their corresponding
 * annotations.
 *
 * @author Willi Schoenborn
 */
public final class SecurityFilterModule extends FilterModule {

    @Override
    protected void configure() {
        filter(Commands.annotatedWith(RequiresAuthentication.class)).through(AuthenticationFilter.class);
        filter(Commands.annotatedWith(RequiresGuest.class)).through(GuestFilter.class);
        filter(Commands.annotatedWith(RequiresPermissions.class)).through(PermissionFilter.class);
        filter(Commands.annotatedWith(RequiresRoles.class)).through(RoleFilter.class);
        filter(Commands.annotatedWith(RequiresUser.class)).through(UserFilter.class);
    }

}
