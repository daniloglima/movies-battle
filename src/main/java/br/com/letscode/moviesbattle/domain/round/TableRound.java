package br.com.letscode.moviesbattle.domain.round;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "round")
@Data @Builder
@NoArgsConstructor
@AllArgsConstructor
public class TableRound {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long battleId;

    @Column(name = "item_a_id")
    private Long itemAId;

    @Column(name = "item_b_id")
    private Long itemBId;

    private Boolean answered;

    private Boolean rightAnswer;

    private Long rightAnswerId;

}
