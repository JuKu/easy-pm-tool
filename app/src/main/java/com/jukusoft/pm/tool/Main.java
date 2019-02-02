package com.jukusoft.pm.tool;

import com.jukusoft.pm.tool.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;

@SpringBootApplication(scanBasePackages = "com.jukusoft.pm.tool")
@RestController
public class Main {

    protected static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws IOException {
        //parse config
        logger.info("parse config ./config/runtime");
        Config.loadDir(new File("../config/runtime/"));

        SpringApplication.run(Main.class, args);
    }

}
