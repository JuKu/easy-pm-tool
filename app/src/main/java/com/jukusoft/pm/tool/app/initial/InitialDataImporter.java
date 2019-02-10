package com.jukusoft.pm.tool.app.initial;

import com.jukusoft.pm.tool.def.dao.GroupDAO;
import com.jukusoft.pm.tool.def.dao.GroupMembershipDAO;
import com.jukusoft.pm.tool.def.dao.UserDAO;
import com.jukusoft.pm.tool.def.model.User;
import com.jukusoft.pm.tool.def.model.permission.Group;
import com.jukusoft.pm.tool.def.model.permission.GroupMembership;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.transaction.Transactional;
import java.util.Objects;

@Configuration
@Profile("default")
public class InitialDataImporter implements InitializingBean {

    protected static final String GROUP_ADMIN = "admin";
    protected static final String GROUP_USERS = "users";

    @Autowired
    protected UserDAO userDAO;

    @Autowired
    protected GroupDAO groupDAO;

    @Autowired
    protected GroupMembershipDAO groupMembershipDAO;

    protected static final Logger logger = LoggerFactory.getLogger(InitialDataImporter.class);

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.debug("check for initial data...");

        this.createDefaultGroupsIfNotExists();
        this.createDefaultUserNoUseExists();
    }

    @Transactional
    protected void createDefaultUserNoUseExists() {
        long nOfUsersInDB = userDAO.count();

        logger.info("{} users found in database", nOfUsersInDB);

        if (nOfUsersInDB == 0) {
            Group adminGroup = groupDAO.findByName(GROUP_ADMIN).orElseGet(() -> {
                throw new IllegalStateException("admin group doesn't exists!");
            });

            Group usersGroup = groupDAO.findByName(GROUP_USERS).orElseGet(() -> {
                throw new IllegalStateException("users group doesn't exists!");
            });

            Objects.requireNonNull(adminGroup);
            Objects.requireNonNull(usersGroup);

            logger.warn("create default user 'admin' with password 'admin' now.");

            User user = new User("admin", "admin", "admin@example.com");
            user.setSuperUser(true);
            user = userDAO.save(user);

            //add user to default groups
            //adminGroup.addMembership(new GroupMembership(adminGroup, user));
            //usersGroup.addMembership(new GroupMembership(usersGroup, user));

            groupDAO.save(adminGroup);
            groupDAO.save(usersGroup);

            groupMembershipDAO.save(new GroupMembership(usersGroup, user));
            groupMembershipDAO.save(new GroupMembership(adminGroup, user));

            logger.info("{} users found in database after user creation", userDAO.count());
        }
    }

    @Transactional
    protected void createDefaultGroupsIfNotExists () {
        long nOfGroups = groupDAO.count();

        if (nOfGroups == 0) {
            Group adminGroup = new Group(GROUP_ADMIN, "Super Administrators", true);
            groupDAO.save(adminGroup);

            Group usersGroup = new Group(GROUP_USERS, "Users", true);
            groupDAO.save(usersGroup);

            logger.info("{} groups found in database after group creation", groupDAO.count());
        }

        //if this 2 statements aren't true, the user has broke his own application, maybe with editing raw data in database
        assert groupDAO.findByName(GROUP_ADMIN).isPresent();
        assert groupDAO.findByName(GROUP_USERS).isPresent();
    }

}
