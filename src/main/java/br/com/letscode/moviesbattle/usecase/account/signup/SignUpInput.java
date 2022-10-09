package br.com.letscode.moviesbattle.usecase.account.signup;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignUpInput {

    private String identity;
    private String password;


    public String getPasswordHash(){
        //TODO APPLY HASH
        return this.password;
    }

}
