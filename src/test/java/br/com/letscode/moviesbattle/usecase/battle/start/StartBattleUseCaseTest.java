package br.com.letscode.moviesbattle.usecase.battle.start;

import br.com.letscode.moviesbattle.domain.battle.BattleRepositoryInMemory;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class StartBattleUseCaseTest {

    @Test
    public void deve_iniciar_uma_nova_batalha(){
        var usecase = new StartBattleUseCase(new BattleRepositoryInMemory());

        var input = StartBattleInput.builder()
                .userId(10L)
                .build();

        var output = usecase.handle(input);

        assertThat(output).isNotNull();
        assertThat(output.isSuccess()).isTrue();
    }

    @Test
    public void deve_impedir_uma_nova_batalha_quando_outra_em_andamento(){
        var repository = new BattleRepositoryInMemory();
        var usecase = new StartBattleUseCase(repository);

        var userId = 10L;
        repository.save(userId, true);

        var input = StartBattleInput.builder()
                .userId(userId)
                .build();

        assertThatThrownBy(() -> usecase.handle(input)).isInstanceOf(AlreadyStartedBattleException.class);
    }

}