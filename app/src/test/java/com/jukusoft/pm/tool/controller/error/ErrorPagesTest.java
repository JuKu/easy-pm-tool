package com.jukusoft.pm.tool.controller.error;

import com.jukusoft.pm.tool.basic.error.Error404Controller;
import com.jukusoft.pm.tool.def.config.Config;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {com.jukusoft.pm.tool.app.Main.class})
@ActiveProfiles("test")
@Import(value = {Error404Controller.class})
public class ErrorPagesTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeClass
    public static void beforeAll () throws IOException {
        System.err.println("load config");
        Config.loadDir(new File("../config/test/"));
    }

    @Test
    public void checkForError404Page() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/errors/error404",
                String.class)).contains("404");
    }

    @Test
    public void testInit () {
        //
    }

}
