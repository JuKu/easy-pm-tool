package com.jukusoft.pm.tool.basic.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;
import java.util.Optional;

@Controller
public class LoginController {

    protected static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    protected MessageSource messageSource;

    @RequestMapping(path = "/login", method = {RequestMethod.GET, RequestMethod.POST})
    public String loginPage (HttpServletRequest request, CsrfToken csrfToken, Model model, Authentication authentication, @RequestParam("error") Optional<String> errorOpt) {
        if (authentication != null  && authentication.isAuthenticated()) {
            logger.info("user '{}' is already authentificated!", authentication.getName());
            return "redirect:/home";
        }

        //model.addAttribute("_csrf", csrfToken);

        model.addAttribute("form_action", "/perform_login");
        model.addAttribute("csrf_parameter_name", csrfToken.getParameterName());
        model.addAttribute("csrf_token", csrfToken.getToken());

        boolean showError = errorOpt.isPresent();
        model.addAttribute("is_error", showError);

        return "login";
    }

}
