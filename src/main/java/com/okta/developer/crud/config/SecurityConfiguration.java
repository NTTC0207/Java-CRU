package com.okta.developer.crud.config;

import com.okta.developer.crud.Repository.UsersRepository;
import com.okta.developer.crud.web.CookieCsrfFilter;
import com.okta.developer.crud.web.SpaWebFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SimpleSavedRequest;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class SecurityConfiguration {
    private final UsersRepository usersRepository;

    public SecurityConfiguration(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, UsersRepository usersRepository) throws Exception {

        http
            .authorizeHttpRequests((authz) -> authz
                .requestMatchers("/", "/index.html", "/static/**",
                    "/*.ico", "/*.json", "/*.png", "/api/user").permitAll()
                .anyRequest().authenticated()
            )
            .csrf((csrf) -> csrf
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler())
            )
            .addFilterAfter(new CookieCsrfFilter(), BasicAuthenticationFilter.class)
            .addFilterAfter(new SpaWebFilter(), BasicAuthenticationFilter.class)
            .oauth2Login()
                .userInfoEndpoint();

        return http.build();
    }

    @Bean
    public RequestCache refererRequestCache() {
        return new HttpSessionRequestCache() {
            @Override
            public void saveRequest(HttpServletRequest request, HttpServletResponse response) {
                String referrer = request.getHeader("referer");
                if (referrer == null) {
                    referrer = request.getRequestURL().toString();
                }
                request.getSession().setAttribute("SPRING_SECURITY_SAVED_REQUEST",
                    new SimpleSavedRequest(referrer));

            }
        };
    }



}
