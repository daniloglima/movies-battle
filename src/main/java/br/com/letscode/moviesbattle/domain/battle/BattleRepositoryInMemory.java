package br.com.letscode.moviesbattle.domain.battle;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
                .filter(entry -> Objects.nonNull(entry.getOpened()))
                .filter(entry -> entry.getUserId().equals(userId))
                .filter(entry -> entry.getOpened())
                .findFirst();
    }

    @Override
    public void updateOpened(long userId, boolean opened) {

        for (int index = 0; index < database.size(); index++) {

            var entry = database.get(index);
            if(entry.getUserId() == userId && entry.getOpened()){

                var newer = TableBattle.builder()
                        .id(entry.getId())
                        .userId(entry.getUserId())
                        .opened(false)
                        .build();

                database.set(index, newer);
            }

        }

    }

    @Override
    public List<Long> findIdEndedBy(long userId) {
        return database.stream()
                .filter(entry -> Objects.nonNull(entry.getOpened()))
                .filter(entry -> entry.getUserId() == userId)
                .filter(entry -> entry.getOpened() == false)
                .map(TableBattle::getId)
                .toList();
    }
}
