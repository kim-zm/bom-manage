package com.web.boot.config;

import java.util.UUID;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import de.codecentric.boot.admin.server.config.AdminServerProperties;
import io.netty.handler.codec.http.HttpMethod;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final AdminServerProperties adminServer;
 
    public SecurityConfig(AdminServerProperties adminServer) {
        this.adminServer = adminServer;
    }
 
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	String contextPath = this.adminServer.getContextPath();
        SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
        successHandler.setTargetUrlParameter("redirectTo");
        successHandler.setDefaultTargetUrl(contextPath + "/");
 
        http
            .authorizeRequests()
                .antMatchers(contextPath + "/assets/**").permitAll()
                .antMatchers(contextPath + "/login").permitAll()
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage(contextPath + "/login")
                .successHandler(successHandler)
                .and()
            .logout()
                .logoutUrl(contextPath + "/logout")
                .and()
            .httpBasic()
                .and()
            .csrf()
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .ignoringAntMatchers(
                  contextPath + "/instances", 
                  contextPath + "/actuator/**"
            );
    }
}
