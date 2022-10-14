package br.com.letscode.moviesbattle.domain.round;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class RoundRepositoryInMemory implements RoundRepository {

    private List<TableRound> database = new ArrayList<>();

    @Override
    public TableRound save(long battleId, long itemAId, long itemBId, boolean answered) {
        var table = TableRound.builder()
                .battleId(battleId)
                .itemAId(itemAId)
                .itemBId(itemBId)
                .answered(answered)
                .id(new Random().nextLong())
                .build();

        database.add(table);

        return table;
    }

    @Override
    public List<TableRound> findByBattleId(Long battleId) {
        return database.stream()
                .filter(entry -> entry.getBattleId().equals(battleId))
                .collect(Collectors.toList());
    }

}
