package br.com.letscode.moviesbattle.domain.round;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class RoundRepositoryInMemory implements RoundRepository {

    private List<TableRound> database = new ArrayList<>();

    @Override
    public TableRound save(long battleId, long itemAId, long itemBId, boolean answered, boolean rightAnswer) {
        Long id = database.size() + 1L;

        var table = TableRound.builder()
                .battleId(battleId)
                .itemAId(itemAId)
                .itemBId(itemBId)
                .rightAnswer(rightAnswer)
                .answered(answered)
                .id(id)
                .build();

        database.add(table);

        return table;
    }

    @Override
    public long countWrongByBattleId(long battleId) {
        return database.stream()
                .filter(entry -> entry.getBattleId().equals(battleId))
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
    public List<TableRound> findByBattleId(Long battleId) {
        return database.stream()
                .filter(entry -> entry.getBattleId().equals(battleId))
                .collect(Collectors.toList());
    }

}
