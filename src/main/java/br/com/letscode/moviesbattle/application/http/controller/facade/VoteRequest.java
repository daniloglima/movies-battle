package br.com.letscode.moviesbattle.application.http.controller.facade;

public class VoteRequest {
    private final long answerId;

    public VoteRequest(long answerId) {
        this.answerId = answerId;
    }

    public long getAnswerId() {
        return answerId;
    }

}
