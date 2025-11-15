package banking.platform.bankingPlatform.infra.security;

import banking.platform.bankingPlatform.domain.user.Clients;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {
    @Value("${api.security.token}")
    private String secretKey;

    public String generetedToken(Clients clients) {

        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            String token = JWT.create()
                    .withIssuer("bankingPlatform")
                    .withSubject(clients.getUsername())
                    .withExpiresAt(genTokenExpires())
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException exception) {
            throw new RuntimeException("ERROR generetion token" + exception);
        }
    }

    private Instant genTokenExpires() {
        return LocalDateTime.now().plusHours(1).toInstant(ZoneOffset.of("-03:00"));
    }

    public String isValidToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            return JWT.require(algorithm)
                    .withIssuer("bankingPlatform")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException e) {
           return null;
        }
    }
}
