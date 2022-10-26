package br.com.letscode.moviesbattle.domain.movies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Component
public class MoviesRepositoryInDatabase implements MoviesRepository {

    private final MoviesRepositorySpringAdapter adapter;

    @Autowired
    public MoviesRepositoryInDatabase(MoviesRepositorySpringAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public TableMovies save(String title, long imdbRating) {
        var entity = TableMovies.builder()
                .title(title)
                .imdbRating(imdbRating)
                .build();

        return adapter.save(entity);
    }

    @Override
    public Optional<TableMovies> findById(long id) {
        return Optional.of(adapter.getReferenceById(id));
    }

    @Override
    public long getMaxId() {
        return adapter.getMaxId();
    }

}

@Repository
interface MoviesRepositorySpringAdapter extends JpaRepository<TableMovies, Long> {

    @Query(value = "SELECT max(id) FROM movies")
    Long getMaxId();

}
