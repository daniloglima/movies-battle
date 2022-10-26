package br.com.letscode.moviesbattle.usecase.raking;

import br.com.letscode.moviesbattle.domain.account.AccountRepository;
import br.com.letscode.moviesbattle.domain.account.TableAccount;
import br.com.letscode.moviesbattle.domain.battle.BattleRepository;
import br.com.letscode.moviesbattle.domain.round.RoundRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ShowRankingUseCase {
    private final RoundRepository roundRepository;
    private final BattleRepository battleRepository;
    private final AccountRepository accountRepository;

    public ShowRankingUseCase(RoundRepository roundRepository, BattleRepository battleRepository, AccountRepository accountRepository) {
        this.roundRepository = roundRepository;
        this.battleRepository = battleRepository;
        this.accountRepository = accountRepository;
    }

    public ShowRakingOutput handle(){
        List<TableAccount> accounts = accountRepository.findAll();

        List<Ranking> rankings = accounts.stream()
                .map(Ranking::transform)
                .map(ranking -> ranking.hydrateBattlesIds(battleRepository::findIdEndedBy))
                .map(ranking -> ranking.hydrateRounds(roundRepository::findByBattleIds))
                .map(Ranking::calculateScore)
                .toList();

        return ShowRakingOutput.of(rankings);
    }

}