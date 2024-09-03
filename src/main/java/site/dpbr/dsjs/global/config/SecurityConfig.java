package site.dpbr.dsjs.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import site.dpbr.dsjs.domain.shared.Role;
import site.dpbr.dsjs.global.filter.ExceptionHandleFilter;
import site.dpbr.dsjs.global.jwt.JwtProvider;
import site.dpbr.dsjs.global.jwt.TokenAuthenticationFilter;

import java.util.Arrays;
import java.util.List;

import static org.springframework.http.HttpStatus.FORBIDDEN;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtProvider tokenProvider;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic(AbstractHttpConfigurer::disable)
                .cors((cors) -> cors
                        .configurationSource(corsConfigurationSource())
                )
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .sessionManagement((sessionManagement) ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        http
                .authorizeHttpRequests((authorize) ->
                        authorize
                                .requestMatchers("/v3/api-docs/**", "/swagger-ui/**").permitAll() // API 명세서

                                .requestMatchers("/admin/test-register", "/admin/login").permitAll() //관리자 로그인
                                .requestMatchers("admin/refresh").permitAll() // 토큰 재발급

                                .requestMatchers("/character/uploadAndFetch", "character/update", "character/export-characters/**").hasAuthority(Role.ROLE_ADMIN.getRole()) // 캐릭터 정보 업로드 및 추출
                                .requestMatchers("/character/search").permitAll() // 캐릭터 정보 검색

                                .anyRequest().authenticated()
                );

        http
                .exceptionHandling(exceptionHandlingCustomizer ->
                        exceptionHandlingCustomizer
                                .authenticationEntryPoint(new HttpStatusEntryPoint(FORBIDDEN))
                                .accessDeniedHandler(new AccessDeniedHandlerImpl())
                );

        http
                .addFilterBefore(new TokenAuthenticationFilter(tokenProvider),
                        UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new ExceptionHandleFilter(),
                        TokenAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(
                "http://localhost:8080",
                "http://maplewind.kro.kr/",
                "http://www.maplewind.kro.kr/"));
        configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(List.of("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
