package com.jukusoft.pm.tool.app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        //session management
        /*http
                .sessionManagement()
                .invalidSessionUrl("/sessionInvalid");*/

        //protect all pages from unauthentificated users
        http
                .authorizeRequests()
                //allow access to / and /login to any user (also unauthentificated ones)
                .antMatchers("/", "/login", "/test", "/actuator", "/actuator/*", "/files/public/**")
                .permitAll()
                //lock out any unauthentificated users from any other page
                .anyRequest()
                .authenticated();

        http
                .formLogin()
                //.loginPage("/login")
                .loginProcessingUrl("/perform_login")
                .defaultSuccessUrl("/homepage.html", true);

        //login
        /*http
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/perform_login")
                .defaultSuccessUrl("/home", true);
                //.failureUrl("/login.html?error=true")
                //.failureHandler(authenticationFailureHandler())*/

        //configure logout page, see also https://www.baeldung.com/spring-security-logout
        http
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll();
    }

    /*@Bean
    @Override
    public UserDetailsService userDetailsService() {
        UserDetails user =
                User.withDefaultPasswordEncoder()
                        .username("user")
                        .password("password")
                        .roles("USER")
                        .build();

        return new InMemoryUserDetailsManager(user);
    }*/

}
