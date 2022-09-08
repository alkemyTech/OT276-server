package com.alkemy.ong.config;

import com.alkemy.ong.config.exception.handler.AuthenticationEntryPointHandler;
import com.alkemy.ong.config.exception.handler.CustomAccessDeniedHandler;
import com.alkemy.ong.config.security.JwtRequestFilter;
import com.alkemy.ong.ports.input.rs.api.ApiConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailService;
    private final JwtRequestFilter jwtRequestFilter;
    private final PasswordEncoder passwordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests()
                .antMatchers("/api/docs/**", "/api/swagger-ui/**", "/v3/api-docs/**", "/auth/login", "/auth/register").permitAll()
                .antMatchers(HttpMethod.GET, "/v1/organizations/public/{id}").permitAll()
                .antMatchers(HttpMethod.POST, ApiConstants.CONTACTS_URI).permitAll()
                .antMatchers(HttpMethod.PUT, ApiConstants.MEMBERS_URI + "/{id}").authenticated()
                .antMatchers(HttpMethod.GET, ApiConstants.USERS_URI).hasRole("ADMIN")
                .antMatchers(HttpMethod.PATCH, ApiConstants.USERS_URI + "/{id}").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.DELETE, ApiConstants.USERS_URI + "/{id}").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.GET, ApiConstants.SLIDES_URI + "/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, ApiConstants.CATEGORIES_URI + "/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, ApiConstants.CATEGORIES_URI + "/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET).authenticated()
                .antMatchers(HttpMethod.POST).hasRole("ADMIN")
                .antMatchers(HttpMethod.PATCH).hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE).hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT).hasRole("ADMIN")
                .and().exceptionHandling()
                .authenticationEntryPoint(new AuthenticationEntryPointHandler())
                .accessDeniedHandler(new CustomAccessDeniedHandler())
                .and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService).passwordEncoder(passwordEncoder);
    }

}
