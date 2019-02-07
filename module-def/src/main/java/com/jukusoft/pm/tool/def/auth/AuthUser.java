package com.jukusoft.pm.tool.def.auth;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class AuthUser extends UsernamePasswordAuthenticationToken {

    public AuthUser(String username, Collection<? extends GrantedAuthority> authorities) {
        super(username, "", authorities);
    }

    public AuthUser(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public AuthUser(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }
}
