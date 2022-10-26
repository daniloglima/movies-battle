package br.com.letscode.moviesbattle.usecase.battle.vote;

import br.com.letscode.moviesbattle.domain.battle.BattleRepository;
import br.com.letscode.moviesbattle.domain.movies.MoviesRepository;
import br.com.letscode.moviesbattle.domain.round.RoundRepository;
import br.com.letscode.moviesbattle.domain.round.TableRound;
import br.com.letscode.moviesbattle.usecase.battle.end.BattleNotStartedException;
import org.springframework.stereotype.Service;
@Service
public class VoteInBattleUseCase {
    private final MoviesRepository moviesRepository;
    private final RoundRepository roundRepository;
    private final BattleRepository battleRepository;

    public VoteInBattleUseCase(MoviesRepository moviesRepository, RoundRepository roundRepository, BattleRepository battleRepository) {
        this.moviesRepository = moviesRepository;
        this.roundRepository = roundRepository;
        this.battleRepository = battleRepository;
    }

    public VoteInBattleOutput handle(VoteInBattleInput input) {

        var battleId = getBattleIdBy(input.getUserId());
        var round = getRoundBy(battleId);

        var firstOption = round.getItemAId();
        var secondOption = round.getItemAId();

        if(input.getAnswerId() == round.getItemAId()){
            secondOption = round.getItemBId();
        } else {
            firstOption = round.getItemBId();
        }

        var other = getMovie(secondOption);
        var answer = getMovie(firstOption);

        var right = answer.checkAnswer(other);
        roundRepository.answerById(round.getId(), right.isRight(), right.getId());

        return VoteInBattleOutput.withSuccess();
    }
    private TableRound getRoundBy(long battleId) {
        var roundBy = roundRepository.findOpenedRoundBy(battleId);
        return roundBy.orElseThrow(RoundNotStartedException::new);
    }

    private long getBattleIdBy(long userId) {
        var openBattle = battleRepository.findOpenedBattleByUserId(userId);
        var table = openBattle.orElseThrow(BattleNotStartedException::new);
        return table.getId();
    }

    private Movie getMovie(long movieId){
        var byId = moviesRepository.findById(movieId);
        var table = byId.orElseThrow(InvalidAnswerException::new);

        var count = roundRepository.countRightVotesBy(movieId);

        var votes = (count > 0 ? count : 1);
        return Movie.of(table, votes);
    }

}