package com.jukusoft.pm.tool.basic.index;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class IndexController {

    protected static Logger logger = LoggerFactory.getLogger(IndexController.class);

    @GetMapping("/")
    public String index(Model model, Authentication authentication, RedirectAttributes attributes) {
        //check, if user is logged in
        if (authentication != null &&
                authentication.isAuthenticated()) {
            logger.info("user is logged in: " + authentication.getName());

            //redirect to /home
            return "redirect:/home";
        } else {
            logger.debug("user isn't logged in, redirect to /login");

            //redirect to login page
            return "redirect:/login";
        }
    }

}
