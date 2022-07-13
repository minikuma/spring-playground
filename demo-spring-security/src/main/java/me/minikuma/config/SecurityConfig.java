package me.minikuma.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 사용자 정의 보안 기능
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailsService userDetailsService;

    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .authorizeRequests()
                .anyRequest()
                .authenticated()
        ;

        http
                .formLogin()
                //.loginPage("/loginPage") // 사용자 정의 로그인 페이지
                .defaultSuccessUrl("/") // 로그인 성공 후 이동 페이지
                .failureUrl("/login")
                .usernameParameter("userId") // 아이디 파라미터 설정
                .passwordParameter("pwd") // 패스워드 파라미터 설정
                .loginProcessingUrl("/login_process") // 로그인 form action url
                .successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                        System.out.println(">>>> authentication :: " + authentication.getName());
                        response.sendRedirect("/");
                    }
                }) // 로그인 성공 후 핸들러
                .failureHandler(new AuthenticationFailureHandler() {
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
                        System.out.println(">>>> exception :: " + exception.getMessage());
                        response.sendRedirect("/login");
                    }
                }) // 로그인 실패 후 핸들러
                .permitAll()
        ;

        // logout
        http
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .deleteCookies("JESSIONID", "remember-me")
                .addLogoutHandler(new LogoutHandler() {
                    @Override
                    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
                        HttpSession session = request.getSession();
                        session.invalidate();
                    }
                })
                .logoutSuccessHandler(new LogoutSuccessHandler() {
                    @Override
                    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                        response.sendRedirect("/login");
                    }
                })
                .deleteCookies("remember-me")
        ;

        // remember me 활성화
        http
                .rememberMe()
                .rememberMeParameter("remember") // 기본 파라미터 명은 remember-me
                .tokenValiditySeconds(3600) // Default 14일
                .alwaysRemember(false) // remember me 기능이 활성화되지 않아도 항상 실행
                .userDetailsService(userDetailsService)
        ;

        // 동시 세션 제어
        http
                .sessionManagement()
                    .maximumSessions(1) // -1 (무제한)
                    .maxSessionsPreventsLogin(false) // false: 기존 세션 만료
                    .expiredUrl("/expired")
                .and()
                    .invalidSessionUrl("/invalid")
        ;

        // 세션 고정 보호
        http
                .sessionManagement()
                .sessionFixation()
                .changeSessionId(); // none, migrateSession, newSession

        // 세션 정책
        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
        ;

        return http.build();
    }
}
