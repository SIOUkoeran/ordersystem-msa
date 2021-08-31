package apigateway.demo.filter;



import io.jsonwebtoken.Jwts;

import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;

import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;



@Component
@Slf4j
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {


    Environment env;
    @Autowired
    public AuthorizationHeaderFilter(Environment env) {
        super(Config.class);
        this.env  = env;
    }

    public static class Config{

    }
    @Value("${springbootwebfluxjjwt.jjwt.secret}")
    private String ss;

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) ->{
            if (!exchange.getRequest()
                    .getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {

                return onError(exchange, "No authorization header", HttpStatus.UNAUTHORIZED);
            }
            String jwt = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
            jwt = jwt.replace("Bearer ", "");
            if (!isJwtValid(jwt)){
                return onError(exchange, "JWT token is Not Valid", HttpStatus.UNAUTHORIZED);

            }


        return chain.filter(exchange);

        };

    }



    private boolean isJwtValid(String jwt) {

        boolean returnValue = true;
        String subject = null;
        try {
            subject = Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(env.getProperty("springbootwebfluxjjwt.jjwt.secret").getBytes()))
                    .build()
                    .parseClaimsJws(jwt)
                    .getBody().getSubject();

        }catch (Exception e){
            returnValue = false;
        }
        if (subject ==null || subject.isEmpty()){
            returnValue = false;
        }
        return returnValue;

    }

    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus ) {
          ServerHttpResponse response = exchange.getResponse();
          response.setStatusCode(httpStatus);
          log.info(err);
          return response.setComplete();


    }


}
