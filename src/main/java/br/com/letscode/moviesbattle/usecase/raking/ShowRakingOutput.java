package br.com.letscode.moviesbattle.usecase.raking;

import lombok.*;

import java.util.Comparator;
import java.util.List;

@Getter
@AllArgsConstructor
public class ShowRakingOutput {

    private List<Result> result;
    public static ShowRakingOutput of(List<Ranking> rankings) {
        var results = rankings.stream()
                                       .map(Result::of)
                                       .sorted(Comparator.comparingLong(Result::getScore))
                                       .toList();

        return new ShowRakingOutput(results);
    }

}