package br.com.letscode.moviesbattle.usecase.battle.round;

import br.com.letscode.moviesbattle.domain.battle.BattleRepository;
import br.com.letscode.moviesbattle.domain.movies.MoviesRepository;
import br.com.letscode.moviesbattle.domain.round.RoundRepository;
import br.com.letscode.moviesbattle.domain.round.TableRound;
import br.com.letscode.moviesbattle.usecase.battle.end.BattleNotStartedException;

public class RoundBattleUseCase {
    private final RoundRepository roundRepository;
    private final BattleRepository battleRepository;
    private final MoviesRepository moviesRepository;
    private static final int NUMBER_OF_ATTEMPTS_ALLOWED = 3;

    public RoundBattleUseCase(RoundRepository roundRepository, BattleRepository battleRepository, MoviesRepository moviesRepository) {
        this.roundRepository = roundRepository;
        this.battleRepository = battleRepository;
        this.moviesRepository = moviesRepository;
    }
    public RoundBattleOutput handle(RoundBattleInput input){
        var hasOpened = battleRepository.findOpenedBattleByUserId(input.getUserId());
        var battle = hasOpened.orElseThrow(BattleNotStartedException::new);

        var count = roundRepository.countWrongByBattleId(battle.getId());
        if(count >= NUMBER_OF_ATTEMPTS_ALLOWED) throw new NoMoreQuestionsAvaliable();

        Round next = getNextRound(battle.getId());
        return RoundBattleOutput.WithSuccess(hydrate(next).getItems());
    }
    private Round getNextRound(long battleId) {
        var table = roundRepository.findOpenedRoundBy(battleId);
        return table.map(Round::of).orElseGet(() -> createNext(battleId));
    }
    private Round createNext(long battleId) {
        long maxId = moviesRepository.getMaxId();

        var firstMovieId = 0L;
        var secondMovieId = getRandomId(maxId);

        do {
            firstMovieId = getRandomId(maxId);
        } while (firstMovieId == secondMovieId);

        TableRound saved = roundRepository.save(
                battleId,
                firstMovieId,
                secondMovieId,
                false,
                false
        );

        return Round.of(saved);
    }
    private long getRandomId(long maxId){
        long leftLimit = 1L;
        return leftLimit + (long) (Math.random() * (maxId - leftLimit));
    }
    private Round hydrate(Round round){
        return round.hydrateItems(id -> moviesRepository.findById(id).map(Item::of).orElseThrow());
    }

}
