package br.com.letscode.moviesbattle.usecase.account.signin;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SignInInput {

    private String identity;
    private String password;

}
