package com.jukusoft.pm.tool.basic.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Controller
public class LoginController {

    protected static final Logger logger = LoggerFactory.getLogger(LoginController.class);

   @RequestMapping(path = "/login", method = {RequestMethod.GET, RequestMethod.POST})
    public String loginPage (HttpServletRequest request, HttpServletResponse response, CsrfToken csrfToken, Model model, @RequestParam("error") Optional<String> errorOpt) {
        //model.addAttribute("_csrf", csrfToken);

        model.addAttribute("form_action", "/perform_login");
        model.addAttribute("csrf_parameter_name", csrfToken.getParameterName());
        model.addAttribute("csrf_token", csrfToken.getToken());

        boolean showError = request.getRequestURL().toString().contains("?error");

        logger.info("request URL: " + request.getRequestURL().toString());
        logger.info("request URI: " + request.getRequestURI());

        logger.info("error is set: " + errorOpt.isPresent());

        if (errorOpt.isPresent()) {
            logger.info("error value: " + errorOpt.get());
        }

        if (showError) {
            logger.debug("show error, because login credentials are wrong.");
        }

        showError = errorOpt.isPresent();
        model.addAttribute("is_error", showError);

        return "login";
    }

}
