package br.com.letscode.moviesbattle.usecase.battle.round;

import br.com.letscode.moviesbattle.domain.battle.BattleRepositoryInMemory;
import br.com.letscode.moviesbattle.domain.movies.MoviesRepositoryInMemory;
import br.com.letscode.moviesbattle.domain.round.RoundRepositoryInMemory;
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
    public void deve_validar_se_existe_questoes_disponiveis() {

        var battleRepository = new BattleRepositoryInMemory();

        var usecase = new RoundBattleUseCase(
                new RoundRepositoryInMemory(),
                battleRepository,
                new MoviesRepositoryInMemory()
        );

        var userId = 10L;
        battleRepository.save(userId, true);

        var input = RoundBattleInput.builder()
                .userId(userId)
                .build();

        assertThatThrownBy(() -> usecase.handle(input)).isInstanceOf(NoMoreQuestionsAvaliable.class);
    }

    @Test
    public void deve_validar_se_todas_as_questoes_ja_foram_respondidas() {

        var roundRepository = new RoundRepositoryInMemory();
        var battleRepository = new BattleRepositoryInMemory();
        var moviesRepository = new MoviesRepositoryInMemory();

        var usecase = new RoundBattleUseCase(
                roundRepository,
                battleRepository,
                moviesRepository
        );

        var userId = 10L;
        var battle = battleRepository.save(userId, true);

        roundRepository.save(battle.getId(), 1, 2, true);
        roundRepository.save(battle.getId(), 3, 4, true);

        moviesRepository.save(1, "A", 0);
        moviesRepository.save(2, "B", 0);
        moviesRepository.save(3, "C", 0);
        moviesRepository.save(4, "D", 0);

        var input = RoundBattleInput.builder()
                .userId(userId)
                .build();

        assertThatThrownBy(() -> usecase.handle(input)).isInstanceOf(NoMoreQuestionsAvaliable.class);
    }

    @Test
    public void deve_retornar_o_proximo_round() {

        var roundRepository = new RoundRepositoryInMemory();
        var battleRepository = new BattleRepositoryInMemory();
        var moviesRepository = new MoviesRepositoryInMemory();

        var usecase = new RoundBattleUseCase(
                roundRepository,
                battleRepository,
                moviesRepository
        );

        var userId = 10L;
        var battle = battleRepository.save(userId, true);

        roundRepository.save(battle.getId(), 1, 2, false);
        roundRepository.save(battle.getId(), 3, 4, false);
        roundRepository.save(battle.getId(), 5, 5, false);

        moviesRepository.save(1, "A", 0);
        moviesRepository.save(2, "B", 0);
        moviesRepository.save(3, "C", 0);
        moviesRepository.save(4, "D", 0);
        moviesRepository.save(5, "E", 0);
        moviesRepository.save(6, "F", 0);

        var input = RoundBattleInput.builder()
                .userId(userId)
                .build();

        var result = usecase.handle(input);
        assertThat(result).isNotNull();

    }
}