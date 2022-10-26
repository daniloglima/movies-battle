package br.com.letscode.moviesbattle.usecase.battle.end;

import br.com.letscode.moviesbattle.usecase.commons.ApplicationException;

public class BattleNotStartedException extends ApplicationException {
    public BattleNotStartedException() {
        this("Battle not started");
    }

    public BattleNotStartedException(String message) {
        super(message);
    }
}
