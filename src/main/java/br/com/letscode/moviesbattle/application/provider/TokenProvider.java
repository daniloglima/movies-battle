package br.com.letscode.moviesbattle.application.provider;

public interface TokenProvider {

    String buildWith(TokenData token);
    TokenData loadFrom(String token);
}

