package com.jukusoft.pm.tool.basic.auth.impl;

import com.jukusoft.pm.tool.def.auth.AuthProvider;
import com.jukusoft.pm.tool.def.auth.AuthUser;
import com.jukusoft.pm.tool.def.dao.UserDAO;
import com.jukusoft.pm.tool.def.model.User;
import com.jukusoft.pm.tool.def.model.auth.AuthentificationMethod;
import com.jukusoft.pm.tool.def.model.permission.GroupMembership;
import com.jukusoft.pm.tool.def.model.permission.Permission;
import com.jukusoft.pm.tool.def.utils.HashUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Component
public class DatabaseAuthProvider implements AuthProvider {

    protected static final Logger logger = LoggerFactory.getLogger(DatabaseAuthProvider.class);

    @Autowired
    protected UserDAO userDAO;

    @Transactional
    @Override
    public AuthUser authenticate(String username, String password) {
        Objects.requireNonNull(userDAO);

        //find user
        Optional<User> userOptional = userDAO.findByUsername(username);
        Objects.requireNonNull(userOptional);

        if (!userOptional.isPresent()) {
            logger.info("user '{}' doesn't exists in database!", username);
            throw new UsernameNotFoundException("username doesn't exists in database!");
        }

        User user = userOptional.get();

        //check, if user was created from this auth provider
        if (user.getAuthMethod() != AuthentificationMethod.DATABASE) {
            throw new AuthenticationServiceException("user '" + username + "' was created from another auth provider (e.q. ldap), so you cannot login with database auth provider.");
        }

        //next, check password
        String salt = user.getSalt();
        String exceptedPasswordHash = HashUtils.computePasswordSHAHash(salt + password);

        if (exceptedPasswordHash.equals(user.getPasswordHash())) {
            logger.info("authentification successful for user '{}'", username);

            Set<String> permissions = new HashSet<>();

            //find groups
            for (GroupMembership groupMembership : user.listGroups()) {
                logger.info("user (" + user.getId() + ") is member in group '" + groupMembership.getGroup().getName() + "'.");

                for (Permission permission : groupMembership.getGroup().listPermissions()) {
                    permissions.add(permission.getToken());
                }
            }

            //convert string permissions into granted authorities
            List<GrantedAuthority> authorityList = new ArrayList<>();

            for (String permission : permissions) {
                authorityList.add(new SimpleGrantedAuthority(permission));
            }

            //TODO: load user permissions

            //password is correct
            return new AuthUser(username, authorityList);
        } else {
            throw new BadCredentialsException("password is wrong.");
        }
    }

    @Override
    public int priority() {
        //small priority
        return 5;
    }

}
