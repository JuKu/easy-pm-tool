package com.jukusoft.pm.tool.app.initial;

import com.jukusoft.pm.tool.def.dao.UserDAO;
import com.jukusoft.pm.tool.def.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InitialDataImporter implements InitializingBean {

    @Autowired
    protected UserDAO userDAO;

    protected static final Logger logger = LoggerFactory.getLogger(InitialDataImporter.class);

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.debug("check for initial data...");

        long nOfUsersInDB = userDAO.count();

        logger.info("{} users found in database", nOfUsersInDB);

        if (nOfUsersInDB == 0) {
            logger.warn("create default user 'admin' with password 'admin' now.");

            User user = new User("admin", "admin", "admin@example.com");
            userDAO.save(user);

            logger.info("{} users found in database after user creation", userDAO.count());
        }
    }

}
