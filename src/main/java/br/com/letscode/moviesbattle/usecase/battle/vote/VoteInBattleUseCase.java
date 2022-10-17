package br.com.letscode.moviesbattle.usecase.battle.vote;

import br.com.letscode.moviesbattle.domain.battle.BattleRepository;
import br.com.letscode.moviesbattle.domain.movies.MoviesRepository;
import br.com.letscode.moviesbattle.domain.round.RoundRepository;
import br.com.letscode.moviesbattle.usecase.battle.end.BattleNotStartedException;

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
        var roundId = getRoundIdBy(battleId);

        var other = getMovie(input.getOtherId());
        var answer = getMovie(input.getAnswerId());

        var right = answer.checkAnswer(other);
        roundRepository.answerById(roundId, right.isRight(), right.getId());

        return VoteInBattleOutput.withSuccess();
    }

    private long getRoundIdBy(long battleId) {
        var roundBy = roundRepository.findOpenedRoundBy(battleId);
        var table = roundBy.orElseThrow(RoundNotStartedException::new);
        return table.getId();
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