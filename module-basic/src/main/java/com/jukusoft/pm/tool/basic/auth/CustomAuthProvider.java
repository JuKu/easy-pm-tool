package com.jukusoft.pm.tool.basic.auth;

import com.jukusoft.pm.tool.def.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class CustomAuthProvider implements AuthenticationProvider {

    protected static final Logger logger = LoggerFactory.getLogger(CustomAuthProvider.class);

    @Autowired
    private AuthServiceSPI authService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        logger.info("authentification object received: " + authentication.getClass().getCanonicalName());

        Objects.requireNonNull(authService);

        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        StringUtils.requireNonEmptyStringOrThrowException(username, new BadCredentialsException("username cannot be null or empty!"));
        StringUtils.requireNonEmptyStringOrThrowException(password, new BadCredentialsException("password cannot be null or empty!"));

        logger.info("try to authentificate user '{}'", authentication.getName());

        return authService.authenticate(username, password);
    }

    @Override
    public boolean supports(Class<?> cls) {
        return cls.equals(
                UsernamePasswordAuthenticationToken.class);
    }

}
