package shop.wannab.frontservice.global.config;


import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import shop.wannab.frontservice.auth.handler.OAuth2SuccessHandler;
import shop.wannab.frontservice.auth.service.CustomOAuth2UserService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import shop.wannab.frontservice.auth.filter.CustomLoginFilter;
import shop.wannab.frontservice.auth.service.AuthClient;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOAuth2UserService oAuth2UserService;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;
    private final ClientRegistrationRepository clientRegistrationRepository;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final AuthClient authClient;

    // 디버깅
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring()
                .requestMatchers("/error", "/favicon.ico");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        CustomLoginFilter customLoginFilter = new CustomLoginFilter(
                authenticationConfiguration.getAuthenticationManager(),
                authClient
        );

        http
            .oauth2Login(oauth -> oauth
                    .authorizationEndpoint(authz -> authz
                            .authorizationRequestResolver(customAuthorizationRequestResolver()))
                    .userInfoEndpoint(ui -> ui.userService(oAuth2UserService))
                    .successHandler(oAuth2SuccessHandler)
            )
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterAt(customLoginFilter, UsernamePasswordAuthenticationFilter.class);

        http.authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll()
        );

        return http.build();
    }

    @Bean
    public OAuth2AuthorizationRequestResolver customAuthorizationRequestResolver() {
        var defaultResolver = new DefaultOAuth2AuthorizationRequestResolver(
                clientRegistrationRepository, "/oauth2/authorization");

        return new OAuth2AuthorizationRequestResolver() {
            @Override
            public OAuth2AuthorizationRequest resolve(HttpServletRequest request) {
                OAuth2AuthorizationRequest req = defaultResolver.resolve(request);
                return customizeRequest(req);
            }

            @Override
            public OAuth2AuthorizationRequest resolve(HttpServletRequest request, String clientRegistrationId) {
                OAuth2AuthorizationRequest req = defaultResolver.resolve(request, clientRegistrationId);
                return customizeRequest(req);
            }

            private OAuth2AuthorizationRequest customizeRequest(OAuth2AuthorizationRequest req) {
                if (req == null) {
                    return null;
                }

                var params = new HashMap<>(req.getAdditionalParameters());
                params.put("userLocale", "ko_KR");
                params.put("serviceProviderCode", "FRIENDS");

                return OAuth2AuthorizationRequest.from(req)
                        .additionalParameters(params)
                        .build();
            }
        };
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}