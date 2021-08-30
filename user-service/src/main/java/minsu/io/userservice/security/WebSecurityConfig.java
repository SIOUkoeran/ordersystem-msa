package minsu.io.userservice.security;


import lombok.AllArgsConstructor;
import minsu.io.userservice.repository.SecurityContextRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class WebSecurityConfig {

    private AuthenticationManager manager;
    private SecurityContextRepository repository;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http){

        return http
                .exceptionHandling()
                .authenticationEntryPoint(((serverWebExchange, e) ->
                        Mono.fromRunnable(()-> serverWebExchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED))))
                .accessDeniedHandler((serverWebExchange, e) ->
                        Mono.fromRunnable(()->serverWebExchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN)))
                .and()
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .authenticationManager(manager)
                .securityContextRepository(repository)
                .authorizeExchange()
                .pathMatchers(HttpMethod.OPTIONS).permitAll()
                .pathMatchers("/actuator/**").permitAll()
                .pathMatchers("/signUp").permitAll()
                .pathMatchers("/login").permitAll()
                .pathMatchers("/health-check").permitAll()
                .anyExchange().authenticated()
                .and().build();
    }
}
