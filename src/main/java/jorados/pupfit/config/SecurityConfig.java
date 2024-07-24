package jorados.pupfit.config;

import jorados.pupfit.repository.UserRepository;
import jorados.pupfit.util.CustomResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity // 시큐리티 활성화 -> 웹 보안 구성을 활성화 -> 커스터마이징
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserRepository userRepository;

    // 관련 필터 등록 (토큰)
    public class CustomSecurityFilterManager extends AbstractHttpConfigurer<CustomSecurityFilterManager, HttpSecurity> {
        @Override
        public void configure(HttpSecurity http) throws Exception {
            AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
            http.addFilter(new JwtAuthenticationFilter(authenticationManager));
            http.addFilter(new JwtAuthorizationFilter(authenticationManager,userRepository));
        }
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // 기본 필터 설정
        http
                .csrf(csrf -> csrf.disable()) // CSRF 비활성화
                .cors(cors -> cors.configurationSource(configurationSource())) // CORS 설정
                .headers(headers -> headers.frameOptions(frameOptionsConfig -> headers.disable())) // iframe 허용
                .exceptionHandling(exception -> exception // 에러 핸들링
                        .authenticationEntryPoint((request, response, authException) -> {
                            CustomResponseUtil.fail(response, "로그인 오류", HttpStatus.UNAUTHORIZED);
                        })
                        .accessDeniedHandler((request, response, e) -> {
                            CustomResponseUtil.fail(response, "권한이 없습니다", HttpStatus.FORBIDDEN);
                        })
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세션 정책 비활성화
                .formLogin(form -> form.disable()) // form login 비활성화
                .httpBasic(httpBasic -> httpBasic.disable()); // HTTP Basic 인증 비활성화


        // 필터 추가 (토큰 관련)
        AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
        http
                .addFilterBefore(new JwtAuthenticationFilter(authenticationManager), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JwtAuthorizationFilter(authenticationManager, userRepository), UsernamePasswordAuthenticationFilter.class);


        // URL 별 권한 설정
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/", "/css/**", "/images/**", "/js/**", "/profile").permitAll()
                        .requestMatchers("/api/user/join","/login", "/logout").permitAll()
                        .requestMatchers("/api/**").authenticated()
                        .anyRequest().denyAll()
                );

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }



    // cors 설정 따로 해줌.
    public CorsConfigurationSource configurationSource() {
        // log.debug("디버그 : SecurityConfig의 configurationSource");
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");

        configuration.addAllowedOriginPattern("*"); // 프론트 서버의 주소 등록
        configuration.setAllowCredentials(true); // 클라이언트에서 쿠키 요청 허용
        configuration.addExposedHeader("Authorization");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


}
