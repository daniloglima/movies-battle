package br.com.letscode.moviesbattle.usecase.battle.start;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Getter @Builder
public class StartBattleOutput {

    private boolean success;
    public static StartBattleOutput withSuccess() {
        return StartBattleOutput.builder()
                .success(true)
                .build();
    }
}
