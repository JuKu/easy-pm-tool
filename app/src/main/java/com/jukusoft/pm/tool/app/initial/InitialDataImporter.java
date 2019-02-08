package com.jukusoft.pm.tool.app.initial;

import com.jukusoft.pm.tool.def.dao.GroupDAO;
import com.jukusoft.pm.tool.def.dao.UserDAO;
import com.jukusoft.pm.tool.def.model.User;
import com.jukusoft.pm.tool.def.model.permission.Group;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("default")
public class InitialDataImporter implements InitializingBean {

    @Autowired
    protected UserDAO userDAO;

    @Autowired
    protected GroupDAO groupDAO;

    protected static final Logger logger = LoggerFactory.getLogger(InitialDataImporter.class);

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.debug("check for initial data...");

        this.createDefaultGroupsIfNotExists();
        this.createDefaultUserNoUseExists();
    }

    protected void createDefaultUserNoUseExists() {
        long nOfUsersInDB = userDAO.count();

        logger.info("{} users found in database", nOfUsersInDB);

        if (nOfUsersInDB == 0) {
            logger.warn("create default user 'admin' with password 'admin' now.");

            User user = new User("admin", "admin", "admin@example.com");
            user.setSuperUser(true);
            userDAO.save(user);

            logger.info("{} users found in database after user creation", userDAO.count());
        }
    }

    protected void createDefaultGroupsIfNotExists () {
        long nOfGroups = groupDAO.count();

        if (nOfGroups == 0) {
            Group adminGroup = new Group("admin", "Super Administrators");
            groupDAO.save(adminGroup);

            Group usersGroup = new Group("users", "Users");
            groupDAO.save(usersGroup);

            logger.info("{} groups found in database after group creation", groupDAO.count());
        }
    }

}
