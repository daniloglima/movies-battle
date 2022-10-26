package br.com.letscode.moviesbattle.usecase.battle.vote;

import br.com.letscode.moviesbattle.domain.movies.TableMovies;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Getter
@Builder
public class Movie {

    private long id;
    private long score;
    private long votes;
    private boolean right;

    public static Movie of(TableMovies table, long votes) {
        return Movie.builder()
                .id(table.getId())
                .votes(votes)
                .score(table.getImdbRating())
                .build();
    }

    public long calculateRating() {
        return (getScore() * getVotes());
    }

    public Movie checkAnswer(Movie other) {
        var thisRating = this.calculateRating();
        var otherRating = other.calculateRating();

        if(thisRating > otherRating){
            return Movie.builder()
                        .id(this.getId())
                        .score(thisRating)
                        .right(true)
                        .build();
        }

        return Movie.builder()
                .id(other.getId())
                .score(otherRating)
                .right(false)
                .build();

    }
}
