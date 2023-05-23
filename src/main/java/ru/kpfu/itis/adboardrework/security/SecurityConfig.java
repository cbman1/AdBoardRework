package ru.kpfu.itis.adboardrework.security;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.config.annotation.web.configurers.oauth2.client.OAuth2LoginConfigurer;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.oidc.IdTokenClaimNames;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;

import java.util.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(
                "/css/**", "/js/**", "/images/**", "/static/**", "/scss/**", "/fonts/**", "/jquery/**", "/ws/**");
    }
    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        return new InMemoryClientRegistrationRepository(googleClientRegistration());
    }

    private static final Set<String> SCOPES = new HashSet<>(Arrays.asList("openid", "profile", "email"));

    private ClientRegistration googleClientRegistration() {
        return ClientRegistration.withRegistrationId("google")
                .clientId("759840759336-ff5vjai1gfqgtiffclrc2r4sa8c92lvt.apps.googleusercontent.com")
                .clientSecret("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")
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .redirectUri("http://localhost/oauth2/callback/")
                .scope(SCOPES)
                .authorizationUri("https://accounts.google.com/o/oauth2/auth")
                .tokenUri("https://accounts.google.com/o/oauth2/token")
                .userInfoUri("https://www.googleapis.com/oauth2/v3/userinfo")
                .userNameAttributeName(IdTokenClaimNames.SUB)
                .clientName("Google")
                .build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .ignoringRequestMatchers("/profile/**")
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/register", "/login", "/email/confirm/**",
                        "/profile", "/advert", "/", "/home", "/map", "/profile/reviews",
                        "/my-favorite", "/swagger-ui.html", "/swagger-ui/**", "/v3/**", "/api/**").permitAll()
                .requestMatchers("/logout", "/upload/**", "/advert/**", "/chats/**").authenticated()
                .and()
//                .oauth2Login(oauth2Login ->
//                        oauth2Login.loginPage("/login").defaultSuccessUrl("/my-profile")
//                                .userInfoEndpoint()
//                                .userService(oauth2UserService())
//                )
                .formLogin(login -> login.loginPage("/login").defaultSuccessUrl("/"))
                .logout(logout -> logout.logoutUrl("/logout").logoutSuccessUrl("/"));
//                .headers(headers -> headers.xssProtection().and().contentSecurityPolicy("script-src 'self' https://api-maps.yandex.ru 'sha256-base64 encoded hash'");

        return httpSecurity.build();
    }
    @Autowired
    public void bindUserDetailsServiceAndPasswordEncoder(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    private OAuth2UserService<OAuth2UserRequest, OAuth2User> oauth2UserService() {
        DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();
        return (userRequest) -> {
            OAuth2User user = delegate.loadUser(userRequest);

            Set<GrantedAuthority> authorities = new HashSet<>();
            authorities.add(new OAuth2UserAuthority(user.getAttributes()));

            String userNameAttributeName = userRequest.getClientRegistration()
                    .getProviderDetails()
                    .getUserInfoEndpoint()
                    .getUserNameAttributeName();

            Map<String, Object> attributes = new HashMap<>(user.getAttributes());
            attributes.put(userNameAttributeName, user.getName());



//            OAuth2UserAuthorityResolver authorityResolver = new OAuth2UserAuthorityResolver();
//            authorities = authorityResolver.resolve(authorities, userNameAttributeName, attributes);
//
//            OAuth2UserAuthoritiesMapper authoritiesMapper = new OAuth2UserAuthoritiesMapper();
//            authorities = authoritiesMapper.mapAuthorities(authorities);

            return new DefaultOAuth2User(authorities, attributes, userNameAttributeName);
        };
    }
}
