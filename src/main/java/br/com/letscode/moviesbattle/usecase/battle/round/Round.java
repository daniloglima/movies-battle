package br.com.letscode.moviesbattle.usecase.battle.round;

import br.com.letscode.moviesbattle.domain.round.TableRound;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

@Builder @Data
@NoArgsConstructor
@AllArgsConstructor
public class Round {
    private long battleId;
    private List<Item> items;
    private boolean answered;
    private boolean rightAnswer;
    public static Round of(TableRound table){

        var isRight = (table.getRightAnswer() != null && table.getRightAnswer());

        return Round.builder()
                .answered(table.getAnswered())
                .rightAnswer(isRight)
                .battleId(table.getBattleId())
                .items(buildItems(table))
                .build();
    }
    private static List<Item> buildItems(TableRound table) {
        return Arrays.asList(
                Item.of(table.getItemAId()),
                Item.of(table.getItemBId())
        );
    }
    public Round hydrateItems(Function<Long, Item> function){
        var hydrated = items.stream()
                .map((entry) -> function.apply(entry.getId()))
                .toList();

        return Round.builder()
                .battleId(this.battleId)
                .answered(this.answered)
                .rightAnswer(this.rightAnswer)
                .items(hydrated)
                .build();
    }
}

