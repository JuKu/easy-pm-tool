package com.jukusoft.pm.tool.basic.index;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller
public class IndexController {

    @GetMapping("/")
    public String index(Model model, Authentication authentication) {
        //TODO: check, if user is logged in

        return "index";
    }

}
