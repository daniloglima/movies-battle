package br.com.letscode.moviesbattle.domain.account;

import java.util.Optional;

public interface AccountRepository {
    Optional<TableAccount> findByIdentity(String identity);

    void save(String identity, String password);
}
