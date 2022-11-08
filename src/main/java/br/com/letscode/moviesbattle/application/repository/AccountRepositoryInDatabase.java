package br.com.letscode.moviesbattle.application.repository;

import br.com.letscode.moviesbattle.domain.account.AccountRepository;
import br.com.letscode.moviesbattle.domain.account.TableAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Component
public class AccountRepositoryInDatabase implements AccountRepository {

    private final AccountRepositorySpringAdapter adapter;

    @Autowired
    public AccountRepositoryInDatabase(AccountRepositorySpringAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public Optional<TableAccount> findByIdentity(String identity) {
        return Optional.ofNullable(adapter.findByIdentity(identity));
    }

    @Override
    public TableAccount save(String identity, String password) {
        TableAccount entity = TableAccount.builder()
                .identity(identity)
                .password(password)
                .build();

        return adapter.save(entity);
    }

    @Override
    public List<TableAccount> findAll() {
        return adapter.findAll();
    }
}

@Repository
interface AccountRepositorySpringAdapter extends JpaRepository<TableAccount, Long> {
    TableAccount findByIdentity(String identity);

}
