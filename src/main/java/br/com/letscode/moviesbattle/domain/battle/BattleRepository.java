package br.com.letscode.moviesbattle.domain.battle;

public interface BattleRepository {

    TableBattle save(long userId, boolean opened);

    boolean hasOpenedByUserId(long userId);

}
