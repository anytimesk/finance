package com.dastro.finance.finance_manager.config;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import com.dastro.finance.finance_manager.util.CookieUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authz -> authz
                                .requestMatchers("/", "/index.html", "/logout", "/oauth2/**").permitAll()
                                .requestMatchers("/assets/**", "/css/**", "/js/**").permitAll()
                                .anyRequest().authenticated()
                )
                .oauth2Login(login -> login
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true)
                        .failureUrl("/login?error=true")
                        .successHandler(authenticationSuccessHandler()))
                .logout(logout -> logout
                        .logoutSuccessHandler(logoutSuccessHandler())
                        .permitAll());

        return http.build();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                                Authentication authentication) throws IOException {
                response.sendRedirect("/user");
            }
        };
    }

    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        return new LogoutSuccessHandler() {
            @Override
            public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
                // Session End Process
                request.getSession().invalidate();

                //response.addCookie(createCookie("JSESSINID", "", 0));
                response.addCookie(CookieUtil.createCookie("JSESSINID", "", 0));

                // When logout then Redirection Uri
                response.sendRedirect("/login");
            }
        };
    }
}
