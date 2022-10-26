package br.com.letscode.moviesbattle.application.http.controller.facade;

import br.com.letscode.moviesbattle.usecase.raking.Result;

public record RankingResponse(long userId, String userName, long score) {

    public static RankingResponse of(Result result) {
        return new RankingResponse(
                result.getUserId(),
                result.getUserName(),
                result.getScore()
        );
    }

}
