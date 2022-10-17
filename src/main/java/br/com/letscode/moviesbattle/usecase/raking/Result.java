package br.com.letscode.moviesbattle.usecase.raking;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
class Result {

    private long userId;

    private String userName;

    private long score;
    public static Result of(Ranking rating) {
        return Result.builder()
                .userId(rating.getUserId())
                .userName(rating.getUserName())
                .score(rating.getRoundCount())
                .build();
    }

}
