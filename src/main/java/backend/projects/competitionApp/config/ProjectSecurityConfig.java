package backend.projects.competitionApp.config;

import backend.projects.competitionApp.filter.JWTGeneratorFilter;
import backend.projects.competitionApp.filter.JWTValidatorFilter;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
public class ProjectSecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        CsrfTokenRequestAttributeHandler requestHandler = new CsrfTokenRequestAttributeHandler();
        requestHandler.setCsrfRequestAttributeName("_csrf");

        http.sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf((csrf) -> csrf.csrfTokenRequestHandler(requestHandler).ignoringRequestMatchers("/api/register", "/api/login", "/api/user/{user_id}", "/api/user/{user_id}",
                                "/api/room", "/api/room/search","/api/room/{id}", "/api/room/{room_id}/user/{user_id}/request", "/api/room/{room_id}/user/{user_id}/dataplayer", "/api/user/{user_id}/room/requests",
                                "/api/room/{room_id}/user/{user_id}", "/api/dailyProfit/room/{room_id}/user/{user_id}/profits"
                        )
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                .cors(httpSecurityCorsConfigurer ->
                    httpSecurityCorsConfigurer.configurationSource(new CorsConfigurationSource() {
                        @Override
                        public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                            CorsConfiguration config = new CorsConfiguration();
                            config.setAllowedOrigins(Collections.singletonList("http://localhost:3000"));
                            config.setAllowedMethods(Collections.singletonList("*"));
                            config.setAllowCredentials(true);
                            config.setAllowedHeaders(Collections.singletonList("*"));
                            config.setExposedHeaders(Arrays.asList("Authorization"));
                            config.setMaxAge(3600L);
                            return config;
                        }
                }))
                .addFilterAfter(new JWTGeneratorFilter(), BasicAuthenticationFilter.class)
                .addFilterBefore(new JWTValidatorFilter(), BasicAuthenticationFilter.class)
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/api/login").permitAll()
                        .requestMatchers("/api/register").permitAll()
                        .requestMatchers("/api/user/{user_id}").hasRole("USER")
                        .requestMatchers("/api/user/{user_id}").hasRole("USER")
                        .requestMatchers("/api/room").hasRole("USER")
                        .requestMatchers("/api/room/{id}").hasRole("USER")
                        .requestMatchers("/api/room/search").hasRole("USER")
                        .requestMatchers("/api/user/{user_id}/room/requests").hasRole("USER")
                        .requestMatchers("/api/room/{room_id}/user/{user_id}").hasRole("USER")
                        .requestMatchers("/api/room/{room_id}/user/{user_id}/request").hasRole("USER")
                        .requestMatchers("/api/room/{room_id}/user/{user_id}/dataplayer").hasRole("USER")
                        .requestMatchers("/api/dailyProfit/room/{room_id}/user/{user_id}/profits").hasRole("USER")
                        .anyRequest().permitAll())
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
