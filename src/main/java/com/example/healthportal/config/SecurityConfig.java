package com.example.healthportal.config;

import com.example.healthportal.Security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor

public class SecurityConfig{


//cors to allow two portnumbers
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowCredentials(true);
        config.setAllowedOrigins(List.of(
                "http://127.0.0.1:5500",
                "http://127.0.0.1:5501"
        )); // frontend
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }



    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain SecurityFilterchain(HttpSecurity http) throws Exception{

        http
                .csrf(csrf -> csrf.disable())

                //  proper CORS usage
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                .authorizeHttpRequests(auth -> auth

                        // PUBLIC
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/doctor/public/**").permitAll()

                        //  allow USER to access patient APIs
                        .requestMatchers("/api/patient/**").hasRole("USER")

                        //  allow both roles
                        .requestMatchers("/api/doctor/getall").hasAnyRole("USER","DOCTOR")
                        .requestMatchers("/api/doctor/search").hasAnyRole("USER","DOCTOR")
                        .requestMatchers("/api/slots/**").hasAnyRole("USER","DOCTOR")

                        //  doctor-only endpoints
                        .requestMatchers("/api/doctor/**").hasRole("DOCTOR")

                        //  booking = USER only
                        .requestMatchers(HttpMethod.POST,"/api/appointments/book-slot").hasRole("USER")

                        //  doctor actions
                        .requestMatchers(HttpMethod.PUT,"/api/appointments/**").hasRole("DOCTOR")

                        //  allow USER to view their appointments
                        .requestMatchers(HttpMethod.GET,"/api/appointments/**").hasAnyRole("USER","DOCTOR")

                        .anyRequest().authenticated()
                )

                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
