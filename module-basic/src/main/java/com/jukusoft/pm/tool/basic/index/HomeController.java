package com.jukusoft.pm.tool.basic.index;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String index(Model model, Authentication authentication, RedirectAttributes attributes) {
        return "pages/home";
    }

}
