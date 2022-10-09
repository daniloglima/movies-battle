package br.com.letscode.moviesbattle.usecase.account.signin;

import br.com.letscode.moviesbattle.domain.account.AccountRepository;
import br.com.letscode.moviesbattle.domain.account.TableAccount;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class SignInUseCase {

    private final AccountRepository repository;

    @Autowired
    public SignInUseCase(AccountRepository repository) {
        this.repository = repository;
    }

    SignInOutput handle(SignInInput input){
        Optional<TableAccount> byIdentity = repository.findByIdentity(input.getIdentity());
        var table = byIdentity.orElseThrow(InvalidCredentialsException::new);

        var password = table.getPassword();
        if(input.getPassword().equalsIgnoreCase(password)){
            return toOutput(table);
        }

        throw new InvalidCredentialsException();
    }

    private SignInOutput toOutput(TableAccount table){
        return SignInOutput.builder()
                .id(table.getId())
                .identity(table.getIdentity())
                .build();
    }

}

