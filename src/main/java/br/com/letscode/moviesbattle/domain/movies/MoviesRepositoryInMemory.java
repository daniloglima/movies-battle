package br.com.letscode.moviesbattle.domain.movies;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class MoviesRepositoryInMemory implements MoviesRepository {

    private List<TableMovies> database = new ArrayList<>();
    @Override
    public TableMovies save(String title, long imdbRating) {
        var id = database.size() + 1L;

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

    @Override
    public long getMaxId() {
        return database.stream()
                       .mapToLong(TableMovies::getId)
                       .max()
                       .orElseThrow(NoSuchElementException::new);
    }

}
