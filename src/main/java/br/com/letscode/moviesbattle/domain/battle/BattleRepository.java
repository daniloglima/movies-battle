package br.com.letscode.moviesbattle.domain.battle;

import java.util.Optional;

public interface BattleRepository {

    TableBattle save(long userId, boolean opened);

    boolean hasOpenedByUserId(long userId);

    Optional<TableBattle> findOpenedBattleByUserId(long userId);

    void updateOpened(long userId, boolean opened);
}
