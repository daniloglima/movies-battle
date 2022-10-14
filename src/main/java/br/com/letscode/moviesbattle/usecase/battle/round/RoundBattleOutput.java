package br.com.letscode.moviesbattle.usecase.battle.round;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RoundBattleOutput {

    private boolean success;
    private List<Item> items;

    public static RoundBattleOutput WithSuccess(List<Item> items){
        return RoundBattleOutput.builder()
                .items(items)
                .success(true)
                .build();
    }

}
