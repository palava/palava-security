package de.cosmocode.palava.security;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Singleton;

/**
 * Binds the {@link PassPhraseGenerator} to {@link SimplePassPhraseGenerator}.
 *
 * @author Willi Schoenborn
 */
public final class SimplePassPhraseGeneratorModule implements Module {

    @Override
    public void configure(Binder binder) {
        binder.bind(PassPhraseGenerator.class).to(SimplePassPhraseGenerator.class).in(Singleton.class);
    }

}
