package br.com.letscode.moviesbattle.usecase.battle.round;

import br.com.letscode.moviesbattle.domain.battle.BattleRepository;
import br.com.letscode.moviesbattle.domain.battle.TableBattle;
import br.com.letscode.moviesbattle.domain.movies.MoviesRepository;
import br.com.letscode.moviesbattle.domain.round.RoundRepository;
import br.com.letscode.moviesbattle.domain.round.TableRound;
import br.com.letscode.moviesbattle.usecase.battle.end.BattleNotStartedException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
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
        Optional<TableBattle> hasOpened = battleRepository.findOpenedBattleByUserId(input.getUserId());
        TableBattle battle = hasOpened.orElseThrow(BattleNotStartedException::new);

        long count = roundRepository.countWrongByBattleId(battle.getId());
        if(count >= NUMBER_OF_ATTEMPTS_ALLOWED) throw new NoMoreQuestionsAvailable();

        Round next = getNextRound(battle.getId());
        return RoundBattleOutput.WithSuccess(hydrate(next).getItems());
    }
    private Round getNextRound(long battleId) {
        Optional<TableRound> table = roundRepository.findOpenedRoundBy(battleId);
        return table.map(Round::of).orElseGet(() -> createNext(battleId));
    }
    private Round createNext(long battleId) {
        long maxId = moviesRepository.getMaxId();

        long firstMovieId = 0L;
        long secondMovieId = getRandomId(maxId);

        do {
            firstMovieId = getRandomId(maxId);
        } while (firstMovieId == secondMovieId);

        TableRound saved = roundRepository.save(
                battleId,
                firstMovieId,
                secondMovieId
        );

        return Round.of(saved);
    }
    private long getRandomId(long maxId){
        long leftLimit = 1L;
        return leftLimit + (long) (Math.random() * (maxId - leftLimit));
    }
    private Round hydrate(Round round){
        return round.hydrateItems(id -> moviesRepository.findById(id).map(Item::of).orElseThrow(RuntimeException::new));
    }

}
