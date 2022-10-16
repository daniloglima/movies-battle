package br.com.letscode.moviesbattle.domain.round;

import java.util.List;
import java.util.Optional;

public interface RoundRepository {
    List<TableRound> findByBattleId(Long battleId);
    TableRound save(long battleId, long itemAId, long itemBId, boolean rightAnswer, boolean answered);
    long countWrongByBattleId(long battleId);
    Optional<TableRound> findOpenedRoundBy(long battleId);
}
