package minsu.io.userservice.security.jwt;



import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import minsu.io.userservice.domain.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.Key;
import java.util.*;

@Component
public class JWTUtil {

    @Value("${springbootwebfluxjjwt.jjwt.secret}")
    private String secret;

    @Value("${springbootwebfluxjjwt.jjwt.expiration}")
    private String expirationTime;

    private Key key;

    @PostConstruct
    public void init(){
        this.key = Keys.hmacShaKeyFor(secret.getBytes());

    }

    public Claims getAllClaimsFromToken(String token){
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    public String getUserEmailFromToken(String token){
        return getAllClaimsFromToken(token).getSubject();
    }

    public Date getExpirationDateFromToken(String token){
        return getAllClaimsFromToken(token).getExpiration();
    }

    private Boolean isTokenExpired(String token){

        return this.getExpirationDateFromToken(token).before(new Date());
    }

    public String generateToken(User user){
        Map<String, Object> claims = new HashMap<>();
        List<String> roles = Arrays.asList("ROLE_USER");
        claims.put("role", roles);

        return doGenerateToken(claims,user.getId());
    }

    private String doGenerateToken(Map<String, Object> claims, String userId){
        Long expirationTimeLong = Long.parseLong(expirationTime);
        final Date createDate = new Date();
        final Date expirationDate = new Date(createDate.getTime()+ expirationTimeLong*10);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userId)
                .setIssuedAt(createDate)
                .setExpiration(expirationDate)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public Boolean validateToken(String token){
        return !isTokenExpired(token);
    }


}
