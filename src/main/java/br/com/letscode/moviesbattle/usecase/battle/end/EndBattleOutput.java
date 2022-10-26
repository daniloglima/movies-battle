package br.com.letscode.moviesbattle.usecase.battle.end;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Getter @Builder
public class EndBattleOutput {

    private boolean success;

    public static EndBattleOutput WithSuccess(){
        return EndBattleOutput.builder()
                .success(true)
                .build();
    }

}
