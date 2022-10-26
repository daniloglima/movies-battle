package br.com.letscode.moviesbattle.usecase.battle.end;

import br.com.letscode.moviesbattle.domain.battle.BattleRepositoryInMemory;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class EndBattleUseCaseTest {

    @Test
    public void deve_finalizar_uma_nova_batalha(){
        var repository = new BattleRepositoryInMemory();
        var usecase = new EndBattleUseCase(repository);

        var userId = 10L;
        repository.save(userId, true);

        var input = EndBattleInput.builder()
                .userId(userId)
                .build();

        var output = usecase.handle(input);

        assertThat(output).isNotNull();
        assertThat(output.isSuccess()).isTrue();
    }

    @Test
    public void deve_recusar_encerrar_batalha_quando_ja_nao_existe_batalha_aberta(){
        var repository = new BattleRepositoryInMemory();
        var usecase = new EndBattleUseCase(repository);

        var input = EndBattleInput.builder()
                .userId(10L)
                .build();

        assertThatThrownBy(() -> usecase.handle(input)).isInstanceOf(BattleNotStartedException.class);
    }

}