package br.com.letscode.moviesbattle.usecase.account.signin;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SignInOutput {

    private long id;
    private String identity;

}
