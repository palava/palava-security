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

import java.util.Random;

import com.google.inject.Inject;
import com.google.inject.name.Named;

/**
 * Simple {@link PassPhraseGenerator} using an algorithm by
 * Ian F. Darwin, http://www.darwinsys.com/.
 *
 * @author Willi Schoenborn
 */
public final class SimplePassPhraseGenerator implements PassPhraseGenerator {

    private static final char[] CHARACTERS = { 
        'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k', 'm',
        'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'M',
        'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
        '2', '3', '4', '5', '6', '7', '8', '9'
    };
    
    private final Random rand = new Random();
    
    @Inject
    @Named("passphrase.length")
    private int length;

    @Override
    public String generatePassPhrase() {
        final StringBuilder buf = new StringBuilder();
        for (int i = 0; i < length; i++) {
            final char next = CHARACTERS[rand.nextInt(CHARACTERS.length)];
            buf.append(next);
        }
        return buf.toString();
    }

}
