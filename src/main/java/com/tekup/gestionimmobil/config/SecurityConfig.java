package com.tekup.gestionimmobil.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * Bean for password encoding using BCrypt.
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Security filter chain configuration.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // Disable CSRF for simplicity (not recommended for production)
            .csrf(csrf -> csrf.disable())
            
            // Configure authorization rules
            .authorizeHttpRequests(authorize -> authorize
                // Public URLs
                .requestMatchers("/", "/acceuil", "/auth/**", "/css/**").permitAll()
                
                // URLs restricted to ADMIN role
                .requestMatchers("/admin/**").hasRole("ADMIN")
                
                // URLs restricted to CLIENT role
                .requestMatchers("/clients/**").hasRole("CLIENT")
                
                // All other URLs require authentication
                .anyRequest().authenticated()
            )
            
            // Configure form-based login
            .formLogin(form -> form
                .loginPage("/auth/signin")                // Custom login page
                .defaultSuccessUrl("/acceuil", true)      // Redirect after successful login
                .permitAll()                              // Allow everyone to see the login page
            )
            
            // Configure logout functionality
            .logout(logout -> logout
                .logoutUrl("/auth/logout")                // URL to trigger logout
                .logoutSuccessUrl("/acceuil")             // Redirect after logout
                .invalidateHttpSession(true)              // Invalidate session
                .deleteCookies("JSESSIONID")              // Delete session cookie
                .permitAll()                              // Allow everyone to access logout
            )
            
            // Optional: Remember-me configuration
            .rememberMe(remember -> remember
                .key("uniqueAndSecret")                   // Key for token generation
                .tokenValiditySeconds(86400)              // Token validity (e.g., 1 day)
            );
// 
        return http.build();
    }
}