package br.com.letscode.moviesbattle.application.provider.spring;

import br.com.letscode.moviesbattle.application.provider.TokenProviderConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TokenProviderConfigInSpring implements TokenProviderConfig {

    @Value("${provider.jwt.secret}")
    private String secretKey;

    @Override
    public String getSecretKey() {
        return secretKey;
    }

}
