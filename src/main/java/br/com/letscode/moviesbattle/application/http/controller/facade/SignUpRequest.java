package br.com.letscode.moviesbattle.application.http.controller.facade;

public class SignUpRequest {

    private final String identity;
    private final String password;

    public SignUpRequest(String identity, String password) {
        this.identity = identity;
        this.password = password;
    }

    public String getIdentity() {
        return identity;
    }

    public String getPassword() {
        return password;
    }
}
