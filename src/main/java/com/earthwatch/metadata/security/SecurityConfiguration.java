package com.earthwatch.metadata.security;

import com.earthwatch.metadata.security.authtokens.AuthTokenAuthenticationFilter;
import com.earthwatch.metadata.security.authtokens.AuthTokenService;
import com.earthwatch.metadata.security.jwt.JwtAuthenticationFilter;
import com.earthwatch.metadata.security.jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;

@Configuration
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //TODO: In production these should not be disabled
        http.csrf().disable().cors().disable();
        http.authorizeRequests(authorize ->
                authorize.antMatchers(HttpMethod.POST, "/customers/**").permitAll()
                        .anyRequest().authenticated());
        http.addFilterBefore(new JwtAuthenticationFilter(jwtService), AnonymousAuthenticationFilter.class);
        http.addFilterBefore(new AuthTokenAuthenticationFilter(authTokenService), AnonymousAuthenticationFilter.class);
        // Prevents a session object being created for a request. Without this the same request is cached and returns the same response irrespective of headers
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        return http.build();
    }

    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthTokenService authTokenService;
}
