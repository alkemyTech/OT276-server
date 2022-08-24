package com.alkemy.ong.config;

import com.alkemy.ong.config.security.JwtRequestFilter;
import com.alkemy.ong.core.usecase.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    UserServiceImpl userService;

    @Autowired
    private JwtRequestFilter filter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .httpBasic().disable()
                .cors()
                .and()
                .authorizeHttpRequests()
                .antMatchers("/auth/**").permitAll()
                .antMatchers(HttpMethod.GET).authenticated()
                .antMatchers(HttpMethod.PATCH, "/**").hasAnyRole("ROLE_ADMIN")
                .antMatchers(HttpMethod.PUT, "/**").hasAnyRole("ROLE_ADMIN")
                .antMatchers(HttpMethod.POST, "/**").hasAnyRole("ROLE_ADMIN")
                .antMatchers(HttpMethod.DELETE, "/**").hasAnyRole("ROLE_ADMIN")

                .anyRequest().authenticated()
                .and()
                .userDetailsService(userService)
                .exceptionHandling()
                .authenticationEntryPoint(

                        (request, response, authException) -> response.sendError(HttpServletResponse.SC_FORBIDDEN,
                                "Unauthorized"))
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
    }

}
