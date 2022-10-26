package br.com.letscode.moviesbattle.domain.account;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "account")
@Getter @Builder
@NoArgsConstructor
@AllArgsConstructor
public class TableAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String identity;

    private String password;

}
