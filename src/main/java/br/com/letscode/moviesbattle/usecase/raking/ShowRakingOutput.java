package br.com.letscode.moviesbattle.usecase.raking;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ShowRakingOutput {

    private final List<Result> result;

    public ShowRakingOutput(List<Result> result) {
        this.result = result;
    }

    public List<Result> getResult() {
        return result;
    }

    public static ShowRakingOutput of(List<Ranking> rankings) {
        List<Result> collect = rankings.stream()
                .map(Result::of)
                .sorted(Comparator.comparingLong(Result::getScore))
                .collect(Collectors.toList());

        return new ShowRakingOutput(collect);
    }

}