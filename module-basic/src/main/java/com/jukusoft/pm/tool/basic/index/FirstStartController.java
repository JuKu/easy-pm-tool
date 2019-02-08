package com.jukusoft.pm.tool.basic.index;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FirstStartController {

    @GetMapping("/first-start")
    public String firstStart () {
        return "pages/first-start";
    }

}
