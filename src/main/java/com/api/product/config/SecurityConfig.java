package com.api.product.config;

import com.api.product.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
@SuppressWarnings("java:S5344")
@Log4j2
public class SecurityConfig {

    private final UserService userService;

    @Bean
    public DefaultSecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/api/products/admin/**").hasRole("ADMIN")
                .requestMatchers("/api/products/**").hasRole("USER")
                .requestMatchers("/actuator/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .and()
                .httpBasic();
        return httpSecurity.build();
    }

    @Autowired
    public void userDetailsService(AuthenticationManagerBuilder auth) throws Exception {

        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        auth.inMemoryAuthentication()
                .withUser("davi2")
                .password(passwordEncoder.encode("1005"))
                .roles("ADMIN, USER")
                .and()
                .withUser("teste2")
                .password(passwordEncoder.encode("1005"))
                .roles("USER");

        auth.userDetailsService(userService)
                .passwordEncoder(passwordEncoder);
    }
}

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//
//        http.csrf().disable().authorizeHttpRequests(authHttpReq -> authHttpReq
//                .anyRequest()
//                .authenticated()
//        ).formLogin()
//                .and()
//                .httpBasic(withDefaults()).;
//       return http.build();
//    }
//
//    @Bean
//    public InMemoryUserDetailsManager userDetailsService() throws Exception {
//
//        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
//        log.info("Password encoded {}", passwordEncoder.encode("1005"));
//        UserDetails user = User.withUsername("davi2")
//                .password(passwordEncoder.encode("1005"))
//                .roles("ADMIN")
//                .build();
//        UserDetails user2 = User.withUsername("teste2")
//                .password(passwordEncoder.encode("1005"))
//                .roles("USER")
//                .build();
//        return new InMemoryUserDetailsManager(user, user2);
//    }