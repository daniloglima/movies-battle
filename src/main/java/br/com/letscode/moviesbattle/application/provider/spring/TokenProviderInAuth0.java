package br.com.letscode.moviesbattle.application.provider.spring;

import br.com.letscode.moviesbattle.application.provider.TokenData;
import br.com.letscode.moviesbattle.application.provider.TokenProvider;
import br.com.letscode.moviesbattle.application.provider.TokenProviderConfig;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class TokenProviderInAuth0 implements TokenProvider {
    private final TokenProviderConfig config;

    @Autowired
    public TokenProviderInAuth0(TokenProviderConfig config) {
        this.config = config;
    }

    @Override
    public String buildWith(TokenData data){
        Algorithm algorithm = Algorithm.HMAC256(config.getSecretKey().getBytes());

        String accessToken = JWT.create()
                .withSubject(data.identity())
                .withIssuedAt(new Date())
                .withExpiresAt(LocalDateTime.now().plusHours(24).atZone(ZoneId.systemDefault()).toInstant())
                .withIssuer("lets-code")
                .withClaim("userId", data.userId())
                .sign(algorithm);

        return accessToken;
    }

    @Override
    public TokenData loadFrom(String token) {
        Algorithm algorithm = Algorithm.HMAC256(config.getSecretKey().getBytes());
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decoded = verifier.verify(token);

        var identity = decoded.getSubject();
        var userId = decoded.getClaim("userId").asLong();

        return new TokenData(userId, identity);
    }
}
