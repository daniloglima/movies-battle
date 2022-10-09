package br.com.letscode.moviesbattle.usecase.account.signup;

import br.com.letscode.moviesbattle.domain.account.AccountRepository;

public class SignUpUseCase {
    private final AccountRepository repository;

    public SignUpUseCase(AccountRepository repository) {
        this.repository = repository;
    }

    public SignUpOutput handle(SignUpInput input){
        var byIdendity = repository.findByIdentity(input.getIdentity());
        byIdendity.ifPresent((value) -> { throw new UserAlreadyRegisteredException(); });

        repository.save(input.getIdentity(), input.getPassword());

        return SignUpOutput.WithSuccess();
    }

}
