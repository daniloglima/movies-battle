package br.com.letscode.moviesbattle.domain.account;

import java.util.List;
import java.util.Optional;

public interface AccountRepository {
    Optional<TableAccount> findByIdentity(String identity);
    TableAccount save(String identity, String password);
    List<TableAccount> findAll();
}
