package br.com.letscode.moviesbattle.usecase.battle.start;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StartBattleInput {

    private long userId;

}
