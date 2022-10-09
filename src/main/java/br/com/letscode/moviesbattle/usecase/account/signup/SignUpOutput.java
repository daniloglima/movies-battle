package br.com.letscode.moviesbattle.usecase.account.signup;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignUpOutput {

    private boolean success;
    public static SignUpOutput WithSuccess(){
        return SignUpOutput.builder()
                .success(true)
                .build();
    }
}
