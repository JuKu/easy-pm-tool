package com.jukusoft.pm.tool.basic.error;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class Error500Controller {

    @RequestMapping(value = "/errors/error500", method = {RequestMethod.GET, RequestMethod.POST})
    public String error404 () {
        return "errors/error500";
    }

}
