package br.com.letscode.moviesbattle.usecase.battle.round;

import br.com.letscode.moviesbattle.domain.movies.TableMovies;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    private long id;
    private String title;

    public static Item of(long id) {
        return of(id, "");
    }

    public static Item of(long id, String title) {
        return new Item(id, title);
    }

    public static Item of(TableMovies table) {
        return Item.builder()
                .id(table.getId())
                .title(table.getTitle())
                .build();
    }

}
