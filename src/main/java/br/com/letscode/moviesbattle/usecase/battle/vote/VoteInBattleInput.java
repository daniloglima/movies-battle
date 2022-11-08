package br.com.letscode.moviesbattle.usecase.battle.vote;

import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class VoteInBattleInput {

    private long userId;
    private long answerId;

}
