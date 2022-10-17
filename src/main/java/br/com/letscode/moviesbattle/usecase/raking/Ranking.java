package br.com.letscode.moviesbattle.usecase.raking;

import br.com.letscode.moviesbattle.domain.account.TableAccount;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.function.Function;

@Data
@Builder
class Ranking {
    private long userId;
    private String userName;
    private List<Long> battleIds;
    private long roundCount;

    public static Ranking hydrateWith(TableAccount account) {
        return Ranking.builder()
                .userId(account.getId())
                .userName(account.getIdentity())
                .battleIds(List.of())
                .build();
    }

    public Ranking hydrateBattlesIds(Function<Long, List<Long>> hydrate) {
        var battleIds = hydrate.apply(this.userId);

        return Ranking.builder()
                .userId(this.userId)
                .userName(this.userName)
                .battleIds(battleIds)
                .build();
    }

    public Ranking hydrateRounds(Function<List<Long>, Long> hydrate) {
        var count = hydrate.apply(this.battleIds);

        return Ranking.builder()
                .userId(this.userId)
                .userName(this.userName)
                .battleIds(battleIds)
                .roundCount(this.roundCount + count)
                .build();
    }
}
