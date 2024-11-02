package com.api.jaebichuri.global.config;

import com.api.jaebichuri.auth.jwt.JwtFilter;
import com.api.jaebichuri.auth.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final AccessDeniedHandler accessDeniedHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        //csrf disable
        httpSecurity
            .csrf((auth) -> auth.disable());

        //Form login 방식 disable
        httpSecurity
            .formLogin((auth) -> auth.disable());

        //http basic 인증 방식 disable
        httpSecurity
            .httpBasic((auth) -> auth.disable());

        //session 설정 (jwt 사용 -> stateless)
        httpSecurity
            .sessionManagement((session) -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        //경로별 인가 작업
        httpSecurity
            .authorizeHttpRequests((auth) -> auth
                .requestMatchers("/", "/swagger-ui/**", "/v3/api-docs/**", "/v2/swagger-config",
                    "/swagger-resources/**").permitAll()
                .requestMatchers("/oauth2/kakao/**").permitAll()
                .requestMatchers("/auctions/**", "/products/**").permitAll()
                .anyRequest().hasAnyAuthority("USER", "ADMIN")
            );

        // jwt필터 추가
        httpSecurity
            .addFilterBefore(new JwtFilter(jwtUtil, userDetailsService),
                UsernamePasswordAuthenticationFilter.class);

        httpSecurity
            .exceptionHandling((exceptions) -> exceptions
                .accessDeniedHandler(accessDeniedHandler)
                .authenticationEntryPoint(authenticationEntryPoint)
            );

        return httpSecurity.build();
    }
}