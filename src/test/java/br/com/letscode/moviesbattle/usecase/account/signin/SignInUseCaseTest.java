package br.com.letscode.moviesbattle.usecase.account.signin;

import br.com.letscode.moviesbattle.application.repository.AccountRepositoryInMemory;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class SignInUseCaseTest {

    @Test
    public void deve_efetuar_login(){
        var repository = new AccountRepositoryInMemory();
        var usecase = new SignInUseCase(repository);

        repository.save("usuario@email.com", "secret");

        var input = SignInInput.builder()
                .identity("usuario@email.com")
                .password("secret")
                .build();

        var output = usecase.handle(input);

        assertThat(output).isNotNull();

        assertThat(output.getId()).isNotNull();
        assertThat(output.getIdentity()).isNotNull();
    }

    @Test
    public void deve_recusar_login_quando_email_nao_for_encontrado(){

        var usecase = new SignInUseCase(new AccountRepositoryInMemory());

        var input = SignInInput.builder()
                .identity("user@email.com")
                .build();

        assertThatThrownBy(() -> usecase.handle(input)).isInstanceOf(InvalidCredentialsException.class);
    }

    @Test
    public void deve_recusar_login_quando_senha_nao_for_igual(){

        var repository = new AccountRepositoryInMemory();
        var usecase = new SignInUseCase(repository);

        repository.save("usuario@email.com", "secret");

        var input = SignInInput.builder()
                .identity("usuario@email.com")
                .password("diferente")
                .build();

        assertThatThrownBy(() -> usecase.handle(input)).isInstanceOf(InvalidCredentialsException.class);
    }

}