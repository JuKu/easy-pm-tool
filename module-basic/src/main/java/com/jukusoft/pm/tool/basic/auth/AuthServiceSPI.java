package com.jukusoft.pm.tool.basic.auth;

import com.jukusoft.pm.tool.def.auth.AuthProvider;
import com.jukusoft.pm.tool.def.auth.AuthUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class AuthServiceSPI implements InitializingBean {

    protected static final Logger logger = LoggerFactory.getLogger(AuthServiceSPI.class);

    @Autowired
    protected List<AuthProvider> authProviderList;

    public AuthUser authenticate(String username, String password) {
        Objects.requireNonNull(this.authProviderList);

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
        logger.info("init AuthServiceSPI");
        Objects.requireNonNull(this.authProviderList);

        //sort list by auth provider priority (e.q. that ldap login will be executed before database login)
        Collections.sort(this.authProviderList);

        if (this.authProviderList.isEmpty()) {
            throw new IllegalStateException("No auth providers found in classpath!");
        }
    }

}
