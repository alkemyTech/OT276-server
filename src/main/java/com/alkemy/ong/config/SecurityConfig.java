package com.alkemy.ong.config;

import com.alkemy.ong.config.exception.handler.AuthenticationEntryPointHandler;
import com.alkemy.ong.config.exception.handler.CustomAccessDeniedHandler;
import com.alkemy.ong.config.security.JwtRequestFilter;
import com.alkemy.ong.core.usecase.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    private final UserDetailsService userDetailsService;

    private final JwtRequestFilter jwtRequestFilter;

    private final UserService userService;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                .antMatchers("/api/docs/**", "/api/swagger-ui/**", "/auth/login", "/auth/register").permitAll()
         .and().exceptionHandling()
         .authenticationEntryPoint(new AuthenticationEntryPointHandler())
         .accessDeniedHandler(new CustomAccessDeniedHandler())
         .and().sessionManagement()
         .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
         .and().addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }
    @Autowired
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
    auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
;    }


}
