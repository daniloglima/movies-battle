package br.com.letscode.moviesbattle.usecase.account.signin;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignInOutput {

    private long id;
    private String identity;

}
