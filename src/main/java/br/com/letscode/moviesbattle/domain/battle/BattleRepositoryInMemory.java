package br.com.letscode.moviesbattle.domain.battle;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BattleRepositoryInMemory implements BattleRepository {

    private List<TableBattle> database = new ArrayList<>();

    @Override
    public TableBattle save(long userId, boolean opened) {
        var id = database.size() + 1L;

        var table = TableBattle.builder()
                .opened(opened)
                .userId(userId)
                .id(id)
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

    @Override
    public Optional<TableBattle> findOpenedBattleByUserId(long userId) {
        return database.stream()
                .filter(entry -> entry.getUserId().equals(userId))
                .filter(entry -> entry.getOpened() == true)
                .findFirst();
    }

    @Override
    public void updateOpened(long userId, boolean opened) {

        for (int index = 0; index < database.size(); index++) {

            var entry = database.get(index);
            if(entry.getUserId() == userId && entry.getOpened() == true){
                entry.setOpened(false);
                database.set(index, entry);
            }

        }

    }
}
