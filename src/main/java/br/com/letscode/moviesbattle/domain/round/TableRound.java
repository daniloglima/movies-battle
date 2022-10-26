package br.com.letscode.moviesbattle.domain.round;

import lombok.*;

import javax.persistence.*;

@Entity(name = "round")
@Getter @Builder
@NoArgsConstructor
@AllArgsConstructor
public class TableRound {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
