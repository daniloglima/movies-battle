package br.com.letscode.moviesbattle.application.http.controller.facade;

public record SignInResponse(String token) {

    public static SignInResponse of(String token){
        return new SignInResponse(token);
    }

}
