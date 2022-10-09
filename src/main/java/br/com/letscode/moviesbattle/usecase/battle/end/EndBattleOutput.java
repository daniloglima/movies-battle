package br.com.letscode.moviesbattle.usecase.battle.end;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EndBattleOutput {

    private boolean success;

    public static EndBattleOutput WithSuccess(){
        return EndBattleOutput.builder()
                .success(true)
                .build();
    }

}
