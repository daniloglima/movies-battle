package br.com.letscode.moviesbattle.usecase.battle.start;

import br.com.letscode.moviesbattle.usecase.commons.ApplicationException;

public class AlreadyStartedBattleException extends ApplicationException {

    public AlreadyStartedBattleException() {
        this("Already started battle.");
    }
    public AlreadyStartedBattleException(String message) {
        super(message);
    }
}
