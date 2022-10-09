package br.com.letscode.moviesbattle.usecase.battle;

import br.com.letscode.moviesbattle.domain.battle.BattleRepository;

public class StartBattleUseCase {

    private final BattleRepository repository;

    public StartBattleUseCase(BattleRepository repository) {
        this.repository = repository;
    }

    StartBattleOutput handle(StartBattleInput input){
        var userId = input.getUserId();
        var hasBattle = repository.hasOpenedByUserId(userId);

        if(hasBattle) throw new AlreadyStartedBattleException();

        var table = repository.save(userId, true);

        return StartBattleOutput.builder()
                .id(table.getId())
                .build();
    }

}
