package com.dastro.finance.finance_manager.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import com.dastro.finance.finance_manager.service.impl.OAuth2UserServiceImpl;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private OAuth2UserServiceImpl oAuth2UserService; 

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                .csrf(csrf -> csrf
                    .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()) // CSRF 토큰 저장소 설정
                )
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/", "/login", "/oauth2/**").permitAll()
                        .requestMatchers("/assets/**", "/css/**", "/js/**").permitAll()
                        .anyRequest().authenticated())
                .oauth2Login(login -> login
                        .loginPage("/login") // 사용자 정의 로그인 페이지
                        .defaultSuccessUrl("/", true) // 로그인 성공 시 리다이렉트
                        .failureUrl("/login?error=true") // 로그인 실패 시 리다이렉트
                        .userInfoEndpoint(userInfo -> userInfo.userService(oAuth2UserService))
                        .permitAll()
                        )
                .logout(logout -> logout
                        .logoutSuccessUrl("/") // 로그아웃 성공 후 리다이렉트할 URL
                        .invalidateHttpSession(true) // 세션 무효화
                        .clearAuthentication(true) // 인증 정보 삭제
                        .permitAll()); // 로그아웃을 누구나 허용

        return http.build();
    }

}
