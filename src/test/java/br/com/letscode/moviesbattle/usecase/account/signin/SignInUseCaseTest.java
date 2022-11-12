package br.com.letscode.moviesbattle.usecase.account.signin;

import br.com.letscode.moviesbattle.application.repository.AccountRepositoryInMemory;
import br.com.letscode.moviesbattle.domain.account.AccountRepository;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class SignInUseCaseTest {

    @Test
    public void deve_efetuar_login(){
        AccountRepository repository = new AccountRepositoryInMemory();
        SignInUseCase usecase = new SignInUseCase(repository);

        repository.save("usuario@email.com", "secret");

        SignInInput input = SignInInput.builder()
                .identity("usuario@email.com")
                .password("secret")
                .build();

        SignInOutput output = usecase.handle(input);

        assertThat(output).isNotNull();

        assertThat(output.getId()).isNotNull();
        assertThat(output.getIdentity()).isNotNull();
    }

    @Test
    public void deve_recusar_login_quando_email_nao_for_encontrado(){

        SignInUseCase usecase = new SignInUseCase(new AccountRepositoryInMemory());

        SignInInput input = SignInInput.builder()
                .identity("user@email.com")
                .build();

        assertThatThrownBy(() -> usecase.handle(input)).isInstanceOf(InvalidCredentialsException.class);
    }

    @Test
    public void deve_recusar_login_quando_senha_nao_for_igual(){

        AccountRepository repository = new AccountRepositoryInMemory();
        SignInUseCase usecase = new SignInUseCase(repository);

        repository.save("usuario@email.com", "secret");

        SignInInput input = SignInInput.builder()
                .identity("usuario@email.com")
                .password("diferente")
                .build();

        assertThatThrownBy(() -> usecase.handle(input)).isInstanceOf(InvalidCredentialsException.class);
    }

}