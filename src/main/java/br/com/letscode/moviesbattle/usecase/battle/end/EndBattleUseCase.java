package br.com.letscode.moviesbattle.usecase.battle.end;

import br.com.letscode.moviesbattle.domain.battle.BattleRepository;

public class EndBattleUseCase {
    private final BattleRepository repository;

    public EndBattleUseCase(BattleRepository repository) {
        this.repository = repository;
    }

    EndBattleOutput handle(EndBattleInput input){
        var userId = input.getUserId();
        var opened = repository.findOpenedBattleByUserId(userId);

        opened.ifPresent((value) -> { throw new BattleNotStartedException(); });

        repository.updateOpened(userId, false);

        return EndBattleOutput.WithSuccess();
    }

}
