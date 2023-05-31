package com.diplomaproject.tendersystembackend.config;

import com.diplomaproject.tendersystembackend.filter.JwtAccessDeniedHandler;
import com.diplomaproject.tendersystembackend.filter.JwtAuthEntryPoint;
import com.diplomaproject.tendersystembackend.filter.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final JwtAuthEntryPoint jwtAuthEntryPoint;
    private final JwtFilter jwtFilter;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final UserDetailsService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SecurityConfig(JwtAuthEntryPoint jwtAuthEntryPoint,
                          JwtFilter jwtFilter,
                          JwtAccessDeniedHandler jwtAccessDeniedHandler,
                          @Qualifier("userDetailsService") UserDetailsService userService,
                          PasswordEncoder passwordEncoder) {
        this.jwtAuthEntryPoint = jwtAuthEntryPoint;
        this.jwtFilter = jwtFilter;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors().and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .antMatchers("/user/register/**", "/user/login", "/tender/**").permitAll()
                .anyRequest().authenticated().and()
                .exceptionHandling()
                .accessDeniedHandler(jwtAccessDeniedHandler)
                .authenticationEntryPoint(jwtAuthEntryPoint);

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService)
                .passwordEncoder(passwordEncoder);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
