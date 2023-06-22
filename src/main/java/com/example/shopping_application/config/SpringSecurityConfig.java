package com.example.shopping_application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class SpringSecurityConfig {

    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;

    public SpringSecurityConfig(PasswordEncoder passwordEncoder,
                                UserDetailsService userDetailsService) {
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> authorizationManagerRequestMatcherRegistry
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/products").permitAll()
                        .requestMatchers("/search").permitAll()
                        .requestMatchers("/products/list").permitAll()
                        .requestMatchers("/getImage").permitAll()
                        .requestMatchers("/user/register").permitAll()
                        .requestMatchers("/user/verify").permitAll()
                        .requestMatchers("/user/forgotPassword").permitAll()
                        .requestMatchers("/user/admin").hasAuthority("ADMIN")
                        .requestMatchers("/user/delivery").hasAnyAuthority("DELIVERY","ADMIN")
                        .requestMatchers("/user/delivery/**").hasAnyAuthority("DELIVERY","ADMIN")
                        .requestMatchers("/img/**").permitAll()
                        .requestMatchers("/vendor/**").permitAll()
                        .requestMatchers("/css/**").permitAll()
                        .requestMatchers("/fonts/**").permitAll()
                        .requestMatchers("/js/**").permitAll()
                        .requestMatchers("/image/**").permitAll()
                        .anyRequest().authenticated())
                .formLogin(httpSecurityFormLoginConfigurer -> httpSecurityFormLoginConfigurer
                        .loginPage("/customLogin")
                        .defaultSuccessUrl("/customSuccessLogin",true)
                        .loginProcessingUrl("/login")
                        .permitAll())
                .logout(httpSecurityLogoutConfigurer -> httpSecurityLogoutConfigurer
                        .logoutSuccessUrl("/")
                        .permitAll());
        return httpSecurity.build();
    }


    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return authenticationProvider;
    }

}