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

import java.util.Properties;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.Singleton;
import com.google.inject.Stage;
import com.google.inject.multibindings.Multibinder;
import com.google.inject.name.Names;

import de.cosmocode.palava.ipc.IpcModule;
import de.cosmocode.palava.ipc.IpcSessionProvider;

/**
 * Tests shiro setup.
 *
 * @author Willi Schoenborn
 */
public final class ShiroTest {

    private static final Logger LOG = LoggerFactory.getLogger(ShiroTest.class);

    /**
     * Simple mock realm implementation.
     *
     * @author Willi Schoenborn
     */
    public static final class MockRealm extends AuthorizingRealm implements Realm {

        @Override
        public boolean supports(AuthenticationToken token) {
            return token instanceof UsernamePasswordToken;
        }
        
        @Override
        protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken raw) throws AuthenticationException {
            final UsernamePasswordToken token = UsernamePasswordToken.class.cast(raw);
            
            if ("willi".equals(token.getUsername()) && "5ucco2Mela?".equals(String.valueOf(token.getPassword()))) {
                return new SimpleAuthenticationInfo(token.getUsername(), token.getPassword(), getName());
            } else {
                throw new AuthenticationException();
            }
        }

        @Override
        protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
            if ("willi".equals(principals.getPrimaryPrincipal())) {
                return new SimpleAuthorizationInfo(ImmutableSet.of("admin"));
            } else {
                return null;
            }
        }
        
    }
    
    private static final class MockModule implements Module {
        
        @Override
        public void configure(Binder binder) {
            Names.bindProperties(binder, ImmutableMap.of("shiro.ini", "classpath:shiro.ini"));
            Multibinder.newSetBinder(binder, Realm.class).addBinding().to(MockRealm.class).in(Singleton.class);
            
            binder.bind(IpcSessionProvider.class).to(MockIpcSessionProvider.class).in(Singleton.class);
        }
        
    }
    
    /**
     * Tests shiro start/getSubject().
     */
    @Test
    public void start() {
        final Injector injector = Guice.createInjector(Stage.PRODUCTION,
            new MockModule(),
            new IpcModule(),
            new SecurityModule()
        );
        final Subject subject = injector.getInstance(Subject.class);
        LOG.trace("Current subject: {}", subject);
        subject.login(new UsernamePasswordToken("willi", "5ucco2Mela?".toCharArray(), true));
        
        subject.checkRole("admin");
    }
    
}
