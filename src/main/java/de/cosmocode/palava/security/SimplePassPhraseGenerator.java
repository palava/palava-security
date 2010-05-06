/**
 * Copyright 2010 CosmoCode GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.cosmocode.palava.security;

import java.util.Random;

import com.google.inject.Inject;
import com.google.inject.name.Named;

/**
 * Simple {@link PassPhraseGenerator} using an algorithm by
 * Ian F. Darwin, http://www.darwinsys.com/.
 *
 * @author Detlef Huettemann
 * @author Willi Schoenborn
 */
final class SimplePassPhraseGenerator implements PassPhraseGenerator {

    // misreadable chars like il1o0 are left out
    private static final char[] CHARACTERS = {
        'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k', 'm',
        'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'M',
        'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
        '2', '3', '4', '5', '6', '7', '8', '9'
    };

    private final Random random = new Random();

    private final int length;

    @Inject
    public SimplePassPhraseGenerator(@Named(PassPhraseGeneratorConfig.LENGTH) int length) {
        this.length = length;
    }
    
    @Override
    public String generatePassPhrase() {
        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            builder.append(CHARACTERS[random.nextInt(CHARACTERS.length)]);
        }
        return builder.toString();
    }

}
