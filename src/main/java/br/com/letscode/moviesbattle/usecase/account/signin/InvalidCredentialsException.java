package br.com.letscode.moviesbattle.usecase.account.signin;

import br.com.letscode.moviesbattle.usecase.commons.ApplicationException;

public class InvalidCredentialsException extends ApplicationException {

    public InvalidCredentialsException() {
        this("Invalid credentials");
    }

    public InvalidCredentialsException(String message) {
        super(message);
    }
}
