package br.com.letscode.moviesbattle.usecase.battle.end;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EndBattleInput {

    private long userId;

}
