package br.com.letscode.moviesbattle.application.http.controller.facade;

public class SignInResponse {

    private final String token;

    public SignInResponse(String token) {
        this.token = token;
    }

    public static SignInResponse of(String token){
        return new SignInResponse(token);
    }

    public String getToken() {
        return token;
    }
}
