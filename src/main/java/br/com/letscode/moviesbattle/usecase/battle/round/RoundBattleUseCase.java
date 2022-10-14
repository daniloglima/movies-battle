package br.com.letscode.moviesbattle.usecase.battle.round;

import br.com.letscode.moviesbattle.domain.battle.BattleRepository;
import br.com.letscode.moviesbattle.domain.movies.MoviesRepository;
import br.com.letscode.moviesbattle.domain.round.RoundRepository;
import br.com.letscode.moviesbattle.usecase.battle.end.BattleNotStartedException;

import java.util.List;
import java.util.Optional;

public class RoundBattleUseCase {
    private final RoundRepository roundRepository;
    private final BattleRepository battleRepository;
    private final MoviesRepository moviesRepository;

    public RoundBattleUseCase(RoundRepository roundRepository, BattleRepository battleRepository, MoviesRepository moviesRepository) {
        this.roundRepository = roundRepository;
        this.battleRepository = battleRepository;
        this.moviesRepository = moviesRepository;
    }

    public RoundBattleOutput handle(RoundBattleInput input){
        var hasOpened = battleRepository.findOpenedBattleByUserId(input.getUserId());
        var battle = hasOpened.orElseThrow(BattleNotStartedException::new);

        var rounds = getRoundsBy(battle.getId());
        checkAllAnswered(rounds);

        var next = getNextRound(rounds).orElseThrow(NoMoreQuestionsAvaliable::new);
        return RoundBattleOutput.WithSuccess(next.getItems());
    }

    private List<Round> getRoundsBy(long battleId){
        var battles = roundRepository.findByBattleId(battleId);
        return battles.stream().map(Round::of).toList();
    }
    private void checkAllAnswered(List<Round> rounds){
        var isAnswered = rounds.stream().allMatch(Round::isFinished);
        if(isAnswered) throw new NoMoreQuestionsAvaliable();
    }
    private Optional<Round> getNextRound(List<Round> rounds) {
        return rounds.stream()
                .filter(Round::isPendent)
                .map(this::hydrate)
                .findAny();
    }
    private Round hydrate(Round round){
        return round.hydrateItems(id -> moviesRepository.findById(id).map(Item::of).orElseThrow());
    }

}
