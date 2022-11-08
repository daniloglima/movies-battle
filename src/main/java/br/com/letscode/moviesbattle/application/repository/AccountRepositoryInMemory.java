package br.com.letscode.moviesbattle.application.repository;

import br.com.letscode.moviesbattle.domain.account.AccountRepository;
import br.com.letscode.moviesbattle.domain.account.TableAccount;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
public class AccountRepositoryInMemory implements AccountRepository {
    private List<TableAccount> database = new ArrayList<>();
    @Override
    public Optional<TableAccount> findByIdentity(String identity) {
        return database.stream().filter(entry -> identity.equalsIgnoreCase(entry.getIdentity())).findFirst();
    }
    @Override
    public TableAccount save(String identity, String password) {
        var id = database.size() + 1L;
        var account = TableAccount.builder()
                .id(id)
                .identity(identity)
                .password(password)
                .build();

        database.add(account);

        return account;
    }

    @Override
    public List<TableAccount> findAll() {
        return this.database;
    }
}
