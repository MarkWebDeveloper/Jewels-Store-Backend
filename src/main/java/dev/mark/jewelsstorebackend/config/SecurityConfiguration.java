package dev.mark.jewelsstorebackend.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


@Configuration
@EnableWebSecurity
@EnableJpaAuditing
public class SecurityConfiguration {
    @Value("${api-endpoint}")
    String endpoint;

    // JpaUserDetailService jpaUserDetailService;

    

    // public SecurityConfiguration(JpaUserDetailService jpaUserDetailService) {
    //     this.jpaUserDetailService = jpaUserDetailService;
    // }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        http
                .cors(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable())
                .formLogin(form -> form.disable());
                // .logout(out -> out
                //         .logoutUrl(endpoint + "/logout")
                //         .deleteCookies("JSSESIONID"))
                // .authorizeHttpRequests(auth -> auth
                //         .requestMatchers(HttpMethod.GET, endpoint + "/login").hasAnyRole("ADMIN","USER")
                //         .requestMatchers(HttpMethod.GET, endpoint + "/users").hasRole("ADMIN")
                //         .requestMatchers(HttpMethod.PUT, endpoint + "/users/eventSignUp/{id}").hasAnyRole("ADMIN","USER")
                //         .requestMatchers(HttpMethod.GET, endpoint + "/cities").permitAll()
                //         .requestMatchers(HttpMethod.GET, endpoint + "/events").permitAll()
                //         .requestMatchers(HttpMethod.POST, endpoint + "/events").hasRole("ADMIN")
                //         .requestMatchers(HttpMethod.POST, endpoint + "/images").permitAll()
                //         .requestMatchers(HttpMethod.POST, endpoint + "/users").permitAll()
                //         .requestMatchers(HttpMethod.GET, endpoint + "/images/**").permitAll()
                //         .anyRequest().authenticated())
                // .userDetailsService(jpaUserDetailService)
                // .httpBasic(Customizer.withDefaults())
                // .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED));
        
        http.headers(header -> header.frameOptions(frame -> frame.sameOrigin()));

        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("Content-Type", "Authorization"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
    }

} 