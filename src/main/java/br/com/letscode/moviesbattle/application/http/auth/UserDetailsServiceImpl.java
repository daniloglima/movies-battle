package br.com.letscode.moviesbattle.application.http.auth;

import br.com.letscode.moviesbattle.domain.account.AccountRepository;
import br.com.letscode.moviesbattle.domain.account.TableAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    private final AccountRepository accountRepository;

    @Autowired
    public UserDetailsServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<TableAccount> byIdentity = accountRepository.findByIdentity(username);
        var account = byIdentity.orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new User(account.getIdentity(), account.getPassword(), Collections.emptyList());
    }

}
