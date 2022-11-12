package br.com.letscode.moviesbattle.application.provider;

public class TokenData {

    private final long userId;
    private final String identity;

    public TokenData(long userId, String identity) {
        this.userId = userId;
        this.identity = identity;
    }

    public long getUserId() {
        return userId;
    }

    public String getIdentity() {
        return identity;
    }
}
