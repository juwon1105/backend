package savenow.backend.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import savenow.backend.config.jwt.JwtAuthenticationFilter;
import savenow.backend.config.jwt.JwtAuthorizationFilter;
import util.CustomResponseUtil;

@Configuration
public class SecurityConfig {

    private final Logger log = LoggerFactory.getLogger(getClass()); // 로그 확인용

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        log.debug("디버그 : BcryptPasswordEncoder 빈 등록됨");
        return new BCryptPasswordEncoder();
    }

    //jwt 필터 등록
    public class CustomSecurityFilterManager extends AbstractHttpConfigurer<CustomSecurityFilterManager, HttpSecurity> {
        @Override
        public void configure(HttpSecurity builder) throws Exception {
            AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);
            builder.addFilter(new JwtAuthenticationFilter(authenticationManager));
            builder.addFilter(new JwtAuthorizationFilter(authenticationManager));
            super.configure(builder);
        }

        public HttpSecurity build(){
            return getBuilder();
        }
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        log.debug("디버그 : filterChain 빈 등록됨");
        http
                .csrf(AbstractHttpConfigurer::disable) // CSRF 보호 비활성화 (필요에 따라 활성화 가능)
                .httpBasic(AbstractHttpConfigurer::disable)// http basic 비활성화
                .formLogin(AbstractHttpConfigurer::disable) // 리액트로 요청 예정
                .cors(cors -> cors.configurationSource(configurationSource())) // 자바스크립트 요청 허용
                .headers(header -> header.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable)) // Iframe 허용 안함
                .with( new CustomSecurityFilterManager() , c -> c.build())
                .exceptionHandling(e -> e.authenticationEntryPoint((request, response, oauthException) -> {
                    CustomResponseUtil.fail(response, "로그인을 진행해 주세요", HttpStatus.UNAUTHORIZED);
                }))
                .exceptionHandling(e-> e.accessDeniedHandler((request,response,exception)->{
                    CustomResponseUtil.fail(response,"권한이 없습니다.", HttpStatus.FORBIDDEN);
                })) // 동작 되나 ??

                // JWT 서버로 만들어서 세션 사용 안함 jsessionId 서버쪽 관리 X
                .sessionManagement(config -> config.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/test", "/api/join/**", "/api/login").permitAll() // 특정 엔드포인트 인증 없이 접근 가능
                        .anyRequest().authenticated() // 나머지 요청은 인증 필요
                );

        return http.build();
    }

    public CorsConfigurationSource configurationSource() {
        log.debug("디버그 : configurationSource cors 설정이 SecurityFilterChain에 등록됨");
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*"); // GET, POST, PUT, DELETE (Javascript 요청 허용)
        configuration.addAllowedOriginPattern("*"); // 모든 IP 주소 허용 (프론트 앤드 IP만 허용 react)
        configuration.setAllowCredentials(true); // 클라이언트에서 쿠키 요청 허용

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    // h2 로컬 환경에서도 테스트 가능
    @Bean
    @ConditionalOnProperty(name = "spring.h2.console.enabled",havingValue = "true")
    public WebSecurityCustomizer configureH2ConsoleEnable() {
        return web -> web.ignoring()
                .requestMatchers(PathRequest.toH2Console());
    }

}

