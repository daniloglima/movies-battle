package br.com.letscode.moviesbattle.domain.movies;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "movies")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TableMovies {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    @Column(name = "imdb_rating")
    private Long imdbRating;

}