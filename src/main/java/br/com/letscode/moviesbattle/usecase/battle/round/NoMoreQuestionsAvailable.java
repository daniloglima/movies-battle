package br.com.letscode.moviesbattle.usecase.battle.round;

import br.com.letscode.moviesbattle.usecase.commons.ApplicationException;

public class NoMoreQuestionsAvailable extends ApplicationException {

    public NoMoreQuestionsAvailable() {
        this("No more questions available");
    }

    public NoMoreQuestionsAvailable(String message) {
        super(message);
    }
}
