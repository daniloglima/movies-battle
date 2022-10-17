package br.com.letscode.moviesbattle.usecase.battle.vote;

import br.com.letscode.moviesbattle.domain.battle.BattleRepositoryInMemory;
import br.com.letscode.moviesbattle.domain.movies.MoviesRepositoryInMemory;
import br.com.letscode.moviesbattle.domain.round.RoundRepositoryInMemory;
import br.com.letscode.moviesbattle.usecase.battle.end.BattleNotStartedException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class VoteInBattleUseCaseTest {

    @Test
    public void deve_validar_quando_nao_houver_batalha_aberta(){

        var usecase = new VoteInBattleUseCase(
                new MoviesRepositoryInMemory(),
                new RoundRepositoryInMemory(),
                new BattleRepositoryInMemory()
        );

        var input =  VoteInBattleInput.builder()
                .userId(1L)
                .answerId(1L)
                .otherId(2L)
                .build();

        assertThatThrownBy(() -> usecase.handle(input)).isInstanceOf(BattleNotStartedException.class);
    }

    @Test
    public void deve_validar_quando_nao_houver_round_aberto(){

        var moviesRepository =  new MoviesRepositoryInMemory();
        var roundRepository = new RoundRepositoryInMemory();
        var battleRepository = new BattleRepositoryInMemory();

        var usecase = new VoteInBattleUseCase(
                moviesRepository,
                roundRepository,
                battleRepository
        );

        var userId = 1L;

        Long battleId = battleRepository.save(userId, true).getId();

       var input =  VoteInBattleInput.builder()
                .userId(1L)
                .answerId(1L)
                .otherId(2L)
                .build();

        assertThatThrownBy(() -> usecase.handle(input)).isInstanceOf(RoundNotStartedException.class);
    }

    @Test
    public void deve_validar_quando_nao_encontrar_filme(){

        var moviesRepository =  new MoviesRepositoryInMemory();
        var roundRepository = new RoundRepositoryInMemory();
        var battleRepository = new BattleRepositoryInMemory();

        var usecase = new VoteInBattleUseCase(
                moviesRepository,
                roundRepository,
                battleRepository
        );

        var userId = 1L;
        Long battleId = battleRepository.save(userId, true).getId();

        roundRepository.save(battleId, 99, 99);

        var input =  VoteInBattleInput.builder()
                .userId(1L).answerId(1L)
                .otherId(2L)
                .build();

        assertThatThrownBy(() -> usecase.handle(input)).isInstanceOf(InvalidAnswerException.class);
    }

    @Test
    public void deve_aplicar_voto_durante_round(){

        var moviesRepository =  new MoviesRepositoryInMemory();
        var roundRepository = new RoundRepositoryInMemory();
        var battleRepository = new BattleRepositoryInMemory();

        var usecase = new VoteInBattleUseCase(
                moviesRepository,
                roundRepository,
                battleRepository
        );

        var userId = 1L;
        Long battleId = battleRepository.save(userId, true).getId();

        Long movieAId = moviesRepository.save("title a", 5).getId();
        Long movieBId = moviesRepository.save("title b", 7).getId();

        Long roundId;
        roundId = roundRepository.save(battleId, movieAId, movieBId).getId();
        roundRepository.answerById(roundId, true, movieAId);

        roundId = roundRepository.save(battleId, movieAId, movieBId).getId();
        roundRepository.answerById(roundId, true, movieAId);

        roundId = roundRepository.save(battleId, movieAId, movieBId).getId();
        roundRepository.answerById(roundId, true, movieAId);

        //open
        roundRepository.save(battleId, movieAId, movieBId);

        var input =  VoteInBattleInput.builder()
                .userId(1L)
                .answerId(1L).otherId(2L)
                .build();

        VoteInBattleOutput output = usecase.handle(input);

        assertThat(output).isNotNull();
        assertThat(output.isSuccess()).isTrue();
    }

}