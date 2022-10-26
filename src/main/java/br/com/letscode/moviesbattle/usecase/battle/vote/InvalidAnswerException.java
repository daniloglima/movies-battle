package br.com.letscode.moviesbattle.usecase.battle.vote;

import br.com.letscode.moviesbattle.usecase.commons.ApplicationException;

public class InvalidAnswerException extends ApplicationException {
    public InvalidAnswerException() {
        this("Invalid answer!");
    }
    public InvalidAnswerException(String message) {
        super(message);
    }
}
