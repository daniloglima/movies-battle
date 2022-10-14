package br.com.letscode.moviesbattle.domain.movies;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class MoviesRepositoryInMemory implements MoviesRepository {

    private List<TableMovies> database = new ArrayList<>();

    @Override
    public TableMovies save(long id, String title, long imdbRating) {
         var table = TableMovies.builder()
                .id(id)
                .title(title)
                .imdbRating(imdbRating)
                .build();

        database.add(table);

        return table;
    }

    @Override
    public Optional<TableMovies> findById(long id) {
        return database.stream()
                       .filter(entry -> entry.getId().equals(id))
                       .findFirst();
    }

}
