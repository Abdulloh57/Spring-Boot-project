package library.uz.springbootwithjpa.config;

import library.uz.springbootwithjpa.service.auth.CustomUserDetailService;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final CustomUserDetailService customUserDetailService;
    private final PasswordEncoder passwordEncoder;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/admin/login", "/admin/register")
                        .permitAll()
                        .anyRequest()
                        .authenticated())

                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                )
                .formLogin(form -> form
                        .loginProcessingUrl("/admin/login")
                        .successHandler((request, response, authentication) -> {
                            response.setStatus(200);
                            response.getWriter().write("Login successful");
                        })
                        .failureHandler((request, response, exception) -> {
                            response.setStatus(401);
                            response.getWriter().write(exception.getMessage());
                        }))
                .logout(logout -> logout
                        .logoutUrl("/admin/logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .clearAuthentication(true)
                        .addLogoutHandler((request , response, autentification) ->{
                            response.setStatus(200);
                        }))
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:5500")); // frontend manzilingiz
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true); // cookie/session asosida login uchun MUHIM

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(customUserDetailService);
        provider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(provider);
    }

}
