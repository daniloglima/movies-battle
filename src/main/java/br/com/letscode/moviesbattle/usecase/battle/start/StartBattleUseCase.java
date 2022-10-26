package br.com.letscode.moviesbattle.usecase.battle.start;

import br.com.letscode.moviesbattle.domain.battle.BattleRepository;
import org.springframework.stereotype.Service;
@Service
public class StartBattleUseCase {
    private final BattleRepository repository;
    public StartBattleUseCase(BattleRepository repository) {
        this.repository = repository;
    }

    public StartBattleOutput handle(StartBattleInput input){
        var userId = input.getUserId();
        var hasBattle = repository.hasOpenedByUserId(userId);

        if(hasBattle) throw new AlreadyStartedBattleException();

        repository.save(userId, true);

        return StartBattleOutput.withSuccess();
    }

}
