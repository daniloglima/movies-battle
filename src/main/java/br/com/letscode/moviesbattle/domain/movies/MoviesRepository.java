package br.com.letscode.moviesbattle.domain.movies;

import java.util.Optional;

public interface MoviesRepository {

    TableMovies save(String title, long imdbRating);
    Optional<TableMovies> findById(long id);
    long getMaxId();
}
