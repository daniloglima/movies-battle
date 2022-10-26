package br.com.letscode.moviesbattle.domain.battle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class BattleRepositoryInDatabase implements BattleRepository {

    private final BattleRepositorySpringAdapter adapter;

    @Autowired
    public BattleRepositoryInDatabase(BattleRepositorySpringAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public TableBattle save(long userId, boolean opened) {
        var entity = TableBattle.builder()
                .userId(userId)
                .opened(opened)
                .build();

        return adapter.save(entity);
    }

    @Override
    public boolean hasOpenedByUserId(long userId) {
        return adapter.countByUserIdAndOpened(userId, true) > 0;
    }

    @Override
    public Optional<TableBattle> findOpenedBattleByUserId(long userId) {
        var table = adapter.findByUserIdAndOpened(userId, true);
        return Optional.ofNullable(table);
    }

    @Override
    public void updateOpened(long userId, boolean opened) {
        adapter.updateOpened(userId, opened);
    }

    @Override
    public List<Long> findIdEndedBy(long userId) {
        var result = adapter.findIdByUserIdAndOpened(userId, false);
        return (Objects.isNull(result) ? Collections.emptyList() : result);
    }
}

@Repository
interface BattleRepositorySpringAdapter extends JpaRepository<TableBattle, Long> {
    public Integer countByUserIdAndOpened(Long userId, boolean opened);
    public TableBattle findByUserIdAndOpened(Long userId, boolean opened);
    @Query("SELECT id FROM battle WHERE user_id = :user_id AND opened = :is_opened")
    public List<Long> findIdByUserIdAndOpened(@Param("user_id") Long userId, @Param("is_opened") boolean opened);
    @Modifying
    @Transactional
    @Query("UPDATE battle SET opened = :is_opened WHERE user_id = :user_id")
    public void updateOpened(@Param("user_id") Long userId, @Param("is_opened") boolean opened);

}
