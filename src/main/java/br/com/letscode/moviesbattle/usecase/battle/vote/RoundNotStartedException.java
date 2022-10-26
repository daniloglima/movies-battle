package br.com.letscode.moviesbattle.usecase.battle.vote;

import br.com.letscode.moviesbattle.usecase.commons.ApplicationException;

public class RoundNotStartedException extends ApplicationException {

    public RoundNotStartedException() {
        this("Round not started.");
    }

    public RoundNotStartedException(String message) {
        super(message);
    }
}
