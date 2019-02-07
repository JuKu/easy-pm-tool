package com.jukusoft.pm.tool.app.config;

import com.jukusoft.pm.tool.basic.error.CustomAccessDeniedHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.AccessDeniedHandler;

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
                .antMatchers("/", "/login", "/test", "/actuator", "/actuator/*", "/files/public/**", "/res/public/**", "/errors/*", "/error", "/test2")
                .permitAll()
                //lock out any unauthentificated users from any other page
                .anyRequest()
                .authenticated();

        http
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/perform_login")
                .failureForwardUrl("/login?error=true")
                .defaultSuccessUrl("/home", true);

        //configure logout page, see also https://www.baeldung.com/spring-security-logout
        http
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll();

        //exception handling
        /*http
                .exceptionHandling()
                .accessDeniedPage("/errors/error403");*/
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler(){
        return new CustomAccessDeniedHandler();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        /*auth.jdbcAuthentication()
        auth.ldapAuthentication()
        auth.authenticationProvider(customAuthProvider);*/
        /*auth.inMemoryAuthentication()
                .withUser("memuser")
                .password(encoder().encode("pass"))
                .roles("USER");*/
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
