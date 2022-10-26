package br.com.letscode.moviesbattle.domain.movies;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "movies")
@Getter @Builder
@NoArgsConstructor
@AllArgsConstructor
public class TableMovies {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private Long imdbRating;

}
