package br.com.letscode.moviesbattle.application.repository;

import br.com.letscode.moviesbattle.domain.round.RoundRepository;
import br.com.letscode.moviesbattle.domain.round.TableRound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
public class RoundRepositoryInDatabase implements RoundRepository {

    private final RoundRepositorySpringAdapter adapter;

    @Autowired
    public RoundRepositoryInDatabase(RoundRepositorySpringAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public List<TableRound> findByBattleId(Long battleId) {
        return adapter.findByBattleId(battleId);
    }

    @Override
    public List<TableRound> findByBattleIds(List<Long> battleIds) {
        return adapter.findAllByBattleIdIn(battleIds);
    }

    @Override
    public TableRound save(long battleId, long itemAId, long itemBId) {
        var entity = TableRound.builder()
                .battleId(battleId)
                .itemAId(itemAId)
                .itemBId(itemBId)
                .rightAnswerId(null)
                .answered(false)
                .build();

        return adapter.save(entity);
    }

    @Override
    public long countWrongByBattleId(long battleId) {
        return adapter.countByBattleIdAndRightAnswer(battleId, false);
    }

    @Override
    public Optional<TableRound> findOpenedRoundBy(long battleId) {
        return adapter.findByBattleIdAndAnswered(battleId, false);
    }

    @Override
    public void answerById(long roundId, boolean rightAnswer, long rightAnswerId) {
        adapter.updateRightAnswerWithIdByRoundId(true, roundId, rightAnswer, rightAnswerId);
    }

    @Override
    public long countRightVotesBy(long movieId) {
        return adapter.countByRightAnswerIdAndRightAnswer(movieId, true);
    }

}

@Repository
interface RoundRepositorySpringAdapter extends JpaRepository<TableRound, Long> {
    List<TableRound> findByBattleId(Long battleId);

    List<TableRound> findAllByBattleIdIn(List<Long> battleId);

    long countByBattleIdAndRightAnswer(long battleId, boolean rightAnswer);

    Optional<TableRound> findByBattleIdAndAnswered(long battleId, boolean answered);

    long countByRightAnswerIdAndRightAnswer(long movieId, boolean rightAnswer);

    @Modifying
    @Transactional
    @Query("UPDATE round SET " +
            "answered = :is_answered, " +
            "right_answer = :right_answer, " +
            "right_answer_id = :right_answer_id " +
            "WHERE id = :round_id")
    void updateRightAnswerWithIdByRoundId(
                                          @Param("is_answered") boolean answered,
                                          @Param("round_id") long roundId,
                                          @Param("right_answer") boolean rightAnswer,
                                          @Param("right_answer_id") long rightAnswerId);

}
