package com.jukusoft.pm.tool.app;

import com.jukusoft.pm.tool.def.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;

@SpringBootApplication(scanBasePackages = "com.jukusoft.pm.tool")
@RestController
@EnableCaching
@EnableAutoConfiguration
@EntityScan("com.jukusoft.pm.tool")
@EnableJpaRepositories("com.jukusoft.pm.tool")
@ComponentScan(basePackages = {"com.jukusoft.pm.tool", "com.jukusoft.pm.tool.basic"})
@EnableGlobalMethodSecurity(securedEnabled = true)
public class Main {

    protected static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws IOException {
        //parse config
        logger.info("parse config ./config/runtime");
        Config.loadDir(new File("./config/runtime/"));

        SpringApplication.run(Main.class, args);
    }

}
