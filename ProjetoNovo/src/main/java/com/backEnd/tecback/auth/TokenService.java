package com.backEnd.tecback.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.backEnd.tecback.models.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    public String secret;

    public String generateToke(User user){
        try{

            Algorithm algorithm = Algorithm.HMAC256(secret);

            String token = JWT.create()
                    .withIssuer("api")
                    .withSubject(user.getUsername())
                    .withExpiresAt(genereateExpirationDate())
                    .sign(algorithm);

            return token;

        }catch (JWTCreationException ex){

            throw new RuntimeException("Erro ao gerar o token: ",ex);

        }
    }

    public String validateToken(String token) {
        try {

        Algorithm algorithm = Algorithm.HMAC256(secret);

        return JWT.require(algorithm)
                .withIssuer("api")
                .build()
                .verify(token)
                .getSubject();

        } catch (JWTVerificationException ex) {
            return "";
        }

    }

    private Instant genereateExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

}
