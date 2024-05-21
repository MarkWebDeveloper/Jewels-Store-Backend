package dev.mark.jewelsstorebackend.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableJpaAuditing
public class SecurityConfiguration {
    @Value("${api-endpoint}")
    String endpoint;

    // JpaUserDetailService jpaUserDetailService;

    public SecurityConfiguration() {
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        
        http
                .cors(withDefaults())
                .csrf(csrf -> csrf.disable())
                // .formLogin(form -> form.disable())
                // .logout(out -> out
                //         .logoutUrl(endpoint + "/logout")
                //         .deleteCookies("JSSESIONID"))
                .authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.GET, "/authorized").permitAll()
                .requestMatchers(HttpMethod.GET, endpoint + "/products/**").hasAuthority("SCOPE_read")
                .requestMatchers(HttpMethod.POST, endpoint + "/products/**").hasAuthority("SCOPE_write")
                .requestMatchers(HttpMethod.PUT, endpoint + "/products/**").permitAll()
                .requestMatchers(HttpMethod.DELETE, endpoint + "/products/**").hasAuthority("SCOPE_write")
                .requestMatchers(HttpMethod.GET, endpoint + "/categories/**").permitAll()
                .requestMatchers(HttpMethod.GET, endpoint + "/imgs/**").permitAll()
                .requestMatchers(HttpMethod.GET, endpoint + "/images/**").permitAll()
                .requestMatchers(HttpMethod.POST, endpoint + "/images/**").permitAll()
                .requestMatchers(HttpMethod.DELETE, endpoint + "/images/**").permitAll()
                .anyRequest().authenticated())
                // .userDetailsService(jpaUserDetailService)
                // .httpBasic(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .oauth2Login(login -> login.loginPage("/oauth2/authorization/client-app"))
                .oauth2Client(withDefaults())
                .oauth2ResourceServer(resourceServer -> resourceServer.jwt(withDefaults()));
        
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