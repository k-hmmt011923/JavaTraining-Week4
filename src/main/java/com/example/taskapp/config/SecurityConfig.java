package com.example.taskapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails user = User.withUsername("testuser")
                .password("{noop}password")
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests(auth -> auth
                .requestMatchers("/login", "/css/**", "/h2-console/**").permitAll()
                .requestMatchers("/api/**").permitAll()
                .requestMatchers("/tasks/**").authenticated()
                .anyRequest().permitAll()
                )
                .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/tasks", true)
                .permitAll()
                )
                .logout(Customizer.withDefaults())
                .csrf(csrf -> csrf
                .ignoringRequestMatchers("/api/**", "/h2-console/**")
                )
                .headers(headers -> headers
                .frameOptions(frame -> frame.disable())
                );

        return http.build();
    }
}
