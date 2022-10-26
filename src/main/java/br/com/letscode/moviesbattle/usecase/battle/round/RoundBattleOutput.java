package br.com.letscode.moviesbattle.usecase.battle.round;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data @Builder
@AllArgsConstructor @NoArgsConstructor
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
