package br.com.letscode.moviesbattle.usecase.account.signup;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Getter
@Builder
public class SignUpInput {

    private String identity;
    private String password;

}
