package br.com.letscode.moviesbattle.usecase.battle.round;

import br.com.letscode.moviesbattle.application.repository.BattleRepositoryInMemory;
import br.com.letscode.moviesbattle.application.repository.MoviesRepositoryInMemory;
import br.com.letscode.moviesbattle.application.repository.RoundRepositoryInMemory;
import br.com.letscode.moviesbattle.domain.round.TableRound;
import br.com.letscode.moviesbattle.usecase.battle.end.BattleNotStartedException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RoundBattleUseCaseTest {

    @Test
    public void deve_validar_se_existe_batalha_em_aberto() {

        var usecase = new RoundBattleUseCase(
                new RoundRepositoryInMemory(),
                new BattleRepositoryInMemory(),
                new MoviesRepositoryInMemory()
        );

        var input = RoundBattleInput.builder().build();

        assertThatThrownBy(() -> usecase.handle(input)).isInstanceOf(BattleNotStartedException.class);
    }
    @Test
    public void deve_validar_quantidade_maxima_de_erros() {

        var roundRepository = new RoundRepositoryInMemory();
        var battleRepository = new BattleRepositoryInMemory();
        var moviesRepository = new MoviesRepositoryInMemory();

        var usecase = new RoundBattleUseCase(
                roundRepository,
                battleRepository,
                moviesRepository
        );

        var userId = 1L;
        battleRepository.save(userId, true);

        moviesRepository.save("title a", 5);
        moviesRepository.save("title b", 6);
        moviesRepository.save("title c", 7);
        moviesRepository.save("title d", 8);

        var battleId = 1L;
        TableRound round;

        round = roundRepository.save(battleId, 1, 2);
        roundRepository.answerById(round.getId(), false, 1);

        round = roundRepository.save(battleId, 3, 4);
        roundRepository.answerById(round.getId(), false, 3);

        round = roundRepository.save(battleId, 5, 6);
        roundRepository.answerById(round.getId(), false, 5);

        var input = RoundBattleInput.builder()
                .userId(userId)
                .build();
        assertThatThrownBy(() -> usecase.handle(input)).isInstanceOf(NoMoreQuestionsAvailable.class);
    }
    @Test
    public void deve_criar_um_novo_round() {

        var roundRepository = new RoundRepositoryInMemory();
        var battleRepository = new BattleRepositoryInMemory();
        var moviesRepository = new MoviesRepositoryInMemory();

        var usecase = new RoundBattleUseCase(
                roundRepository,
                battleRepository,
                moviesRepository
        );

        var userId = 1L;
        battleRepository.save(userId, true);

        moviesRepository.save("title a", 5);
        moviesRepository.save("title b", 6);
        moviesRepository.save("title c", 7);
        moviesRepository.save("title d", 8);

        var input = RoundBattleInput.builder()
                .userId(userId)
                .build();

        var output = usecase.handle(input);

        assertThat(output).isNotNull();

        var battleId = 1L;
        var rounds = roundRepository.findByBattleId(battleId);
        assertThat(rounds).isNotNull();
        assertThat(rounds).hasSize(1);

        var round = rounds.get(0);
        assertThat(round.getItemAId()).isNotEqualTo(round.getItemBId());

    }
    @Test
    public void deve_retornar_mesmo_round() {

        var roundRepository = new RoundRepositoryInMemory();
        var battleRepository = new BattleRepositoryInMemory();
        var moviesRepository = new MoviesRepositoryInMemory();

        var usecase = new RoundBattleUseCase(
                roundRepository,
                battleRepository,
                moviesRepository
        );

        var userId = 1L;
        battleRepository.save(userId, true);

        moviesRepository.save("title a", 5);
        moviesRepository.save("title b", 6);
        moviesRepository.save("title c", 7);
        moviesRepository.save("title d", 8);

        var input = RoundBattleInput.builder()
                .userId(userId)
                .build();

        var firstHandle = usecase.handle(input);

        for (int i = 0; i < 10; i++) {
            var atualHandle = usecase.handle(input);
            assertThat(atualHandle).isEqualTo(firstHandle);
        }

    }


}