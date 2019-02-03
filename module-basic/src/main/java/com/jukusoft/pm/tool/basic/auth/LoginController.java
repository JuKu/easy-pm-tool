package com.jukusoft.pm.tool.basic.auth;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String loginPage (HttpServletRequest request, CsrfToken csrfToken, Model model) {
        //model.addAttribute("_csrf", csrfToken);

        model.addAttribute("form_action", "/perform_login");
        model.addAttribute("csrf_parameter_name", csrfToken.getParameterName());
        model.addAttribute("csrf_token", csrfToken.getToken());

        model.addAttribute("is_error", request.getAttribute("error") != null);

        return "login";
    }

}
