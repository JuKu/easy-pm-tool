package com.jukusoft.pm.tool.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
public class TestController {

    @RequestMapping(path = "/test", method = RequestMethod.GET)
    public String testPage () {
        return "test";
    }

    @RequestMapping("/test1")
    @ResponseBody
    public String handleRequest (Locale locale) {
        return String.format("Request received. Language: %s, Country: %s %n",
                locale.getLanguage(), locale.getDisplayCountry());
    }

}
