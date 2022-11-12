package br.com.letscode.moviesbattle.usecase.raking;

import br.com.letscode.moviesbattle.domain.account.TableAccount;
import br.com.letscode.moviesbattle.domain.round.TableRound;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.function.Function;

@Getter @Builder
class Ranking {
    private long userId;
    private String userName;
    private List<Long> battleIds;
    private long totalRounds;
    private long totalRights;
    private long score ;

    public static Ranking transform(TableAccount account) {
        return Ranking.builder()
                .userId(account.getId())
                .userName(account.getIdentity())
                .build();
    }

    public Ranking hydrateBattlesIds(Function<Long, List<Long>> hydrate) {
        List<Long> battleIds = hydrate.apply(getUserId());

        return Ranking.builder()
                .userId(getUserId())
                .userName(getUserName())
                .battleIds(battleIds)
                .build();
    }

    public Ranking hydrateRounds(Function<List<Long>, List<TableRound>> hydrate) {
        List<TableRound> rounds = hydrate.apply(getBattleIds());

        long total = rounds.size();
        long rights = rounds.stream()
                .filter(TableRound::getRightAnswer)
                .count();

        return Ranking.builder()
                .userId(getUserId())
                .userName(getUserName())
                .totalRounds(total)
                .totalRights(rights)
                .build();
    }

    public Ranking calculateScore() {

        long score = 0L;

        if(getTotalRounds() > 0 && getTotalRights() > 0){
            long percentage = ((getTotalRights() * 100) / getTotalRounds());
            score = getTotalRounds() * percentage;
        }

        return Ranking.builder()
                .userId(getUserId())
                .userName(getUserName())
                .battleIds(getBattleIds())
                .totalRounds(getTotalRounds())
                .totalRights(getTotalRights())
                .score(score)
                .build();
    }
}
