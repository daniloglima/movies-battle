package br.com.letscode.moviesbattle.domain.battle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BattleRepositoryInMemory implements BattleRepository {

    private List<TableBattle> database = new ArrayList<>();

    @Override
    public TableBattle save(long userId, boolean opened) {
        var table = TableBattle.builder()
                .opened(opened)
                .userId(userId)
                .id(new Random().nextLong())
                .build();

        database.add(table);

        return table;
    }

    @Override
    public boolean hasOpenedByUserId(long userId) {
        return database.stream()
                .filter(entry -> entry.getUserId().equals(userId))
                .anyMatch(TableBattle::getOpened);
    }
}
