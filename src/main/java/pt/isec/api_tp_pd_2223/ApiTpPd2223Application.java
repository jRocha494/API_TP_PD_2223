package pt.isec.api_tp_pd_2223;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;
import pt.isec.api_tp_pd_2223.security.RsaKeyProperties;
import pt.isec.api_tp_pd_2223.security.UserAuthenticationProvider;

@SpringBootApplication
@EnableJpaRepositories
@ConfigurationPropertiesScan
public class ApiTpPd2223Application
{
    private final RsaKeyProperties rsaKeys;

    public ApiTpPd2223Application(RsaKeyProperties rsaKeys)
    {
        this.rsaKeys = rsaKeys;
    }

    public static void main(String[] args)
    {
        SpringApplication.run(ApiTpPd2223Application.class, args);
    }

    @Bean
    JwtEncoder jwtEncoder()
    {
        JWK jwk = new RSAKey.Builder(rsaKeys.publicKey()).privateKey(rsaKeys.privateKey()).build();
        JWKSource<SecurityContext> jwkSource = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwkSource);
    }

    @Bean
    JwtDecoder jwtDecoder()
    {
        return NimbusJwtDecoder.withPublicKey(rsaKeys.publicKey()).build();
    }

    @Configuration
    @EnableWebSecurity
    public class SecurityConfig
    {
        @Autowired
        private UserAuthenticationProvider authProvider;

        @Autowired
        public void configAuthentication(AuthenticationManagerBuilder auth)
        {
            auth.authenticationProvider(authProvider);
        }

        @Bean
        @Order(1)
        public SecurityFilterChain loginFilterChain(HttpSecurity http) throws Exception
        {
            return http
                    .csrf(csrf -> csrf.disable())
                    .securityMatcher("/login")
                    .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
                    .httpBasic(Customizer.withDefaults())
                    .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .build();
        }

        @Bean
        @Order(2)
        public SecurityFilterChain adminFilterChain(HttpSecurity http) throws Exception
        {
            return http
                    .csrf(csrf -> csrf.disable())
                    .securityMatcher("/user/**")
                    .authorizeHttpRequests(auth -> auth.anyRequest().hasAnyAuthority("SCOPE_ADMIN"))
                    .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
                    .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .build();
        }

        @Bean
        @Order(2)
        public SecurityFilterChain showAllFilterChain(HttpSecurity http) throws Exception
        {
            return http
                    .csrf(csrf -> csrf.disable())
                    .securityMatcher("/show/all")
                    .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
                    .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
                    .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .build();
        }

        @Bean
        public SecurityFilterChain genericFilterChain(HttpSecurity http) throws Exception
        {
            return http
                    .csrf(csrf -> csrf.disable())
                    .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
                    .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
                    .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .build();
        }
    }
}
