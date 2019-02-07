package com.jukusoft.pm.tool.basic.auth;

import com.jukusoft.pm.tool.def.auth.AuthProvider;
import com.jukusoft.pm.tool.def.auth.AuthUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ServiceLoader;

@Service
public class AuthServiceSPI implements InitializingBean {

    protected static final Logger logger = LoggerFactory.getLogger(AuthServiceSPI.class);

    protected final List<AuthProvider> authProviderList = new ArrayList<>();

    public AuthUser authenticate(String username, String password) {
        for (AuthProvider authProvider : authProviderList) {
            try {
                logger.debug("try authentification provider for user '{}': {}", username, authProvider.getClass().getSimpleName());
                AuthUser user = authProvider.authenticate(username, password);

                if (user == null) {
                    //failure
                    throw new IllegalStateException("authentification provider returned null: " + authProvider.getClass().getCanonicalName());
                }

                return user;
            } catch (AuthenticationException e) {
                //try next authentification provider
                logger.info("auth provider {} failed, so try next authentification provider", authProvider.getClass().getSimpleName());

                continue;
            }
        }

        logger.warn("all authentification providers failed for user '{}'", username);
        throw new BadCredentialsException("user credentials are wrong!");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        //find all auth providers in classpath
        ServiceLoader<AuthProvider> authProviders = ServiceLoader.load(AuthProvider.class);

        logger.info("search for auth providers in classpath...");
        int counter = 0;

        for (AuthProvider provider : authProviders) {
            logger.info("auth provider found: {}", provider.getClass().getCanonicalName());
            this.authProviderList.add(provider);

            counter++;
        }

        logger.info("{} auth providers found in classpath.", counter);

        //sort list by auth provider priority (e.q. that ldap login will be executed before database login)
        Collections.sort(this.authProviderList);

        if (counter == 0) {
            throw new IllegalStateException("No auth providers found in classpath!");
        }
    }

}
