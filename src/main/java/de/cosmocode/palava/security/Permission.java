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

/**
 * A {@link Permission} is
 * <strong>an approval of a mode of access to a resource</strong>.
 *
 * A {@link Permission} contains a target and an optinal action.
 *
 * @author Willi Schoenborn
 * @see <a href="http://en.wikipedia.org/wiki/Role-based_access_control">Wikipedia</a>
 */
public interface Permission {

    /**
     * Provide the target property's value.
     * 
     * @return the target of this {@link Permission}
     */
    String getTarget();
    
    /**
     * Provide the action property's value.
     * 
     * @return the action of this {@link Permission}
     */
    String getAction();
    
}
