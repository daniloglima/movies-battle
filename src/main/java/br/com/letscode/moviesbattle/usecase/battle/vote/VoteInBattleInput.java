package br.com.letscode.moviesbattle.usecase.battle.vote;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VoteInBattleInput {

    private long userId;
    private long otherId;
    private long answerId;

}
