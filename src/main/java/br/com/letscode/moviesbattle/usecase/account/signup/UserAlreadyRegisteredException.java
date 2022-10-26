package br.com.letscode.moviesbattle.usecase.account.signup;

import br.com.letscode.moviesbattle.usecase.commons.ApplicationException;

public class UserAlreadyRegisteredException extends ApplicationException {

    public UserAlreadyRegisteredException() {
        this("User already registered.");
    }
    public UserAlreadyRegisteredException(String message) {
        super(message);
    }

}
