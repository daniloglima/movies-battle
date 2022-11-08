package br.com.letscode.moviesbattle.usecase.account.signup;

import br.com.letscode.moviesbattle.application.repository.AccountRepositoryInMemory;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class SignUpUseCaseTest {

    @Test
    public void deve_criar_um_novo_usuario(){

        var usecase = new SignUpUseCase(new AccountRepositoryInMemory());

        var input = SignUpInput.builder()
                .identity("usuario@email.com")
                .password("password")
                .build();

        var output = usecase.handle(input);

        assertThat(output).isNotNull();
        assertThat(output.isSuccess()).isTrue();
    }

    @Test
    public void deve_validar_usuario_ja_registrado(){

        var usecase = new SignUpUseCase(new AccountRepositoryInMemory());

        var input = SignUpInput.builder()
                .identity("usuario@email.com")
                .password("password")
                .build();

        var output = usecase.handle(input);
        assertThat(output.isSuccess()).isTrue();

        assertThatThrownBy(() -> usecase.handle(input)).isInstanceOf(UserAlreadyRegisteredException.class);
    }
}