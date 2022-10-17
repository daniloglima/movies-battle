package br.com.letscode.moviesbattle.domain.round;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class RoundRepositoryInMemory implements RoundRepository {

    private List<TableRound> database = new ArrayList<>();

    @Override
    public TableRound save(long battleId, long itemAId, long itemBId) {
        Long id = database.size() + 1L;

        var table = TableRound.builder()
                .battleId(battleId)
                .itemAId(itemAId)
                .itemBId(itemBId)
                .rightAnswerId(null)
                .answered(false)
                .id(id)
                .build();

        database.add(table);

        return table;
    }

    @Override
    public long countWrongByBattleId(long battleId) {
        return database.stream()
                .filter(entry -> entry.getBattleId().equals(battleId))
                .filter(entry -> Objects.nonNull(entry.getRightAnswer()))
                .filter(entry -> entry.getRightAnswer() == false)
                .count();
    }

    @Override
    public Optional<TableRound> findOpenedRoundBy(long battleId) {
        return database.stream()
                .filter(entry -> entry.getBattleId().equals(battleId))
                .filter(entry -> entry.getAnswered() == Boolean.FALSE)
                .findFirst();
    }

    @Override
    public void answerById(long roundId, boolean isRight, long movieId) {
        for (int index = 0; index < database.size(); index++) {
            var entry = database.get(index);

            if(entry.getId() == roundId){
                var table = TableRound.builder()
                        .answered(true)
                        .id(entry.getId())
                        .battleId(entry.getBattleId())
                        .itemAId(entry.getItemAId())
                        .itemBId(entry.getItemBId())
                        .rightAnswer(isRight)
                        .rightAnswerId(movieId)
                        .build();

                database.set(index, table);
                return;
            }
        }
    }

    @Override
    public long countRightVotesBy(long movieId) {
        return database.stream()
                .filter(entry -> Objects.nonNull(entry.getRightAnswer()))
                .filter(entry -> entry.getRightAnswerId() == movieId)
                .filter(entry -> entry.getRightAnswer() == true)
                .count();
    }

    @Override
    public List<TableRound> findByBattleId(Long battleId) {
        return database.stream()
                .filter(entry -> entry.getBattleId().equals(battleId))
                .collect(Collectors.toList());
    }

}
