package br.com.letscode.moviesbattle.usecase.battle.vote;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class VoteInBattleOutput {

    private boolean success;
    public static VoteInBattleOutput withSuccess() {
        return VoteInBattleOutput.builder()
                .success(true)
                .build();
    }
}
