package com.springDataJpa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) {
        httpSecurity.csrf(csrf -> csrf.disable()).authorizeHttpRequests(
                auth -> auth.requestMatchers(HttpMethod.POST, "/api/v1/users").permitAll()
                        .requestMatchers("/api/v1/users/*/orders/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers("/api/v1/users/**").hasRole("ADMIN")
                        .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults());

        return httpSecurity.build();
    }

    @Bean
    UserDetailsManager userDetailsManager(PasswordEncoder passwordEncoder) {
        UserDetails admin = User
                .withUsername("admin")
                .password(passwordEncoder.encode("surajit"))
                .roles("ADMIN")
                .build();

        UserDetails user = User
                .withUsername("user")
                .password(passwordEncoder.encode("surajit"))
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(admin, user);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
