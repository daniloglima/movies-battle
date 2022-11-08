package br.com.letscode.moviesbattle.usecase.account.signup;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SignUpInput {

    private String identity;
    private String password;

}
