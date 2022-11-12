package br.com.letscode.moviesbattle.application.http.controller.facade;

import br.com.letscode.moviesbattle.usecase.raking.Result;

public class RankingResponse {

    private final long userId;
    private final String userName;
    private final long score;

    public RankingResponse(long userId, String userName, long score) {
        this.userId = userId;
        this.userName = userName;
        this.score = score;
    }

    public long getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public long getScore() {
        return score;
    }

    public static RankingResponse of(Result result) {
        return new RankingResponse(
                result.getUserId(),
                result.getUserName(),
                result.getScore()
        );
    }

}
