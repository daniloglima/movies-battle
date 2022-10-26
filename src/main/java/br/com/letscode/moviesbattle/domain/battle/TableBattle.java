package br.com.letscode.moviesbattle.domain.battle;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "battle")
@Getter @Builder
@NoArgsConstructor
@AllArgsConstructor
public class TableBattle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Boolean opened;

}
